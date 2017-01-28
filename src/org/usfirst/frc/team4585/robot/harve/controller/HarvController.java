package org.usfirst.frc.team4585.robot.harve.controller;

import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team4585.robot.harve.model.*;
import org.usfirst.frc.team4585.robot.harve.model.drive.HarvDrive;
import org.usfirst.frc.team4585.robot.harve.model.drive.MecanumDrive;
import org.usfirst.frc.team4585.robot.harve.view.*;
import java.time.Clock;;

public class HarvController {
	HarvDrive drive;
	HarvInput input;
	SmartDashboard dashboard;
	Sensors sensors;
	
	final double millisBetweenIterations=20;

	double magX, magY, magRot;
	double degreesRotated;
	double rotationAcceleration;
	double[][] rotationSamples;
	int currentSample;
	long changeInTime;
	double rotLimit;
	double time;
	//rotation variables
	private double rps;
	private double maxRotationPerIteration;
	private double millisPerIteration;
	private double intendedAngle;
	private double angleDifference;
	private double timeRotating;

	public HarvController() {
		millisPerIteration = 20;
		rps = 1;
		maxRotationPerIteration = 0;
		rotationSamples = new double[10][2];
		currentSample = 0;
		changeInTime = 0;
		drive = new MecanumDrive(0, 1, 2, 3);
		input = new HarvInput(0);
		dashboard = new SmartDashboard();
		sensors = new Sensors();
		time = 0;
	}

	private void findRotationAcceleration(int iterationTime) {// code for
																// calibrating
																// robot
		if (time + 40 < System.currentTimeMillis() && currentSample < 10) {
			rotationSamples[currentSample][0] = sensors.getAngle();
			currentSample += 1;
			changeInTime += System.currentTimeMillis() - time;
		}
	}
	
	private void findIntendedDegrees(){//this method tries to get intended degrees close to the actual degrees of the robot
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
		final double B = 1.2;
		final double D = 0.008;//scalar for the rotation value to make sure it is below 1
		final double A = 2;//scalar for how fast the robot turns under user controll
		final double C = 1.6;
		final double skewTolerance = 1.8;
		double rotationValue = Math.abs((angleDifference * D)) + 0.16;
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1-(Math.abs(magY)* 0.50);
		
		angleDifference = sensors.getAngle() - intendedAngle;
		
		this.findIntendedDegrees();
		rotationValue = Math.abs((angleDifference * D)) + 0.16;
		if(input.getJoystickInput(Axis.Z) < 0|| input.getJoystickInput(Axis.Z) > 0){//detects if there is imput and sets magRot acordingly
			magRot = (input.getJoystickInput(Axis.Z) * rotLimit);
		}	
		if(angleDifference < 0 - skewTolerance){// corrects based off the intended angle
			magRot = Math.abs(rotationValue);
		}else if( angleDifference > 0 + skewTolerance){
			magRot = -Math.abs(rotationValue);
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
	}

	public void robotInit() {
		time = System.currentTimeMillis();
		input.makeRound(true);
		sensors.calibrateGyro();
	}

	public void autonomous() {

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
	
	
	public void test() {
	}

}
