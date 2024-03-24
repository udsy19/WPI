// Author: Udaya Vijay Anand

#ifndef STUDENT2_H_INCLUDED
#define STUDENT2_H_INCLUDED

// Define a constant for the timer increment
#define TIMER_INCREMENT 100

// Define a struct for the packet queue
struct pktQueue {
    struct pkt* waitingPkt; // Pointer to the packet
    struct pktQueue* next;  // Pointer to the next element in the queue
};

// Define an enum for the possible states of process A
typedef enum StateOfA{
    WAIT_FOR_CALL_ZERO_A,
    WAIT_FOR_ACK_ZERO,
    WAIT_FOR_CALL_ONE_A,
    WAIT_FOR_ACK_ONE
} StateOfA;

// Define an enum for the possible states of process B
typedef enum StateOfB{
    WAIT_FOR_CALL_ZERO_B,
    WAIT_FOR_CALL_ONE_B
} StateOfB;

// Function prototypes
void copyString(char* destination, char* source, int length);
struct pkt* constructPkt(int seqnum, int acknum, int checksum, char* payload);
struct msg* constructMsg(char* data);
void enqueue(struct pktQueue* pqueue, struct pkt* pkt);
struct pkt* dequeue(struct pktQueue** pqueue);
int calculateChecksum(char* vdata, int acknum, int seqnum);
int isCorrupt(struct pkt* packet);
void constructACK(struct pkt* packet, int acknum);
void printQueue(struct pktQueue* pqueue);
void A_sendPkt(struct pktQueue* pqueue, StateOfA* stateOfA);

#endif // STUDENT2_H_INCLUDED
