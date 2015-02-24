package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("virtual-server")
public class VirtualServer {

    @XStreamAsAttribute()
    private String mode;
    @XStreamAlias("request-bucket")
    private RequestBucket requestBucket;
    @XStreamImplicit(itemFieldName = "profile-bucket")
    private List<ProfileBucket> profileBuckets;
    @XStreamImplicit(itemFieldName = "web-app-bucket")
    private List<WebAppBucket> webAppBuckets;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public RequestBucket getRequestBucket() {
        return requestBucket;
    }

    public void setRequestBucket(RequestBucket requestBucket) {
        this.requestBucket = requestBucket;
    }

    public List<ProfileBucket> getProfileBuckets() {
        return profileBuckets;
    }

    public void setProfileBuckets(List<ProfileBucket> profileBuckets) {
        this.profileBuckets = profileBuckets;
    }

    public List<WebAppBucket> getWebAppBuckets() {
        return webAppBuckets;
    }

    public void setWebAppBuckets(List<WebAppBucket> webAppBuckets) {
        this.webAppBuckets = webAppBuckets;
    }
}
