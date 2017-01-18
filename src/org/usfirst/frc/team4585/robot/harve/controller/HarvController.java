package org.usfirst.frc.team4585.robot.harve.controller;

import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team4585.robot.harve.model.*;
import org.usfirst.frc.team4585.robot.harve.view.*;

public class HarvController{
	HarvDrive drive;
	HarvInput input;
	SmartDashboard dashboard;
	
	double magX, magY, magRot;
	
	public HarvController(){
		drive = new HarvDrive(0,1,2,3);
		input = new HarvInput(0);
		dashboard =  new SmartDashboard();
	}
	
	public void robotInit() {

	}
	
	public void autonomous() {

	}
	
	public void operatorControl() {
		input.makeRound(true);
		input.update();
		magX = input.getJoystickInput(Axis.X);
		magY = input.getJoystickInput(Axis.Y);
		magRot = input.getJoystickInput(Axis.Z);
		drive.updateDrive(magX, magY, magRot);
	}
	
	public void test() {
	}
	
}
