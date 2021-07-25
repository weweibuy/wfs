package com.weweibuy.wfs.brms.client;

import com.weweibuy.brms.api.RuleQueryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author durenhao
 * @date 2021/7/25 17:11
 **/
@FeignClient(name = "ruleQueryClient", contextId = "ruleQueryClient", url = "localhost:8050")
public interface RuleQueryClient extends RuleQueryApi {
}
