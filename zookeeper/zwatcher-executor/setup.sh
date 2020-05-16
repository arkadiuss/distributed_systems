#!/bin/bash

for i in 1 2 3; do
	../apache-zookeeper-3.6.1-bin/bin/zkServer.sh $1 ../apache-zookeeper-3.6.1-bin/conf/zoo$i.cfg
done


