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
		magX = input.getJoystickInput(Axsis.X);
		magY = input.getJoystickInput(Axsis.Y);
		magRot = input.getJoystickInput(Axsis.Z);
		
		drive.updateDrive(magX, magY, magRot);
	}
	
	public void test() {
	}
	
}
