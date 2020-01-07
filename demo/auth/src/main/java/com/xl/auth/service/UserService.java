package com.xl.auth.service;

import com.xl.auth.service.hystrix.UserServiceHystrix;
import com.xl.common.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName UserService
 * @Description:
 * @Author xr
 * @Date 2019/12/24
 * @Version V1.0
 **/
@FeignClient(name="soa",fallback = UserServiceHystrix.class)
public interface UserService {

    @RequestMapping(value="/user/getUserInfo",method= RequestMethod.POST)
    public UserVO getUserInfo(@RequestParam("token") final String token);
}
