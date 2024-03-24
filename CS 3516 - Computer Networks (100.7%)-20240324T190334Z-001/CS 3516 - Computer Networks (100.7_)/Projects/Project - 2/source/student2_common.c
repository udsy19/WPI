// Author: Udaya Vijay Anand

#include "project2.h"
#include "student2.h"
#include <stdlib.h>
#include <stdio.h>

// Copies a string of a given length from source to destination
void copyString(char* destination, char* source, int length) {
    for (int i = 0; i < MESSAGE_LENGTH; i++)
        destination[i] = source[i];
}

// Constructs a packet with the given parameters and returns a pointer to it
struct pkt* constructPkt(int seqnum,int acknum,int checksum,char* payload) {
    // Allocate memory for the packet
    struct pkt* packetPtr = (struct pkt*) malloc(sizeof(struct pkt));
    
    // Set the packet fields
    packetPtr->seqnum = seqnum;
    packetPtr->acknum = acknum;
    packetPtr->checksum = checksum;
    
    // Copy the payload string into the packet
    copyString(packetPtr->payload, payload, MESSAGE_LENGTH);
    
    // Return the pointer to the packet
    return packetPtr;
}

// Calculates the checksum for a packet using its payload, acknum, and seqnum
int calculateChecksum(char* vdata,int acknum, int seqnum) {
    int i, checksum = 0;

    // Add the ASCII values of the payload characters multiplied by their index
    for(i = 0; i < MESSAGE_LENGTH; i++){
        checksum += (int)(vdata[i]) * i;
    }

    // Add the weighted acknum and seqnum values to the checksum
    checksum += acknum * 21;
    checksum += seqnum * 22;
    
    // Return the calculated checksum
    return checksum;
}

// Determines if a packet is corrupt by comparing its stored checksum with a newly calculated one
int isCorrupt(struct pkt* packet) {
    // If the stored checksum matches the calculated checksum, the packet is not corrupt
    if (calculateChecksum(packet->payload, packet->acknum, packet->seqnum) == packet->checksum) {
        return 0;
    }
    
    // If the checksums do not match, the packet is corrupt
    return 1;
}
