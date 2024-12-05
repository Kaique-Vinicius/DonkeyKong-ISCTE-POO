package objects;

import interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Princess extends GameObject implements Interactable {

	public Princess(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Princess";
	}

	@Override
	public void Interact(GameObject i) {
		// TODO Auto-generated method stub
		
	}

}
