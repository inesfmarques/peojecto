// Implementation of Vertices for Priority Queue

package bwgraph;

class Vertex implements Comparable<Vertex>{
	int id;
	int dist;
	
	// Constructor from Vertex
	public Vertex(int i, int d) {
		id = i;
		dist = d;
	}

	public int getId() {
		return id;
	}

	public int getDist() {
		return dist;
	}
	
	public int compareTo(Vertex v) {
		int r = Integer.compare(dist, v.dist);
		if (r == 0) {return Integer.compare(id, v.id);}
		else return r;
	}
	
}