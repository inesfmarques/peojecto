// Implementation of BipWGSS with adjacency matrix

package bwgraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BipWGSS_Matrix implements BipWGSS {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	int source; // Label of source (equals L+R)
	int sink; // Label of sink (equals L+R+1)
	boolean[] visited; //array of visited vertices for the DFS
	
	// L x R x 2 matrix
	// Entry (i,j) is the pair [d, w] and represents the edge from i to j+L:
	// --> d is the direction: 1 if (i)--->(j+L); -1 if (i)<---(j+L); 0 if no edge exists
	// --> w is the weight of the edge
	double[][][] E;
	
	// L x 2 matrix, i-th entry is the pair [d, w] and represents edge from source to vertex i
	// --> d is the direction: 1 if (source)--->(i); -1 if (source)<---(i); 0 if no edge exists
	// --> w is the weight of the edge
	double[][] s;
	
	// R x 2 matrix, i-th entry is the pair [d, w] and represents edge from vertex i to sink
	// --> d is the direction: 1 if (i)--->(sink); -1 if (i)<---(sink); 0 if no edge exists
	// --> w is the weight of the edge
	double[][] t;
	
	// Constructor from BipG (deep copy)
	public BipWGSS_Matrix(BipWG G) {
		L = G.getL();
		R = G.getR();
		source = L+R;
		sink = L+R+1;
		visited = new boolean[L+R+2];
		E = new double[L][R][2];
		for(int i = 0; i < L; i++) {
			for(int j = L; j < L+R; j++) {
				if (G.edgeQ(i, j)) {
					E[i][j-L][0] = 1;
					E[i][j-L][1] = -G.getWeight(i, j);
				}
				else if (G.edgeQ(j, i)) {
					E[i][j-L][0] = -1;
					E[i][j-L][1] = -G.getWeight(j, i);
				}
			}
		}
		s = new double[L][2];
		for(int i = 0; i < L; i++) {
			s[i][0] = 1;
			s[i][1] = 0;
		}
		t = new double[R][2];
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
	
	// Return id of source
	public int getSource() {
		return source;
	}
	
	// Return id of sink
	public int getSink() {
		return sink;
	}
	
	//Reset visited
	public void setVisited() {
		visited = new boolean[L+R+2];
	}
	
	// Returns weight of edge from i to j
	public double getWeight(int l, int r) {
		if (l == source && r < L && s[r][0] == 1) return s[r][1];
		else if (r == source && l < L && s[l][0] == -1) return s[l][1];
		else if (r == sink && l >=L && l < L+R && t[l-L][0] == 1) return t[l-L][1];
		else if (l == sink && r >=L && r < L+R && t[r-L][0] == -1) return t[r-L][1];
		else if (l < L && r >= L && r < L+R && E[l][r-L][0] == 1) return E[l][r-L][1];
		else if (r < L && l >= L && l < L+R && E[r][l-L][0] == -1) return E[r][l-L][1];
		else return 0;
	}
	
	// Adds directed edge from vertex l to vertex r with weight w
	public void addEdge(int l, int r, double w) {
		if (l == source && r < L) {
			s[r][0] = 1;
			s[r][1] = w;
		}
		else if (r == source && l < L) {
			s[l][0] = -1;
			s[l][1] = w;
		}
		else if (r == sink && l >=L && l < L+R) {
			t[l-L][0] = 1;
			t[l-L][1] = w;
		}
		else if (l == sink && r >=L && r < L+R) {
			t[r-L][0] = -1;
			t[r-L][1] = w;
		}
		else if (l < L && r >= L && r < L+R) {
			E[l][r-L][0] = 1;
			E[l][r-L][1] = w;
		}
		else if (r < L && l >= L && l < L+R) {
			E[r][l-L][0] = -1;
			E[r][l-L][1] = w;
		}
		return;
	}
	
	// Returns True if there is an edge from l to r, False otherwise
	public boolean edgeQ(int l, int r) {
		if (l == source && r < L) return s[r][0] == 1;
		if (r == source && l < L) return s[l][0] == -1;
		if (r == sink && l >=L && l < L+R) return t[l-L][0] == 1;
		if (l == sink && r >=L && r < L+R) return t[r-L][0] == -1;
		if (l < L && r >= L && r < L+R) return E[l][r-L][0] == 1;
		if (r < L && l >= L && l < L+R) return E[r][l-L][0] == -1;
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
	
	public boolean invertPathBF(int v, double[] dist) {
		//Mark v as visited
		visited[v] = true;
		
		// Case v == sink
		if (v == sink) return true;
		
		// Case v == source
		if (v == source) {
			// Go through the neighbors, reverse the edge if a path was found
			ArrayList<Integer> l = lovers(v);
			for (int i = 0; i < l.size(); i++) {
				int lover = l.get(i);
				if (!visited[lover]) {
					if(invertPathBF(lover, dist)) {
						addEdge(lover, v, -getWeight(v,lover));
						return true;
					}
				}
			}
			return false;
		}
		
		// Case v != source, sink
		// To save some recursive calls, we'll use the fact vertices in the second set only have one neighbor
		// Therefore, assume v is in the first set
		else {
			ArrayList<Integer> l = lovers(v); // List of neighbors			
			// Go through neighbors
			for (int i = 0; i < l.size(); i++) {
				int lover = l.get(i); // The current neighbor of v we're looking at
				double w = getWeight(v,lover);
				// If dist[lover] is correct and DFS starting at lover finds a path,
				// we reverse the edges and return 'true'
				if (!visited[lover] && dist[lover] == dist[v] + w) {
					if(invertPathBF(lover, dist)) {
						addEdge(lover, v, -w);
						return true;
					}
				}
			}
			// If there is no path from any of the neighbors then we return false
			return false;
		}
	}
	
	public double[] BellmanFord(){
		double[] dist = new double[L+R+2]; //lista das distancias a source
		for(int i=0; i<L+R+2; i++) {
			dist[i] = Double.MAX_VALUE;
		}
		
		dist[source] = 0;
		Queue<Integer> vertices = new LinkedList<Integer>(); //lista com vertices para a primeira iteracao
		vertices.add(source);
		for(int i=0; i<L+R; i++) {
			vertices.add(i);
		}
		for(int i=0; i<L+R+1; i++) {
			Queue<Integer> changedv = new LinkedList<Integer>(); //lista com vertices cujo dist mudou
			while(!vertices.isEmpty()) {
				int e = vertices.poll();
				//relaxar arestas
				ArrayList<Integer> l = lovers(e);
				for(int j=0; j<l.size(); j++) {
					int u = e;
					int v = l.get(j);
					double w = getWeight(u,v);
					if(dist[u] != Double.MAX_VALUE && dist[u] + w < dist[v]) {
						dist[v] = dist[u] + w;
						if(v != sink) changedv.add(v); //se dist mudou adicionar a changedv
					}
				}
			}
			if(changedv.isEmpty()) break;
			vertices = changedv;
		}
		return dist;
	}
	
	public boolean invertPathDijkstra(int v, double[] dist, double[] h) {
		//Mark v as visited
		visited[v] = true;
		
		// Case v == sink
		if (v == sink) return true;
		
		// Case v == source
		if (v == source) {
			// Go through the neighbors, reverse the edge if a path was found
			ArrayList<Integer> l = lovers(v);
			for (int i = 0; i < l.size(); i++) {
				int lover = l.get(i);
				if (!visited[lover]) {
					if(invertPathDijkstra(lover, dist, h)) {
						addEdge(lover, v, -getWeight(v,lover));
						return true;
					}
				}
			}
			return false;
		}
		
		// Case v != source, sink
		// To save some recursive calls, we'll use the fact vertices in the second set only have one neighbor
		// Therefore, assume v is in the first set
		else {
			ArrayList<Integer> l = lovers(v); // List of neighbors			
			// Go through neighbors
			for (int i = 0; i < l.size(); i++) {
				int lover = l.get(i); // The current neighbor of v we're looking at
				double w = getWeight(v,lover) + h[v] - h[lover];
				// If dist[lover] is correct and DFS starting at lover finds a path,
				// we reverse the edges and return 'true'
				if (!visited[lover] && dist[lover] == dist[v] + w) {
					if(invertPathDijkstra(lover, dist, h)) {
						addEdge(lover, v, -getWeight(v,lover));
						return true;
					}
				}
			}
			// If there is no path from any of the neighbors then we return false
			return false;
		}
	}


	private int getMinDist(double[] dist, boolean[] seen) {
		double MinDist = Double.MAX_VALUE;
		int MinNode = -1;
	    for (int i=0; i<seen.length; i++) {
	    	double d = dist[i];
	        if (!seen[i] && d < MinDist) {
	            MinDist = d;
	            MinNode = i;
	        }
	    }
	    return MinNode;
	}
	
	public double[] Dijkstra(double[] h) {
		boolean[] seen = new boolean[L+R+2]; //true é visited, false é unvisited
		double[] dist = new double[L+R+2];
		//int[] pred = new int[L+R+2];
		for(int i=0; i<L+R+2; i++) {
			dist[i] = Double.MAX_VALUE;
			//pred[i] = -1;
		}
		
		dist[source] = 0;
		int current = source;
		for(int i=0; i<L+R+1; i++) {
			seen[current] = true;
			if(current == sink) continue;
			ArrayList<Integer> l = lovers(current);
			for(int j=0; j<l.size(); j++) {
				int v = l.get(j);
				if(!seen[v] && !(h[v] == Double.MAX_VALUE)) {
					double w = getWeight(current,v) + h[current] - h[v];
					if(dist[current] != Double.MAX_VALUE && dist[current] + w < dist[v]) {
						dist[v] = dist[current] + w;
						//pred[lover] = currentnode;
					}
				}
			}
			int next = getMinDist(dist, seen);
			if(next == -1) {break;}
			else {current = next;}
		}
		/* Build the path
		*LinkedList<Integer> path = new LinkedList<Integer>(); // Where we store the path
		*if(pred[sink] != -1) {
		*	path.add(sink);
		*	int e = sink;
		*	while(e!=source) {
		*		path.addFirst(pred[e]);
		*		e=pred[e];
		*	}
		}*/
		return dist;
	}
	
	public double[] DijkstraPQ(double[] h) {
		boolean[] seen = new boolean[L+R+2]; //true visited, false unvisited
		double[] dist = new double[L+R+2];
		PriorityQueue<Vertex> PQ = new PriorityQueue<Vertex>();
		Vertex vertex;
		
		for(int i=0; i<L+R+2; i++) {
			if(i == L+R) {
				dist[i] = 0;
				vertex = new Vertex(i,0);
				PQ.add(vertex);
			} else {
				dist[i] = Double.MAX_VALUE;
				vertex = new Vertex(i,Double.MAX_VALUE);
				PQ.add(vertex);
			}
		}
		
		int current;
		while(!PQ.isEmpty()) {
			current = PQ.poll().getId();
			if(dist[current] == Double.MAX_VALUE) break;
			seen[current] = true;
			if(current == sink) continue;
			ArrayList<Integer> l = lovers(current);
			for(int j=0; j<l.size(); j++) {
				int v = l.get(j);
				if(!seen[v] && !(h[v] == Double.MAX_VALUE)) {
					double w = getWeight(current,v) + h[current] - h[v];
					if(dist[current] != Double.MAX_VALUE && dist[current] + w < dist[v]) {
						dist[v] = dist[current] + w;
						vertex = new Vertex(v,dist[v]);
						PQ.add(vertex);
					}
				}
			}
		}

		return dist;
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
			if (s[i][0] == 1) string += "--> " + source + " ---[" + s[i][1] + "]---> " + i + "\n";
			if (s[i][0] == -1) string += "--> " + source + " <---[" + s[i][1] + "]--- " + i + "\n";
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
