package com.xl.soa.controller;

import com.xl.common.bean.Address;
import com.xl.common.vo.BaseResultVO;
import com.xl.common.vo.UserVO;
import com.xl.soa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private UserService userService;

    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public UserVO getUserVOTest(String token){
        System.out.println("token:"+token);
        return new UserVO("aaa","123456");
    }

    @RequestMapping(value = "getAddress", method = RequestMethod.GET)
    @ResponseBody
    public BaseResultVO getAddress(){
        List<Address> addresses = userService.findAll();
        return new BaseResultVO(addresses);
    }
}
