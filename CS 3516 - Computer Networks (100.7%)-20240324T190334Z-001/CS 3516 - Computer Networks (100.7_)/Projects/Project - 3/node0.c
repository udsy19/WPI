#include <stdio.h>
#include "project3.h"

extern int TraceLevel;

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt0;
struct NeighborCosts   *neighbor0;

/* students to write the following two routines, and maybe some others */
void printdt0(int MyNodeNumber, struct NeighborCosts *neighbor, struct distance_table *dtptr);

void rtinit0() {
    int i, j;

    /* Initialize the distance table with INFINITY */
    for (i = 0; i < MAX_NODES; i++){
        for (j = 0; j < MAX_NODES; j++)
            dt0.costs[i][j] = INFINITY;
    }

    /* Add the initial costs to the distance table */
    neighbor0 = getNeighborCosts(0);
    for (i = 0; i < neighbor0->NodesInNetwork; i++) {
        dt0.costs[i][i] = neighbor0->NodeCosts[i];
    }

    /* Create and send packets to neighbors */
    struct RoutePacket packet;
    for (i = 0; i < MAX_NODES; i++) {
        packet.mincost[i] = dt0.costs[i][i];
    }
    packet.sourceid = 0;
    for (i = 0; i < neighbor0->NodesInNetwork; i++) {
        if (i != 0 && neighbor0->NodeCosts[i] != INFINITY) {
            packet.destid = i;
            tolayer2(packet);
        }
    }

    printdt0(0, neighbor0, &dt0);
}

void rtupdate0( struct RoutePacket *rcvdpkt ) {
    int i, j;
    int src = rcvdpkt->sourceid;
    int updated = 0;

    /* Update distance table */
    for (i = 0; i < MAX_NODES; i++) {
        if (i != 0) {
            int new_cost = dt0.costs[src][src] + rcvdpkt->mincost[i];
            if (new_cost < dt0.costs[i][src]) {
                dt0.costs[i][src] = new_cost;
                updated = 1;
            }
        }
    }

    /* Send updated costs to neighbors if there's an update */
    if (updated) {
        struct RoutePacket packet;
        for (i = 0; i < MAX_NODES; i++) {
            packet.mincost[i] = INFINITY;
            for (j = 0; j < MAX_NODES; j++) {
                if (dt0.costs[i][j] < packet.mincost[i]) {
                    packet.mincost[i] = dt0.costs[i][j];
                }
            }
        }
        packet.sourceid = 0;
        for (i = 0; i < neighbor0->NodesInNetwork; i++) {
            if (i != 0 && neighbor0->NodeCosts[i] != INFINITY) {
                packet.destid = i;
                tolayer2(packet);
            }
        }

        printdt0(0, neighbor0, &dt0);
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
void printdt0( int MyNodeNumber, struct NeighborCosts *neighbor, 
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
}    // End of printdt0

