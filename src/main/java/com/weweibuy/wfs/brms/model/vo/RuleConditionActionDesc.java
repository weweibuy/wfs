package com.weweibuy.wfs.brms.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/8/7 12:44
 **/
@Data
public class RuleConditionActionDesc {

    private List<String> condition;

    private List<String> action;


}
