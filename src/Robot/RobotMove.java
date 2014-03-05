package Robot;

public class RobotMove {
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private final int direction;
	
	public RobotMove(int dir){
		this.direction = dir;
	}
	
	public int getDir(){
		return direction;
	}
	
	public String toString(){
		if(direction == 0){
			return "NORTH";
		} else if(direction == 1)
		{
			return "EAST";
		}else if(direction == 2)
		{
			return "SOUTH";
		}else if(direction == 3)
		{
			return "WEST";
		}
		return "}}}}}}}}}}}}}}}}}}";
	}
}
