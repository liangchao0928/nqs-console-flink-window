package com.eystar.gen.service.impl;
import com.eystar.gen.entity.CPPon;
import com.eystar.gen.mapper.CPPonMapper;
import com.eystar.gen.service.PPonService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pPonService")
public class PPonServiceImpl implements PPonService {

    @Autowired
    private CPPonMapper cpPonMapper;

    @DataSource(DataSourceType.bigdata)
    public int insert(CPPon cpPon){
        int cph= cpPonMapper.insertSelective(cpPon);
        return cph;
    }

    @DataSource(DataSourceType.bigdata)
    public void insertList(List<CPPon> cpPons) {
        cpPonMapper.insertList(cpPons);
    }
}
