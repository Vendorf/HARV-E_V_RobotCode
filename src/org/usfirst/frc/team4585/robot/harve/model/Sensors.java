package org.usfirst.frc.team4585.robot.harve.model;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import java.util.ArrayList;
public class Sensors {
	ADXRS450_Gyro gyroscope;
	ADXL345_SPI spiAccelerometer;
	BuiltInAccelerometer BIAccelerometer;
	ArrayList<Double[]> accelerationList;
	int accelerationSampleSize;
	double currentSpeedX;
	double currentSpeedY;
	double currentSpeedZ;
	
	public Sensors(){ //default constructor
		gyroscope = new ADXRS450_Gyro();
		spiAccelerometer = new ADXL345_SPI(Port.kMXP, Accelerometer.Range.k2G);
		BIAccelerometer = new BuiltInAccelerometer();
		accelerationList = new ArrayList<Double[]>();
		accelerationSampleSize = 20;
	}
	
	private double average(double[] numbs){
		double value = 0;
		for(int i = 0; i < numbs.length; i ++){
			value += numbs[i];
		}
		return value/numbs.length;
	}
	
	public boolean calibrateGyro(){
		boolean hasCalibrated = false;
		try{
			gyroscope.calibrate();
			hasCalibrated = true;
		}catch(Exception e){}
		return hasCalibrated;
	}
	
	public double getAngle(){
		return gyroscope.getAngle();
	}
	
	public boolean updateBIAcceleration(){
		boolean updated = false;
		if(accelerationList.size() < accelerationSampleSize){
			Double [] values = {(double) System.currentTimeMillis(), BIAccelerometer.getX(), BIAccelerometer.getY(), BIAccelerometer.getZ()};
			accelerationList.add(values);
			updated = true;
		}else{
			accelerationList.remove(0);
			Double [] values = {(double) System.currentTimeMillis(), BIAccelerometer.getX(), BIAccelerometer.getY(), BIAccelerometer.getZ()};
			accelerationList.add(values);
			updated = true;
		}
		double averageAccelerationX = 0;
		double averageAccelerationY = 0;
		double averageAccelerationZ = 0;
		double oldestTime = accelerationList.get(0)[0];
		double newestTime = accelerationList.get(accelerationList.size()-1)[0];
		for(int i = 0; i < accelerationList.size();i ++){
			averageAccelerationX += accelerationList.get(i)[1];
			averageAccelerationY += accelerationList.get(i)[2];
			averageAccelerationZ += accelerationList.get(i)[3];
		}
		averageAccelerationX = averageAccelerationX/accelerationList.size();
		averageAccelerationY = averageAccelerationY/accelerationList.size();
		averageAccelerationZ = averageAccelerationZ/accelerationList.size();
		currentSpeedX += averageAccelerationX * (oldestTime - newestTime);
		currentSpeedY += averageAccelerationY * (oldestTime - newestTime);
		currentSpeedZ += averageAccelerationZ * (oldestTime - newestTime);
		
		return updated;
	}
	
	public boolean updateSPIAcceleration(){
		boolean hasUpdated = false;
		if(accelerationList.size() < accelerationSampleSize){
			Double [] values = {(double) System.currentTimeMillis(), spiAccelerometer.getX(), spiAccelerometer.getY(), spiAccelerometer.getZ()};
			accelerationList.add(values);
			hasUpdated = true;
		}else{
			accelerationList.remove(0);
			Double [] values = {(double) System.currentTimeMillis(), spiAccelerometer.getX(), spiAccelerometer.getY(), spiAccelerometer.getZ()};
			accelerationList.add(values);
			hasUpdated = true;
		}
		double averageAccelerationX = 0;
		double averageAccelerationY = 0;
		double averageAccelerationZ = 0;
		double oldestTime = accelerationList.get(0)[0];
		double newestTime = accelerationList.get(accelerationList.size()-1)[0];
		for(int i = 0; i < accelerationList.size();i ++){
			averageAccelerationX += accelerationList.get(i)[1];
			averageAccelerationY += accelerationList.get(i)[2];
			averageAccelerationZ += accelerationList.get(i)[3];
		}
		averageAccelerationX = averageAccelerationX/accelerationList.size();
		averageAccelerationY = averageAccelerationY/accelerationList.size();
		averageAccelerationZ = averageAccelerationZ/accelerationList.size();
		currentSpeedX += averageAccelerationX * (oldestTime - newestTime);
		currentSpeedY += averageAccelerationY * (oldestTime - newestTime);
		currentSpeedZ += averageAccelerationZ * (oldestTime - newestTime);
		return hasUpdated;
	}
	
	public BuiltInAccelerometer getAccelerometer(){
		return this.BIAccelerometer;
	}
	
	public void reset(){
		gyroscope.reset();
		for(Double[] sample: accelerationList){
			accelerationList.remove(sample);
		}
	}
	
	public double getSpeedX(){
		return this.currentSpeedX;
	}
	
	public double getSpeedY(){
		return this.currentSpeedY;
	}
	
	public double getSpeedZ(){
		return this.currentSpeedZ;
	}
	
	public void setAccelerationSampleSize(int size){
		accelerationSampleSize = size;
	}
}
