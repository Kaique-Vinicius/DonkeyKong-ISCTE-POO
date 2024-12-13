package objects;

import interfaces.Attackable;
import interfaces.Interactable;
import interfaces.Movable;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable, Interactable, Attackable {
	
	private float lifePoints = 5;
	private float attackPoints = 1;
	
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
		
		if(objectAtNewPosition instanceof DonkeyKong) {
			Attack(objectAtNewPosition);
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

	public void Interact(GameObject obj) {

		if(obj instanceof Sword) {
			ImageGUI.getInstance().setStatusMessage("Espada Coletada");
			GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(obj);
			ImageGUI.getInstance().removeImage(obj);
		} else if (obj instanceof Steak){
			ImageGUI.getInstance().setStatusMessage("Bife Coletado");
			GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(obj);
			ImageGUI.getInstance().removeImage(obj);
		} else if(obj instanceof Door){
			Door door = (Door) obj;
			
			if(obj instanceof Princess) {
				ImageGUI.getInstance().setStatusMessage("Parabéns!!! Você completou o jogo");
				ImageGUI.getInstance().dispose();
				return;
			}
			GameEngine.getInstance().changeRoom(door.getNextRoomFile());
		}
		ImageGUI.getInstance().update();
	}

	@Override
	public void Attack(GameObject a) {
		if(a instanceof DonkeyKong) {
			
			DonkeyKong kong = (DonkeyKong) a;
			
			kong.setLife(this.attackPoints);
			
			ImageGUI.getInstance().setStatusMessage("Manel atacou Kong! Vida restante: " + kong.getLife());
			
			if(kong.getLife() <= 0) {
				GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(kong);
				ImageGUI.getInstance().removeImage(kong);
				ImageGUI.getInstance().setStatusMessage("DonkeyKong foi de arrasta pra cima");
			}
			
			ImageGUI.getInstance().update();
		}
	}

	@Override
	public float getLife() {
		return lifePoints;
	}

	@Override
	public float getAttack() {
		return attackPoints;
	}

	@Override
	public void setLife(float dmg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttack(float dmg) {
		// TODO Auto-generated method stub
		
	}
}
