package com.mp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Agno II
 *         Jess Gano
 *         Claude Sedillo
 * ADVIDISC S17
 */
public class Vector {

    private Double[] data;
    private int dimension;
    
    private static final Double D_ZERO = 0.0;
    private static final Double D_ONE = 1.0;

    public Vector(int dimension) {
        this.dimension = dimension;
        this.data = new Double[dimension];

        for(int i = 0; i < dimension; i++) {
            this.data[i] = 0.0;
        }
    }

    public Vector(Double[] array, int dimension) {
        this.data = array;
        this.dimension = dimension;
    }
    
    public Vector scale(Double scalar) {
    	
    	for (int i = 0; i < dimension; i++) {
    		if(this.data[i].compareTo(D_ZERO) != 0)
    			this.data[i] = this.data[i] * scalar;
    	}
    	
    	return this;
    }

    public Vector add(Vector addend) {
  
    	if (addend.dimension != this.dimension) {
    		System.out.println("\nInvalid operation.");
    	} else {
    		for (int i = 0; i < dimension; i++) {
        		this.data[i] = this.data[i] + addend.data[i];
        	}
    	}
        return this;
    }

	public static Vector Gauss_Jordan(List<Vector> vectors, int dimension, Vector constants) {
		
		Vector solutionVector = null;
		Boolean isSolvable = false;
		dimension = transform_vectors(vectors, dimension);
		
		isSolvable = vectors.size() == dimension && constants.dimension == dimension;
		
		REF(vectors, dimension, constants, isSolvable);
		RREF(vectors, dimension, constants, isSolvable);
		
		if(isSolvable) {
	        solutionVector = check_for_inconsistency(vectors, constants, dimension);
		}
		
		//printAllVectors(vectors, constants);
		
		return solutionVector;
	}

	private static void REF(List<Vector> vectors, int dimension, Vector constants, Boolean isSolvable) {
		int vectListSize = vectors.size();
		int maxValue = dimension;
		
		if(dimension > vectors.size()) {
			maxValue = vectors.size();
		}
		

		for (int currentIndex = 0; currentIndex < maxValue; currentIndex++) {
			Double currentElement = vectors.get(currentIndex).data[currentIndex];

			// check if current element 0.
			if (currentElement.compareTo(D_ZERO) == 0) { // if 0, we have to swap.
				int nextIndex = findNonZero(vectors, vectListSize, currentIndex, currentElement);

				if (nextIndex != -1) {
					swapRows(vectors, currentIndex, nextIndex, dimension, constants, isSolvable);
					currentElement = vectors.get(currentIndex).data[currentIndex];
				}
			}

			// check if current element 1.
			if (currentElement.compareTo(D_ONE) != 0 && currentElement.compareTo(D_ZERO) != 0) { // if greater we have to scale vector by dividing it to become 1.
				divideRow(vectors, currentIndex, currentElement, dimension, constants, isSolvable);
			}

			// make all elements below currentElement, value 1, as 0. (vectListSize - 1
			// because last element diagonal has nothing below it.
			if (currentIndex + 1 != vectListSize) { // if last row, don't do anything.

				for (int succeedingIndex = currentIndex + 1; succeedingIndex < vectListSize; succeedingIndex++) {
					addRows(vectors, currentIndex, succeedingIndex, dimension, constants, isSolvable);
				}

			}
		}
	}

	private static void RREF(List<Vector> vectors, int dimension, Vector constants, Boolean isSolvable) {
		
		int maxValue = dimension;
		
		if(dimension > vectors.size()) {
			maxValue = vectors.size();
		}

		for (int currentIndex = maxValue - 1; currentIndex > 0; currentIndex--) {
			
			if(vectors.get(currentIndex).data[currentIndex].compareTo(D_ZERO) != 0) {
				for (int precedingIndex = currentIndex - 1; precedingIndex >= 0; precedingIndex--) {
					addRows(vectors, currentIndex, precedingIndex, dimension, constants, isSolvable);
				}
			}
		}
	}

	public static int span(List<Vector> vectors, int dimensions) {
		Vector vector = new Vector(dimensions);
		Boolean found = false;
		int count = 0;
		int index = 0;

		Gauss_Jordan(vectors, dimensions, vector);
		
		int newDimension = vectors.get(0).dimension; //newly tranformed dimension

		for (int currentIndex = 0; currentIndex < vectors.size(); currentIndex++) {
			index = 0;
			found = false;
			while (index < newDimension && !found) {
				if (vectors.get(currentIndex).data[index].compareTo(D_ZERO) != 0) {
					found = true;
					count++;
				}
				index++;
			}

		}

		return count;
	}

	/* Helper functions */
	/*Transforms vector list from row to column.*/
	private static int transform_vectors(List<Vector> vectors, int dimension) {

		int size = vectors.size();
		List<Double[]> tempDoubleList = new ArrayList<Double[]>();
		
		for(int i = 0; i < dimension; i++) {
			tempDoubleList.add(new Double[size]);
		}
		
		for(int col = 0; col < dimension; col++) {
			
			for( int row = 0; row < vectors.size(); row++) {
				tempDoubleList.get(col)[row] = vectors.get(row).data[col];
				
			}
		}
		
		vectors.clear();
		
		for(int i = 0; i < dimension; i++) {
			vectors.add(new Vector(tempDoubleList.get(i), size));
		}
		
		
		return size;
	}

	/* Find other nonzero element row when current element is 0 */
	private static int findNonZero(List<Vector> vectors, int vectListSize, int currentIndex, Double currentElement) {
		Boolean found = false;
		int nextIndex = currentIndex + 1;

		// check if there exists a nonzero element on a vector below the current
		// vector's element.
		while (!found && nextIndex < vectListSize) {

			if (currentElement.compareTo(vectors.get(nextIndex).data[currentIndex]) != 0) {
				currentElement = vectors.get(nextIndex).data[currentIndex];
				found = true;
			} else {
				nextIndex++;
			}
		}

		if (found == false) {// no swapping allowed.
			nextIndex = -1;
		}

		return nextIndex;
	}

	private static void swapRows(List<Vector> vectors, int currentIndex, int nextIndex, int dimension, Vector constants, Boolean isSolvable) {
		Collections.swap(vectors, currentIndex, nextIndex);
		
		// swap constants
		if(isSolvable) {
			Double temp = constants.data[nextIndex];
			constants.data[nextIndex] = constants.data[currentIndex];
			constants.data[currentIndex] = temp;
		}

	}

	/*
	 * Used for gaus-jordan elimination: dividing row so that the current non-zero
	 * element is 1.
	 */
	private static void divideRow(List<Vector> vectors, int vecListIndex, Double currentElement, int dimension, Vector constants, Boolean isSolvable) {

		Double mult = (1.0 / currentElement);
		vectors.get(vecListIndex).scale(mult);

		// multiply to constant
		if (isSolvable)
			constants.data[vecListIndex] = constants.data[vecListIndex] * mult;

	}

	private static void addRows(List<Vector> vectors, int currentIndex, int otherIndex, int dimension, Vector constants, Boolean isSolvable) {
		
		if (vectors.get(otherIndex).data[currentIndex].compareTo(D_ZERO) != 0) {
			Double mult = vectors.get(otherIndex).data[currentIndex];
			
			//Scale and Add addenned.
			vectors.get(otherIndex).add(vectors.get(currentIndex).scale(-mult));
			
			//Bring addened back to its original.
			vectors.get(currentIndex).scale(-(1.0 / mult));

			if(isSolvable)
				constants.data[otherIndex] += -mult * constants.data[currentIndex];
		}
	}
	

	private static Vector check_for_inconsistency(List<Vector> vectors, Vector constants, int dimension) {
		Boolean isAllZeros = false;
		Boolean isInconsistent = false;
		int row = 0;
		int col = 0;
		
		while(row < vectors.size() && !isInconsistent) {
			isAllZeros = true;
			col = 0;
			while(col < dimension && isAllZeros) {
				if(vectors.get(row).data[col] != 0) {
					isAllZeros = false;
				}
				else col++;
			}
			
			if(isAllZeros && constants.data[row] != 0) {
				isInconsistent = true;
			}
			else row++;
		}
		
		if(!isInconsistent) {
			System.out.println("Consistent!");
			return constants;
		}
		
		System.out.println("Inconsistent!");
		return null;
	}
	
	public void printElements() {
		System.out.print("[");
		for (int i = 0; i < this.dimension; i++) {
			System.out.print(data[i]);

			if (i + 1 != dimension) {
				System.out.print(" ");
			}
		}
		System.out.print("]" + " Dimension: " + this.dimension + "\n");
	}

	private static void printAllVectors(List<Vector> vectors, Vector constants) {
		for (int i = 0; i < vectors.size(); i++) { // checking onli.
			vectors.get(i).printElements();
		}
		
		System.out.print("constants: ");
		constants.printElements();
	}

}
