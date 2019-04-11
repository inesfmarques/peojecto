// Implementation of bipartite graph
// Used for class MatroidIntersector
// The ones used in package bgraph don't work because there can't be edges i ---> j and j ---> i

package matroid;

import java.util.ArrayList;

public class BipGMI {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	
	// Array of adjacency lists
	// The i-th position is a list with the neighbors of vertex i
	ArrayList<Integer>[] adjacencyList;
	
	// Constructor
	public BipGMI(int n, int m) {
		L = n;
		R = m;
		adjacencyList = new ArrayList[L+R];
		for (int i = 0; i < L+R; i++) adjacencyList[i] = new ArrayList<Integer>();
	}
	
	public int getL() {
		return L;
	}

	public int getR() {
		return R;
	}

	public void addEdge(int l, int r) {
		if (!adjacencyList[l].contains(r)) adjacencyList[l].add(r);
		return;
	}

	public void addEdge(int l, int[] rArray) {
		for (int i = 0; i < rArray.length; i++) addEdge(l, rArray[i]);
		return;
	}

	public boolean edgeQ(int l, int r) {
		return adjacencyList[l].contains(r);
	}

	public ArrayList<Integer> lovers(int v) {
		return adjacencyList[v];
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
				string += i + " ---> " + j + "\n";
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
				string += i + " <--- " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		return string;
	}
}
