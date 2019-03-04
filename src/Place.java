import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("place")
public class Place {


	@Param(0)
	private int row;
	@Param(1)
	private int column;
	
	public Place(int r,int c){
		this.row=r;
		this.column=c;
	}
	
	@Override
	public String toString() {
		return "Place [row=" + row + ", column=" + column + "]";
	}

	public Place() {
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
	
	

}
