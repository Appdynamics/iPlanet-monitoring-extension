package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("dns-bucket")
public class DNSBucket {
    @XStreamAsAttribute()
    private String flagCacheEnabled;
    @XStreamAsAttribute()
    private String countCacheEntries;
    @XStreamAsAttribute()
    private String maxCacheEntries;
    @XStreamAsAttribute()
    private String countCacheHits;
    @XStreamAsAttribute()
    private String countCacheMisses;
    @XStreamAsAttribute()
    private String flagAsyncEnabled;
    @XStreamAsAttribute()
    private String countAsyncNameLookups;
    @XStreamAsAttribute()
    private String countAsyncAddrLookups;
    @XStreamAsAttribute()
    private String countAsyncLookupsInProgress;

    public String getFlagCacheEnabled() {
        return flagCacheEnabled;
    }

    public void setFlagCacheEnabled(String flagCacheEnabled) {
        this.flagCacheEnabled = flagCacheEnabled;
    }

    public String getCountCacheEntries() {
        return countCacheEntries;
    }

    public void setCountCacheEntries(String countCacheEntries) {
        this.countCacheEntries = countCacheEntries;
    }

    public String getMaxCacheEntries() {
        return maxCacheEntries;
    }

    public void setMaxCacheEntries(String maxCacheEntries) {
        this.maxCacheEntries = maxCacheEntries;
    }

    public String getCountCacheHits() {
        return countCacheHits;
    }

    public void setCountCacheHits(String countCacheHits) {
        this.countCacheHits = countCacheHits;
    }

    public String getCountCacheMisses() {
        return countCacheMisses;
    }

    public void setCountCacheMisses(String countCacheMisses) {
        this.countCacheMisses = countCacheMisses;
    }

    public String getFlagAsyncEnabled() {
        return flagAsyncEnabled;
    }

    public void setFlagAsyncEnabled(String flagAsyncEnabled) {
        this.flagAsyncEnabled = flagAsyncEnabled;
    }

    public String getCountAsyncNameLookups() {
        return countAsyncNameLookups;
    }

    public void setCountAsyncNameLookups(String countAsyncNameLookups) {
        this.countAsyncNameLookups = countAsyncNameLookups;
    }

    public String getCountAsyncAddrLookups() {
        return countAsyncAddrLookups;
    }

    public void setCountAsyncAddrLookups(String countAsyncAddrLookups) {
        this.countAsyncAddrLookups = countAsyncAddrLookups;
    }

    public String getCountAsyncLookupsInProgress() {
        return countAsyncLookupsInProgress;
    }

    public void setCountAsyncLookupsInProgress(String countAsyncLookupsInProgress) {
        this.countAsyncLookupsInProgress = countAsyncLookupsInProgress;
    }
}
