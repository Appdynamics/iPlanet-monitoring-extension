package com.appdynamics.monitors.iPlanet.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("profile")
public class Profile {
    @XStreamAsAttribute()
    private String id;
    @XStreamAsAttribute()
    private String name;
    @XStreamAsAttribute()
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
