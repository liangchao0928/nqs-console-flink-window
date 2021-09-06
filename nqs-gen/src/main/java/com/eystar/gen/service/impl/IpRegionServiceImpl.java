package com.eystar.gen.service.impl;
import com.eystar.gen.entity.TMIpRegion;
import com.eystar.gen.mapper.TMIpRegionMapper;
import com.eystar.gen.service.IpRegionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ipRegionService")
public class IpRegionServiceImpl implements IpRegionService {


    @Autowired
    private TMIpRegionMapper tmIpRegionMapper;



    public TMIpRegion findByIP(String ip) {
        return tmIpRegionMapper.selectByIpFirst(ip);
    }
}