#!/bin/bash

export CLASSPATH=$(hadoop classpath)
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/user01/LAB1/E2/OUT

hadoop jar Cache.jar Dictionary.CacheDriver -files /user/user01/LAB1/E2/DATA/latin.txt /user/user01/LAB1/E1/OUT/part-r-00000 /user/user01/LAB1/E2/OUT 
