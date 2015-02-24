package com.appdynamics.monitors.iPlanet.beans;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("connection-queue-bucket")
public class ConnectionQueueBucket {
    @XStreamAsAttribute()
    @XStreamAlias("connection-queue")
    private String connectionQueue;
    @XStreamAsAttribute()
    private String countTotalConnections;
    @XStreamAsAttribute()
    private String countQueued;
    @XStreamAsAttribute()
    private String peakQueued;
    @XStreamAsAttribute()
    private String maxQueued;
    @XStreamAsAttribute()
    private String countOverflows;
    @XStreamAsAttribute()
    private String countTotalQueued;
    @XStreamAsAttribute()
    private String ticksTotalQueued;
    @XStreamAsAttribute()
    private String countQueued1MinuteAverage;
    @XStreamAsAttribute()
    private String countQueued5MinuteAverage;
    @XStreamAsAttribute()
    private String countQueued15MinuteAverage;


    public String getConnectionQueue() {
        return connectionQueue;
    }

    public void setConnectionQueue(String connectionQueue) {
        this.connectionQueue = connectionQueue;
    }

    public String getCountTotalConnections() {
        return countTotalConnections;
    }

    public void setCountTotalConnections(String countTotalConnections) {
        this.countTotalConnections = countTotalConnections;
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

    public String getCountOverflows() {
        return countOverflows;
    }

    public void setCountOverflows(String countOverflows) {
        this.countOverflows = countOverflows;
    }

    public String getCountTotalQueued() {
        return countTotalQueued;
    }

    public void setCountTotalQueued(String countTotalQueued) {
        this.countTotalQueued = countTotalQueued;
    }

    public String getTicksTotalQueued() {
        return ticksTotalQueued;
    }

    public void setTicksTotalQueued(String ticksTotalQueued) {
        this.ticksTotalQueued = ticksTotalQueued;
    }

    public String getCountQueued1MinuteAverage() {
        return countQueued1MinuteAverage;
    }

    public void setCountQueued1MinuteAverage(String countQueued1MinuteAverage) {
        this.countQueued1MinuteAverage = countQueued1MinuteAverage;
    }

    public String getCountQueued5MinuteAverage() {
        return countQueued5MinuteAverage;
    }

    public void setCountQueued5MinuteAverage(String countQueued5MinuteAverage) {
        this.countQueued5MinuteAverage = countQueued5MinuteAverage;
    }

    public String getCountQueued15MinuteAverage() {
        return countQueued15MinuteAverage;
    }

    public void setCountQueued15MinuteAverage(String countQueued15MinuteAverage) {
        this.countQueued15MinuteAverage = countQueued15MinuteAverage;
    }
}
