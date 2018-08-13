### Problem with Netty4ClientHttpRequestFactory and spring boot 2 sleuth tracing

## App

This is a minimal spring boot 2 app to demonstrate the problem with switching from the default AsyncClientHttpRequestFactory to Netty4ClientHttpRequestFactory.

## Steps to reproduce

1.  Start an endpoint to hit from within spring boot app:
docker run -p 8111:80 kennethreitz/httpbin

2.  Start the demo app:
mvn spring-boot:run

3.  Make sure the spring boot app has service enough requests for the problem to be reproducible:
./load-test.sh

4.  When the service has serviced the requests from step 4:  curl http://localhost:8080/go

5.  Look at the logs, will typically see:
```
2018-08-13 15:07:36.454  INFO [-,8f1ac087cc83c929,8f1ac087cc83c929,false] 19870 --- [io-8080-exec-63] com.example.demo.HelloController         : START
2018-08-13 15:07:36.459  INFO [-,1e8a1260e46fbeb8,affe4082d15d80f4,false] 19870 --- [ntLoopGroup-2-1] com.example.demo.MyInterceptor           : SUCCESS
```

This shows that the traceId for the request is wrong in the interceptor.

If the default AsyncClientHttpRequestFactory was used this would have been something like:
```
2018-08-13 15:10:19.805  INFO [-,5e27c2d485920400,5e27c2d485920400,false] 22487 --- [o-8080-exec-198] com.example.demo.HelloController         : START
2018-08-13 15:10:19.808  INFO [-,5e27c2d485920400,e425b9abd06c2793,false] 22487 --- [skExecutor-1001] com.example.demo.MyInterceptor           : SUCCESS
```

In that example the traceid is the same (5e27c2d485920400).