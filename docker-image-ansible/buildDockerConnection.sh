 #!/bin/sh

 rm -rf plain-ansible
 cp -R ../plain-ansible plain-ansible


# building base image with python
docker build -f python-plain.df -t sparsick/python:plain .

 cd plain-ansible

# mysql Docker image based on Ansible with connection docker
docker run --name database --rm -td sparsick/python:plain
ansible-playbook -i inventories/docker -u root setup-db.yml
docker commit database sparsick/mysql:ansibledocker
docker stop database

# tomcat Docker image based on Ansible with connection docker
docker run --name tomcat --rm -td sparsick/python:plain
ansible-playbook -i inventories/docker -u root setup-app.yml
docker commit tomcat sparsick/tomcat:ansibledocker
docker stop tomcat

cd ..
