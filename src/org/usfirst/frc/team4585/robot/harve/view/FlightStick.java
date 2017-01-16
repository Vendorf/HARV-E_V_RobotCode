package org.usfirst.frc.team4585.robot.harve.view;

import edu.wpi.first.wpilibj.Joystick;

public class FlightStick {
	private double coeffX;
	private double coeffY;
	private double coeffZ;

	private double deadzoneX;
	private double deadzoneY;
	private double deadzoneZ;

	private double curveX;
	private double curveY;
	private double curveZ;
	
	private double magX;
	private double magY;
	private double magZ;
	
	private boolean isSquare;
		
	Joystick joyStick;
	
	public FlightStick(){//default constructor
		isSquare = false;
		this.coeffX = 1.0;
		this.coeffY = 1.0;
		this.coeffZ = 1.0;
		
		this.deadzoneX = 0.15;
		this.deadzoneY = 0.15;
		this.deadzoneZ = 0.15;
		
		this.curveX = 2;
		this.curveY = 2;
		this.curveZ = 2;
		
		this.magX = 0;
		this.magY = 0;
		this.magZ = 0;
	}
	
	public FlightStick(int chanel){
		this();
		joyStick = new Joystick(chanel);
	}
	
	public void updateFlightStick(){ // need to set values so they can be used to turn them into round inputs instead of square
		magX = joyStick.getX();
		if(!(magX <= deadzoneX && magX>= -deadzoneX))
			magX = Math.copySign(Math.pow((Math.abs(magX) - deadzoneX) * coeffX, curveX), magX);//make sure magnitude is same signed as it whent in then subtract the deadzone then multily it by the coefficiant then power it by the curv
		else
			magX = 0;
		magY = joyStick.getY();
		if(!(magY <= deadzoneY && magY>= -deadzoneY))
			magY = Math.copySign(Math.pow((Math.abs(magY) - deadzoneY) * coeffY, curveY), magY);
		else
			magY = 0;
		magZ = joyStick.getZ();
		if(!(magZ <= deadzoneZ && magZ>= -deadzoneZ))
			magZ = Math.copySign(Math.pow((Math.abs(magZ) - deadzoneZ) * coeffZ, curveZ), magZ);
		else
			magZ = 0;
		//turning inputs round
		if(!isSquare){
			double angle = Math.atan2(magY, magX);
			magX = Math.cos(angle) * Math.abs(magX);// get unit circle values to make sure both things added together can only = one
			magY = Math.sin(angle) * Math.abs(magY);// then multiply them by their original value to give it controller accuracy or place in the unit circle instead of on the edge.
		}
	}
	
	public double getInput(Axsis axsis){
		double magnitude = 0;
		switch(axsis){
		case X:magnitude = magX;
		break;
		case Y:magnitude = magY;
		break;
		case Z:magnitude = magZ;
		break;
		default :break;
		}
		return magnitude;
	}
	
//	public double getInput(Axsis axsis){
//		double number = 0;
//		switch(axsis){
//		case X: 
//			number = joyStick.getX();
//			if(number <= deadzoneX && number>= - deadzoneX) number = 0;
//			else{
//			number = Math.copySign((Math.abs(number) - deadzoneX) *coeffX , number);
//			number = Math.copySign(Math.pow(number, curveX), number);
//			}
//			break;
//		case Y:
//			number = joyStick.getY();
//			if(number <= deadzoneY && number>= - deadzoneY) number = 0;
//			else{
//			number = Math.copySign((Math.abs(number) - deadzoneY) *coeffY,number);
//			number = Math.copySign(Math.pow(number, curveY), number);
//			}
//			break;
//		case Z:
//			number = joyStick.getZ();
//			if(number <= deadzoneZ && number>= - deadzoneZ) number = 0;
//			else{
//			number = Math.copySign((Math.abs(number) - deadzoneZ) *coeffZ,number);
//			number = Math.copySign(Math.pow(number, curveZ), number);
//			}
//			break;
//		default:
//			break;
//		}
//		return number;
//	}

	public double getCoeffX() {
		return coeffX;
	}

	public void setCoeffX(double coeffX) {
		this.coeffX = coeffX;
	}

	public double getCoeffY() {
		return coeffY;
	}

	public void setCoeffY(double coeffY) {
		this.coeffY = coeffY;
	}

	public double getCoeffZ() {
		return coeffZ;
	}

	public void setCoeffZ(double coeffZ) {
		this.coeffZ = coeffZ;
	}

	public double getDeadzoneX() {
		return deadzoneX;
	}

	public void setDeadzoneX(double deadzoneX) {
		this.deadzoneX = deadzoneX;
	}

	public double getDeadzoneY() {
		return deadzoneY;
	}

	public void setDeadzoneY(double deadzoneY) {
		this.deadzoneY = deadzoneY;
	}

	public double getDeadzoneZ() {
		return deadzoneZ;
	}

	public void setDeadzoneZ(double deadzoneZ) {
		this.deadzoneZ = deadzoneZ;
	}

	public double getCurveX() {
		return curveX;
	}

	public void setCurveX(double curveX) {
		this.curveX = curveX;
	}

	public double getCurveY() {
		return curveY;
	}

	public void setCurveY(double curveY) {
		this.curveY = curveY;
	}

	public double getCurveZ() {
		return curveZ;
	}

	public void setCurveZ(double curveZ) {
		this.curveZ = curveZ;
	}
	
	public boolean isRound(){
		return !this.isSquare;
	}
	
	public void setIsRound(boolean isRound){
		this.isSquare = !isRound;
	}
}
