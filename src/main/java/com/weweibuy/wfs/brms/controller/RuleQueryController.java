package com.weweibuy.wfs.brms.controller;

import com.weweibuy.brms.api.model.dto.resp.RuleSetRespDTO;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.wfs.brms.controller.contant.RuleBasePath;
import com.weweibuy.wfs.brms.service.RuleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author durenhao
 * @date 2021/7/25 17:05
 **/
@RestController
@RequestMapping(RuleBasePath.PATH + "/query")
@RequiredArgsConstructor
public class RuleQueryController {

    private final RuleQueryService ruleQueryService;

    @GetMapping("/rule-set/list")
    public CommonDataResponse<CommonPageResult<RuleSetRespDTO>> ruleSet(String ruleSetKey, String ruleSetName,
                                                                        CommonPageRequest pageRequest) {
        return ruleQueryService.ruleSet(ruleSetKey, ruleSetName, pageRequest);
    }

}
