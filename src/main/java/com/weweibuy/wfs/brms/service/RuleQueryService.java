package com.weweibuy.wfs.brms.service;

import com.weweibuy.brms.api.model.eum.ModelTypeEum;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.framework.common.core.utils.OptionalEnhance;
import com.weweibuy.wfs.brms.model.resp.RuleSetDetailRespDTO;
import com.weweibuy.wfs.brms.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/7/25 17:09
 **/
@Service
@RequiredArgsConstructor
public class RuleQueryService {

    private final RuleRepository ruleRepository;


    public Mono<Optional<RuleSetDetailRespDTO>> ruleSetDetail(String ruleSetKey) {
        return ruleRepository.ruleSet(ruleSetKey)
                .map(ro -> ro.map(r -> BeanCopyUtils.copy(r, RuleSetDetailRespDTO.class)))
                .flatMap(ro ->
                        ruleRepository.ruleSetModel(ruleSetKey)
                                .map(ra -> ra.stream()
                                        .collect(Collectors.toMap(a -> ModelTypeEum.INPUT.toString().equals(a.getModelType()),
                                                Function.identity())))
                                .map(m -> OptionalEnhance.fromOptional(ro)
                                        .peek(rd -> rd.setModelInput(m.get(true)))
                                        .peek(rd -> rd.setModelOutput(m.get(false))).toOptional()));

    }


}
