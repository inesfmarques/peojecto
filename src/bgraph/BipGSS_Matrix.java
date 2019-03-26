// Implementation of BipGSS with adjacency matrix

package bgraph;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class BipGSS_Matrix implements BipGSS {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	int source; // Label of source, (equals L+R)
	int sink; // Label of sink (equals L+R+1)
	
	// L x R matrix, entry (i,j) represents the edge from i to j+L:
	// --> Positive if edge goes from i to j+L; negative otherwise
	// --> Absolute value is the weight of edge
	int[][] E;
	
	// Array of length L, i-th entry represents edge from source to vertex i
	int[] s;
	
	// Array of length R, i-th entry represents edge from vertex i+L to sink
	int[] t;
	
	// Constructor from BipG (deep copy)
	public BipGSS_Matrix(BipG G) {
		L = G.getL();
		R = G.getR();
		source = L+R;
		sink = L+R+1;
		E = new int[L][R];
		for(int i = 0; i < L; i++) {
			for(int j = L; j < L+R; j++) {
				if (G.edgeQ(i,j)) E[i][j-L] = 1;
				else if (G.edgeQ(j,i)) E[i][j-L] = -1;
				else E[i][j-L] = 0;
			}
		}
		s = new int[L];
		for(int i = 0; i < L; i++) s[i] = 1;
		t = new int[R];
		for(int j = 0; j < R; j++) t[j] = 1;
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
	public boolean edgeQ(int l, int r) {
		if (l == source && r < L && s[r] > 0) return s[r] == 1;
		if (r == source && l < L && s[l] < 0) return s[l] == -1;
		if (r == sink && l >=L && l < L+R && t[l] > 0) return t[l] == 1;
		if (l == sink && r >=L && r < L+R && t[r] < 0) return t[r] == -1;
		if (l < L && r >= L && r < L+R && E[l][r-L] > 0) return E[l][r-L] == 1;
		if (r < L && l >= L && l < L+R && E[r][l-L] < 0) return E[r][l-L] == -1;
		return false;
	}
	
	// Adds directed edge from vertex l to vertex r
	public void addEdge(int l, int r) {
		if (l == source && r < L) s[r] = 1;
		if (r == source && l < L) s[l] = -1;
		if (r == sink && l >=L && l < L+R) t[l] = 1;
		if (l == sink && r >=L && r < L+R) t[r] = -1;
		if (l < L && r >= L && r < L+R) E[l][r-L] = 1;
		if (r < L && l >= L && l < L+R) E[l][r-L] = -1;
		return;
	}
	
	// Returns a list with the descendants of vertex v
	public ArrayList<Integer> lovers(int v) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if(v == source) {
			for(int i=0; i<L; i++) {
				if(s[i] == 1) result.add(i);
			}
		} else if(v == sink) {
			for(int i=0; i<R; i++) {
				if(t[i] == -1) result.add(i);
			}
		} else if(v < L) {
			for(int j=L; j<L+R; j++) {
				if(E[v][j-L] == 1) result.add(j);
			}
			if(s[v]==-1) result.add(source);
			
		} else if(v>=L && v<L+R) {
			for(int i=0; i<L; i++) {
				if(E[i][v-L] == -1) result.add(i);
			}
			if(t[v-L]==1) result.add(sink);
		}
		return result;
	}
	
	// Finds a path from source to sink through BFS
	public LinkedList<Integer> bfs() {
		// Queue for BFS
		Queue<Integer> queue = new LinkedList<Integer>();
		// Array where we store which vertices we've visited
		// i-th entry is:
		// --> j if vertex i was found through vertex j
		// --> -1 if vertex i was never visited
		int[] visited = new int[L+R+2];
		for(int i=0; i<L+R+2; i++) {
			visited[i] = -1;
		}
		// Variable used in the loop
		int e = 0;
		int head = 0;
		
		// Add source to queue
		queue.add(source);
		
		// BFS
		BFS:
		while(!queue.isEmpty()) {
			head = queue.poll();
			ArrayList<Integer> l = lovers(head); // Get neighbors
			for(int i = 0; i < l.size(); i++) { // Go through neighbors
				e = l.get(i);
				if(visited[e] == -1) { // Check if we've visited this neighbor
					queue.add(e);
					visited[e] = head;
					// If we're looking at the sink, break loop
					if (e == sink) break BFS;
				}
			}
		}
		
		// Build the path
		LinkedList<Integer> path = new LinkedList<Integer>(); // Where we store the path
		if(!queue.isEmpty()) {
			path.add(0, sink);
			while(e != source) {
				path.add(0, visited[e]);
				e=visited[e];
			}
		}
		return path;
	}
	
	// Finds a path from source to sink through DFS
	public LinkedList<Integer> dfs() {
		// Stack for DFS
		Stack<Integer> stack = new Stack<Integer>();
		// Array where we store which vertices we've visited
		// i-th entry is:
		// --> j if vertex i was found through vertex j
		// --> -1 if vertex i was never visited
		int[] visited = new int[L+R+2];
		for(int i=0; i<L+R+2; i++) {
			visited[i] = -1;
		}
		// Variables used in the loop
		int e = 0;
		int head = 0;
		
		// Add source to stack
		stack.push(source);
		
		// DFS
		DFS:
		while(!stack.empty()) {
			head = stack.pop();
			ArrayList<Integer> l = lovers(head); // Get neighbors
			for(int i = 0; i < l.size(); i++) { // Go through neighbors
				e = l.get(i);
				if(visited[e] == -1) { // Check if we've visited this neighbor
					stack.push(e);
					visited[e] = head;
					// If we're looking at the sink, break loop
					if (e == sink) break DFS;
				}
			}
		}
		
		// Build the path
		LinkedList<Integer> path = new LinkedList<Integer>(); // Where we store the path
		if(!stack.empty()) {
			path.add(0, sink);
			while(e != source) {
				path.add(0, visited[e]);
				e=visited[e];
			}
		}
		return path;
	}
	// Receives a list of vertices that form a path from source to sink and inverts it
	public void invertPath(LinkedList<Integer> path) {
		// First vertex is the sink
		path.removeFirst();
		s[path.getFirst()] = -1;
		int i,j;
		while(path.size() != 2) {
			i = path.getFirst();
			j = path.get(1);
			if (i<j) E[i][j-L] = -E[i][j-L];
			else E[j][i-L] = -E[j][i-L];
			path.removeFirst();
		}
		// Last edge is the source
		t[path.getFirst()-L] = -1;
		
		// Make path empty
		path.clear();
		
		return;
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
				string += "--> " + i + " <---[" + -E[i][j-L] + "]--- " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		// Add edges with source
		b = true;
		string += "Edges regarding source:";
		for(int i = 0; i < L; i++) {
			if (s[i] == 0) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (s[i] > 0) string += "--> " + source + " ---[" + s[i] + "]---> " + i + "\n";
			if (s[i] < 0) string += "--> " + source + " <---[" + -s[i] + "]--- " + i + "\n";
		}
		if (b) string += " None!\n";
		
		// Add edges with sink
		b = true;
		string += "Edges regarding sink:";
		for(int i = L; i < L+R; i++) {
			if (t[i-L] == 0) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (t[i-L] > 0) string += "--> " + i + " ---[" + t[i-L] + "]---> " + sink + "\n";
			if (t[i-L] < 0) string += "--> " + i + " <---[" + -t[i-L] + "]--- " + sink + "\n";
		}
		if (b) string += " None!\n";
		
		return string;
	}

}
