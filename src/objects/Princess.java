package objects;

import interfaces.Interactable;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Princess extends GameObject implements ImageTile {

	public Princess(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Princess";
	}

}
