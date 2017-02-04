package org.usfirst.frc.team4585.robot.harve.controller;

import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team4585.robot.harve.model.*;
import org.usfirst.frc.team4585.robot.harve.model.drive.DefaultDrive;
import org.usfirst.frc.team4585.robot.harve.model.drive.HarvDrive;
import org.usfirst.frc.team4585.robot.harve.model.drive.MecanumDrive;
import org.usfirst.frc.team4585.robot.harve.model.autonomous.*;
import org.usfirst.frc.team4585.robot.harve.view.*;
import java.util.Queue;

public class HarvController {
	HarvDrive drive;
	HarvInput input;
	HarvAutoController autonomous;
	SmartDashboard dashboard;
	Sensors sensors;
	
	final double millisBetweenIterations=20;

	private double magX, magY, magRot;
	private double rotLimit;
	private double time;
	//rotation variables
	private double rps;
	private double maxRotationPerIteration;
	private double millisPerIteration;
	private double intendedAngle;
	private double angleDifference;
	private double timeRotating;
	private double[] pMagRotSamples;
	private double pMagRot;

	public HarvController() {
		millisPerIteration = 10;
		rps = 1;
		maxRotationPerIteration = 0;
		drive = new MecanumDrive(0, 1, 2, 3);
//		drive = new DefaultDrive(0,1);
		input = new HarvInput(0);
		autonomous = new HarvAutoController();
		dashboard = new SmartDashboard();
		sensors = new Sensors();
		pMagRotSamples = new double[10];
		time = 0;
	}

	private void findIntendedAngle(){//this method tries to get intended degrees close to the actual degrees of the robot
		final double A = 1;
		final double B = 2;
		final double C = 1.1;
		final double D = 1.5;
		double y = (Math.pow(B * C,Math.abs(input.getJoystickInput(Axis.Z))) - A);
		if(y >0 + 0.111)
			intendedAngle += Math.copySign(y, input.getJoystickInput(Axis.Z)) * this.maxRotationPerIteration * D;//exponent curve close to robots acceleration
		else
			intendedAngle += 0;
	}
	
	private double findIntendedAngle(double inputMag, double A, double B, double C){
		double angle = 0;
		double y = (Math.pow(B * C, Math.abs(inputMag))) - A;
		angle = y * maxRotationPerIteration;
		return angle;
	}
	
	private void augmentedDriveControlV2(){
		final double A = 0.12;
		final double D = 0.004;
		double rotationValue = 0;
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1-(Math.abs(magY));
		angleDifference = sensors.getAngle() - intendedAngle;

		rotationValue = Math.abs((angleDifference * D)) * maxRotationPerIteration;
		if(input.getJoystickInput(Axis.Z) > 0 || input.getJoystickInput(Axis.Z) < 0){
			intendedAngle = sensors.getAngle();
			magRot = input.getJoystickInput(Axis.Z) * rotLimit;
		}else{
			if(angleDifference > 0){//positive
				magRot = -(rotationValue + A) * rotLimit;
			}
			else if(angleDifference < 0){//negative
				magRot = (rotationValue + A) * rotLimit;
			}
		}
	}

	private void augmentedDriveControl() {
		maxRotationPerIteration = rps * 360 * (millisPerIteration / 1000);
		final double A = 0.12;
		final double B = 2;
		final double C = 0.076;//value to subtract for decelration
		final double D = 0.004;//scalar for the rotation value to make sure it is below 1
		final double E = 40;
		final double skewTolerance = 1.8;
		double rotationValue = Math.abs((angleDifference * D)) + 0.16;
		double pMagRotPlaceHolder = 0;
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1-(Math.abs(magY));
		
		angleDifference = sensors.getAngle() - intendedAngle;
		
		this.findIntendedAngle();
		rotationValue = Math.abs((angleDifference * D)) * maxRotationPerIteration;
		
		if(angleDifference > 0 + skewTolerance || angleDifference < 0 - skewTolerance){
//			
//			if there is input that takes priority. keep input vurtual rotation close to actual rotation
//			if(input.getJoystickInput(Axis.Z) > 0 + A|| input.getJoystickInput(Axis.Z) < 0 - A){
//				if(angleDifference > input.getJoystickInput(Axis.Z) * 5){//less than the direction that you are going
//					magRot = (input.getJoystickInput(Axis.Z) * rotLimit + rotationValue);
//				}else if(angleDifference < -input.getJoystickInput(Axis.Z) * 5){
//					magRot = (input.getJoystickInput(Axis.Z) * rotLimit + rotationValue);
//				}else{
//				magRot = input.getJoystickInput(Axis.Z);
//				}
//				
//				//get pMagRot samples while receaving input.
//				pMagRotSamples[pMagRotSamples.length -1] = magRot;
//				//update pMagRot with new samples
//				
//				//find the average of all the samples 
//				pMagRot = 0;
//				for(int i = 0; i < this.pMagRotSamples.length; i ++){
//					pMagRot += pMagRotSamples[i];
//				}
//				pMagRot /= pMagRotSamples.length;
//				
//			//there is not input but rotation is still off a bit, rotate.
//			}else{
				//find if pMagRot is negative or positive
//				if(pMagRot > 0){//pMagRot is positive
//					if(angleDifference > 0){//positive don't need to increase intended angle
//					}
//					else if(angleDifference < 0){//negative
//						pMagRot -= pMagRot * 0.90;
//						intendedAngle += Math.copySign(this.findIntendedAngle(pMagRot, 1, 2, 1.1),pMagRot) * E;
//						magRot = 0;
//					}
//				}else if(pMagRot < 0){//pMagRot is negative
//					if(angleDifference > 0){//positive
//						pMagRot -= pMagRot * 0.90;
//						intendedAngle += Math.copySign(this.findIntendedAngle(pMagRot, 1, 2, 1.1),pMagRot) * E;
//						magRot = 0;
//					}
//					else if(angleDifference < 0){//negative don't need to increase intended angle
//					}	
//				}
				if(angleDifference > 0){//positive
					magRot = -(rotationValue + A) * rotLimit;
				}
				else if(angleDifference < 0){//negative
					magRot = (rotationValue + A) * rotLimit;
				}
//			}
		}else
			magRot = 0;
		
		SmartDashboard.putNumber("pMagRot", pMagRot);
		//assign preveous magnitudes to an array for later use
		for(int i = 0; i < this.pMagRotSamples.length; i ++){//move every value down the array
			if(i != this.pMagRotSamples.length -1)
				this.pMagRotSamples[i] = this.pMagRotSamples[i +1];
		}
	}

	private void showInformation() {
		SmartDashboard.putNumber("intended rotation", this.intendedAngle);
		SmartDashboard.putNumber("angle difference", this.angleDifference);
		SmartDashboard.putNumber("YSpeed", sensors.getSpeedY());
		SmartDashboard.putNumber("XSpeed", sensors.getSpeedX());
		SmartDashboard.putNumber("Zspeed", sensors.getSpeedZ());
		SmartDashboard.putNumber("Rotation in degrees", (int) sensors.getAngle());
		SmartDashboard.putNumber("horozontal magnitude", magX);
		SmartDashboard.putNumber("vertical magnitude", magY);
		SmartDashboard.putNumber("rotation magnitude", magRot);
		SmartDashboard.putNumber("time rotating", this.timeRotating);
		SmartDashboard.putNumber("input rotation", input.getJoystickInput(Axis.Z));
		SmartDashboard.putNumber("magRot valuesLast", this.pMagRotSamples[0]);
		SmartDashboard.putNumber("magRot values1", this.pMagRotSamples[1]);
		SmartDashboard.putNumber("magRot values2", this.pMagRotSamples[2]);
		SmartDashboard.putNumber("magRot valuesFirst", this.pMagRotSamples[this.pMagRotSamples.length -1]);

	}

	public void robotInit() {
		time = System.currentTimeMillis();
		input.makeRound(true);
		sensors.calibrateGyro();
	}

	public void autonomous() {
		autonomous.start();
	}

	public void operatorControl() {
		
		if (System.currentTimeMillis() >= time + millisPerIteration) {
			input.update();
			this.augmentedDriveControlV2();
			sensors.updateBIAcceleration();
			this.showInformation();
			time = System.currentTimeMillis();
			drive.update(magX, magY, magRot);
		}
	}
	
	
	public void test() {
	}

}
