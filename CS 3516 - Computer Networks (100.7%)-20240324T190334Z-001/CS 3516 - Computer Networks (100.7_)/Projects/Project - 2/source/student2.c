// Author: Udaya Vijay Anand

#include <stdio.h>
#include <stdlib.h>
#include "project2.h"
#include <string.h>
#include "student2.h"

// Global variables
struct pktQueue *queueA;
StateOfA AState;
StateOfB BState;
int seqNumber;

// Function to send a packet from A
// Check the current state of A and send the appropriate packet
void A_sendPkt(struct pktQueue *queue, StateOfA *state)
{
    // Send the packet if A is in the correct state and there's a packet to send
    switch (*state)
    {
    case WAIT_FOR_CALL_ZERO_A:
        if (queue->waitingPkt != NULL)
        {
            tolayer3(AEntity, *(queue->waitingPkt));
            startTimer(AEntity, TIMER_INCREMENT);
            *state = WAIT_FOR_ACK_ZERO;
        }
        break;
    case WAIT_FOR_CALL_ONE_A:
        if (queue->waitingPkt != NULL)
        {
            tolayer3(AEntity, *(queue->waitingPkt));
            startTimer(AEntity, TIMER_INCREMENT);
            *state = WAIT_FOR_ACK_ONE;
        }
        break;
    }
}

// Function called when A has a message to send
void A_output(struct msg msgToSend)
{
    // Create a packet with the message to send
    int chksum = calculateChecksum(msgToSend.data, seqNumber, seqNumber);
    struct pkt *packetToSend = constructPkt(seqNumber, seqNumber, chksum, msgToSend.data);

    // Enqueue the packet and toggle the sequence number
    enqueue(queueA, packetToSend);
    seqNumber = (seqNumber == 0) ? 1 : 0;

    // Send the packet
    A_sendPkt(queueA, &AState);
}

// Function called when B has a message to send (not used in this implementation)
void B_output(struct msg msgToSend) {}

// Function called when A receives a packet
void A_input(struct pkt receivedPacket)
{
    struct pkt *dequeuedPkt;
    switch (AState)
    {
    case WAIT_FOR_ACK_ZERO:
        if (isCorrupt(&receivedPacket) || (receivedPacket.acknum != 0))
        {
            // Resend the packet and restart the timer
            stopTimer(AEntity);
            tolayer3(AEntity, *(queueA->waitingPkt));
            startTimer(AEntity, TIMER_INCREMENT);
        }
        else
        {
            // Dequeue the packet, stop the timer, and transition to the next state
            stopTimer(AEntity);
            dequeuedPkt = dequeue(&queueA);
            free(dequeuedPkt);
            AState = WAIT_FOR_CALL_ONE_A;
            A_sendPkt(queueA, &AState);
        }
        break;
    case WAIT_FOR_ACK_ONE:
        if (isCorrupt(&receivedPacket) || (receivedPacket.acknum != 1))
        {
            // Resend the packet and restart the timer
            stopTimer(AEntity);
            tolayer3(AEntity, *(queueA->waitingPkt));
            startTimer(AEntity, TIMER_INCREMENT);
        }
        else
        {
            // Dequeue the packet, stop the timer, and transition to the next state
            stopTimer(AEntity);
            dequeuedPkt = dequeue(&queueA);
            free(dequeuedPkt);
            AState = WAIT_FOR_CALL_ZERO_A;
            A_sendPkt(queueA, &AState);
        }
        break;
    }
}
// Function called when A's timer expires
void A_timerinterrupt()
{
    // Resend the packet and restart the timer
    stopTimer(AEntity);
    tolayer3(AEntity, *(queueA->waitingPkt));
    startTimer(AEntity, TIMER_INCREMENT);
}

// Function to initialize A
void A_init()
{
    AState = WAIT_FOR_CALL_ZERO_A;
    seqNumber = 0;
    queueA = (struct pktQueue *)malloc(sizeof(struct pktQueue));
    if (queueA == NULL)
    {
        // Handle memory allocation failure
        perror("Error: Unable to allocate memory for queueA.");
        exit(EXIT_FAILURE);
    }
    queueA->waitingPkt = NULL;
    queueA->next = NULL;
}

// Function called when B receives a packet
void B_input(struct pkt receivedPacket)
{
    struct pkt *packetToSend = (struct pkt *)malloc(sizeof(struct pkt));
    if (packetToSend == NULL)
    {
        // Handle memory allocation failure
        perror("Error: Unable to allocate memory for packetToSend.");
        exit(EXIT_FAILURE);
    }

    // Check the current state of B and respond accordingly using a switch statement
    switch (BState)
    {
    case WAIT_FOR_CALL_ZERO_B:
        if (isCorrupt(&receivedPacket) || (receivedPacket.seqnum != 0))
        {
            constructACK(packetToSend, 1);
        }
        else
        {
            BState = WAIT_FOR_CALL_ONE_B;
            struct msg *receivedMsg = constructMsg(receivedPacket.payload);
            tolayer5(BEntity, *receivedMsg);
            free(receivedMsg);
            constructACK(packetToSend, 0);
        }
        break;
    case WAIT_FOR_CALL_ONE_B:
        if (isCorrupt(&receivedPacket) || (receivedPacket.seqnum != 1))
        {
            constructACK(packetToSend, 0);
        }
        else
        {
            BState = WAIT_FOR_CALL_ZERO_B;
            struct msg *receivedMsg = constructMsg(receivedPacket.payload);
            tolayer5(BEntity, *receivedMsg);
            free(receivedMsg);
            constructACK(packetToSend, 1);
        }
        break;
    default:
        // Handle invalid state
        fprintf(stderr, "Error: Invalid state in B_state.\n");
        exit(EXIT_FAILURE);
    }

    // Send an ACK packet
    tolayer3(BEntity, *packetToSend);
    free(packetToSend);
}

// Function called when B's timer expires (not used in this implementation)
void B_timerinterrupt()
{
}

// Function to initialize B
void B_init()
{
    BState = WAIT_FOR_CALL_ZERO_B;
}

