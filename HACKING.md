# Building EnMasse

## Prerequisites

To build EnMasse, you need

   * [JDK](http://openjdk.java.net/) >= 1.8
   * [Apache Maven](https://maven.apache.org/) >= 3.3.1
   * [Docker](https://www.docker.com/)
   * [GNU Make](https://www.gnu.org/software/make/)
   * [Asciidoctor](https://asciidoctor.org/) >= 1.5.7

The EnMasse java and node modules are built using maven. All docker images are built using make.

## Building

### Pre-installation

*Note*: Make sure docker daemon is in running state.

#### Doing a full build, run unit tests and build docker images:

    make

This can be run at the top level or within each module. You can also run the 'build', 'test', and 'package' targets individually.
This builds all modules including java.


#### Tagging and push images to a docker registry

    export DOCKER_ORG=myorg
    export DOCKER_REGISTRY=docker.io
    #optional parameters
    export COMMIT=v.1.0.3 #for specific version of your image

    docker login -u myuser -p mypassword $DOCKER_REGISTRY

    # To generate templates to pull images from your docker hub org
    make -C templates

    make docker_tag
    make docker_push

*Note*: If you are using OpenShift and 'oc cluster up', you can push images directly to the builtin registry
by setting `DOCKER_ORG=myproject` and `DOCKER_REGISTRY=172.30.1.1:5000` instead.

#### Deploying to an OpenShift instance assuming already logged in

    make deploy

#### Running smoketests against a deployed instance

    make SYSTEMTEST_ARGS=SmokeTest systemtests

### Running full systemtest suite

#### Install systemtests dependencies

    ansible-playbook systemtests/ansible/playbooks/systemtests-dependencies.yml --tags clients --skip-tags dependencies

#### Running the systemtests

    make systemtests
    
#### Run single system test

    make SYSTEMTEST_ARGS="io.enmasse.systemtest.standard.QueueTest#testCreateDeleteQueue" systemtests
    
## Reference

This is a reference of the different make targets and options that can be set when building an
individual module:

#### Make targets

   * `build`        - build
   * `test`         - run tests
   * `package`      - create artifact bundle
   * `docker_build` - build docker image
   * `docker_tag`   - tag docker image
   * `docker_push`  - push docker image
   * `deploy`       - deploys the built templates to OpenShift. The images referenced by the template must be available in a docker registry
   * `systemtests`  - run systemtests

Some of these tasks can be configured using environment variables as listed below.

#### Debugging Java Code on OpenShift or Kubernetes

To enable debug mode for the Java based components, it's necessary to setup following environment variables:

   * JAVA_DEBUG - set to true to enable or false to disable
   * JAVA_DEBUG_PORT - 8787 by default and can be any value above 1000 if need to change it

Use this command to change environment variables values for the deployment

    $CMD set env deployments/<deployment-name> JAVA_DEBUG=true

Where $CMD is `oc` or `kubectl` command depends of the environment.

The following deployment names are available depending on their types and EnMasse configuration:

   * `address-space-controller`
   * `admin`
   * `api-server`
   * `keycloak-controller`
   * `standard-controller`
   * `service-broker`
   * `topic-forwarder`
   * `mqtt-gateway`
   * `mqtt-lwt`
   * `queue-scheduler`

For forwarding port from the remote pod to the local host invoke following command (it will lock terminal) and then
connect with development tool to the forwarded port on localhost

   $CMD port-forward $(oc get pods | grep <deployment-name> | awk '{print $1}') $JAVA_DEBUG_PORT:$JAVA_DEBUG_PORT

#### Environment variables

There are several environment variables that control the behavior of the build. Some of them are
only consumed by some tasks:

   * OPENSHIFT_MASTER  - URL to OpenShift master. Consumed by `deploy` and `systemtests` targets
   * OPENSHIFT_USER    - OpenShift user. Consumed by `deploy` target
   * OPENSHIFT_PASSWD  - OpenShift password. Consumed by `deploy` target
   * OPENSHIFT_TOKEN   - OpenShift token. Consumed by `systemtests` target
   * OPENSHIFT_PROJECT - OpenShift project for EnMasse. Consumed by `deploy` and `systemtests` targets
   * DOCKER_ORG        - Docker organization for EnMasse images. Consumed by `build`, `package`, `docker*` targets. tasks. Defaults to `enmasseproject`
   * DOCKER_REGISTRY   - Docker registry for EnMasse images. Consumed by `build`, `package`, `docker_tag` and `docker_push` targets. Defaults to `docker.io`
   * TAG               - Tag used as docker image tag in snapshots and in the generated templates. Consumed by `build`, `package`, `docker_tag` and `docker_push` targets.
