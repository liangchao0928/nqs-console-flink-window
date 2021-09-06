package com.eystar.gen.mapper;


import com.eystar.gen.entity.CPTraffic;

import java.util.List;

public interface CPTrafficMapper {


    int insert(CPTraffic record);

    int insertSelective(CPTraffic record);

    void insertList(List<CPTraffic> cpTraffics);

}