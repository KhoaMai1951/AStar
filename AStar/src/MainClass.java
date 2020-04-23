import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainClass {

	public static void main(String[] args) {
		
		//Bản đồ 4x4 
		//Được đi trên dưới phải trái, đi chéo
		//Trong kết quả xuất ra, PATH là đường đi, __ là không đi, BLOCK là chướng ngại vật
		//Nếu kết quả xuất ra không có PATH thì BLOCK đã chặn hết đường đi có thể nên không có đường đi
		
		Cell[][] map = new Cell[4][4]; // OK

		//Create 4x4 graph
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				//============SET TYPE FOR EACH CELL====================
				if(row == 0 && col == 0)
				{
					map[col][row] = new Cell("START");
					map[col][row].setgCost(0);							//Local cost
					map[col][row].sethCost(Cell.calculateHX(row, col));		//Heuristic cost
					map[col][row].setfCost(5);	//Global cost
					map[col][row].setOpen(1);
					map[col][row].setClose(0);
					map[col][row].setNeutral(0);
					map[col][row].setRouteToGo(0);
				}
				else if(row == 3 && col == 3)
				{
					map[col][row] = new Cell("END");
					map[col][row].setgCost(9999);						//Local cost
					map[col][row].sethCost(Cell.calculateHX(col, row));		//Heuristic cost
					map[col][row].setfCost(9999);						//Global cost
					map[col][row].setOpen(0);
					map[col][row].setClose(0);
					map[col][row].setNeutral(1);
					map[col][row].setRouteToGo(0);
				}
				else 
				{
					
					map[col][row] = new Cell(Cell.cellType());
					map[col][row].setgCost(9999);						//Local cost
					map[col][row].sethCost(0);							//Heuristic cost
					map[col][row].setfCost(9999);						//Global cost
					map[col][row].setOpen(0);
					map[col][row].setClose(0);
					map[col][row].setNeutral(1);
					map[col][row].setRouteToGo(0);
				}
			}
		}
		
		//Output 4x4 Graph
		System.out.println("Ban do cung duong di va chi phi f(x) cho tung o");
		System.out.println();
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				switch(map[col][row].getType())
				{
				case "START":
					System.out.print(map[col][row].getType() + "      ");
					break;
				case "END":
					System.out.print(map[col][row].getType() + "      ");
					break;
				case "PATH":
					System.out.print(map[col][row].getgCost() + "        ");
					break;
				case "BLOCK":
					System.out.print(map[col][row].getType() +  "      ");
					break;
				}
			}
			System.out.println("");
		}
		
		AStar(map, 4, 4);
		
		establishShortestRoute(map, 4, 4);
		
		System.out.println("");
		System.out.println("Duong ngan nhat :");
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				switch(map[col][row].getType())
				{
				case "START":
					System.out.print(map[col][row].getType() + "      ");
					break;
				case "END":
					System.out.print(map[col][row].getType() + "      ");
					break;
				case "PATH":
					if(map[col][row].getRouteToGo() == 1)
						System.out.print(map[col][row].getType() + "       ");
					else if(map[col][row].getRouteToGo() == 0)
						System.out.print("___" + "        ");
					break;
				case "BLOCK":
					System.out.print(map[col][row].getType() + "      ");
					break;
				}
				
			}
			System.out.println("");
		}
	}

	public static void AStar(Cell[][] map, int mapCol, int mapRow) 
	{
		while(true)
		{
			//System.out.println("test");
			//------------------Find open Cell with lowest F cost-----------------------
			ArrayList<Cell> openList = new ArrayList<Cell>();
			
			
			int colMinFCost = 0;
			int rowMinFCost = 0;
			int minFCost = 0;
			
			//Add Cell is in Open to a list to sort
			for(int row = 0; row < mapRow; row++)
			{
				for(int col = 0; col < mapCol; col++)
				{
					if(map[col][row].getOpen() == 1)
					{
						map[col][row].setCol(col);
						map[col][row].setRow(row);
						openList.add(map[col][row]);
					}
				}
			}
			Collections.sort(openList, new Comparator<Cell>() {
			    @Override
			    public int compare(Cell o1, Cell o2) {
			    	return Integer.compare(o1.getfCost(), o2.getfCost());
			    }
			});
			
			
			if(openList.size() == 0)
			{
				return;
			}
			colMinFCost = openList.get(0).getCol();
			rowMinFCost = openList.get(0).getRow();
			
			//Remove current lowest F cost Cell from Open and add current to Close
			map[colMinFCost][rowMinFCost].setOpen(0);
			map[colMinFCost][rowMinFCost].setClose(1);
			
			//If current Cell is End, path has been found!
			//if(map[colMinFCost][rowMinFCost].getType() == "END")
			if(colMinFCost==3 && rowMinFCost == 3)
			{
				//System.out.println("Parent cua END la "+map[3][3].getParentX()+","+map[3][3].getParentY());
				break;
			}
			//-------------------Find neighbors of the current Cell
			//Find and handle North
			if(rowMinFCost > 0)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost, rowMinFCost - 1, map);
			}
			//End find and handle north
			
			//Find and handle South
			if(rowMinFCost < 3)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost, rowMinFCost + 1, map);
			}
			//End find and handle South
			
			//Find and handle East
			if(colMinFCost < 3)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost + 1, rowMinFCost, map);
			}
			//End find and handle East
			
			//Find and handle West
			if(colMinFCost > 0)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost - 1, rowMinFCost, map);
			}
			//End find and handle West
			
			//Find and handle North East
			if(colMinFCost < 3 && rowMinFCost > 0)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost + 1, rowMinFCost - 1, map);
			}
			//End find and handle North East
			
			//Find and handle South East
			if(colMinFCost < 3 && rowMinFCost < 3)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost + 1, rowMinFCost + 1, map);
			}
			//End find and handle South East
			
			//Find and handle South West
			if(colMinFCost > 0 && rowMinFCost < 3)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost - 1, rowMinFCost + 1, map);
			}
			//End find and handle South West
			
			//Find and handle North West
			if(colMinFCost > 0 && rowMinFCost > 0)
			{
				handleNeighbor(colMinFCost, rowMinFCost, colMinFCost - 1, rowMinFCost - 1, map);
			}
			//End find and handle North West
		}
	}

	public static void establishShortestRoute(Cell[][] map, int mapCol, int mapRow)
	{
		Cell currentCell = map[3][3];
		while(true)
		{
			
			int col = currentCell.getParentX();
			int row = currentCell.getParentY();
			
			if(col == 0 && row == 0)
			{
				break;
			}
			
			map[col][row].setRouteToGo(1);
			
			currentCell = map[col][row];
			
		}
	}
	
	public static void handleNeighbor(int col, int row, int neighborCol, int neighborRow, Cell[][] map)
	{
		if(map[neighborCol][neighborRow].getClose() == 0 )
		{
			if(map[neighborCol][neighborRow].getType() != "BLOCK")
			{
				//New local path from current node to neighbor node
				int newPath = map[col][row].getgCost() 
						+ Cell.calculateRealDistance(col, row, neighborCol, neighborRow);
				//If new local path < Neighbor's old local path then set neighbor new f value
				if(newPath < map[neighborCol][neighborRow].getgCost())
				{
					map[neighborCol][neighborRow].setgCost(newPath);
					map[neighborCol][neighborRow].setfCost(newPath + map[neighborCol][neighborRow].gethCost());					
					map[neighborCol][neighborRow].setParentX(col);
					map[neighborCol][neighborRow].setParentY(row);
					//If neighbor is not in OPEN, add to OPEN
					if(map[neighborCol][neighborRow].getNeutral() == 1)
					{
						map[neighborCol][neighborRow].setOpen(1);
						map[neighborCol][neighborRow].setNeutral(0);
					}
				}
			}
		}
	}
}
