// Interface for directed weighted bipartite graph

package bwgraph;

import java.util.ArrayList;

public interface BipWG {
	// Bipartite graph has two sets of vertices, {0, ... , n-1} (left set) and {n, ... , n+m-1} (right set)
	// Edges have weights and go from vertices of different sets
	// Only one edge per pair of vertices
	
	// Return size of left set
	int getL();
	
	// Return size of right set
	int getR();
	
	// Returns weight of edge from i to j
	// Returns 0 if no such edge exists
	int getWeight(int l, int r);
	
	// Adds directed edge from vertex l to vertex r with weight w
	// Overrides if there is already an edge
	// Does nothing if l and r are in the same set
	void addEdge(int l, int r, int w);
	
	// Returns True if there is an edge from l to r, False otherwise
	boolean edgeQ(int l, int r);
	
	// Returns a list with the descendants of vertex v
	ArrayList<Integer> lovers(int v);
	
	// Finds a maximum bipartite matching using Ford-Fulkerson with Bellman-Ford to find the augmenting paths
	ArrayList<int[]> MatchingBellmanFord();
	
	// Finds a maximum bipartite matching using Ford-Fulkerson with Dijkstra to find the augmenting paths
	ArrayList<int[]> MatchingDijkstra();
	
	// Finds a maximum bipartite matching using Ford-Fulkerson with Dijkstra using Priority Queue to find the augmenting paths
	ArrayList<int[]> MatchingDijkstraPQ();
}
