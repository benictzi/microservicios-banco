
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','server.port','9092');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','spring.datasource.url','jdbc:h2:tcp://172.16.238.12:1521/banco');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','spring.datasource.driver-class-name','org.h2.Driver');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','spring.datasource.username','sa');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','spring.datasource.password','');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','spring.datasource.hikari.maximum-pool-size','50');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','eureka.client.initial-instance-info-replication-interval-seconds','5');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','eureka.client.registry-fetch-interval-seconds', '5');
INSERT INTO PROPERTIES VALUES('servicio-cuenta','dev','default','eureka.client.service-url.defaultZone','http://172.16.238.27:9050/eureka/');




INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','server.port','9093');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','spring.datasource.url','jdbc:h2:tcp://172.16.238.12:1521/banco');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','spring.datasource.driver-class-name','org.h2.Driver');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','spring.datasource.username','sa');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','spring.datasource.password','');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','spring.datasource.hikari.maximum-pool-size','50');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','eureka.client.initial-instance-info-replication-interval-seconds','5');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','eureka.client.registry-fetch-interval-seconds', '5');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','eureka.client.service-url.defaultZone','http://172.16.238.27:9050/eureka/');

INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','servicios.cuenta.url','http://172.16.238.2:9092');
INSERT INTO PROPERTIES VALUES('servicio-cliente','dev','default','servicios.cuenta.id','servicio-cuenta');

