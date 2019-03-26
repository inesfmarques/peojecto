// Implementation of BipG with adjacency matrix

package bgraph;

import java.util.ArrayList;
import java.util.LinkedList;

public class BipG_Matrix implements BipG{
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	
	// L x R matrix
	// Entry (i,j) represents the edge from i to j+L: 1 if edge goes from i to j+L; -1 otherwise
	// --> Absolute value is the weight of edge
	int[][] E;
	
	// Constructor
	public BipG_Matrix(int n, int m) {
		L = n;
		R = m;
		E = new int[L][R];
		for(int i = 0; i < L; i++) {
			for(int j = 0; j < R; j++) {
				E[i][j] = 0;
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
	
	// Adds directed edge from vertex l to vertex r
	public void addEdge(int l, int r) {
		if(l < L && r >= L && r < L+R) E[l][r-L] = 1;	
		if(r < L && l >= L && l > L+R) E[r][l-L] = -1;
		return;
	}
	
	// Adds directed edges from vertex l to vertices in array r_array
	public void addEdge(int l, int[] r_array) {
		for (int i = 0; i < r_array.length; i++) this.addEdge(l, r_array[i]);
		return;
	}
	
	// Returns 'true' if there is an edge from l to r, 'false' otherwise
	public boolean edgeQ(int l, int r) {
		if(l < L && r >= L && r < L+R) return E[l][r-L] == 1;
		if(r < L && l >= L && l < L+R) return E[r][l-L] == -11;
		return false;
	}
	
	// Returns a list with the descendants of vertex v
	public ArrayList<Integer> lovers(int v){
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if (v < L) {
			for(int j=L; j<L+R; j++) {
				if(E[v][j-L] > 0) result.add(j);
			}
		} else if (v >= L && v < L+R) {
			for(int i=0; i<L; i++) {
				if(E[i][v-L] < 0) result.add(i);
			}
		}
		return result;
	}
	
	// Finds a maximum bipartite matching using Edmonds-Karp
	public LinkedList<int[]> EdmondsKarp() {
		
		// Graph with source and sink added
		BipGSS_Matrix ResidualGraph = new BipGSS_Matrix(this);
		
		// Do the cycle: find a path, then reverse it
		LinkedList<Integer> path = ResidualGraph.bfs();		
		while(!path.isEmpty()) {
			ResidualGraph.invertPath(path);
			path = ResidualGraph.bfs();
		}		
		// Create list with results
		LinkedList<int[]> result = new LinkedList<int[]>();
		for (int i = 0; i < ResidualGraph.getL(); i++) {
			for (int j = ResidualGraph.L; j < ResidualGraph.getL() + ResidualGraph.getR(); j++) {
				if (ResidualGraph.edgeQ(j, i)) {
					result.add(new int[] {i,j});
					continue;
				}
			}
		}
		return result;
	}
	
	public LinkedList<int[]> FordFulkerson() {
		// Graph with source and sink added
		BipGSS_Matrix ResidualGraph = new BipGSS_Matrix(this);
		
		// Do the cycle: find a path, then reverse it
		LinkedList<Integer> path = ResidualGraph.dfs();		
		while(!path.isEmpty()) {
			ResidualGraph.invertPath(path);
			path = ResidualGraph.dfs();
		}		
		// Create list with results
		LinkedList<int[]> result = new LinkedList<int[]>();
		for (int i = 0; i < ResidualGraph.getL(); i++) {
			for (int j = ResidualGraph.L; j < ResidualGraph.getL() + ResidualGraph.getR(); j++) {
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
				if (E[i][j-L] <= 0) continue;
				if (b) {
					string += "\n";
					b = false;
				}
				string += "--> " + i + " ---[" + E[i][j-L] + "]---> " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		// Add edges from set 2 to set 1
		b = true;
		string += "Edges from set 2 to set 1:";
		for(int i = 0; i < L; i++) {
			for(int j = L; j < L+R; j++) {
				if (E[i][j-L] >= 0) continue;
				if (b) {
					string += "\n";
					b = false;
				}
				string += "--> " + i + " <---[" + -E[i][j-L] + "]---> " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		return string;
	}
}
