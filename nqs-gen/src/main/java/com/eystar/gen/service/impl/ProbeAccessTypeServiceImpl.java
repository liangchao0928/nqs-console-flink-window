package com.eystar.gen.service.impl;


import com.eystar.gen.entity.TPProbeAccessType;
import com.eystar.gen.mapper.TPProbeAccessTypeMapper;
import com.eystar.gen.service.ProbeAccessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("probeAccessTypeService")
public class ProbeAccessTypeServiceImpl implements ProbeAccessTypeService {

    @Autowired
    private TPProbeAccessTypeMapper tpProbeAccessTypeMapper;


    public List<TPProbeAccessType> selectByProbeId(String probeId) {
        List<TPProbeAccessType> lp=tpProbeAccessTypeMapper.selectByProbeId(probeId);
        return lp;
    }

    public TPProbeAccessType findByAccessType( String probeId,  String accessTypeName) {
        TPProbeAccessType p=tpProbeAccessTypeMapper.findByAccessType(probeId,accessTypeName);
        return p;
    }

    public int insert(TPProbeAccessType tpProbeAccessType) {
        int  p=tpProbeAccessTypeMapper.insertSelective(tpProbeAccessType);
        return p;
    }

    public int update(TPProbeAccessType tpProbeAccessType) {
        int  p=tpProbeAccessTypeMapper.updateByPrimaryKeySelective(tpProbeAccessType);
        return p;
    }

    public void deleteAccessTypeByName( String probeId, String accessTypeName) {
        tpProbeAccessTypeMapper.deleteAccessTypeByName(probeId,accessTypeName);
    }
}
