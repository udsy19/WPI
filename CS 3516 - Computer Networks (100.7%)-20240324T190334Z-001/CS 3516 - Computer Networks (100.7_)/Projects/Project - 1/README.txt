@Author Udaya Vijay Anand
HTTP Client and Server

This project involves a web client and server that communicate using the HTTP/1.1 protocol. The client can download web pages from a remote server and measure the time it takes to access the server. The server can serve static files to clients and handle GET requests.
Requirements

Before using the HTTP client and server, make sure the following software is installed on your system:

   ** GNU C Compiler (gcc)
   ** Make utility

***Compiling the code***
    To compile the HTTP client and server, go to the directory with the source files and run make. This generates the http_client and http_server executable files.
    
***Running the server***
    Run the HTTP server using the command ./http_server <port_number>, replacing <port_number> with the desired port. For instance, ./http_server 7890 will start the server on port 7890. To stop the server, use Ctrl+C

***Running the client***
    Run the HTTP client with ./http_client [-p] <server_url> <port_number>. Replace <server_url> with the server's URL or IP address and <port_number> with the server's port number. Use the optional -p option to measure the time it takes to access the server.
    For example, ./http_client www.example.com 80 will download index.html from the server at www.example.com on port 80. ./http_client -p www.example.com 80 will measure the time it takes to access the same server.
    ./http_client localhost 7890

***Runnig the RTT Calc (Personal Use Case to Calculate RTT)***
    Run the RTT client with ./RTT_calc  <server_url> <port_number>. Replace <server_url> with the server's URL or IP address and <port_number> with the server's port number.
    This will return the RTT for a total of 10 trials and will calculate the average RTT. This is used only to easen the documentation process and to take multiple trials at one instance.