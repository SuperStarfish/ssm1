Super starfish mania
====================

#### Goal
... to write ...

#### Build status

###### Master
[![Build Status](https://travis-ci.org/foresterre/ContextProject.svg?branch=master)]()

###### Dev
[![Build Status](https://travis-ci.org/foresterre/ContextProject.svg?branch=dev)]()

#### Dependencies

* accel-lib-android (Included as jar package)

#### Structure

The project consists of multiple sub projects. Each sub projects is included as 
a git submodule in this main repository.

* Starfish (the main repository)

Consists of each subproject submodule. It also contains the projects root gradle
build file, the gradle root configuration and optionally any documents.

* Starfish-Android
* Starfish-Aquarium
* Starfish-client
* Starfish-DataStructures
* Starfish-Desktop (currently for debugging purposes)
* Starfish-GameLogic 
* Starfish-Launcher
* Starfish-Server
* Starfish-View (UI)



#### Tools

###### Static analysis:
* [PMD](http://pmd.sourceforge.net/)
* [Find bugs](http://findbugs.sourceforge.net/)
* [CheckStyle](http://checkstyle.sourceforge.net/)

###### Code coverage:
* [Cobertura](http://cobertura.github.io/cobertura/)