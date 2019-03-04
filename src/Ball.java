import java.awt.Color;

public class Ball {
	private String colore;
	private int player;
	public Ball(int player){
		this.player = player;
		if(player == 0) {
			colore = "nero";
		}else {
			colore = "bianco";
		}
	}
	public String getColor() {
		return colore;
	}
	public int getPlayer() {
		return player;
	}
}
