package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleActionRespDTO;
import lombok.Data;

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

}
