 #!/bin/sh
docker tag sparsick/mysql:ansible 192.168.33.11:5000/sparsick/mysql:ansible
docker push 192.168.33.11:5000/sparsick/mysql:ansible

docker tag sparsick/tomcat:ansible 192.168.33.11:5000/sparsick/tomcat:ansible
docker push 192.168.33.11:5000/sparsick/tomcat:ansible

# check go vagrant machine and call 
