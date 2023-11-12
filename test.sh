#!/bin/bash

# Compile all the Java files
javac ImList.java Pair.java PQ.java Customer.java Server.java Event.java ArriveEvent.java LeaveEvent.java ServeEvent.java WaitEvent.java DoneEvent.java EventComp.java Simulator.java Main.java DefaultServiceTime.java

# Check if the compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

# Check if an argument is provided
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <input_file>"
    exit 1
fi

# Extract the base name without extension (e.g., "1" from "1.in")
BASENAME=$(basename "$1" .in)

# Run the Java program and compare the output
OUTPUT=$(java Main < "$1" | diff -wy - "$BASENAME.out")

# Check the exit status of diff
if [ $? -ne 0 ]; then
    echo "$OUTPUT"
else
    echo "-------"
    echo "Success"
    echo "-------"
fi
