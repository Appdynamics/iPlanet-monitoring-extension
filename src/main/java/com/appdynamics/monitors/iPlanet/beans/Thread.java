/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("thread")
public class Thread {

    @XStreamAsAttribute
    private String mode;
    @XStreamAsAttribute
    private String timeStarted;
    @XStreamAlias("connection-queue")
    @XStreamAsAttribute
    private String connectionQueue;
    @XStreamAlias("request-bucket")
    private RequestBucket requestBucket;
    @XStreamImplicit(itemFieldName = "profile-bucket")
    private List<ProfileBucket> profileBuckets;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(String timeStarted) {
        this.timeStarted = timeStarted;
    }

    public String getConnectionQueue() {
        return connectionQueue;
    }

    public void setConnectionQueue(String connectionQueue) {
        this.connectionQueue = connectionQueue;
    }

    public RequestBucket getRequestBucket() {
        return requestBucket;
    }

    public void setRequestBucket(RequestBucket requestBucket) {
        this.requestBucket = requestBucket;
    }

    public List<ProfileBucket> getProfileBuckets() {
        return profileBuckets;
    }

    public void setProfileBuckets(List<ProfileBucket> profileBuckets) {
        this.profileBuckets = profileBuckets;
    }
}
