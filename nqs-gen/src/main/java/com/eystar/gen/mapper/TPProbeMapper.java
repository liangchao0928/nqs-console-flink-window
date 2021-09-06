package com.eystar.gen.mapper;


import com.eystar.gen.entity.TPProbe;

public interface TPProbeMapper {
    int deleteByPrimaryKey(String id);

    int insert(TPProbe record);

    int insertSelective(TPProbe record);

    TPProbe selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TPProbe record);

    int updateByPrimaryKey(TPProbe record);

}