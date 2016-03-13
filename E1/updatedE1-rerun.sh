#!/bin/bash

export CLASSPATH=$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/user01/LAB1/E1/OUT
hadoop jar Dictionary.jar Dictionary.DictionaryDriver /user/user01/LAB1/E1/DATA /user/user01/LAB1/E1/OUT 
