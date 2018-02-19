/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("keepalive-bucket")
public class KeepaliveBucket {
    @XStreamAsAttribute()
    private String countConnections;
    @XStreamAsAttribute()
    private String maxConnections;
    @XStreamAsAttribute()
    private String countHits;
    @XStreamAsAttribute()
    private String countFlushes;
    @XStreamAsAttribute()
    private String countRefusals;
    @XStreamAsAttribute()
    private String countTimeouts;
    @XStreamAsAttribute()
    private String secondsTimeout;

    public String getCountConnections() {
        return countConnections;
    }

    public void setCountConnections(String countConnections) {
        this.countConnections = countConnections;
    }

    public String getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(String maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getCountHits() {
        return countHits;
    }

    public void setCountHits(String countHits) {
        this.countHits = countHits;
    }

    public String getCountFlushes() {
        return countFlushes;
    }

    public void setCountFlushes(String countFlushes) {
        this.countFlushes = countFlushes;
    }

    public String getCountRefusals() {
        return countRefusals;
    }

    public void setCountRefusals(String countRefusals) {
        this.countRefusals = countRefusals;
    }

    public String getCountTimeouts() {
        return countTimeouts;
    }

    public void setCountTimeouts(String countTimeouts) {
        this.countTimeouts = countTimeouts;
    }

    public String getSecondsTimeout() {
        return secondsTimeout;
    }

    public void setSecondsTimeout(String secondsTimeout) {
        this.secondsTimeout = secondsTimeout;
    }
}
