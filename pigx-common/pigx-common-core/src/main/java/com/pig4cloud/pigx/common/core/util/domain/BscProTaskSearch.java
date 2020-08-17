package com.pig4cloud.pigx.common.core.util.domain;

import java.io.Serializable;

/**
 * 代办查询
 */
public class BscProTaskSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usercode;
    private String businessid;
    private String instid;
    private String type;
    private Integer status;
    private String nameDesLike;



//    like查询操作
    private String userLike;
    private String cuserLike;
    private String proKeyLike;
    private String businessidLike;
    private String proInstIdLike;


    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNameDesLike() {
        return nameDesLike;
    }

    public void setNameDesLike(String nameDesLike) {
        this.nameDesLike = nameDesLike;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUserLike() {
        return userLike;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

    public String getCuserLike() {
        return cuserLike;
    }

    public void setCuserLike(String cuserLike) {
        this.cuserLike = cuserLike;
    }

    public String getProKeyLike() {
        return proKeyLike;
    }

    public void setProKeyLike(String proKeyLike) {
        this.proKeyLike = proKeyLike;
    }

    public String getBusinessidLike() {
        return businessidLike;
    }

    public void setBusinessidLike(String businessidLike) {
        this.businessidLike = businessidLike;
    }

    public String getProInstIdLike() {
        return proInstIdLike;
    }

    public void setProInstIdLike(String proInstIdLike) {
        this.proInstIdLike = proInstIdLike;
    }
}
