package com.xl.soa.service.impl;

import com.xl.common.bean.Address;
import com.xl.soa.mapper.AddressMapper;
import com.xl.soa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description:
 * @Author xr
 * @Date 2019/12/24
 * @Version V1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AddressMapper addressMapper;


    @Override
    @Transactional
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }
}
