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
