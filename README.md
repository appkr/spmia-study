Spring Microservices In Action
- https://github.com/carnellj/spmia_overview

```sh
$ git clone git@github.com/appkr/spima-study
$ cd license-service
$ ./gradlew clean bootRun
```

```sh
$ curl -s -H http://localhost:8080/v1/organizations/7af0a582-b16c-497a-95cb-7b75db9bd8a5/licenses/7a8adeb8-43d7-48f6-b861-ea4c4d566080
# HTTP/1.1 200
# Content-Type: application/json
# Transfer-Encoding: chunked
# Date: Sun, 05 Apr 2020 07:30:53 GMT
# 
# {"id":"7a8adeb8-43d7-48f6-b861-ea4c4d566080","organizationId":"7af0a582-b16c-497a-95cb-7b75db9bd8a5","productName":"Teleco","licenseType":"Seat"}
```
