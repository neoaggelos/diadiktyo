BASE=/home/neo/Documents/ntua/diadiktyo/diadiktyo
CLASSPATH=$(BASE)/jars/axis.jar:$(BASE)/jars/servlet.jar:

all: war

war: classfile
	jar cvfM project1.war WEB-INF

classfile: src/ProjectServlet.java
	javac -cp ${CLASSPATH} src/ProjectServlet.java -d WEB-INF/classes/

clean:
	rm -rf *.war WEB-INF/classes/*.class
