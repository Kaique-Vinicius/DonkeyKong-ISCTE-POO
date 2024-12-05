package objects;

import interfaces.Movable;
import pt.iscte.poo.game.GameEngine;
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
		
		Point2D newPosition = getPosition().plus(direction.asVector());
		
		if(!isWithinBounds(newPosition))
			return;
		
		GameObject objectAtNewPosition = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(newPosition);
		
		if(objectAtNewPosition != null && objectAtNewPosition.getName().equals("Wall")) {
			return;
		}
		
		super.setPosition(newPosition);
		fall();
	}
	
	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}
	
	public void fall() {
		while(true) {
			Point2D positionBelow = getPosition().plus(Direction.DOWN.asVector());
			
			if(!isWithinBounds(positionBelow)) {
				break;
			}
			
			GameObject objectBelow = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(positionBelow);
			
			if(objectBelow != null &&
					 (objectBelow instanceof Wall || 
				      objectBelow instanceof Stairs || 
				      objectBelow instanceof Trap)) {
				break;
			}
			
			super.setPosition(positionBelow);
		}
	}
	
	public void dash(Point2D dash) {
		Point2D dashPosition = new Point2D(dash.getX() + 2, dash.getY());
		
		if(isWithinBounds(dashPosition)) {
			super.setPosition(dashPosition);
		}
	}
}
