package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("process")
public class Process {
    @XStreamAsAttribute()
    private String mode;
    @XStreamAsAttribute()
    private String timeStarted;
    @XStreamAsAttribute()
    private String countConfigurations;
    @XStreamAlias("connection-queue-bucket")
    private ConnectionQueueBucket connectionQueueBucket;
    @XStreamAlias("thread-pool-bucket")
    private ThreadPoolBucket threadPoolBucket;
    @XStreamAlias("dns-bucket")
    private DNSBucket dnsBucket;
    @XStreamAlias("keepalive-bucket")
    private KeepaliveBucket keepaliveBucket;
    @XStreamAlias("cache-bucket")
    private CacheBucket cacheBucket;
    @XStreamImplicit(itemFieldName = "thread")
    private List<Thread> threads;
    @XStreamAlias("jvm")
    private JVM jvm;

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

    public String getCountConfigurations() {
        return countConfigurations;
    }

    public void setCountConfigurations(String countConfigurations) {
        this.countConfigurations = countConfigurations;
    }

    public ConnectionQueueBucket getConnectionQueueBucket() {
        return connectionQueueBucket;
    }

    public void setConnectionQueueBucket(ConnectionQueueBucket connectionQueueBucket) {
        this.connectionQueueBucket = connectionQueueBucket;
    }

    public ThreadPoolBucket getThreadPoolBucket() {
        return threadPoolBucket;
    }

    public void setThreadPoolBucket(ThreadPoolBucket threadPoolBucket) {
        this.threadPoolBucket = threadPoolBucket;
    }

    public DNSBucket getDnsBucket() {
        return dnsBucket;
    }

    public void setDnsBucket(DNSBucket dnsBucket) {
        this.dnsBucket = dnsBucket;
    }

    public KeepaliveBucket getKeepaliveBucket() {
        return keepaliveBucket;
    }

    public void setKeepaliveBucket(KeepaliveBucket keepaliveBucket) {
        this.keepaliveBucket = keepaliveBucket;
    }

    public CacheBucket getCacheBucket() {
        return cacheBucket;
    }

    public void setCacheBucket(CacheBucket cacheBucket) {
        this.cacheBucket = cacheBucket;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public JVM getJvm() {
        return jvm;
    }

    public void setJvm(JVM jvm) {
        this.jvm = jvm;
    }
}
