all: test

Deque.class: Deque.java
	../../bin/javac-algs4 Deque.java

testDeque: Deque.class
	../../bin/java-algs4 -ea Deque

RandomizedQueue.class:
	../../bin/javac-algs4 RandomizedQueue.java

testRQ: RandomizedQueue.class
	../../bin/java-algs4 -ea RandomizedQueue

test: testDeque testRQ

clean:
	rm *.class
