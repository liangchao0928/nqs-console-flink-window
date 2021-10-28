package com.eystar.gen.service;


import com.eystar.gen.entity.CPHeartbeat;

import java.util.List;

public interface HeartbeatService {


    int insert(CPHeartbeat cpHeartbeat);

    int insertList(List<CPHeartbeat> lh);
}
