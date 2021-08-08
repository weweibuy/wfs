package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleModelAttrRespDTO;
import lombok.Data;

/**
 * 模型描述
 *
 * @author durenhao
 * @date 2021/8/8 22:24
 **/
@Data
public class RuleModelAttrDescRespDTO extends RuleModelAttrRespDTO {

    /**
     * 属性全名
     */
    private String fullAttrName;

    /**
     * 属性全名描述
     */
    private String fullAttrNameDesc;


    public void fullAttrAndDesc(String fullAttrName, String fullAttrNameDesc) {
        this.fullAttrName = fullAttrName;
        this.fullAttrNameDesc = fullAttrNameDesc;
    }

}
