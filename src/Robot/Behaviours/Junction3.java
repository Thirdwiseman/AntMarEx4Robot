package Robot.Behaviours;

import java.util.ArrayList;

import util.Graph;
import Robot.Position;
import Robot.RobotMove;
import Robot.RobotPuzzle;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.RangeFinder;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import util.Search;

public class Junction3 implements Behavior {
	private boolean suppressed;
	private boolean takeControl;
	private SensorPort sp;
	private int blackRight;
	private int blackLeft;
	private int whiteRight;
	private int whiteLeft;
	private NXTRegulatedMotor right;
	private NXTRegulatedMotor left;
	private int patternIndex = 0;
	private ArrayList<RobotMove> pattern;
	private Position pos;
	private RangeFinder ranger;
	private Graph g;

	public Junction3(NXTRegulatedMotor right, NXTRegulatedMotor left,
			int whiteRight, int whiteLeft, int blackRight, int blackLeft,
			ArrayList<RobotMove> pattern, Position pos, RangeFinder _ranger,
			Graph g) {
		super();
		this.suppressed = false;
		this.takeControl = false;
		this.whiteRight = whiteRight;
		this.blackRight = blackRight;
		this.whiteLeft = whiteLeft;
		this.blackLeft = blackLeft;
		this.right = right;
		this.left = left;
		this.pattern = pattern;
		this.pos = pos;
		this.ranger = _ranger;
		this.g = g;

	}

	public boolean takeControl() {

		return Math.abs(SensorPort.S2.readValue() - this.blackLeft) <= 3
				&& Math.abs(SensorPort.S4.readValue() - this.blackRight) <= 3;

	}

	public void action() {
		right.stop();
		left.stop();
		pattern();
		right.stop();
		left.stop();
		
		
	}

	public void suppress() {
		suppressed = true;
	}

	public void pattern() {
		Delay.msDelay(200);
		if (!pattern.isEmpty()) {
			LCD.clear();
			System.out.println(pattern.get(0).getDir() + "       " + pos.getDir());
			Delay.msDelay(1000);
			if (pattern.get(0).getDir() == pos.getDir()) {
				left.rotate(100, true);
				right.rotate(100);
			} else if (pattern.get(0).getDir() == pos.getDir() + 1) {
				right.rotate(50, true);
				left.rotate(440);
				pos.turnRight();
			}  else if (pattern.get(0).getDir() == pos.getDir() -1) {
				left.rotate(50, true);
				right.rotate(440);
				pos.turnLeft();
			}
			if(isWall())
			{	
				g.createBlockage(pos.getX(), pos.getY(), pos.getTargetX(), pos.getTargetY());
				System.out.println(pos.getX() +"   " + pos.getY() +"   " + pos.getTargetX()+"   " + pos.getTargetY());
				Delay.msDelay(1000);
				Search<RobotPuzzle, RobotMove> search = new Search<RobotPuzzle, RobotMove>(
						2, new RobotPuzzle(g, pos.getX(), pos.getY(), pos.getGoalX(), pos.getGoalY()), 
						new RobotPuzzle(g, pos.getGoalX(), pos.getGoalY(), pos.getY(), pos.getX()));
				
				System.out.println(pattern = search.findSolution());
				
				Delay.msDelay(1000);
				
				if (pattern.get(0).getDir() - pos.getDir() == 1) 
				{
					right.rotate(50, true);
					left.rotate(440);
					pos.turnRight();
				} else if (Math.abs(pattern.get(0).getDir() - pos.getDir()) == 2) 
				{
					left.rotate(780, true);
					right.rotate(-780);
					pos.turnRight();
					pos.turnRight();
				} else if (pattern.get(0).getDir() == -1) 
				{
					left.rotate(50, true);
					right.rotate(440);
					pos.turnLeft();
				}
				pattern.remove(0);
				
			}
		} else {
			Sound.setVolume(Sound.VOL_MAX);
			Sound.beep();
			LCD.drawString("Found", 0, 0);
			//System.exit(0);
		}

		pattern.remove(0);
		pos.move();
		
	}
	
	private boolean isWall()
	{
		
		System.out.println(this.getRange());
		Delay.msDelay(1000);
		if(this.getRange() < 15)
		{
			
			left.backward();
			right.backward();
			while(!(Math.abs(SensorPort.S2.readValue() - this.blackLeft) <= 3
				&& Math.abs(SensorPort.S4.readValue() - this.blackRight) <= 3))
			{	
			}
			right.stop();
			left.stop();
			LCD.clear();
			System.out.println("UH OH SPAGHETTIOS");
			Delay.msDelay(2000);
			//System.exit(12345678);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private int getRange(){
		int readings = 29;
		int distance = 0;
		for(int i = 0; i < readings; i++)
		{
			distance += ranger.getRange();
		}
		return distance / readings;
	}
}
