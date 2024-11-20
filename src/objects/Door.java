package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Door implements ImageTile {
	
	private Point2D position;

	public Door(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "DoorWay";
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
