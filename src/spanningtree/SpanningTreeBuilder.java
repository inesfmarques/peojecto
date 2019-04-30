package spanningtree;

import java.util.ArrayList;

import graph.OrientedGraph;
import matroid.Matroid;
import matroid.MatroidIntersector;

public class SpanningTreeBuilder {
	
	MatroidIntersector<int[], GraphicMatroidSubset, IntoMatroidSubset> intersector =
			new MatroidIntersector<int[], GraphicMatroidSubset, IntoMatroidSubset>();
			
	ArrayList<int[]> spanningTree(OrientedGraph G, int v){
		Matroid<int[], GraphicMatroidSubset> matroid1 = new GraphicMatroid(G);
		Matroid<int[], IntoMatroidSubset> matroid2 = new IntoMatroid(G, v);
		
		return intersector.intersection(matroid1, matroid2);
	}
	
	boolean spanningTreeQ(OrientedGraph G, int v) {
		return spanningTree(G, v).size() == G.getSize() - 1;
	}

}
