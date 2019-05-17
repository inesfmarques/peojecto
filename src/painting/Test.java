/* Example of usage of Painter class */
package painting;

import java.util.ArrayList;
import java.util.Arrays;

import graph.Graph;

public class Test {

	public static void main(String[] args) {
		/* Create a graph using Graph class (from 'graph' package) */
		Graph G = new Graph(7);
		G.addEdge(0, new int[] {1,2,4,5,6});
		G.addEdge(1, new int[] {2,3,4,6});
		G.addEdge(2, new int[] {3,4,5});
		G.addEdge(3, new int[] {4,5,6});
		G.addEdge(4, new int[] {6});
		G.addEdge(5, new int[] {6});
		/* Print the graph */
		System.out.println("Graph G:");
		System.out.println(G);
		
		/* Initialize Painter class */
		Painter painter = new Painter();
		
		/* Set number of colors */
		int colors = 2;
		/* Paint the graph using the given number of colors */
		ArrayList<int[]>[] solution = painter.paint(G, colors);
		/* The solution is given in the format { {Edges painted by color 0}, {Edges painted by color 1}, ... } (an edge is an array [u,v]) */
		
		/* Print the results */
		ArrayList<int[]> current;
		System.out.println("Paint G with " + solution.length + " colors:");
		for (int i = 0; i < solution.length; i++) {
			System.out.println("Color " + i + ":");
			current = solution[i];
			for (int j = 0; j < current.size(); j++) {
				System.out.print(Arrays.toString(current.get(j)));
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
		
		/* With which number of colors can you paint G? */
		System.out.println("Can you paint G using 1 color? " + painter.paintQ(G, 1));
		System.out.println("Can you paint G using 2 colors? " + painter.paintQ(G, 2));
		System.out.println("Can you paint G using 3 colors? " + painter.paintQ(G, 3));
		System.out.println("Can you paint G using 4 colors? " + painter.paintQ(G, 4));
		System.out.println("Can you paint G using 5 colors? " + painter.paintQ(G, 5));
		System.out.println();
		
		/* Compute the paint number */
		System.out.println("The paint number of G is " + painter.paintNumber(G));
	}

}
