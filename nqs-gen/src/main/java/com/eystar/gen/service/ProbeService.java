package com.eystar.gen.service;


import com.eystar.gen.entity.TPProbe;

public interface ProbeService {



//    String getProbe(String probeId);


//    CPPinfo findProbeInfoById(String id);

    TPProbe findById(String id);

    void updateProbe(TPProbe tpProbe);

    int insertProbe(TPProbe tpProbe);

//    List<CPPon> getList();

//    JSONObject charts(String probeId, long startTime);
}
