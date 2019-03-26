// Implementation of BipWGSS with adjacency matrix

package bwgraph;

import java.util.ArrayList;

public class BipWGSS_Matrix implements BipWGSS {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	int source; // Label of source (equals L+R)
	int sink; // Label of sink (equals L+R+1)
	
	// L x R x 2 matrix
	// Entry (i,j) is the pair [d, w] and represents the edge from i to j+L:
	// --> d is the direction: 1 if (i)--->(j+L); -1 if (i)<---(j+L); 0 if no edge exists
	// --> w is the weight of the edge
	int[][][] E;
	
	// L x 2 matrix, i-th entry is the pair [d, w] and represents edge from source to vertex i
	// --> d is the direction: 1 if (source)--->(i); -1 if (source)<---(i); 0 if no edge exists
	// --> w is the weight of the edge
	int[][] s;
	
	// R x 2 matrix, i-th entry is the pair [d, w] and represents edge from vertex i to sink
	// --> d is the direction: 1 if (i)--->(sink); -1 if (i)<---(sink); 0 if no edge exists
	// --> w is the weight of the edge
	int[][] t;
	
	// Constructor from BipG (deep copy)
	public BipWGSS_Matrix(BipWG G) {
		L = G.getL();
		R = G.getR();
		source = L+R;
		sink = L+R+1;
		E = new int[L][R][2];
		for(int i = 0; i < L; i++) {
			for(int j = L; j < L+R; j++) {
				if (G.edgeQ(i, j)) {
					E[i][j-L][0] = 1;
					E[i][j-L][1] = G.getWeight(i, j);
				}
				else if (G.edgeQ(j, i)) {
					E[i][j-L][0] = -1;
					E[i][j-L][1] = G.getWeight(j, i);
				}
			}
		}
		s = new int[L][2];
		for(int i = 0; i < L; i++) {
			s[i][0] = 1;
			s[i][1] = 0;
		}
		t = new int[R][2];
		for(int j = 0; j < R; j++) {
			t[j][0] = 1;
			t[j][1] = 0;
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
	public int getWeight(int l, int r) {
		if (l == source && r < L && s[r][0] == 1) return s[r][1];
		else if (r == source && l < L && s[l][0] == -1) return s[l][1];
		else if (r == sink && l >=L && l < L+R && t[l][0] == 1) return t[l][1];
		else if (l == sink && r >=L && r < L+R && t[r][0] == -1) return t[r][1];
		else if (l < L && r >= L && r < L+R && E[l][r-L][0] == 1) return E[l][r-L][1];
		else if (r < L && l >= L && l < L+R && E[r][l-L][0] == -1) return E[r][l-L][1];
		else return 0;
	}
	
	// Adds directed edge from vertex l to vertex r with weight w
	public void addEdge(int l, int r, int w) {
		if (l == source && r < L) {
			s[r][0] = 1;
			s[r][1] = w;
		}
		else if (r == source && l < L) {
			s[l][0] = -1;
			s[l][1] = w;
		}
		else if (r == sink && l >=L && l < L+R) {
			t[l][0] = 1;
			t[l][1] = w;
		}
		else if (l == sink && r >=L && r < L+R) {
			t[r][0] = -1;
			t[r][1] = w;
		}
		else if (l < L && r >= L && r < L+R) {
			E[l][r-L][0] = 1;
			E[l][r-L][1] = w;
		}
		else if (r < L && l >= L && l < L+R) {
			E[l][r-L][0] = -1;
			E[l][r-L][1] = w;
		}
		return;
	}
	
	// Returns True if there is an edge from l to r, False otherwise
	public boolean edgeQ(int l, int r) {
		if (l == source && r < L) return s[r][0] == 1;
		if (r == source && l < L) return s[l][0] == -1;
		if (r == sink && l >=L && l < L+R) return t[l][0] == 1;
		if (l == sink && r >=L && r < L+R) return t[r][0] == -1;
		if (l < L && r >= L && r < L+R) return E[l][r-L][0] == 1;
		if (r < L && l >= L && l < L+R) return E[l][r-L][0] == -1;
		return false;
	}
	
	// Returns a list with the descendants of vertex v
	public ArrayList<Integer> lovers(int v) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if(v == source) {
			for(int i=0; i<L; i++) {
				if(s[i][0] == 1) result.add(i);
			}
		} else if(v == sink) {
			for(int i=0; i<R; i++) {
				if(t[i][0] == -1) result.add(i);
			}
		} else if(v<L) {
			for(int j=L; j<L+R; j++) {
				if(E[v][j-L][0] == 1) result.add(j);
			}
			if(s[v][0] == -1) result.add(source);
			
		} else if(v>=L && v<L+R) {
			for(int i=0; i<L; i++) {
				if(E[i][v-L][0] == -1) result.add(i);
			}
			if(t[v-L][0] == 1) result.add(sink);
		}
		return result;
	}
	
	// toString method
	public String toString() {
		String string = "Number of vertices in set 1: " + L + "\nNumber of vertices in set 2: " + R + "\nSource id: " + source + "\nSink id: " + sink + "\n";
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
				string += "--> " + i + " <---[" + E[i][j-L][1] + "]--- " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		// Add edges with source
		b = true;
		string += "Edges regarding source:";
		for(int i = 0; i < L; i++) {
			if (s[i][0] == 0) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (s[i][1] == 1) string += "--> " + source + " ---[" + s[i][1] + "]---> " + i + "\n";
			if (s[i][1] == -1) string += "--> " + source + " <---[" + s[i][1] + "]--- " + i + "\n";
		}
		if (b) string += " None!\n";
		
		// Add edges with sink
		b = true;
		string += "Edges regarding sink:";
		for(int i = L; i < L+R; i++) {
			if (t[i-L][0] == 0) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (t[i-L][0] == 1) string += "--> " + i + " ---[" + t[i-L][1] + "]---> " + sink + "\n";
			if (t[i-L][0] == -1) string += "--> " + i + " <---[" + t[i-L][1] + "]--- " + sink + "\n";
		}
		if (b) string += " None!\n";
		
		return string;
	}

}
