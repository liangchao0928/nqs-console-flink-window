package com.eystar.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TPdcRegion implements Serializable {

    @JSONField(name="r_code")
    @ApiModelProperty(value = "行政区划代码")
    private Long rCode;

    @JSONField(name="r_name")
    @ApiModelProperty(value = "行政区划名称")
    private String rName;

    @JSONField(name="r_level")
    @ApiModelProperty(value = "行政区划等级")
    private Short rLevel;

    @JSONField(name="r_parent_code")
    @ApiModelProperty(value = "上级行政区划")
    private Long rParentCode;

    @JSONField(name="r_tag")
    @ApiModelProperty(value = "地域标记")
    private String rTag;

    @JSONField(name="region_path")
    @ApiModelProperty(value = "code的path路径")
    private String regionPath;

    @JSONField(name="region_name_path")
    @ApiModelProperty(value = "name的path路径")
    private String regionNamePath;


    private static final long serialVersionUID = 1L;

    public Long getrCode() {
        return rCode;
    }

    public void setrCode(Long rCode) {
        this.rCode = rCode;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public Short getrLevel() {
        return rLevel;
    }

    public void setrLevel(Short rLevel) {
        this.rLevel = rLevel;
    }

    public Long getrParentCode() {
        return rParentCode;
    }

    public void setrParentCode(Long rParentCode) {
        this.rParentCode = rParentCode;
    }

    public String getrTag() {
        return rTag;
    }

    public void setrTag(String rTag) {
        this.rTag = rTag;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getRegionNamePath() {
        return regionNamePath;
    }

    public void setRegionNamePath(String regionNamePath) {
        this.regionNamePath = regionNamePath;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rCode=").append(rCode);
        sb.append(", rName=").append(rName);
        sb.append(", rLevel=").append(rLevel);
        sb.append(", rParentCode=").append(rParentCode);
        sb.append(", rTag=").append(rTag);
        sb.append(", regionPath=").append(regionPath);
        sb.append(", regionNamePath=").append(regionNamePath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}