

import java.util.ArrayList;
import java.util.List;

import com.mp1.Vector;

public class Driver {
	
	public static void TEST_GJ() {
		int dimension = 2 /*4*/;
		Double[] d1 = {1.0, 4.0/*, 0.0, 3.0*/};
		Double[] d2 = {-3.0, -12.0/*, -1.0, -3.0*/};
//		Double[] d3 = {-1.0, 2.0, -2.0, -2.0};
//		Double[] d4 = {1.0, 0.0, 0.0, 4.0};
		
		Double[] constants = {7.0, 6.0, /* -8.0, 7.0*/};
		
		Vector v1 = new Vector(d1, d1.length);
		Vector v2 = new Vector(d2, d2.length);
//		Vector v3 = new Vector(d3, d3.length);
//		Vector v4 = new Vector(d4, d4.length);
		Vector cv = new Vector(constants, constants.length);
		
		List<Vector> vecList = new ArrayList<Vector>();
		
		vecList.add(v1);
		vecList.add(v2);
//		vecList.add(v3);
//		vecList.add(v4);
		
		Vector answer = Vector.Gauss_Jordan(vecList, dimension, cv);
		
		
		try {
			System.out.println("Main: Consistent!");
			answer.printElements();
		}catch(Exception e) {
			System.out.println("Main: Inconsistent!");
			System.out.println("No solution.");
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
	
	//for testing
	public static void main(String[] args) {
		//TEST_GJ();
		SPAN_TESTING();
	}
}
