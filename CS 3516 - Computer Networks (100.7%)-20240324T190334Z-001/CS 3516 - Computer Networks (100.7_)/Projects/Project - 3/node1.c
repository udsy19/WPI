#include <stdio.h>
#include "project3.h"

extern int TraceLevel;

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt1;
struct NeighborCosts   *neighbor1;

/* students to write the following two routines, and maybe some others */
void printdt0(int MyNodeNumber, struct NeighborCosts *neighbor, struct distance_table *dtptr);


void rtinit1() {
    int i, j;
    for (i = 0; i < MAX_NODES; i++) {
        for (j = 0; j < MAX_NODES; j++)
            dt1.costs[i][j] = 9999;
    }

    dt1.costs[0][0] = 1;
    dt1.costs[1][1] = 0;
    dt1.costs[2][2] = 1;
    dt1.costs[3][3] = 9999;

    struct RoutePacket packet;
    for (i = 0; i < MAX_NODES; i++) {
        packet.mincost[i] = 999; // default mincost
    }
    for (i = 0; i < MAX_NODES; i++) {
        for (j = 0; j < MAX_NODES; j++) {
            if (dt1.costs[i][j] < packet.mincost[i]) {
                packet.mincost[i] = dt1.costs[i][j];
            }
        }
    }

    packet.sourceid = 1;
    packet.destid = 0;
    tolayer2(packet);
    packet.destid = 2;
    tolayer2(packet);
    packet.destid = 3;
    tolayer2(packet);
    printdt1(1, neighbor1, &dt1);
}

void rtupdate1(struct RoutePacket *rcvdpkt) {
    int i, j, k;
    int src = rcvdpkt->sourceid;
    for (i = 0; i < MAX_NODES; i++) {
        dt1.costs[i][src] = rcvdpkt->mincost[src] + rcvdpkt->mincost[i];
    }

    struct RoutePacket packet1;
    for (i = 0; i < MAX_NODES; i++) {
        for (j = 0; j < MAX_NODES; j++) {
            packet1.mincost[j] = 9999; // default mincost
        }
        for (j = 0; j < MAX_NODES; j++) {
            if (dt1.costs[i][j] < packet1.mincost[i]) {
                packet1.mincost[i] = dt1.costs[i][j];
            }
        }
    }

    k = 0;
    for (i = 0; i < MAX_NODES; i++) {
        if (packet1.mincost[i] < rcvdpkt->mincost[i]) {
            rcvdpkt->mincost[i] = packet1.mincost[i];
            k = 1;
        }
    }

    if (k == 1) {
        rcvdpkt->sourceid = 1;
        rcvdpkt->destid = 0;
        tolayer2(*rcvdpkt);
        rcvdpkt->destid = 2;
        tolayer2(*rcvdpkt);
        rcvdpkt->destid = 3;
        tolayer2(*rcvdpkt);
        printdt1(1, neighbor1, &dt1);
    }
}


/////////////////////////////////////////////////////////////////////
//  printdt
//  This routine is being supplied to you.  It is the same code in
//  each node and is tailored based on the input arguments.
//  Required arguments:
//  MyNodeNumber:  This routine assumes that you know your node
//                 number and supply it when making this call.
//  struct NeighborCosts *neighbor:  A pointer to the structure 
//                 that's supplied via a call to getNeighborCosts().
//                 It tells this print routine the configuration
//                 of nodes surrounding the node we're working on.
//  struct distance_table *dtptr: This is the running record of the
//                 current costs as seen by this node.  It is 
//                 constantly updated as the node gets new
//                 messages from other nodes.
/////////////////////////////////////////////////////////////////////
void printdt1( int MyNodeNumber, struct NeighborCosts *neighbor, 
		struct distance_table *dtptr ) {
    int       i, j;
    int       TotalNodes = neighbor->NodesInNetwork;     // Total nodes in network
    int       NumberOfNeighbors = 0;                     // How many neighbors
    int       Neighbors[MAX_NODES];                      // Who are the neighbors

    // Determine our neighbors 
    for ( i = 0; i < TotalNodes; i++ )  {
        if (( neighbor->NodeCosts[i] != INFINITY ) && i != MyNodeNumber )  {
            Neighbors[NumberOfNeighbors] = i;
            NumberOfNeighbors++;
        }
    }
    // Print the header
    printf("                via     \n");
    printf("   D%d |", MyNodeNumber );
    for ( i = 0; i < NumberOfNeighbors; i++ )
        printf("     %d", Neighbors[i]);
    printf("\n");
    printf("  ----|-------------------------------\n");

    // For each node, print the cost by travelling thru each of our neighbors
    for ( i = 0; i < TotalNodes; i++ )   {
        if ( i != MyNodeNumber )  {
            printf("dest %d|", i );
            for ( j = 0; j < NumberOfNeighbors; j++ )  {
                    printf( "  %4d", dtptr->costs[i][Neighbors[j]] );
            }
            printf("\n");
        }
    }
    printf("\n");
}    // End of printdt1

