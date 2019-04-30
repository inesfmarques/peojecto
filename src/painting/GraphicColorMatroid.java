// Implements the graphic matroid for the painting problem
// A set of edges is in the matroid if edges with the same color don't form a cycle

// The edge v <---> w together with the color c is represented by int[] {v,w,c}, with v <= w

// The belongTo methods are implemented in the subset class

package painting;

import java.util.ArrayList;

import matroid.Matroid;
import graph.Graph;

public class GraphicColorMatroid implements Matroid<int[], GraphicColorMatroidSubset> {

	Graph G;
	int color;
	
	// Constructor
	public GraphicColorMatroid(Graph G, int color) {
		this.G = G;
		this.color = color;
	}
	
	// Returns a list with the elements in the matroid ground set
	public ArrayList<int[]> getGroundSet() {
		ArrayList<int[]> list = new ArrayList<>();
		ArrayList<Integer> lovers;
		
		for (int i = 0; i < G.getSize(); i++) {
			lovers = G.lovers(i);
			for (int j = 0; j < lovers.size(); j++) {
				if (lovers.get(j) < i) continue;
				for (int c = 0; c < color; c++) list.add(new int[] {i, lovers.get(j), c});
			}
		}
		
		return list;
	}

	// Input:
	// --> set: ArrayList of elements of the ground set
	// Output: 'true' if set is in the matroid, 'false' otherwise
	public boolean belongsTo(ArrayList<int[]> set) {
		// 'collisions' is where we store the possible collisions
		// For each color, we have an array of ArrayList. The i-th position contains the vertices j such that the edge (i,j)
		// would create a cycle
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[][] collisions = new ArrayList[color][G.getSize()];
		for (int c = 0; c < color; c++) {
			for (int n = 0; n < G.getSize(); n++) {
				collisions[c][n] = new ArrayList<Integer>();
				collisions[c][n].add(n);
			}
		}
		
		int[] currentEdge;
		ArrayList<Integer>[] currentCollisions;
		
		// Go through all edges in 'set'
		for (int i = 0; i < set.size(); i++) {
			currentEdge = set.get(i);
			currentCollisions = collisions[currentEdge[2]];
			
			// Check if there is collision
			if (currentCollisions[currentEdge[0]].contains(currentEdge[1])) return false;
			
			// If there isn't, update 'collisions'
			currentCollisions[currentEdge[0]].addAll(currentCollisions[currentEdge[1]]);
			currentCollisions[currentEdge[1]] = currentCollisions[currentEdge[0]];
		}
		
		return true;
	}
	
	// Converts an ArrayList into a Subset, to be used with the remaining methods
	public GraphicColorMatroidSubset arrayToSubset(ArrayList<int[]> set){
		return new GraphicColorMatroidSubset(G.getSize(), set, color);
	}

	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	public boolean belongsTo(GraphicColorMatroidSubset set, int[] x) {
		return set.addQ(x);
	}

	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// --> y: element in set
	// Output: 'true' if (set U {x}) \ {y} is in the matorid, 'false' otherwise
	public boolean belongsTo(GraphicColorMatroidSubset set, int[] x, int[] y) {
		return set.addQ(x, y); 
	}

}
