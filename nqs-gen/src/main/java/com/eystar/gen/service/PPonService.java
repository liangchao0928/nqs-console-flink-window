package com.eystar.gen.service;


import com.eystar.gen.entity.CPPon;

import java.util.List;

public interface PPonService {


    int insert(CPPon cpPon);

    void insertList(List<CPPon> cpPons);
}
