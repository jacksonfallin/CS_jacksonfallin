#!/bin/sh

rm -f *.class
javac -Xmaxerrs 5 *.java
if [ $? != 0 ] ; then
    exit 1
fi
java -ea MakeImage 
rm *.class
