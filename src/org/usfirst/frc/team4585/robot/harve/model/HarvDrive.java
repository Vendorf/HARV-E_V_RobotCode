package org.usfirst.frc.team4585.robot.harve.model;

public class HarvDrive {
	MecanumDrive mecanumDrive;
	double rotLimiter;
	double yLimiter;
	double xLimiter;
	
	public HarvDrive(int frontLeft, int backLeft, int frontRight, int backRight){
		mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
		rotLimiter = 0;
	}
	
	private double limit(double value, double limiter){
		double limitedValue = 0;
		if(value < 0 && value > limiter -1){//tests if the value is negative or positive tehn tests if it is under the max value minus the limiting value
			limitedValue = value + limiter;
		}else if(value > 0 && value < 1 - limiter){
			limitedValue = value - limiter;
		}
		return limitedValue;
	}
	
	public double getWheelRotation(int wheel){
		double wheelRotation = mecanumDrive.getRotation(wheel);
		return wheelRotation;
	}
	
	public void updateDrive(double magX, double magY, double magRot){
		double driveMagX = limit(magX, xLimiter);
		double driveMagY = limit(magY, yLimiter);
		double driveMagRot = limit(magRot, rotLimiter);
		mecanumDrive.updateMotors(driveMagX, driveMagY, driveMagRot);
	}
	
	public void limitRotation(double rotLimiter){
		this.rotLimiter = rotLimiter;
	}
	
	public void limitVertical(double yLimiter){
		this.yLimiter = yLimiter;
	}
	
	public void limitHorozontal(double xLimiter){
		this.xLimiter = xLimiter;
	}
	
	public void hasVoltageRegulation(boolean hasVoltageRegulation){
		
	}
}
