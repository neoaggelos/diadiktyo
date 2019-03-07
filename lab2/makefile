CLASSPATH=../jars/axis.jar:../jars/servlet.jar:

GETwithURL.class: GETwithURL.java
	javac -cp ${CLASSPATH} GETwithURL.java

HTTPGetServlet.class: HTTPGetServlet.java
	javac -cp ${CLASSPATH} HTTPGetServlet.java

CookieServlet.class: CookieServlet.java
	javac -cp ${CLASSPATH} CookieServlet.java

GETwithURL: GETwithURL.class

myServletDir: HTTPGetServlet.class
	cp HTTPGetServlet.class myServletDir/WEB-INF/classes/
	cd myServletDir && jar cvfM myServletDir.war WEB-INF/ && mv myServletDir.war ..

cookieServlet: CookieServlet.class
	cp CookieServlet.class cookieServlet/WEB-INF/classes/
	cd cookieServlet && jar cvfM cookieServlet.war WEB-INF/ *.html && mv cookieServlet.war ..

clean:
	rm -rf *.war *.class