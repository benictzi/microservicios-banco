FROM adoptopenjdk:8-jre-hotspot

EXPOSE 9093

ADD ./build/libs/*.jar servicio-cliente.jar

ENTRYPOINT [ "java", "-jar", "servicio-cliente.jar" ]