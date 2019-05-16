package bwgraph;

import java.util.Arrays;
import java.util.Random;

import bgraph.BipG;
import bgraph.BipG_List;
import bgraph.BipG_Matrix;

public class TimeTest_fix_edges {
	
	public static void main(String[] args) {
		//TODO Set variables
		// --> N: number of graphs for each probability
		// --> min: minimum number of vertices in each set
		// --> max: maximum number of vertices in each set
		// --> p: array with edge densities
		// --> seed: for random
		// --> m: lenght of the list of p's
		int N = 7;
		int a = 2000;
		//double[] p = new double[] {0.1, 0.001, 0.00001};
		int[] v = new int[] {50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200};
		long seed = 1;

		
		// Set seed
		Random random = new Random();
		random.setSeed(seed);
		
		// List with times
		double[][] time = new double[v.length][6];
		
		// Variables used
		int L, R;
		int[] result = new int[6];
		long startTime, finishTime;
		
		// Do the tests
		System.out.println("Starting tests:");
		for (int i = 0; i < v.length; i++) {
			System.out.println("--> Performing tests for v = " + v[i] + "...");
			
			
			for (int n = 0; n < N; n++) {
				
				// Build the graph randomly
				// --> Sizes of left and right sets
				L = v[i];
				R = v[i];
				
				// --> Setting the graph
				BipWG GM = new BipWG_Matrix(L, R);
				BipWG GL = new BipWG_List(L, R);
				
				int l,r,w;
				
				// --> Adding edges randomly
				for (int j = 0; j < a; j++) {
					l = random.nextInt(v[i]);
					r = random.nextInt(v[i]) + L;
					w = random.nextInt(6);
					while(GM.edgeQ(l, r)) {
						l = random.nextInt(v[i]);
						r = random.nextInt(v[i]) + L;
					}
					GM.addEdge(l, r, w);
					GL.addEdge(l, r, w);
				}
				
				// Do the tests
				// result is where we store the size of matching found by each method
				
				// --> MatchingBellmanFord() on matrix implementation
				startTime = System.nanoTime();
				result[0] = GM.MatchingBellmanFord().size();
				finishTime = System.nanoTime();
				time[i][0] += (finishTime - startTime) / 1000;
				
				// --> MatchingDijkstra() on matrix implementation
				startTime = System.nanoTime();
				result[1] = GM.MatchingDijkstra().size();
				finishTime = System.nanoTime();
				time[i][1] += (finishTime - startTime) / 1000;

				// --> MatchingDijkstraPQ() on matrix implementation
				startTime = System.nanoTime();
				result[2] = GM.MatchingDijkstraPQ().size();
				finishTime = System.nanoTime();
				time[i][2] += (finishTime - startTime) / 1000;

				// --> MatchingBellmanFord() on adjacency lists implementation
				startTime = System.nanoTime();
				result[3] = GL.MatchingBellmanFord().size();
				finishTime = System.nanoTime();
				time[i][3] += (finishTime - startTime) / 1000;

				// --> MatchingDijkstra() on adjacency lists implementation
				startTime = System.nanoTime();
				result[4] = GL.MatchingDijkstra().size();
				finishTime = System.nanoTime();
				time[i][4] += (finishTime - startTime) / 1000;
				
				// --> MatchingDijkstraPQ() on adjacency lists implementation
				startTime = System.nanoTime();
				result[5] = GL.MatchingDijkstraPQ().size();
				finishTime = System.nanoTime();
				time[i][5] += (finishTime - startTime) / 1000;
				
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
					System.out.println("MatchingBellmanFord() on matrix implementation: " + result[0]);
					System.out.println("MatchingDijkstra() on matrix implementation: " + result[1]);
					System.out.println("MatchingDijkstraPQ() on matrix implementation: " + result[2]);
					System.out.println("MatchingBellmanFord() on adjacency lists implementation: " + result[3]);
					System.out.println("MatchingDijkstra() on adjacency lists implementation: " + result[4]);
					System.out.println("MatchingDijkstraPQ() on adjacency lists implementation: " + result[5]);
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
		for (int i = 0; i < v.length; i++) {
			System.out.println("--> For v = " + v[i] + " the average times were");
			System.out.println("    " + time[i][0] + " milliseconds for MatchingBellmanFord() on matrix implementation");
			System.out.println("    " + time[i][1] + " milliseconds for MatchingDijkstra() on matrix implementation");
			System.out.println("    " + time[i][2] + " milliseconds for MatchingDijkstraPQ() on matrix implementation");
			System.out.println("    " + time[i][3] + " milliseconds for MatchingBellmanFord() on adjacency lists implementation");
			System.out.println("    " + time[i][4] + " milliseconds for MatchingDijkstra() on adjacency lists implementation");
			System.out.println("    " + time[i][5] + " milliseconds for MatchingDijkstraPQ() on adjacency lists implementation");
		}
		System.out.println();
		
		boolean commaQ;
		System.out.println("Print para a Mariana:");
		//criado um arrat com m numeros aleatórios entre 0 e 1 que precisam de ser ordenados com arraysort
		
		
		
		
			System.out.print("--> BF on matrix: {");
			commaQ = false;
			for (int i = 0; i < v.length; i++) {
				if (commaQ) System.out.print(", ");
				System.out.print(time[i][0]);
				commaQ = true;
			}
			System.out.println("}");
		
		
			System.out.print("--> Dijkstra on matrix: {");
			commaQ = false;
			for (int i = 0; i < v.length; i++) {
				if (commaQ) System.out.print(", ");
				System.out.print(time[i][1]);
				commaQ = true;
			}
			System.out.println("}");
		
			System.out.print("--> Dijkstra on matrix PQ: {");
			commaQ = false;
			for (int i = 0; i < v.length; i++) {
				if (commaQ) System.out.print(", ");
				System.out.print(time[i][2]);
				commaQ = true;
	
			}
			System.out.println("}");
		
			System.out.print("--> BF on lists: {");
			commaQ = false;
			for (int i = 0; i < v.length; i++) {
				if (commaQ) System.out.print(", ");
				System.out.print(time[i][3]);
				commaQ = true;
			}
			System.out.println("}");
		
	
			System.out.print("--> Dijkstra on lists: {");
			commaQ = false;
			for (int i = 0; i < v.length; i++) {
				if (commaQ) System.out.print(", ");
				System.out.print(time[i][4]);
				commaQ = true;
			}
			System.out.println("}");
		
	
			System.out.print("--> DijkstraPQ on lists: {");
			commaQ = false;
			for (int i = 0; i < v.length; i++) {
				if (commaQ) System.out.print(", ");
				System.out.print(time[i][5]);
				commaQ = true;
			}
			System.out.println("}");
		
		
		System.out.println();
		
		
		System.out.println("PROCESS TERMINATED");
		return;
		
	}

}
