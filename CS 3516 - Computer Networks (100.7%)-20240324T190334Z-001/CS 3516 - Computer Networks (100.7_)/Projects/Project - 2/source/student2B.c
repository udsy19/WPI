// Author: Udaya Vijay Anand

#include "project2.h"
#include "student2.h"
#include <stdlib.h>
#include <stdio.h>

// Function to construct an ACK packet
void constructACK(struct pkt* packet, int acknum) {
    packet->acknum = acknum; // Set the ACK number
    packet->seqnum = acknum; // Set the sequence number (same as ACK number in this implementation)
    copyString(packet->payload, "01234567890123456789", MESSAGE_LENGTH); // Fill payload with dummy data
    packet->checksum = calculateChecksum(packet->payload, packet->acknum, packet->seqnum); // Calculate and set the checksum
}

// Function to construct a message struct from the given payload data
struct msg* constructMsg(char* data) {
    struct msg* messagePtr = (struct msg*) malloc(sizeof(struct msg)); // Allocate memory for the message struct
    copyString(messagePtr->data, data, MESSAGE_LENGTH); // Copy the payload data into the message struct
    return messagePtr; // Return the constructed message struct
}
