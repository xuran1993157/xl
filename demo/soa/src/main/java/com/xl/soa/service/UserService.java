package com.xl.soa.service;

import com.xl.common.bean.Address;

import java.util.List;

/**
 * @ClassName UserService
 * @Description:
 * @Author xr
 * @Date 2019/12/24
 * @Version V1.0
 **/
public interface UserService {

    List<Address> findAll();
}
