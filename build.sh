#!/bin/bash

# Clean the build folder
rm -rf ./build
mkdir ./build

mkdir ./build/jdbc
mkdir ./build/util
mkdir ./build/menu
cp ./src/jdbc/mysql-connector-java-5.1.47.jar ./build/jdbc

# go to build folder
cd ./build

# Building for Font.class
cp ../src/util/Font.java ./util
javac ./util/Font.java
rm -f ./util/Font.java

# Building for Util.class
cp ../src/jdbc/Util.java .
javac Util.java
mv Util.class ./jdbc
rm -f Util.java

# Building for Admin.class
cp ../src/menu/Admin.java ./
javac ./Admin.java
mv Admin.class ./menu
rm -f ./Admin.java

# Building for the Main program
cp ../src/Main.java ./
javac ./Main.java
rm -f ./Main.java

# Run the java program
java -cp .:jdbc/mysql-connector-java-5.1.47.jar Main
