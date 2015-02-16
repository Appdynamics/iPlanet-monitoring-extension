/**
 * Copyright 2014 AppDynamics, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdynamics.monitors.iPlanet;

import com.appdynamics.TaskInputArgs;
import com.appdynamics.extensions.PathResolver;
import com.appdynamics.extensions.http.SimpleHttpClient;
import com.appdynamics.extensions.yml.YmlReader;
import com.appdynamics.monitors.iPlanet.config.Configuration;
import com.google.common.base.Strings;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class IPlanetMonitor extends AManagedMonitor {

    private static final Logger logger = Logger.getLogger(IPlanetMonitor.class);

    private static final String CONFIG_ARG = "config-file";


    public IPlanetMonitor() {
        String version = getClass().getPackage().getImplementationTitle();
        String msg = String.format("Using Monitor Version [%s]", version);
        logger.info(msg);
        System.out.println(msg);
    }

    public TaskOutput execute(Map<String, String> taskArguments, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        if (taskArguments != null) {
            logger.info("Starting the iPlanet Monitoring Task");
            String configFilename = getConfigFilename(taskArguments.get(CONFIG_ARG));

            try {
                Configuration config = YmlReader.readFromFile(configFilename, Configuration.class);

                Map<String, String> clientArguments = buildHttpClientArguments(config);

                SimpleHttpClient httpClient = SimpleHttpClient.builder(clientArguments).build();

                StatsCollector statsCollector = new StatsCollector(httpClient);
                Map<String, Number> stats = statsCollector.collect(config);
                print(config.getMetricPrefix(), stats);

                logger.info("iPlanet monitoring task completed successfully.");
                return new TaskOutput("iPlanet monitoring task completed successfully.");
            } catch (Exception e) {
                logger.error("Metrics collection failed", e);
                throw new TaskExecutionException("Metrics collection failed", e);
            }
        }
        throw new TaskExecutionException("iPlanet monitoring task completed with failures.");
    }

    private void print(String metricPrefix, Map<String, Number> stats) {
        for(Map.Entry<String, Number> stat : stats.entrySet()) {
            printMetric(metricPrefix+stat.getKey(), stat.getValue());
        }
    }

    private void printMetric(String metricPath, Number metricValue) {

        MetricWriter metricWriter = super.getMetricWriter(metricPath, MetricWriter.METRIC_AGGREGATION_TYPE_AVERAGE, MetricWriter.METRIC_TIME_ROLLUP_TYPE_AVERAGE, MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
        );
        if (metricValue instanceof Double) {
            metricWriter.printMetric(String.valueOf(Math.round((Double) metricValue)));
        } else if (metricValue instanceof Float) {
            metricWriter.printMetric(String.valueOf(Math.round((Float) metricValue)));
        } else {
            metricWriter.printMetric(String.valueOf(metricValue));
        }
    }

    private String getConfigFilename(String filename) {
        if (filename == null) {
            return "";
        }
        // for absolute paths
        if (new File(filename).exists()) {
            return filename;
        }
        // for relative paths
        File jarPath = PathResolver.resolveDirectory(AManagedMonitor.class);
        String configFileName = "";
        if (!Strings.isNullOrEmpty(filename)) {
            configFileName = jarPath + File.separator + filename;
        }
        return configFileName;
    }

    private Map<String, String> buildHttpClientArguments(Configuration config) {
        Map<String, String> clientArgs = new HashMap<String, String>();
        clientArgs.put(TaskInputArgs.HOST, config.getHost());
        clientArgs.put(TaskInputArgs.PORT, String.valueOf(config.getPort()));

        //set optional proxy params
        clientArgs.put(TaskInputArgs.PROXY_HOST, config.getProxyHost());
        clientArgs.put(TaskInputArgs.PROXY_PORT, String.valueOf(config.getProxyPort()));
        clientArgs.put(TaskInputArgs.PROXY_USER, config.getProxyUser());
        clientArgs.put(TaskInputArgs.PROXY_PASSWORD, config.getProxyPassword());

        return clientArgs;
    }
}