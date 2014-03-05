package Robot.Behaviours;

import Robot.Position;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.RangeFinder;
import lejos.robotics.subsumption.Behavior;

public class Blockage implements Behavior {
	private boolean suppressed;
	private boolean takeControl;
	private SensorPort sp;
	private final RangeFinder ranger;
	private NXTRegulatedMotor left;
	private NXTRegulatedMotor right;
	
	
	public Blockage(RangeFinder _ranger, NXTRegulatedMotor right, NXTRegulatedMotor left, Position pos)
	{
		ranger = _ranger;
		this.right = right;
		this.left = left;
	}
	
	public boolean takeControl() {
		if(ranger.getRange() <= 15)
		{
			return true;
		}
		return false;
	}

	@Override
	public void action() 
	{
		left.stop();
		right.stop();
		
	}

	@Override
	public void suppress() 
	{
		suppressed = true;
	}

}
