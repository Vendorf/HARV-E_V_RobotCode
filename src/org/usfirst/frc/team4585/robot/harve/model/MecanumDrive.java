package org.usfirst.frc.team4585.robot.harve.model;

import edu.wpi.first.wpilibj.Spark;

public class MecanumDrive {
	private final int FRONTLEFT = 0, BACKLEFT = 1, FRONTRIGHT =2, BACKRIGHT = 3;
	Spark[] wheelMotors;
	Spark frontLeft, backLeft, frontRight, backRight;
	float wheelSize;
	double speed;
	public MecanumDrive(int frontLeft, int backLeft, int frontRight, int backRight){
		wheelMotors = new Spark[]{
				this.frontLeft = new Spark(frontLeft),
				this.backLeft = new Spark(backLeft),
				this.frontRight = new Spark(frontRight),
				this.backRight = new Spark(backRight)};
		wheelSize = 0;
		speed = 0;
	}
	
	public void updateMotors(double magX, double magY, double magZ){
		this.wheelMotors[FRONTLEFT].set(magY + magX - magZ);
		this.wheelMotors[BACKLEFT].set(magY - magX  - magZ);
		this.wheelMotors[FRONTRIGHT].set(magY - magX + magZ);
		this.wheelMotors[BACKLEFT].set(magY + magX + magZ);
	}
	 
	public double getRotation(int wheel){
		double wheelRotation = 0;
		
		return wheelRotation;
	}
	
	public void setRotation(double mag, int wheel){
		
	}
	
	public void setPower(double mag, int wheel){
		
	}
	
	public void setSpeed(double mag, int wheel){
		
	}
	
	public void setWheelSize(float size){
		
	}
	
	public float getWheelSize(){
		return this.wheelSize;
	}
}
