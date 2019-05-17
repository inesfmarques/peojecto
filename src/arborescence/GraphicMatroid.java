// Implements the graphic matroid for the directed spanning
// A set of edges is in the matroid if they don't form a cycle (not counting orientations)

// An edge from u to v is represented by an int[] {u,v}

// The belongTo methods are implemented in the subset class

package arborescence;

import java.util.ArrayList;

import graph.OrientedGraph;
import matroid.Matroid;

public class GraphicMatroid implements Matroid<int[], GraphicMatroidSubset> {

	OrientedGraph G;
	
	// Constructor
	public GraphicMatroid(OrientedGraph G) {
		this.G = G;
	}
	
	// Returns a list with the elements in the matroid ground set
	public ArrayList<int[]> getGroundSet() {
		// The ground set is the set of edges
		
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
		// 'collisions' is where we store the possible collisions
		// The i-th position contains the vertices j such that the edge (i,j) would create a cycle
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] collisions = new ArrayList[G.getSize()];
		for (int n = 0; n < G.getSize(); n++) {
			collisions[n] = new ArrayList<Integer>();
			collisions[n].add(n);
		}
		
		int[] currentEdge;
		
		// Go through all edges in 'set'
		for (int i = 0; i < set.size(); i++) {
			currentEdge = set.get(i);
			
			// Check if there is collision
			if (collisions[currentEdge[0]].contains(currentEdge[1])) return false;
			
			// If there isn't, update 'collisions'
			// Recall we ignore the directions
			collisions[currentEdge[0]].addAll(collisions[currentEdge[1]]);
			collisions[currentEdge[1]] = collisions[currentEdge[0]];
		}

		return true;
	}
	
	// Converts an ArrayList into a Subset, to be used with the remaining methods
	public GraphicMatroidSubset arrayToSubset(ArrayList<int[]> set) {
		return new GraphicMatroidSubset(G.getSize(), set);
	}

	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	public boolean belongsTo(GraphicMatroidSubset set, int[] x) {
		return set.addQ(x);
	}

	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set such that (set U {x}) is not in the matroid
	// --> y: element in set
	// Output: 'true' if (set U {x} \ {y}) is in the matorid, 'false' otherwise
	public boolean belongsTo(GraphicMatroidSubset set, int[] x, int[] y) {
		return set.addQ(x, y);
	}

}
