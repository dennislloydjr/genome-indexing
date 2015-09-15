USERNAME=`whoami`
g++ -fPIC -shared -I$JAVA_HOME/include/ -I$JAVA_HOME/include/linux psort.cpp -o libpsort.so -O3
g++ -O3 sampler.cpp -o sampler
javac -classpath ./lib/hadoop-core-0.20.205.0.jar:./lib/hadoop-tools-0.20.205.0.jar:./lib/commons-cli-1.2.jar:./lib/commons-logging-api-1.0.4.jar -d classes/ *.java
jar -cvf SimpleBucketSorter.jar -C classes/ .
hadoop fs -rm /user/$USERNAME/psort/libraries/libpsort.so
hadoop fs -copyFromLocal libpsort.so /user/$USERNAME/psort/libraries/libpsort.so
