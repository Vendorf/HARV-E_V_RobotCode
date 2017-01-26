package org.usfirst.frc.team4585.robot.harve.controller;

import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team4585.robot.harve.model.*;
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
	double[] rotationSamples;
	int currentSample;
	long changeInTime;
	double rotLimit;
	double time;

	public HarvController() {
<<<<<<< HEAD
		rps = 1;
		maxRotationPerIteration = rps * 360 * (millisPerIteration / 1000);
		rotationSamples = new double[10][2];
=======
		rotationSamples = new double[10];
>>>>>>> origin/master
		currentSample = 0;
		changeInTime = 0;
		drive = new HarvDrive(0, 1, 2, 3);
		input = new HarvInput(0);
		dashboard = new SmartDashboard();
		degreesRotated = 0;
		sensors = new Sensors();
		time = 0;
	}

	private void findRotationAcceleration(int iterationTime) {// code for
																// calibrating
																// robot
		if (time + 40 < System.currentTimeMillis() && currentSample < 10) {
			rotationSamples[currentSample] = sensors.getAngle();
			currentSample += 1;
			changeInTime += System.currentTimeMillis() - time;
		}
	}

<<<<<<< HEAD
	private void augmentedDriveControl() {
		final double angleToMagnitude = 3;
		final double skewTolerance = 3;
		final double motorDeadzone = 0.14;
		final double B = 1.2;
		final double D = 0.008;//scalar for the rotation value to make sure it is below 1
		final double A = .90;//scalar for how fast the robot actualy turns under user controll
		final double C = 1.6;
		double rotationValue;
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1-Math.abs(magY);
		angleDifference = sensors.getAngle() - intendedDegrees;
		intendedDegrees += input.getJoystickInput(Axis.Z) * maxRotationPerIteration * rotLimit * B;
		rotationValue = Math.abs((angleDifference * D)) + 0.16;
		if(input.getJoystickInput(Axis.Z) < 0 - motorDeadzone || input.getJoystickInput(Axis.Z) > 0 + motorDeadzone){
			magRot = (input.getJoystickInput(Axis.Z) * rotLimit * A) * (rotationValue *C + 1);
		}
		else if( angleDifference < 0 - skewTolerance){
			timeRotating += millisPerIteration/1000;
			magRot = Math.abs(rotationValue);
		}
		else if(angleDifference > 0 + skewTolerance){
			timeRotating += millisPerIteration/1000;
			magRot = -Math.abs(rotationValue);
		}
		else{
			timeRotating = 0;
			magRot = 0;
		}
=======
	private void agmentedDriveControl() {
		final double rpm = 0;
		final double rotationcoefficient = 8;
		final double skewTolerance = 4;
		final double skewTolerancecoefficient = 1.00;
		input.update();
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1 - Math.abs(magY);
		magRot = input.getJoystickInput(Axis.Z) * rotLimit;

		degreesRotated += (magRot * rotationcoefficient);
		double skew = Math.abs(degreesRotated - sensors.getAngle());

		if (degreesRotated > sensors.getAngle() * skewTolerancecoefficient - skewTolerance) magRot = magRot - (-skew / 30);
		else if (degreesRotated < sensors.getAngle() * skewTolerancecoefficient + skewTolerance) magRot = magRot - (skew / 30);
		else
			;
>>>>>>> origin/master
	}

	private void newAugmentedDriveControl() {
		final double skewTolerance = 4;
		final double maxAngularSpeed = 180;
		
		double degreesPerIteration=(maxAngularSpeed * millisBetweenIterations)/1000;
		double rotationCoefficient=.1;

		input.update();
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1 - Math.abs(magY);
		degreesRotated += input.getJoystickInput(Axis.Z) * rotLimit * degreesPerIteration;
		
		if(sensors.getAngle()-degreesRotated > skewTolerance)
			magRot=.25+(sensors.getAngle()-degreesRotated)*rotationCoefficient;
		else
			magRot = 0;

	}

	private void showInformation() {
		SmartDashboard.putNumber("intended rotation", this.intendedDegrees);
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
<<<<<<< HEAD
		if (System.currentTimeMillis() >= time + millisPerIteration) {
			input.update();
=======
		if (System.currentTimeMillis() >= time + millisBetweenIterations) {
			// input.update();
>>>>>>> origin/master
			// magX = input.getJoystickInput(Axis.X);
			// magY = input.getJoystickInput(Axis.Y);
			// magRot = input.getJoystickInput(Axis.Z);//z=log3(y-0.5)-2.2/2
			// rotLimit = 1 -Math.abs(magY);
			//// //-(Math.pow((Math.abs(magY)-0.5),(1.0/3.0)) +1.2)/2.0 + 1.0;
			// magRot = magRot * Math.abs(rotLimit);

<<<<<<< HEAD
			this.augmentedDriveControl();
=======
			this.newAugmentedDriveControl();
>>>>>>> origin/master

			sensors.updateBIAcceleration();

			this.showInformation();
			// if(magY!=0) magRot = Math.copySign(Math.abs(magRot) *
			// (0.25/(Math.abs(magY)+.1)), magRot);
			time = System.currentTimeMillis();
			
			drive.updateDrive(magX, magY, magRot);
		}
	}
	public void test() {
	}

}
