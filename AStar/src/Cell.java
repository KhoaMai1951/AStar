import java.util.Random;

public class Cell {
	int gCost;	//cost from start to this 
	int hCost;	//cost from this to end 
	int fCost;
	String type; 
	
	int open;
	int close;
	int neutral;
	
	int col;
	int row;
	
	int routeToGo;

	public int getRouteToGo() {
		return routeToGo;
	}
	public void setRouteToGo(int routeToGo) {
		this.routeToGo = routeToGo;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getNeutral() {
		return neutral;
	}
	public void setNeutral(int neutral) {
		this.neutral = neutral;
	}
	int parentX;
	int parentY;
	
	public int getParentX() {
		return parentX;
	}
	public void setParentX(int parentX) {
		this.parentX = parentX;
	}
	public int getParentY() {
		return parentY;
	}
	public void setParentY(int parentY) {
		this.parentY = parentY;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getgCost() {
		return gCost;
	}
	public void setgCost(int gCost) {
		this.gCost = gCost;
	}
	public int gethCost() {
		return hCost;
	}
	public void sethCost(int hCost) {
		this.hCost = hCost;
	}
	
	public int getfCost() {
		return fCost;
	}
	public void setfCost(int fCost) {
		this.fCost = fCost;
	}
	public void setfCost(int gCost, int hCost) {
		this.fCost = gCost + hCost;
	}
	public Cell(int gCost, int hCost, String type) {
		super();
		this.gCost = gCost;
		this.hCost = hCost;
		this.type = type;
	}
	public Cell(String type) {
		this.type = type;
	}
	public static String cellType()
	{	
		return "PATH";
	}
	
	public static int calculateGX(int x, int y)
	{
		return  (int) Math.sqrt((x-0)*(x-0) + (y-0)*(y-0));
	}
	
	public static int calculateRealDistance(int x1, int y1, int x2, int y2)
	{
		return  (int) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public static int calculateHX(int x, int y)
	{
		return  (int) Math.sqrt((x-4)*(x-4) + (y-4)*(y-4));
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
	public int getClose() {
		return close;
	}
	public void setClose(int close) {
		this.close = close;
	}
}
