/* Example of usage of BipWG and matching methods */

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
		ArrayList<int[]> results = G.MatchingBellmanFord();
		
		/* Print the results */
		System.out.println();
		System.out.println("Maximal matching of graph 1:");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
		System.out.print("\n\n");
		
		/* Create graph */
		BipWG H = new BipWG_Matrix(3, 3);
		/* Add edges to graph */
		H.addEdge(0, new int[] {3,4}, new double[] {-15,-13});
		H.addEdge(1, new int[] {3,5}, new double[] {-6,-4});
		H.addEdge(2, new int[] {4,5}, new double[] {-14,-10});
		/* Print graph */
		System.out.println("Graph 2:");
		System.out.print(H);
		
		/* Find a matching using one of the methods */
		results = H.MatchingDijkstraPQ();
		
		/* Print the results */
		System.out.println();
		System.out.println("Maximal matching of graph 2:");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
		System.out.print("\n\n");
	}

}
