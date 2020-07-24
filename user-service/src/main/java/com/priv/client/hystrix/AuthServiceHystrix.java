package com.priv.client.hystrix;


import com.priv.client.AuthServiceClient;
import com.priv.entity.JWT;
import org.springframework.stereotype.Component;


/**
 * @author fenghen
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        System.out.println("--------opps getToken hystrix---------");
        return null;
    }
}
