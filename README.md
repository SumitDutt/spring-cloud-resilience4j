# spring-cloud-resilience4j

 // @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    //@Retry(name="ompanyBreaker",fallbackMethod = "companyBreakerFallback")
   // @RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
<!-- Resiliece4j -->
		<!--<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>-->
		<dependency>
			<groupId>io.github.resilience4j</groupId>
			<artifactId>resilience4j-spring-boot3</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>

resilience4j.circuitbreaker.instances.companyBreaker.slidingWindowSize=10
resilience4j.circuitbreaker.instances.companyBreaker.minimumNumberOfCalls=5
resilience4j.circuitbreaker.instances.companyBreaker.permittedNumberOfCallsInHalfOpenState=3
resilience4j.circuitbreaker.instances.companyBreaker.waitDurationInOpenState=10s
resilience4j.circuitbreaker.instances.companyBreaker.failureRateThreshold=50
resilience4j.circuitbreaker.instances.companyBreaker.slidingWindowType=count_based


resilience4j.circuitbreaker.instances.companyBreaker.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.companyBreaker.register-health-indicator=true
resilience4j.circuitbreaker.instances.companyBreaker.automatic-transition-from-open-to-half-open-enabled=true
resilience4j.circuitbreaker.instances.companyBreaker.sliding-window-type=count_base

resilience4j.ratelimiter.instances.companyBreaker.timeout-duration = 0s
resilience4j.ratelimiter.instances.companyBreaker.limit-refresh-period = 4s
resilience4j.ratelimiter.instances.companyBreaker.limit-for-period = 2




# Expose health endpoint
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=always
management.health.circuitbreakers.enabled=true
#management.health.ratelimiter.enabled=true
