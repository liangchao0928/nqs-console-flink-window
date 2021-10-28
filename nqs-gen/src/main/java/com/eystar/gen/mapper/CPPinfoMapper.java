package com.eystar.gen.mapper;


import com.eystar.gen.entity.CPPinfo;

import java.util.List;

public interface CPPinfoMapper {


    int insert(CPPinfo record);

    int insertSelective(CPPinfo record);


    void insertList(List<CPPinfo> records);


}