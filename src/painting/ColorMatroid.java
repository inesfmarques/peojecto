// Implements the partition matroid for the painting problem
// A set of pairs (e, c) of an edge and a color is in the matroid if no two pairs share an edge

// The edge v <---> w together with the color c is represented by int[] {v,w,c}, with v <= w

// The belongTo methods are implemented in the subset class

package painting;

import java.util.ArrayList;
import java.util.Arrays;

import graph.Graph;
import matroid.Matroid;

public class ColorMatroid implements Matroid<int[], ColorMatroidSubset> {
	
	Graph G;
	int color;
	
	// Constructor
	public ColorMatroid(Graph G, int color) {
		this.G = G;
		this.color = color;
	}
	
	// Returns a list with the elements in the matroid ground set
	public ArrayList<int[]> getGroundSet() {
		// Build the list with all edges and colors
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
		// Go through all elements, check if any are the same edge
		boolean[][] history = new boolean[G.getSize()][G.getSize()];
		for (int i = 0; i < set.size(); i++) {
			if (history[set.get(i)[0]][set.get(i)[1]]) return false;
			history[set.get(i)[0]][set.get(i)[1]] = true;
		}
		return true;
	}
	
	// Converts an ArrayList into a Subset, to be used with the remaining methods
	public ColorMatroidSubset arrayToSubset(ArrayList<int[]> set){
		return new ColorMatroidSubset(G.getSize(), set);
	}

	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	public boolean belongsTo(ColorMatroidSubset set, int[] x) {
		return set.addQ(x);
	}

	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set such that (set U {x}) is not in the matroid
	// --> y: element in set
	// Output: 'true' if (set U {x} \ {y}) is in the matorid, 'false' otherwise
	public boolean belongsTo(ColorMatroidSubset set, int[] x, int[] y) {
		return set.addQ(x, y);
	}

}
