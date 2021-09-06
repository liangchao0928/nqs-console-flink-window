package com.eystar.gen.service.impl;

import com.eystar.gen.entity.TTTaskSrcDest;
import com.eystar.gen.mapper.TTTaskSrcDestMapper;
import com.eystar.gen.service.TaskSrcDesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("taskSrcDesService")
public class TaskSrcDesServiceImpl implements TaskSrcDesService {

    @Autowired
    private TTTaskSrcDestMapper ttTaskSrcDestMapper;

    public TTTaskSrcDest findById(String taskId) {
        return ttTaskSrcDestMapper.findById(taskId);
    }
}
