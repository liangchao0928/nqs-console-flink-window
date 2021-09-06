package com.eystar.gen.service;



import com.eystar.gen.entity.TPProbeAccessType;

import java.util.List;

public interface ProbeAccessTypeService {


    List<TPProbeAccessType> selectByProbeId(String probeId);

    TPProbeAccessType findByAccessType(String probeId, String accessTypeName);

    int insert(TPProbeAccessType tpProbeAccessType);

    int update(TPProbeAccessType tpProbeAccessType);

    void deleteAccessTypeByName(String probeId, String accessTypeName);
}
