 #!/bin/sh

docker run -it --name database -p 3306:3306 -d sparsick/mysql:ansible
docker run -it --name tomcat --rm --link database -v $(pwd)/../plain-ansible/demo-web-application-1.war:/opt/tomcat/webapps/demo.war -p 8080:8080 sparsick/tomcat:ansible
