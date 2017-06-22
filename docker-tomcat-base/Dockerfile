FROM ubuntu:16.04

ADD ./apache-tomcat-8.5.11.tar.gz /opt/
    
RUN apt-get update -y && \
    apt-get install openjdk-8-jre -y && \
    apt-get clean && \
    apt-get autoclean && \
    apt-get autoremove -y  && \
    ln -s /opt/apache-tomcat-8.5.11 /opt/tomcat85

ENV CATALINA_HOME /opt/tomcat85
ENV PATH $CATALINA_HOME/bin:$PATH
WORKDIR $CATALINA_HOME 

ENTRYPOINT [ "catalina.sh"]  
CMD ["run"]
EXPOSE 8080
