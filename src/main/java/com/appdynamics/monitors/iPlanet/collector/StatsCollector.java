package com.appdynamics.monitors.iPlanet.collector;


import com.appdynamics.monitors.iPlanet.config.Configuration;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;

import java.util.Map;

public interface StatsCollector {

    Map<String, Number> collect(Configuration configuration) throws TaskExecutionException;
}
