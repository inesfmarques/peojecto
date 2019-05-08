// Auxiliary class for the graphical matroid of the directed spanning tree problem
// It associates to the set of edges a history of which edges have been painted

package painting;

import java.util.ArrayList;

public class ColorMatroidSubset {
	
	// List with all edges
	ArrayList<int[]> edgeSet;
	
	// Matrix that stores the painted edges
	// The (i,j)-th entry is 'true' if the edge i <---> j is in edgeSet with some color
	boolean[][] history;
	
	// Constructor
	// It is assumed the set edgeSet is in the matroid
	protected ColorMatroidSubset(int n, ArrayList<int[]> edgeSet) {
		this.edgeSet = edgeSet;
		
		this.history = new boolean[n][n];
		for (int i = 0; i < edgeSet.size(); i++) history[edgeSet.get(i)[0]][edgeSet.get(i)[1]] = true;
	}
	
	// Returns 'true' if adding edge 'e' doesn't create conflicts in the terminations, 'false' otherwise
	protected boolean addQ(int[] e) {
		// Check if there is an edge with the same endpoints
		return !history[e[0]][e[1]];
	}
	
	// Returns 'true' if adding edge 'e' and removing edge 'f' doesn't create conflicts in the terminations, 'false' otherwise
	// We know adding edge 'e' creates conflicts
	protected boolean addQ(int[] e, int[] f) {
		return (e[0] == f[0] && e[1] == f[1]);
	}

}
