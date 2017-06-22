 #!/bin/sh

docker stop registry

docker rmi 192.168.33.11:5000/sparsick/mysql:ansible localhost:5000/sparsick/mysql:ansible 192.168.33.11:5000/sparsick/tomcat:ansible localhost:5000/sparsick/tomcat:ansible
