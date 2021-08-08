package com.weweibuy.wfs.brms.repository;

import com.weweibuy.brms.api.model.dto.resp.*;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.wfs.brms.client.RuleQueryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author durenhao
 * @date 2021/7/25 17:11
 **/
@Repository
@RequiredArgsConstructor
public class RuleRepository {

    private final RuleQueryClient ruleQueryClient;


    public CommonPageResult<RuleSetRespDTO> ruleSet(String ruleSetKey, String ruleSetName,
                                                    CommonPageRequest pageRequest) {
        return Optional.ofNullable(ruleQueryClient.ruleSet(ruleSetKey, ruleSetName, pageRequest))
                .map(CommonDataResponse::getData)
                .orElse(CommonPageResult.empty());
    }

    public Optional<RuleSetRespDTO> ruleSet(String ruleSetKey) {
        return Optional.ofNullable(ruleQueryClient.ruleSet(ruleSetKey))
                .map(CommonDataResponse::getData);
    }


    public List<RuleSetModelRespDTO> ruleSetModel(String ruleSetKey) {
        return Optional.ofNullable(ruleQueryClient.ruleSetModel(ruleSetKey))
                .map(CommonDataResponse::getData)
                .orElse(Collections.emptyList());
    }

    public List<RuleActionRespDTO> ruleAction(String ruleSetKey) {
        return Optional.ofNullable(ruleQueryClient.ruleAction(ruleSetKey))
                .map(CommonDataResponse::getData)
                .orElse(Collections.emptyList());
    }

    public List<RuleConditionRespDTO> ruleCondition(String ruleSetKey) {
        return Optional.ofNullable(ruleQueryClient.ruleCondition(ruleSetKey))
                .map(CommonDataResponse::getData)
                .orElse(Collections.emptyList());
    }

    public Optional<RuleRespDTO> rule(String ruleKey) {
        return Optional.ofNullable(ruleQueryClient.rule(ruleKey))
                .map(CommonDataResponse::getData);
    }

    public List<RuleModelAttrRespDTO> ruleSetModelAttr(String modelKey) {
        return Optional.ofNullable(ruleQueryClient.ruleSetModelAttr(modelKey))
                .map(CommonDataResponse::getData)
                .orElse(Collections.emptyList());
    }

    public Optional<ModelRespDTO> model(String modelKey) {
        return Optional.ofNullable(ruleQueryClient.model(modelKey))
                .map(CommonDataResponse::getData);
    }
}
