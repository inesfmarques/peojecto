// Interface for directed weighted bipartite graph coupled with source and sink

package bwgraph;

import java.util.ArrayList;
import java.util.LinkedList;

public interface BipWGSS {
	// Bipartite graph has two sets of vertices, {0, ... , n-1} (left set) and {n, ... , n+m-1} (right set)
	// Edges go from vertices of different sets
	// Only one edge per pair of vertices
	// Source is vertex n+m and starts with an edge of weight 0 to vertices {0, ... , n-1}
	// Sink is vertex n+m+1 and starts with an edge of weight 0 from vertices {n, ... , n+m-1}
	
	// Return size of left set
	int getL();
	
	// Return size of right set
	int getR();
	
	// Return id of source
	int getSource();
	
	// Return id of sink
	int getSink();
	
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
	
	// Finds a path from source to sink through Bellman-Ford to minimize the cost
	// Returns the list of vertex labels that form the path
	// Returns an empty list if no such path exists
	LinkedList<Integer> BellmanFord();
			
	// Receives a list of vertices that form a path from source to sink and inverts it
	// path is destroyed into an empty list
	void invertPath(LinkedList<Integer> path);
}
