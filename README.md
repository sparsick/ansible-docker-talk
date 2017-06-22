klassische installation von Tomcat und klassisches Deployment von Webapp mit Ansible
installation von tomcat und deployment von webapp mit Docker
wiederverwendung von der Ansible playbooks um das docker image zu beschreiben => weitere kombination mit packer

ansible-container?
ansible playbooks um die Container zu verwalten

Beispiel:

tomcat webapp mit DB?



## Running the Code Samples

The code samples are tested with Ansible 2.2.1.0 and Docker 17.03.0-ce .

### Setup Test Infrastructure
I prepare a Vagrantfile for the setup of the test infrastructure. The only prerequires are that you have to install VirtualBox and Vagrant on your machine. Then follow these steps:

1. Open a CLI and go to the location of the file `Vagrantfile`.
2. Call `vagrant up`. Vagrant will download the necessary image for VirtualBox. That will take some times.
3. Then copy your public key for the authentication that is needed for a SSH login.
```
ssh-copy-id -i ~/.ssh/id_rsa vagrant@192.168.33.10
```
Hint: Public and private keys can be generated with the following command: `ssh-keygen`

### Run Ansible Playbooks
There exists three playbooks for setting up a database, installing Apache Tomcat and deploying WAR file on an installed Apache Tomcat

1. Go to the folder `plain-ansible`
2. Call `ansible-playbook -i inventories/test -u vagrant setup-db.yml` for setting up the database.
3. Call `ansible-playbook -i inventories/test -u vagrant setup-app.yml` for installing Apache Tomcat.
4. Call `ansible-playbook -i inventories/test -u vagrant deploy-demo.yml` for deploying demo wep appp on an installed Apache Tomcat.

### Reuse Ansible Playbook

`docker-compose up`


### Docker Image lifecycle mit ansible
Create or modify /etc/docker/daemon.json
{ "insecure-registries":["myregistry.example.com:5000"] }
Restart docker daemon
sudo service docker restart

 `docker pull localhost:5000/sparsick/tomcat:plain`

oder `curl -s http://localhost:5000/v2/sparsick/tomcat/tags/list` tag plain bzw tag ansible

Precondition:
apt-get install python-docker

build-and-push-images.yml --extra-vars "registry_hostname=localhost"

### Docker Container Lifecylce

setup-dockerd.yml
setup-docker-registry.yml
../docker-image-lifecycle/build-and-push-images.yml --extra-vars "registry_hostname=192.168.33.11"
