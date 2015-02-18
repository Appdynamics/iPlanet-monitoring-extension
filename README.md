# Oracle iPlanet Monitoring Extension

This extension works only with the standalone machine agent.

##Use Case

Oracle iPlanet Web Server, formerly  known as Sun Java System Web Server or Sun ONE Web Server, delivers a secure infrastructure for hosting different web technologies and applications for enterprises.

##Prerequisite

Follow the link to enable .perf stats, http://docs.oracle.com/cd/E19146-01/821-1834/abyaq/index.html

##Installation

1. Run "mvn clean install"
2. Download and unzip the file 'target/iPlanetMonitor.zip' to \<machineagent install dir\}/monitors
3. Open <b>monitor.xml</b> and configure yml path
4. Open <b>config.yml</b> and configure the server details

<b>monitor.xml</b>
~~~
<argument name="config-file" is-required="true" default-value=""monitors/iPlanetMonitor/config.yml" />
~~~

<b>config.yml</b>
~~~
# iPlanet instance particulars
#https or http
protocol: "https"
host: "localhost"
port: 8443
username: ""
password: ""
statsPath: ".perf"

#Proxy details if any
#proxyHost: ""
#proxyPort:
#proxyUser: ""
#proxyPassword: ""

#prefix used to show up metrics in AppDynamics
metricPrefix:  "Custom Metrics|iPlanet|"
~~~

##Metrics
The following metrics are reported.

###CacheInfo

| Metric Path  |
|----------------|
| Acceleratable Hit Ratio/Count |
| Acceleratable Hit Ratio/Max |
| Acceleratable Hit Ratio/Percent |
| Acceleratable Reponse/Count | 
| Acceleratable Reponse/Max |
| Acceleratable Reponse/Percent |
| Acceleratable Requests/Count |
| Acceleratable Requests/Max |
| Acceleratable Requests/Percent |
| Accelerator Entries/Count |
| Accelerator Entries/Max | 
| File Cache Entries/Count |
| File Cache Entries/Max |
| File Cache Hit Ratio/Count |
| File Cache Hit Ratio/Max |
| File Cache Hit Ratio/Percent |
| Maximum Age |
| Total Sessions Count |

###ConnectionQueue

| Metric Path  |
|----------------|
| Average Queue Length(1 min) |
| Average Queue Length(5 min) |
| Average Queue Length(15 min) |
| Average Queueing Delay |
| Current Queue Length |
| Limit Queue Length |
| Peak Queue Length |
| Total Connections Queued |

###DNSCacheInfo

| Metric Path  |
|----------------|
| CacheEntries/Count |
| CacheEntries/Max |
| HitRatio/Count |
| HitRatio/Max |
| HitRatio/Percent |
| Enabled |

###KeepAliveInfo

| Metric Path  |
|----------------|
| Count |
| Flushes |
| Hits |
| Max |
| Refusals |
| Timeout (in sec) |
| Timeouts |

###ListenSocket

| Metric Path  |
|----------------|
| Acceptor Threads |

###Native Pools

| Metric Path  |
|----------------|
| NativePool/Work Queue/Length |
| NativePool/Work Queue/Limit |
| NativePool/Work Queue/Peak |
| NativePool/Idle |
| NativePool/Limit |
| NativePool/Peak |

###Performance Counters

| Metric Path  |
|----------------|
| default-bucket/Function Processing Time/Average(X 1000) |
| default-bucket/Function Processing Time/Percent |
| default-bucket/Function Processing Time/Total(X 1000) |
| default-bucket/Latency/Average(X 1000) |
| default-bucket/Latency/Percent |
| default-bucket/Latency/Total(X 1000) |
| default-bucket/Total Response Time/Average(X 1000) |
| default-bucket/Total Response Time/Percent |
| default-bucket/Total Response Time/Total(X 1000) |
| default-bucket/Number of Invocations |
| default-bucket/Number of Requests |

###SessionCreationInfo

| Metric Path  |
|----------------|
| Total Sessions/Created |
| Total Sessions/Max |
| Active Sessions |
| Keep-Alive Sessions |

#Custom Dashboard
![](https://github.com/Appdynamics/iPlanet-monitoring-extension/raw/master/iPlanet_Custom_Dash_Board.png)

##Contributing

Always feel free to fork and contribute any changes directly here on GitHub.

##Community

Find out more in the [AppSphere]() community.

##Support

For any questions or feature request, please contact [AppDynamics Center of Excellence](mailto:help@appdynamics.com).
