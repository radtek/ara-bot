package com.zhuiyi.config.aspect;

import com.zhuiyi.config.param.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tree
 * @version 1.0
 * date: 2018/8/23 17:47
 * description: 此类用于支持未实现的接口透传老服务
 * own:
 */
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {

    @Autowired
    private CustomProperties customProperties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("yibot/v1/api/faqtouchoverview/exportFaq", customProperties.getOldServiceUrl() + "data_get/fetchFaqData");
        registry.addRedirectViewController("searchlog", customProperties.getOldServiceUrl() + "searchlog");
        registry.addRedirectViewController("data_get/assureDataAmount", customProperties.getOldServiceUrl() + "data_get/assureDataAmount");
        registry.addRedirectViewController("data_get/assureData", customProperties.getOldServiceUrl() + "data_get/assureData");
        registry.addRedirectViewController("hot_analysis/hotWord", customProperties.getOldServiceUrl() + "hot_analysis/hotWord");
        registry.addRedirectViewController("sentiment_analysis/askTrend", customProperties.getOldServiceUrl() + "sentiment_analysis/askTrend");
        registry.addRedirectViewController("sentiment_analysis/wordInsight", customProperties.getOldServiceUrl() + "sentiment_analysis/wordInsight");
        registry.addRedirectViewController("sentiment_analysis/typicalIntent", customProperties.getOldServiceUrl() + "sentiment_analysis/typicalIntent");
        registry.addRedirectViewController("sentiment_analysis/emotionAnalyse", customProperties.getOldServiceUrl() + "sentiment_analysis/emotionAnalyse");
        registry.addRedirectViewController("relatedFaq", customProperties.getOldServiceUrl() + "relatedFaq");
        registry.addRedirectViewController("assistanceStatis", customProperties.getOldServiceUrl() + "assistanceStatis");
    }
}
