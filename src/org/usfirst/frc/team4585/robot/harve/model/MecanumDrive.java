package org.usfirst.frc.team4585.robot.harve.model;

import edu.wpi.first.wpilibj.Spark;

public class MecanumDrive extends HarvDrive{
	private final int FRONTLEFT = 0, BACKLEFT = 1, FRONTRIGHT =2, BACKRIGHT = 3;
	Spark[] wheelMotors;
	Spark frontLeft, backLeft, frontRight, backRight;
	float wheelSize;
	double speed;
	public MecanumDrive(int frontLeft, int backLeft, int frontRight, int backRight){
		super();
		wheelMotors = new Spark[]{
				this.frontLeft = new Spark(frontLeft),
				this.backLeft = new Spark(backLeft),
				this.frontRight = new Spark(frontRight),
				this.backRight = new Spark(backRight)};
		wheelMotors[FRONTLEFT].setInverted(true);
		wheelMotors[BACKLEFT].setInverted(true);
		wheelSize = 0;
		speed = 0;
	}
	
	public void update(double magX, double magY, double magRot){
		this.setMagX(magX);
		this.setMagY(magY);
		this.setMagRot(magRot);
		this.wheelMotors[FRONTLEFT].set(this.getMagY() - this.getMagX()- this.getMagRot());
		this.wheelMotors[BACKLEFT].set(this.getMagY() + this.getMagX()  - this.getMagRot());
		this.wheelMotors[FRONTRIGHT].set(this.getMagY() + this.getMagX() + this.getMagRot());
		this.wheelMotors[BACKRIGHT].set(this.getMagY() - this.getMagX()+ this.getMagRot());
	}
	 
	public double getRotation(int wheel){
		double wheelRotation = 0;
		
		return wheelRotation;
	}
	
	public void setWheelRotation(double mag, int wheel){
		
	}
	
	public void setWheelPower(double mag, int wheel){
		
	}
	
	public void setWheelSpeed(double mag, int wheel){
		
	}
	
	public void setWheelSize(float size){
		
	}
	
	public float getWheelSize(){
		return this.wheelSize;
	}

	@Override
	public void update(double magY, double magRot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getWheelRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWheelSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}
}
