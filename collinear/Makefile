.SUFFIXES: .java .class .o

all: test

JFILES := $(shell ls -q *.java)
OUTPUTS := $(JFILES:.java=.o)
DIRNAME := $(shell basename $(shell pwd))

# Teach make to convert .java to .class
.java.class:
	../../bin/javac-algs4 $*.java

# Teach make to convert .class to .o
.class.o:
	../../bin/java-algs4 -ea $* > $*.o 2>&1

test: $(OUTPUTS)
	cat $(OUTPUTS)

clean:
	rm -f *.class *.o

zip:
	zip $(DIRNAME).zip Point.java BruteCollinearPoints.java FastCollinearPoints.java
