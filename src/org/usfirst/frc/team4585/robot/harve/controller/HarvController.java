package org.usfirst.frc.team4585.robot.harve.controller;

import org.usfirst.frc.team4585.robot.harve.model.*;
import org.usfirst.frc.team4585.robot.harve.view.*;

public class HarvController{
	HarvDrive drive;
	HarvInput input;
	
	double magX, magY, magRot;
	
	public HarvController(){
		drive = new HarvDrive(0,1,2,3);
		input = new HarvInput(0);
	}
	
	public void robotInit() {

	}
	
	public void autonomous() {

	}
	
	public void operatorControl() {
		input.makeRound(true);
		input.update();
		drive.updateDrive(input.getJoystickInput(Axsis.X), input.getJoystickInput(Axsis.Y), input.getJoystickInput(Axsis.Z));
	}
	
	public void test() {
	}
	
}
