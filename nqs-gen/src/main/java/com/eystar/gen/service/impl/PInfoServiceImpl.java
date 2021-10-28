package com.eystar.gen.service.impl;


import com.eystar.gen.entity.CPPinfo;
import com.eystar.gen.mapper.CPPinfoMapper;
import com.eystar.gen.service.PInfoService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pInfoService")
public class PInfoServiceImpl implements PInfoService {

    @Autowired
    private CPPinfoMapper cpPinfoMapper;


    @DataSource(DataSourceType.bigdata)
    public int insert(CPPinfo cpPinfo) {
        int pi= cpPinfoMapper.insert(cpPinfo);
        return pi;
    }

    @DataSource(DataSourceType.bigdata)
    public void insertList(List<CPPinfo> records){
        cpPinfoMapper.insertList(records);
    }
}
