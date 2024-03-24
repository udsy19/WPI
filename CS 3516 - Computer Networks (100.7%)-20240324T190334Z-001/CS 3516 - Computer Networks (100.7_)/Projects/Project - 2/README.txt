@Author Udaya Vijay Anand

*** Compiling the Code ***
  To compile the code, open a terminal and navigate to the directory containing the source files. 
  Run the following command to compile the code: 
    "make"
  If you want to remove the executable and object files, run the following command:
    "make clean"



*** Running the Code ***
To run the code, execute the following command in the terminal:
  ./project2 <num_of_messages> <packet_loss_prob> <packet_corrupt_prob> <packet_order_prob> <avg_time_between_msgs> <trace_level> <random_action> <bidirectional>

Or alternatively, to run the following executable file with instructions, run the code below.
This will give an idea for the parameters of the input:
  ./project2

*** Here are the explanations of the arguments: ***
  <num_of_messages>: The number of messages to simulate (an integer between 1 and 10000).
  <packet_loss_prob>: The probability of packet loss (a number between 0.0 and 1.0).
  <packet_corrupt_prob>: The probability of packet corruption (a number between 0.0 and 1.0).
  <packet_order_prob>: The probability of packets arriving out of order (a number between 0.0 and 1.0).
  <avg_time_between_msgs>: The average time between messages from sender's layer 5 (a number greater than 0.0).
  <trace_level>: The level of tracing desired (an integer between 0 and 4).
  <random_action>: Whether to randomize the actions of the entities (0 for no, 1 for yes).
  <bidirectional>: Whether to use bidirectional communication (0 for no, 1 for yes).
Note: The values for the arguments must be within the ranges specified. If you enter an invalid value, the program will prompt you to enter a valid value.



*** Output ***
The program will output log messages to the console as it simulates the data link layer. 
The log messages include information such as sent/received messages, packet timeouts, and acknowledgments. 
The program will also output statistics about the simulation, including the number of messages sent/received, 
the number of packets sent/dropped/corrupted, and the average time a message took to travel from sender to receiver.

