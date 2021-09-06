package com.eystar.gen.service.impl;


import com.eystar.gen.entity.gwdata.*;
import com.eystar.gen.mapper.GwDnsMapper;
import com.eystar.gen.mapper.GwFlashMapper;
import com.eystar.gen.mapper.GwHttpMapper;
import com.eystar.gen.mapper.GwPingMapper;
import com.eystar.gen.service.GwDataService;
import com.eystar.gen.source.DataSource;
import com.eystar.gen.source.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gwDataService")
public class GwDataServiceImpl implements GwDataService {


    @Autowired
    private GwDnsMapper gwDnsMapper;

    @Autowired
    private GwFlashMapper gwFlashMapper;

    @Autowired
    private GwHttpMapper gwHttpMapper;

    @Autowired
    private GwPingMapper gwPingMapper;

    @DataSource(DataSourceType.bigdata)
    public int insertData(GwData gwData) {
        int i=0;
        if(gwData instanceof GwPingData){
            System.out.println("GwPingData入库");
            i=gwPingMapper.insertSelective((GwPingData)gwData);
        }
        if(gwData instanceof GwDnsData){
            System.out.println("GwDnsData入库");
            i=gwDnsMapper.insertSelective((GwDnsData)gwData);
        }
        if(gwData instanceof GwFlashData){
            System.out.println("Flash入库");
            i=gwFlashMapper.insertSelective((GwFlashData)gwData);
        }
        if(gwData instanceof GwHttpData){
            System.out.println("GwHttpData入库");
            i=gwHttpMapper.insertSelective((GwHttpData)gwData);
        }
        return i;
    }
}
