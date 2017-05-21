package s17cs350project.planner;

import java.io.InputStream;
import java.util.List;

/**
 * Defines a planning tool for calculating linear distances over a path of three-dimensional coordinates.
 * It accepts an input stream of comma-delimited coordinate triples with one triple per line.
 * This native coordinate system of triples can be any permutation of the components (x,y,z),
 * with each axis independently increasing in either direction. The canonical coordinate system maps each
 * component and direction to (A,B,C).
 */
public class WaypointPlanner {
    /**
     * Creates a planner. It defines the axes and reads the coordinates. [3]
     *
     * @param axisA      the definition of how to interpret the A axis
     * @param axisB      the definition of how to interpret the B axis
     * @param axisC      the definition of how to interpret the C axis
     * @param unitNative the unit of the native coordinate system
     * @param instream   the input stream containing the coordinates
     */
    public WaypointPlanner(E_AxisNative axisA, E_AxisNative axisB, E_AxisNative axisC, E_Unit unitNative, InputStream instream) {

    }

    /**
     * Calculates the total distance along the path of coordinates. [5]
     *
     * @param axes                  the axes of the coordinates to account for in calculating the distance
     * @param isCanonicalElseNative use the canonical order defined in the triple constructor or use original native
     *                              order from the input stream
     * @param unit                  the unit of the distances
     * @return the total distance
     */
    public double calculateDistance(E_AxisCombinationNeutral axes, boolean isCanonicalElseNative, E_Unit unit) {
        return 0.0;
    }

    /**
     * Calculates the intermediate distances between coordinates along the path of coordinates. [30]
     *
     * @param axes                  the axes of the coordinates to account for in calculating the distance
     * @param isCanonicalElseNative use the canonical order defined in the triple constructor or use original native
     *                              order from the input stream
     * @param unit                  the unit of the distances
     * @return the intermediate distances
     */
    public List<Double> calculateDistances(E_AxisCombinationNeutral axes, boolean isCanonicalElseNative, E_Unit unit) {
        return null;
    }

    /**
     * Gets the definition of how to interpret the A axis. [1]
     *
     * @return the definition
     */
    public E_AxisNative getAxisA() {
        return null;
    }

    /**
     * Gets the definition of how to interpret the B axis. [1]
     *
     * @return the definition
     */
    public E_AxisNative getAxisB() {
        return null;
    }

    public E_AxisNative getAxisC() {
        return null;
    }


    /**
     * Defines a generic triple of coordinates defined as (first,second,third). Each word corresponds to the position,
     * but the interpretation depends on which axis is mapped to it; for example, (z,y,x) or (A,B,C).
     */
    public static class Coordinates {
        double first, second, third;

        /**
         * Creates a triple.
         *
         * @param f the first coordinate
         * @param s the second coordinate
         * @param t the third coordinate
         */
        Coordinates(double f, double s, double t) {
            this.first = f;
            this.second = s;
            this.third = t;
        }

        /**
         * Outputs the triple in the form (first second third).
         *
         * @return the string
         */
        public String toString() {
            return first + " " + second + " " + third;
        }
    }

    /**
     * Defines which components of a coordinate triple to use in distance calculations. [7]
     */
    public enum E_AxisCombinationNeutral {
        FIRST,                  //(X2 - X1)
        SECOND,                 //(Y2 - Y1)
        THIRD,                  //(Z2 - Z1)
        FIRST_SECOND,           //((X2,Y2) - (X1,Y1))
        FIRST_THIRD,            //((X2,Z2) - (X1,Z1))
        SECOND_THIRD,           //((Y2,Z2) - (Y1,Z1))
        FIRST_SECOND_THRID      //((X2,Y2,Z2) - (X1,Y1,Z1))
    }

    /**
     * Defines how to interpret a lettered axis; i.e., axisA, axisB, or axisC. [6]
     */
    public enum E_AxisNative {
        X_MINUS, X_PLUS, Y_MINUS, Y_PLUS, Z_MINUS, Z_PLUS
    }

    /**
     * Defines the unit of the native coordinate system. [4]
     */
    public enum E_Unit {
        FEET, KILOMETERS, METERS, MILES
    }
}