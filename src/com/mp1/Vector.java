package com.mp1;

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
    	
    	return this;
    }

    public Vector add(Vector addened) {

        return this;
    }

    public static Vector Gauss_Jordan(List<Vector> vectors,
                                      int dimension,
                                      Vector constants) {

        return new Vector(1); // temp
    }

    public static int span(List<Vector> vectors, int dimensions) {

        return 1;
    }

    /**
     *
     * @param index - the position of the number in the vector.
     * @return the number in that given index.
     */
    public Double getElement(int index) {
        return this.data[index];
    }

    public int getDimension() {
        return this.dimension;
    }
    
    public void printElements() {
        System.out.print("[");

        for(int i = 0; i < this.dimension; i++) {
            System.out.print(this.data[i]);
            if (i + 1 != this.dimension) {
                System.out.print(" ");
            }
        }

        System.out.print("]");
    }

}
