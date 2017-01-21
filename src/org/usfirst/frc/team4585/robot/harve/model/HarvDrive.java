package org.usfirst.frc.team4585.robot.harve.model;

public class HarvDrive {
	MecanumDrive mecanumDrive;
	double magX, magY, magRot;
	
	public HarvDrive(int frontLeft, int backLeft, int frontRight, int backRight){
		mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
	}
	
	public double getWheelRotation(int wheel){
		double wheelRotation = mecanumDrive.getRotation(wheel);
		return wheelRotation;
	}
	
	public void updateDrive(double magX, double magY, double magRot){
		this.magX = magX;
		this.magY = magY;
		this.magRot = magRot;
		mecanumDrive.updateMotors(magX, magY, magRot);
	}
	
	public void rotate(double magnitude){
		mecanumDrive.updateMotors(this.magX, this.magY, magnitude);
	}
	
	public void hasVoltageRegulation(boolean hasVoltageRegulation){
		
	}
}
