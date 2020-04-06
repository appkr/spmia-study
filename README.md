## Spring Microservices In Action
- https://github.com/carnellj/spmia_overview

#### Run application
```sh
$ git clone git@github.com/appkr/spima-study
$ cd license-service
$ ./gradlew clean bootRun
```

#### Build & run docker
```sh
$ ./gradlew jibDockerBuild
$ docker run -p 8080:8080 license-service:0.0.1-SNAPSHOT
```
> `Note` in the container
> ```
> .
> ├── classpath
> │   └── license-service-0.0.1-SNAPSHOT-original.jar
> └── libs
>     ├── HdrHistogram-2.1.11.jar
>     ├── ...
>     └── tomcat-embed-websocket-9.0.33.jar
> ```

#### Test

##### CH2

LicenseService
```sh
$ curl -s -i http://localhost:8080/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c/licenses/08dbe05-606e-4dad-9d33-90ef10e334f9
# HTTP/1.1 200
# Content-Type: application/json
# Transfer-Encoding: chunked
# Date: Sun, 05 Apr 2020 07:30:53 GMT
#
# {"id":"08dbe05-606e-4dad-9d33-90ef10e334f9","organizationId":"442adb6e-fa58-47f3-9ca2-ed1fecdfe86c","productName":"Teleco","licenseType":"Seat"}
```

##### CH3

ConfigServer - LicenseService
```sh
$ curl -s http://localhost:8888/license-service/default | jq
# {
#   "name": "license-service",
#   "profiles": [
#     "default"
#   ],
#   "label": null,
#   "version": null,
#   "state": null,
#   "propertySources": [
#     {
#       "name": "file:////Users/appkr/workspace/spmia/configsvr/src/main/resources/config/# license-service/license-service.yml",
#       "source": {
#         "example.property": "I AM IN THE DEFAULT",
#         "spring.jpa.database": "MYSQL",
#         "...": "..."
#       }
#     }
#   ]
# }
```

##### CH4

Organization Service

```sh
$ curl -s -i http://localhost:8090/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c
# HTTP/1.1 200
# Content-Type: application/json
# Transfer-Encoding: chunked
# Date: Mon, 06 Apr 2020 15:25:47 GMT
#
# {"id":"442adb6e-fa58-47f3-9ca2-ed1fecdfe86c","name":"HR-PowerSuite","contactName":"Doug Drewry","contactEmail":"doug.drewry@hr.com","contactPhone":"920-555-1212"}
```

Eureka - LicenseService
```sh
$ curl -s -H "Accept:application/json" http://localhost:8761/eureka/apps/license-service | jq
# {
#   "application": {
#     "name": "LICENSE-SERVICE",
#     "instance": [
#       {
#         "instanceId": "172.30.1.18:license-service:8080",
#         "hostName": "172.30.1.18",
#         "app": "LICENSE-SERVICE",
#         "ipAddr": "172.30.1.18",
#         "status": "UP",
#         "overriddenStatus": "UNKNOWN",
#         "port": {
#           "$": 8080,
#           "@enabled": "true"
#         },
#         "securePort": {
#           "$": 443,
#           "@enabled": "false"
#         },
#         "countryId": 1,
#         "dataCenterInfo": {
#           "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
#           "name": "MyOwn"
#         },
#         "leaseInfo": {
#           "renewalIntervalInSecs": 30,
#           "durationInSecs": 90,
#           "registrationTimestamp": 1586187230363,
#           "lastRenewalTimestamp": 1586187380260,
#           "evictionTimestamp": 0,
#           "serviceUpTimestamp": 1586187229821
#         },
#         "metadata": {
#           "management.port": "8080"
#         },
#         "homePageUrl": "http://172.30.1.18:8080/",
#         "statusPageUrl": "http://172.30.1.18:8080/actuator/info",
#         "healthCheckUrl": "http://172.30.1.18:8080/actuator/health",
#         "vipAddress": "license-service",
#         "secureVipAddress": "license-service",
#         "isCoordinatingDiscoveryServer": "false",
#         "lastUpdatedTimestamp": "1586187230363",
#         "lastDirtyTimestamp": "1586187229736",
#         "actionType": "ADDED"
#       }
#     ]
#   }
# }
```

Eureka - OrganizationService
```sh
$ curl -s -H "Accept:application/json" http://localhost:8761/eureka/apps/organization-service | jq
# {
#   "application": {
#     "name": "ORGANIZATION-SERVICE",
#     "instance": [
#       {
#         "instanceId": "172.30.1.18:organization-service:8090",
#         "hostName": "172.30.1.18",
#         "app": "ORGANIZATION-SERVICE",
#         "ipAddr": "172.30.1.18",
#         "status": "UP",
#         "overriddenStatus": "UNKNOWN",
#         "port": {
#           "$": 8090,
#           "@enabled": "true"
#         },
#         "securePort": {
#           "$": 443,
#           "@enabled": "false"
#         },
#         "countryId": 1,
#         "dataCenterInfo": {
#           "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
#           "name": "MyOwn"
#         },
#         "leaseInfo": {
#           "renewalIntervalInSecs": 30,
#           "durationInSecs": 90,
#           "registrationTimestamp": 1586187451376,
#           "lastRenewalTimestamp": 1586187451376,
#           "evictionTimestamp": 0,
#           "serviceUpTimestamp": 1586187450840
#         },
#         "metadata": {
#           "management.port": "8090"
#         },
#         "homePageUrl": "http://172.30.1.18:8090/",
#         "statusPageUrl": "http://172.30.1.18:8090/actuator/info",
#         "healthCheckUrl": "http://172.30.1.18:8090/actuator/health",
#         "vipAddress": "organization-service",
#         "secureVipAddress": "organization-service",
#         "isCoordinatingDiscoveryServer": "false",
#         "lastUpdatedTimestamp": "1586187451377",
#         "lastDirtyTimestamp": "1586187450777",
#         "actionType": "ADDED"
#       }
#     ]
#   }
# }
```
