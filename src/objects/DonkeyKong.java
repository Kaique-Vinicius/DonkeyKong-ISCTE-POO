package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong implements ImageTile {
	
	private Point2D position;
	
	public DonkeyKong(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}

}
