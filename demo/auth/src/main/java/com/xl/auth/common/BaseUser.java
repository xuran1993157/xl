package com.xl.auth.common;

import lombok.Data;

/**
 * @ClassName BaseUser
 * @Description: TODO
 * @Author xr
 * @Date 2019/12/23
 * @Version V1.0
 **/
@Data
public class BaseUser {
    private String username;
    private String password;
    private Integer active;
}
