/* Example of usage of ArborescenceBuilder class */

package arborescence;

import java.util.ArrayList;
import java.util.Arrays;

import graph.OrientedGraph;

public class Test {

	public static void main(String[] args) {
		/* Create an oriented graph */
		OrientedGraph G = new OrientedGraph(13);
		G.addEdge(0, new int[] {1,3,7,8});
		G.addEdge(1, new int[] {2,4,6});
		G.addEdge(2, new int[] {0,2,5});
		G.addEdge(3, new int[] {1});
		G.addEdge(4, new int[] {3,5,7});
		G.addEdge(5, new int[] {9});
		G.addEdge(6, new int[] {4,6});
		G.addEdge(7, new int[] {3,8});
		G.addEdge(8, new int[] {3,5});
		G.addEdge(9, new int[] {10});
		G.addEdge(10, new int[] {11,12});
		/* Print the graph */
		System.out.println("Graph G:");
		System.out.println(G);
		
		/* Initialize arborescence builder class */
		ArborescenceBuilder builder = new ArborescenceBuilder();
		
		/* Find a spanning forest of arborescences (in this case it's a single arborescence) */
		ArrayList<int[]> arb = builder.arborescence(G);
		/* Print the result */
		System.out.println("Spanning arborescence:");
		for (int i = 0; i < arb.size(); i++) System.out.println(Arrays.toString(arb.get(i)));
		
		/* Find a spanning forest of arborescences with root 10 (in this case it's not a single arborescence) */
		arb = builder.arborescence(G, 10);
		/* Print the result */
		System.out.println("\nSpanning forest of arborescences with root 10:");
		for (int i = 0; i < arb.size(); i++) System.out.println(Arrays.toString(arb.get(i)));
		
		/* Can you build a spanning tree? */
		System.out.println("Can you build a spanning tree? " + builder.spanningTreeQ(G));
		System.out.println("Can you build a spanning tree with root 0? " + builder.spanningTreeQ(G, 0));
		System.out.println("Can you build a spanning tree with root 10? " + builder.spanningTreeQ(G, 10));
	}

}
