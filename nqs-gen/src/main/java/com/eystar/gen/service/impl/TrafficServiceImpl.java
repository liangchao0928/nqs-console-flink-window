package com.eystar.gen.service.impl;


import com.eystar.gen.entity.CPTraffic;
import com.eystar.gen.mapper.CPTrafficMapper;
import com.eystar.gen.service.TrafficService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trafficService")
public class TrafficServiceImpl implements TrafficService {

    @Autowired
    private CPTrafficMapper cpTrafficMapper;

    @DataSource(DataSourceType.bigdata)
    public int insert(CPTraffic cpTraffic){
        int cph= cpTrafficMapper.insertSelective(cpTraffic);
        return cph;
    }

    @DataSource(DataSourceType.bigdata)
    public void insertList(List<CPTraffic> cpTraffics) {
        cpTrafficMapper.insertList(cpTraffics);
    }
}
