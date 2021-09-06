package com.eystar.gen.mapper;


import com.eystar.gen.entity.TTTaskSrcDest;

public interface TTTaskSrcDestMapper {


    TTTaskSrcDest findById(String taskId);


    int insert(TTTaskSrcDest record);

    int insertSelective(TTTaskSrcDest record);



}