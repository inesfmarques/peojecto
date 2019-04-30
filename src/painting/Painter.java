// Class that solves the the coloring problem

package painting;

import java.util.ArrayList;

import graph.Graph;
import matroid.Matroid;
import matroid.MatroidIntersector;

public class Painter {
	
	// Matroid intersector used to solve the problem
	MatroidIntersector<int[], ColorMatroidSubset, GraphicColorMatroidSubset> intersector =
			new MatroidIntersector<int[], ColorMatroidSubset, GraphicColorMatroidSubset>();
	
	// Returns the best way of coloring a graph with the given number of colors
	// Output: the i-th position is an ArrayList with the edges are colored with the color i
	ArrayList<int[]>[] paint(Graph G, int color){
		// Create the matroids
		Matroid<int[], ColorMatroidSubset> matroid1 = new ColorMatroid(G, color);
		Matroid<int[], GraphicColorMatroidSubset> matroid2 = new GraphicColorMatroid(G, color);
		
		// Get the solution
		ArrayList<int[]> solution = intersector.intersection(matroid1, matroid2);
		
		// Convert the solution into the described format
		@SuppressWarnings("unchecked")
		ArrayList<int[]>[] coloring = new ArrayList[color];
		for (int i = 0; i < color; i++) coloring[i] = new ArrayList<int[]>();
		
		int[] edge;
		for (int i = 0; i < solution.size(); i++) {
			edge = solution.get(i);
			coloring[edge[2]].add(new int[] {edge[0], edge[1]});
		}

		return coloring;
	}
	
	// Returns 'true' if it's possible to color all edges of a graph with the given number of colors
	boolean paintQ(Graph G, int color) {
		// Create the matroids
		Matroid<int[], ColorMatroidSubset> matroid1 = new ColorMatroid(G, color);
		Matroid<int[], GraphicColorMatroidSubset> matroid2 = new GraphicColorMatroid(G, color);
		
		// Get the solution
		ArrayList<int[]> solution = intersector.intersection(matroid1, matroid2); // Its size is the number of edges painted
		
		// Find the number of edges in G
		// Adding up the number of neighbors of each vertex counts every edge twice
		int edgesTotal = 0;
		for (int i = 0; i < G.getSize(); i++) edgesTotal += G.lovers(i).size();
		edgesTotal /= 2;
		
		// Return
		return solution.size() == edgesTotal;
	}
	
	// Returns the minimum number of colors necessary to paint all edges of G
	int paintNumber(Graph G) {
		// 'color' is the current number of colors we're testing
		int color = 0;
		
		// This number is at least the ceiling of |E|/(|V|-1)
		// --> Set 'color' = |E|
		for (int i = 0; i < G.getSize(); i++) color += G.lovers(i).size();
		color /= 2;
		// --> Compute the ceiling of |E|/(|V|-1)
		color = (color + (G.getSize()-1)) / (G.getSize()-1);
		
		// Test all possibilities
		// We know it's possible to do it with |V|-1 colors
		while (color < G.getSize() - 1 && !paintQ(G, color)) color++;
		
		return color;
	}

}
