package com.eystar.gen.service.impl;


import com.eystar.gen.entity.CPPinfoReal;
import com.eystar.gen.mapper.CPPinfoRealMapper;
import com.eystar.gen.service.PInfoRealService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PInfoRealService")
public class PInfoRealServiceImpl implements PInfoRealService {

    @Autowired
    private CPPinfoRealMapper cpPinfoRealMapper;


    @DataSource(DataSourceType.bigdata)
    public int insert(CPPinfoReal cpPinfoReal) {
        int pi= cpPinfoRealMapper.insert(cpPinfoReal);
        return pi;
    }

    public void insertList(List<CPPinfoReal> record) {
        cpPinfoRealMapper.insertList(record);
    }
}
