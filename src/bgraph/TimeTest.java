package bgraph;

import java.util.Random;

public class TimeTest {
	
	public static void main(String[] args) {
		//TODO Set variables
		// --> N: number of graphs for each probability
		// --> min: minimum number of vertices in each set
		// --> max: maximum number of vertices in each set
		// --> edgeDensity: array with edge densities
		// --> seed: for random
		int N = 10;
		int min = 200;
		int max = 400;
		double[] p = new double[] {0.05, 0.1, 0.2, 0.4, 0.6, 0.8, 0.9, 0.95};
		long seed = 1;
		
		// Set seed
		Random random = new Random();
		random.setSeed(seed);
		
		// List with times
		double[][] time = new double[p.length][6];
		
		// Variables used
		int L, R;
		int[] result = new int[6];
		long startTime, finishTime;
		
		// Do the tests
		System.out.println("Starting tests:");
		for (int i = 0; i < p.length; i++) {
			System.out.println("--> Performing tests for p = " + p[i] + "...");
			for (int n = 0; n < N; n++) {
				
				// Build the graph randomly
				// --> Sizes of left and right sets
				L = min + random.nextInt(max - min + 1);
				R = min + random.nextInt(max - min + 1);

				// --> Setting the graph
				BipG GM = new BipG_Matrix(L, R);
				BipG GL = new BipG_List(L, R);
				
				// --> Adding edges randomly
				for (int l = 0; l < L; l++) {
					for (int r = L; r < L+R; r++) {
						if (random.nextDouble() < p[i]) {
							GM.addEdge(l, r);
							GL.addEdge(l, r);
						}
					}
				}
				
				// Do the tests
				// result is where we store the size of matching found by each method
				
				// --> EdmondsKarp() on matrix implementation
				startTime = System.nanoTime();
				result[0] = GM.EdmondsKarp().size();
				finishTime = System.nanoTime();
				time[i][0] += (finishTime - startTime) / 1000000;

				// --> FordFulkerson() on matrix implementation
				startTime = System.nanoTime();
				result[1] = GM.FordFulkerson().size();
				finishTime = System.nanoTime();
				time[i][1] += (finishTime - startTime) / 1000000;

				// --> HopcroftKarp() on matrix implementation
				startTime = System.nanoTime();
				result[2] = GM.HopcroftKarp().size();
				finishTime = System.nanoTime();
				time[i][2] += (finishTime - startTime) / 1000000;

				// --> EdmondsKarp() on adjacency lists implementation
				startTime = System.nanoTime();
				result[3] = GL.EdmondsKarp().size();
				finishTime = System.nanoTime();
				time[i][3] += (finishTime - startTime) / 1000000;
				
				// --> FordFulkerson() on adjacency lists implementation
				startTime = System.nanoTime();
				result[4] = GL.FordFulkerson().size();
				finishTime = System.nanoTime();
				time[i][4] += (finishTime - startTime) / 1000000;
				
				// --> HopcroftKarp() on adjacency lists implementation
				startTime = System.nanoTime();
				result[5] = GL.HopcroftKarp().size();
				finishTime = System.nanoTime();
				time[i][5] += (finishTime - startTime) / 1000000;
				
				// Check if results are the same
				if(!(result[0] == result[1] &&
						result[0] == result[2] &&
						result[0] == result[3] &&
						result[0] == result[4] &&
						result[0] == result[5])) {
					// If the results don't match, we print the graph and the results and end the process
					System.out.println("ERROR: Some methods didn't find a maximum matching");
					System.out.println();
					System.out.println("Graph:");
					System.out.println(GM);
					System.out.println();
					System.out.println("Results:");
					System.out.println("EdmondsKarp() on matrix implementation: " + result[0]);
					System.out.println("FordFulkerson() on matrix implementation: " + result[1]);
					System.out.println("HopcroftKarp() on matrix implementation: " + result[2]);
					System.out.println("EdmondsKarp() on adjacency lists implementation: " + result[3]);
					System.out.println("FordFulkerson() on adjacency lists implementation: " + result[4]);
					System.out.println("HopcroftKarp() on adjacency lists implementation: " + result[5]);
					System.out.println();
					System.out.println("PROCESS TERMINATED");
					return;
				}
			}
			
			// After all tests are done, we take the average
			for (int j = 0; j < 6; j++) time[i][j] /= N;
			System.out.println("    Done!");
		}
		
		// All tests are done, print what was found
		System.out.println("Tests done!");
		for (int i = 0; i < p.length; i++) {
			System.out.println("--> For p = " + p[i] + " the average times were");
			System.out.println("    " + time[i][0] + " milliseconds for EdmondsKarp() on matrix implementation");
			System.out.println("    " + time[i][1] + " milliseconds for FordFulkerson() on matrix implementation");
			System.out.println("    " + time[i][2] + " milliseconds for HopcroftKarp() on matrix implementation");
			System.out.println("    " + time[i][3] + " milliseconds for EdmondsKarp() on adjacency lists implementation");
			System.out.println("    " + time[i][4] + " milliseconds for FordFulkerson() on adjacency lists implementation");
			System.out.println("    " + time[i][5] + " milliseconds for HopcroftKarp() on adjacency lists implementation");
		}
		System.out.println();
		System.out.println("PROCESS TERMINATED");
		return;
		
	}

}
