package com.priv.client;

import com.priv.client.hystrix.AuthServiceHystrix;
import com.priv.entity.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author fenghen
 * @FeignClient(value = "uaa-service",configuration=FeignConfig.class,fallback =AuthServiceHystrix.class )
 * value为调用的服务名称，configuration为feign的配置类，这里没有，一般通过配置类可以设置远程调用失败后重试，
 */
@FeignClient(value = "uaa-service",fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {

    /**
     * /oauth/token是OAuth2的默认路径，通过该路径可以拿到token,通过熔断器，如果出现访问延迟等问题，会熔断到AuthServiceHystrix，返回null,就是空的token
     * @param authorization
     * @param type
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type,
                 @RequestParam("username") String username, @RequestParam("password") String password);
}



