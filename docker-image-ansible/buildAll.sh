# !/bin/bash

cp -R ../plain-ansible plain-ansible

docker build -f setup-app.df -t sparsick/tomcat:ansible .

rm -rf plain-ansible
