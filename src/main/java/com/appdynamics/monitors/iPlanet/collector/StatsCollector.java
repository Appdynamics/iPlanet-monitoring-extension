/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.collector;


import com.appdynamics.monitors.iPlanet.config.Configuration;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;

import java.util.Map;

public interface StatsCollector {

    Map<String, Number> collect(Configuration configuration) throws TaskExecutionException;
}
