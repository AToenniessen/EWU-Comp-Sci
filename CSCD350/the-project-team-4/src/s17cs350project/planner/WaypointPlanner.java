package s17cs350project.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines a planning tool for calculating linear distances over a path of three-dimensional coordinates.
 * It accepts an input stream of comma-delimited coordinate triples with one triple per line.
 * This native coordinate system of triples can be any permutation of the components (x,y,z),
 * with each axis independently increasing in either direction. The canonical coordinate system maps each
 * component and direction to (A,B,C).
 */
public class WaypointPlanner {
    private E_AxisNative[] axes;
    private E_Unit nativeUnit;
    private static final E_Unit canonicalUnit = E_Unit.METERS;
    private BufferedReader instream;
    private List<Coordinates> myCoordinates;

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
        if (axisA == null || axisB == null || axisC == null || unitNative == null || instream == null)
            throw new IllegalArgumentException("Parameters passed in cannot be null");
        if (axisA.getCardinalAxis().equals(axisB.getCardinalAxis()) ||
                axisA.getCardinalAxis().equals(axisC.getCardinalAxis()) ||
                axisB.getCardinalAxis().equals(axisC.getCardinalAxis()))
            throw new IllegalArgumentException("Axes must be on unique dimensional planes");
        axes = new E_AxisNative[]{axisA, axisB, axisC};
        nativeUnit = unitNative;
        this.instream = new BufferedReader(new InputStreamReader(instream));
        try {
            this.instream.mark(1000000);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (axes == null || unit == null)
            throw new IllegalArgumentException("Axis and or unit must be valid");
        double distance = 0.0;
        for (double d : calculateDistances(axes, isCanonicalElseNative, unit)) {
            distance += d;
        }
        return distance;
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
        if (axes == null || unit == null)
            throw new IllegalArgumentException("Axis and or unit must be valid");
        ArrayList<Double> distances = new ArrayList<>();
        getCoordinates(isCanonicalElseNative, unit);
        for (int i = 1; i < myCoordinates.size(); i++) {
            Coordinates c1 = myCoordinates.get(i);
            Coordinates c2 = myCoordinates.get(i - 1);
            distances.add(canonicalUnit.convertTo(c1.calculateDistance(axes, c1, c2), unit));
        }
        return distances;
    }

    /**
     * Gets the definition of how to interpret the A axis. [1]
     *
     * @return the definition
     */
    public E_AxisNative getAxisA() {
        return axes[0];
    }

    /**
     * Gets the definition of how to interpret the B axis. [1]
     *
     * @return the definition
     */
    public E_AxisNative getAxisB() {
        return axes[1];
    }

    /**
     * Gets the definition of how to interpret the C axis. [1]
     *
     * @return the definition
     */
    public E_AxisNative getAxisC() {
        return axes[2];
    }

    /**
     * Returns the coordinates read from the input stream. [5]
     *
     * @param isCanonicalElseNative whether to use the canonical order of the components in each coordinate triple as
     *                              defined in the constructor or the original native order from the input stream
     * @param unit                  the unit of the coordinates
     * @return the coordinates
     */
    public List<Coordinates> getCoordinates(boolean isCanonicalElseNative, E_Unit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        List<Coordinates> coordinates = new ArrayList<>();
        List<Coordinates> myCoordinates = new ArrayList<>();
        instream.lines().forEach(line -> {
            double[] d = Arrays.stream(line.split(",")).map(String::trim).mapToDouble(Double::parseDouble).toArray();
            if(d.length < 3 || d.length > 3)
                throw new IllegalArgumentException("Coordinates passed in are not on the 3 dimensional plane, cannot process");
            if (isCanonicalElseNative) {
                d = E_AxisNative.mapTo(d, this.axes);
            }
            myCoordinates.add(new Coordinates(nativeUnit.convertTo(d[0], canonicalUnit), nativeUnit.convertTo(d[1],
                    canonicalUnit), nativeUnit.convertTo(d[2], canonicalUnit)));
            coordinates.add(new Coordinates(nativeUnit.convertTo(d[0], unit), nativeUnit.convertTo(d[1], unit),
                    nativeUnit.convertTo(d[2], unit)));
        });
        this.myCoordinates = myCoordinates;
        try {
            instream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }


    /**
     * Gets the unit of the native coordinate system. [1]
     *
     * @return the unit
     */
    public E_Unit getUnitNative() {
        return nativeUnit;
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
        public Coordinates(double f, double s, double t) {
            if (Double.isNaN(f) || Double.isNaN(s) || Double.isNaN(t)) {
                throw new IllegalArgumentException("Double must be a number value");
            }
            this.first = f;
            this.second = s;
            this.third = t;
        }

        /**
         * gets the first coordinate
         *
         * @return the first coordinate
         */
        public double getFirst() {
            return first;
        }

        /**
         * gets the second coordinate
         *
         * @return the second coordinate
         */
        public double getSecond() {
            return second;
        }

        /**
         * gets the third coordinate
         *
         * @return the third coordinate
         */
        public double getThird() {
            return third;
        }


        /**
         * Outputs the triple in the form (first second third).
         *
         * @return the string
         */
        @Override
        public String toString() {
            return "(" + first + " " + second + " " + third + ")";
        }

        /**
         * method that calculates the distance between 2 points on any combination of dimensional axes
         *
         * @param axes  the dimension(s) the distance should be calculated on
         * @param p1    point one
         * @param p2    point two
         * @return  the distance between the points based on axe(s) passed in
         */
        private double calculateDistance(E_AxisCombinationNeutral axes, Coordinates p1, Coordinates p2) {
            switch (axes) {
                case FIRST:                 //(X2 - X1)
                    return Math.abs(p2.first - p1.first);
                case SECOND:                //(Y2 - Y1)
                    return Math.abs(p2.second - p1.second);
                case THIRD:                 //(Z2 - Z1)
                    return Math.abs(p2.third - p1.third);
                case FIRST_SECOND:          //(X2,Y2) - (X1,Y1)
                    return distance2D(p1.first, p2.first, p1.second, p2.second);
                case FIRST_THIRD:           //(X2,Z2) - (X1,Z1)
                    return distance2D(p1.first, p2.first, p1.third, p2.third);
                case SECOND_THIRD:          //(Y2,Z2) - (Y1,Z1)
                    return distance2D(p1.second, p2.second, p1.third, p2.third);
                case FIRST_SECOND_THIRD:    //(X2,Y2,Z2) - (X1,Y1,Z1)
                    return distance3D(p1.first, p2.first, p1.second, p2.second, p1.third, p2.third);
            }
            return 0.0;
        }

        /**
         * Helper method of calculateDistances that accounts for 2D distance calculations
         *
         * @param X1    point 1 X coordinate
         * @param X2    point 2 X coordinate
         * @param Y1    point 1 Y coordinate
         * @param Y2    point 2 Y coordinate
         * @return  the distance between the XY of point 1 and point 2
         */
        private double distance2D(double X1, double X2, double Y1, double Y2) {
            return Math.abs(Math.sqrt(Math.pow(Math.abs(X2 - X1), 2) + Math.pow(Math.abs(Y2 - Y1), 2)));
        }

        /**
         * Helper method of calculateDistances that accounts for 3D distance calculations
         *
         * @param X1    point 1 X coordinate
         * @param X2    point 2 X coordinate
         * @param Y1    point 1 Y coordinate
         * @param Y2    point 2 Y coordinate
         * @param Z1    point 1 Z coordinate
         * @param Z2    point 2 Z coordinate
         * @return  the distance between the XYZ of point 1 and point 2
         */
        private double distance3D(double X1, double X2, double Y1, double Y2, double Z1, double Z2) {
            return Math.abs(Math.sqrt(Math.pow(X2 - X1, 2) + Math.pow(Y2 - Y1, 2) + Math.pow(Z2 - Z1, 2)));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinates)) return false;

            Coordinates that = (Coordinates) o;

            return Double.compare(that.first, first) == 0 && Double.compare(that.second, second) == 0 && Double.compare(that.third, third) == 0;
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
        FIRST_SECOND_THIRD      //((X2,Y2,Z2) - (X1,Y1,Z1))
    }

    /**
     * Defines how to interpret a lettered axis; i.e., axisA, axisB, or axisC. [6]
     */
    public enum E_AxisNative {
        X_MINUS, X_PLUS, Y_MINUS, Y_PLUS, Z_MINUS, Z_PLUS;

        /**
         * Method used to map the ABC passed in from instream to XYZ internally
         *
         * @param axes  the ABC to be mapped
         * @param def   the mapping scheme
         * @return  a double array representing the XYZ mapping of ABC
         */
        private static double[] mapTo(double[] axes, E_AxisNative[] def) {
            double[] converted = new double[axes.length];
            for (int i = 0; i < axes.length; i++) {
                switch (def[i]) {
                    case X_MINUS:
                        if (axes[0] != 0.0)
                            converted[i] = axes[0] * -1;
                        else
                            converted[i] = axes[0];
                        break;
                    case X_PLUS:
                        converted[i] = axes[0];
                        break;
                    case Y_MINUS:
                        if (axes[0] != 0.0)
                            converted[i] = axes[1] * -1;
                        else
                            converted[i] = axes[1];
                        break;
                    case Y_PLUS:
                        converted[i] = axes[1];
                        break;
                    case Z_MINUS:
                        if (axes[0] != 0.0)
                            converted[i] = axes[2] * -1;
                        else
                            converted[i] = axes[2];
                        break;
                    case Z_PLUS:
                        converted[i] = axes[2];
                        break;
                }
            }
            return converted;
        }

        public String getCardinalAxis() {
            switch (this) {
                case X_PLUS:
                    return ("X");
                case X_MINUS:
                    return ("X");
                case Y_PLUS:
                    return ("Y");
                case Y_MINUS:
                    return ("Y");
                case Z_PLUS:
                    return ("Z");
                case Z_MINUS:
                    return ("Z");
            }
            return "";
        }

    }

    /**
     * Defines the unit of the native coordinate system. [4]
     */
    public enum E_Unit {
        FEET, KILOMETERS, METERS, MILES;

        /**
         * Conversion method for distance units
         *
         * @param val   the double to be converted
         * @param unit  the unit to convert to
         * @return  the converted unit
         */
        private double convertTo(double val, E_Unit unit) {
            val = toMeter(val);
            switch (unit) {
                case FEET:
                    return val * 3.28084;
                case KILOMETERS:
                    return val * .001;
                case MILES:
                    return val * .000621371;
            }
            return val;
        }

        /**
         * Helper method for converTo that changed val to meters for simplified conversion process
         *
         * @param val   double to be converted to meter
         * @return  the value converted to meters from the original unit
         */
        private double toMeter(double val) {
            switch (this) {
                case KILOMETERS:
                    return val * 1000.0;
                case METERS:
                    return val * 1.0;
                case MILES:
                    return val * 1609.34;
                case FEET:
                    return val * .3048;
            }
            return val;
        }
    }
}
