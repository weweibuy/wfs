package com.weweibuy.wfs.brms.controller;

import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.wfs.brms.controller.contant.RuleBasePath;
import com.weweibuy.wfs.brms.model.resp.RuleSetDetailRespDTO;
import com.weweibuy.wfs.brms.service.RuleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2021/7/25 17:05
 **/
@RestController
@RequestMapping(RuleBasePath.PATH + "/query")
@RequiredArgsConstructor
public class RuleQueryController {

    private final RuleQueryService ruleQueryService;

    @GetMapping("/rule-set/detail")
    public Mono<CommonDataResponse<RuleSetDetailRespDTO>> ruleSetDetail(String ruleSetKey) {
        return ruleQueryService.ruleSetDetail(ruleSetKey)
                .map(o -> o.map(CommonDataResponse::success)
                        .orElse(CommonDataResponse.success(null)));
    }

    @GetMapping("/rule/attr/detail")
    public Mono<CommonDataResponse<RuleSetDetailRespDTO>> ruleAttrDetail(String ruleSetKey) {
        return ruleQueryService.ruleSetDetail(ruleSetKey)
                .map(o -> o.map(CommonDataResponse::success)
                        .orElse(CommonDataResponse.success(null)));
    }



}
