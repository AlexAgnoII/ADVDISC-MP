package com.mp2;

import java.util.ArrayList;
import java.util.List;
import com.mp1.Vector;

public class Matrix {
	
	private List<Vector> matrix;
	private int rowCount;
	private int columnCount;
	
	public Matrix(int dimension) {
		this.rowCount = this.columnCount =  dimension;
		this.matrix = new ArrayList<Vector>();
		
		for(int i = 0; i < dimension; i++) {
			Double[] data = new Double[dimension];
			
			for(int x = 0; x < dimension; x++) {
				if(x == i) {
					data[x] = 1.0;
				}
				else {
					data[x] = 0.0;
				}
			}
			
			
			Vector v = new Vector(data, dimension);
			this.matrix.add(v);
		}
	}
	
	public Matrix(List<Vector> list, int dimension) {
		this.matrix = transformMatrix(list, dimension);
	}
	
	private List<Vector> transformMatrix(List<Vector> list, int dimension) {
		int size = list.size();
		List<Vector> vecTest = new ArrayList<Vector>();
		List<Double[]> dTestList = new ArrayList<Double[]>();
		
		if(dimension == size) {
			this.rowCount = this.columnCount = dimension;
		}
		
		else {
			this.rowCount = dimension;
			this.columnCount = size;
		}
		
		for(int i = 0; i < dimension; i++) {
			dTestList.add(new Double[size]);
		}
		

		for(int i = 0; i < this.rowCount; i++) {
			
			for( int j = 0; j < this.columnCount; j++) {
				dTestList.get(i)[j] = list.get(j).getElement(i);
			}
		}
		
		for(int i = 0; i < this.rowCount; i++) {
			vecTest.add(new Vector(dTestList.get(i), this.columnCount));
		}
		
		return vecTest;
	}
	
	public void printMatrix() {
		for(int i = 0; i < this.rowCount; i++) {
			this.matrix.get(i).printElements();
		}
		
		System.out.println("Row count: " + this.rowCount);
		System.out.println("Column count: " + this.columnCount);
	}
	
}
