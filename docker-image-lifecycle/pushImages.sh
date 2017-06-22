 #!/bin/sh
docker tag sparsick/mysql:ansible localhost:5000/sparsick/mysql:plain
docker push localhost:5000/sparsick/mysql:plain

docker tag sparsick/tomcat:ansible localhost:5000/sparsick/tomcat:plain
docker push localhost:5000/sparsick/tomcat:plain

# testing
curl -s http://localhost:5000/v2/sparsick/tomcat/tags/list
curl -s http://localhost:5000/v2/sparsick/mysql/tags/list
