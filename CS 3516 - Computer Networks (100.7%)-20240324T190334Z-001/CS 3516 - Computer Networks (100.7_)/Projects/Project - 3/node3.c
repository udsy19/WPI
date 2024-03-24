#include <stdio.h>
#include "project3.h"

extern int TraceLevel;

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt3;
struct NeighborCosts   *neighbor3;

/* students to write the following two routines, and maybe some others */

void rtinit3() {
    int i, j;
    for (i = 0; i < MAX_NODES; i++) {
        for (j = 0; j < MAX_NODES; j++) {
            dt3.costs[i][j] = 9999;
        }
    }

    dt3.costs[0][0] = 7;
    dt3.costs[1][1] = 9999;
    dt3.costs[2][2] = 2;
    dt3.costs[3][3] = 0;

    neighbor3 = getNeighborCosts(3);

    struct RoutePacket packet;
    for (i = 0; i < MAX_NODES; i++) {
        packet.mincost[i] = 999; // default mincost
    }
    for (i = 0; i < MAX_NODES; i++) {
        for (j = 0; j < MAX_NODES; j++) {
            if (dt3.costs[i][j] < packet.mincost[i]) {
                packet.mincost[i] = dt3.costs[i][j];
            }
        }
    }

    packet.sourceid = 3;
    for (i = 0; i < neighbor3->NodesInNetwork; i++) {
        if (neighbor3->NodeCosts[i] != INFINITY && i != 3) {
            packet.destid = i;
            tolayer2(packet);
        }
    }
    printdt3(3, neighbor3, &dt3);
}

void rtupdate3(struct RoutePacket *rcvdpkt) {
    int src = rcvdpkt->sourceid;
    int i, j, k;

    for (i = 0; i < MAX_NODES; i++) {
        dt3.costs[i][src] = rcvdpkt->mincost[src] + rcvdpkt->mincost[i];
    }

    struct RoutePacket packet3;
    for (i = 0; i < MAX_NODES; i++) {
        for (k = 0; k < MAX_NODES; k++) {
            packet3.mincost[k] = 9999; // default mincost
        }
        for (j = 0; j < MAX_NODES; j++) {
            if (dt3.costs[i][j] < packet3.mincost[i]) {
                packet3.mincost[i] = dt3.costs[i][j];
            }
        }
    }

    int update_flag = 0;
    for (i = 0; i < MAX_NODES; i++) {
        if (packet3.mincost[i] < rcvdpkt->mincost[i]) {
            rcvdpkt->mincost[i] = packet3.mincost[i];
            update_flag = 1;
        }
    }

    if (update_flag) {
        rcvdpkt->sourceid = 3;
        for (i = 0; i < neighbor3->NodesInNetwork; i++) {
            if (neighbor3->NodeCosts[i] != INFINITY && i != 3) {
                rcvdpkt->destid = i;
                               tolayer2(*rcvdpkt);
            }
        }
    }
    printdt3(3, neighbor3, &dt3);
}

void printdt3(int MyNodeNumber, struct NeighborCosts *neighbor,
struct distance_table *dtptr) {
    int i, j;
    int TotalNodes = neighbor->NodesInNetwork; // Total nodes in network
    int NumberOfNeighbors = 0; // How many neighbors
    int Neighbors[MAX_NODES]; // Who are the neighbors
    // Determine our neighbors
    for (i = 0; i < TotalNodes; i++) {
        if ((neighbor->NodeCosts[i] != INFINITY) && i != MyNodeNumber) {
            Neighbors[NumberOfNeighbors] = i;
            NumberOfNeighbors++;
        }
    }
    // Print the header
    printf("                via     \n");
    printf("   D%d |", MyNodeNumber);
    for (i = 0; i < NumberOfNeighbors; i++)
        printf("     %d", Neighbors[i]);
    printf("\n");
    printf("  ----|-------------------------------\n");

    // For each node, print the cost by travelling thru each of our neighbors
    for (i = 0; i < TotalNodes; i++) {
        if (i != MyNodeNumber) {
            printf("dest %d|", i);
            for (j = 0; j < NumberOfNeighbors; j++) {
                printf("  %4d", dtptr->costs[i][Neighbors[j]]);
            }
            printf("\n");
        }
    }
    printf("\n");
}

