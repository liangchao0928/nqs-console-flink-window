package com.eystar.gen.mapper;


import com.eystar.gen.entity.CPStatus;

import java.util.List;

public interface CPStatusMapper {


    int insert(CPStatus record);

    int insertSelective(CPStatus record);

    void insertList(List<CPStatus> cpStatuss);

}