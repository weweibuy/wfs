package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleConditionRespDTO;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import lombok.Data;

/**
 * @author durenhao
 * @date 2021/7/25 22:15
 **/
@Data
public class RuleConditionWithDescRespDTO extends RuleConditionRespDTO {

    /**
     * 属性名称描述
     */
    private String attrNameDesc;

    /**
     * 条件操作符描述
     */
    private String conditionOperatorDesc;

    /**
     * 逻辑操作符描述
     */
    private String logicalOperatorDesc;

    /**
     * 条件描述
     */
    private String conditionDesc;

    public static RuleConditionWithDescRespDTO fromRuleCondition(RuleConditionRespDTO ruleConditionRespDTO) {
        return BeanCopyUtils.copy(ruleConditionRespDTO, RuleConditionWithDescRespDTO.class);
    }

    public void conditionDesc(String attrName) {
        this.conditionDesc = attrName + " " + getConditionOperator() + " " + getConditionValue();
    }

}
