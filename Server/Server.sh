#!/bin/bash  
export CLASSPATH=$CALSSPATH:./classes:/var/www/html/DistributedPlayer-Server/Server/libs/ice-3.7.0.jar:/var/www/html/DistributedPlayer-Server/Server/libs/vlcj-3.10.1-test-sources.jar:/var/www/html/DistributedPlayer-Server/Server/libs/jna-4.1.0.jar:/var/www/html/DistributedPlayer-Server/Server/libs/jna-platform-4.1.0.jar:/var/www/html/DistributedPlayer-Server/Server/libs/slf4j-api-1.7.10.jar:/var/www/html/DistributedPlayer-Server/Server/libs/vlcj-3.10.1.jar:/var/www/html/DistributedPlayer-Server/Server/libs/vlcj-3.10.1-tests.jar:/var/www/html/DistributedPlayer-Server/Server/libs/vlcj-3.10.1-sources.jar:/var/www/html/DistributedPlayer-Server/Server/libs/vlcj-3.10.1-javadoc.jar:/var/www/html/DistributedPlayer-Server/Server/libs/slf4j-simple-1.6.1.jar:/var/www/html/DistributedPlayer-Server/Server/libs/log4j-over-slf4j-1.7.2.jar
javac -d classes/ generated/Mp3Player/*.java
javac -cp "libs/*" -d classes/ app/*.java generated/Mp3Player/*.java
java Server
