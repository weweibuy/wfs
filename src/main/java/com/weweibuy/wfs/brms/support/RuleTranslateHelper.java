package com.weweibuy.wfs.brms.support;

import com.weweibuy.brms.api.model.dto.resp.ModelRespDTO;
import com.weweibuy.brms.api.model.dto.resp.RuleModelAttrRespDTO;
import com.weweibuy.brms.api.model.dto.resp.RuleSetModelRespDTO;
import com.weweibuy.brms.api.model.eum.ModelAttrTypeEum;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.wfs.brms.model.resp.RuleModelAttrDescRespDTO;
import com.weweibuy.wfs.brms.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/7/27 22:40
 **/
@Component
@RequiredArgsConstructor
public class RuleTranslateHelper {

    private final RuleRepository ruleRepository;


    public List<RuleModelAttrDescRespDTO> translateAttr(RuleSetModelRespDTO ruleSetModel, String keySeparator) {
        List<RuleModelAttrDescRespDTO> modelAttrDescList = new ArrayList<>();
        ruleRepository.model(ruleSetModel.getModelKey())
                .ifPresent(m -> translateAttr(m, "", m.getModelDesc(), keySeparator, modelAttrDescList));
        return modelAttrDescList;
    }

    private void translateAttr(ModelRespDTO model, String lastKey, String lastName, String keySeparator, List<RuleModelAttrDescRespDTO> container) {
        List<RuleModelAttrRespDTO> modelAttrList = ruleRepository.ruleSetModelAttr(model.getModelKey());
        Map<Boolean, List<RuleModelAttrRespDTO>> isObjectMap = modelAttrList.stream()
                .collect(Collectors.groupingBy(a -> ModelAttrTypeEum.OBJECT.toString().equals(a.getAttrType())));
        List<RuleModelAttrRespDTO> noObjectAttrList = isObjectMap.get(false);
        if (CollectionUtils.isNotEmpty(noObjectAttrList)) {
            noObjectAttrList.stream()
                    .map(a -> BeanCopyUtils.copy(a, RuleModelAttrDescRespDTO.class))
                    .peek(a -> a.fullAttrAndDesc(key(lastKey, a.getAttrName(), keySeparator), desc(lastName, a.getAttrDesc())))
                    .forEach(container::add);
        }
        List<RuleModelAttrRespDTO> objectAttrList = isObjectMap.get(true);
        if (CollectionUtils.isNotEmpty(objectAttrList)) {
            objectAttrList.forEach(a -> ruleRepository.model(a.getAttrModelKeyRef())
                    .ifPresent(m -> translateAttr(m, key(lastKey, a.getAttrName(), keySeparator),
                            desc(lastName, a.getAttrDesc()), keySeparator, container)));
        }

    }

    private String key(String lastKey, String currentKey, String keySeparator) {
        return Optional.ofNullable(lastKey)
                .filter(StringUtils::isNotBlank)
                .map(l -> l + keySeparator + currentKey)
                .orElse(currentKey);
    }

    private String desc(String lastName, String attrDesc) {
        return lastName + "." + attrDesc;
    }


}
