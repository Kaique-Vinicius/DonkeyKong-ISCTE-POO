package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends GameObject {
	
	public DonkeyKong(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}

}
