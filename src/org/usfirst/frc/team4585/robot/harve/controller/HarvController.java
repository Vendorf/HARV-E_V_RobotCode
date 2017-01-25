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
		rotationSamples = new double[10];
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
		SmartDashboard.putNumber("intended rotation", this.degreesRotated);
		SmartDashboard.putNumber("YSpeed", sensors.getSpeedY());
		SmartDashboard.putNumber("XSpeed", sensors.getSpeedX());
		SmartDashboard.putNumber("Zspeed", sensors.getSpeedZ());
		SmartDashboard.putNumber("Rotation in degrees", (int) sensors.getAngle());
		SmartDashboard.putNumber("horozontal", magX);
		SmartDashboard.putNumber("vertical", magY);
		SmartDashboard.putNumber("rotation", magRot);
	}

	public void robotInit() {
		time = System.currentTimeMillis();
		input.makeRound(true);
		sensors.calibrateGyro();
	}

	public void autonomous() {

	}

	public void operatorControl() {
		if (System.currentTimeMillis() >= time + millisBetweenIterations) {
			// input.update();
			// magX = input.getJoystickInput(Axis.X);
			// magY = input.getJoystickInput(Axis.Y);
			// magRot = input.getJoystickInput(Axis.Z);//z=log3(y-0.5)-2.2/2
			// rotLimit = 1 -Math.abs(magY);
			//// //-(Math.pow((Math.abs(magY)-0.5),(1.0/3.0)) +1.2)/2.0 + 1.0;
			// magRot = magRot * Math.abs(rotLimit);

			this.newAugmentedDriveControl();

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
