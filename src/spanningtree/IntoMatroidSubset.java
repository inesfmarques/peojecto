// Auxiliary class for the into matroid of the directed spanning tree problem
// It associates to the set of edges the vertices with edges into them

package spanningtree;

import java.util.ArrayList;

public class IntoMatroidSubset {
	
	// List with all edges
	ArrayList<int[]> edgeSet;
	
	// Vertex such that no edges can go into it
	int forbidden;
	
	// Array with the terminations of the edges
	// The i-th entry is 'true' if there is an edge that goes into the vertex i
	boolean[] terminations;
	
	// Constructor
	// It is assumed the set edgeSet is in the matroid
	// v is the forbidden vertex
	protected IntoMatroidSubset(int n, ArrayList<int[]> edgeSet, int v) {
		this.edgeSet = edgeSet;
		
		this.terminations = new boolean[n];
		for (int i = 0; i < edgeSet.size(); i++) terminations[edgeSet.get(i)[1]] = true;
		
		this.forbidden = v;
	}
	
	// Returns 'true' if adding edge 'e' doesn't create conflicts in the terminations, 'false' otherwise
	protected boolean addQ(int[] e) {
		if (e[1] == forbidden) return false;
		else return !terminations[e[1]];
	}
	
	// Returns 'true' if adding edge 'e' and removing edge 'f' doesn't create conflicts in the terminations, 'false' otherwise
	protected boolean addQ(int[] e, int[] f) {
		// If e and f have the same termination, then we return 'true'
		if (e[1] == f[1]) return true;
		// Otherwise, f is not relevant to check if there are conflicts in the terminations
		else if (e[1] == forbidden) return false;
		else return !terminations[e[1]];
	}
}
