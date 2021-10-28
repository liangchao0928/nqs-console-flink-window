package com.eystar.gen.service;


import com.eystar.gen.entity.CPPinfo;

import java.util.List;

public interface PInfoService {

      int  insert(CPPinfo cpPinfo);

      void insertList(List<CPPinfo> records);
}
