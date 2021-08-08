package com.weweibuy.wfs.brms.service;

import com.weweibuy.brms.api.model.RuleBuildConstant;
import com.weweibuy.brms.api.model.dto.resp.RuleRespDTO;
import com.weweibuy.brms.api.model.dto.resp.RuleSetModelRespDTO;
import com.weweibuy.brms.api.model.eum.ModelTypeEum;
import com.weweibuy.brms.api.model.eum.RuleActionValueTypeEum;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.framework.common.core.utils.OptionalEnhance;
import com.weweibuy.wfs.brms.manager.RuleQueryManager;
import com.weweibuy.wfs.brms.model.resp.*;
import com.weweibuy.wfs.brms.repository.RuleRepository;
import com.weweibuy.wfs.brms.support.RuleTranslateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    private final RuleQueryManager ruleQueryManager;

    private final RuleTranslateHelper ruleTranslateHelper;


    public RuleSetDetailRespDTO ruleSetDetail(String ruleSetKey) {
        return OptionalEnhance.fromOptional(ruleRepository.ruleSet(ruleSetKey))
                .map(r -> BeanCopyUtils.copy(r, RuleSetDetailRespDTO.class))
                .peek(r -> {
                    Map<Boolean, RuleSetModelRespDTO> map = ruleRepository.ruleSetModel(ruleSetKey).stream()
                            .collect(Collectors.toMap(a -> ModelTypeEum.INPUT.toString().equals(a.getModelType()),
                                    Function.identity()));
                    r.model(map.get(true), map.get(false));
                })
                .orElse(null);
    }


    public RuleAttrDetailRespDTO ruleAttrDetail(String ruleKey) {
        RuleRespDTO rule = ruleRepository.rule(ruleKey)
                .orElseThrow(() -> Exceptions.business("规则不存在"));

        List<RuleSetModelRespDTO> modelList = ruleRepository.ruleSetModel(rule.getRuleSetKey());
        RuleSetModelRespDTO inputModel = modelList.stream()
                .filter(r -> ModelTypeEum.INPUT.toString().equals(r.getModelType()))
                .findFirst()
                .orElse(null);

        RuleSetModelRespDTO outputModel = modelList.stream()
                .filter(r -> ModelTypeEum.OUTPUT.toString().equals(r.getModelType()))
                .findFirst()
                .orElse(null);

        RuleAttrDetailRespDTO ruleAttrDetailRespDTO = new RuleAttrDetailRespDTO();

        if (inputModel != null) {
            List<RuleModelAttrDescRespDTO> inputAttrDesc = ruleTranslateHelper.translateAttr(inputModel, RuleBuildConstant.MODEL_ATTR_SEPARATOR);
            ruleAttrDetailRespDTO.setInputModel(inputAttrDesc);

            Map<String, RuleModelAttrDescRespDTO> modelAttrDescMap = inputAttrDesc.stream()
                    .collect(Collectors.toMap(RuleModelAttrDescRespDTO::getFullAttrName, Function.identity()));

            List<RuleConditionWithDescRespDTO> collect = ruleRepository.ruleCondition(ruleKey).stream()
                    .map(RuleConditionWithDescRespDTO::fromRuleCondition)
                    .peek(c ->
                            c.conditionDesc(modelAttrDescMap.get(c.getAttrName())))
                    .collect(Collectors.toList());
            ruleAttrDetailRespDTO.setCondition(collect);
        }

        if (outputModel != null) {
            List<RuleModelAttrDescRespDTO> outputAttrDesc = ruleTranslateHelper.translateAttr(outputModel, ".");
            ruleAttrDetailRespDTO.setOutputModel(outputAttrDesc);

            Map<String, RuleModelAttrDescRespDTO> modelAttrDescMap = outputAttrDesc.stream()
                    .collect(Collectors.toMap(RuleModelAttrDescRespDTO::getFullAttrName, Function.identity()));

            Map<String, RuleModelAttrDescRespDTO> inputMap = Optional.ofNullable(inputModel)
                    .map(i -> ruleTranslateHelper.translateAttr(i, RuleBuildConstant.FORMULA_ATTR_SEPARATOR))
                    .map(ial -> ial.stream()
                            .collect(Collectors.toMap(RuleModelAttrDescRespDTO::getFullAttrName, Function.identity())))
                    .orElse(Collections.emptyMap());

            List<RuleActionWithDescRespDTO> collect = ruleRepository.ruleAction(ruleKey).stream()
                    .map(RuleActionWithDescRespDTO::fromRuleAction)
                    .peek(a ->
                            RuleActionValueTypeEum.fromValue(a.getActionValueType())
                                    .ifPresent(t -> a.actionDesc(t, modelAttrDescMap.get(a.getAttrName()), inputMap)))
                    .collect(Collectors.toList());
            ruleAttrDetailRespDTO.setAction(collect);
        }

        return ruleAttrDetailRespDTO;
    }


}
