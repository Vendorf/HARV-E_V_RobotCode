package org.usfirst.frc.team4585.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;

import org.usfirst.frc.team4585.robot.harve.controller.HarvController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {

	HarvController robotController;
	
	public Robot() {
		robotController = new HarvController();
	}

	@Override
	public void robotInit() {
		robotController.robotInit();
	}
	
	@Override
	public void autonomous() {
		while(this.isAutonomous() && this.isEnabled()){
			robotController.autonomous();
		}
	}
	
	@Override
	public void operatorControl() {
		while(isOperatorControl()&& isEnabled() ){
			robotController.operatorControl();
		}
	}
	
	@Override
	public void test() {
		while(this.isTest() && isEnabled()){
			robotController.test();
		}
	}
}
