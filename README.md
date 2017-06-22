# anible-docker-talk
You can find here the slides and the sample code of my talk "Es muss nicht immer gleich Docker sein - IT Automation, die zu einem passt" that I presented on Softwerkskammer Ruhrgebiet in Dortmund at 21st June 2017.


## Running the Code Samples
The code samples are tested with Ansible 2.3.0.0, Docker 17.04.0-ce and Docker Compose 1.11.2.

### Setup Test Infrastructure
I prepare some Vagrantfiles for the setup of the test infrastructure if necessary. The only prerequires are that you have to install VirtualBox and Vagrant on your machine. Then follow these steps:

1. Open a CLI and go to the location of the file `Vagrantfile`.
2. Call `vagrant up`. Vagrant will download the necessary image for VirtualBox. That will take some times.
3. Then copy your public key for the authentication that is needed for a SSH login.
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
4. Call `ansible-playbook -i inventories/test -u vagrant deploy-demo.yml` for deploying demo wep appp on an installed Apache Tomcat.

### Docker Image Build with Ansible (docker-image-ansible)
These examples shows how Ansible playbooks can reuse for Docke image builds.

1. Go to the foler `docker-image-ansible`.
2. Run shell script `buildAll.sh` for building Docker images (tomcat.df and mysql.df) that include Ansible commands in `RUN`.
3. Run shell script `runAll.sh` for running Docker container based on images tomcat.df and mysql.df.
4. Run shell script `stop.sh` for stopping the container.
5. Call `docker-compose up` for running the Docker container with `docker-compose.yml`.


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
2. Start a virtual machine with `vagrant up`.
2. Call `ansible-playbook -i inventories/test -u vagrant setup-dockerd.yml` for installing Docker and Docker Compose.
3. Call `ansible-playbook -i inventories/test -u vagrant setup-docker-registry.yml` for installing Docker registry.
4. Call `ansible-playbook -i inventories/test -u vagrant ../docker-image-lifecycle/build-and-push-images.yml --extra-vars "registry_hostname=192.168.33.11"` for pushing images to Docker registry to demonstrate the next playbooks.
3. Call `ansible-playbook -i inventories/test -u vagrant deploy-docker-container.yml` for deploying Docker container with plain Ansible.
3. Call `ansible-playbook -i inventories/test -u vagrant deploy-docker-container-compose.yml` for deploying Docker container with Ansible and Docker Compose.
