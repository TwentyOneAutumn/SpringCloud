package com.server.join.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.server.join.domain.Address;
import com.server.join.mapper.AddressMapper;
import com.server.join.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceImpl extends MPJBaseServiceImpl<AddressMapper, Address> implements IAddressService {
}
