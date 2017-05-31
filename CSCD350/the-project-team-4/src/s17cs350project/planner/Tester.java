package s17cs350project.planner;

import java.io.*;
import java.util.List;


/*
 * Tester file made directly from the examples Dr. Tappan uploaded on shelby.
 * Ideal output shown below
 */

public class Tester {
	
	public static void main(String[] args)
	{
		String input = "1 ,2,3 \n 9,7,5 \n -1,-3, -5\n -1,-5,-9 \n 4, 6,2";
		WaypointPlanner.E_AxisNative axisA = WaypointPlanner.E_AxisNative.X_PLUS;
		WaypointPlanner.E_AxisNative axisB = WaypointPlanner.E_AxisNative.Y_PLUS;
		WaypointPlanner.E_AxisNative axisC = WaypointPlanner.E_AxisNative.Z_PLUS;
		InputStream instream = new ByteArrayInputStream(input.getBytes());
		WaypointPlanner planner = new WaypointPlanner(axisA, axisB, axisC, WaypointPlanner.E_Unit.KILOMETERS, instream);
		List<WaypointPlanner.Coordinates> coordinatesNative = planner.getCoordinates(false, WaypointPlanner.E_Unit.KILOMETERS);
		List<WaypointPlanner.Coordinates> coordinatesCanonical = planner.getCoordinates(true, WaypointPlanner.E_Unit.KILOMETERS);
		System.out.println("coordinatesNative = " + coordinatesNative);
		System.out.println("coordinatesCanonical = " + coordinatesCanonical);
		List<Double> distancesNative = planner.calculateDistances(WaypointPlanner.E_AxisCombinationNeutral.FIRST_SECOND, false, WaypointPlanner.E_Unit.KILOMETERS);
		List<Double> distancesCanonical = planner.calculateDistances(WaypointPlanner.E_AxisCombinationNeutral.FIRST_SECOND, true, WaypointPlanner.E_Unit.KILOMETERS);
		System.out.println("distancesNative= " + distancesNative);
		System.out.println("distancesCanonical = " + distancesCanonical);
		double distanceNative = planner.calculateDistance(WaypointPlanner.E_AxisCombinationNeutral.FIRST_SECOND, false, WaypointPlanner.E_Unit.KILOMETERS);
		double distanceCanonical = planner.calculateDistance(WaypointPlanner.E_AxisCombinationNeutral.FIRST_SECOND, true, WaypointPlanner.E_Unit.KILOMETERS);
		System.out.println("distanceNative = " + distanceNative);
		System.out.println("distanceCanonical = " + distanceCanonical);
	}
	
	/*
	 *	((1000.0 2000.0 3000.0) (1000.0 2000.0 3000.0))
	 *	((9000.0 7000.0 5000.0) (9000.0 7000.0 5000.0))
	 *	((-1000.0 -3000.0 -5000.0) (-1000.0 -3000.0 -5000.0))
	 *	((-1000.0 -5000.0 -9000.0) (-1000.0 -5000.0 -9000.0))
	 *	((4000.0 6000.0 2000.0) (4000.0 6000.0 2000.0))
	 *	coordinatesNative = [(1.0 2.0 3.0), (9.0 7.0 5.0), (-1.0 -3.0 -5.0), (-1.0 -5.0 -9.0), (4.0 6.0 2.0)]
	 *	coordinatesCanonical = [(1.0 2.0 3.0), (9.0 7.0 5.0), (-1.0 -3.0 -5.0), (-1.0 -5.0 -9.0), (4.0 6.0 2.0)]
	 *	distancesNative= [9.433981132056603, 14.142135623730951, 2.0, 12.083045973594572]
	 *	distancesCanonical = [9.433981132056603, 14.142135623730951, 2.0, 12.083045973594572]
	 *	distanceNative = 37.659162729382125
	 *	distanceCanonical = 37.659162729382125
	 */
	
	
	
	
	
	
	
	
	
}
