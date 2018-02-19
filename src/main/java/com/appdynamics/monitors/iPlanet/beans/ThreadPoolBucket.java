/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("thread-pool-bucket")
public class ThreadPoolBucket {

    @XStreamAsAttribute
    @XStreamAlias("thread-pool")
    private String threadPool;
    @XStreamAsAttribute
    private String countThreadsIdle;
    @XStreamAsAttribute
    private String countThreads;
    @XStreamAsAttribute
    private String maxThreads;
    @XStreamAsAttribute
    private String countQueued;
    @XStreamAsAttribute
    private String peakQueued;
    @XStreamAsAttribute
    private String maxQueued;

    public String getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(String threadPool) {
        this.threadPool = threadPool;
    }

    public String getCountThreadsIdle() {
        return countThreadsIdle;
    }

    public void setCountThreadsIdle(String countThreadsIdle) {
        this.countThreadsIdle = countThreadsIdle;
    }

    public String getCountThreads() {
        return countThreads;
    }

    public void setCountThreads(String countThreads) {
        this.countThreads = countThreads;
    }

    public String getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(String maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getCountQueued() {
        return countQueued;
    }

    public void setCountQueued(String countQueued) {
        this.countQueued = countQueued;
    }

    public String getPeakQueued() {
        return peakQueued;
    }

    public void setPeakQueued(String peakQueued) {
        this.peakQueued = peakQueued;
    }

    public String getMaxQueued() {
        return maxQueued;
    }

    public void setMaxQueued(String maxQueued) {
        this.maxQueued = maxQueued;
    }
}
