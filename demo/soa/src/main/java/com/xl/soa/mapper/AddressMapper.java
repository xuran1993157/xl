package com.xl.soa.mapper;

import com.xl.common.bean.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description:
 * @Author xr
 * @Date 2020/1/8
 * @Version V1.0
 **/
@Repository
public interface AddressMapper {
    List<Address> selectAll();
}
