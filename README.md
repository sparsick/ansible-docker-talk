# ansible-docker-talk
You can find here the slides and the sample code of my talk "Es muss nicht gleich Docker sein - IT Automation, die zu einem passt" that I presented on "JUG Münster" in Bochum at 21st November 2018.


## Running the Code Samples
The code samples are tested with Ansible 2.4.0.0, Serverspec 2.40.0, Docker 17.06.2-ce and Docker Compose 1.16.1

### Setup Test Infrastructure
I prepare some Vagrantfiles for the setup of the test infrastructure if necessary. The only prerequires are that you have to install VirtualBox and Vagrant on your machine. It is tested with Vagrant 2.0.0 . Then follow these steps:

1. Open a CLI and go to the location of the file `Vagrantfile`.
2. Call `vagrant up`. Vagrant will download the necessary image for VirtualBox. That will take some times.
3. Then copy your public key for the authentication that is needed for a SSH login. The password for the user `vagrant` is `vagrant`.
```
ssh-copy-id -i ~/.ssh/id_rsa vagrant@192.168.33.10
```
Hint: Public and private keys can be generated with the following command: `ssh-keygen`

### Run Ansible Playbooks (plain-ansible)
There exists three playbooks for setting up a database, installing Apache Tomcat and deploying WAR file on an installed Apache Tomcat

1. Go to the folder `plain-ansible`.
2. Start a virtual machine with `vagrant up`.
2. Call `ansible-playbook -i inventories/test -u vagrant setup-db.yml` for setting up the database.
3. Call `ansible-playbook -i inventories/test -u vagrant setup-app.yml` for installing Apache Tomcat.
4. Call `ansible-playbook -i inventories/test -u vagrant deploy-demo.yml` for deploying demo wep app on an installed Apache Tomcat.
5. Now the demo web application is available on http://192.168.33.10:8080/demo


#### Run Serverspec Tests For Ansible Setup Playbooks (plain-ansible)
The Serverspec tests are stored in the folder `plain-ansible/spec/ansible_demo`. To run the test, execute following steps, only prerequirement is that the virtualbox is provisioned wit h above Ansible playbooks:

1. Go to folder `plain-ansible`
2. Call `rake spec`

### Docker Image Build with Ansible (docker-image-ansible)
These examples shows how Ansible playbooks can reuse for Docker image builds.

1. Go to the folder `docker-image-ansible`.
2. Run shell script `buildAll.sh` for building Docker images based on Dockerfiles (tomcat.df and mysql.df) that include Ansible commands in `RUN`.
3. Run shell script `buildDockerConnection.sh` for building Docker images based on Docker file python-plain.df and Ansible Connection type _docker_ .
3. Run shell script `runAll.sh` for running Docker container based on images from Docker files tomcat.df and mysql.df.
4. Run shell script `stop.sh` for stopping the container.
5. Call `docker-compose -f docker-compose-ansible.yml up` for running the Docker container based on Docker images built with using Ansible during the image build (see `docker-compose-ansible.yml`).
5. Call `docker-compose -f docker-compose-ansibledocker.yml up` for running the Docker container based on Docker images built with Ansible over connection type docker (see `docker-compose-ansibledocker.yml`).
6. Call `docker-compose -f docker-compose-build.yml up` for running the Docker container based on Dockerfiles with Ansible playbooks (see `docker-compose-build.yml`)

After starting the containers, the demo webapplication is available on http://localhost:8080/demo.

### Docker Image Lifecycle with Ansible (docker-image-lifecycle)
Precondition:
1. Create or modify `/etc/docker/daemon.json` with `{ "insecure-registries":["localhost:5000"] }`
2. Restart docker daemon `sudo service docker restart`
3. Install Python docker package `apt-get install python-docker`.

These examples shows how to manage the Docker image lifecycle with Ansible playbook.
1. Go to folder `docker-image-lifecycle`.
1. Run shell script `start-docker-registry.sh` for starting docker registry.
2. Run shell script `pushImages.sh` for pushing images to docker registry with plain docker commands.
3. Call `ansible-playbook build-and-push-images.yml --extra-vars "registry_hostname=localhost"` for building the images of the last example and pushing these images to docker registry.
4. You can test if the images are in the registry with `docker pull localhost:5000/sparsick/tomcat:plain` or `curl -s http://localhost:5000/v2/sparsick/tomcat/tags/list`
5. Run shell script `stop-docker-registry.sh` for stopping docker registry.

### Docker and Java (docker-java)
This example bases on this [blog post](https://banzaicloud.com/blog/java-resource-limits/).
The multi-module Maven project produces a small Java application that consumes memory and two Docker images with this application.

One Docker image bases Java 8 and the other bases on Java 10.

Following commands demonstrate Java's memory behaviour:

```shell
# Jar Running Outside Docker

$> java -jar memory-consumer-1.0-SNAPSHOT.jar
Initial free memory: 4332MB
Max memory: 4336MB
Reserve: 2047MB
Free memory: 2288MB

#Jar Running inside Docker
#Note: cgroup capabilties must be enabled

$> docker run sparsick/docker-java8-demo
Initial free memory: 4334MB
Max memory: 4336MB
Reserve: 2047MB
Free memory: 2287MB


$> docker run -m256M sparsick/docker-java8-demo
Initial free memory: 4334MB
Max memory: 4336MB
Reserve: 2047MB
Killed


$> docker run -m256M -e JAVA_OPT='-Xms64M -Xmx256M' sparsick/docker-java8-demo
Initial free memory: 227MB
Max memory: 228MB
Reserve: 181MB
Free memory: 48MB

#For JDK 8u131+ and JDK 9
$> docker run -m256M -e JAVA_OPT='-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1' sparsick/docker-java8-demo
Initial free memory: 227MB
Max memory: 228MB
Reserve: 182MB
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at MemoryConsumer.main(MemoryConsumer.java:27)


$> docker run -m256M -e JAVA_OPT='-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms64M' sparsick/docker-java8-demo
Initial free memory: 227MB
Max memory: 228MB
Reserve: 181MB
Free memory: 50MB

# Java 10
$> docker run -m256M -e sparsick/docker-java10-demo
Initial free memory: 120MB
Max memory: 121MB
Reserve: 96MB
Free memory: 24MB

```

### Java Webapplication Example (java-web)
Here is the source code of the sample web application. It's the application for the deployment samples and it demonstrates how we can use Docker container in our integration tests integrated in the build. For this Testcontainers is used. Ensure that a Docker Daemon (dockerd) runs your machine.

Test classes `DbMigrationITest` and `PersonRepositoryITest` demonstate the integration of a Docker container in our test.
