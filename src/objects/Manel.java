package objects;

import interfaces.Movable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable {
	
	public Manel(Point2D initialPosition){
		super(initialPosition);
	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 2;
	}

	public void move(Direction direction) {
		super.setPosition(getPosition().plus(direction.asVector()));
	}
	
}
