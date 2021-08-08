package com.weweibuy.wfs.brms.manager;

import com.weweibuy.wfs.brms.repository.RuleRepository;
import com.weweibuy.wfs.brms.support.RuleTranslateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2021/7/29 20:55
 **/
@Component
@RequiredArgsConstructor
public class RuleQueryManager {

    private final RuleRepository ruleRepository;

    private final RuleTranslateHelper ruleTranslateHelper;



}
