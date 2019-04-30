// Interface for directed bipartite graph coupled with source and sink

package bgraph;

import java.util.ArrayList;

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
	
	// Adds directed edge from vertex l to vertex r
	// Does nothing if l and r are in the same set
	void addEdge(int l, int r);	
	
	// Returns 'true' if there is an edge from l to r, 'false' otherwise
	boolean edgeQ(int l, int r);
	
	// Returns a list with the descendants of vertex v
	//
	// [WARNING - SHALLOW COPY] Changing the output of this method may change the graph!
	ArrayList<Integer> lovers(int v);
	
	// Finds a path from source to sink through BFS
	// Returns the list of vertex labels that form the path
	// Returns an empty list if no such path exists
	ArrayList<Integer> bfs();
	
	// Finds a path from source to sink through DFS
	// Returns the list of vertex labels that form the path
	// Returns an empty list if no such path exists
	ArrayList<Integer> dfs();
	
	// Receives a list of vertices that form a path from source to sink and inverts it
	void invertPath(ArrayList<Integer> path);
	
	// Auxiliary method for Hopcroft-Karp
	// Returns an array with L+R+2 entries containing the distances of each vertex to the source
	// The i-th entry is dist(i, source)
	// If i cannot be reached from source or is bigger than dist(sink, source), the entry is -1
	int[] HopcroftKarpBFS();
	
	// Auxiliary method for Hopcroft-Karp
	// Receives a vertex v and an array of distances dist
	// Checks if there is a path from u to sink such that dist along that path never decreases
	// If there is, returns 'true' and inverts it. Otherwise, returns 'false'
	// 
	// [WARNING] dist may be changed in the process
	boolean HopcroftKarpDFS(int v, int[] dist);
}
