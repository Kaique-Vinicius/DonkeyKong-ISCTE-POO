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
		
		if(objectAtNewPosition != null) {
			this.Interact(objectAtNewPosition);
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
							objectBelow instanceof Trap ||
							objectBelow instanceof HiddenTrap)) {
				break;
			}

			super.setPosition(positionBelow);
		}
	}

	public void dash() {
		Point2D dashPosition = new Point2D(this.getPosition().getX() + 2, this.getPosition().getY());

		if(isWithinBounds(dashPosition)) {
			super.setPosition(dashPosition);
		}
	}

	public void Interact(GameObject obj) {

		if(obj instanceof Sword) {
			Sword sword = (Sword) obj;
			ImageGUI.getInstance().setStatusMessage("Espada Coletada, dano passa a ser:" + (attackPoints+sword.getDamageStatus()));
			setAttack(sword.getDamageStatus());
			GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(obj);
			ImageGUI.getInstance().removeImage(obj);
		} else if (obj instanceof Steak){
			Steak steak = (Steak) obj;
			steak.applyEffect(this);
			GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(obj);
			ImageGUI.getInstance().removeImage(obj);
		} else if(obj instanceof Door){
			Door door = (Door) obj;
			GameEngine.getInstance().changeRoom(door.getNextRoomFile());

		}else if(obj instanceof Princess) {
			ImageGUI.getInstance().setStatusMessage("Parabéns!!! Você completou o jogo");
			ImageGUI.getInstance().dispose();
			try {
				Thread.sleep(100);
			}catch(Exception e) {
				System.err.println(e);
			}

			System.exit(0);
		} else if(obj instanceof HiddenTrap) {
			HiddenTrap trap = (HiddenTrap) obj;
			trap.activateTrap();
			trap.interactWithTrap(this);
			GameEngine.getInstance().getCurrentRoom().getGameObjects().remove(trap);
			ImageGUI.getInstance().removeImage(trap);

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
		GameEngine gameEngine = GameEngine.getInstance();
		ImageGUI imageGUI = ImageGUI.getInstance();
		
		if(gameEngine.getManelRemainingLifes() != 0) {
			lifePoints -= dmg;
			
			if (lifePoints <= 0) {
				lifePoints = 0;
				gameEngine.subtractManelLife();
				
				if(gameEngine.getManelRemainingLifes() > 0) {
					//gameEngine.getCurrentRoom().getManel().setPosition(gameEngine.getCurrentRoom().getHeroStartingPosition());
					imageGUI.setStatusMessage("Vidas Restantes: " + gameEngine.getManelRemainingLifes());
					replenishLife();
				}else {
					imageGUI.setStatusMessage("GameOver! Reiniciando Jogo");
					System.exit(0);
				}
			} else {
				System.out.println("Manel sofreu dano! Vida restante: " + lifePoints);
			}
			
			imageGUI.setStatusMessage("Vida do Manel: " + lifePoints);
			
		}else {
			imageGUI.setStatusMessage("GameOver!");
		}

	}
	
	public void replenishLife() {
		this.lifePoints = 5;
	}
	
	public void replenishLifeSteak(int lifePoint) {
		this.lifePoints+=lifePoints;
	}

	@Override
	public void setAttack(float dmg) {
		attackPoints+= dmg;
	}
}
