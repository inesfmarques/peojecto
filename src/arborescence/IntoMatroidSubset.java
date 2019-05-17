// Auxiliary class for the into matroid of the directed spanning tree problem
// It associates to the set of edges the vertices with edges into them

package arborescence;

import java.util.ArrayList;

public class IntoMatroidSubset {
	
	// List with all edges
	ArrayList<int[]> edgeSet;
	
	// Array with the terminations of the edges
	// The i-th entry is 'true' if there is an edge that goes into the vertex i
	boolean[] terminations;
	
	// Constructor
	// It is assumed the set edgeSet is in the matroid
	// v is the forbidden vertex
	protected IntoMatroidSubset(int n, ArrayList<int[]> edgeSet) {
		this.edgeSet = edgeSet;
		
		this.terminations = new boolean[n];
		for (int i = 0; i < edgeSet.size(); i++) terminations[edgeSet.get(i)[1]] = true;
	}
	
	// Returns 'true' if adding edge 'e' doesn't create conflicts in the terminations, 'false' otherwise
	protected boolean addQ(int[] e) {
		return !terminations[e[1]];
	}
	
	// Returns 'true' if adding edge 'e' and removing edge 'f' doesn't create conflicts in the terminations, 'false' otherwise
	// We know adding edge 'e' creates conflicts
	protected boolean addQ(int[] e, int[] f) {
		return e[1] == f[1];
	}
}
