package org.usfirst.frc.team4585.robot.harve.view;

public class HarvInput {
	FlightStick flightStick;		
	double magX, magY, magRot;
	public HarvInput(){//defualt constructor
		magX = 0;
		magY = 0;
		magRot = 0;
	}
	
	public HarvInput(int chanel){
		this();
		flightStick = new FlightStick(chanel);
	}
	
	public void setJoystickChanel(int chanel){
		flightStick = new FlightStick(chanel);
	}
	
	public void update(){
		flightStick.updateFlightStick();
	}
	
	public double getJoystickInput(Axis axsis){
		return flightStick.getInput(axsis);
	}
	
	public void makeRound(boolean isRound){
		flightStick.setIsRound(isRound);
	}
	
}
