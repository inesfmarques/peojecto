package bwgraph;

public class Test {

	public static void main(String[] args) {
		BipWG grapha = new BipWG_Matrix(10,4);
		BipWGSS graph = new BipWGSS_Matrix(grapha);
		graph.addEdge(2, 12, 0);
		graph.addEdge(12, 9, -6);
		graph.addEdge(5, 13, -2);
		System.out.println(graph);
		
		graph.addEdge(13, 5, 2);
		System.out.println(graph);
		
		System.out.println(graph.edgeQ(5, 13));
		System.out.println(graph.getWeight(5, 13));
		System.out.println(graph.edgeQ(13, 5));
		System.out.println(graph.getWeight(13, 5));
		
		System.out.println();
		
		System.out.println(graph.edgeQ(2,12));
		System.out.println(graph.getWeight(2,12));
		System.out.println(graph.edgeQ(12,2));
		System.out.println(graph.getWeight(12,2));
	}

}
