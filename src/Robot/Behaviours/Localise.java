package Robot.Behaviours;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class Localise implements Behavior {
	private boolean localised = false;
	private boolean junction = false;
	private int black;
	private int white;
	private NXTRegulatedMotor left;
	private NXTRegulatedMotor right;
	private DifferentialPilot pilot;
	
	public Localise(NXTRegulatedMotor left, NXTRegulatedMotor right, DifferentialPilot pilot, int white, int black){
		super();
		this.white = white;
		this.black = black;
		this.left = left;
		this.right = right;
		this.pilot = pilot;
		
	}

	@Override
	public boolean takeControl() {
		return !localised;
	}

	@Override
	public void action() {
		while(!(Math.abs(SensorPort.S4.readValue() - this.black) <= 3) && 
				!(Math.abs(SensorPort.S2.readValue() - this.black) <= 3)){
			pilot.forward();
			
		}
		pilot.stop();
		if((Math.abs(SensorPort.S4.readValue() - this.black) <= 3)){
			right.setSpeed(46);
			left.setSpeed(80);
			right.backward();
			left.forward();
		}
		else if ((Math.abs(SensorPort.S2.readValue() - this.black) <= 3)){
			right.setSpeed(80);
			left.setSpeed(46);
			right.forward();
			left.backward();
		}
		while(!(Math.abs(SensorPort.S4.readValue() - this.black) <= 3) 
				|| !(Math.abs(SensorPort.S2.readValue() - this.black) <= 3)){
			Thread.yield();
			
		}
		pilot.stop();
		pilot.travel(8, true);
		while(pilot.isMoving()){
			if((Math.abs(SensorPort.S2.readValue() - this.black) <= 3) 
					|| (Math.abs(SensorPort.S4.readValue() - this.black) <= 3)){
				junction = true;
			}
		}
		pilot.stop();
		pilot.travel(-8);
		if(junction){
			right.setSpeed(80);
			left.setSpeed(46);
			right.backward();
			left.forward();
			Delay.msDelay(500);
			while(!(Math.abs(SensorPort.S2.readValue() - this.black) <= 3) 
					|| !(Math.abs(SensorPort.S4.readValue() - this.black) <= 3)){
				Thread.yield();
			}
			pilot.stop();
		}
		pilot.stop();
		right.rotate(50, true);
		left.rotate(440);
		
		localised = true;
	}

	@Override
	public void suppress() {

	}

}
