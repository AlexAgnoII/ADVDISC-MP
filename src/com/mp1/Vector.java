package com.mp1;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * @author Alex Agno II
 *         Jess Gano
 *         Claude Sedillo
 * ADVDISC S18
 */
public class Vector {

    private double[] data;
    private int dimension;

    public Vector(int dimension) {
        this.dimension = dimension;
        this.data = new double[dimension];

        for(int i = 0; i < dimension; i++) {
            this.data[i] = 0.0;
        }
    }

    public Vector(double[] array, int dimension) {
        this.data = array;
        this.dimension = dimension;
    }
    
    public Vector scale(double scalar) {
    	
    	for (int i = 0; i < dimension; i++) {
    		if(this.data[i] != 0.0)
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
		
		return solutionVector;
	}
	
	public static double Gauss_Jordan_Det(List<Vector> matrix, int rowCount, int colCount) {
		double answer = 0;
		Vector constants = new Vector(rowCount);
		
		answer = REF(matrix, colCount, constants, true);
		RREF(matrix, colCount, constants, true);
		
		//check if a row is only full of zeros. (if such exist, return 0)
		for(int i = 0; i < rowCount; i++) {
			boolean hasNonZero = false;
			
			for(int j = 0; j < colCount; j++) {
				if(matrix.get(i).data[i] != 0.0) {
					hasNonZero = true;
				}
			}
			
			if(!hasNonZero) {
				return 0.0;
			}
		}
		
		return answer;
	}
	

	public static void Gauss_jordan_inverse(List<Vector> matrix, int rowCount, int colCount) {
		Vector constants = new Vector(rowCount);
		
		REF(matrix, colCount, constants, true);
		RREF(matrix, colCount, constants, true);
	}
	

	private static double REF(List<Vector> vectors, int dimension, Vector constants, Boolean isSolvable) {
		int vectListSize = vectors.size();
		int maxValue = dimension;
		double number = 1.0;
		
		if(dimension > vectors.size()) {
			maxValue = vectors.size();
		}
		

		for (int currentIndex = 0; currentIndex < maxValue; currentIndex++) {
			double currentElement = vectors.get(currentIndex).data[currentIndex];

			// check if current element 0.
			if (currentElement == 0.0) { // if 0, we have to swap.
				int nextIndex = findNonZero(vectors, vectListSize, currentIndex, currentElement);

				if (nextIndex != -1) {
					number *= swapRows(vectors, currentIndex, nextIndex, dimension, constants, isSolvable);
					currentElement = vectors.get(currentIndex).data[currentIndex];
				}
			}

			// check if current element 1.
			if (currentElement != 1.0 && currentElement != 0.0) { // if greater we have to scale vector by dividing it to become 1.
				number *= divideRow(vectors, currentIndex, currentElement, dimension, constants, isSolvable);
			}

			// make all elements below currentElement, value 1, as 0. (vectListSize - 1
			// because last element diagonal has nothing below it.
			if (currentIndex + 1 != vectListSize) { // if last row, don't do anything.

				for (int succeedingIndex = currentIndex + 1; succeedingIndex < vectListSize; succeedingIndex++) {
					addRows(vectors, currentIndex, succeedingIndex, dimension, constants, isSolvable);
				}

			}
		}
		
		return number;
	}

	private static void RREF(List<Vector> vectors, int dimension, Vector constants, Boolean isSolvable) {
		
		int maxValue = dimension;
		
		if(dimension > vectors.size()) {
			maxValue = vectors.size();
		}

		for (int currentIndex = maxValue - 1; currentIndex > 0; currentIndex--) {
			
			if(vectors.get(currentIndex).data[currentIndex] != 0.0) {
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
				if (vectors.get(currentIndex).data[index] != 0.0) {
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
		List<double[]> tempDoubleList = new ArrayList<double[]>();
		
		for(int i = 0; i < dimension; i++) {
			tempDoubleList.add(new double[size]);
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
	private static int findNonZero(List<Vector> vectors, int vectListSize, int currentIndex, double currentElement) {
		Boolean found = false;
		int nextIndex = currentIndex + 1;

		// check if there exists a nonzero element on a vector below the current
		// vector's element.
		while (!found && nextIndex < vectListSize) {

			if (currentElement != vectors.get(nextIndex).data[currentIndex]) {
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

	private static double swapRows(List<Vector> vectors, int currentIndex, int nextIndex, int dimension, Vector constants, Boolean isSolvable) {
		Collections.swap(vectors, currentIndex, nextIndex);
		
		// swap constants
		if(isSolvable) {
			double temp = constants.data[nextIndex];
			constants.data[nextIndex] = constants.data[currentIndex];
			constants.data[currentIndex] = temp;
		}
		
		return -1.0;
	}

	/*
	 * Used for gaus-jordan elimination: dividing row so that the current non-zero
	 * element is 1.
	 */
	private static double divideRow(List<Vector> vectors, int vecListIndex, double currentElement, int dimension, Vector constants, Boolean isSolvable) {

		double mult = (1.0 / currentElement);
		vectors.get(vecListIndex).scale(mult);

		// multiply to constant
		if (isSolvable)
			constants.data[vecListIndex] = constants.data[vecListIndex] * mult;
		
		return currentElement;

	}

	private static void addRows(List<Vector> vectors, int currentIndex, int otherIndex, int dimension, Vector constants, Boolean isSolvable) {
		
		if (vectors.get(otherIndex).data[currentIndex] != 0.0) {
			double mult = vectors.get(otherIndex).data[currentIndex];
			
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
			//System.out.println("Consistent!");
			return constants;
		}
		
		//System.out.println("Inconsistent!");
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
	
	public double getElement(int i) {
		return this.data[i];
	}

}
