// Implementation of Vertices for Priority Queue

package bwgraph;

class Vertex implements Comparable<Vertex>{
	int id;
	double dist;
	
	// Constructor from Vertex
	public Vertex(int i, double d) {
		id = i;
		dist = d;
	}

	public int getId() {
		return id;
	}

	public double getDist() {
		return dist;
	}
	
	public int compareTo(Vertex v) {
		int r = Double.compare(dist, v.dist);
		if (r == 0) {return Integer.compare(id, v.id);}
		else return r;
	}
	
}