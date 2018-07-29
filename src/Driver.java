

import java.util.ArrayList;
import java.util.List;

import com.mp1.Vector;

public class Driver {
	
	public static void gauss_Jordan_test() {
        Double[] test1 = {1.0, 2.0, 3.0};
        Double[] test2 = {1.0, 4.0, 6.0};
        Double[] test3 = {2.0, -3.0, -5.0};
        Double[] testConstraint = {9.0, 1.0, 0.0};

        Vector v1 = new Vector(test1, test1.length);
        Vector v2 = new Vector(test2, test2.length);
        Vector v3 = new Vector(test3, test3.length);
        Vector constraints = new Vector(testConstraint, 3);

        List<Vector> vecList = new ArrayList<Vector>();

        vecList.add(v1);
        vecList.add(v2);
        vecList.add(v3);

        Vector solution = Vector.Gauss_Jordan(vecList, 3, constraints);
        
        solution.printElements();
//        int theSpan = Vector.span(vecList, 3);
//        System.out.println("The vecctors spans as: " + theSpan + "d");
    }
	
	public static void ADVDISC_SAMPLES_SPAN() {
        Double[] test1 = {1.0, 2.0, 3.0, 4.0};
        Double[] test2 = {2.0, 3.0, 4.0, 5.0};
        Double[] test3 = {1.0, 3.0, 5.0, 7.0};
        Double[] test4 = {2.0, 7.0, 8.0, 9.0};

        Vector v1 = new Vector(test1, test1.length);
        Vector v2 = new Vector(test2, test2.length);
        Vector v3 = new Vector(test3, test3.length);
        Vector v4 = new Vector(test4, test4.length);

        List<Vector> vecList = new ArrayList<Vector>();

        vecList.add(v1);
        vecList.add(v2);
        vecList.add(v3);
        vecList.add(v4);

        int answer = Vector.span(vecList, 4);
        
        System.out.println("Span is: " + answer + "d");
	}
	
	public static void ADVDISC_SAMPLES_GJ() {
        Double[] test1 = {2.0, 6.0};
        Double[] test2 = {4.0, 3.0};
        Double[] constants = {-10.0, 6.0, 5.0};


        Vector v1 = new Vector(test1, test1.length);
        Vector v2 = new Vector(test2, test2.length);
        Vector cv = new Vector(constants, constants.length);

        List<Vector> vecList = new ArrayList<Vector>();

        vecList.add(v1);
        vecList.add(v2);

        Vector answer = Vector.Gauss_Jordan(vecList, 2, cv);
        
        try {
        	answer.printElements();
        }catch(Exception NullPointerException) {
        	System.out.println("Unsolvable.");
        }
	}
	
	//for testing
	public static void main(String[] args) {
		//gauss_Jordan_test();
		//ADVDISC_SAMPLES_SPAN();
		ADVDISC_SAMPLES_GJ();
	}
}
