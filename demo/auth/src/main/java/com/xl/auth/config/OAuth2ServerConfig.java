package com.xl.auth.config;

import com.xl.auth.handler.CustomerAuthHandler;
import com.xl.auth.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @ClassName OAuth2ServerConfig
 * @Description:
 * @Author xr
 * @Date 2019/12/23
 * @Version V1.0
 **/
@Configuration
public class OAuth2ServerConfig {
    public static final String ROLE_ADMIN = "ADMIN";
    //访问客户端密钥
    public static final String CLIENT_SECRET = "111111";
    //访问客户端ID
    public static final String CLIENT_ID ="client_1";
    //鉴权模式
    public static final String GRANT_TYPE[] = {"password","refresh_token"};

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private CustomerAuthHandler customerAuthHandler;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.stateless(false)
                     .accessDeniedHandler(customerAuthHandler)
                     .authenticationEntryPoint(customerAuthHandler);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                 .and()
                                 //请求权限配置
                                 .authorizeRequests()
                                 //下边的路径放行,不需要经过认证
                                 .antMatchers("/oauth/*", "/auth/user/login").permitAll()
                                 //OPTIONS请求不需要鉴权
                                 .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                 //用户的增删改接口只允许管理员访问
                                 .antMatchers(HttpMethod.POST, "/auth/user").hasAnyAuthority(ROLE_ADMIN)
                                 .antMatchers(HttpMethod.PUT, "/auth/user").hasAnyAuthority(ROLE_ADMIN)
                                 .antMatchers(HttpMethod.DELETE, "/auth/user").hasAnyAuthority(ROLE_ADMIN)
                                 //获取角色 权限列表接口只允许系统管理员及高级用户访问
                                 .antMatchers(HttpMethod.GET, "/auth/role").hasAnyAuthority(ROLE_ADMIN)
                                 //其余接口没有角色限制，但需要经过认证，只要携带token就可以放行
                                 .anyRequest()
                                 .authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        RedisConnectionFactory redisConnectionFactory;
        @Autowired
        private DataSource dataSource;


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String finalSecret =  "{bcrypt}" + new BCryptPasswordEncoder().encode(CLIENT_SECRET);
            //配置两个客户端,一个用于password认证一个用于client认证
            clients.inMemory()
                                 .withClient(CLIENT_ID)
                                 //密码模式及refresh_token模式
                                 .authorizedGrantTypes(GRANT_TYPE[0], GRANT_TYPE[1])
                                 .scopes("all")
                                 .secret(finalSecret);
            //这个地方指的是从jdbc查出数据来存储
//            clients.withClientDetails(clientDetails());
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(new InMemoryTokenStore())
                    .authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }


        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("isAuthenticated()")
                                 .checkTokenAccess("permitAll()");
        }

        /**
         * 这里配置数据源，就不再走内存读取的方式，从数据库读取
         * @MethodName: clientDetails
         * @Description:
         * @Param: []
         * @Return: org.springframework.security.oauth2.provider.ClientDetailsService
         * @Author: xr
         * @Date: 2019/12/24
        **/
        @Bean
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }
    }
}
