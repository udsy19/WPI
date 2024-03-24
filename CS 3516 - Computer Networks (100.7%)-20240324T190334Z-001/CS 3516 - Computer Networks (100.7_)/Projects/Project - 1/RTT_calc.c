#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <sys/time.h>

#define BUFSIZE 1024

void error(char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n, i;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[BUFSIZE];

    if (argc < 3) {
       fprintf(stderr,"usage %s [-p] hostname port\n", argv[0]);
       exit(1);
    }


    portno = atoi(argv[argc-1]);
    server = gethostbyname(argv[argc-2]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    memset((char *) &serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    memcpy((char *)&serv_addr.sin_addr.s_addr, (char *)server->h_addr_list[0], server->h_length);
    serv_addr.sin_port = htons(portno);

    double total_rtt = 0.0;
    for (i = 0; i < 10; i++) {
        sockfd = socket(AF_INET, SOCK_STREAM, 0);
        if (sockfd < 0)
            error("ERROR opening socket");

        if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0)
            error("ERROR connecting");

        // Construct the GET request
        char request[BUFSIZE];
        sprintf(request, "GET / HTTP/1.1\r\nHost: %s\r\n\r\n", argv[argc-2]);

        // Measure RTT
        struct timeval start, end;
        gettimeofday(&start, NULL);

        // Send the request to the server
        n = write(sockfd, request, strlen(request));
        if (n < 0)
             error("ERROR writing to socket");

        // Read the server's response
        memset(buffer, 0, BUFSIZE);
        n = read(sockfd, buffer, BUFSIZE-1);
        if (n < 0)
             error("ERROR reading from socket");

        // Measure RTT
        gettimeofday(&end, NULL);
        double rtt = (end.tv_sec - start.tv_sec) * 1000.0 + (end.tv_usec - start.tv_usec) / 1000.0;
        total_rtt += rtt;
        printf("RTT %d: %.2f ms\n", i+1, rtt);

        close(sockfd);
    }

    printf("Average RTT: %.2f ms\n", total_rtt/10.0);

    return 0;
}
