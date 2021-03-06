package com.mp2;

import java.util.ArrayList;
import java.util.List;
import com.mp1.Vector;

/**
 * 
 * @author Alex Agno II
 *         Jess Gano
 *         Claude Sedillo
 * ADVDISC S18
 */
public class Matrix {
	
	private List<Vector> matrix;
	private int rowCount;
	private int columnCount;
	
	public Matrix(int dimension) {
		this.rowCount = this.columnCount =  dimension;
		this.matrix = new ArrayList<Vector>();
		
		for(int i = 0; i < dimension; i++) {
			double[] data = new double[dimension];
			
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
			 List<double[]> dTest = new ArrayList<double[]>();
			 List<Vector> vecList = new ArrayList<Vector>();
			 
			 for(int i = 0; i < newRowCount; i++) {
				 double[] dTestArray = new double[other.columnCount];
				 
				 for(int x = 0; x < newColCount; x++) {
					 double answer = 0.0;
					 
					 for(int j =0; j < this.columnCount; j++) {
							answer += this.matrix.get(i).getElement(j) * other.matrix.get(j).getElement(x);
					}
					 
					 dTestArray[x] = answer;
				 }
				 
				 dTest.add(dTestArray);

			 }
			 
			 for(int i = 0; i < newRowCount; i++) {
				 vecList.add(new Vector(dTest.get(i), newColCount));
			 }
			 
			 //transform before placing it in a matrix, because by default the matrix transforms the given list of vectors.
			 vecList = transformMatrix(vecList, newColCount);
			 result = new Matrix(vecList, newRowCount);
		}
		
		return result;
	}
	
	public double det() {
		double answer = 0.0;
		List<Vector> matrixCopy = copyMatrix();
		
		if(rowCount == columnCount) {
			answer = Vector.Gauss_Jordan_Det(matrixCopy, rowCount, columnCount);
		}
		
		else {
			return (Double) null;
		}
		
		return answer;
	}
	
	public Matrix inverse() {
		Matrix answer = null;
		
		if(this.rowCount == this.columnCount) {
			double determinant = det();
			
			if(determinant != 0.0) {
				//append identity matrix to matrix
				List<Vector> appendedMatrix = appendIdentityMatrix();
				List<Vector> inverseMatrix = null;
				int appendedColumnCount = columnCount*2;
				Vector.Gauss_jordan_inverse(appendedMatrix, this.rowCount, appendedColumnCount);
				
				//extract inverse.
				inverseMatrix = extractInverse(appendedMatrix);
				inverseMatrix = transformMatrix(inverseMatrix, this.rowCount); //rowCount or columnCount, since row and col is always equal.
				answer = new Matrix(inverseMatrix, this.columnCount); //rowCount or columnCount, since row and col is always equal.
			}
		}
		
		return answer;
	}
	
	private List<Vector> appendIdentityMatrix() {
		List<Vector> appendedMatrix = new ArrayList<Vector>();

		for(int i = 0; i < this.rowCount; i++) {
			double[] d = new double[this.columnCount*2];
			
			for(int j = 0; j < this.columnCount; j++) {
				d[j] = this.matrix.get(i).getElement(j);
				
				if(j==i) {
					d[j+this.columnCount] = 1.0;
				}
				else d[j+this.columnCount] = 0.0;
			}
			
			appendedMatrix.add(new Vector(d, this.columnCount*2));
		}
		
		return appendedMatrix;
	}
	
	private List<Vector> extractInverse(List<Vector> appendedMatrix) {
		List<Vector> inverseMatrix = new ArrayList<Vector>();
		
		for(int i = 0; i < this.rowCount; i++) {
			double[] d = new double[this.columnCount];
			
			for(int j = this.columnCount; j < this.columnCount*2; j++) {
				d[j - this.columnCount] = appendedMatrix.get(i).getElement(j);
			}
			
			inverseMatrix.add(new Vector(d, this.columnCount));
		}
		
		return inverseMatrix;
	}
	
	private List<Vector> copyMatrix() {
		List<Vector> copy = new ArrayList<Vector>();
		
		for(int i = 0; i < this.rowCount; i++) {
			double[] d = new double[columnCount];
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
		List<double[]> dTestList = new ArrayList<double[]>();
		
		for(int i = 0; i < dimension; i++) {
			dTestList.add(new double[size]);
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
	
	public void transpose() {
		
	}
	
}
