// Class used for matroid intersection
// The matroids used must implement the interface Matroid

package matroid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import bgraph.*;

public class MatroidIntersector<Type> {
	
	// Computes a maximal set in both matroids
	// It is assumed they have the same ground set
	public ArrayList<Type> intersection(Matroid<Type> m, Matroid<Type> n){
		
		// (In the comments, GS = ground set)
		
		ArrayList<Type> maximalSet = new ArrayList<Type>(); // Current iteration of maximal set
		ArrayList<Type> maximalSetC = m.getGroundSet(); // Current iteration of complementary of maximal set
		int maximalSetSize = 0; // Size of 'maximalSet'
		int groundSetSize = maximalSetC.size(); // Size of GS
		
		// Declaration of variables
		boolean foundQ;
		boolean[] path;
		int current = 0, currentLover;
		int[] history = new int[groundSetSize];
		Queue<Integer> queue;
		ArrayList<Integer> lovers;
		ArrayList<Type> maximalSetNew, maximalSetCNew;
		BipG G;	
		
		// Improve 'maximalSet'
		maximalSetWhile:
		while (maximalSetSize < groundSetSize) {
			
			// From now on, we consider the numbering of GS obtained by joining 'maximalSet' and 'maximalSetC'.
			// The i-th element of GS is:
			// --> [If i < maximalSetSize] the i-th element of 'maximalSet'
			// --> [If maximalSetSize <= i < groundSetSize] the (i - maximalSetSize)-th element of 'maximalSetC'
			
			// Build the graph
			G = new BipG_List(maximalSetSize, groundSetSize - maximalSetSize);				
			
			for (int i = 0; i < groundSetSize; i++) {
				for (int j = 0; j < maximalSetSize - groundSetSize; j++) {
					if (m.belongsTo(maximalSet, maximalSetC.get(j), maximalSet.get(i))) G.addEdge(i, j + groundSetSize);
					if (n.belongsTo(maximalSet, maximalSetC.get(j), maximalSet.get(i))) G.addEdge(j + groundSetSize, i);
				}
			}
			
			// Build 'queue' and 'history' for BFS
			// 'queue' is made of the indexes of the elements of GS.
			
			// The i-th entry of 'history' is:
			// --> -1 if the i-th element of GS is in the starting set
			// --> -2 if the i-th element of GS is in the ending set
			// --> -3 if the i-th element of GS hasn't been reached
			// --> 0 <= j < groundSetSize if the i-th element of GS has been reached by the j-th element of GS
			queue = new LinkedList<Integer>();
			for (int i = 0; i < maximalSetSize; i++) history[i] = -3;
			for (int i = maximalSetSize; i < groundSetSize; i++) {
				if (m.belongsTo(maximalSet, maximalSetC.get(i - maximalSetSize))) {

					// Check if the set maximalSet U {maximalSetC.get(i - maximalSetSize)}
					// belongs to both matroids
					if (n.belongsTo(maximalSet, maximalSetC.get(i - maximalSetSize))) {
						// Add this element to 'maximalSet'
						maximalSet.add(maximalSetC.get(i - maximalSetSize));
						maximalSetC.remove(i - maximalSetSize);
						maximalSetSize++;
						// Restart the while
						continue maximalSetWhile;
					}
						
					queue.add(i);
					history[i] = -1;
				}
				else if (n.belongsTo(maximalSet, maximalSetC.get(i - maximalSetSize))) {
					history[i] = -2;
				}
				else {
					history[i] = -3;
				}
			}
			
			// 'foundQ' is changed to true if a path is found
			foundQ = false;
			
			// BFS search
			BFS:
			while (!queue.isEmpty()) {
				current = queue.remove(); // First element of queue
				lovers = G.lovers(current); // Neighbors of 'current'
				
				// Go through neighbors
				for (int i = 0; i < lovers.size(); i++) {
					currentLover = lovers.get(i); // Current neighbor
					
					// Neighbor was never reached before
					if (history[currentLover] == -3) {
						history[currentLover] = current;
						queue.add(currentLover);
					}
					
					// Neighbor is an ending vertex
					if (history[currentLover] == -2) {
						history[currentLover] = current;
						current = currentLover; // Set 'current' as the last vertex of the path
						foundQ = true;
						break BFS;
					}
				}
			}
			
			// If no path was found, 'maximalSet' is maximal and we stop the while
			if (!foundQ) break;
			
			// Otherwise, build the new 'maximalSet'
			
			// Build the path that BFS found
			// The i-th entry of 'path' is true if the i-th element of GS is in the path found, false otherwise
			path = new boolean[groundSetSize];
			
			// To build 'path', recall that 'current' is the last vertex of the path
			while (history[current] != -1) {
				path[current] = true;
				current = history[current]; // Go back in the path
			}
			path[current] = true; // Add the first vertex of the path
			
			// Build the new 'maximalSet' and 'maximalSetC'
			maximalSetNew = new ArrayList<Type>();
			maximalSetCNew = new ArrayList<Type>();
			
			// For i < maximalSetSize, we add the i-th element of GS if it wasn't on the path
			for (int i = 0; i < maximalSetSize; i++) {
				if (!path[i]) maximalSetNew.add(maximalSet.get(i));
				else maximalSetCNew.add(maximalSet.get(i));
			}
			// For maximalSetSize <= i < groundSetSize, we add the i-th element of GS if it was on the path
			for (int i = maximalSetSize; i < groundSetSize; i++) {
				if (path[i]) maximalSetNew.add(maximalSetC.get(i - maximalSetSize));
				else maximalSetCNew.add(maximalSetC.get(i - maximalSetSize));
			}
			
			// Replace the old sets
			maximalSet = maximalSetNew;
			maximalSetC = maximalSetCNew;
			
			// Update 'maximalSetSize'
			maximalSetSize++;
		}
		
		return maximalSet;
	}
}
