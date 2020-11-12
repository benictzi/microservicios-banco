FROM adoptopenjdk:8-jre-hotspot

EXPOSE 9092

ADD ./build/libs/*.jar servicio-cuenta.jar

ENTRYPOINT [ "java", "-jar", "servicio-cuenta.jar" ]