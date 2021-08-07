package com.weweibuy.wfs.brms.manager;

import com.weweibuy.brms.api.model.RuleBuildConstant;
import com.weweibuy.brms.api.model.dto.resp.RuleSetModelRespDTO;
import com.weweibuy.brms.api.model.eum.ModelTypeEum;
import com.weweibuy.brms.api.model.eum.RuleActionValueTypeEum;
import com.weweibuy.wfs.brms.model.resp.RuleActionWithDescRespDTO;
import com.weweibuy.wfs.brms.model.resp.RuleAttrDetailRespDTO;
import com.weweibuy.wfs.brms.model.resp.RuleConditionWithDescRespDTO;
import com.weweibuy.wfs.brms.repository.RuleRepository;
import com.weweibuy.wfs.brms.support.RuleTranslateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/7/29 20:55
 **/
@Component
@RequiredArgsConstructor
public class RuleQueryManager {

    private final RuleRepository ruleRepository;

    private final RuleTranslateHelper ruleTranslateHelper;


    public Mono<RuleAttrDetailRespDTO> ruleTranslate(String ruleKey) {
        return ruleRepository.rule(ruleKey)
                .flatMap(ro ->
                        ro.map(r -> ruleRepository.ruleSetModel(r.getRuleSetKey())
                                .flatMap(rml -> desc(ruleKey, rml)))
                                .orElse(Mono.empty()));
    }


    public Mono<RuleAttrDetailRespDTO> desc(String ruleKey, List<RuleSetModelRespDTO> ruleSetModelList) {
        RuleSetModelRespDTO inputModel = ruleSetModelList.stream()
                .filter(rm -> ModelTypeEum.INPUT.toString().equals(rm.getModelType()))
                .findFirst()
                .orElse(null);

        RuleSetModelRespDTO outputModel = ruleSetModelList.stream()
                .filter(rm -> ModelTypeEum.OUTPUT.toString().equals(rm.getModelType()))
                .findFirst()
                .orElse(null);

        Mono<List<RuleConditionWithDescRespDTO>> conditionMono = conditionList(inputModel, ruleKey);

        Mono<List<RuleActionWithDescRespDTO>> actionMono = actionList(outputModel, inputModel, ruleKey);

        return Mono.just(new RuleAttrDetailRespDTO())
                .flatMap(ruleTranslateRespDTO ->
                        Optional.ofNullable(inputModel)
                                .map(i -> conditionMono.map(list ->
                                        ruleTranslateRespDTO.condition(list)))
                                .orElseGet(() -> Mono.just(ruleTranslateRespDTO)))
                .flatMap(ruleTranslateRespDTO ->
                        Optional.ofNullable(outputModel)
                                .map(o -> actionMono.map(list ->
                                        ruleTranslateRespDTO.action(list)))
                                .orElseGet(() -> Mono.just(ruleTranslateRespDTO)));
    }


    public Mono<List<RuleConditionWithDescRespDTO>> conditionList(RuleSetModelRespDTO inputModel, String ruleKey) {
        return ruleTranslateHelper.translateAttr(inputModel, RuleBuildConstant.MODEL_ATTR_SEPARATOR)
                .flatMap(stringStringMap -> ruleRepository.ruleCondition(ruleKey)
                        .map(rcl -> rcl.stream()
                                .map(RuleConditionWithDescRespDTO::fromRuleCondition)
                                .peek(c -> c.conditionDesc(stringStringMap.get(c.getAttrName())))
                                .collect(Collectors.toList())));
    }


    public Mono<List<RuleActionWithDescRespDTO>> actionList(RuleSetModelRespDTO outputModel, RuleSetModelRespDTO inputModel, String ruleKey) {

        return ruleTranslateHelper.translateAttr(outputModel, ".")
                .flatMap(stringStringMap -> ruleRepository.ruleAction(ruleKey)
                        .flatMap(ruleActionList -> Optional.ofNullable(inputModel)
                                .map(i -> ruleTranslateHelper.translateAttr(i, RuleBuildConstant.FORMULA_ATTR_SEPARATOR))
                                .orElse(Mono.just(Collections.emptyMap()))
                                .map(inputMap ->
                                        ruleActionList.stream()
                                                .map(RuleActionWithDescRespDTO::fromRuleAction)
                                                .peek(a ->
                                                        RuleActionValueTypeEum.fromValue(a.getActionValueType())
                                                                .ifPresent(t -> a.actionDesc(t, stringStringMap.get(a.getAttrName()), inputMap)))
                                                .collect(Collectors.toList()))));


    }

}
