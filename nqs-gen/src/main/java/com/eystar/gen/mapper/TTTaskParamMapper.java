package com.eystar.gen.mapper;


import com.eystar.gen.entity.TTTaskParam;

public interface TTTaskParamMapper {


    int deleteByPrimaryKey(String id);

    int insert(TTTaskParam record);

    int insertSelective(TTTaskParam record);

    TTTaskParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TTTaskParam record);

    int updateByPrimaryKey(TTTaskParam record);
}