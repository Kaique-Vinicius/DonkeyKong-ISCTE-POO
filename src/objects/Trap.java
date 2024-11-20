package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Trap implements ImageTile {
	
	private Point2D position;
	
	public Trap(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "Trap";
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
