package com.priv.client.hystrix;

import com.priv.client.UserServiceClient;
import com.priv.dto.RespDTO;
import com.priv.entity.User;
import org.springframework.stereotype.Component;


/**
 * Created by fangzhipeng on 2017/5/31.
 */
@Component
public class UserServiceHystrix implements UserServiceClient {

    @Override
    public RespDTO<User> getUser(String token, String username) {
        System.out.println(token);
        System.out.println(username);
        return null;
    }
}
