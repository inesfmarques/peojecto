// Implementation of BipWGSS with adjacency lists

package bwgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BipWGSS_List implements BipWGSS {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	int source; // Label of source (equals L+R)
	int sink; // Label of sink (equals L+R+1)
	boolean[] visited; //array of visited vertices for the DFS
	
	// Array of adjacency lists
	// The i-th position is a list with the neighbors of vertex i
	ArrayList<Integer>[] adjacencyList;
	
	// Array of the weights of edges
	// The i-th position corresponds to the weights of the edges leaving i
	// The edge is the same as the corresponding in adjacencyList
	ArrayList<Double>[] weightsList;

	// Constructor from BipWG (deep copy)
	public BipWGSS_List(BipWG G) {
		L = G.getL();
		R = G.getR();
		source = L+R;
		sink = L+R+1;
		visited = new boolean[L+R+2];
		
		adjacencyList = new ArrayList[L+R+2];
		weightsList = new ArrayList[L+R+2];
		
		// Vertices 0, ... , L+R-1
		int J;
		for (int i = 0; i < L+R; i++) {
			adjacencyList[i] = new ArrayList<Integer>(G.lovers(i));
			
			weightsList[i] = new ArrayList<Double>();
			J = adjacencyList[i].size();
			for (int j = 0; j < J; j++) weightsList[i].add( -G.getWeight(i, adjacencyList[i].get(j)) );
			if (i >= L) {
				adjacencyList[i].add(sink);
				weightsList[i].add(0.);
			}
		}
		
		// Source
		adjacencyList[L+R] = new ArrayList<Integer>();
		weightsList[L+R] = new ArrayList<Double>();
		for (int i = 0; i < L; i++) {
			adjacencyList[L+R].add(i);
			weightsList[L+R].add(0.);
		}
		
		// Sink
		adjacencyList[L+R+1] = new ArrayList<Integer>();
		weightsList[L+R+1] = new ArrayList<Double>();		
	}
	
	public int getL() {
		return L;
	}

	public int getR() {
		return R;
	}

	public int getSource() {
		return source;
	}

	public int getSink() {
		return sink;
	}
	
	//Reset visited
	public void setVisited() {
		visited = new boolean[L+R+2];
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

	public boolean edgeQ(int l, int r) {
		return adjacencyList[l].contains(r);
	}

	// Returns a list with the descendants of vertex v
	public ArrayList<Integer> lovers(int v) {
		return adjacencyList[v];
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
				string += "--> " + i + " <---[" + getWeight(j, i) + "]--- " + j + "\n";
			}
		}
		if (b) string += " None!\n";
		
		// Add edges with source
		b = true;
		string += "Edges regarding source:";
		for(int i = 0; i < L; i++) {
			if (!edgeQ(i, source) && !edgeQ(source, i)) continue;
			if (b) {
				string += "\n";
				b = false;
			}
			if (edgeQ(i, source)) string += "--> " + source + " ---[" + getWeight(i, source) + "]---> " + i + "\n";
			if (edgeQ(source, i)) string += "--> " + source + " <---[" + getWeight(source, i) + "]--- " + i + "\n";
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
			if (edgeQ(i, sink)) string += "--> " + source + " ---[" + getWeight(i, sink) + "]---> " + i + "\n";
			if (edgeQ(sink, i)) string += "--> " + source + " <---[" + getWeight(sink, i) + "]--- " + i + "\n";
		}
		if (b) string += " None!\n";
		
		return string;
	}

}
