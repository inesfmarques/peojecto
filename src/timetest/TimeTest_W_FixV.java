/* Usage of TimeTest to obtain time performances of maximum weighted bipartite matchings for fixed number of vertices */
/* Generates Figure 3a of the report */

package timetest;

import java.util.Random;

import bwgraph.BipWG;
import bwgraph.BipWG_List;
import bwgraph.BipWG_Matrix;

public class TimeTest_W_FixV {
	
	public static void main(String[] args) {
		//TODO Set variables
				// --> N: number of graphs for each probability
				// --> min: minimum number of vertices in each set
				// --> max: maximum number of vertices in each set
				// --> p: array with edge densities
				// --> seed: for random
				// --> test: set i-th entry to true if you want to use the i-th method, false otherwise
		int N = 10;
		int min = 1000;
		int max = 1000;
		double[] p = {0.001, 0.0500, 0.1000, 0.1500, 0.2000, 0.2500, 0.3000, 0.3500, 0.4000, 0.4500, 0.5000, 0.550, 0.6000, 0.6500, 0.7000, 0.7500, 0.8000, 0.8500, 0.9000, 0.950};
		long seed = 1;
		boolean[] test = new boolean[] {true, true, true, true, true, true};
		
		// Set seed
		Random random = new Random();
		random.setSeed(seed);
		
		// List with times
		double[][] time = new double[p.length][6];
		
		// Variables used
		boolean check;
		int L, R;
		int[] result = new int[6];
		long startTime, finishTime;
		double resultTotal;
		
		// Do the tests
		System.out.println("Starting tests:");
		for (int i = 0; i < p.length; i++) {
			System.out.println("--> Performing tests for p = " + p[i] + "...");
			
			resultTotal = 0;
			
			for (int n = 0; n < N; n++) {
				
				// Build the graph randomly
				// --> Sizes of left and right sets
				L = min + random.nextInt(max - min + 1);
				R = min + random.nextInt(max - min + 1);

				// --> Setting the graph
				BipWG GM = new BipWG_Matrix(L, R);
				BipWG GL = new BipWG_List(L, R);
				
				// --> Adding edges randomly
				for (int l = 0; l < L; l++) {
					for (int r = L; r < L+R; r++) {
						if (random.nextDouble() < p[i]) {
							int w = new Random().nextInt(10);
							GM.addEdge(l, r, w);
							GL.addEdge(l, r, w);
						}
					}
				}
				
				// Do the tests
				// result is where we store the size of matching found by each method
				
				// --> MatchingBellmanFord() on matrix implementation
				if (test[0]) {
					startTime = System.nanoTime();
					result[0] = GM.MatchingBellmanFord().size();
					finishTime = System.nanoTime();
					time[i][0] += (finishTime - startTime) / 1000;
				}
				
				// --> MatchingDijkstra() on matrix implementation
				if (test[1]) {
					startTime = System.nanoTime();
					result[1] = GM.MatchingDijkstra().size();
					finishTime = System.nanoTime();
					time[i][1] += (finishTime - startTime) / 1000;
				}
				
				// --> MatchingDijkstraPQ() on matrix implementation
				if (test[2]) {
					startTime = System.nanoTime();
					result[2] = GM.MatchingDijkstraPQ().size();
					finishTime = System.nanoTime();
					time[i][2] += (finishTime - startTime) / 1000;
				}
				
				// --> MatchingBellmanFord() on adjacency lists implementation
				if (test[3]) {
					startTime = System.nanoTime();
					result[3] = GL.MatchingBellmanFord().size();
					finishTime = System.nanoTime();
					time[i][3] += (finishTime - startTime) / 1000;
				}
				
				// --> MatchingDijkstra() on adjacency lists implementation
				if (test[4]) {
					startTime = System.nanoTime();
					result[4] = GL.MatchingDijkstra().size();
					finishTime = System.nanoTime();
					time[i][4] += (finishTime - startTime) / 1000;
				}
				
				// --> MatchingDijkstraPQ() on adjacency lists implementation
				if (test[5]) {
					startTime = System.nanoTime();
					result[5] = GL.MatchingDijkstraPQ().size();
					finishTime = System.nanoTime();
					time[i][5] += (finishTime - startTime) / 1000;
				}
				
				
				// Check if results are the same
				check = true;
				for (int a = 0; a < 6; a++) {
					for (int b = a+1; b < 6; b++) {
						if (test[a] && test[b] && result[a] != result[b]) check = false;
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
					if (test[0]) System.out.println("MatchingBellmanFord() on matrix implementation: " + result[0]);
					if (test[1]) System.out.println("MatchingDijkstra() on matrix implementation: " + result[1]);
					if (test[2]) System.out.println("MatchingDijkstraPQ() on matrix implementation: " + result[2]);
					if (test[3]) System.out.println("MatchingBellmanFord() on adjacency lists implementation: " + result[3]);
					if (test[4]) System.out.println("MatchingDijkstra() on adjacency lists implementation: " + result[4]);
					if (test[5]) System.out.println("MatchingDijkstraPQ() on adjacency lists implementation: " + result[5]);
					System.out.println();
					System.out.println("PROCESS TERMINATED");
					return;
				}
				
				for (int a = 0; a < 6; a++) {
					if (test[a]) {
						resultTotal += result[a];
						break;
					}
				}
			}
			
			// After all tests are done, we take the average
			for (int j = 0; j < 6; j++) time[i][j] /= N;
			resultTotal /= N;
			
			System.out.println("    Maximum matchings had on average size " + resultTotal);
			
			// All tests are done, print what was found
			System.out.println("Tests done!");
			// Print results found for this case
			System.out.println("    --> For p = " + p[i] + " the average times were");
			if (test[0]) System.out.println("        " + time[i][0] + " microseconds for MacthingBellmanFord() on matrix implementation");
			if (test[1]) System.out.println("        " + time[i][1] + " microseconds for MatchingDijkstra() on matrix implementation");
			if (test[2]) System.out.println("        " + time[i][2] + " microseconds for MacthingDijkstraPQ() on matrix implementation");
			if (test[3]) System.out.println("        " + time[i][3] + " microseconds for MatchingBellmanFord() on adjacency lists implementation");
			if (test[4]) System.out.println("        " + time[i][4] + " microseconds for MatchingDijkstra() on adjacency lists implementation");
			if (test[5]) System.out.println("        " + time[i][5] + " microseconds for MatchingDijkstraPQ() on adjacency lists implementation");
			
			System.out.println("    Done!");
		}
		
		// All tests are done, print all results found
		System.out.println("Tests done!");
		for (int i = 0; i < p.length; i++) {
			System.out.println("--> For p = " + p[i] + " the average times were");
			if (test[0]) System.out.println("    " + time[i][0] + " microseconds for EdmondsKarp() on matrix implementation");
			if (test[1]) System.out.println("    " + time[i][1] + " microseconds for FordFulkerson() on matrix implementation");
			if (test[2]) System.out.println("    " + time[i][2] + " microseconds for HopcroftKarp() on matrix implementation");
			if (test[3]) System.out.println("    " + time[i][3] + " microseconds for MatroidIntersectionBad() on matrix implementation");
			if (test[4]) System.out.println("    " + time[i][4] + " microseconds for MatroidIntersection() on matrix implementation");
			if (test[5]) System.out.println("    " + time[i][5] + " microseconds for EdmondsKarp() on adjacency lists implementation");
		}
		System.out.println();
				
		System.out.println("PROCESS TERMINATED");
		return;	
	}

}
