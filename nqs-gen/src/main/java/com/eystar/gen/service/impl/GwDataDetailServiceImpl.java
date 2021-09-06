package com.eystar.gen.service.impl;

import com.eystar.gen.entity.gwdata.*;
import com.eystar.gen.mapper.*;
import com.eystar.gen.service.GwDataDetailService;

import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gwDataDetailService")
public class GwDataDetailServiceImpl implements GwDataDetailService {


    @Autowired
    private GwHttpDetailMapper gwHttpDetailMapper;


    @DataSource(DataSourceType.bigdata)
    public int insertDataList(List<GwData> gwDataList) {
        int i=0;
        if(gwDataList.get(0) instanceof GwHttpDetailData){
            gwHttpDetailMapper.insertList(gwDataList);
        }
        return i;
    }
}
