
CC = gcc
CFLAGS = -Wall -Wextra -Werror -pedantic

all: http_client http_server RTT_calc

http_client: http_client.c
	$(CC) $(CFLAGS) http_client.c -o http_client

http_server: http_server.c
	$(CC) $(CFLAGS) http_server.c -o http_server

RTT_calc: RTT_calc.c
	$(CC) $(CFLAGS) RTT_calc.c -o RTT_calc

clean:
	rm -f http_client http_server RTT_calc
