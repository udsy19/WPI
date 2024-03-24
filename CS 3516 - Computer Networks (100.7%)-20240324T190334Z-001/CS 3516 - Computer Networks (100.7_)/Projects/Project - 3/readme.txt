README

This program implements distributed, asynchronous, distance vector routing algorithm. The program simulates a network of nodes that exchange routing information with each other to determine the shortest paths to all other nodes in the network.

The program is implemented in C and consists of several files:

- `project3.c`: The main file that initializes the network and starts the simulation.
- `node0.c`, `node1.c`, `node2.c`, `node3.c`: Files that contain the implementation of each node in the network.
- `project3.h`: Header file that contains the function prototypes and data structures used in the program.
- `makefile`: A makefile used to build and execute the program.

To compile and run the program, navigate to the directory containing the source files and type `make` on the command line. This will build the program and create an executable file called `project3`. Then type `./project3` to start the simulation.

The program allows for setting the trace level on the command line by specifying `-t` followed by the trace level (0, 1, or 2). For example, to run the program with trace level 1, type `./project3 -t 1`.

During the simulation, the nodes will exchange routing information with each other to determine the shortest paths to all other nodes in the network. The program will output the current state of each node's distance table at each step of the simulation.

Once the simulation is complete, the program will output the final state of each node's distance table and the total number of packets transmitted by each node.

Note: The program assumes that the network topology and link costs are defined in a configuration file called `topology.dat`. The format of the configuration file is described in the comments of the `project3.c` file.

