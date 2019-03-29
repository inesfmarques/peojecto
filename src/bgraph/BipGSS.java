// Interface for directed bipartite graph coupled with source and sink

package bgraph;

import java.util.ArrayList;
import java.util.LinkedList;

public interface BipGSS {
	// Bipartite graph has two sets of vertices, {0, ... , n-1} (left set) and {n, ... , n+m-1} (right set)
	// Edges go from vertices of different sets
	// Only one edge per pair of vertices
	// Source is vertex n+m and starts with an edge to vertices {0, ... , n-1}
	// Sink is vertex n+m+1 and starts with an edge from vertices {n, ... , n+m-1}
	
	// Return size of left set
	int getL();
	
	// Return size of right set
	int getR();
	
	// Return id of source
	int getSource();
	
	// Return id of sink
	int getSink();
	
	// Returns 'true' if there is an edge from l to r, 'false' otherwise
	boolean edgeQ(int l, int r);
	
	// Returns a list with the descendants of vertex v
	ArrayList<Integer> lovers(int v);
	
	// Finds a path from source to sink through BFS
	// Returns the list of vertex labels that form the path
	// Returns an empty list if no such path exists
	LinkedList<Integer> bfs();
	
	// Finds a path from source to sink through DFS
	// Returns the list of vertex labels that form the path
	// Returns an empty list if no such path exists
	LinkedList<Integer> dfs();
	
	// Receives a list of vertices that form a path from source to sink and inverts it
	// path is destroyed into an empty list
	void invertPath(LinkedList<Integer> path);
}
