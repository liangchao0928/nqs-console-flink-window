package com.eystar.gen.mapper;

import com.eystar.gen.entity.TPProbeAccessType;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TPProbeAccessTypeMapper {



    int insert(TPProbeAccessType record);

    int insertSelective(TPProbeAccessType record);

    TPProbeAccessType selectByPrimaryKey(String id);

    TPProbeAccessType findByAccessType(@Param("probeId") String probeId, @Param("accessTypeName") String accessTypeName);

    List<TPProbeAccessType> selectByProbeId(String probeId);

    int updateByPrimaryKeySelective(TPProbeAccessType record);

    int updateByPrimaryKey(TPProbeAccessType record);

    void deleteAccessTypeByName(@Param("probeId") String probeId, @Param("accessTypeName") String accessTypeName);
}