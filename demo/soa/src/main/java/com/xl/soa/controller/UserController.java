package com.xl.soa.controller;

import com.xl.common.vo.UserVO;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description: TODO
 * @Author xr
 * @Date 2019/12/24
 * @Version V1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public UserVO getUserVOTest(String token){
        System.out.println("token:"+token);
        return new UserVO("aaa","123456");
    }
}
