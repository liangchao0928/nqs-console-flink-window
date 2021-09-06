package com.eystar.gen.mapper;


import com.eystar.gen.entity.TMIpRegion;

public interface TMIpRegionMapper {
    int deleteByPrimaryKey(String id);

    int insert(TMIpRegion record);

    int insertSelective(TMIpRegion record);

    TMIpRegion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TMIpRegion record);

    int updateByPrimaryKey(TMIpRegion record);

    TMIpRegion selectByIpFirst(String ip);


}