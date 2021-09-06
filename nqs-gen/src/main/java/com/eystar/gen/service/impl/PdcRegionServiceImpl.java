package com.eystar.gen.service.impl;

import com.eystar.gen.entity.TPdcRegion;
import com.eystar.gen.mapper.TPdcRegionMapper;
import com.eystar.gen.service.PdcRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pdcRegionService")
public class PdcRegionServiceImpl implements PdcRegionService {

    @Autowired
    private TPdcRegionMapper tPdcRegionMapper;

    public TPdcRegion findByRcode(long rCode) {
        return tPdcRegionMapper.selectByPrimaryKey(rCode);
    }

    public int updateRname(String rNameDb, String rName,int level) {
        return tPdcRegionMapper.updateRname(rNameDb,rName,level);
    }

    public int updateRnameLike(String rNameDb, String rName) {
        return tPdcRegionMapper.updateRnameLike( rNameDb,  rName);
    }



    public List<TPdcRegion> selectByPathNameLike(String pathName) {
        return tPdcRegionMapper.selectByPathNameLike(pathName);
    }
}
