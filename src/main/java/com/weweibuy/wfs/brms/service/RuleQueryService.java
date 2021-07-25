package com.weweibuy.wfs.brms.service;

import com.weweibuy.brms.api.model.dto.resp.RuleSetRespDTO;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.wfs.brms.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author durenhao
 * @date 2021/7/25 17:09
 **/
@Service
@RequiredArgsConstructor
public class RuleQueryService {

    private final RuleRepository ruleRepository;

    public CommonDataResponse<CommonPageResult<RuleSetRespDTO>> ruleSet(String ruleSetKey, String ruleSetName,
                                                                        CommonPageRequest pageRequest) {
        return ruleRepository.ruleSet(ruleSetKey, ruleSetName, pageRequest);
    }
}
