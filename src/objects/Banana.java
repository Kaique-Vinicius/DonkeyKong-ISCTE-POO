package objects;

import pt.iscte.poo.utils.Point2D;

public class Banana extends GameObject{

	public Banana(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Banana";
	}
	
	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}

}
