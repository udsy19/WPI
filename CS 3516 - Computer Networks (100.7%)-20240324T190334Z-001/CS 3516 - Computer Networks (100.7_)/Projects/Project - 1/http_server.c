//@Author Udaya Vijay Anand


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFSIZE 1000

void error(char *msg)
{
    perror(msg);
    exit(1);
}

void handle_client(int client_socket)
{
    char buffer[BUFSIZE];
    int n;

    memset(buffer, 0, BUFSIZE);
    n = read(client_socket, buffer, BUFSIZE-1);
    if (n < 0)
        error("ERROR reading from socket");

    printf("%s", buffer);

    // Extract the requested file name from the HTTP request
    char filename[BUFSIZE];
    char *pos = strstr(buffer, "GET ");
    if (pos != NULL) {
        sscanf(pos + 4, "%[^ ]", filename);
    } else {
        strcpy(filename, "index.html");
    }

    // Open the requested file
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        // If the file does not exist, send a 404 Not Found response
        char response[BUFSIZE];
        sprintf(response, "HTTP/1.1 404 Not Found\r\n\r\n");
        write(client_socket, response, strlen(response));
    } else {
        // If the file exists, send a 200 OK response followed by the file contents
        char response[BUFSIZE];
        sprintf(response, "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
        write(client_socket, response, strlen(response));

        while ((n = fread(buffer, 1, BUFSIZE, file)) > 0) {
            write(client_socket, buffer, n);
        }

        fclose(file);
    }

    close(client_socket);
}

int main(int argc, char *argv[])
{
    int sockfd, newsockfd, portno;
    socklen_t clilen;
    struct sockaddr_in serv_addr, cli_addr;

    if (argc < 2) {
        fprintf(stderr,"ERROR, no port provided\n");
        exit(1);
    }

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0)
        error("ERROR opening socket");

    memset((char *) &serv_addr, 0, sizeof(serv_addr));
    portno = atoi(argv[1]);
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);

    if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
        error("ERROR on binding");

    listen(sockfd, 5);

    while (1) {
        clilen = sizeof(cli_addr);
        newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
        if (newsockfd < 0)
            error("ERROR on accept");

        handle_client(newsockfd);
    }

    close(sockfd);
    return 0;
}