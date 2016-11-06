.SUFFIXES: .java .class .o

all: out

JFILES := $(shell ls -q *.java)
OUTPUTS := $(JFILES:.java=.o)
DIRNAME := $(shell basename $(shell pwd))

# Teach make to convert .java to .class
.java.class:
	../../bin/javac-algs4 $*.java

PuzzleChecker.class:
	../../bin/javac-algs4 tests/PuzzleChecker.java
	mv tests/PuzzleChecker.class .

test: PuzzleChecker.class Solver.class
	../../bin/java-algs4 -ea PuzzleChecker tests/*.txt

# Teach make to convert .class to .o
.class.o:
	../../bin/java-algs4 -ea $* > $*.o 2>&1

out: $(OUTPUTS)
	cat $(OUTPUTS)

clean:
	rm -f *.class *.o

zip:
	zip $(DIRNAME).zip *.java
