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
	

	double magX, magY, magRot;
	double degreesRotated;
	double rotationAcceleration;
	int currentSample;
	long changeInTime;
	double rotLimit;
	double time;
	double degreesToAim=0;
	// variables for rotation controll
	final double millisPerIteration=20;
	double timeRotating;
	double angleDifference;
	double intendedDegrees;
	double maxRotationPerIteration;
	double rps; //rotations per second
	double[][] rotationSamples;//taking current rotation and current time
	

	public HarvController() {
		rps = 2;
		maxRotationPerIteration = rps * 360 * (millisPerIteration / 1000);
		rotationSamples = new double[10][2];
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
			rotationSamples[currentSample][0] = sensors.getAngle();
			currentSample += 1;
			changeInTime += System.currentTimeMillis() - time;
		}
	}

	private void agmentedDriveControl() {
		final double angleToMagnitude = 3;
		final double skewTolerance = 4;
		final double B = 6,D = -0.3;//D shifts the graph over into a usable range
		final double A = 1.2;
		double rotationValue;
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1-Math.abs(magY);
		angleDifference = intendedDegrees - sensors.getAngle();
		intendedDegrees = (Math.abs(input.getJoystickInput(Axis.Z)) * 1)/ (1+ A * Math.pow(Math.E,-B * (timeRotating + D)));
		//test line of code to try and have a nice acceleration curve( y = c/1+aE^-bx) might be better to have angle difference factored into c
		rotationValue = ( 1 * Math.abs(input.getJoystickInput(Axis.Z))+0.1/ (1+ A * Math.pow(Math.E,-1 * (timeRotating + D-0.1))));
		if(input.getJoystickInput(Axis.Z) < 0 && angleDifference < 0 - skewTolerance){
			timeRotating += millisPerIteration;
			magRot = -rotationValue;
		}
		else if(input.getJoystickInput(Axis.Z) > 0 && angleDifference > 0 + skewTolerance){
			timeRotating += millisPerIteration;
			magRot = rotationValue;
		}
		else timeRotating -= millisPerIteration * 2;
		
	}

	private void newAugmentedDriveControl() {
		final double skewTolerance = 4;
		final double maxAngularSpeed = 180;
		
		double degreesPerIteration=(maxAngularSpeed * millisPerIteration)/1000;
		double skew;
		double angularVelocity = 0;
		double rotationCoefficient=.1;

		input.update();
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		rotLimit = 1 - Math.abs(magY);
		degreesRotated += input.getJoystickInput(Axis.Z) * rotLimit * degreesPerIteration;
		
		if(sensors.getAngle()-degreesRotated > skewTolerance)
			magRot=(sensors.getAngle()-degreesRotated)*rotationCoefficient;

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
		if (System.currentTimeMillis() >= time + millisPerIteration) {
			// input.update();
			// magX = input.getJoystickInput(Axis.X);
			// magY = input.getJoystickInput(Axis.Y);
			// magRot = input.getJoystickInput(Axis.Z);//z=log3(y-0.5)-2.2/2
			// rotLimit = 1 -Math.abs(magY);
			//// //-(Math.pow((Math.abs(magY)-0.5),(1.0/3.0)) +1.2)/2.0 + 1.0;
			// magRot = magRot * Math.abs(rotLimit);

			this.agmentedDriveControl();

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
