 #!/bin/sh

rm -rf plain-ansible
cp -R ../plain-ansible plain-ansible

docker build -f setup-app.df -t sparsick/tomcat:ansible .
docker build -f setup-db.df -t sparsick/mysql:ansible .
