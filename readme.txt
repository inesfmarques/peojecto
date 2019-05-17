AA Project - Maximum Bipartite Matchings and Matroid Intersections (implemented in Java)
By: Henrique Santos (83630), Inês Marques (83633), Mariana Filipe (83641)

Package descriptions:
 - 'arborescence': Solves the spanning arborescences problem (Section 4.1 of report)
    Class 'Test' showcases main methods
    Requires 'graph' and 'matroid' packages
 - 'bgraph': Implements bipartite graphs (matrix and adjacency lists) and contains methods to find maximum matchings
    Class 'Test' showcases main methods
    Requires 'matroid' package
 - 'bgraph': Implements weighted bipartite graphs (matrix and adjacency lists) and contains methods to find maximum matchings
    Class 'Test' showcases main methods
 - 'graph': Implements other graphs required by the matroid intersection problems (Section 4 of the report)
 - 'matroid': Implements the matroid intersection algorithm.
 - 'painting': Solves the painting problem (Section 4.2 of report)
    Class 'Test' showcases main methods
    Requires 'graph' and 'matroid' packages
 - 'timetest': Stores the classes used to measure the time performances of maximum matching methods
    Classes 'TimeTest_UW_Example' and 'TimeTest_W_Example' showcase how to use them (for unweighted and weighted graphs, respectively)
    Requires 'bgraph', 'bgraph' and 'matroid' packages