// Interface for directed weighted bipartite graph coupled with source and sink

package bwgraph;

import java.util.ArrayList;

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
	
	// Return visited
	void setVisited();
	
	// Returns weight of edge from i to j
	// Returns 0 if no such edge exists
	double getWeight(int l, int r);
	
	// Adds directed edge from vertex l to vertex r with weight w
	// Overrides if there is already an edge
	// Does nothing if l and r are in the same set
	void addEdge(int l, int r, double w);
	
	// Returns True if there is an edge from l to r, False otherwise
	boolean edgeQ(int l, int r);
	
	// Returns a list with the descendants of vertex v
	ArrayList<Integer> lovers(int v);
	
	// Receives a vertex v and an array of distances dist
	// Checks if there is a path from u to sink such that dist along that path never decreases
	// If there is, returns 'true' and inverts it. Otherwise, returns 'false'
	boolean invertPathBF(int v, double[] dist);
	
	// Finds a path from source to sink through Bellman-Ford to minimize the cost
	// Returns the list of distances to the source
	double[] BellmanFord();
	
	// Receives a vertex v, an array of distances dist and an array of function values h
	// Checks if there is a path from u to sink such that dist along that path never decreases
	// If there is, returns 'true' and inverts it. Otherwise, returns 'false'
	boolean invertPathDijkstra(int v, double[] dist, double[] h);
	
	// Finds a path from source to sink through Dijkstra to minimize the cost
	// Returns the list of distances to the source
	double[] Dijkstra(double[] h);
	
	// Finds a path from source to sink through Dijkstra to minimize the cost using Priority Queue
	// Returns the list of distances to the source
	double[] DijkstraPQ(double[] h);
		
}
