package objects;

import interfaces.Attackable;
import interfaces.Movable;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.game.GameEngine;

public class Bat extends GameObject implements Movable, Attackable {
	
	private float attackPoints = 1;

	public Bat(Point2D position) {
		super(position);
	}


	@Override
	public String getName() {
		return "Bat";
	}
	
	@Override
	public int getLayer() {
		return 2;
	}

	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}

	public void setPosition(Point2D newPosition) {
		if(isWithinBounds(newPosition)) {
			super.setPosition(newPosition);
		}
	}

	public void moveRandomly() {
		if(isAboveStairs()) {
			move(Direction.DOWN);
		} else {
			Direction moveRandomly = Math.random() < 0.5 ? Direction.LEFT : Direction.RIGHT;
			move(moveRandomly);
		}
	}
	
	@Override
	public void move(Direction direction) {
		Point2D newPosition = getPosition().plus(direction.asVector());

		if(!isWithinBounds(newPosition)) {
			return;
		}

		GameObject objectAtNewPosition = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(newPosition);

		if(objectAtNewPosition instanceof Manel) {
			Attack(objectAtNewPosition);
			return;
		}

		if(direction == Direction.DOWN && objectAtNewPosition instanceof Stairs) {
			setPosition(newPosition);
			ImageGUI.getInstance().update();
			return;
		}

		if(direction == Direction.LEFT || direction == Direction.RIGHT) {
			if(objectAtNewPosition == null || objectAtNewPosition instanceof Floor) {
				setPosition(newPosition);
				ImageGUI.getInstance().update();

			}
		}
	}

	private Boolean isAboveStairs() {
		Point2D positionBelow = getPosition().plus(Direction.DOWN.asVector());
		GameObject objectBelow = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(positionBelow);
		return objectBelow instanceof Stairs;
	}

	@Override
	public void Attack(GameObject a) {
		if( a instanceof Manel) {
			Manel manel = (Manel) a;
			
			manel.setLife(attackPoints);
			
			ImageGUI.getInstance().setStatusMessage("Morcego atacou Manel! Vida restante: " + manel.getLife());
			if(manel.getLife() <= 0) {
				GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(manel);
				ImageGUI.getInstance().removeImage(manel);
				ImageGUI.getInstance().setStatusMessage("Game Over!");
			}
			ImageGUI.getInstance().update();
		}
	}


	@Override
	public float getLife() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setLife(float dmg) {
		// TODO Auto-generated method stub

	}


	@Override
	public float getAttack() {
		return this.attackPoints;
	}


	@Override
	public void setAttack(float dmg) {
		// TODO Auto-generated method stub

	}


	

}
