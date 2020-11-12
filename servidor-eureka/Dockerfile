FROM adoptopenjdk:8-jre-hotspot

EXPOSE 9050

ADD ./build/libs/*.jar servidor-eureka.jar

ENTRYPOINT [ "java", "-jar", "servidor-eureka.jar" ]