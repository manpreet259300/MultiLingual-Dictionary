#!/bin/bash

export CLASSPATH=$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes CacheMapper.java
javac -d classes CacheReducer.java
jar -cvf Cache.jar -C classes/ .
javac -classpath $CLASSPATH:Cache.jar -d classes CacheDriver.java
jar -uvf Cache.jar -C classes/ .
