package com.weweibuy.wfs.brms.repository;

import com.weweibuy.brms.api.model.dto.resp.RuleSetRespDTO;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.wfs.brms.client.RuleQueryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author durenhao
 * @date 2021/7/25 17:11
 **/
@Repository
@RequiredArgsConstructor
public class RuleRepository {

    private final RuleQueryClient ruleQueryClient;


    public CommonDataResponse<CommonPageResult<RuleSetRespDTO>> ruleSet(String ruleSetKey, String ruleSetName,
                                                                        CommonPageRequest pageRequest) {
        return ruleQueryClient.ruleSet(ruleSetKey, ruleSetName, pageRequest);
    }
}
