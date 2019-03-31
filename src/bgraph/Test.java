package bgraph;
import java.util.Arrays;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		BipG G = new BipG_List(10, 13);
		
		G.addEdge(0, new int[] {13,14,17,20});
		G.addEdge(1, new int[] {10,11,12,17,19});
		G.addEdge(2, new int[] {11,13,14,16,17,18,20});
		G.addEdge(3, new int[] {12,14,16,17,19});
		G.addEdge(4, new int[] {11,13,19});
		G.addEdge(5, new int[] {15,16,18,19});
		G.addEdge(6, new int[] {11,18,19,20});
		G.addEdge(7, new int[] {12,13,15,16});
		G.addEdge(8, new int[] {10,11,12,14,15,18,19});
		G.addEdge(9, new int[] {11,13,16,17,18,19,20});
		
		/*
		G.addEdge(0, new int[] {10,11});
		G.addEdge(1, new int[] {10,14});
		G.addEdge(2, new int[] {12,13});
		G.addEdge(3, new int[] {10,14});
		G.addEdge(4, new int[] {11,13});
		*/
		System.out.println("Starting graph:");
		System.out.print(G);
		System.out.println();
		ArrayList<int[]> results = G.HopcroftKarp();
		System.out.println("Result: ");
		for (int i = 0; i < results.size(); i++) {
			System.out.println(Arrays.toString(results.get(i)));
		}
	}

}
