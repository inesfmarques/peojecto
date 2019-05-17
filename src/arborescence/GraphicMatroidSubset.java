// Auxiliary class for the graphical matroid of the directed spanning tree problem
// It associates to the set of edges the corresponding disjoint set structure

package arborescence;

import java.util.ArrayList;

public class GraphicMatroidSubset{
	
	// List with all edges
	ArrayList<int[]> edgeSet;
	
	// Disjoint set data structure
	// The i-th position is either -1 (if vertex i has no parents) or the parent of vertex i
	int[] disjointSet;
	
	// Subsets obtained by removing one edge
	// The (i,j)-th position is either 'null' or the Subset obtained by removing the edge i ---> j, with i <= j
	GraphicMatroidSubset[][] subsetsWithoutElement; 
	
	// Constructor
	// It is assumed the set edgeSet is in the matroid
	protected GraphicMatroidSubset(int n, ArrayList<int[]> edgeSet) {
		this.edgeSet = new ArrayList<int[]>(edgeSet);
		
		disjointSet = new int[n];
		for (int i = 0; i < n; i++) disjointSet[i] = -1;
		for (int i = 0; i < edgeSet.size(); i++) setParent(edgeSet.get(i)[0], edgeSet.get(i)[1]);
		
		subsetsWithoutElement = new GraphicMatroidSubset[n][n];
	}
	
	// Returns the parent of a vertex
	private int getParent(int v) {
		int i = v;
		while (disjointSet[i] != -1) i = disjointSet[i];
		return i;
	}
	
	// Set the parent of a vertex
	private void setParent(int v, int parent) {
		int i = v;
		while (disjointSet[i] != -1) i = disjointSet[i];
		disjointSet[i] = parent;
		return;
	}
	
	// Returns 'true' if adding edge 'e' doesn't close a cycle, 'false' otherwise
	protected boolean addQ(int[] e) {
		return !(getParent(e[0]) == getParent(e[1]));
	}
	
	// Returns 'true' if adding edge 'e' and removing edge 'f' doesn't close a cycle, 'false' otherwise
	protected boolean addQ(int[] e, int[] f) {
		
		// Divide in cases according to f[0] <= f[1] or not
		if (f[0] <= f[1]) {
			// Check if we have the subset without f
			if (subsetsWithoutElement[f[0]][f[1]] == null) {
				// If we don't, create it
				
				// Create the edgeSet without f
				ArrayList<int[]> edgeSetMinusf = new ArrayList<int[]>(edgeSet);
				for (int i = 0; i < edgeSetMinusf.size(); i++) {
					if (edgeSetMinusf.get(i)[0] == f[0] && edgeSetMinusf.get(i)[1] == f[1]) {
						edgeSetMinusf.remove(i);
						break;
					}
				}

				// Create the subset
				subsetsWithoutElement[f[0]][f[1]] = new GraphicMatroidSubset(disjointSet.length, edgeSetMinusf);				
			}
			
			return subsetsWithoutElement[f[0]][f[1]].addQ(e);
		}
		
		else {
			// Check if we have the subset without f
			if (subsetsWithoutElement[f[1]][f[0]] == null) {
				// If we don't, create it
				
				// Create the edgeSet without f
				ArrayList<int[]> edgeSetMinusf = new ArrayList<int[]>(edgeSet);
				for (int i = 0; i < edgeSetMinusf.size(); i++) {
					if (edgeSetMinusf.get(i)[0] == f[0] && edgeSetMinusf.get(i)[1] == f[1]) {
						edgeSetMinusf.remove(i);
						break;
					}
				}

				// Create the subset
				subsetsWithoutElement[f[1]][f[0]] = new GraphicMatroidSubset(disjointSet.length, edgeSetMinusf);				
			}
			
			return subsetsWithoutElement[f[1]][f[0]].addQ(e);			
		}
	}
}
