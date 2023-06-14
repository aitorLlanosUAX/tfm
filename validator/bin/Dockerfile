FROM openjdk:11              
VOLUME /tmp                  
EXPOSE 8081
ADD target/batch-validator-1.1.jar batch-validator-1.1.jar 
ENTRYPOINT ["java","-jar","/batch-validator-1.1.jar"]       