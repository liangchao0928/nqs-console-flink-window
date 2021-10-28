package com.eystar.gen.service;


import com.eystar.gen.entity.CPStatus;

import java.util.List;

public interface PStatusService {


    int insert(CPStatus cpStatus);

    void insertList(List<CPStatus> cpStatuss);
}
