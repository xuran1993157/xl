package com.xl.auth.service.hystrix;

import com.xl.auth.service.UserService;
import com.xl.common.vo.UserVO;
import org.springframework.stereotype.Service;

/**
 * 回调类
 * @MethodName:
 * @Description:
 * @Param:
 * @Return:
 * @Author: xr
 * @Date: 2019/12/24
**/
@Service
public class UserServiceHystrix implements UserService {

    @Override
    public UserVO getUserInfo(String token) {
        System.out.println("进入断路器");
        return null;
    }
}
