package org.usfirst.frc.team4585.robot.harve.model;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Gyroscope {
	ADXRS450_Gyro gyroscope;
	
	public Gyroscope(){ //default constructor
		gyroscope = new ADXRS450_Gyro();
	}
	
	public boolean calibrate(){
		boolean hasCalibrated = false;
		try{
			gyroscope.calibrate();
			hasCalibrated = true;
		}catch(Exception e){
			
		}
		return hasCalibrated;
	}
	
	public void reset(){
		
	}
}
