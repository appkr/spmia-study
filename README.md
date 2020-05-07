## Spring Microservices In Action
- https://github.com/carnellj/spmia_overview

```sh
$ gradle -q projects

Root project 'spmia'
+--- Project ':apigateway'
+--- Project ':configsvr'
+--- Project ':eurekasvr'
+--- Project ':license-service'
\--- Project ':organization-service'
```

## 1 Run application locally
Create databases
```sql
CREATE DATABASE IF NOT EXISTS spmia_organization;
CREATE DATABASE IF NOT EXISTS spmia_license;
```

Run applications one by one
```sh
$ git clone git@github.com/appkr/spima-study
$ ./gradlew clean 
$ ./gradlew :configsvr:bootRun 
$ ./gradlew :eurekasvr:bootRun 
$ ./gradlew :apigateway:bootRun 
$ ./gradlew :organization-service:bootRun 
$ ./gradlew :license-service:bootRun 
```

OR <kbd>Cmd</kbd>+<kbd>8</kbd> if you prefer intellij
![](docs/rundashboard.png) 

## 2 Run application on docker
To start
```sh
$ ./gradlew up
```
Note. The database and `mysql_data` volume will be automatically created at first run

To stop <kbd>Ctrl</kbd>+<kbd>c</kbd>

To clear the containers
```sh
$ ./gradlew down
```

> `Note` in the application container
>
> We use [jib-gradle-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) to build and run the applications on docker
> ```sh
> $ tree /app
> app
> ├── classpath
> │   └── license-service-0.0.1-SNAPSHOT-original.jar
> └── libs
>     ├── HdrHistogram-2.1.11.jar
>     ├── ...
>     └── tomcat-embed-websocket-9.0.33.jar
> ```
> 
> ```sh
> $ docker image inspect license-service:latest | jq ".[0].Config.Entrypoint"
>    [
>      "java",
>      "-cp",
>      "/app/classpath/*:/app/libs/*",
>      "dev.appkr.licenses.LicenseServiceApplication"
>    ]
> ```

---

## 3 Exercise

This section is my personal record of studying the book

#### CH2

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

#### CH3

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

#### CH4

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

Eureka - LicenseService
```sh
$ curl -s -H "Accept:application/json" http://localhost:8761/eureka/apps/license-service | jq
# {
#   "application": {
#     "name": "LICENSE-SERVICE",
#     "...": "..."
#   }
# }
```

FeignClient
```sh
$ curl -s http://localhost:8080/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c/licenses/08dbe05-606e-4dad-9d33-90ef10e334f9 | jq
# {
#   "licenseId": "08dbe05-606e-4dad-9d33-90ef10e334f9",
#   "organizationId": "442adb6e-fa58-47f3-9ca2-ed1fecdfe86c",
#   "organization": {
#     "id": "442adb6e-fa58-47f3-9ca2-ed1fecdfe86c",
#     "name": "HR-PowerSuite",
#     "contactName": "Doug Drewry",
#     "contactEmail": "doug.drewry@hr.com",
#     "contactPhone": "920-555-1212"
#   },
#   "productName": "WildCat Application Gateway",
#   "licenseType": "core-prod",
#   "licenseMax": 16,
#   "licenseAllocated": 16,
#   "comment": "I AM IN THE DEFAULT"
# }
```

DiscoveryClient
```sh
$ curl -s http://localhost:8080/v1/tools/eureka/services | jq
# [
#   "LICENSE-SERVICE - http://172.30.1.18:8080:8080",
#   "ORGANIZATION-SERVICE - http://172.30.1.18:8090:8090"
# ]
```

![](docs/eurekasvr.png)

#### CH5

To relay parent thread's context - Use ThreadLocal
```bash
$ curl -s -H "tmx-correlation-id: TEST-CORRELATION-ID" http://localhost:8080/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c/licenses/08dbe05-606e-4dad-9d33-90ef10e334f9 | jq
```
```bash
# The threadNames are the same... was not able to make HystrixCommand run on another thread
2020-04-13 00:36:04.948  INFO 96412 --- [nio-8080-exec-2] d.a.licenses.utils.UserContextFilter     : UserContextFilter Correlation id: TEST-CORRELATION-ID
2020-04-13 00:36:04.952  INFO 96412 --- [nio-8080-exec-2] d.a.l.c.LicenseServiceController         : LicenseServiceController.getLicense Correlation id: TEST-CORRELATION-ID
2020-04-13 00:36:04.993  INFO 96412 --- [nio-8080-exec-2] d.a.licenses.services.LicenseService     : LicenseService.getOrganization Correlation id: TEST-CORRELATION-ID
```

#### CH6

The book suggests Zuul Proxy, but the [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) was used instead.

Is the gateway working?
```bash
$ curl -s http://localhost:5555/actuator/gateway/routes | jq
# @see https://cloud.spring.io/spring-cloud-gateway/2.1.x/single/spring-cloud-gateway.html#_retrieving_the_routes_defined_in_the_gateway
#  [
#    {
#      "predicate": "Paths: [/LICENSE-SERVICE/**], match trailing slash: true",
#      "metadata": {
#        "management.port": "8080"
#      },
#      "route_id": "ReactiveCompositeDiscoveryClient_LICENSE-SERVICE",
#      "filters": [
#        "[[RewritePath /LICENSE-SERVICE/(?<remaining>.*) = '/${remaining}'], order = 1]"
#      ],
#      "uri": "lb://LICENSE-SERVICE",
#      "order": 0
#    },
#    {
#      "predicate": "Paths: [/ORGANIZATION-SERVICE/**], match trailing slash: true",
#      "metadata": {
#        "management.port": "8090"
#      },
#      "route_id": "ReactiveCompositeDiscoveryClient_ORGANIZATION-SERVICE",
#      "filters": [
#        "[[RewritePath /ORGANIZATION-SERVICE/(?<remaining>.*) = '/${remaining}'], order = 1]"
#      ],
#      "uri": "lb://ORGANIZATION-SERVICE",
#      "order": 0
#    }
#  ]
```

```bash
$ curl -s http://localhost:5555/LICENSE-SERVICE/actuator/health | jq
#  {
#    "status": "UP"
#  }

$ curl -s http://localhost:5555/lic/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c/licenses/08dbe05-606e-4dad-9d33-90ef10e334f9 | jq
#  {
#    "licenseId": "08dbe05-606e-4dad-9d33-90ef10e334f9",
#    ...
#  }
```

Context relay
```bash
$ curl -s -i -H "x-b3-traceid: d4ca90093540675a" http://localhost:5555/lic/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c/licenses/08dbe05-606e-4dad-9d33-90ef10e334f9
#  HTTP/1.1 200 OK
#  transfer-encoding: chunked
#  Content-Type: application/json
#  Date: Sun, 19 Apr 2020 07:25:27 GMT
#  x-b3-traceid: d4ca90093540675a
#  
#  {"licenseId":"08dbe05-606e-4dad-9d33-90ef10e334f9","organizationId":"442adb6e-fa58-47f3-9ca2-ed1fecdfe86c","organization":{"id":"442adb6e-fa58-47f3-9ca2-ed1fecdfe86c","name":"HR-PowerSuite","contactName":"Doug Drewry","contactEmail":"doug.drewry@hr.com","contactPhone":"920-555-1212"},"productName":"WildCat Application Gateway","licenseType":"core-prod","licenseMax":16,"licenseAllocated":16,"comment":"I AM IN THE DEFAULT"}

# license-service
# 2020-04-19 16:25:15.869  INFO 52587 --- [nio-8080-exec-1] d.a.licenses.utils.UserContextFilter     : UserContextFilter traceId: d4ca90093540675a
# 2020-04-19 16:25:15.896  INFO 52587 --- [nio-8080-exec-1] d.a.l.c.LicenseServiceController         : LicenseServiceController.getLicense traceId: d4ca90093540675a

# organization-service
# 2020-04-19 16:25:16.437  INFO 52595 --- [nio-8090-exec-1] d.a.o.utils.UserContextFilter            : UserContextFilter traceId: d4ca90093540675a
# 2020-04-19 16:25:16.463  INFO 52595 --- [nio-8090-exec-1] d.a.o.c.OrganizationServiceController    : OrganizationServiceController.getOrganization traceId: d4ca90093540675a
```

#### CH7

```bash
$ curl -s -XPOST -H "Authorization: basic d2ViYXBwOmNoYW5nZWl0" -H "Content-Type: application/x-www-form-urlencoded" http://localhost:8901/oauth/token?grant_type=password&scope=webclient&username=appkr&password=P@ssw0rd | jq
#  {
#    "access_token": "bf576485-6688-4ce8-826f-6c7454778a3c",
#    "token_type": "bearer",
#    "refresh_token": "41a9238f-fe7c-4863-91bd-f558cccde4fa",
#    "expires_in": 43184,
#    "scope": "webclient"
#  }
```