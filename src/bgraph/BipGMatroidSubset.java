// Auxiliary class for the left and right matroids for finding a maximum matching
// It associates to the set of edges an array that marks the vertices that already have an edge

package bgraph;

import java.util.ArrayList;

public class BipGMatroidSubset {
	
	// Number of vertices in the left set of the graph
	int L;
	
	// List with all edges
	ArrayList<int[]> edgeSet;
	
	// Boolean that sets the side
	// 'true' if it's the left-side matroid, 'false' if it's the right-side matroid
	boolean side;
	
	// Stores the vertices that have an edge
	// The i-th entry is 'true' if:
	// --> [For left-side matroid] vetex i has an edge
	// --> [For right-side matroid] vertex (i+L) has an edge 
	boolean[] vertexMarks;
	
	// Constructor
	// It is assumed the set edgeSet is in the matroid
	BipGMatroidSubset(boolean side, int L, int R, ArrayList<int[]> edgeSet){
		this.L = L;
		
		this.edgeSet = edgeSet;
		
		this.side = side;
		
		if (side) {
			vertexMarks = new boolean[L];
			for (int i = 0; i < edgeSet.size(); i++) vertexMarks[edgeSet.get(i)[0]] = true;
		}
		else {
			vertexMarks = new boolean[R];
			for (int i = 0; i < edgeSet.size(); i++) vertexMarks[edgeSet.get(i)[1] - L] = true;
		}
	}
	
	// Returns 'true' if adding edge 'e' doesn't create conflicts in the terminations, 'false' otherwise
	protected boolean addQ(int[] e) {
		// Check if there is an edge with the same endpoint
		if (side) return !vertexMarks[e[0]];
		else return !vertexMarks[e[1] - L];
	}

	// Returns 'true' if adding edge 'e' and removing edge 'f' doesn't create conflicts in the terminations, 'false' otherwise
	protected boolean addQ(int[] e, int[] f) {
		// Different cases according to 'side'
		if (side) {
			// If e and f have the same endpoint, return 'true'
			if (e[0] == f[0]) return true;
			
			// Otherwise, removing f from the edge set is irrelevant
			else return !vertexMarks[e[0]];
		}
		
		else {
			// If e and f have the same endpoint, return 'true'
			if (e[1] == f[1]) return true;
			
			// Otherwise, removing f from the edge set is irrelevant
			else return !vertexMarks[e[1] - L];
		}
	}
}
