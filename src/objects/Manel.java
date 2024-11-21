package objects;

import interfaces.Movable;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

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
		
		if(isWithinBounds(getPosition().plus(direction.asVector())))
			super.setPosition(getPosition().plus(direction.asVector()));
		
		GameEngine.getInstance().getCurrentRoom();
	}
	
	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}
	
}
