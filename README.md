# ansible-docker-talk
You can find here the slides and the sample code of my talk "Es muss nicht immer gleich Docker sein:IT Automation, die zu einem passt" that I presented on JUG Saxony Day in Dresden at 29th September 2017.


## Running the Code Samples
The code samples are tested with Ansible 2.4.0.0, Ansible Container 0.9.2, Serverspec 2.40.0, Docker 17.06.2-ce and Docker Compose 1.16.1

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

### Docker Container Lifecylce with Ansible (docker-container-ansible)
These examples show how to install Docker, Docker Compose and Docker registry with Ansible playbooks and how to deploy Docker container with Ansible playbooks.

1. Go to folder `docker-container-ansible`.
2. Start a virtual machine with `vagrant up`. Dont't forget to copy the ssh id.
3. Add `192.168.33.11:5000` to your unsecured registry list and restart your Docker service.
2. Call `ansible-playbook -i inventories/test -u vagrant setup-dockerd.yml` for installing Docker and Docker Compose.
3. Call `ansible-playbook -i inventories/test -u vagrant setup-docker-registry.yml` for installing Docker registry.
4. Call `ansible-playbook -i inventories/test -u vagrant ../docker-image-lifecycle/build-and-push-images.yml --extra-vars "registry_hostname=192.168.33.11"` for pushing images to Docker registry to demonstrate the next playbooks.
3. Call `ansible-playbook -i inventories/test -u vagrant deploy-docker-container.yml` for deploying Docker container with plain Ansible.
3. Call `ansible-playbook -i inventories/test -u vagrant deploy-docker-container-compose.yml` for deploying Docker container with Ansible and Docker Compose. (Be sure no container runs on the target host)

After starting the containers, the demo web application is available on http://192.168.33.11:8080 .

### Java Webapplication Example (java-web)
Here is the source code of the sample web application. It's the application for the deployment samples and it demonstrates how we can use Docker container in our integration tests integrated in the build. For this Testcontainers is used. Ensure that a Docker Daemon (dockerd) runs your machine.

Test classes `DbMigrationITest` and `PersonRepositoryITest` demonstate the integration of a Docker container in our test.
