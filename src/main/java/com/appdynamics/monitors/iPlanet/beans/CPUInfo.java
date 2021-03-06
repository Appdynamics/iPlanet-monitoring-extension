/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("cpu-info")
public class CPUInfo {
    @XStreamAsAttribute()
    private String cpu;
    @XStreamAsAttribute()
    private String percentIdle;
    @XStreamAsAttribute()
    private String percentUser;
    @XStreamAsAttribute()
    private String percentKernel;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getPercentIdle() {
        return percentIdle;
    }

    public void setPercentIdle(String percentIdle) {
        this.percentIdle = percentIdle;
    }

    public String getPercentUser() {
        return percentUser;
    }

    public void setPercentUser(String percentUser) {
        this.percentUser = percentUser;
    }

    public String getPercentKernel() {
        return percentKernel;
    }

    public void setPercentKernel(String percentKernel) {
        this.percentKernel = percentKernel;
    }
}
