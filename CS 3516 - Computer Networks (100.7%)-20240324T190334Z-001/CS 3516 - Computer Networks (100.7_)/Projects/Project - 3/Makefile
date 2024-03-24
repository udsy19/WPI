CC = gcc
CFLAGS = -Wall -g

# Define object files
OBJ = node0.o node1.o node2.o node3.o project3.o

# Define header files
HDR = project3.h

# Default target to compile the executable
all: project3

# Compile each object file
node0.o: node0.c $(HDR)
	$(CC) $(CFLAGS) -c node0.c

node1.o: node1.c $(HDR)
	$(CC) $(CFLAGS) -c node1.c

node2.o: node2.c $(HDR)
	$(CC) $(CFLAGS) -c node2.c

node3.o: node3.c $(HDR)
	$(CC) $(CFLAGS) -c node3.c

project3.o: project3.c $(HDR)
	$(CC) $(CFLAGS) -c project3.c

# Link object files into the executable
project3: $(OBJ)
	$(CC) $(CFLAGS) -o project3 $(OBJ)

# Clean target to remove object files and executable
clean:
	rm -f *.o project3
