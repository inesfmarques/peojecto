package bwgraph;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
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
		
		BipWG G = new BipWG_Matrix(3,3);
		G.addEdge(0, 3, 1);
		G.addEdge(0, 4, 5);
		G.addEdge(1, 4, 8);
		G.addEdge(1, 5, 1);
		G.addEdge(2, 3, 2);
		G.addEdge(2, 5, 0);
		
		System.out.println("Starting graph:");
		System.out.print(G);
		System.out.println();
		ArrayList<int[]> results = G.MatchingDijkstra();
		System.out.println("Result: ");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
	}

}
