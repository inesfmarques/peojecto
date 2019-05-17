/* Example of usage of BipG and matching methods */

package bgraph;
import java.util.Arrays;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		/* Create graph */
		BipG G = new BipG_Matrix(3, 3);
		/* Add edges to graph */
		G.addEdge(0, new int[] {3});
		G.addEdge(1, new int[] {3,4});
		G.addEdge(2, new int[] {4,5});
		/* Print graph */
		System.out.println("Graph 1:");
		System.out.print(G);
		
		/* Find a matching using one of the methods */
		ArrayList<int[]> results = G.FordFulkerson();
		
		/* Print the results */
		System.out.println();
		System.out.println("Maximal matching of graph 1:");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
		System.out.print("\n\n");
		
		
		/* Create graph */
		BipG H = new BipG_List(6, 5);
		/* Add edges to graph */
		H.addEdge(0, new int[] {6,7});
		H.addEdge(1, new int[] {6,10});
		H.addEdge(2, new int[] {8,9});
		H.addEdge(3, new int[] {6,7,10});
		H.addEdge(4, new int[] {7,9});
		H.addEdge(5, new int[] {7});
		/* Print graph */
		System.out.println("Graph 2:");
		System.out.print(H);
		
		/* Find a matching using one of the methods */
		results = H.MatroidIntersection();
		
		/* Print the results */
		System.out.println();
		System.out.println("Maximal matching of graph 2:");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
	}
}
