package matroid;

import java.util.ArrayList;
import java.util.Arrays;

import bgraph.*;

public class Test {

	public static void main(String[] args) {
		/*
		BipG G = new BipG_List(10, 8);
		G.addEdge(0, new int[] {10,11});
		G.addEdge(1, new int[] {10,14});
		G.addEdge(2, new int[] {12,13});
		G.addEdge(3, new int[] {10,14});
		G.addEdge(4, new int[] {11,13});
		
		System.out.println(G);
		
		Matroid<int[]> m = new BipGMatroid(G, true);
		
		ArrayList<int[]> groundSet = m.getGroundSet();
		// Print groundSet
		System.out.print("Ground set: [ ");
		for (int i = 0; i < groundSet.size(); i++) System.out.print(Arrays.toString(groundSet.get(i)) + " ");
		System.out.println("]");
		
		ArrayList<int[]> set = new ArrayList<int[]>();
		set.add(new int[] {0,10});
		set.add(new int[] {2,12});
		set.add(new int[] {1,10});
		set.add(new int[] {4,11});
		System.out.println(m.belongsTo(set));
		
		set.add(new int[] {0,11});
		System.out.println(m.belongsTo(set));
		
		set.remove(set.size() - 1);
		System.out.println(m.belongsTo(set, new int[] {3,14}));
		System.out.println(m.belongsTo(set, new int[] {0,11}));
		System.out.println(m.belongsTo(set, new int[] {3,14}, new int[] {0,10}));
		System.out.println(m.belongsTo(set, new int[] {0,11}, new int[] {0,10}));
		System.out.println(m.belongsTo(set, new int[] {0,11}, new int[] {2,12}));
		
		Matroid<int[]> n = new BipGMatroid(G, false);
		MatroidIntersector<int[]> intersector = new MatroidIntersector<int[]>();
		ArrayList<int[]> pairing = intersector.intersection(m, n);
		// Print pairing
		System.out.println();
		System.out.print("Pairing: [ ");
		for (int i = 0; i < pairing.size(); i++) System.out.print(Arrays.toString(pairing.get(i)) + " ");
		System.out.println("]");
		*/
		
		BipG G = new BipG_List(6, 5);
		
		G.addEdge(0, 10);
		G.addEdge(2, 9);
		G.addEdge(3, 7);
		G.addEdge(3, 9);
		G.addEdge(4, 7);
		G.addEdge(4, 10);
		
		Matroid<int[]> m = new BipGMatroid(G, true);
		Matroid<int[]> n = new BipGMatroid(G, false);
		MatroidIntersector<int[]> intersector = new MatroidIntersector<int[]>();
		ArrayList<int[]> pairing = intersector.intersection(m, n);
		// Print pairing
		System.out.println();
		System.out.print("Pairing: [ ");
		for (int i = 0; i < pairing.size(); i++) System.out.print(Arrays.toString(pairing.get(i)) + " ");
		System.out.println("]");
	}

}
