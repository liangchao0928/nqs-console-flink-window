package com.eystar.gen.service;


import com.eystar.gen.entity.CPPinfoReal;

import java.util.List;

public interface PInfoRealService {

      int  insert(CPPinfoReal cpPinfoReal);

      void  insertList(List<CPPinfoReal> record);
}
