package com.priv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author fenghen
 * OAuth2  Provider 的配置
 */
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 配置客户端，与资源服务器保持一致
     * accessTokenValiditySeconds(24*3600) ：24小时过期
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("uaa-service")
                .secret("123456")
                .scopes("service")
                .autoApprove(true)
                .authorizedGrantTypes("implicit","refresh_token", "password", "authorization_code")
                .accessTokenValiditySeconds(24*3600);
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * 配置了tokenStore,tokenStore使用JwtTokenStore,JwtTokenStore没有存储，tokenStore需要JwtAccessTokenConverter对象，用于token的转换
     * 本案例使用非对称加密算法RSA对jwt加密
     * 配置了authenticationManager，需要配置AuthenticationManager这个Bean,来源于WebSecurityConfigurationAdapt的配置，只有配置了这个bean，才能够密码验证
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * 必须设置allowFormAuthenticationForClients 否则没有办法用postman获取token
     * 也需要指定密码加密方式BCryptPasswordEncoder
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


    /**
     * tokenStore就是用来保存token(封装在OAuth2AccessToken中),TokenStore的实现类，有InMemoryTokenStore、JdbcTokenStore、JwkTokenStore、RedisTokenStore。
     * InMemoryTokenStore将OAuth2AccessToken保存在内存中，它有很多的ConcurrentHashMap属性。
     * JdbcTokenStore将OAuth2AccessToken保存在数据库中，其构造方法需要DataSource，用于构造JdbcTemplate，通过JdbcTemplate来操作数据库。
     * RedisTokenStore将OAuth2AccessToken保存到Reis中，构造方法需要RedisConnectionFactory，之后通过Connection操作Redis。
     * JwtTokenStore,如下所示
     * @return  返回一个JwtTokenStore类型的tokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * JwtAccessTokenConverter是用来生成token的转换器，而token令牌默认是有签名的，且资源服务器需要验证这个签名。此处的加密及验签包括两种方式：
     * 对称加密、非对称加密（公钥密钥）
     * 对称加密需要授权服务器和资源服务器存储同一key值，而非对称加密可使用密钥加密，暴露公钥给资源服务器验签，本文中使用非对称加密方式，
     * JwtAccessTokenConverter是TokenEnhancer的子类，帮助程序在JWT编码的令牌值和OAuth身份验证信息之间进行转换（在两个方向上），同时充当TokenEnhancer授予令牌的时间。
     * @return
     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //导入证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("fzp-jwt.jks"), "fzp123".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("fzp-jwt"));
        return converter;
    }
}
