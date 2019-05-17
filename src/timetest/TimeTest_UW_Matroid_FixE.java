/* Usage of TimeTest to obtain time performances of matroid intersection methods for fixed number of edges */
/* Generates Figure 4b of the report */

package timetest;

import java.util.Random;

import bgraph.BipG;
import bgraph.BipG_List;
import bgraph.BipG_Matrix;

public class TimeTest_UW_Matroid_FixE {
	
	public static void main(String[] args) {
		//TODO Set variables
		// --> N: number of graphs for each probability
		// --> a: number of edges
		// --> v: array of possible number of vertices on each side of the bipartite graph
		// --> seed: for random
		// --> test: set i-th entry to true if you want to use the i-th method, false otherwise
		int N = 10;
		int a = 2000;
		int[] v = new int[] {50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200};
		long seed = 1;
		boolean[] test = new boolean[] {false, false, false, true, true,
										false, false, false, true, true};

		
		// Set seed
		Random random = new Random();
		random.setSeed(seed);
		
		// List with times
		double[][] time = new double[v.length][10];
		
		// Variables used
		boolean check;
		int L, R;
		int[] result = new int[10];
		long startTime, finishTime;
		double resultTotal;
		

		// Do the tests
		System.out.println("Starting tests:");
		for (int i = 0; i < v.length; i++) {
			System.out.println("--> Performing tests for v = " + v[i] + "...");
			
			resultTotal = 0;
			
			for (int n = 0; n < N; n++) {
				
				// Build the graph randomly
				// --> Sizes of left and right sets
				L = v[i];
				R = v[i];
				
				// --> Setting the graph
				BipG GM = new BipG_Matrix(L, R);
				BipG GL = new BipG_List(L, R);
				
				int l,r;
				
				// --> Adding edges randomly
				for (int j = 0; j < a; j++) {
					l = random.nextInt(v[i]);
					r = random.nextInt(v[i]) + L;
					while(GM.edgeQ(l, r)) {
						l = random.nextInt(v[i]);
						r = random.nextInt(v[i]) + L;
					}
					GM.addEdge(l, r);
					GL.addEdge(l, r);
				}
				
				
				
				// Do the tests
				// result is where we store the size of matching found by each method
				
				// --> EdmondsKarp() on matrix implementation
				if (test[0]) {
					startTime = System.nanoTime();
					result[0] = GM.EdmondsKarp().size();
					finishTime = System.nanoTime();
					time[i][0] += (finishTime - startTime) / 1000;
				}

				// --> FordFulkerson() on matrix implementation
				if (test[1]) {
					startTime = System.nanoTime();
					result[1] = GM.FordFulkerson().size();
					finishTime = System.nanoTime();
					time[i][1] += (finishTime - startTime) / 1000;
				}
				
				// --> HopcroftKarp() on matrix implementation
				if (test[2]) {
					startTime = System.nanoTime();
					result[2] = GM.HopcroftKarp().size();
					finishTime = System.nanoTime();
					time[i][2] += (finishTime - startTime) / 1000;
				}
				
				// --> MatroidIntersectionBad() on matrix implementation
				if (test[3]) {
					startTime = System.nanoTime();
					result[3] = GM.MatroidIntersectionBad().size();
					finishTime = System.nanoTime();
					time[i][3] += (finishTime - startTime) / 1000;
				}
				
				// --> MatroidIntersection() on matrix implementation
				if (test[4]) {
					startTime = System.nanoTime();
					result[4] = GM.MatroidIntersection().size();
					finishTime = System.nanoTime();
					time[i][4] += (finishTime - startTime) / 1000;
				}

				// --> EdmondsKarp() on adjacency lists implementation
				if (test[5]) {
					startTime = System.nanoTime();
					result[5] = GL.EdmondsKarp().size();
					finishTime = System.nanoTime();
					time[i][5] += (finishTime - startTime) / 1000;
				}
				
				// --> FordFulkerson() on adjacency lists implementation
				if (test[6]) {
					startTime = System.nanoTime();
					result[6] = GL.FordFulkerson().size();
					finishTime = System.nanoTime();
					time[i][6] += (finishTime - startTime) / 1000;
				}
				
				// --> HopcroftKarp() on adjacency lists implementation
				if (test[7]) {
					startTime = System.nanoTime();
					result[7] = GL.HopcroftKarp().size();
					finishTime = System.nanoTime();
					time[i][7] += (finishTime - startTime) / 1000;
				}
					
				// --> MatroidIntersectionBad() on adjacency lists implementation
				if (test[8]) {
					startTime = System.nanoTime();
					result[8] = GL.MatroidIntersectionBad().size();
					finishTime = System.nanoTime();
					time[i][8] += (finishTime - startTime) / 1000000;
				}
				
				// --> MatroidIntersection() on adjacency lists implementation
				if (test[9]) {
					startTime = System.nanoTime();
					result[9] = GL.MatroidIntersection().size();
					finishTime = System.nanoTime();
					time[i][9] += (finishTime - startTime) / 1000000;
				}
				
				// Check if results are the same
				check = true;
				for (int aa = 0; aa < 10; aa++) {
					for (int b = aa+1; b < 10; b++) {
						if (test[aa] && test[b] && result[aa] != result[b]) check = false;
					}
				}
				
				if(!check) {
					// If the results don't match, we print the graph and the results and end the process
					System.out.println("ERROR: Some methods didn't find a maximum matching");
					System.out.println();
					System.out.println("Graph:");
					System.out.println(GM);
					System.out.println();
					System.out.println("Results:");
					if (test[0]) System.out.println("EdmondsKarp() on matrix implementation: " + result[0]);
					if (test[1]) System.out.println("FordFulkerson() on matrix implementation: " + result[1]);
					if (test[2]) System.out.println("HopcroftKarp() on matrix implementation: " + result[2]);
					if (test[3]) System.out.println("MatroidIntersectionBad() on matrix implementation: " + result[3]);
					if (test[4]) System.out.println("MatroidIntersection() on matrix implementation: " + result[4]);
					if (test[5]) System.out.println("EdmondsKarp() on adjacency lists implementation: " + result[5]);
					if (test[6]) System.out.println("FordFulkerson() on adjacency lists implementation: " + result[6]);
					if (test[7]) System.out.println("HopcroftKarp() on adjacency lists implementation: " + result[7]);
					if (test[8]) System.out.println("MatroidIntersectionBad() on adjacency lists implementation: " + result[8]);
					if (test[9]) System.out.println("MatroidIntersection() on adjacency lists implementation: " + result[9]);
					System.out.println();
					System.out.println("PROCESS TERMINATED");
					return;
				}
				
				
				for (int aa = 0; aa < 10; aa++) {
					if (test[aa]) {
						resultTotal += result[aa];
						break;
					}
				}
			}
			
			
			
			// After all tests are done, we take the average
			for (int j = 0; j < 10; j++) time[i][j] /= N;
			resultTotal /= N;
			
			System.out.println("    Maximum matchings had on average size " + resultTotal);
			// Print results found for this case
			System.out.println("    --> For v = " + v[i] + " the average times were");
			if (test[0]) System.out.println("        " + time[i][0] + " microseconds for EdmondsKarp() on matrix implementation");
			if (test[1]) System.out.println("        " + time[i][1] + " microseconds for FordFulkerson() on matrix implementation");
			if (test[2]) System.out.println("        " + time[i][2] + " microseconds for HopcroftKarp() on matrix implementation");
			if (test[3]) System.out.println("        " + time[i][3] + " microseconds for MatroidIntersectionBad() on matrix implementation");
			if (test[4]) System.out.println("        " + time[i][4] + " microseconds for MatroidIntersection() on matrix implementation");
			if (test[5]) System.out.println("        " + time[i][5] + " microseconds for EdmondsKarp() on adjacency lists implementation");
			if (test[6]) System.out.println("        " + time[i][6] + " microseconds for FordFulkerson() on adjacency lists implementation");
			if (test[7]) System.out.println("        " + time[i][7] + " microseconds for HopcroftKarp() on adjacency lists implementation");
			if (test[8]) System.out.println("        " + time[i][8] + " microseconds for MatroidIntersectionBad() on adjacency lists implementation");
			if (test[9]) System.out.println("        " + time[i][9] + " microseconds for MatroidIntersection() on adjacency lists implementation");
			
			System.out.println("    Done!");
		}
		
		// All tests are done, print all results found
		System.out.println("Tests done!");
		for (int i = 0; i < v.length; i++) {
			System.out.println("--> For v = " + v[i] + " the average times were");
			if (test[0]) System.out.println("    " + time[i][0] + " microseconds for EdmondsKarp() on matrix implementation");
			if (test[1]) System.out.println("    " + time[i][1] + " microseconds for FordFulkerson() on matrix implementation");
			if (test[2]) System.out.println("    " + time[i][2] + " microseconds for HopcroftKarp() on matrix implementation");
			if (test[3]) System.out.println("    " + time[i][3] + " microseconds for MatroidIntersectionBad() on matrix implementation");
			if (test[4]) System.out.println("    " + time[i][4] + " microseconds for MatroidIntersection() on matrix implementation");
			if (test[5]) System.out.println("    " + time[i][5] + " microseconds for EdmondsKarp() on adjacency lists implementation");
			if (test[6]) System.out.println("    " + time[i][6] + " microseconds for FordFulkerson() on adjacency lists implementation");
			if (test[7]) System.out.println("    " + time[i][7] + " microseconds for HopcroftKarp() on adjacency lists implementation");
			if (test[8]) System.out.println("    " + time[i][8] + " microseconds for MatroidIntersectionBad() on adjacency lists implementation");
			if (test[9]) System.out.println("    " + time[i][9] + " microseconds for MatroidIntersection() on adjacency lists implementation");
		}
		System.out.println();
		
		System.out.println("PROCESS TERMINATED");
		return;	
	}

}
