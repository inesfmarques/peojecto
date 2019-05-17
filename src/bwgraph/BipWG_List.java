// Implementation of BipWG with adjacency lists

package bwgraph;

import java.util.ArrayList;

public class BipWG_List implements BipWG {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	
	// Array of adjacency lists
	// The i-th position is a list with the neighbors of vertex i
	ArrayList<Integer>[] adjacencyList;
	
	// Array of the weights of edges
	// The i-th position corresponds to the weights of the edges leaving i
	// The edge is the same as the corresponding in adjacencyList
	ArrayList<Double>[] weightsList;
	
	// Constructor
	public BipWG_List(int n, int m) {
		L = n;
		R = m;
		
		adjacencyList = new ArrayList[L+R];
		weightsList = new ArrayList[L+R];
		for (int i = 0; i < L+R; i++) {
			adjacencyList[i] = new ArrayList<Integer>();
			weightsList[i] = new ArrayList<Double>();
		}
	}

	// Return size of left set
	public int getL() {
		return L;
	}

	// Return size of right set
	public int getR() {
		return R;
	}
	
	// Return weight of edge from l to r
	public double getWeight(int l, int r) {
		// Try to find edge from l to r
		int i = 0;
		ArrayList<Integer> list = adjacencyList[l];
		int I = list.size();
		while (i < I && list.get(i) != r) i++;
		
		if (i == I) return 0;
		else return weightsList[l].get(i);
	}

	// Adds directed edge from vertex l to vertex r with weight w
	public void addEdge(int l, int r, double w) {
		// Try to find edge from l to r
		int i = 0;
		ArrayList<Integer> list = adjacencyList[l];
		int I = list.size();
		while (i < I && list.get(i) != r) i++;
		
		// If an already existing edge was found, replace its weight
		if (i < I) weightsList[l].set(i, w);
		
		// If no edge was found, add it and remove edge from r to l, if it exists
		else {
			list.add(r);
			weightsList[l].add(w);
			
			list = adjacencyList[r];
			I = list.size();
			for (i = 0; i < I; i++) {
				if (list.get(i) == l) {
					// We've found an r --> l edge. Remove it and we're done
					list.remove(i);
					weightsList[r].remove(i);
					return;
				}
			}
		}
		
		return;
	}
	
	public void addEdge(int l, int[] rArray, double[] wArray) {
		if(rArray.length == wArray.length) {
			for (int i = 0; i < rArray.length; i++) this.addEdge(l, rArray[i], wArray[i]);
		}
		return;
	}

	// Returns 'true' if there is an edge from l to r, 'false' otherwise
	public boolean edgeQ(int l, int r) {
		return adjacencyList[l].contains(r);
	}

	// Returns a list with the descendants of vertex v
	public ArrayList<Integer> lovers(int v) {
		return adjacencyList[v];
	}
	
	// Finds a maximum bipartite matching using MaxFlow and BellmanFord
	public ArrayList<int[]> MatchingBellmanFord() {
		// Graph with source and sink added
		BipWGSS ResidualGraph = new BipWGSS_List(this);
		
		// BF: get distances to source
		double[] dist = ResidualGraph.BellmanFord();
		
		// Repeat DFS + BF until we can't reach the sink
		while(dist[ResidualGraph.getSink()] != Double.MAX_VALUE) {
			while(ResidualGraph.invertPathBF(ResidualGraph.getSource(), dist)) {
				ResidualGraph.setVisited();
			}
			dist = ResidualGraph.BellmanFord();
			ResidualGraph.setVisited();
		}
		
		// Create list with results
		ArrayList<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < ResidualGraph.getL(); i++) {
			for (int j = ResidualGraph.getL(); j < ResidualGraph.getL() + ResidualGraph.getR(); j++) {
				if (ResidualGraph.edgeQ(j, i)) {
					result.add(new int[] {i,j});
					continue;
				}
			}
		}
		
		return result;
	}
	
	// Finds a maximum bipartite matching using MaxFlow and Dijkstra
	public ArrayList<int[]> MatchingDijkstra() {
		// Graph with source and sink added
		BipWGSS ResidualGraph = new BipWGSS_List(this);
		
		// BF: get distances to source
		double[] h = ResidualGraph.BellmanFord();
		while(ResidualGraph.invertPathBF(ResidualGraph.getSource(), h)) {
			ResidualGraph.setVisited();
		}
		ResidualGraph.setVisited();
		
		//Apply Dijkstra with changed weights
		double[] dist = ResidualGraph.Dijkstra(h);
		// Repeat DFS + Dijkstra until we can't reach the sink
		while(dist[ResidualGraph.getSink()] != Double.MAX_VALUE) {
			while(ResidualGraph.invertPathDijkstra(ResidualGraph.getSource(), dist, h)) {
				ResidualGraph.setVisited();
			}
			h = dist;
			dist = ResidualGraph.Dijkstra(h);
			ResidualGraph.setVisited();
		}
		
		// Create list with results
		ArrayList<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < ResidualGraph.getL(); i++) {
			for (int j = ResidualGraph.getL(); j < ResidualGraph.getL() + ResidualGraph.getR(); j++) {
				if (ResidualGraph.edgeQ(j, i)) {
					result.add(new int[] {i,j});
					continue;
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<int[]> MatchingDijkstraPQ() {
		// Graph with source and sink added
		BipWGSS ResidualGraph = new BipWGSS_Matrix(this);
		
		// BF: get distances to source
		double[] h = ResidualGraph.BellmanFord();
		while(ResidualGraph.invertPathBF(ResidualGraph.getSource(), h)) {
			ResidualGraph.setVisited();
		}
		ResidualGraph.setVisited();
		
		//Apply Dijkstra with changed weights
		double[] dist = ResidualGraph.DijkstraPQ(h);
		// Repeat DFS + Dijkstra until we can't reach the sink
		while(dist[ResidualGraph.getSink()] != Double.MAX_VALUE) {
			while(ResidualGraph.invertPathDijkstra(ResidualGraph.getSource(), dist, h)) {
				ResidualGraph.setVisited();
			}
			h = dist;
			dist = ResidualGraph.DijkstraPQ(h);
			ResidualGraph.setVisited();
		}
		
		// Create list with results
		ArrayList<int[]> result = new ArrayList<int[]>();
		for (int i = 0; i < ResidualGraph.getL(); i++) {
			for (int j = ResidualGraph.getL(); j < ResidualGraph.getL() + ResidualGraph.getR(); j++) {
				if (ResidualGraph.edgeQ(j, i)) {
					result.add(new int[] {i,j});
					continue;
				}
			}
		}
		
		return result;
	}

	// toString method
	public String toString() {
		String string = "Number of vertices in set 1: " + L + "\nNumber of vertices in set 2: " + R + "\n";
		boolean b;
		
		// Add edges from set 1 to set 2
		b = true;
		string += "Edges from set 1 to set 2:";
		for(int i = 0; i < L; i++) {
			for(int j = L; j < L+R; j++) {
				if (!edgeQ(i, j)) continue;
				if (b) {
					string += "\n";
					b = false;
				}
				string += "--> " + i + " ---[" + getWeight(i, j) + "]---> " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		// Add edges from set 2 to set 1
		b = true;
		string += "Edges from set 2 to set 1:";
		for(int i = 0; i < L; i++) {
			for(int j = L; j < L+R; j++) {
				if (!edgeQ(j, i)) continue;
				if (b) {
					string += "\n";
					b = false;
				}
				string += "--> " + i + " <---[" + getWeight(j, i) + "]---> " + j + "\n";
			}
		}
		if (b) string += " None!\n";
			
		return string;
	}

}
