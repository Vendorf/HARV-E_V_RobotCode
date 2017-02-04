//NOTE: Do not know how you want to actually utilize this class, so this
//is based on the idea of it returning how far the robot has steered off
//course (received via a getter) with the ability to change this value using a setter
package org.usfirst.frc.team4585.robot.harve.model;

import java.util.HashMap;
import java.util.Map;
import org.usfirst.frc.team4585.robot.harve.view.*;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class Stabilize  {
	public boolean stabilizing = false;
	//No clue if this is the right accelerometer; easy fix if this is wrong
	private BuiltInAccelerometer accelerometer;
	public Stabilize(){
		accelerometer = new BuiltInAccelerometer();
	}
	double velX = 0;
	double velY = 0;
	double velZ = 0;
	double xMoved = 0;
	double yMoved = 0;
	double zMoved = 0;
	Map<Axis, Double> velDict = new HashMap<Axis, Double>(){{
		put(Axis.X, velX);
		put(Axis.Y, velY);
		put(Axis.Z, velZ);
	}};
	Map<Axis, Double> movedDict = new HashMap<Axis, Double>(){{
		put(Axis.X, xMoved);
		put(Axis.Y, yMoved);
		put(Axis.Z, zMoved);
	}};
	private void tick(){
			double time = 0.02;
			double accX = (accelerometer.getX() * 9.81);
			double accY = (accelerometer.getY()* 9.81);
			double accZ = (accelerometer.getZ()* 9.81);
			
			xMoved += velDict.get(Axis.X) * time + 0.5 * accX * Math.pow(time, 2);
			yMoved += velDict.get(Axis.Y) * time + 0.5 * accY * Math.pow(time, 2);
			zMoved += velDict.get(Axis.Z) * time + 0.5 * accZ * Math.pow(time, 2);

			movedDict.put(Axis.X, xMoved);
			movedDict.put(Axis.Y, yMoved);
			movedDict.put(Axis.Z, zMoved);
			velDict.put(Axis.X, (accX * time));
			velDict.put(Axis.Y, (accY * time));
			velDict.put(Axis.Z, (accZ * time));		
	}
	
	public double[] returnToPos(){
		tick();
		if(movedDict.get(Axis.X) > 0 || movedDict.get(Axis.Y) > 0){
			//Ensures always adding up to one
			double totalMoved = movedDict.get(Axis.X) + movedDict.get(Axis.Y);
			double magX = xMoved / totalMoved;
			double magY = yMoved / totalMoved;
			double[] magArray = new double[]{magX, magY};
			return magArray;
		}
		else
		{
			double[] noMagArray = new double[]{0, 0};
			return noMagArray;
		}
	}
	
	
	public void setStabilizing(boolean value){
		stabilizing = value;
	}
	
	public void setVelocity(Axis ax, double value){
		velDict.put(ax, value);
	}
	public void reset(){
		movedDict.put(Axis.X, (double) 0);
		movedDict.put(Axis.Y, (double) 0);
		movedDict.put(Axis.Z, (double) 0);
		velDict.put(Axis.X, (double) 0);
		velDict.put(Axis.Y, (double) 0);
		velDict.put(Axis.Z, (double) 0);
	}
/*
	public double getMoved(Axis ax){
		return movedDict.get(ax);
	}
	public void setMoved(Axis ax, double value){
		movedDict.put(ax, value);
	}
	*/
	
}
