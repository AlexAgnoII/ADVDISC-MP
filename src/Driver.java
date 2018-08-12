

import java.util.ArrayList;


import java.util.List;

import com.mp1.Vector;
import com.mp2.Matrix;

public class Driver {
	
	public static void TEST_GJ() {
		int dimension = 3;
		Double[] d1 = {2.0, 1.0, 3.0};
		Double[] d2 = {-1.0, -2.0, -1.0};
		Double[] d3 = {2.0, 1.0, 2.0};
		
		Double[] constants = {10.0, 8.0, 11.0};
		
		Vector v1 = new Vector(d1, d1.length);
		Vector v2 = new Vector(d2, d2.length);
		Vector v3 = new Vector(d3, d3.length);
		Vector cv = new Vector(constants, constants.length);
		
		List<Vector> vecList = new ArrayList<Vector>();
		
		vecList.add(v1);
		vecList.add(v2);
		vecList.add(v3);
		
		Vector answer = Vector.Gauss_Jordan(vecList, dimension, cv);
		
		
		try {
			answer.printElements();
			System.out.println("Main: Consistent!");
		}catch(Exception e) {
			System.out.println("No solution.");
			System.out.println("Main: Inconsistent!");
			e.printStackTrace();
		}
	}
	
	public static void SPAN_TESTING() {
		int dimension = 2;
		
		Double[] d1 = {1.0, 4.0/*, 3.0, 1.0*/};
		Double[] d2 = {-3.0, -12.0/*, 5.0, 4.0*/};
//		Double[] d3 = {-3.0, 1.0, 2.0, -1.0};
//		Double[] d4 = {1.0, 2.0, 5.0, 2.0};
		
		List<Vector> vecList = new ArrayList<Vector>();
		
		Vector v1 = new Vector(d1, d1.length);
		Vector v2 = new Vector(d2, d2.length);
//		Vector v3 = new Vector(d3, d3.length);
//		Vector v4 = new Vector(d4, d4.length);
		
		vecList.add(v1);
		vecList.add(v2);
//		vecList.add(v3);
//		vecList.add(v4);
		
		int span = Vector.span(vecList, dimension);
		
		System.out.println(span);
	}
	
	public static void MATRIX_TEST() {
//		Double[] d1 = {1.0};//, 3.0, 4.0};
//		Double[] d2 = {2.0};//, 2.0, 1.0};
//		Double[] d3 = {1.0};//, 0.0, 9.0};
//		
//		Vector v1 = new Vector(d1, d1.length);
//		Vector v2 = new Vector(d2, d2.length);
//		Vector v3 = new Vector(d3, d3.length);
//		
//		List<Vector> vecList = new ArrayList<Vector>();
//		vecList.add(v1);
//		vecList.add(v2);
//		vecList.add(v3);
//		
//		Matrix m = new Matrix(vecList, 1);
		Matrix m = new Matrix(10);
		m.printMatrix();
		
	}
	
	public static void MATRIX_TIMES_TEST() {
		
		//matrix 1
		int matrix_1_dimension = 3;
		Double[] d1 = {1.0, 2.0, 4.0};
		Double[] d2 = {0.0, 1.0, 2.0};
		Double[] d3 = {1.0, 1.0, 3.0};
		
		Vector v1 = new Vector(d1, d1.length);
		Vector v2 = new Vector(d2, d2.length);
		Vector v3 = new Vector(d3, d3.length);
		
		List<Vector> vecList1 = new ArrayList<Vector>();
		vecList1.add(v1);
		vecList1.add(v2);
		vecList1.add(v3);
		
		Matrix m1 = new Matrix(vecList1, matrix_1_dimension);

		
		
		//matrix 2
		int matrix_2_dimension = 3;
		Double[] d4 = {1.0, 0.0, 4.0};
		Double[] d5 = {1.0, 0.0, 1.0};
		Double[] d6 = {2.0, 1.0, 5.0};
		
		Vector v4 = new Vector(d4, d4.length);
		Vector v5 = new Vector(d5, d5.length);
	    Vector v6 = new Vector(d6, d6.length);
		
		List<Vector> vecList2 = new ArrayList<Vector>();
		vecList2.add(v4);
		vecList2.add(v5);
	    vecList2.add(v6);
		
		Matrix m2 = new Matrix(vecList2, matrix_2_dimension);
		
		System.out.println("Matrix 1");
		m1.printMatrix();
		System.out.println();
		System.out.println("Matrix 2");
		m2.printMatrix();
		System.out.println("\n\n");
		
		try {
			
			Matrix ans = m1.times(m2);
			ans.printMatrix();
		}catch(NullPointerException e) {
			System.out.println("No solution");
		}
	}
	
	public static void MATRIX_DETERMINANT() {
		Double[] d1 = {2.0, -3.0, -2.0, -1.0};
		Double[] d2 = {-1.0, 1.0, 1.0, 3.0};
		Double[] d3 = {3.0, 0.0, 4.0, 0.0};
		Double[] d4 = {0.0, 4.0, 1.0, -2.0};
		
		
		Vector v1 = new Vector(d1, d1.length);
		Vector v2 = new Vector(d2, d2.length);
	    Vector v3 = new Vector(d3, d3.length);
	    Vector v4 = new Vector(d4, d4.length);
		
		List<Vector> vecList = new ArrayList<Vector>();
		vecList.add(v1);
		vecList.add(v2);
	    vecList.add(v3);
	    vecList.add(v4);
	    
	    Matrix m = new Matrix(vecList, 4);
	    m.printMatrix();
	    System.out.println();
	    System.out.println(m.det());
	}
	
	public static void MATRIX_INVERSE() {
		Double[] d1 = {1.0, 0.0};
		Double[] d2 = {0.0, 2.0};
		Double[] d3 = {1.0, 1.0};

		
		
		Vector v1 = new Vector(d1, d1.length);
		Vector v2 = new Vector(d2, d2.length);
	    Vector v3 = new Vector(d3, d3.length);

		
		List<Vector> vecList = new ArrayList<Vector>();
		vecList.add(v1);
		vecList.add(v2);
	    vecList.add(v3);

	    
	    Matrix m = new Matrix(vecList, 2);
	    m.printMatrix();
	    System.out.println();
	    
	    
	    try {
	    	Matrix inverse = m.inverse();
	    	inverse.printMatrix();
	    	
	    } catch(NullPointerException e) {
	    	System.out.println("No inverse.");
	    }

	}


	//for testing
	public static void main(String[] args) {
		MATRIX_INVERSE();
	}
	
}
