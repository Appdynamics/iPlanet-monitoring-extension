/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("jvm")
public class JVM {

    @XStreamAsAttribute()
    private String countClassesLoaded;
    @XStreamAsAttribute()
    private String countTotalClassesLoaded;
    @XStreamAsAttribute()
    private String countTotalClassesUnloaded;
    @XStreamAsAttribute()
    private String sizeHeap;
    @XStreamAsAttribute()
    private String peakThreads;
    @XStreamAsAttribute()
    private String countTotalThreadsStarted;
    @XStreamAsAttribute()
    private String countThreads;
    @XStreamAsAttribute()
    private String countGarbageCollections;
    @XStreamAsAttribute()
    private String millisecondsGarbageCollection;

    public String getCountClassesLoaded() {
        return countClassesLoaded;
    }

    public void setCountClassesLoaded(String countClassesLoaded) {
        this.countClassesLoaded = countClassesLoaded;
    }

    public String getCountTotalClassesLoaded() {
        return countTotalClassesLoaded;
    }

    public void setCountTotalClassesLoaded(String countTotalClassesLoaded) {
        this.countTotalClassesLoaded = countTotalClassesLoaded;
    }

    public String getCountTotalClassesUnloaded() {
        return countTotalClassesUnloaded;
    }

    public void setCountTotalClassesUnloaded(String countTotalClassesUnloaded) {
        this.countTotalClassesUnloaded = countTotalClassesUnloaded;
    }

    public String getSizeHeap() {
        return sizeHeap;
    }

    public void setSizeHeap(String sizeHeap) {
        this.sizeHeap = sizeHeap;
    }

    public String getPeakThreads() {
        return peakThreads;
    }

    public void setPeakThreads(String peakThreads) {
        this.peakThreads = peakThreads;
    }

    public String getCountTotalThreadsStarted() {
        return countTotalThreadsStarted;
    }

    public void setCountTotalThreadsStarted(String countTotalThreadsStarted) {
        this.countTotalThreadsStarted = countTotalThreadsStarted;
    }

    public String getCountThreads() {
        return countThreads;
    }

    public void setCountThreads(String countThreads) {
        this.countThreads = countThreads;
    }

    public String getCountGarbageCollections() {
        return countGarbageCollections;
    }

    public void setCountGarbageCollections(String countGarbageCollections) {
        this.countGarbageCollections = countGarbageCollections;
    }

    public String getMillisecondsGarbageCollection() {
        return millisecondsGarbageCollection;
    }

    public void setMillisecondsGarbageCollection(String millisecondsGarbageCollection) {
        this.millisecondsGarbageCollection = millisecondsGarbageCollection;
    }
}
