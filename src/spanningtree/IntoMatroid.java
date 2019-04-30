// Implements the into matroid of the directed spanning tree problem
// A set of edges is in the matroid if:
// --> No two edges go into the same vertex
// --> No edge goes into a forbidden vertex (that is fixed)

// An edge from u to v is represented by an int[] {u,v}

// The belongTo methods are implemented in the subset class

package spanningtree;

import java.util.ArrayList;

import matroid.Matroid;
import graph.OrientedGraph;

public class IntoMatroid implements Matroid<int[], IntoMatroidSubset> {
	
	OrientedGraph G;
	int forbidden;
	
	// Constructor
	public IntoMatroid(OrientedGraph G, int v) {
		this.G = G;
		this.forbidden = v;
	}

	// Returns a list with the elements in the matroid ground set
	public ArrayList<int[]> getGroundSet() {
		// The ground set is the set of edges of G
		
		ArrayList<int[]> list = new ArrayList<int[]>();
		ArrayList<Integer> lovers;
		for (int i = 0; i < G.getSize(); i++) {
			lovers = G.lovers(i);
			for (int j = 0; j < lovers.size(); j++) list.add(new int[] {i, lovers.get(j)});
		}

		return list;
	}
	
	// Input:
	// --> set: ArrayList of elements of the ground set
	// Output: 'true' if set is in the matroid, 'false' otherwise
	public boolean belongsTo(ArrayList<int[]> set) {
		// Go through edges and check if there are conflicts in the terminations
		
		// Array with the terminations of the edges
		// The i-th entry is 'true' if there is an edge that goes into the vertex i
		boolean[] terminations = new boolean[G.getSize()];
		
		// Go through all edges
		for (int i = 0; i < set.size(); i++) {
			if (set.get(i)[1] == forbidden) return false;
			if (terminations[set.get(i)[1]]) return false;
			terminations[set.get(i)[1]] = true;
		}
		
		return true;
	}

	// Converts an ArrayList into a Subset, to be used with the remaining methods
	// Input:
	// --> set: ArrayList of elements that is in the matroid
	public IntoMatroidSubset arrayToSubset(ArrayList<int[]> set) {
		return new IntoMatroidSubset(G.getSize(), set, forbidden);
	}

	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	public boolean belongsTo(IntoMatroidSubset set, int[] x) {
		return set.addQ(x);
	}

	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set
	// --> y: element in set
	// Output: 'true' if (set U {x}) \ {y} is in the matorid, 'false' otherwise
	public boolean belongsTo(IntoMatroidSubset set, int[] x, int[] y) {
		return set.addQ(x, y);
	}

}
