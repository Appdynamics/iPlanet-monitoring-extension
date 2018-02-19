/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.monitors.iPlanet.collector;

import com.appdynamics.extensions.http.Response;
import com.appdynamics.extensions.http.SimpleHttpClient;
import com.appdynamics.extensions.http.WebTarget;
import com.appdynamics.monitors.iPlanet.beans.CPUInfo;
import com.appdynamics.monitors.iPlanet.beans.CacheBucket;
import com.appdynamics.monitors.iPlanet.beans.ConnectionQueueBucket;
import com.appdynamics.monitors.iPlanet.beans.DNSBucket;
import com.appdynamics.monitors.iPlanet.beans.JVM;
import com.appdynamics.monitors.iPlanet.beans.KeepaliveBucket;
import com.appdynamics.monitors.iPlanet.beans.Process;
import com.appdynamics.monitors.iPlanet.beans.Profile;
import com.appdynamics.monitors.iPlanet.beans.ProfileBucket;
import com.appdynamics.monitors.iPlanet.beans.RequestBucket;
import com.appdynamics.monitors.iPlanet.beans.Server;
import com.appdynamics.monitors.iPlanet.beans.ServletBucket;
import com.appdynamics.monitors.iPlanet.beans.Stats;
import com.appdynamics.monitors.iPlanet.beans.Thread;
import com.appdynamics.monitors.iPlanet.beans.ThreadPoolBucket;
import com.appdynamics.monitors.iPlanet.beans.VirtualServer;
import com.appdynamics.monitors.iPlanet.beans.WebAppBucket;
import com.appdynamics.monitors.iPlanet.config.Configuration;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XMLStatsCollector implements StatsCollector {

    private static final Logger logger = Logger.getLogger(XMLStatsCollector.class);

    private static final String STAT_SEPARATOR = "|";

    private SimpleHttpClient httpClient;

    public XMLStatsCollector(SimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Map<String, Number> collect(Configuration configuration) throws TaskExecutionException {
        try {
            WebTarget statsRequest = httpClient.target().path(configuration.getStatsPath());
            Response response = statsRequest.get();
            if (response.getStatus() == 200) {
                String responseString = response.string();
                Map<String, Number> stats = parseResponse(responseString);
                return stats;
            } else {
                logger.error("Got " + response.getStatus() + " with message[" + response.getStatusText() + "] from server");
                throw new TaskExecutionException("Got " + response.getStatus() + " with message[" + response.getStatusText() + "] from server");
            }
        } catch (Exception e) {
            logger.error("Error while getting stats", e);
            throw new TaskExecutionException("Error while getting stats", e);
        }
    }

    private Map<String, Number> parseResponse(String responseString) {
        XStream xStream = new XStream();
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(Stats.class);

        Stats stats = (Stats) xStream.fromXML(responseString);
        Map<String, Number> statsMap = extractStats(stats);
        return statsMap;
    }

    private Map<String, Number> extractStats(Stats stats) {

        Map<String, Number> statsMap = new HashMap<String, Number>();

        Server server = stats.getServer();
        String serverPrefix = "Server" + STAT_SEPARATOR + server.getId();
        populateServerMetrics(statsMap, server, serverPrefix);


        Process process = server.getProcess();
        String processPrefix = serverPrefix + STAT_SEPARATOR + "Process";
        ConnectionQueueBucket connectionQueueBucket = process.getConnectionQueueBucket();

        populateConnectionQueueStats(statsMap, processPrefix, connectionQueueBucket);

        ThreadPoolBucket threadPoolBucket = process.getThreadPoolBucket();
        populateThreadPoolStats(statsMap, server, processPrefix, threadPoolBucket);

        DNSBucket dnsBucket = process.getDnsBucket();
        populatDNSStats(statsMap, processPrefix, dnsBucket);

        KeepaliveBucket keepaliveBucket = process.getKeepaliveBucket();
        populateKeepaliveStats(statsMap, processPrefix, keepaliveBucket);

        CacheBucket cacheBucket = process.getCacheBucket();
        populateCacheStats(statsMap, processPrefix, cacheBucket);

        List<Thread> threads = process.getThreads();
        List<Thread> filteredThreads = removeIdleTherads(threads);
        Multimap<String, Thread> groupedThreads = groupThreadsByMode(filteredThreads);
        String threadsPrefix = processPrefix + STAT_SEPARATOR + "Thread";
        Set<String> groupedMods = groupedThreads.keySet();
        for (String mode : groupedMods) {
            Collection<Thread> modeThreads = groupedThreads.get(mode);
            int index = 0;
            for (Thread thread : modeThreads) {
                String threadModePrefix = threadsPrefix + STAT_SEPARATOR + mode + STAT_SEPARATOR + index;
                RequestBucket requestBucket = thread.getRequestBucket();
                populateRequestBucketStats(statsMap, threadModePrefix, requestBucket);

                List<ProfileBucket> profileBuckets = thread.getProfileBuckets();
                populateProfileBuckets(statsMap, server, threadModePrefix, profileBuckets);
                index++;
            }
        }

        JVM jvm = process.getJvm();
        populateJVMStats(statsMap, processPrefix, jvm);

        VirtualServer virtualServer = server.getVirtualServer();
        String virtualServerPrefix = serverPrefix + STAT_SEPARATOR + "Virtual Server";
        RequestBucket requestBucket = virtualServer.getRequestBucket();
        populateRequestBucketStats(statsMap, virtualServerPrefix, requestBucket);

        List<ProfileBucket> profileBuckets = virtualServer.getProfileBuckets();
        populateProfileBuckets(statsMap, server, virtualServerPrefix, profileBuckets);

        List<WebAppBucket> webAppBuckets = virtualServer.getWebAppBuckets();
        poopulateWebAppBuckets(statsMap, virtualServerPrefix, webAppBuckets);

        List<CPUInfo> cpuInfos = server.getCpuInfos();
        String cpuInfosPrefix = serverPrefix + STAT_SEPARATOR + "CPU Info";
        populateCPUInfos(statsMap, cpuInfosPrefix, cpuInfos);

        return statsMap;
    }

    private void populateJVMStats(Map<String, Number> statsMap, String processPrefix, JVM jvm) {
        String jvmPrefix = processPrefix + STAT_SEPARATOR + "JVM";
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "countClassesLoaded", Integer.valueOf(jvm.getCountClassesLoaded()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "countTotalClassesLoaded", Integer.valueOf(jvm.getCountTotalClassesLoaded()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "countTotalClassesUnloaded", Integer.valueOf(jvm.getCountTotalClassesUnloaded()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "sizeHeap", Integer.valueOf(jvm.getSizeHeap()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "peakThreads", Integer.valueOf(jvm.getPeakThreads()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "countTotalThreadsStarted", Integer.valueOf(jvm.getCountTotalThreadsStarted()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "countThreads", Integer.valueOf(jvm.getCountThreads()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "countGarbageCollections", Integer.valueOf(jvm.getCountGarbageCollections()));
        statsMap.put(jvmPrefix + STAT_SEPARATOR + "millisecondsGarbageCollection", Integer.valueOf(jvm.getMillisecondsGarbageCollection()));
    }

    private void populateCacheStats(Map<String, Number> statsMap, String processPrefix, CacheBucket cacheBucket) {
        String cacheBucketPrefix = processPrefix + STAT_SEPARATOR + "cache-bucket";
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "flagEnabled", Integer.valueOf(cacheBucket.getFlagEnabled()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "secondsMaxAge", Integer.valueOf(cacheBucket.getSecondsMaxAge()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countEntries", Integer.valueOf(cacheBucket.getCountEntries()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "maxEntries", Integer.valueOf(cacheBucket.getMaxEntries()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countOpenEntries", Integer.valueOf(cacheBucket.getCountOpenEntries()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "maxOpenEntries", Integer.valueOf(cacheBucket.getMaxOpenEntries()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "sizeHeapCache", Integer.valueOf(cacheBucket.getSizeHeapCache()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "maxHeapCacheSize", Integer.valueOf(cacheBucket.getMaxHeapCacheSize()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "sizeMmapCache", Integer.valueOf(cacheBucket.getSizeMmapCache()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "maxMmapCacheSize", Integer.valueOf(cacheBucket.getMaxMmapCacheSize()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countHits", Integer.valueOf(cacheBucket.getCountHits()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countMisses", Integer.valueOf(cacheBucket.getCountMisses()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countInfoHits", Integer.valueOf(cacheBucket.getCountInfoHits()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countInfoMisses", Integer.valueOf(cacheBucket.getCountInfoMisses()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countContentHits", Integer.valueOf(cacheBucket.getCountContentHits()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countContentMisses", Integer.valueOf(cacheBucket.getCountContentMisses()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countAcceleratorEntries", Integer.valueOf(cacheBucket.getCountAcceleratorEntries()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countAcceleratableRequests", Integer.valueOf(cacheBucket.getCountAcceleratableRequests()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countUnacceleratableRequests", Integer.valueOf(cacheBucket.getCountUnacceleratableRequests()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countAcceleratableResponses", Integer.valueOf(cacheBucket.getCountAcceleratableResponses()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countUnacceleratableResponses", Integer.valueOf(cacheBucket.getCountUnacceleratableResponses()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countAcceleratorHits", Integer.valueOf(cacheBucket.getCountAcceleratorHits()));
        statsMap.put(cacheBucketPrefix + STAT_SEPARATOR + "countAcceleratorMisses", Integer.valueOf(cacheBucket.getCountAcceleratorMisses()));
    }

    private void populateKeepaliveStats(Map<String, Number> statsMap, String processPrefix, KeepaliveBucket keepaliveBucket) {
        String keepaliveBucketPrefix = processPrefix + STAT_SEPARATOR + "keepalive-bucket";
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "countConnections", Integer.valueOf(keepaliveBucket.getCountConnections()));
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "maxConnections", Integer.valueOf(keepaliveBucket.getMaxConnections()));
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "countHits", Integer.valueOf(keepaliveBucket.getCountHits()));
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "countFlushes", Integer.valueOf(keepaliveBucket.getCountFlushes()));
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "countRefusals", Integer.valueOf(keepaliveBucket.getCountRefusals()));
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "countTimeouts", Integer.valueOf(keepaliveBucket.getCountTimeouts()));
        statsMap.put(keepaliveBucketPrefix + STAT_SEPARATOR + "secondsTimeout", Integer.valueOf(keepaliveBucket.getSecondsTimeout()));
    }

    private void populatDNSStats(Map<String, Number> statsMap, String processPrefix, DNSBucket dnsBucket) {
        String dnsBucketPrefix = processPrefix + STAT_SEPARATOR + "dns-bucket";
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "flagCacheEnabled", Integer.valueOf(dnsBucket.getFlagAsyncEnabled()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "countCacheEntries", Integer.valueOf(dnsBucket.getCountCacheEntries()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "maxCacheEntries", Integer.valueOf(dnsBucket.getMaxCacheEntries()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "countCacheHits", Integer.valueOf(dnsBucket.getCountCacheHits()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "countCacheMisses", Integer.valueOf(dnsBucket.getCountCacheMisses()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "flagAsyncEnabled", Integer.valueOf(dnsBucket.getFlagAsyncEnabled()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "countAsyncNameLookups", Integer.valueOf(dnsBucket.getCountAsyncAddrLookups()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "countAsyncAddrLookups", Integer.valueOf(dnsBucket.getCountAsyncAddrLookups()));
        statsMap.put(dnsBucketPrefix + STAT_SEPARATOR + "countAsyncLookupsInProgress", Integer.valueOf(dnsBucket.getCountAsyncLookupsInProgress()));
    }

    private void populateThreadPoolStats(Map<String, Number> statsMap, Server server, String processPrefix, ThreadPoolBucket threadPoolBucket) {
        String threadPoolBucketPrefix = processPrefix + STAT_SEPARATOR + "thread-pool-bucket" + STAT_SEPARATOR + server.getThreadPool().getName();
        statsMap.put(threadPoolBucketPrefix + STAT_SEPARATOR + "countThreadsIdle", Integer.valueOf(threadPoolBucket.getCountThreadsIdle()));
        statsMap.put(threadPoolBucketPrefix + STAT_SEPARATOR + "countThreads", Integer.valueOf(threadPoolBucket.getCountThreads()));
        statsMap.put(threadPoolBucketPrefix + STAT_SEPARATOR + "maxThreads", Integer.valueOf(threadPoolBucket.getMaxThreads()));
        statsMap.put(threadPoolBucketPrefix + STAT_SEPARATOR + "countQueued", Integer.valueOf(threadPoolBucket.getCountQueued()));
        statsMap.put(threadPoolBucketPrefix + STAT_SEPARATOR + "peakQueued", Integer.valueOf(threadPoolBucket.getPeakQueued()));
        statsMap.put(threadPoolBucketPrefix + STAT_SEPARATOR + "maxQueued", Integer.valueOf(threadPoolBucket.getMaxQueued()));
    }

    private void populateConnectionQueueStats(Map<String, Number> statsMap, String processPrefix, ConnectionQueueBucket connectionQueueBucket) {
        String connectionQueueBucketPrefix = processPrefix + STAT_SEPARATOR + "connection-queue-bucket";
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countTotalConnections", Integer.valueOf(connectionQueueBucket.getCountTotalConnections()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countQueued", Integer.valueOf(connectionQueueBucket.getCountQueued()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "peakQueued", Integer.valueOf(connectionQueueBucket.getPeakQueued()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "maxQueued", Integer.valueOf(connectionQueueBucket.getMaxQueued()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countOverflows", Integer.valueOf(connectionQueueBucket.getCountOverflows()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countTotalQueued", Integer.valueOf(connectionQueueBucket.getCountTotalQueued()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "ticksTotalQueued", Integer.valueOf(connectionQueueBucket.getTicksTotalQueued()));
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countQueued1MinuteAverage(X 1000)", Double.valueOf(connectionQueueBucket.getCountQueued1MinuteAverage()) * 1000);
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countQueued5MinuteAverage(X 1000)", Double.valueOf(connectionQueueBucket.getCountQueued5MinuteAverage()) * 1000);
        statsMap.put(connectionQueueBucketPrefix + STAT_SEPARATOR + "countQueued15MinuteAverage(X 1000)", Double.valueOf(connectionQueueBucket.getCountQueued15MinuteAverage()) * 1000);
    }

    private void populateServerMetrics(Map<String, Number> statsMap, Server server, String serverPrefix) {
        statsMap.put(serverPrefix + STAT_SEPARATOR + "secondsRunning", Integer.valueOf(server.getSecondsRunning()));
        statsMap.put(serverPrefix + STAT_SEPARATOR + "ticksPerSecond", Integer.valueOf(server.getTicksPerSecond()));
        statsMap.put(serverPrefix + STAT_SEPARATOR + "maxProcs", Integer.valueOf(server.getMaxProcs()));
        statsMap.put(serverPrefix + STAT_SEPARATOR + "maxThreads", Integer.valueOf(server.getMaxThreads()));
        statsMap.put(serverPrefix + STAT_SEPARATOR + "load1MinuteAverage(X 1000)", Double.valueOf(server.getLoad1MinuteAverage()) * 1000);
        statsMap.put(serverPrefix + STAT_SEPARATOR + "load5MinuteAverage(X 1000)", Double.valueOf(server.getLoad5MinuteAverage()) * 1000);
        statsMap.put(serverPrefix + STAT_SEPARATOR + "load15MinuteAverage(X 1000)", Double.valueOf(server.getLoad15MinuteAverage()) * 1000);
        statsMap.put(serverPrefix + STAT_SEPARATOR + "rateBytesTransmitted", Integer.valueOf(server.getRateBytesTransmitted()));
        statsMap.put(serverPrefix + STAT_SEPARATOR + "rateBytesReceived", Integer.valueOf(server.getRateBytesReceived()));
    }

    private void populateCPUInfos(Map<String, Number> statsMap, String cpuInfosPrefix, List<CPUInfo> cpuInfos) {

        for (CPUInfo cpuInfo : cpuInfos) {
            statsMap.put(cpuInfosPrefix + STAT_SEPARATOR + cpuInfo.getCpu() + STAT_SEPARATOR + "percentIdle", Double.valueOf(cpuInfo.getPercentIdle()));
            statsMap.put(cpuInfosPrefix + STAT_SEPARATOR + cpuInfo.getCpu() + STAT_SEPARATOR + "percentUser", Double.valueOf(cpuInfo.getPercentUser()));
            statsMap.put(cpuInfosPrefix + STAT_SEPARATOR + cpuInfo.getCpu() + STAT_SEPARATOR + "percentKernel", Double.valueOf(cpuInfo.getPercentKernel()));
        }
    }

    private void poopulateWebAppBuckets(Map<String, Number> statsMap, String statPrefix, List<WebAppBucket> webAppBuckets) {
        String webAppBucketPrefix = statPrefix + STAT_SEPARATOR + "web-app-bucket";

        for (WebAppBucket webAppBucket : webAppBuckets) {

            String currentWebAppBucketPrefix = webAppBucketPrefix + STAT_SEPARATOR + webAppBucket.getUri();
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "enabled", "enabled".equals(webAppBucket.getMode()) ? 1 : 0);
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "countJsps", Integer.valueOf(webAppBucket.getCountJsps()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "countReloadedJsps", Integer.valueOf(webAppBucket.getCountReloadedJsps()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "countSessions", Integer.valueOf(webAppBucket.getCountSessions()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "countActiveSessions", Integer.valueOf(webAppBucket.getCountActiveSessions()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "peakActiveSessions", Integer.valueOf(webAppBucket.getPeakActiveSessions()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "countRejectedSessions", Integer.valueOf(webAppBucket.getCountRejectedSessions()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "countExpiredSessions", Integer.valueOf(webAppBucket.getCountExpiredSessions()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "secondsSessionAliveMax", Integer.valueOf(webAppBucket.getSecondsSessionAliveMax()));
            statsMap.put(currentWebAppBucketPrefix + STAT_SEPARATOR + "secondsSessionAliveAverage", Integer.valueOf(webAppBucket.getSecondsSessionAliveAverage()));

            List<ServletBucket> servletBuckets = webAppBucket.getServletBuckets();
            String servletBucketPrefix = currentWebAppBucketPrefix + STAT_SEPARATOR + "servlet-bucket";

            if (servletBuckets != null) {
                for (ServletBucket servletBucket : servletBuckets) {
                    statsMap.put(servletBucketPrefix + STAT_SEPARATOR + servletBucket.getName() + STAT_SEPARATOR + "countRequests", Integer.valueOf(servletBucket.getCountRequests()));
                    statsMap.put(servletBucketPrefix + STAT_SEPARATOR + servletBucket.getName() + STAT_SEPARATOR + "countErrors", Integer.valueOf(servletBucket.getCountErrors()));
                    statsMap.put(servletBucketPrefix + STAT_SEPARATOR + servletBucket.getName() + STAT_SEPARATOR + "millisecondsProcessing", Integer.valueOf(servletBucket.getMillisecondsProcessing()));
                    statsMap.put(servletBucketPrefix + STAT_SEPARATOR + servletBucket.getName() + STAT_SEPARATOR + "millisecondsPeakProcessing", Integer.valueOf(servletBucket.getMillisecondsPeakProcessing()));
                }
            }
        }
    }

    private void populateProfileBuckets(Map<String, Number> statsMap, Server server, String statPrefix, List<ProfileBucket> profileBuckets) {
        String threadProfileBucket = statPrefix + STAT_SEPARATOR + "profile-bucket";
        for (ProfileBucket profileBucket : profileBuckets) {
            String profileName = getProfileName(profileBucket.getProfile(), server.getProfiles());
            statsMap.put(threadProfileBucket + STAT_SEPARATOR + profileName + STAT_SEPARATOR + "countCalls", Integer.valueOf(profileBucket.getCountCalls()));
            statsMap.put(threadProfileBucket + STAT_SEPARATOR + profileName + STAT_SEPARATOR + "countRequests", Integer.valueOf(profileBucket.getCountRequests()));
            statsMap.put(threadProfileBucket + STAT_SEPARATOR + profileName + STAT_SEPARATOR + "ticksDispatch", Integer.valueOf(profileBucket.getTicksDispatch()));
            statsMap.put(threadProfileBucket + STAT_SEPARATOR + profileName + STAT_SEPARATOR + "ticksFunction", Integer.valueOf(profileBucket.getTicksFunction()));
        }
    }

    private void populateRequestBucketStats(Map<String, Number> statsMap, String statPrefix, RequestBucket requestBucket) {

        String requestBucketPrefix = statPrefix + STAT_SEPARATOR + "request-bucket";

        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "countRequests", Integer.valueOf(requestBucket.getCountRequests()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "countBytesReceived", Integer.valueOf(requestBucket.getCountBytesReceived()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "countBytesTransmitted", Integer.valueOf(requestBucket.getCountBytesTransmitted()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "rateBytesTransmitted", Integer.valueOf(requestBucket.getRateBytesTransmitted()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "maxByteTransmissionRate", Integer.valueOf(requestBucket.getMaxByteTransmissionRate()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "countOpenConnections", Integer.valueOf(requestBucket.getCountOpenConnections()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "maxOpenConnections", Integer.valueOf(requestBucket.getMaxOpenConnections()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count2xx", Integer.valueOf(requestBucket.getCount2xx()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count3xx", Integer.valueOf(requestBucket.getCount3xx()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count4xx", Integer.valueOf(requestBucket.getCount4xx()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count5xx", Integer.valueOf(requestBucket.getCount5xx()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "countOther", Integer.valueOf(requestBucket.getCountOther()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count200", Integer.valueOf(requestBucket.getCount200()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count302", Integer.valueOf(requestBucket.getCount302()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count304", Integer.valueOf(requestBucket.getCount304()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count400", Integer.valueOf(requestBucket.getCount400()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count401", Integer.valueOf(requestBucket.getCount401()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count403", Integer.valueOf(requestBucket.getCount403()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count404", Integer.valueOf(requestBucket.getCount404()));
        statsMap.put(requestBucketPrefix + STAT_SEPARATOR + "count503", Integer.valueOf(requestBucket.getCount503()));
    }

    private String getProfileName(String profileId, List<Profile> profiles) {
        for (Profile profile : profiles) {
            if (profileId.equals(profile.getId())) {
                return profile.getDescription();
            }
        }
        return null;
    }

    private List<Thread> removeIdleTherads(List<Thread> threads) {
        Iterable<Thread> filter = Iterables.filter(threads, new Predicate<Thread>() {
            public boolean apply(Thread input) {
                return !"none".equals(input.getConnectionQueue());
            }
        });
        return Lists.newArrayList(filter);
    }

    private Multimap<String, Thread> groupThreadsByMode(List<Thread> threads) {
        ImmutableListMultimap<String, Thread> groupedThreads = Multimaps.index(threads, new Function<Thread, String>() {
            public String apply(Thread input) {
                return input.getMode();
            }
        });
        return groupedThreads;
    }
}
