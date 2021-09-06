package com.eystar.gen.mapper;



import com.eystar.gen.entity.gwdata.GwData;
import com.eystar.gen.entity.gwdata.GwHttpDetailData;

import java.util.List;

public interface GwHttpDetailMapper {


    int insert(GwHttpDetailData record);

    int insertSelective(GwHttpDetailData record);

    int insertList(List<GwData> lgd);

}