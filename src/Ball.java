import java.awt.Color;

public class Ball {
	private String colore;
	public Ball(int player){
		if(player == 0) {
			colore = "nero";
		}else {
			colore = "bianco";
		}
	}
	public String getColor() {
		return colore;
	}
}
