FROM adoptopenjdk:8-jre-hotspot

EXPOSE 9099

ADD ./build/libs/*.jar servidor-configuracion.jar

ENTRYPOINT [ "java", "-jar", "servidor-configuracion.jar" ]