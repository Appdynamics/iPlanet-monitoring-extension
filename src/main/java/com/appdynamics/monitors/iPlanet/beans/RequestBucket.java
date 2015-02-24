package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("request-bucket")
public class RequestBucket {

    @XStreamAsAttribute()
    private String method;
    @XStreamAsAttribute()
    private String uri;
    @XStreamAsAttribute()
    private String countRequests;
    @XStreamAsAttribute()
    private String countBytesReceived;
    @XStreamAsAttribute()
    private String countBytesTransmitted;
    @XStreamAsAttribute()
    private String rateBytesTransmitted;
    @XStreamAsAttribute()
    private String maxByteTransmissionRate;
    @XStreamAsAttribute()
    private String countOpenConnections;
    @XStreamAsAttribute()
    private String maxOpenConnections;
    @XStreamAsAttribute()
    private String count2xx;
    @XStreamAsAttribute()
    private String count3xx;
    @XStreamAsAttribute()
    private String count4xx;
    @XStreamAsAttribute()
    private String count5xx;
    @XStreamAsAttribute()
    private String countOther;
    @XStreamAsAttribute()
    private String count200;
    @XStreamAsAttribute()
    private String count302;
    @XStreamAsAttribute()
    private String count304;
    @XStreamAsAttribute()
    private String count400;
    @XStreamAsAttribute()
    private String count401;
    @XStreamAsAttribute()
    private String count403;
    @XStreamAsAttribute()
    private String count404;
    @XStreamAsAttribute()
    private String count503;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCountRequests() {
        return countRequests;
    }

    public void setCountRequests(String countRequests) {
        this.countRequests = countRequests;
    }

    public String getCountBytesReceived() {
        return countBytesReceived;
    }

    public void setCountBytesReceived(String countBytesReceived) {
        this.countBytesReceived = countBytesReceived;
    }

    public String getCountBytesTransmitted() {
        return countBytesTransmitted;
    }

    public void setCountBytesTransmitted(String countBytesTransmitted) {
        this.countBytesTransmitted = countBytesTransmitted;
    }

    public String getRateBytesTransmitted() {
        return rateBytesTransmitted;
    }

    public void setRateBytesTransmitted(String rateBytesTransmitted) {
        this.rateBytesTransmitted = rateBytesTransmitted;
    }

    public String getMaxByteTransmissionRate() {
        return maxByteTransmissionRate;
    }

    public void setMaxByteTransmissionRate(String maxByteTransmissionRate) {
        this.maxByteTransmissionRate = maxByteTransmissionRate;
    }

    public String getCountOpenConnections() {
        return countOpenConnections;
    }

    public void setCountOpenConnections(String countOpenConnections) {
        this.countOpenConnections = countOpenConnections;
    }

    public String getMaxOpenConnections() {
        return maxOpenConnections;
    }

    public void setMaxOpenConnections(String maxOpenConnections) {
        this.maxOpenConnections = maxOpenConnections;
    }

    public String getCount2xx() {
        return count2xx;
    }

    public void setCount2xx(String count2xx) {
        this.count2xx = count2xx;
    }

    public String getCount3xx() {
        return count3xx;
    }

    public void setCount3xx(String count3xx) {
        this.count3xx = count3xx;
    }

    public String getCount4xx() {
        return count4xx;
    }

    public void setCount4xx(String count4xx) {
        this.count4xx = count4xx;
    }

    public String getCount5xx() {
        return count5xx;
    }

    public void setCount5xx(String count5xx) {
        this.count5xx = count5xx;
    }

    public String getCountOther() {
        return countOther;
    }

    public void setCountOther(String countOther) {
        this.countOther = countOther;
    }

    public String getCount200() {
        return count200;
    }

    public void setCount200(String count200) {
        this.count200 = count200;
    }

    public String getCount302() {
        return count302;
    }

    public void setCount302(String count302) {
        this.count302 = count302;
    }

    public String getCount304() {
        return count304;
    }

    public void setCount304(String count304) {
        this.count304 = count304;
    }

    public String getCount400() {
        return count400;
    }

    public void setCount400(String count400) {
        this.count400 = count400;
    }

    public String getCount401() {
        return count401;
    }

    public void setCount401(String count401) {
        this.count401 = count401;
    }

    public String getCount403() {
        return count403;
    }

    public void setCount403(String count403) {
        this.count403 = count403;
    }

    public String getCount404() {
        return count404;
    }

    public void setCount404(String count404) {
        this.count404 = count404;
    }

    public String getCount503() {
        return count503;
    }

    public void setCount503(String count503) {
        this.count503 = count503;
    }
}
