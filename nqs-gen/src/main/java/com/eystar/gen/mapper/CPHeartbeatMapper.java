package com.eystar.gen.mapper;


import com.eystar.gen.entity.CPHeartbeat;

import java.util.List;

public interface CPHeartbeatMapper {

    int insert(CPHeartbeat record);

    int insertSelective(CPHeartbeat record);

    int insertList(List<CPHeartbeat> lh);
}