import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class Cell {


	@Override
	public String toString() {
		return "Cell [row=" + row + ", column=" + column + ", player=" + player + "]";
	}

	@Param(0)
	private int row;
	@Param(1)
	private int column;
	@Param(2)
	private int player;
	
	public Cell(int r,int c,int v){
		this.row=r;
		this.column=c;
		this.player=v;
	}
	
	public Cell() {
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
	
	

}
