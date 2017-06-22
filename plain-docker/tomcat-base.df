FROM ubuntu:16.04

RUN apt-get update -y && \
    apt-get install openjdk-8-jre curl -y && \
    curl http://ftp.halifax.rwth-aachen.de/apache/tomcat/tomcat-8/v8.5.12/bin/apache-tomcat-8.5.12.tar.gz -o /opt/tomcat.tar.gz && \
    tar -xf /opt/tomcat.tar.gz -C /opt && \ 
    rm -f /opt/tomcat.tar.gz && \
    ln -s /opt/apache-tomcat-8.5.12 /opt/tomcat85 && \
    apt-get remove curl -y

ENV CATALINA_HOME /opt/tomcat85
ENV PATH $CATALINA_HOME/bin:$PATH
WORKDIR $CATALINA_HOME 

ENTRYPOINT [ "catalina.sh"]  
CMD ["run"]
EXPOSE 8080
