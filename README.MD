# Gradle + Scala Development Project Base
This is a simple development project base you can build upon and customize to jumpstart a new Gradle + Scala project,
instead of starting from scratch. The template provides a skeleton with a range of options from which you can choose to keep only those relevant for your project.

## Content Overview

The project base includes:
- template HOCON config file
- config file loading based on filepath from input args using PureConfig magic
- template log4J setup
- ScalaTest
- Gradle shadowJar (fatJAR) building with commit hash recorded in its Manifest
- Gradle TAR distribution packaging the fatJAR with additional resources and the configuration file
- Gradle template setup for docker image building (just the Gradle side of things, no Dockerfile)
- a template bash script to fill in with any pre-processing you need and trigger the JAR (if Docker is being used, this can be your Docker entrypoint)
- a base for keeping multiple configuration files and dynamically loading the correct one based on `env` build property

## Prerequisites
- git
 
## Setup
1. Use GitHub's _Use this template_ feature to start your own repository with this structure or run `git clone https://github.com/holuond/gradle_scala_dev_project_base`.
1. In your IDE, create a new project from existing sources (select the `build.gradle` file).
1. Run `gradlew clean build --info` to build the project.
1. (Recommended) Replace all placeholder values. Search for all occurrences of `your.app`, `your.pckg` and
 `your.repo` and replace them with your custom values based on the context.
Also replace `your/package` in the path under `src/main/your/package` and `src/test/your/package` and rename the root project directory.
1. (Optional) If you require a different Gradle version then provided via the wrapper, run `gradle wrapper` if you have
a local Gradle installation or use `gradlew wrapper --gradle-version <version>`.

## Usage Examples
1. You can choose to only use the part of the setup that outputs a distribution archive containing a HOCON config
file, logging configuration and a fatJar and remove the Docker setup from `build.gradle`.
Then, you can run the following to build and package the distribution tar archive:
    ```bash
    gradlew clean distTar --info
    ```
    And then you simply unpack the distribution and run the jar:
    ```bash
    dist_dir='your/project/build/distributions'
    artifact_name='example-0.0.1-SNAPSHOT'
    dist="$dist_dir/$artifact_name"
    
    if [ ! -d $dist ]; then
        tar -xf $dist.tar --directory $dist_dir
    fi
    $dist/bin/run.sh -j $dist/lib/$artifact_name-all.jar -c $dist/conf/application.conf -l $dist/conf/log4j.xml
    ```
2. Or you can wrap the output distribution archive in a Docker image. In that case, you can use the prepared `docker` task in the
`build.gradle` file and connect it and its build arguments to your Dockerfile (where you can consume the ARGs from the `docker` task).
You can leverage `run.sh` as your Docker entrypoint.