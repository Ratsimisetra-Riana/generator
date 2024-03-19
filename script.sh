javac -d . *.java
jar cvf class_generator.jar classGenerator
export CLASSPATH=/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator:$CLASSPATH
java Main.java