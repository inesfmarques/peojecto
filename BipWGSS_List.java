// Implementation of BipWGSS with adjacency lists

package bwgraph;

import java.util.ArrayList;
import java.util.LinkedList;

public class BipWGSS_List implements BipWGSS {
	int L; // Number of vertices on the left set
	int R; // Number of vertices on the right set
	int source; // Label of source (equals L+R)
	int sink; // Label of sink (equals L+R+1)
	
	// Array of adjacency lists
	// The i-th position is a list with the neighbors of vertex i
	ArrayList<Integer>[] adjacencyList;
	
	// Array of the weights of edges
	// The i-th position corresponds to the weights of the edges leaving i
	// The edge is the same as the corresponding in adjacencyList
	ArrayList<Integer>[] weightsList;

	// Constructor from BipWG (deep copy)
	public BipWGSS_List(BipWG G) {
		L = G.getL();
		R = G.getR();
		source = L+R;
		sink = L+R+1;
		
		adjacencyList = new ArrayList[L+R+2];
		weightsList = new ArrayList[L+R+2];
		
		// Vertices 0, ... , L+R-1
		int J;
		for (int i = 0; i < L+R; i++) {
			adjacencyList[i] = new ArrayList<Integer>(G.lovers(i));
			
			weightsList[i] = new ArrayList<Integer>();
			J = adjacencyList[i].size();
			for (int j = 0; j < J; j++) weightsList[i].add( G.getWeight(i, adjacencyList[i].get(j)) );
			if (i >= L) {
				adjacencyList[i].add(sink);
				weightsList[i].add(0);
			}
		}
		
		// Source
		adjacencyList[L+R] = new ArrayList<Integer>();
		weightsList[L+R] = new ArrayList<Integer>();
		for (int i = 0; i < L; i++) {
			adjacencyList[L+R].add(i);
			weightsList[L+R].add(0);
		}
		
		// Sink
		adjacencyList[L+R+1] = new ArrayList<Integer>();
		weightsList[L+R+1] = new ArrayList<Integer>();		
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

	// Return weight of edge from l to r
	public int getWeight(int l, int r) {
		// Try to find edge from l to r
		int i = 0;
		ArrayList<Integer> list = adjacencyList[l];
		int I = list.size();
		while (i < I && list.get(i) != r) i++;
		
		if (i == I) return 0;
		else return weightsList[l].get(i);
	}

	// Adds directed edge from vertex l to vertex r with weight w
	public void addEdge(int l, int r, int w) {
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

	@Override
	public LinkedList<Integer> BellmanFord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invertPath(LinkedList<Integer> path) {
		// TODO Auto-generated method stub

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
