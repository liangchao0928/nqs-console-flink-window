package com.eystar.gen.mapper;


import com.eystar.gen.entity.CPPon;

import java.util.List;

public interface CPPonMapper {


    int insert(CPPon record);

    int insertSelective(CPPon record);

    void insertList(List<CPPon> cpPons);



}