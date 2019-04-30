package spanningtree;

import java.util.ArrayList;
import java.util.Arrays;

import graph.OrientedGraph;

public class Test {

	public static void main(String[] args) {
		OrientedGraph G = new OrientedGraph(10);
		G.addEdge(0, new int[] {1,3,7,8});
		G.addEdge(1, new int[] {2,4,6});
		G.addEdge(2, new int[] {0,2,5});
		G.addEdge(3, new int[] {1});
		G.addEdge(4, new int[] {3,5,7});
		G.addEdge(5, new int[] {});
		G.addEdge(6, new int[] {4,6});
		G.addEdge(7, new int[] {3,8});
		G.addEdge(8, new int[] {3,5});
		G.addEdge(9, new int[] {1});
		
		System.out.println(G);
		
		SpanningTreeBuilder builder = new SpanningTreeBuilder();
		ArrayList<int[]> tree = builder.spanningTree(G, 2);
		
		System.out.println("Tree:");
		for (int i = 0; i < tree.size(); i++) System.out.println(Arrays.toString(tree.get(i)));
	}

}
