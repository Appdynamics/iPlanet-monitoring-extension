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

import com.appdynamics.extensions.http.Response;
import com.appdynamics.extensions.http.SimpleHttpClient;
import com.appdynamics.extensions.http.WebTarget;
import com.appdynamics.monitors.iPlanet.config.Configuration;
import com.google.common.base.CharMatcher;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class StatsCollector {

    private static final Logger logger = Logger.getLogger(StatsCollector.class);

    private SimpleHttpClient httpClient;

    public StatsCollector(SimpleHttpClient httpClient) {
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
        } catch(Exception e) {
            logger.error("Error while getting stats", e);
            throw new TaskExecutionException("Error while getting stats", e);
        }
    }

    private Map<String, Number> parseResponse(String responseString) {
        String[] responseLines = responseString.split("\n");

        Map<String, Number> stats = new HashMap<String, Number>();

        int length = responseLines.length;
        int curIndex = 0;

        while (curIndex != length) {
            String s = responseLines[curIndex];
            if ("ConnectionQueue:".equals(s)) {
                curIndex += 2;
                String queueLengths = responseLines[curIndex];
                String[] lengths = parseStatLine(queueLengths);
                String[] lengthsArray = lengths[1].split("/");
                stats.put("ConnectionQueue|Current Queue Length", Integer.valueOf(lengthsArray[0]));
                stats.put("ConnectionQueue|Peak Queue Length", Integer.valueOf(lengthsArray[1]));
                stats.put("ConnectionQueue|Limit Queue Length", Integer.valueOf(lengthsArray[2]));

                curIndex++;
                String totalQueueConnections = responseLines[curIndex];
                String[] total = parseStatLine(totalQueueConnections);
                stats.put("ConnectionQueue|Total Connections Queued", Integer.valueOf(total[1]));

                curIndex++;
                String avgQueueLengths = responseLines[curIndex];
                String[] avgLengths = parseStatLine(avgQueueLengths);
                String[] avgLengthsArray = avgLengths[1].split(",");
                stats.put("ConnectionQueue|Average Queue Length(1 min)", Double.valueOf(avgLengthsArray[0].trim()));
                stats.put("ConnectionQueue|Average Queue Length(5 min)", Double.valueOf(avgLengthsArray[1].trim()));
                stats.put("ConnectionQueue|Average Queue Length(15 min)", Double.valueOf(avgLengthsArray[2].trim()));

                curIndex++;
                String avgQueueDelay = responseLines[curIndex];
                String[] queueDelay = parseStatLine(avgQueueDelay);
                String delayTime = CharMatcher.JAVA_DIGIT.or(CharMatcher.is('.')).retainFrom(queueDelay[1]);
                stats.put("ConnectionQueue|Average Queueing Delay", Double.valueOf(delayTime));
            }

            if (s.contains("ListenSocket")) {
                curIndex += 2;

                curIndex++;
                String acceptorThreads = responseLines[curIndex];
                String[] acceptor = parseStatLine(acceptorThreads);
                stats.put("ListenSocket|Acceptor Threads", Integer.valueOf(acceptor[1]));
            }

            if ("KeepAliveInfo:".equals(s)) {
                curIndex += 2;
                String keepAliveCount = responseLines[curIndex];
                String[] keepAliveCountArr = parseStatLine(keepAliveCount);
                String[] keepAliveCounts = keepAliveCountArr[1].split("/");
                stats.put("KeepAliveInfo|Count", Integer.valueOf(keepAliveCounts[0]));
                stats.put("KeepAliveInfo|Max", Integer.valueOf(keepAliveCounts[1]));

                curIndex++;
                String keepAliveHits = responseLines[curIndex];
                String[] keepAliveHitsArr = parseStatLine(keepAliveHits);
                stats.put("KeepAliveInfo|Hits", Integer.valueOf(keepAliveHitsArr[1]));

                curIndex++;
                String keepAliveFlushes = responseLines[curIndex];
                String[] keepAliveFlushesArr = parseStatLine(keepAliveFlushes);
                stats.put("KeepAliveInfo|Flushes", Integer.valueOf(keepAliveFlushesArr[1]));

                curIndex++;
                String keepAliveRefusals = responseLines[curIndex];
                String[] keepAliveRefusalsArr = parseStatLine(keepAliveRefusals);
                stats.put("KeepAliveInfo|Refusals", Integer.valueOf(keepAliveRefusalsArr[1]));

                curIndex++;
                String keepAliveTimeouts = responseLines[curIndex];
                String[] keepAliveTimeoutsArr = parseStatLine(keepAliveTimeouts);
                stats.put("KeepAliveInfo|Timeouts", Integer.valueOf(keepAliveTimeoutsArr[1]));

                curIndex++;
                String keepAliveTimeout = responseLines[curIndex];
                String[] keepAliveTimeoutArr = parseStatLine(keepAliveTimeout);
                String keepAliveTimeoutValue = CharMatcher.JAVA_DIGIT.retainFrom(keepAliveTimeoutArr[1]);
                stats.put("KeepAliveInfo|Timeout (in sec)", Integer.valueOf(keepAliveTimeoutValue));
            }

            if ("SessionCreationInfo:".equals(s)) {
                curIndex += 2;
                String activeSessions = responseLines[curIndex];
                String[] activeSessionsArr = parseStatLine(activeSessions);
                stats.put("SessionCreationInfo|Active Sessions", Integer.valueOf(activeSessionsArr[1]));

                curIndex++;
                String KeepAliveSessions = responseLines[curIndex];
                String[] KeepAliveSessionsArr = parseStatLine(KeepAliveSessions);
                stats.put("SessionCreationInfo|Keep-Alive Sessions", Integer.valueOf(KeepAliveSessionsArr[1]));

                curIndex++;
                String totalSessionsCreated = responseLines[curIndex];
                String[] totalSessionsCreatedArr = parseStatLine(totalSessionsCreated);
                String[] totalSessionsCount = totalSessionsCreatedArr[1].split("/");
                stats.put("SessionCreationInfo|Total Sessions|Created", Integer.valueOf(totalSessionsCount[0]));
                stats.put("SessionCreationInfo|Total Sessions|Max", Integer.valueOf(totalSessionsCount[1]));
            }

            if ("CacheInfo:".equals(s)) {
                curIndex += 2;
                String fileCacheEnabled = responseLines[curIndex];
                String[] fileCacheEnabledArr = parseStatLine(fileCacheEnabled);
                stats.put("CacheInfo|Total Sessions Count", "yes".equalsIgnoreCase(fileCacheEnabledArr[1]) ? 1 : 0);

                curIndex++;
                String fileCacheEntries = responseLines[curIndex];
                String[] fileCacheEntriesArr = parseStatLine(fileCacheEntries);
                String[] cacheEntries = parseNumbers(fileCacheEntriesArr[1]);
                stats.put("CacheInfo|File Cache Entries|Count", Integer.valueOf(cacheEntries[0]));
                stats.put("CacheInfo|File Cache Entries|Max", Integer.valueOf(cacheEntries[1]));

                curIndex++;
                String fileCacheHitRatio = responseLines[curIndex];
                String[] fileCacheHitRatioArr = parseStatLine(fileCacheHitRatio);
                String[] hitRatioCounts = parseNumbersWithPercent(fileCacheHitRatioArr[1]);
                stats.put("CacheInfo|File Cache Hit Ratio|Count", Integer.valueOf(hitRatioCounts[0]));
                stats.put("CacheInfo|File Cache Hit Ratio|Max", Integer.valueOf(hitRatioCounts[1]));
                stats.put("CacheInfo|File Cache Hit Ratio|Percent", Double.valueOf(hitRatioCounts[2]));

                curIndex++;
                String maxAge = responseLines[curIndex];
                String[] maxAgeArr = parseStatLine(maxAge);
                stats.put("CacheInfo|Maximum Age", Integer.valueOf(maxAgeArr[1]));

                curIndex++;
                String acceleratorEntries = responseLines[curIndex];
                String[] acceleratorEntriesArr = parseStatLine(acceleratorEntries);
                String[] entriesCount = parseNumbers(acceleratorEntriesArr[1]);
                stats.put("CacheInfo|Accelerator Entries|Count", Integer.valueOf(entriesCount[0]));
                stats.put("CacheInfo|Accelerator Entries|Max", Integer.valueOf(entriesCount[1]));

                curIndex++;
                String acceleratableRequests = responseLines[curIndex];
                String[] acceleratableRequestsArr = parseStatLine(acceleratableRequests);
                String[] requestCount = parseNumbersWithPercent(acceleratableRequestsArr[1]);
                stats.put("CacheInfo|Acceleratable Requests|Count", Integer.valueOf(requestCount[0]));
                stats.put("CacheInfo|Acceleratable Requests|Max", Integer.valueOf(requestCount[1]));
                stats.put("CacheInfo|Acceleratable Requests|Percent", Double.valueOf(requestCount[2]));

                curIndex++;
                String acceleratableResponses = responseLines[curIndex];
                String[] acceleratableResponsesArr = parseStatLine(acceleratableResponses);
                String[] responseCount = parseNumbersWithPercent(acceleratableResponsesArr[1]);
                stats.put("CacheInfo|Acceleratable Reponse|Count", Integer.valueOf(responseCount[0]));
                stats.put("CacheInfo|Acceleratable Reponse|Max", Integer.valueOf(responseCount[1]));
                stats.put("CacheInfo|Acceleratable Reponse|Percent", Double.valueOf(responseCount[2]));

                curIndex++;
                String acceleratableHitRatio = responseLines[curIndex];
                String[] acceleratableHitRatioArr = parseStatLine(acceleratableHitRatio);
                String[] hitRatioCount = parseNumbersWithPercent(acceleratableHitRatioArr[1]);
                stats.put("CacheInfo|Acceleratable Hit Ratio|Count", Integer.valueOf(hitRatioCount[0]));
                stats.put("CacheInfo|Acceleratable Hit Ratio|Max", Integer.valueOf(hitRatioCount[1]));
                stats.put("CacheInfo|Acceleratable Hit Ratio|Percent", Double.valueOf(hitRatioCount[2]));
            }

            if ("Native pools:".equals(s)) {
                curIndex += 2;

                curIndex++;
                String idlePeekLimit = responseLines[curIndex];
                String[] idlePeekLimitArr = parseStatLine(idlePeekLimit);
                String[] poolCounts = parseNumbers(idlePeekLimitArr[1]);
                stats.put("Native pools|NativePool|Idle", Integer.valueOf(poolCounts[0]));
                stats.put("Native pools|NativePool|Peak", Integer.valueOf(poolCounts[1]));
                stats.put("Native pools|NativePool|Limit", Integer.valueOf(poolCounts[2]));

                curIndex++;
                String workQueue = responseLines[curIndex];
                String[] workQueueArr = parseStatLine(workQueue);
                String[] workQueueCounts = parseNumbers(workQueueArr[1]);
                stats.put("Native pools|NativePool|Work Queue|Length", Integer.valueOf(workQueueCounts[0]));
                stats.put("Native pools|NativePool|Work Queue|Peak", Integer.valueOf(workQueueCounts[1]));
                stats.put("Native pools|NativePool|Work Queue|Limit", Integer.valueOf(workQueueCounts[2]));
            }

            if ("DNSCacheInfo:".equals(s)) {
                curIndex += 2;
                String enabled = responseLines[curIndex];
                String[] enabledArr = parseStatLine(enabled);
                stats.put("DNSCacheInfo|Enabled", "yes".equalsIgnoreCase(enabledArr[1]) ? 1 : 0);

                curIndex++;
                String cacheEntries = responseLines[curIndex];
                String[] cacheEntriesArr = parseStatLine(cacheEntries);
                String[] entries = parseNumbers(cacheEntriesArr[1]);
                stats.put("DNSCacheInfo|CacheEntries|Count", Integer.valueOf(entries[0]));
                stats.put("DNSCacheInfo|CacheEntries|Max", Integer.valueOf(entries[1]));

                curIndex++;
                String hitRatio = responseLines[curIndex];
                String[] hitRatioArr = parseStatLine(hitRatio);
                String[] hitRatioCounts = parseNumbersWithPercent(hitRatioArr[1]);
                stats.put("DNSCacheInfo|HitRatio|Count", Integer.valueOf(hitRatioCounts[0]));
                stats.put("DNSCacheInfo|HitRatio|Max", Integer.valueOf(hitRatioCounts[1]));
                stats.put("DNSCacheInfo|HitRatio|Percent", Double.valueOf(hitRatioCounts[2]));
            }

            if ("Performance Counters:".equals(s)) {
                curIndex += 3;

                curIndex++;
                String totalRequests = responseLines[curIndex];
                String[] totalRequestsArr = parseStatLine(totalRequests);
                stats.put("Performance Counters|Total number of requests", Integer.valueOf(totalRequestsArr[1]));

                curIndex++;
                String reqProcessionTime = responseLines[curIndex];
                String[] reqProcessionTimeArr = parseStatLine(reqProcessionTime);
                stats.put("Performance Counters|Request processing time|Average(X 1000)", Double.valueOf(reqProcessionTimeArr[1]) * 1000);
                stats.put("Performance Counters|Request processing time|Total(X 1000)", Double.valueOf(reqProcessionTimeArr[2]) * 1000);

                curIndex++;

                curIndex++;
                String defaultBucket = responseLines[curIndex];
                String bucketName = defaultBucket.substring(0, defaultBucket.indexOf("(")).trim();

                curIndex++;
                String noOfRequests = responseLines[curIndex];
                String[] noOfRequestsArr = parseStatLine(noOfRequests);
                stats.put("Performance Counters|" + bucketName + "|Number of Requests", Integer.valueOf(noOfRequestsArr[1]));

                curIndex++;
                String noOfInvocations = responseLines[curIndex];
                String[] noOfInvocationsArr = parseStatLine(noOfInvocations);
                stats.put("Performance Counters|" + bucketName + "|Number of Invocations", Integer.valueOf(noOfInvocationsArr[1]));

                curIndex++;
                String latency = responseLines[curIndex];
                String[] latencyArr = parseStatLine(latency);
                stats.put("Performance Counters|" + bucketName + "|Latency|Average(X 1000)", Double.valueOf(latencyArr[1]) * 1000);
                stats.put("Performance Counters|" + bucketName + "|Latency|Total(X 1000)", Double.valueOf(latencyArr[2]) * 1000);
                stats.put("Performance Counters|" + bucketName + "|Latency|Percent", Double.valueOf(parsePercent(latencyArr[3])));

                curIndex++;
                String functionProcessionTime = responseLines[curIndex];
                String[] functionProcessionTimeArr = parseStatLine(functionProcessionTime);
                stats.put("Performance Counters|" + bucketName + "|Function Processing Time|Average(X 1000)", Double.valueOf(functionProcessionTimeArr[1]) * 1000);
                stats.put("Performance Counters|" + bucketName + "|Function Processing Time|Total(X 1000)", Double.valueOf(functionProcessionTimeArr[2]) * 1000);
                stats.put("Performance Counters|" + bucketName + "|Function Processing Time|Percent", Double.valueOf(parsePercent(functionProcessionTimeArr[3])));

                curIndex++;
                String totalResponseTime = responseLines[curIndex];
                String[] totalResponseTimeArr = parseStatLine(totalResponseTime);
                stats.put("Performance Counters|" + bucketName + "|Total Response Time|Average(X 1000)", Double.valueOf(totalResponseTimeArr[1]) * 1000);
                stats.put("Performance Counters|" + bucketName + "|Total Response Time|Total(X 1000)", Double.valueOf(totalResponseTimeArr[2]) * 1000);
                stats.put("Performance Counters|" + bucketName + "|Total Response Time|Percent", Double.valueOf(parsePercent(totalResponseTimeArr[3])));
            }
            curIndex++;
        }
        return stats;
    }

    private String[] parseNumbersWithPercent(String numbers) {
        String[] numbersWithPercent = new String[3];
        String[] parsed = parseNumbers(numbers);
        numbersWithPercent[0] = parsed[0].trim();
        String numberAndPercent = parsed[1];
        numbersWithPercent[1] = numberAndPercent.substring(0, numberAndPercent.indexOf("(")).trim();
        numbersWithPercent[2] = numberAndPercent.substring(numberAndPercent.indexOf("(") + 1, numberAndPercent.indexOf("%")).trim();
        return numbersWithPercent;
    }

    private String[] parseNumbers(String numbers) {
        return numbers.split("/");
    }

    private String parsePercent(String number) {
        return number.substring(number.indexOf("(") + 1, number.indexOf("%")).trim();
    }

    private String[] parseStatLine(String statLine) {
        return statLine.split("[a-z|0-9|)|:][ \t]{2,}");
    }
}