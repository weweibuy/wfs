package com.weweibuy.wfs.brms.client;

import com.weweibuy.brms.api.model.dto.req.RuleHitLogReqDTO;
import com.weweibuy.brms.api.model.dto.resp.*;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/7/25 17:11
 **/
@ReactiveFeignClient(name = "ruleQueryClient", qualifier = "ruleQueryClient", url = "localhost:8050")
@RequestMapping("/rule/query")
public interface RuleQueryClient {

    @GetMapping("/rule-set/list")
    Mono<CommonDataResponse<CommonPageResult<RuleSetRespDTO>>> ruleSet(@RequestParam("ruleSetKey") String ruleSetKey, @RequestParam("ruleSetName") String ruleSetName,
                                                                       @SpringQueryMap CommonPageRequest pageRequest);

    @GetMapping("/rule-set")
    Mono<CommonDataResponse<RuleSetRespDTO>> ruleSet(@RequestParam("ruleSetKey") String ruleSetKey);

    @GetMapping("/rule/list")
    Mono<CommonDataResponse<CommonPageResult<RuleRespDTO>>> ruleList(@RequestParam("ruleSetKey") String ruleSetKey, @SpringQueryMap CommonPageRequest pageRequest);

    @GetMapping("/rule-set/model")
    Mono<CommonDataResponse<List<RuleSetModelRespDTO>>> ruleSetModel(@RequestParam("ruleSetKey") String ruleSetKey);

    @GetMapping("/model/attr")
    Mono<CommonDataResponse<List<RuleModelAttrRespDTO>>> ruleSetModelAttr(@RequestParam("modelKey") String modelKey);

    @GetMapping("/rule/condition")
    Mono<CommonDataResponse<List<RuleConditionRespDTO>>> ruleCondition(@RequestParam("ruleKey") String ruleKey);

    @GetMapping("/rule/action")
    Mono<CommonDataResponse<List<RuleActionRespDTO>>> ruleAction(@RequestParam("ruleKey") String ruleKey);

    @GetMapping("/rule/hit-log/list")
    Mono<CommonDataResponse<CommonPageResult<RuleHitLogRespDTO>>> ruleHitLog(@SpringQueryMap RuleHitLogReqDTO query);

    @GetMapping("/rule")
    Mono<CommonDataResponse<RuleRespDTO>> rule(@RequestParam("ruleKey") String ruleKey);

    @GetMapping("/model")
    Mono<CommonDataResponse<ModelRespDTO>> model(@RequestParam("modelKey") String modelKey);


}
