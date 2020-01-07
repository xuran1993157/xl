package com.xl.auth.controller;

import com.xl.auth.service.UserService;
import com.xl.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description: TODO
 * @Author xr
 * @Date 2019/12/24
 * @Version V1.0
 **/
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public UserVO getUserVOTest(String token){
        return userService.getUserInfo("token11111");
    }
}
