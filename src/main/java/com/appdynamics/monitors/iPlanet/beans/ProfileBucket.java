/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("profile-bucket")
public class ProfileBucket {
    @XStreamAsAttribute()
    private String profile;
    @XStreamAsAttribute()
    private String countCalls;
    @XStreamAsAttribute()
    private String countRequests;
    @XStreamAsAttribute()
    private String ticksDispatch;
    @XStreamAsAttribute()
    private String ticksFunction;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCountCalls() {
        return countCalls;
    }

    public void setCountCalls(String countCalls) {
        this.countCalls = countCalls;
    }

    public String getCountRequests() {
        return countRequests;
    }

    public void setCountRequests(String countRequests) {
        this.countRequests = countRequests;
    }

    public String getTicksDispatch() {
        return ticksDispatch;
    }

    public void setTicksDispatch(String ticksDispatch) {
        this.ticksDispatch = ticksDispatch;
    }

    public String getTicksFunction() {
        return ticksFunction;
    }

    public void setTicksFunction(String ticksFunction) {
        this.ticksFunction = ticksFunction;
    }
}
