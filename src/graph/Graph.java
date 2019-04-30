// Implementation of non-oriented graph with adjacency lists

package graph;

import java.util.ArrayList;

public class Graph {
	int n; // Number of vertices
	
	// Array of adjacency lists
	// The i-th position is a list with the neighbors of vertex i
	ArrayList<Integer>[] adjacencyList;
	
	// Constructor
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		this.adjacencyList = new ArrayList[n];
		for (int i = 0; i < n; i++) adjacencyList[i] = new ArrayList<Integer>();
	}
	
	// Return number of vertices
	public int getSize() {
		return n;
	}

	// Add edge from vertex l to vertex r
	public void addEdge(int l, int r) {
		if (!adjacencyList[l].contains(r)) {
			adjacencyList[l].add(r);
			adjacencyList[r].add(l);
		}
		return;
	}

	// Adds edges from vertex l to vertices in array rArray
	public void addEdge(int l, int[] rArray) {
		for (int i = 0; i < rArray.length; i++) addEdge(l, rArray[i]);
		return;
	}

	// Returns 'true' if there is an edge from l to r, 'false' otherwise
	public boolean edgeQ(int l, int r) {
		return adjacencyList[l].contains(r);
	}

	// Returns a list with the neighbors of vertex v
	//
	// [WARNING - SHALLOW COPY] Changing the output of this method may change the graph!
	public ArrayList<Integer> lovers(int v) {
		return adjacencyList[v];
	}
	
	// toString method
	public String toString() {
		String string = "Number of vertices: " + n + "\nEdges:\n";

		// Add edges
		for (int i = 0; i < n; i++) {
			if (adjacencyList[i].isEmpty()) continue;
			
			string += i + " <--->";
			for (int j = 0; j < adjacencyList[i].size(); j++) {
				if (j == 0) string += " " + adjacencyList[i].get(j);
				else string += ", " + adjacencyList[i].get(j);
			}
			string += "\n";
		}
		
		return string;
	}
}
