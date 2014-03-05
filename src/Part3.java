import java.util.ArrayList;

import util.Graph;
import Robot.Position;
import Robot.RobotMove;
import Robot.RobotPuzzle;
import Robot.Behaviours.Blockage;
import Robot.Behaviours.DriveForwards;
import Robot.Behaviours.FollowLineLeft;
import Robot.Behaviours.FollowLineRight;
import Robot.Behaviours.Junction3;
import Robot.Behaviours.Junction4;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import util.Search;

public class Part3 extends Robot.RobotDemoNoExit implements Runnable {

	private final NXTRegulatedMotor wheelLeft = Motor.C;
	private final NXTRegulatedMotor wheelRight = Motor.B;
	private final LightSensor lightLeft;
	private final LightSensor lightRight;
	private int MoveSpeed = 130;
	private ArrayList<RobotMove> pattern;
	private int whiteRight;
	private int blackRight;
	private int whiteLeft;
	private int blackLeft;
	private int x;
	private int y;
	private int dir = 0;
	private int goalX;
	private int goalY;

	public Part3() {
		super();
		lightLeft = new LightSensor(SensorPort.S2);
		lightRight = new LightSensor(SensorPort.S4);
		pattern = new ArrayList<RobotMove>();

		Button.ESCAPE.addButtonListener(new ButtonListener() {

			@Override
			public void buttonPressed(Button b) {
				dir = -1;
			}
			
			@Override
			public void buttonReleased(Button b) {

			}
		});
		Button.LEFT.addButtonListener(new ButtonListener() {

			@Override
			public void buttonPressed(Button b) {
				dir = 0;
			}

			@Override
			public void buttonReleased(Button b) {

			}
		});
		Button.ENTER.addButtonListener(new ButtonListener() {

			@Override
			public void buttonPressed(Button b) {
				dir = 1;
			}

			@Override
			public void buttonReleased(Button b) {

			}
		});
		Button.RIGHT.addButtonListener(new ButtonListener() {
			@Override
			public void buttonPressed(Button b) {
				dir = 2;
			}

			@Override
			public void buttonReleased(Button b) {

			}
		});
	}

	public static void main(String args[]) {
		Part3 demo = new Part3();
		Button.waitForAnyPress();
		demo.run();

	}

	@Override
	public void run() {

		lightCalib();
		Graph g = new Graph(10, 7);
		x = 0;
		y = 0;
		goalX = 9;
		goalY = 6;
		Position pos = new Position(x, y , 0, goalX, goalY);

		Search<RobotPuzzle, RobotMove> search = new Search<RobotPuzzle, RobotMove>(
				2, new RobotPuzzle(g, pos.getX(), pos.getY(), pos.getGoalX(), pos.getGoalY()), 
				new RobotPuzzle(g, pos.getGoalX(), pos.getGoalY(), pos.getY(), pos.getX()));

		System.out.println(pattern = search.findSolution());

		Button.waitForAnyPress();
		System.out.println("Press button to go!");
		Button.waitForAnyPress();
		pilot.setTravelSpeed(MoveSpeed);
		pilot.setRotateSpeed(MoveSpeed);
		wheelLeft.setSpeed(MoveSpeed);
		wheelRight.setSpeed(MoveSpeed);
		
		if (pattern.get(0).getDir() == 1) {
			pilot.rotate(-93);
			pos.setDir(1);
		} else if (pattern.get(0).getDir() == 2) {
			pilot.rotate(-186);
			pos.setDir(2);
		} else if (pattern.get(0).getDir() == 3) {
			pilot.rotate(93);
			pos.setDir(3);
		}
		pattern.remove(0);
		pos.move();
		System.out.println(pos);
		Delay.msDelay(2001);
		Behavior[] behaviours = {
				new DriveForwards(pilot),
				new FollowLineLeft(wheelRight, SensorPort.S2, whiteLeft,
						blackLeft),
				new FollowLineRight(wheelLeft, SensorPort.S4, whiteRight,
						blackRight),
				new Junction4(Motor.B, Motor.C, whiteRight, whiteLeft,
						blackRight, blackLeft, pattern, pos, new UltrasonicSensor(SensorPort.S1), g)};

		Arbitrator arby = new Arbitrator(behaviours);
		arby.start();
	}

	public void lightCalib() {
		System.out.println("Press button on black");
		Button.waitForAnyPress();
		blackRight = lightRight.getLightValue();
		blackLeft = lightLeft.getLightValue();
		System.out.println("Press button on white");
		Button.waitForAnyPress();
		whiteRight = lightRight.getLightValue();
		whiteLeft = lightLeft.getLightValue();
	}
}
