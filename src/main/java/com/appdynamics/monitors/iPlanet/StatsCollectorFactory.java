/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet;


import com.appdynamics.extensions.http.SimpleHttpClient;
import com.appdynamics.monitors.iPlanet.collector.PerfStatsCollector;
import com.appdynamics.monitors.iPlanet.collector.StatsCollector;
import com.appdynamics.monitors.iPlanet.collector.XMLStatsCollector;
import com.appdynamics.monitors.iPlanet.config.Configuration;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.Logger;

public class StatsCollectorFactory {

    private static final Logger logger = Logger.getLogger(StatsCollectorFactory.class);
    private static final String STATS_FORMAT_TEXT = "text";
    private static final String STATS_FORMAT_XML = "xml";

    public static StatsCollector getStatsCollector(Configuration configuration, SimpleHttpClient httpClient) throws TaskExecutionException {
        String statsFormat = configuration.getStatsFormat();
        if (STATS_FORMAT_TEXT.equals(statsFormat)) {
            return new PerfStatsCollector(httpClient);
        } else if (STATS_FORMAT_XML.equals(statsFormat)) {
            return new XMLStatsCollector(httpClient);
        } else {
            logger.error("Invalid stats format. Supports only text or xml");
            throw new TaskExecutionException("Invalid stats format. Supports only text or xml");
        }
    }

}
