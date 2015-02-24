package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("web-app-bucket")
public class WebAppBucket {
    @XStreamAsAttribute()
    private String uri;
    @XStreamAsAttribute()
    private String mode;
    @XStreamAsAttribute()
    private String countJsps;
    @XStreamAsAttribute()
    private String countReloadedJsps;
    @XStreamAsAttribute()
    private String countSessions;
    @XStreamAsAttribute()
    private String countActiveSessions;
    @XStreamAsAttribute()
    private String peakActiveSessions;
    @XStreamAsAttribute()
    private String countRejectedSessions;
    @XStreamAsAttribute()
    private String countExpiredSessions;
    @XStreamAsAttribute()
    private String secondsSessionAliveMax;
    @XStreamAsAttribute()
    private String secondsSessionAliveAverage;
    @XStreamImplicit(itemFieldName = "servlet-bucket")
    private List<ServletBucket> servletBuckets;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCountJsps() {
        return countJsps;
    }

    public void setCountJsps(String countJsps) {
        this.countJsps = countJsps;
    }

    public String getCountReloadedJsps() {
        return countReloadedJsps;
    }

    public void setCountReloadedJsps(String countReloadedJsps) {
        this.countReloadedJsps = countReloadedJsps;
    }

    public String getCountSessions() {
        return countSessions;
    }

    public void setCountSessions(String countSessions) {
        this.countSessions = countSessions;
    }

    public String getCountActiveSessions() {
        return countActiveSessions;
    }

    public void setCountActiveSessions(String countActiveSessions) {
        this.countActiveSessions = countActiveSessions;
    }

    public String getPeakActiveSessions() {
        return peakActiveSessions;
    }

    public void setPeakActiveSessions(String peakActiveSessions) {
        this.peakActiveSessions = peakActiveSessions;
    }

    public String getCountRejectedSessions() {
        return countRejectedSessions;
    }

    public void setCountRejectedSessions(String countRejectedSessions) {
        this.countRejectedSessions = countRejectedSessions;
    }

    public String getCountExpiredSessions() {
        return countExpiredSessions;
    }

    public void setCountExpiredSessions(String countExpiredSessions) {
        this.countExpiredSessions = countExpiredSessions;
    }

    public String getSecondsSessionAliveMax() {
        return secondsSessionAliveMax;
    }

    public void setSecondsSessionAliveMax(String secondsSessionAliveMax) {
        this.secondsSessionAliveMax = secondsSessionAliveMax;
    }

    public String getSecondsSessionAliveAverage() {
        return secondsSessionAliveAverage;
    }

    public void setSecondsSessionAliveAverage(String secondsSessionAliveAverage) {
        this.secondsSessionAliveAverage = secondsSessionAliveAverage;
    }

    public List<ServletBucket> getServletBuckets() {
        return servletBuckets;
    }

    public void setServletBuckets(List<ServletBucket> servletBuckets) {
        this.servletBuckets = servletBuckets;
    }
}
