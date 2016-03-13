#!/bin/bash

export CLASSPATH=$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes DictionaryMapper.java
javac -d classes DictionaryReducer.java
jar -cvf Dictionary.jar -C classes/ .
javac -classpath $CLASSPATH:Dictionary.jar -d classes DictionaryDriver.java
jar -uvf Dictionary.jar -C classes/ .
