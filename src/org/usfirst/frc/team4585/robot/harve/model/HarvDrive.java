package org.usfirst.frc.team4585.robot.harve.model;

public abstract class HarvDrive {
	private double magX;
	private double magY;
	private double magRot;
	public HarvDrive(){//default constructor
		magX = 0;
		magY = 0;
		magRot = 0;
	}
	
	abstract public void update(double magX, double magY, double magRot);
	abstract public void update(double magY, double magRot);
	abstract public double getWheelRotation();
	abstract public double getWheelSpeed();
	
	public double getMagX(){
		return this.magX;
	}
	
	public double getMagY(){
		return this.magY;
	}
	
	public double getMagRot(){
		return this.magRot;
	}
	
	protected void setMagY(double magY){
		this.magY = magY;
	}
	
	protected void setMagX(double magX){
		this.magX = magX;
	}
	
	protected void setMagRot(double magRot){
		this.magRot = magRot;
	}
	
}
