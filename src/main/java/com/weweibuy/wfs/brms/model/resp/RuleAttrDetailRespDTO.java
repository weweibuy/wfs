package com.weweibuy.wfs.brms.model.resp;

import lombok.Data;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/7/25 22:10
 **/
@Data
public class RuleAttrDetailRespDTO {

    /**
     * 规则条件描述
     */
    private List<RuleConditionWithDescRespDTO> condition;

    /**
     * 输入模型描述
     */
    private List<RuleModelAttrDescRespDTO> inputModel;

    /**
     * 输出模型描述
     */
    private List<RuleModelAttrDescRespDTO> outputModel;

    /**
     * 规则动作描述
     */
    private List<RuleActionWithDescRespDTO> action;

    public RuleAttrDetailRespDTO condition(List<RuleConditionWithDescRespDTO> condition) {
        this.condition = condition;
        return this;
    }

    public RuleAttrDetailRespDTO action(List<RuleActionWithDescRespDTO> action) {
        this.action = action;
        return this;
    }


}

