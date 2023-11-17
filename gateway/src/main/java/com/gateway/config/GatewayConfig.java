//package com.gateway.config;
//
//import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
//import com.alibaba.csp.sentinel.adapter.spring.webflux.SentinelWebFluxFilter;
//import com.alibaba.csp.sentinel.adapter.spring.webflux.exception.SentinelBlockExceptionHandler;
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.WebProperties;
//import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.validation.MessageCodesResolver;
//import org.springframework.validation.Validator;
//import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
//import org.springframework.web.reactive.config.*;
//import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
//import org.springframework.web.reactive.result.view.ViewResolver;
//import org.springframework.web.reactive.socket.server.WebSocketService;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * gateway网关配置
// */
//@Configuration
//public class GatewayConfig {
//
//    private final List<ViewResolver> viewResolvers;
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//    public GatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
//                         ServerCodecConfigurer serverCodecConfigurer) {
//        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//
//    @Bean
//    @Order(-1)
//    public SentinelBlockExceptionHandler sentinelBlockExceptionHandler() {
//        // Register the block exception handler for Spring WebFlux.
//        return new SentinelBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }
//
//    @Bean
//    @Order(-1)
//    public SentinelWebFluxFilter sentinelWebFluxFilter() {
//        // Register the Sentinel WebFlux filter.
//        return new SentinelWebFluxFilter();
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        // Register the block exception handler for Spring Cloud Gateway.
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }
//
//    @Bean
//    @Order(-1)
//    public GlobalFilter globalFilter() {
//        return new SentinelGatewayFilter();
//    }
//
//    @PostConstruct
//    public void doInit() {
//        initCustomizedApis();
//        initGatewayRules();
//    }
//
//    private void initCustomizedApis() {
//        Set<ApiDefinition> definitions = new HashSet<>();
//        ApiDefinition api1 = new ApiDefinition("some_customized_api")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    add(new ApiPathPredicateItem().setPattern("/ahas"));
//                    add(new ApiPathPredicateItem().setPattern("/product/**")
//                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});
//        ApiDefinition api2 = new ApiDefinition("another_customized_api")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    add(new ApiPathPredicateItem().setPattern("/**")
//                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});
//        definitions.add(api1);
//        definitions.add(api2);
//        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
//    }
//
//    private void initGatewayRules() {
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        rules.add(new GatewayFlowRule("aliyun_route")
//                .setCount(10)
//                .setIntervalSec(1)
//        );
//        rules.add(new GatewayFlowRule("aliyun_route")
//                .setCount(2)
//                .setIntervalSec(2)
//                .setBurst(2)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
//                )
//        );
//        rules.add(new GatewayFlowRule("httpbin_route")
//                .setCount(10)
//                .setIntervalSec(1)
//                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
//                .setMaxQueueingTimeoutMs(600)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_HEADER)
//                        .setFieldName("X-Sentinel-Flag")
//                )
//        );
//        rules.add(new GatewayFlowRule("httpbin_route")
//                .setCount(1)
//                .setIntervalSec(1)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
//                        .setFieldName("pa")
//                )
//        );
//        rules.add(new GatewayFlowRule("httpbin_route")
//                .setCount(2)
//                .setIntervalSec(30)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
//                        .setFieldName("type")
//                        .setPattern("warn")
//                        .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_CONTAINS)
//                )
//        );
//
//        rules.add(new GatewayFlowRule("some_customized_api")
//                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
//                .setCount(5)
//                .setIntervalSec(1)
//                .setParamItem(new GatewayParamFlowItem()
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
//                        .setFieldName("pn")
//                )
//        );
//        GatewayRuleManager.loadRules(rules);
//    }
//}