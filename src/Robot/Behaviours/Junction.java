package Robot.Behaviours;

import java.util.ArrayList;

import robotPuzzle.RobotMove;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class Junction implements Behavior {
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
	private int direction;

	public Junction(NXTRegulatedMotor right, NXTRegulatedMotor left,
			int whiteRight, int whiteLeft, int blackRight, int blackLeft,
			ArrayList<RobotMove> pattern, int direction) {
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
		this.direction = direction;
		

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
			if (pattern.get(0).getDir() == direction) {
				left.rotate(100, true);
				right.rotate(100);
			} else if (pattern.get(0).getDir() == direction + 1 || pattern.get(0).getDir() == direction -3) {
				right.rotate(50, true);
				left.rotate(440);
				direction = (direction + 1) % 4;
			}  else if (pattern.get(0).getDir() == direction -1) {
				left.rotate(50, true);
				right.rotate(440);
				direction = (direction - 1 + 4) % 4;
			}
		} else {
			Sound.setVolume(Sound.VOL_MAX);
			Sound.beep();
			LCD.drawString("Found", 0, 0);
			System.exit(0);
		}

		pattern.remove(0);

	}
}

