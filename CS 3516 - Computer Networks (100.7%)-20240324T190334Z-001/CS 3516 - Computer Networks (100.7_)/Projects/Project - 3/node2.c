#include <stdio.h>
#include "project3.h"

extern int TraceLevel;

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt2;
struct NeighborCosts *neighbor2;

/* students to write the following two routines, and maybe some others */

void rtinit2() {
    // Initialize the distance table for node 2
    for (int i = 0; i < MAX_NODES; i++) {
        for (int j = 0; j < MAX_NODES; j++) {
            dt2.costs[i][j] = 9999;
        }
    }

    dt2.costs[0][0] = 3;
    dt2.costs[1][1] = 1;
    dt2.costs[2][2] = 0;
    dt2.costs[3][3] = 2;

    // Send initial packets to neighbors
    struct RoutePacket packet;
    for (int i = 0; i < MAX_NODES; i++) {
        packet.mincost[i] = 999;
        for (int j = 0; j < MAX_NODES; j++) {
            if (dt2.costs[i][j] < packet.mincost[i]) {
                packet.mincost[i] = dt2.costs[i][j];
            }
        }
    }

    packet.sourceid = 2;
    packet.destid = 0;
    toLayer2(packet);
    packet.destid = 1;
    toLayer2(packet);
    packet.destid = 3;
    toLayer2(packet);
    printdt2(2, neighbor2, &dt2);
}


void rtupdate2(struct RoutePacket *rcvdpkt) {
    int src = rcvdpkt->sourceid;
    int update_flag = 0;

    for (int i = 0; i < MAX_NODES; i++) {
        int new_cost = rcvdpkt->mincost[i] + dt2.costs[src][src];
        if (new_cost < dt2.costs[i][src]) {
            dt2.costs[i][src] = new_cost;
            update_flag = 1;
        }
    }

    if (update_flag) {
        // Send updated packets to neighbors
        struct RoutePacket packet;
        for (int i = 0; i < MAX_NODES; i++) {
            packet.mincost[i] = 999;
            for (int j = 0; j < MAX_NODES; j++) {
                if (dt2.costs[i][j] < packet.mincost[i]) {
                    packet.mincost[i] = dt2.costs[i][j];
                }
            }
        }

        packet.sourceid = 2;
        packet.destid = 0;
        toLayer2(packet);
        packet.destid = 1;
        toLayer2(packet);
        packet.destid = 3;
        toLayer2(packet);
        printdt2(2, neighbor2, &dt2);
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
void printdt2( int MyNodeNumber, struct NeighborCosts *neighbor, 
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
}    // End of printdt2

