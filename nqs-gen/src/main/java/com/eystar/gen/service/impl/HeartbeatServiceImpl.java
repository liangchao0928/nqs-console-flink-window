package com.eystar.gen.service.impl;


import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.mapper.CPHeartbeatMapper;
import com.eystar.gen.service.HeartbeatService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("heartbeatService")
public class HeartbeatServiceImpl implements HeartbeatService {

    @Autowired
    private CPHeartbeatMapper cpHeartbeatMapper;

    @DataSource(DataSourceType.bigdata)
    public int insert(CPHeartbeat cpHeartbeat){
        int cph= cpHeartbeatMapper.insertSelective(cpHeartbeat);
        return cph;
    }

    @DataSource(DataSourceType.bigdata)
    public int insertList(List<CPHeartbeat> lh) {
        int cph= cpHeartbeatMapper.insertList(lh);
        return cph;
    }
}
