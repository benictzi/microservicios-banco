FROM adoptopenjdk:8-jre-hotspot

EXPOSE 9027

ADD ./build/libs/*.jar servidor-eureka.jar

ENTRYPOINT [ "java", "-jar", "servidor-eureka.jar" ]