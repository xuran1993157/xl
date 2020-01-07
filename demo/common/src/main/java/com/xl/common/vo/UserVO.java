package com.xl.common.vo;

import lombok.Data;

/**
 * @ClassName UserVO
 * @Description: TODO
 * @Author xr
 * @Date 2019/12/24
 * @Version V1.0
 **/
@Data
public class UserVO extends BaseVO{

    public UserVO(){
        super();
    }
    public UserVO(String username, String password){
        this.username = username;
        this.password = password;
    }

    private String username;

    private String password;
}
