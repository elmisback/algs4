.SUFFIXES: .java .class .o

all: test

# Define our classes.
JFILES := $(shell ls -q *.java)
OUTPUTS := $(JFILES:.java=.o)

# Teach make to convert .java to .class
.java.class:
	../../bin/javac-algs4 $*.java

# Teach make to convert .class to .o
.class.o:
	../../bin/java-algs4 -ea $* > $*.o

Subset.o: Subset.class
	echo A B C D E F G H I | ../../bin/java-algs4 -ea Subset 3 > Subset.o

# A target to build .class files.
classes: $(JFILES:.java=.class)

test: $(OUTPUTS)
	cat $(OUTPUTS)

clean:
	rm -f *.class *.o

zip:
	zip queues.zip *.java
