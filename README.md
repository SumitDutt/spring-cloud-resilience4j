
#GATE WAY</br>
spring.cloud.gateway.routes[0].id=company_service</br>
spring.cloud.gateway.routes[0].uri=lb://COMPANY-SERVICE</br>
#spring.cloud.gateway.routes[0].uri=http://localhost:8081</br>
spring.cloud.gateway.routes[0].predicates[0]=Path=/companies/**</br>
</br>
spring.cloud.gateway.routes[1].id=job_service</br>
spring.cloud.gateway.routes[1].uri=lb://JOB-SERVICE</br>
#spring.cloud.gateway.routes[1].uri=http://localhost:8082</br>
spring.cloud.gateway.routes[1].predicates[0]=Path=/jobs/**</br>
 
spring.cloud.gateway.routes[2].id=review_service</br>
spring.cloud.gateway.routes[2].uri=lb://REVIEW-SERVICE</br>
#spring.cloud.gateway.routes[2].uri=http://localhost:8083</br>
spring.cloud.gateway.routes[2].predicates[0]=Path=/reviews/**</br>

spring.cloud.gateway.routes[3].id=eureka-server</br>
spring.cloud.gateway.routes[3].uri=http://localhost:8761</br>
spring.cloud.gateway.routes[3].predicates[0]=Path=/eureka/main </br>
spring.cloud.gateway.routes[3].filters[0]=SetPath=/</br>

logging.level.root=info</br>
logging.level.org.springframework.cloud.gateway.route.RouteDefinitionLocator=INFO</br>
logging.level.org.springframework.cloud.gateway = TRACE</br>

<dependency></br>
			<groupId>org.springframework.cloud</groupId></br>
			<artifactId>spring-cloud-starter-gateway</artifactId></br>
		</dependency></br>
