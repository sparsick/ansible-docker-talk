 #!/bin/sh
docker tag sparsick/mysql:ansible localhost:5000/sparsick/mysql:plain
docker push localhost:5000/sparsick/mysql:plain

docker tag sparsick/tomcat:ansible localhost:5000/sparsick/tomcat:plain
docker push localhost:5000/sparsick/tomcat:plain

# check go vagrant machine and call
