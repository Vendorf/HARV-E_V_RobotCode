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
	
	Stabilize stabilizer;
	
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
	private double[] pMagRot;

	public HarvController() {
		millisPerIteration = 20;
		rps = 1;
		maxRotationPerIteration = 0;
		drive = new MecanumDrive(0, 1, 2, 3);
//		drive = new DefaultDrive(0,1);
		input = new HarvInput(0);
		autonomous = new HarvAutoController();
		dashboard = new SmartDashboard();
		sensors = new Sensors();
		pMagRot = new double[20];
		time = 0;
		
		stabilizer = new Stabilize();
		//Commented out as it does not work ATM and should not be called from here anyways (should go from Robot.java)
		this.stabilizeRobot();
	}

	private void findIntendedAngle(){//this method tries to get intended degrees close to the actual degrees of the robot
		final double A = 1;
		final double B = 2;
		final double C = 1.1;
		double y = (Math.pow(B * C,Math.abs(input.getJoystickInput(Axis.Z))) - A);
		if(y >0 + 0.111)
			intendedAngle += Math.copySign(y, input.getJoystickInput(Axis.Z)) * this.maxRotationPerIteration;//exponent curve close to robots acceleration
		else
			intendedAngle += 0;
	}

	private void augmentedDriveControl() {
		maxRotationPerIteration = rps * 360 * (millisPerIteration / 1000);
		final double A = 0.12;
		final double B = 2;
		final double C = 0.002;//value to subtract for decelration
		final double D = 0.008;//scalar for the rotation value to make sure it is below 1
		final double skewTolerance = 1.8;
		double rotationValue = Math.abs((angleDifference * D)) + 0.16;
		double pMagRot = 0;
		double pMagRotPlaceHolder = 0;
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1-(Math.abs(magY)* 0.70);
		
		angleDifference = sensors.getAngle() - intendedAngle;
		
		this.findIntendedAngle();
		rotationValue = Math.abs((angleDifference * D));
		if(input.getJoystickInput(Axis.Z) < 0|| input.getJoystickInput(Axis.Z) > 0){//detects if there is imput and sets magRot acordingly
			if(angleDifference < 0 - skewTolerance){
				magRot = input.getJoystickInput(Axis.Z)*rotLimit + Math.abs(rotationValue) * B;
			}else if(angleDifference > 0 + skewTolerance){
				magRot = input.getJoystickInput(Axis.Z)*rotLimit - Math.abs(rotationValue) * B;
			}
			
			for(int i = 0; i < this.pMagRot.length; i ++){//
				if(i == this.pMagRot.length -1) 
					this.pMagRot[i] = magRot;
				else
					this.pMagRot[i] = this.pMagRot[i +1];
			}
			
			pMagRot = this.pMagRot[this.pMagRot.length-1];
		}	
		else if(angleDifference < 0 - skewTolerance || angleDifference > 0 + skewTolerance){// corrects based off the intended angle
			if(angleDifference < 0 -skewTolerance && pMagRot < -A){
				pMagRot -= C;
				intendedAngle += pMagRot * maxRotationPerIteration;
				SmartDashboard.putNumber("pmagRot", pMagRot);

			}else if(angleDifference > 0 + skewTolerance && pMagRot > A){
				pMagRot -= C;
				intendedAngle += pMagRot * maxRotationPerIteration;
				SmartDashboard.putNumber("pmagRot", pMagRot);

			}else{
				if(angleDifference < 0 -skewTolerance){
					magRot = Math.abs(rotationValue + A);
				}else if( angleDifference > 0 + skewTolerance){
					magRot = -Math.abs(rotationValue + A);
				}
			}
		}else{
			magRot = 0;
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
		SmartDashboard.putNumber("magRot valuesFirst", this.pMagRot[0]);
		SmartDashboard.putNumber("magRot values1", this.pMagRot[1]);
		SmartDashboard.putNumber("magRot values2", this.pMagRot[2]);
		SmartDashboard.putNumber("magRot valuesLast", this.pMagRot[this.pMagRot.length -1]);

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
			this.augmentedDriveControl();
			sensors.updateBIAcceleration();
			this.showInformation();
			time = System.currentTimeMillis();
			drive.update(magX, magY, magRot);
		}
	}
	
	public void stabilizeRobot(){
		while(true){
			double inX = input.getJoystickInput(Axis.X);
			double inY = input.getJoystickInput(Axis.Y);
			if(inX == 0 && inY == 0){
				double[] magArray = stabilizer.returnToPos();
				drive.update(magArray[0], magArray[1], 0);
			}
			else{
				stabilizer.reset();
			}
		}
	}
	
	public void test() {
	}

}
