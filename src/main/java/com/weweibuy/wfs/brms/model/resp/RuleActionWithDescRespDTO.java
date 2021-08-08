package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleActionRespDTO;
import com.weweibuy.brms.api.model.eum.RuleActionValueTypeEum;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.framework.common.core.utils.OptionalEnhance;
import lombok.Data;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/7/25 22:18
 **/
@Data
public class RuleActionWithDescRespDTO extends RuleActionRespDTO {

    /**
     * 属性名称描述
     */
    private String attrNameDesc;

    /**
     * 动作值计算公式 描述
     */
    private String valueCalculateFormulaDesc;

    /**
     * 取整方式描述
     */
    private String calculateRoundingModeDesc;

    /**
     * 动作描述
     */
    private String actionDesc;

    private RuleModelAttrDescRespDTO inputModelAttr;

    private RuleModelAttrDescRespDTO outputModelAttr;


    public static RuleActionWithDescRespDTO fromRuleAction(RuleActionRespDTO ruleActionRespDTO) {
        return BeanCopyUtils.copy(ruleActionRespDTO, RuleActionWithDescRespDTO.class);
    }

    public void actionDesc(RuleActionValueTypeEum actionValueType,
                           RuleModelAttrDescRespDTO outputModelAttr, Map<String, RuleModelAttrDescRespDTO> inputAttrMap) {
        if (outputModelAttr != null) {
            this.outputModelAttr = outputModelAttr;
            this.attrNameDesc = outputModelAttr.getFullAttrNameDesc();
            this.actionDesc = actionDesc0(actionValueType, attrNameDesc, inputAttrMap);
        }

    }

    private String actionDesc0(RuleActionValueTypeEum actionValueType,
                               String attrDesc, Map<String, RuleModelAttrDescRespDTO> inputAttrMap) {
        switch (actionValueType) {
            case INPUT:
                return attrDesc + " = " + getActionValue();
            case CALCULATE:
                String anElse = Optional.ofNullable(getValueCalculateFormula())
                        .map(s -> Arrays.stream(s.split(" "))
                                .map(String::trim)
                                .map(i -> OptionalEnhance.ofNullable(inputAttrMap.get(i))
                                        .peek(a -> this.inputModelAttr = a)
                                        .map(RuleModelAttrDescRespDTO::getFullAttrNameDesc)
                                        .orElse(i))
                                .collect(Collectors.joining(" ")))
                        .orElse("");
                return attrDesc + " = " + anElse;
            default:
                return "";
        }
    }

}
