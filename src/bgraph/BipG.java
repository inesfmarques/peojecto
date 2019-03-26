// Interface for directed bipartite graph

package bgraph;

import java.util.ArrayList;
import java.util.LinkedList;

public interface BipG {
	// Bipartite graph has two sets of vertices, {0, ... , n-1} (left set) and {n, ... , n+m-1} (right set)
	// Edges go from vertices of different sets
	// Only one edge per pair of vertices
	
	// Return size of left set
	int getL();
	
	// Return size of right set
	int getR();
	
	// Adds directed edge from vertex l to vertex r
	// Does nothing if l and r are in the same set
	void addEdge(int l, int r);
	
	// Adds directed edges from vertex l to vertices in array r_array
	// Does nothing when vertices are in the same set
	void addEdge(int l, int[] r_array);
	
	// Returns 'true' if there is an edge from l to r, 'false' otherwise
	boolean edgeQ(int l, int r);
	
	// Returns a list with the descendants of vertex v
	ArrayList<Integer> lovers(int v);
	
	// Finds a maximum bipartite matching using Edmonds-Karp (that is, Ford-Fulkerson and BFS to improve the matching)
	LinkedList<int[]> EdmondsKarp();
	
	// Finds a maximum bipartite matching using Ford-Fulkerson with DFS to improve the matching
	LinkedList<int[]> FordFulkerson();
}
