

import java.util.ArrayList;
import java.util.List;

import com.mp1.Vector;

public class Driver {
	
	public static void gauss_Jordan_test() {
        Double[] test1 = {1.0, 1.0, 2.0};
        Double[] test2 = {2.0, 4.0, -3.0};
        Double[] test3 = {3.0, 6.0, -5.0};
        Double[] testConstraint = {9.0, 1.0, 0.0};

        Vector v1 = new Vector(test1, test1.length);
        Vector v2 = new Vector(test2, test2.length);
        Vector v3 = new Vector(test3, test3.length);
        Vector constraints = new Vector(testConstraint, 3);

        List<Vector> vecList = new ArrayList<Vector>();

        vecList.add(v1);
        vecList.add(v2);
        vecList.add(v3);

        Vector.Gauss_Jordan(vecList, 3, constraints);
//        int theSpan = Vector.span(vecList, 3);
//        System.out.println("The vecctors spans as: " + theSpan + "d");
    }
	
	//for testing
	public static void main(String[] args) {
		gauss_Jordan_test();
	}
}
