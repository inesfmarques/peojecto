// Interface for matroid of elements
// Necessary to use MatroidIntersector class

package matroid;

import java.util.ArrayList;

// Type is the class of elements of the matroid.

// Subset is a class of a subset of elements Type
// It's specific for every Type
// An ArrayList<Type> is converted into TypeSubset so that the belongsTo methods are faster
public interface Matroid<Type, TypeSubset> {
	
	// Returns a list with the elements in the matroid ground set
	ArrayList<Type> getGroundSet();
	
	// Input:
	// --> set: ArrayList of elements of the ground set
	// Output: 'true' if set is in the matroid, 'false' otherwise
	boolean belongsTo(ArrayList<Type> set);
	
	// Converts an ArrayList into a Subset, to be used with the remaining methods
	// Input:
	// --> set: ArrayList of elements that is in the matroid
	TypeSubset arrayToSubset(ArrayList<Type> set);
	
	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	boolean belongsTo(TypeSubset set, Type x);
	
	// Input:
	// --> set: Subset of elements that is in the matroid
	// --> x: element not in set
	// --> y: element in set
	// Output: 'true' if (set U {x}) \ {y} is in the matorid, 'false' otherwise
	boolean belongsTo(TypeSubset set, Type x, Type y);
}
