package com.weweibuy.wfs.brms.repository;

import com.weweibuy.brms.api.model.dto.resp.*;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.wfs.brms.client.RuleQueryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

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


    public Mono<CommonDataResponse<CommonPageResult<RuleSetRespDTO>>> ruleSet(String ruleSetKey, String ruleSetName,
                                                                              CommonPageRequest pageRequest) {
        return ruleQueryClient.ruleSet(ruleSetKey, ruleSetName, pageRequest);
    }

    public Mono<Optional<RuleSetRespDTO>> ruleSet(String ruleSetKey) {
        return ruleQueryClient.ruleSet(ruleSetKey)
                .map(r -> Optional.ofNullable(r.getData()));
    }


    public Mono<List<RuleSetModelRespDTO>> ruleSetModel(String ruleSetKey) {
        return ruleQueryClient.ruleSetModel(ruleSetKey)
                .map(c -> Optional.ofNullable(c.getData())
                        .orElse(Collections.emptyList()));
    }

    public Mono<List<RuleActionRespDTO>> ruleAction(String ruleSetKey) {
        return ruleQueryClient.ruleAction(ruleSetKey)
                .map(c -> Optional.ofNullable(c.getData())
                        .orElse(Collections.emptyList()));
    }

    public Mono<List<RuleConditionRespDTO>> ruleCondition(String ruleSetKey) {
        return ruleQueryClient.ruleCondition(ruleSetKey)
                .map(c -> Optional.ofNullable(c.getData())
                        .orElse(Collections.emptyList()));
    }

    public Mono<Optional<RuleRespDTO>> rule(String ruleKey) {
        return ruleQueryClient.rule(ruleKey)
                .map(c -> Optional.ofNullable(c.getData()));
    }

    public Mono<List<RuleModelAttrRespDTO>> ruleSetModelAttr(String modelKey) {
        return ruleQueryClient.ruleSetModelAttr(modelKey)
                .map(c -> Optional.ofNullable(c.getData())
                        .orElse(Collections.emptyList()));
    }

    public Mono<Optional<ModelRespDTO>> model(String modelKey) {
        return ruleQueryClient.model(modelKey)
                .map(c -> Optional.ofNullable(c.getData()));
    }
}
