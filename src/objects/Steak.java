package objects;

import pt.iscte.poo.utils.Point2D;

public class Steak extends GameObject {

	public Steak(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "GoodMeat";
	}

	@Override
	public int getLayer() {
		return 0;
	}
}

