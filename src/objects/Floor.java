package objects;

import pt.iscte.poo.utils.Point2D;

public class Floor extends GameObject{

	public Floor(Point2D postion) {
		super(postion);
	}

	@Override
	public String getName() {
		return "Floor";
	}

	@Override
	public int getLayer() {
		return 0;
	}

}
