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
	
	private double magX; //magnitude of an axis
	private double magY;
	private double magZ;
	
	private double pMagX; //previous or raw input of an axis
	private double pMagY;
	private double pMagZ;
	
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
		pMagX = joyStick.getX();
		if(!(pMagX <= deadzoneX && pMagX>= -deadzoneX))
			magX = Math.copySign(Math.pow((Math.abs(pMagX) - deadzoneX) * coeffX, curveX), pMagX);//make sure magnitude is same signed as it whent in then subtract the deadzone then multily it by the coefficiant then power it by the curv
		else
			magX = 0;
		pMagY = joyStick.getY();
		if(!(pMagY <= deadzoneY && pMagY>= -deadzoneY))
			magY = Math.copySign(Math.pow((Math.abs(pMagY) - deadzoneY) * coeffY, curveY), pMagY);
		else
			magY = 0;
		pMagZ = joyStick.getZ();
		if(!(pMagZ <= deadzoneZ && pMagZ>= -deadzoneZ))
			magZ = Math.copySign(Math.pow((Math.abs(pMagZ) - deadzoneZ) * coeffZ, curveZ), pMagZ);
		else 
			magZ = 0;
		//turning inputs round
		if(!isSquare){
			double angle0 = Math.atan2(magY, magX);
			double angle1 = Math.atan2(magY, magZ);
			double angle2 = Math.atan2(magZ, magX);
			double magX0 = Math.cos(angle0);
			double magX1 = Math.cos(angle2);
			double magY0 = Math.sin(angle0);
			double magY1 = Math.sin(angle1);
			double magZ0 = Math.cos(angle1);
			double magZ1 = Math.sin(angle2);
			magX = ((magX0 + magX1)/2) * Math.abs(magX);// get unit circle values to make sure both things added together can only = one
			magY = ((magY0 + magY1)/2) * Math.abs(magY);// then multiply them by their original value to give it controller accuracy or place in the unit circle instead of on the edge.
			magZ = ((magZ0 + magZ1)/2) * Math.abs(magZ);
		}
	}
	
	public double getInput(Axis axsis){
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
