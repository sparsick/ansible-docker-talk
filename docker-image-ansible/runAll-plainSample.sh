 #!/bin/sh

docker run -it --name database -d --rm sparsick/mysql:plain
docker run -it --name tomcat --rm --link database -v $(pwd)/plain-ansible/demo-web-application-1.war:/opt/tomcat/webapps/demo.war -p 8080:8080 sparsick/tomcat:plain
