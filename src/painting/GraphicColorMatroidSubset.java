// Auxiliary class for the graphical matroid for the painting problem
// It associates to the set of edges the corresponding disjoint set structure, divided by colors

package painting;

import java.util.ArrayList;

public class GraphicColorMatroidSubset {
	
	// List with all edges
	ArrayList<int[]> edgeSet;
	
	// Number of colors
	int colors;
	
	// Disjoint set data structure
	// The c-th column corresponds to the set of edges colored with c
	// The (i,c)-th position is either -1 (if vertex i has no parents) or the parent of vertex i
	int[][] disjointSet;
	
	// Subsets obtained by removing one edge
	// The (i,j,c)-th position is either 'null' or the Subset obtained by removing the edge i <---> j colored with c
	GraphicColorMatroidSubset[][][] subsetsWithoutElement; 

	// Constructor
	// It is assumed the set edgeSet is in the matroid
	protected GraphicColorMatroidSubset(int n, ArrayList<int[]> edgeSet, int c) {
		this.edgeSet = edgeSet;
		this.colors = c;
		
		disjointSet = new int[n][c];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < c; j++) disjointSet[i][j] = -1;
		}
		for (int i = 0; i < edgeSet.size(); i++) setParent(edgeSet.get(i)[0], edgeSet.get(i)[1], edgeSet.get(i)[2]);
		
		subsetsWithoutElement = new GraphicColorMatroidSubset[n][n][c];
	}
	
	// Returns the parent of a vertex for a specific color
	private int getParent(int v, int c) {
		int i = v;
		while (disjointSet[i][c] != -1) i = disjointSet[i][c];
		return i;
	}
	
	// Set the parent of a vertex
	private void setParent(int v, int c, int parent) {
		int i = v;
		while (disjointSet[i][c] != -1) i = disjointSet[i][c];
		disjointSet[i][c] = parent;
		return;
	}
	
	// Returns 'true' if adding edge 'e' doesn't close a cycle, 'false' otherwise
	protected boolean addQ(int[] e) {
		return !(getParent(e[0], e[2]) == getParent(e[1], e[2]));
	}
	
	// Returns 'true' if adding edge 'e' and removing edge 'f' doesn't close a cycle, 'false' otherwise
	protected boolean addQ(int[] e, int[] f) {
		// Check if we have the subset without f
		if (subsetsWithoutElement[f[0]][f[1]][f[2]] == null) {
			// If we don't, create it
			
			// Create the edgeSet without f
			ArrayList<int[]> edgeSetMinusf = new ArrayList<int[]>(edgeSet);
			for (int i = 0; i < edgeSetMinusf.size(); i++) {
				if (edgeSetMinusf.get(i)[0] == f[0] && edgeSetMinusf.get(i)[1] == f[1] && edgeSetMinusf.get(i)[2] == f[2]) {
					edgeSetMinusf.remove(i);
					break;
				}
			}

			// Create the subset
			subsetsWithoutElement[f[0]][f[1]][f[2]] = new GraphicColorMatroidSubset(disjointSet.length, edgeSetMinusf, colors);				
		}
		
		return subsetsWithoutElement[f[0]][f[1]][f[2]].addQ(e);
	}
}
