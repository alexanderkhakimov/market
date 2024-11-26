FROM openjdk:20
COPY target/market-0.0.1-SNAPSHOT.jar market.jar
ENTRYPOINT ["java","-jar","/market.jar"]
EXPOSE 8080
