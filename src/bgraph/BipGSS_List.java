// Implementation of BipGSS with adjacency lists

package bgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BipGSS_List implements BipGSS {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	int source; // Label of source (equals L+R)
	int sink; // Label of sink (equals L+R+1)
	
	// Array of adjacency lists
	// The i-th position is a list with the neighbors of vertex i
	ArrayList<Integer>[] adjacencyList;
	
	// Constructor from BipG (deep copy)
	public BipGSS_List(BipG G) {
		L = G.getL();
		R = G.getR();
		source = L+R;
		sink = L+R+1;
		
		adjacencyList = new ArrayList[L+R+2];
		for (int i = 0; i < L+R; i++) {
			adjacencyList[i] = new ArrayList<Integer>(G.lovers(i));
			if (i >= L) adjacencyList[i].add((Integer) sink);
		}
		
		adjacencyList[L+R] = new ArrayList<Integer>();
		for (int i = 0; i < L; i++) adjacencyList[L+R].add((Integer) i);
		
		adjacencyList[L+R+1] = new ArrayList<Integer>();
	}
	
	// Return size of left set
	public int getL() {
		return L;
	}

	// Return size of right set
	public int getR() {
		return R;
	}

	// Return id of source
	public int getSource() {
		return source;
	}

	// Return id of sink
	public int getSink() {
		return sink;
	}
	
	// Adds directed edge from vertex l to vertex r
	public void addEdge(int l, int r) {
		if (!adjacencyList[l].contains(r)) {
			adjacencyList[l].add(r);
			adjacencyList[r].remove((Integer) l);
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

	// Finds a path from source to sink through BFS
	public ArrayList<Integer> bfs() {
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
				if(visited[e] == -1) { // Check if we've visited this vertex
					queue.add(e);
					visited[e] = head;
					// If we're looking at the sink, break loop
					if (e == sink) break BFS;
				}
			}
		}
		
		// Build the path
		ArrayList<Integer> path = new ArrayList<Integer>(); // Where we store the path
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
	public ArrayList<Integer> dfs() {
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
			for(int i = l.size() - 1; i > -1; i--) { // Go through neighbors
				e = l.get(i);
				if(visited[e] == -1) { // Check if we've visited this vertex
					stack.push(e);
					visited[e] = head;
					// If we're looking at the sink, break loop
					if (e == sink) break DFS;
				}
			}
		}
		
		// Build the path
		ArrayList<Integer> path = new ArrayList<Integer>(); // Where we store the path
		if(!stack.empty()) {
			path.add(0, sink);
			while(e != source) {
				path.add(0, visited[e]);
				e=visited[e];
			}
		}
		return path;
	}
	
	// Auxiliary method for Hopcroft-Karp: BFS phase
	public int[] HopcroftKarpBFS() {
		// Array with the distances
		int[] dist = new int[sink+1];
		for(int i = 0; i < sink+1; i++) dist[i] = -1;
		dist[source] = 0;
		
		// Queue used for BFS
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(source);
		
		// Variables used in BFS
		int head, v;
		ArrayList<Integer> l;
		
		// BFS
		while(!queue.isEmpty()) {
			head = queue.poll();
			l = lovers(head); // Get neighbors
			for(int i = 0; i < l.size(); i++) { // Go through neighbors
				v = l.get(i);
				
				// We update v and add him to the queue if the following occur:
				// --> We haven't visited it
				// --> The source hasn't been visited or dist(source, v) <= dist(source, sink)
				if(dist[v] == -1 && (dist[sink] == -1 || dist[head] < dist[sink])) {
					dist[v] = dist[head] + 1;
					
					// We only need to add v to the queue if dist(source, v) < dist(source, sink)
					if(dist[sink] == -1 || dist[v] < dist[sink]) queue.add(v);
				}
			}
		}
		
		return dist;
	}	
	
	// Auxiliary method for HopcroftKarp: DFS phase
	public boolean HopcroftKarpDFS(int v, int[] dist) {
		// Case v == sink
		if (v == sink) return true;
		
		// Case v == source
		if (v == source) {
			// Go through the neighbors, reverse the edge if a path was found
			ArrayList<Integer> l = lovers(v);
			for (int i = 0; i < l.size(); i++) {
				if (HopcroftKarpDFS(l.get(i), dist)) addEdge(l.get(i), v);
			}
			
			return true; // BFS done before guarantees there will be a path
		}
		
		// Case v != source, sink
		// To save some recursive calls, we'll use the fact vertices in the second set only have one neighbor
		// Therefore, assume v is in the first set
		else {
			ArrayList<Integer> l = lovers(v); // List of neighbors
			int n, nn = 0; // Variables we'll use
			
			// Go through neighbors
			for (int i = 0; i < l.size(); i++) {
				n = l.get(i); // The current neighbor of v we're looking at
				nn = lovers(n).get(0); // The (only) neighbor of n
				
				// If dist[nn] is correct and DFS starting at nn finds a path,
				// we reverse the edges and return 'true'
				if (dist[nn] == dist[v] + 2 && HopcroftKarpDFS(nn, dist)) {
					addEdge(nn, n);
					addEdge(n, v);
					return true;
				}
			}
			
			// If there is no path from any of the neighbors, then we return false
			// To avoid returning here (in another DFS call), we change dist[v] to -1
			dist[v] = -1;
			return false;
		}
	}
	
	// Receives a list of vertices that form a path from source to sink and inverts it
	public void invertPath(ArrayList<Integer> path) {
		for (int i = 0; i < path.size() - 1; i++) addEdge(path.get(i+1), path.get(i));
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
		
		// Add edges with source
		b = true;
		string += "Edges regarding source:";
		for(int i = 0; i < L; i++) {
			if (!edgeQ(source, i) && !edgeQ(i, source)) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (edgeQ(source, i)) string += source + " ---> " + i + "\n";
			if (edgeQ(i, source)) string += source + " <--- " + i + "\n";
		}
		if (b) string += " None!\n";
		
		// Add edges with sink
		b = true;
		string += "Edges regarding sink:";
		for(int i = L; i < L+R; i++) {
			if (!edgeQ(i, sink) && !edgeQ(sink, i)) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (edgeQ(i, sink)) string += i + " ---> " + sink + "\n";
			if (edgeQ(sink, i)) string += i + " <--- " + sink + "\n";
		}
		if (b) string += " None!\n";
		
		return string;
	}

}