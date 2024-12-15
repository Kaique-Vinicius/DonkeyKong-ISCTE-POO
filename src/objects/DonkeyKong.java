package objects;

import interfaces.Attackable;
import interfaces.Movable;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class DonkeyKong extends GameObject implements Movable, Attackable {

	private float lifePoints = 5;
	private float attackPoints = 1;

	private static final int Minimun_Level_For_Advanced_Mov = 1;

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
		if(!isWithinBounds(newPosition)) {
			return;
		}

		GameObject objectAtNewPosition = GameEngine.getInstance().getCurrentRoom().gameObjectPosition(newPosition);

		if(objectAtNewPosition != null && (objectAtNewPosition instanceof Wall) || 
				objectAtNewPosition instanceof Stairs || 
				objectAtNewPosition instanceof Door ||
				objectAtNewPosition instanceof Princess) {
			return;
		}

		if(objectAtNewPosition instanceof Manel) {
			Attack(objectAtNewPosition);
			return;
		}

		setPosition(newPosition);
		Banana banana = new Banana(newPosition);

		GameEngine.getInstance().getCurrentRoom().addBananas(banana);
		ImageGUI.getInstance().addImage(banana);

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

	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}

	public void updateMovement(Point2D jumpManPosition, int currentLevel) {
		if(currentLevel < Minimun_Level_For_Advanced_Mov) {
			moveRandomly();
		}
		else {
			moveTowards(jumpManPosition);
		}
	}

	@Override
	public void Attack(GameObject a) {
		// TODO Auto-generated method stub
		if(a instanceof Manel) {

			Manel manel = (Manel) a;

			manel.setLife(this.attackPoints);

			ImageGUI.getInstance().setStatusMessage("Kong atacou Manel! Vida restante: " + manel.getLife());

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
		return this.lifePoints;
	}


	@Override
	public float getAttack() {
		return this.attackPoints;
	}

	@Override
	public void setLife(float dmg) {
		this.lifePoints -= dmg;

		if(this.lifePoints <= 0) {
			GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(this);
			ImageGUI.getInstance().removeImage(this);
			ImageGUI.getInstance().setStatusMessage("DonkeyKong foi derrotado!");
		}
	}

	@Override
	public void setAttack(float dmg) {
		this.attackPoints += dmg;
	}
}
