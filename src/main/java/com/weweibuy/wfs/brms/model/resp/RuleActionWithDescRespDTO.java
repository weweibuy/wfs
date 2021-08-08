package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleActionRespDTO;
import com.weweibuy.brms.api.model.eum.RuleActionValueTypeEum;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
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


    public static RuleActionWithDescRespDTO fromRuleAction(RuleActionRespDTO ruleActionRespDTO) {
        return BeanCopyUtils.copy(ruleActionRespDTO, RuleActionWithDescRespDTO.class);
    }

    public void actionDesc(RuleActionValueTypeEum actionValueType,
                           String attrDesc, Map<String, String> inputAttrMap) {
        this.attrNameDesc = attrDesc;
        this.actionDesc = actionDesc0(actionValueType, attrDesc, inputAttrMap);
    }

    private String actionDesc0(RuleActionValueTypeEum actionValueType,
                               String attrDesc, Map<String, String> inputAttrMap) {
        switch (actionValueType) {
            case INPUT:
                return attrDesc + " = " + getActionValue();
            case CALCULATE:
                String anElse = Optional.ofNullable(getValueCalculateFormula())
                        .map(s -> Arrays.stream(s.split(" "))
                                .map(String::trim)
                                .map(i -> Optional.ofNullable(inputAttrMap.get(i)).orElse(i))
                                .collect(Collectors.joining(" ")))
                        .orElse("");
                return attrDesc + " = " + anElse;
            default:
                return "";
        }
    }

}
