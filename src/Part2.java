import java.util.ArrayList;

import util.Graph;
import Robot.RobotMove;
import Robot.RobotPuzzle;
import Robot.Behaviours.DriveForwards;
import Robot.Behaviours.FollowLineLeft;
import Robot.Behaviours.FollowLineRight;
import Robot.Behaviours.Junction;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import util.Search;

public class Part2 extends Robot.RobotDemoNoExit implements Runnable {

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
	private int dir = 0;

	public Part2() {
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
		Part2 demo = new Part2();
		Button.waitForAnyPress();
		demo.run();

	}

	@Override
	public void run() {

		lightCalib();
		Graph g = new Graph(10, 7);

		g.createBlockage(1, 1, 2, 1);
		
		g.createBlockage(0, 1, 1, 1);
		g.createBlockage(0, 2, 0, 3);
		g.createBlockage(1, 0, 2, 0);
		g.createBlockage(5, 0, 6, 0);
		g.createBlockage(4, 1, 5, 1);
		g.createBlockage(4, 2, 5, 2);
		g.createBlockage(5, 2, 5, 3);
		g.createBlockage(2, 2, 2, 3);
		g.createBlockage(2, 3, 3, 3);
		g.createBlockage(1, 5, 2, 5);
		g.createBlockage(3, 6, 4, 6);
		g.createBlockage(4, 4, 5, 4);
		g.createBlockage(5, 5, 5, 6);
		g.createBlockage(6, 4, 7, 4);
		g.createBlockage(6, 5, 7, 5);

		Search<RobotPuzzle, RobotMove> search = new Search<RobotPuzzle, RobotMove>(2, new RobotPuzzle(
				g, 0, 0, 9, 6), new RobotPuzzle(g, 9, 6, 0, 0));
		
		System.out.println(pattern = search.findSolution());
		
		
		Button.waitForAnyPress();
		System.out.println("Press button to go!");
		Button.waitForAnyPress();
		pilot.setTravelSpeed(MoveSpeed);
		pilot.setRotateSpeed(MoveSpeed);
		wheelLeft.setSpeed(MoveSpeed);
		wheelRight.setSpeed(MoveSpeed);

		if(pattern.get(0).getDir() == 1){
			pilot.rotate(-93);
			dir = 1;
		} else if(pattern.get(0).getDir() == 2){
			pilot.rotate(-186);
			dir = 2;
		} else if(pattern.get(0).getDir() == 3){
			pilot.rotate(93);
			dir = 3;
		}
		pattern.remove(0);

		
		Behavior[] behaviours = {
				new DriveForwards(pilot),
				new FollowLineLeft(wheelRight, SensorPort.S2, whiteLeft,
						blackLeft),
				new FollowLineRight(wheelLeft, SensorPort.S4, whiteRight,
						blackRight),
				new Junction(Motor.B, Motor.C, whiteRight, whiteLeft,
						blackRight, blackLeft, pattern, dir) };

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
