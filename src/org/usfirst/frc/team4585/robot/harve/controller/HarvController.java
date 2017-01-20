package org.usfirst.frc.team4585.robot.harve.controller;

import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team4585.robot.harve.model.*;
import org.usfirst.frc.team4585.robot.harve.view.*;
import java.time.Clock;;

public class HarvController{
	HarvDrive drive;
	HarvInput input;
	SmartDashboard dashboard;
	
	double magX, magY, magRot;
	double rotLimit;
	double time;
	
	Gyroscope gyro;
	
	public HarvController(){
		drive = new HarvDrive(0,1,2,3);
		input = new HarvInput(0);
		dashboard =  new SmartDashboard();
		//gyro = new Gyroscope();
		time = 0;
	}
	
	public void robotInit() {
		time = System.currentTimeMillis();
		input.makeRound(true);
	}
	
	public void autonomous() {

	}
	
	public void operatorControl() {
		if(System.currentTimeMillis() > time + 0.001){
			input.update();
			magX = input.getJoystickInput(Axis.X);
			magY = input.getJoystickInput(Axis.Y);
			magRot = input.getJoystickInput(Axis.Z);//z=log3(y-0.5)-2.2/2
			rotLimit = 1 -Math.abs(magY);
//			//-(Math.pow((Math.abs(magY)-0.5),(1.0/3.0)) +1.2)/2.0 + 1.0;
			magRot = magRot * Math.abs(rotLimit);
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
