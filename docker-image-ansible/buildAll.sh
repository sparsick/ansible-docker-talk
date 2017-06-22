 #!/bin/sh

rm -rf plain-ansible
cp -R ../plain-ansible plain-ansible

docker build -f tomcat.df -t sparsick/tomcat:ansible .
docker build -f mysql.df -t sparsick/mysql:ansible .
