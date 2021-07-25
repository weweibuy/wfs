package com.weweibuy.wfs.brms.model.resp;

import com.weweibuy.brms.api.model.dto.resp.RuleSetModelRespDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author durenhao
 * @date 2021/7/25 20:54
 **/
@Data
public class RuleSetDetailRespDTO {

    /**
     * id自增1
     */
    private Long id;

    /**
     * 规则集key(package)
     */
    private String ruleSetKey;

    /**
     * 规则集名称
     */
    private String ruleSetName;

    /**
     * 输入模型
     */
    private RuleSetModelRespDTO modelInput;

    /**
     * 输出模型
     */
    private RuleSetModelRespDTO modelOutput;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
