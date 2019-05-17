package arborescence;

import java.util.ArrayList;

import graph.OrientedGraph;
import matroid.Matroid;
import matroid.MatroidIntersector;

public class ArborescenceBuilder {
	
	// Matroid intersector
	MatroidIntersector<int[], GraphicMatroidSubset, IntoMatroidSubset> intersector =
			new MatroidIntersector<int[], GraphicMatroidSubset, IntoMatroidSubset>();
	
	// Builds a forest of arborescences from the given graph	
	ArrayList<int[]> arborescence(OrientedGraph G){
		Matroid<int[], GraphicMatroidSubset> matroid1 = new GraphicMatroid(G);
		Matroid<int[], IntoMatroidSubset> matroid2 = new IntoMatroid(G);
		
		return intersector.intersection(matroid1, matroid2);
	}
	
	// Builds a forest of arborescences from the given graph with fixed root
	ArrayList<int[]> arborescence(OrientedGraph G, int v){
		// Create a copy of the graph with the same edges except for the ones ending in v
		OrientedGraph H = new OrientedGraph(G.getSize());
		ArrayList<Integer> lovers;
		for (int i = 0; i < G.getSize(); i++) {
			lovers = G.lovers(i);
			for (int j = 0; j < lovers.size(); j++) {
				if (lovers.get(j) != v) H.addEdge(i, lovers.get(j));
			}
		}
		
		return arborescence(H);
	}
	
	// Returns 'true' if there is a spanning arborescence
	boolean spanningTreeQ(OrientedGraph G) {
		return arborescence(G).size() == G.getSize() - 1;
	}
	
	// Returns 'true' if there is a spanning arborescence with given root
	boolean spanningTreeQ(OrientedGraph G, int v) {
		return arborescence(G, v).size() == G.getSize() - 1;
	}

}
