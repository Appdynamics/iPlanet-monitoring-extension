/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("servlet-bucket")
public class ServletBucket {

    @XStreamAsAttribute()
    private String name;
    @XStreamAsAttribute()
    private String countRequests;
    @XStreamAsAttribute()
    private String countErrors;
    @XStreamAsAttribute()
    private String millisecondsProcessing;
    @XStreamAsAttribute()
    private String millisecondsPeakProcessing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountRequests() {
        return countRequests;
    }

    public void setCountRequests(String countRequests) {
        this.countRequests = countRequests;
    }

    public String getCountErrors() {
        return countErrors;
    }

    public void setCountErrors(String countErrors) {
        this.countErrors = countErrors;
    }

    public String getMillisecondsProcessing() {
        return millisecondsProcessing;
    }

    public void setMillisecondsProcessing(String millisecondsProcessing) {
        this.millisecondsProcessing = millisecondsProcessing;
    }

    public String getMillisecondsPeakProcessing() {
        return millisecondsPeakProcessing;
    }

    public void setMillisecondsPeakProcessing(String millisecondsPeakProcessing) {
        this.millisecondsPeakProcessing = millisecondsPeakProcessing;
    }
}
