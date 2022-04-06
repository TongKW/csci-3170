#!/bin/bash

# Clean the build folder
rm -rf ./build
mkdir ./build

mkdir ./build/jdbc
mkdir ./build/util
mkdir ./build/menu

cp ./src/jdbc/mysql-connector-java-5.1.47.jar ./build/jdbc

javac ./src/jdbc/Util.java -d ./build/jdbc
javac ./src/util/Font.java -d ./build/util
javac ./src/menu/Admin.java -d ./build/menu
cp ./src/Main.java ./build
javac ./build/Main.java -d ./


