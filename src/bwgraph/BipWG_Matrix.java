// Implementation of BipWG with adjacency matrix

package bwgraph;

import java.util.ArrayList;
import java.util.Arrays;

public class BipWG_Matrix implements BipWG{
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	
	// L x R x 2 matrix
	// Entry (i,j) is the pair [d, w] and represents the edge from i to j+L:
	// --> d is the direction: 1 if (i)--->(j+L); -1 if (i)<---(j+L); 0 if no edge exists
	// --> w is the weight of the edge
	double[][][] E;
	
	// Constructor
	public BipWG_Matrix(int n, int m) {
		L = n;
		R = m;
		E = new double[L][R][2];
		for(int i = 0; i < L; i++) {
			for(int j = 0; j < R; j++) {
				for(int k = 0; k < 2; k++) E[i][j][k] = 0;
			}
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
	
	// Returns weight of edge from i to j
	public double getWeight(int l, int r) {
		if(l < L && r >= L && r < L+R && E[l][r-L][0] == 1) return E[l][r-L][1];
		if(r < L && l >= L && l < L+R && E[r][l-L][0] == -1) return E[r][l-L][1];
		return 0;
	}
	
	// Adds directed edge from vertex l to vertex r with weight w
	public void addEdge(int l, int r, double w) {
		if(l < L && r >= L && r < L+R) {
			E[l][r-L][0] = 1;
			E[l][r-L][1] = w;	
		}
		if(r < L && l >= L && l < L+R) {
			E[r][l-L][0] = -1;
			E[r][l-L][1] = w;
		}
		return;
	}
	
	// Returns True if there is an edge from l to r, False otherwise
	public boolean edgeQ(int l, int r) {
		if (l < L && r >= L && r < L+R) return E[l][r-L][0] == 1;
		if (r < L && l >= L && l < L+R) return E[r][l-L][0] == -1;
		return false;
	}
	
	// Returns a list with the descendants of vertex v
	public ArrayList<Integer> lovers(int v){
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (v < L) {
			for(int j=L; j<L+R; j++) {
				if(edgeQ(v, j)) result.add(j);
			}
		} else if (v >= L && v < L+R) {
			for(int i=0; i<L; i++) {
				if(edgeQ(i, v)) result.add(i);
			}
		}
		return result;
	}
	
	// Finds a maximum bipartite matching using MaxFlow and BellmanFord
	public ArrayList<int[]> MatchingBellmanFord() {
		// Graph with source and sink added
		BipWGSS ResidualGraph = new BipWGSS_Matrix(this);
		
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
		BipWGSS ResidualGraph = new BipWGSS_Matrix(this);
		
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
			if (E[i][j-L][0] != 1) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			string += "--> " + i + " ---[" + E[i][j-L][1] + "]---> " + j + "\n";
		}
	}
	if (b) string += " None!\n";
	
	// Add edges from set 2 to set 1
	b = true;
	string += "Edges from set 2 to set 1:";
	for(int i = 0; i < L; i++) {
		for(int j = L; j < L+R; j++) {
			if (E[i][j-L][0] != -1) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			string += "--> " + i + " <---[" + E[i][j-L][1] + "]---> " + j + "\n";
		}
	}
	if (b) string += " None!\n";
		
	return string;
	}
}
