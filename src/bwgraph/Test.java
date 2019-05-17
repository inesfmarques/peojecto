package bwgraph;

import java.util.ArrayList;
import java.util.Arrays;

import bgraph.BipG;
import bgraph.BipG_Matrix;

public class Test {

	public static void main(String[] args) {
		/* Create graph */
		BipWG G = new BipWG_Matrix(3, 3);
		/* Add edges to graph */
		G.addEdge(0, new int[] {3,4}, new double[] {1,5});
		G.addEdge(1, new int[] {4,5}, new double[] {8,1});
		G.addEdge(2, new int[] {3,5}, new double[] {2,0});
		/* Print graph */
		System.out.println("Graph 1:");
		System.out.print(G);
		
		/* Find a matching using one of the methods */
		ArrayList<int[]> results = G.MatchingDijkstra();
		
		/* Print the results */
		System.out.println();
		System.out.println("Maximal matching of graph 1:");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
		System.out.print("\n\n");
		
		/*
		BipWG graph = new BipWG_Matrix(10,4);
		graph.addEdge(2, 12, 0);
		graph.addEdge(12, 9, -6);
		graph.addEdge(5, 13, -2);
		System.out.println(graph);
		
		graph.addEdge(13, 5, 2);
		System.out.println(graph);
		
		System.out.println(graph.edgeQ(5, 13));
		System.out.println(graph.getWeight(5, 13));
		System.out.println(graph.edgeQ(13, 5));
		System.out.println(graph.getWeight(13, 5));
		
		System.out.println();
		
		System.out.println(graph.edgeQ(2,12));
		System.out.println(graph.getWeight(2,12));
		System.out.println(graph.edgeQ(12,2));
		System.out.println(graph.getWeight(12,2));
		*/
	}

}
