package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("server")
public class Server {
    @XStreamAsAttribute()
    private String id;
    @XStreamAsAttribute()
    private String secondsRunning;
    @XStreamAsAttribute()
    private String ticksPerSecond;
    @XStreamAsAttribute()
    private String maxProcs;
    @XStreamAsAttribute()
    private String maxThreads;
    @XStreamAsAttribute()
    private String flagProfilingEnabled;
    @XStreamAsAttribute()
    private String load1MinuteAverage;
    @XStreamAsAttribute()
    private String load5MinuteAverage;
    @XStreamAsAttribute()
    private String load15MinuteAverage;
    @XStreamAsAttribute()
    private String rateBytesTransmitted;
    @XStreamAsAttribute()
    private String rateBytesReceived;
    @XStreamAlias("thread-pool")
    private ThreadPool threadPool;
    @XStreamImplicit(itemFieldName = "profile")
    private List<Profile> profiles;
    @XStreamAlias("process")
    private Process process;
    @XStreamAlias("virtual-server")
    private VirtualServer virtualServer;
    @XStreamImplicit(itemFieldName = "cpu-info")
    private List<CPUInfo> cpuInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecondsRunning() {
        return secondsRunning;
    }

    public void setSecondsRunning(String secondsRunning) {
        this.secondsRunning = secondsRunning;
    }

    public String getTicksPerSecond() {
        return ticksPerSecond;
    }

    public void setTicksPerSecond(String ticksPerSecond) {
        this.ticksPerSecond = ticksPerSecond;
    }

    public String getMaxProcs() {
        return maxProcs;
    }

    public void setMaxProcs(String maxProcs) {
        this.maxProcs = maxProcs;
    }

    public String getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(String maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getFlagProfilingEnabled() {
        return flagProfilingEnabled;
    }

    public void setFlagProfilingEnabled(String flagProfilingEnabled) {
        this.flagProfilingEnabled = flagProfilingEnabled;
    }

    public String getLoad1MinuteAverage() {
        return load1MinuteAverage;
    }

    public void setLoad1MinuteAverage(String load1MinuteAverage) {
        this.load1MinuteAverage = load1MinuteAverage;
    }

    public String getLoad5MinuteAverage() {
        return load5MinuteAverage;
    }

    public void setLoad5MinuteAverage(String load5MinuteAverage) {
        this.load5MinuteAverage = load5MinuteAverage;
    }

    public String getLoad15MinuteAverage() {
        return load15MinuteAverage;
    }

    public void setLoad15MinuteAverage(String load15MinuteAverage) {
        this.load15MinuteAverage = load15MinuteAverage;
    }

    public String getRateBytesTransmitted() {
        return rateBytesTransmitted;
    }

    public void setRateBytesTransmitted(String rateBytesTransmitted) {
        this.rateBytesTransmitted = rateBytesTransmitted;
    }

    public String getRateBytesReceived() {
        return rateBytesReceived;
    }

    public void setRateBytesReceived(String rateBytesReceived) {
        this.rateBytesReceived = rateBytesReceived;
    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public VirtualServer getVirtualServer() {
        return virtualServer;
    }

    public void setVirtualServer(VirtualServer virtualServer) {
        this.virtualServer = virtualServer;
    }

    public List<CPUInfo> getCpuInfos() {
        return cpuInfos;
    }

    public void setCpuInfos(List<CPUInfo> cpuInfos) {
        this.cpuInfos = cpuInfos;
    }
}
