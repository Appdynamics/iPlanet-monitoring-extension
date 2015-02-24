package com.appdynamics.monitors.iPlanet.beans;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("cache-bucket")
public class CacheBucket {
    @XStreamAsAttribute()
    private String flagEnabled;
    @XStreamAsAttribute()
    private String secondsMaxAge;
    @XStreamAsAttribute()
    private String countEntries;
    @XStreamAsAttribute()
    private String maxEntries;
    @XStreamAsAttribute()
    private String countOpenEntries;
    @XStreamAsAttribute()
    private String maxOpenEntries;
    @XStreamAsAttribute()
    private String sizeHeapCache;
    @XStreamAsAttribute()
    private String maxHeapCacheSize;
    @XStreamAsAttribute()
    private String sizeMmapCache;
    @XStreamAsAttribute()
    private String maxMmapCacheSize;
    @XStreamAsAttribute()
    private String countHits;
    @XStreamAsAttribute()
    private String countMisses;
    @XStreamAsAttribute()
    private String countInfoHits;
    @XStreamAsAttribute()
    private String countInfoMisses;
    @XStreamAsAttribute()
    private String countContentHits;
    @XStreamAsAttribute()
    private String countContentMisses;
    @XStreamAsAttribute()
    private String countAcceleratorEntries;
    @XStreamAsAttribute()
    private String countAcceleratableRequests;
    @XStreamAsAttribute()
    private String countUnacceleratableRequests;
    @XStreamAsAttribute()
    private String countAcceleratableResponses;
    @XStreamAsAttribute()
    private String countUnacceleratableResponses;
    @XStreamAsAttribute()
    private String countAcceleratorHits;
    @XStreamAsAttribute()
    private String countAcceleratorMisses;

    public String getFlagEnabled() {
        return flagEnabled;
    }

    public void setFlagEnabled(String flagEnabled) {
        this.flagEnabled = flagEnabled;
    }

    public String getSecondsMaxAge() {
        return secondsMaxAge;
    }

    public void setSecondsMaxAge(String secondsMaxAge) {
        this.secondsMaxAge = secondsMaxAge;
    }

    public String getCountEntries() {
        return countEntries;
    }

    public void setCountEntries(String countEntries) {
        this.countEntries = countEntries;
    }

    public String getMaxEntries() {
        return maxEntries;
    }

    public void setMaxEntries(String maxEntries) {
        this.maxEntries = maxEntries;
    }

    public String getCountOpenEntries() {
        return countOpenEntries;
    }

    public void setCountOpenEntries(String countOpenEntries) {
        this.countOpenEntries = countOpenEntries;
    }

    public String getMaxOpenEntries() {
        return maxOpenEntries;
    }

    public void setMaxOpenEntries(String maxOpenEntries) {
        this.maxOpenEntries = maxOpenEntries;
    }

    public String getSizeHeapCache() {
        return sizeHeapCache;
    }

    public void setSizeHeapCache(String sizeHeapCache) {
        this.sizeHeapCache = sizeHeapCache;
    }

    public String getMaxHeapCacheSize() {
        return maxHeapCacheSize;
    }

    public void setMaxHeapCacheSize(String maxHeapCacheSize) {
        this.maxHeapCacheSize = maxHeapCacheSize;
    }

    public String getSizeMmapCache() {
        return sizeMmapCache;
    }

    public void setSizeMmapCache(String sizeMmapCache) {
        this.sizeMmapCache = sizeMmapCache;
    }

    public String getMaxMmapCacheSize() {
        return maxMmapCacheSize;
    }

    public void setMaxMmapCacheSize(String maxMmapCacheSize) {
        this.maxMmapCacheSize = maxMmapCacheSize;
    }

    public String getCountHits() {
        return countHits;
    }

    public void setCountHits(String countHits) {
        this.countHits = countHits;
    }

    public String getCountMisses() {
        return countMisses;
    }

    public void setCountMisses(String countMisses) {
        this.countMisses = countMisses;
    }

    public String getCountInfoHits() {
        return countInfoHits;
    }

    public void setCountInfoHits(String countInfoHits) {
        this.countInfoHits = countInfoHits;
    }

    public String getCountInfoMisses() {
        return countInfoMisses;
    }

    public void setCountInfoMisses(String countInfoMisses) {
        this.countInfoMisses = countInfoMisses;
    }

    public String getCountContentHits() {
        return countContentHits;
    }

    public void setCountContentHits(String countContentHits) {
        this.countContentHits = countContentHits;
    }

    public String getCountContentMisses() {
        return countContentMisses;
    }

    public void setCountContentMisses(String countContentMisses) {
        this.countContentMisses = countContentMisses;
    }

    public String getCountAcceleratorEntries() {
        return countAcceleratorEntries;
    }

    public void setCountAcceleratorEntries(String countAcceleratorEntries) {
        this.countAcceleratorEntries = countAcceleratorEntries;
    }

    public String getCountAcceleratableRequests() {
        return countAcceleratableRequests;
    }

    public void setCountAcceleratableRequests(String countAcceleratableRequests) {
        this.countAcceleratableRequests = countAcceleratableRequests;
    }

    public String getCountUnacceleratableRequests() {
        return countUnacceleratableRequests;
    }

    public void setCountUnacceleratableRequests(String countUnacceleratableRequests) {
        this.countUnacceleratableRequests = countUnacceleratableRequests;
    }

    public String getCountAcceleratableResponses() {
        return countAcceleratableResponses;
    }

    public void setCountAcceleratableResponses(String countAcceleratableResponses) {
        this.countAcceleratableResponses = countAcceleratableResponses;
    }

    public String getCountUnacceleratableResponses() {
        return countUnacceleratableResponses;
    }

    public void setCountUnacceleratableResponses(String countUnacceleratableResponses) {
        this.countUnacceleratableResponses = countUnacceleratableResponses;
    }

    public String getCountAcceleratorHits() {
        return countAcceleratorHits;
    }

    public void setCountAcceleratorHits(String countAcceleratorHits) {
        this.countAcceleratorHits = countAcceleratorHits;
    }

    public String getCountAcceleratorMisses() {
        return countAcceleratorMisses;
    }

    public void setCountAcceleratorMisses(String countAcceleratorMisses) {
        this.countAcceleratorMisses = countAcceleratorMisses;
    }
}
