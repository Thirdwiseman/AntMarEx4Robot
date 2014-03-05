package Robot;

public class Position 
{
private int x;
private int y;
private int dir;
private int targetX;
private int targetY;
private int goalX;
private int goalY;

	public Position(int x, int y, int dir, int goalX, int goalY)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.goalX = goalX;
		this.goalY = goalY;
		/**
		 * THE FOLLOWING TWO LINES WERE YOUR RESPONSIBILITY
		 */
		this.targetX = x;
		this.targetY = y;
	}
	
	public void turnRight()
	{
		dir = (dir + 1) % 4;
	}
	
	public void turnLeft()
	{
		dir = (dir - 1) % 4;
	}
	
	public void move()
	{
		if(dir == 0)
		{
			y ++;
			targetY = y + 1;
			targetX = x;
		} else if(dir == 1)
		{
			x ++;
			targetX = x + 1;
			targetY = y;
		} else if(dir == 2)
		{
			y --;
			targetY = y - 1;
			targetX = x;
		}else if(dir == 3)
		{
			x --;
			targetX = x - 1;
			targetY = y;
		}
	}

	public int getTargetX() {
		return targetX;
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getGoalX() {
		return goalX;
	}

	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}
	
	public String toString()
	{
		return "X: " + x + " Y: " + y + " Dir:" + dir + "\n targetX: " + targetX + " targetY: " + targetY;
	}
}
