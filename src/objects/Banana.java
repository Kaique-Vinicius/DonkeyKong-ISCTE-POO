package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends GameObject{

	public Banana(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Banana";
	}

	public void fall() {

		Point2D positionBelow = getPosition().plus(Direction.DOWN.asVector());

		if(!isWithinBounds(positionBelow)) {
			ImageGUI.getInstance().removeImage(this);
		}

		//GameObject objectBelow = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(positionBelow);

		super.setPosition(positionBelow);

	}

	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}

}
