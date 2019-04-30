package painting;

import java.util.ArrayList;
import java.util.Arrays;

import graph.Graph;

public class Test {

	public static void main(String[] args) {
	
		
		Graph G = new Graph(7);
		G.addEdge(0, new int[] {1,2,4,5,6});
		G.addEdge(1, new int[] {2,3,4,6});
		G.addEdge(2, new int[] {3,4,5});
		G.addEdge(3, new int[] {4,5,6});
		G.addEdge(4, new int[] {6});
		G.addEdge(5, new int[] {6});
		
		
		/*
		Graph G = new Graph(4);
		G.addEdge(0, new int[] {1,2,3});
		G.addEdge(1, new int[] {2,3});
		G.addEdge(2, new int[] {3});
		*/
		/*
		int n = 200;
		double p = 0.01;
		
		Random random = new Random();
		random.setSeed(1);
		
		int counter = 0;
		Graph G = new Graph(n);
		for (int l = 0; l < n; l++) {
			for (int r = l; r < n; r++) {
				if (random.nextDouble() < p) {
					G.addEdge(l, r);
					counter++;
				}
			}
		}
		System.out.println(counter);
		*/
		
		System.out.println(G);
		
		System.out.print("Starting painter...");
		Painter painter = new Painter();
		ArrayList<int[]>[] solution = painter.paint(G, 2);
		System.out.println("Done!");
		
		
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
			System.out.println();
		}
		
		
		System.out.println(painter.paintQ(G, 1));
		System.out.println(painter.paintQ(G, 2));
		System.out.println(painter.paintQ(G, 3));
		System.out.println(painter.paintQ(G, 4));
		System.out.println(painter.paintQ(G, 5));
		System.out.println();
		
		System.out.println(painter.paintNumber(G));
	}

}
