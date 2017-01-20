package org.usfirst.frc.team4585.robot.harve.controller;

import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team4585.robot.harve.model.*;
import org.usfirst.frc.team4585.robot.harve.view.*;
import java.time.Clock;;

public class HarvController{
	HarvDrive drive;
	HarvInput input;
	SmartDashboard dashboard;
	Sensors sensors;
	
	double magX, magY, magRot;
	double rotLimit;
	double time;
		
	public HarvController(){
		drive = new HarvDrive(0,1,2,3);
		input = new HarvInput(0);
		dashboard =  new SmartDashboard();
		sensors = new Sensors();
		time = 0;
	}
	
	public void robotInit() {
		time = System.currentTimeMillis();
		input.makeRound(true);
		sensors.calibrateGyro();
	}
	
	public void autonomous() {

	}
	
	public void operatorControl() {
		if(System.currentTimeMillis() > time + 20){
			input.update();
			magX = input.getJoystickInput(Axis.X);
			magY = input.getJoystickInput(Axis.Y);
			magRot = input.getJoystickInput(Axis.Z);//z=log3(y-0.5)-2.2/2
			rotLimit = 1 -Math.abs(magY);
//			//-(Math.pow((Math.abs(magY)-0.5),(1.0/3.0)) +1.2)/2.0 + 1.0;
			magRot = magRot * Math.abs(rotLimit);
			sensors.updateSPIAcceleration();
			SmartDashboard.putNumber("YSpeed", sensors.getSpeedY());
			SmartDashboard.putNumber("XSpeed", sensors.getSpeedX());
			SmartDashboard.putNumber("Zspeed", sensors.getSpeedZ());
			SmartDashboard.putNumber("Rotation in degrees", sensors.getAngle());
			SmartDashboard.putNumber("horozontal", magX);
			SmartDashboard.putNumber("vertical", magY);
			SmartDashboard.putNumber("rotation", magRot);
//			if(magY!=0) magRot = Math.copySign(Math.abs(magRot) * (0.25/(Math.abs(magY)+.1)), magRot);
			time = System.currentTimeMillis();
		}
		drive.updateDrive(magX, magY, magRot);
	}
	
	public void test() {
	}
	
}
