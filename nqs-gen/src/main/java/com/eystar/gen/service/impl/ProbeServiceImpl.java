package com.eystar.gen.service.impl;

import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.mapper.TPProbeMapper;
import com.eystar.gen.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("probeService")
public class ProbeServiceImpl implements ProbeService {

    @Autowired
    private TPProbeMapper tPProbeMapper;

    public TPProbe findById(String id) {
        TPProbe probe=tPProbeMapper.selectByPrimaryKey(id);
        return probe;
    }

    public void updateProbe(TPProbe tpProbe) {
        tPProbeMapper.updateByPrimaryKeySelective(tpProbe);
    }

    public int insertProbe(TPProbe tpProbe) {
        int count =0;
        if(tPProbeMapper.selectByPrimaryKey(tpProbe.getId())==null) {
            count = tPProbeMapper.insert(tpProbe);
        }
        return count;
    }

}
