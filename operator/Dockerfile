FROM enmasseproject/java-base:8-8

ARG version=latest
ENV VERSION ${version}
ADD target/enmasse-operator-${VERSION}.jar /enmasse-operator.jar
ENV LOG_LEVEL info

CMD ["/opt/run-java/launch_java.sh", "/enmasse-operator.jar"]
