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
		setRowColumn(dimension, list.size());
		this.matrix = transformMatrix(list, dimension);
	}
	
	public Matrix times(Matrix other) {
		
		Matrix result = null;
		
		//size mismatch check
		if(this.columnCount == other.rowCount) {
			 int newRowCount = this.rowCount;
			 int newColCount = other.columnCount;
			 List<Double[]> dTest = new ArrayList<Double[]>();
			 List<Vector> vecList = new ArrayList<Vector>();
			 
			 for(int i = 0; i < newRowCount; i++) {
				 Double[] dTestArray = new Double[other.columnCount];
				 
				 for(int x = 0; x < newColCount; x++) {
					 Double answer = 0.0;
					 
					 for(int j =0; j < this.columnCount; j++) {
							answer += this.matrix.get(i).getElement(j) * other.matrix.get(j).getElement(x);
					}
					 
					 dTestArray[x] = answer;
				 }
				 
				 dTest.add(dTestArray);

			 }
			 
			 //test print to check answers
//			 for(Double[] d : dTest) {
//				 for(int i = 0; i < other.columnCount; i++) {
//					 System.out.print(d[i] + " ");
//				 }
//				 System.out.println();
//			 }
			 
			 for(int i = 0; i < newRowCount; i++) {
				 vecList.add(new Vector(dTest.get(i), newColCount));
			 }
			 
			 //transform before placing it in a matrix, because by default the matrix transforms the given list of vectors.
			 vecList = transformMatrix(vecList, newColCount);
			 result = new Matrix(vecList, newRowCount);
		}
		
		result.printMatrix();
		return result;
	}
	
	public double det() {
		double answer = 0.0;
		List<Vector> matrixCopy = copyMatrix(this.matrix);
		
		if(rowCount == columnCount) {
			answer = Vector.Gauss_Jordan_Det(matrixCopy, rowCount, columnCount);
		}
		
		return answer;
	}
	
	public Matrix inverse() {
		return null;
	}
	
	private List<Vector> copyMatrix(List<Vector> matrix) {
		List<Vector> copy = new ArrayList<Vector>();
		
		for(int i = 0; i < this.rowCount; i++) {
			Double[] d = new Double[columnCount];
			for(int j = 0; j < this.columnCount; j++) {
				d[j] = this.matrix.get(i).getElement(j);
			}
			
			copy.add(new Vector(d, this.columnCount));
		}
		
		return copy;
	}
	
	
	private List<Vector> transformMatrix(List<Vector> list, int dimension) {
		int size = list.size();
		List<Vector> vecTest = new ArrayList<Vector>();
		List<Double[]> dTestList = new ArrayList<Double[]>();
		
		for(int i = 0; i < dimension; i++) {
			dTestList.add(new Double[size]);
		}
		

		for(int i = 0; i < dimension; i++) {
			
			for( int j = 0; j < size; j++) {
				dTestList.get(i)[j] = list.get(j).getElement(i);
			}
		}
		
		for(int i = 0; i < dimension; i++) {
			vecTest.add(new Vector(dTestList.get(i),size));
		}
		
		return vecTest;
	}
	
	private void setRowColumn(int dimension, int size) {
		if(dimension == size) {
			this.rowCount = this.columnCount = dimension;
		}
		
		else {
			this.rowCount = dimension;
			this.columnCount = size;
		}
	}
	
	public void printMatrix() {
		for(int i = 0; i < this.rowCount; i++) {
			this.matrix.get(i).printElements();
		}
		
		System.out.println("Row count: " + this.rowCount);
		System.out.println("Column count: " + this.columnCount);
	}
	
}
