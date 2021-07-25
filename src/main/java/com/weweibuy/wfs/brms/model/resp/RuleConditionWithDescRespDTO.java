package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleConditionRespDTO;
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


}
