package com.eystar.gen.mapper;


import com.eystar.gen.entity.CPPinfoReal;

import java.util.List;

public interface CPPinfoRealMapper {


    int insert(CPPinfoReal record);

    int insertSelective(CPPinfoReal record);

    void insertList(List<CPPinfoReal> records);
}