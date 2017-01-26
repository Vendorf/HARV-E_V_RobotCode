package org.usfirst.frc.team4585.robot.harve.model;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class VoltageRegulator {
	static private PowerDistributionPanel powerDistroPanel;
	static private double cumulativeCurrent;
	static private double scalingCoefficient;
	static private long time;
	static private long timeDifference;
	static private double[] voltageSamples;
	
	public VoltageRegulator(int sampleSize){
		powerDistroPanel = new PowerDistributionPanel();
		cumulativeCurrent = 0;
		scalingCoefficient = 0;
		time = 0;
		voltageSamples = new double[sampleSize];
	}
	
	public void update(){
		timeDifference = time - System.currentTimeMillis();
		time = System.currentTimeMillis();
		trackPowerConsumption();
		
	}
	
	public void getSample(){
		
	}
	
	private void trackPowerConsumption(){
		cumulativeCurrent += (Math.pow(powerDistroPanel.getTotalCurrent()/48, 1.75)-5)/50;
		if(cumulativeCurrent < 0)
			cumulativeCurrent  = 0;
		
		for(int i =0; i < voltageSamples.length; i++){
			if(i > voltageSamples.length && i < voltageSamples.length- 1)
				voltageSamples[i] = voltageSamples[i + 1];
			else
				voltageSamples[i] = 0;
		}
		voltageSamples[voltageSamples.length -1] = powerDistroPanel.getVoltage();
	}
	
	private double getAverageVoltage(){
		double averageVoltage = 0;
		for(int i =0; i < voltageSamples.length; i++)
			averageVoltage += voltageSamples.length;
		averageVoltage /= voltageSamples.length;
		if(powerDistroPanel.getVoltage() < averageVoltage)
			averageVoltage = (averageVoltage + powerDistroPanel.getVoltage())/2;
		
		return averageVoltage;
	}
	
	public double calculateCoefficient(final double voltageThreshhold){
		double scalingCoefficient = 0;
		double adjustedCumulativeCurrent = cumulativeCurrent;
		if(!(adjustedCumulativeCurrent > 0 || adjustedCumulativeCurrent < 0))
			adjustedCumulativeCurrent = 100;
		
		if(getAverageVoltage() < voltageThreshhold)
			scalingCoefficient = Math.min(Math.pow(1/(voltageThreshhold + 1 - getAverageVoltage()), 3), Math.pow((100/adjustedCumulativeCurrent), 3));
		else
			scalingCoefficient = Math.pow((100/adjustedCumulativeCurrent), 3);
		
		if(scalingCoefficient > 1)
			scalingCoefficient = 1;
		this.scalingCoefficient = scalingCoefficient;
		return scalingCoefficient;
	}
}
