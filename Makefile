# Makefile to compile 

BINDIR=./bin
SRCDIR=./src
DOCDIR=./doc

.SUFFIXES: .java .class

${BINDIR}/%.class: ${SRCDIR}/%.java
	javac $< -cp ${BINDIR} -d ${BINDIR} 

# first build rule
${BINDIR}/C.class: ${BINDIR}/PowerData.class ${BINDIR}/HashTable.class ${BINDIR}/PowerHashApp.class 

clean:
	rm -f ${BINDIR}/*.class

docs:
	javadoc  -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

cleandocs:
	rm -rf ${DOCDIR}/*


