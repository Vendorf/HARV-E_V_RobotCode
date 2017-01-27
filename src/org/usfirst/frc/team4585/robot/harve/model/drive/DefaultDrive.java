package org.usfirst.frc.team4585.robot.harve.model.drive;

import edu.wpi.first.wpilibj.Spark;

public class DefaultDrive extends HarvDrive{
	private Spark leftSide,rightSide;
	
	public DefaultDrive(short leftSide, short rightSide){
		this.leftSide = new Spark(leftSide);
		this.rightSide = new Spark(rightSide);
		this.rightSide.setInverted(true);
	}
	
	public void invertMotors(){
		if(this.rightSide.getInverted())
			this.rightSide.setInverted(false);
		else this.rightSide.setInverted(true);
		if(this.leftSide.getInverted())
			this.leftSide.setInverted(false);
		else this.leftSide.setInverted(true);
	}
	
	public DefaultDrive(int leftSide, int rightSide){
		super();
		this.leftSide = new Spark(leftSide);
		this.rightSide = new Spark(rightSide);
	}
	
	@Override
	public void update(double magY, double magRot){
		if(Math.abs(magY) > 1)this.setMagY(1);
		else this.setMagY(magY);
		if(Math.abs(magRot) > 1)this.setMagRot(1);
		else this.setMagRot(magRot);
		
		leftSide.set(this.getMagY() - this.getMagRot());
		rightSide.set(this.getMagY() + this.getMagRot());
	}

	@Override
	public void update(double magX, double magY, double magRot) {
		update(magY, magRot);
	}

	@Override
	public double getWheelRotation() {
		return 0;
	}

	@Override
	public double getWheelSpeed() {
		return 0;
	}
}
