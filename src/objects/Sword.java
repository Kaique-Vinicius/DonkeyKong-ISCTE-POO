package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Sword implements ImageTile {

	private Point2D position;
	
	public Sword(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Sword";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}


}
