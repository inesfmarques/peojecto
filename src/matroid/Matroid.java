// Interface for matroid of elements
// Necessary to use Intersector class

package matroid;

import java.util.ArrayList;

public interface Matroid<Type> {
	
	// Returns a list with the elements in the matroid ground set
	ArrayList<Type> getGroundSet();
	
	// Input:
	// --> set: ArrayList of elements of the ground set
	// Output: 'true' if set is in the matroid, 'false' otherwise
	boolean belongsTo(ArrayList<Type> set);
	
	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	boolean belongsTo(ArrayList<Type> set, Type x);
	
	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// --> y: element in set
	// Output: 'true' if (set U {x}) \ {y} is in the matorid, 'false' otherwise
	boolean belongsTo(ArrayList<Type> set, Type x, Type y);
}
