package com.eystar.gen.service.impl;

import com.eystar.gen.entity.CPStatus;
import com.eystar.gen.mapper.CPStatusMapper;
import com.eystar.gen.service.PStatusService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("pStatusService")
public class PStatusServiceImpl implements PStatusService {

    @Autowired
    private CPStatusMapper cpStatusMapper;

    @DataSource(DataSourceType.bigdata)
    public int insert(CPStatus cpStatus){
        int cph= cpStatusMapper.insertSelective(cpStatus);
        return cph;
    }
}
