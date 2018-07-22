package com.mp1;

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
    
    public Vector scale(int scalar) {
    	
    	for (int i = 0; i < dimension; i++) {
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

	public static Vector Gauss_Jordan(List<Vector> vectors, int dimension, // dimension of all vectors in vectors list.
			Vector constants) {

		REF(vectors, dimension, constants);
		RREF(vectors, dimension, constants);

		System.out.println("Final: ");
		printAllVectors(vectors);
		System.out.print("Constants: ");
		constants.printElements();

		return constants;
	}

	private static void REF(List<Vector> vectors, int dimension, // dimension of all vectors in vectors list.
			Vector constants) {
		int vectListSize = vectors.size();

		System.out.println("Initial: ");
		printAllVectors(vectors);
		System.out.print("Constants: ");
		constants.printElements();
		System.out.println();

		// row echelon form
		for (int currentIndex = 0; currentIndex < vectListSize; currentIndex++) {
			Double currentElement = vectors.get(currentIndex).data[currentIndex];
			System.out.println("Row[" + currentIndex + "] Current element: " + currentElement);

			// check if current element 0.
			if (currentElement.compareTo(D_ZERO) == 0) { // if 0, we have to swap.
				System.out.println("its 0!");
				int nextIndex = findNonZero(vectors, vectListSize, currentIndex, currentElement, constants);

				if (nextIndex != -1) {
					swapRows(vectors, currentIndex, nextIndex, constants);
				}

				currentElement = vectors.get(currentIndex).data[currentIndex];
				System.out.println("New Row[" + currentIndex + "] Current element: " + currentElement);

				printAllVectors(vectors);
				System.out.print("Constants: ");
				constants.printElements();
			}

			// check if current element 1.
			if (currentElement.compareTo(D_ONE) != 0) { // if greater we have to scale vector by dividing it to become 1.
				System.out.println("its greater than 1 OR its negative!!");
				divideRow(vectors, dimension, currentIndex, currentElement, constants);
				printAllVectors(vectors);
				System.out.print("Constants: ");
				constants.printElements();
			}

			else if (currentElement.compareTo(D_ZERO) == 0.0) {
				System.out.println("Its still zero!");
			}

			// make all elements below currentElement, value 1, as 0. (vectListSize - 1
			// because last element diagonal has nothing below it.
			if (currentIndex + 1 != vectListSize) { // if last row, don't do anything.

				for (int succeedingIndex = currentIndex + 1; succeedingIndex < vectListSize; succeedingIndex++) {
					addRows(vectors, currentIndex, succeedingIndex, dimension, constants);
				}

			}

			System.out.println();
		}
	}

	private static void RREF(List<Vector> vectors, int dimension, // dimension of all vectors in vectors list.
			Vector constants) {

		for (int currentIndex = vectors.size() - 1; currentIndex > 0; currentIndex--) {
			System.out.println(
					"Row[" + currentIndex + "] Current element: " + vectors.get(currentIndex).data[currentIndex]);
			for (int precedingIndex = currentIndex - 1; precedingIndex >= 0; precedingIndex--) {
				addRows(vectors, currentIndex, precedingIndex, dimension, constants);
			}

		}
	}

	public static int span(List<Vector> vectors, int dimensions) {
		Vector vector = new Vector(dimensions);
		Boolean found = false;
		int count = 0;
		int index = 0;

		Gauss_Jordan(vectors, dimensions, vector);
		System.out.println("Span mode: ");

		for (int currentIndex = 0; currentIndex < vectors.size(); currentIndex++) {
			index = 0;
			found = false;
			while (index < dimensions && !found) {
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

	/* Find other nonzero element row when current element is 0 */
	private static int findNonZero(List<Vector> vectors, int vectListSize, int currentIndex, Double currentElement,
			Vector constants) {
		Boolean found = false;
		int nextIndex = currentIndex + 1;

		// check if there exists a nonzero element on a vector below the current
		// vector's element.
		while (!found && nextIndex < vectListSize) {

			if (currentElement.compareTo(vectors.get(nextIndex).data[currentIndex]) != 0) {
				currentElement = vectors.get(nextIndex).data[currentIndex];
				found = true;
				System.out.println("Nonzero found!");
			} else {
				nextIndex++;
			}
		}

		if (found == false) {// no swapping allowed.
			System.out.println("Did not find any other nonzero..");
			nextIndex = -1;
		}

		return nextIndex;
	}

	private static void swapRows(List<Vector> vectors, int currentIndex, int nextIndex, Vector constants) {
		Collections.swap(vectors, currentIndex, nextIndex);
		// swap constants
		Double temp = constants.data[nextIndex];
		constants.data[nextIndex] = constants.data[currentIndex];
		constants.data[currentIndex] = temp;
	}

	/*
	 * Used for gaus-jordan elimination: dividing row so that the current non-zero
	 * element is 1.
	 */
	private static void divideRow(List<Vector> vectors, int dimension, int vecListIndex, Double currentElement,
			Vector constants) {

		Double mult = (1.0 / currentElement);

		for (int i = 0; i < dimension; i++) {
			if (vectors.get(vecListIndex).data[i].compareTo(D_ZERO) != 0)
				vectors.get(vecListIndex).data[i] = vectors.get(vecListIndex).data[i] * mult;
		}

		// multiply to constant
		if (constants.data[vecListIndex].compareTo(D_ZERO) != 0)
			constants.data[vecListIndex] = constants.data[vecListIndex] * mult;

	}

	private static void addRows(List<Vector> vectors, int currentIndex, int otherIndex, int dimension,
			Vector constants) {
		System.out.println("Checking: " + vectors.get(otherIndex).data[currentIndex]);

		if (vectors.get(otherIndex).data[currentIndex].compareTo(D_ZERO) != 0) {
			Double mult = vectors.get(otherIndex).data[currentIndex];
			for (int i = 0; i < dimension; i++) {
				vectors.get(otherIndex).data[i] += -mult * vectors.get(currentIndex).data[i];
			}

			constants.data[otherIndex] += -mult * constants.data[currentIndex];
		}

		printAllVectors(vectors);
		System.out.print("Constants: ");
		constants.printElements();
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

	private static void printAllVectors(List<Vector> vectors) {
		for (int i = 0; i < vectors.size(); i++) { // checking onli.
			vectors.get(i).printElements();
		}
	}

}
