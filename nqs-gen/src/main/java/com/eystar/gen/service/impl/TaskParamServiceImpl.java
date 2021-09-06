package com.eystar.gen.service.impl;

import com.eystar.gen.entity.TTTaskParam;
import com.eystar.gen.mapper.TTTaskParamMapper;
import com.eystar.gen.service.TaskParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("taskParamService")
public class TaskParamServiceImpl implements TaskParamService {

    @Autowired
    private TTTaskParamMapper ttTaskParamMapper;

    public TTTaskParam findById(String taskParamId) {
        return ttTaskParamMapper.selectByPrimaryKey(taskParamId);
    }
}
