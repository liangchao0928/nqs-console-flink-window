package com.eystar.gen.service;



import com.eystar.gen.entity.CPTraffic;

import java.util.List;

public interface TrafficService {


    int insert(CPTraffic cpTraffic);

    void insertList(List<CPTraffic> cpTraffics);
}
