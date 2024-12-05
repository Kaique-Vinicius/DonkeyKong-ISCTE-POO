package objects;

import interfaces.Movable;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class DonkeyKong extends GameObject implements Movable {
	
	private static final int Minimun_Level_For_Advanced_Mov = 2;
	
	public DonkeyKong(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void move(Direction direction) {
		Point2D newPosition = getPosition().plus(direction.asVector());
		if(!ImageGUI.getInstance().isWithinBounds(newPosition)) {
			return;
		}
		
		GameObject objectAtNewPosition = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(newPosition);
		
		if(objectAtNewPosition != null && (objectAtNewPosition.getName().equals("Wall") || objectAtNewPosition.getName().equals("Stairs"))) {
			return;
		}
		
		setPosition(newPosition);
	}
	
	public void moveRandomly() {
		Direction moveRandomly = Direction.random();
		move(moveRandomly);
	}
	
	public void moveTowards(Point2D jumpManPosition) {
		Point2D currentPosition = getPosition();
		int differenceX = Integer.compare(jumpManPosition.getX(), currentPosition.getX());
		
		if(differenceX != 0) {
			Direction direction = Direction.forVector(new Vector2D(differenceX, 0));
			move(direction);
		}
	}
	
	public void updateMovement(Point2D jumpManPosition, int currentLevel) {
		if(currentLevel < Minimun_Level_For_Advanced_Mov) {
			moveRandomly();
		}
		else {
			moveTowards(jumpManPosition);
		}
	}

}
