package org.usfirst.frc.team4585.robot.harve.model.drive;

public abstract class HarvDrive {
	private double magX;
	private double magY;
	private double magRot;
	private double wheelSize;
	private double speed;
	private double maxMagRot;
	private double maxMagX;
	private double maxMagY;
	public HarvDrive(){//default constructor
		magX = 0;
		magY = 0;
		magRot = 0;
		maxMagRot = 1;
		maxMagX = 1;
		maxMagY = 1;
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
	
	public void setWheelSize(double wheelSize){
		this.wheelSize = wheelSize;
	}
	
	public double getWheelSize(){
		return this.wheelSize;
	}
	
	public double getSpeed(){
		return this.speed;
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

	public double getMaxMagRot() {
		return maxMagRot;
	}

	public void setMaxMagRot(double maxMagRot) {
		this.maxMagRot = maxMagRot;
	}

	public double getMaxMagX() {
		return maxMagX;
	}

	public void setMaxMagX(double maxMagX) {
		this.maxMagX = maxMagX;
	}

	public double getMaxMagY() {
		return maxMagY;
	}

	public void setMaxMagY(double maxMagY) {
		this.maxMagY = maxMagY;
	}
	
}
