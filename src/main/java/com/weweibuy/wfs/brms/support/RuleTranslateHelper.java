package com.weweibuy.wfs.brms.support;

import com.weweibuy.brms.api.model.dto.resp.ModelRespDTO;
import com.weweibuy.brms.api.model.dto.resp.RuleModelAttrRespDTO;
import com.weweibuy.brms.api.model.dto.resp.RuleSetModelRespDTO;
import com.weweibuy.brms.api.model.eum.ModelAttrTypeEum;
import com.weweibuy.wfs.brms.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/7/27 22:40
 **/
@Component
@RequiredArgsConstructor
public class RuleTranslateHelper {

    private final RuleRepository ruleRepository;


    public Mono<Map<String, String>> translateAttr(RuleSetModelRespDTO ruleSetModel, String keySeparator) {
        Map<String, String> container = new HashMap<>();
        return ruleRepository.model(ruleSetModel.getModelKey())
                .flatMap(mo ->
                        mo.map(m -> translateAttr(m, "",
                                m.getModelDesc(), keySeparator, container))
                                .orElse(Mono.empty()));
    }


    private Mono<Map<String, String>> translateAttr(ModelRespDTO model, String lastKey, String lastName, String keySeparator,
                                                    Map<String, String> container) {
        return ruleRepository.ruleSetModelAttr(model.getModelKey())
                .map(mal -> mal.stream()
                        .collect(Collectors.groupingBy(a -> ModelAttrTypeEum.OBJECT.toString().equals(a.getAttrType()))))
                .flatMap(isObjectMap -> {
                    List<RuleModelAttrRespDTO> noObjectAttrList = isObjectMap.get(false);
                    Mono<Map<String, String>> mono1 = Mono.just(new HashMap<>());
                    if (CollectionUtils.isNotEmpty(noObjectAttrList)) {
                        noObjectAttrList.forEach(a ->
                                container.put(key(lastKey, a.getAttrName(), keySeparator), lastName + "." + a.getAttrDesc()));
                        mono1 = Mono.just(container);
                    }
                    List<RuleModelAttrRespDTO> objectAttrList = isObjectMap.get(true);
                    Mono<Map<String, String>> mono2 = Mono.just(new HashMap<>());
                    if (CollectionUtils.isNotEmpty(objectAttrList)) {
                        mono2 = Flux.fromIterable(objectAttrList)
                                .flatMap(a -> ruleRepository.model(a.getAttrModelKeyRef())
                                        .flatMap(mao -> mao.map(m ->
                                                translateAttr(m, key(lastKey, a.getAttrName(), keySeparator),
                                                        lastName + "." + a.getAttrDesc(), keySeparator, container))
                                                .orElse(Mono.empty())))
                                .reduce((a, b) -> {
                                            a.putAll(b);
                                            return a;
                                        }
                                );
                    }
                    return mono1.zipWith(mono2, (a, b) -> {
                        a.putAll(b);
                        return a;
                    });
                });

    }

    private String key(String lastKey, String currentKey, String keySeparator) {
        return Optional.ofNullable(lastKey)
                .filter(StringUtils::isNotBlank)
                .map(l -> l + keySeparator + currentKey)
                .orElse(currentKey);
    }

}
