package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Steak extends GameObject {
	
	private final int steakStatus = 1;
	
	private static final int TURNS_TO_ROTTEN = 15;
	private int turnsRemaining;
	
	private boolean isRotten = false;

	public Steak(Point2D position) {
		super(position);
		this.turnsRemaining = TURNS_TO_ROTTEN;
	}

	@Override
	public String getName() {
		return isRotten ? "BadMeat" : "GoodMeat";
	}

	@Override
	public int getLayer() {
		return 0;
	}
	
	public Boolean isRotten() {
		return isRotten;
	}
	
	public void decreaseTurns() {
		if(!isRotten) {
			turnsRemaining--;
			if(turnsRemaining <= 0) {
				isRotten = true;
				System.out.println("O bife apodreceu");
				ImageGUI.getInstance().setStatusMessage("O bife apodreceu");
			}
		}
	}
	
	public void applyEffect(Manel manel) {
		if(isRotten) {
			manel.setLife(steakStatus);
			ImageGUI.getInstance().setStatusMessage("Comeu um bife apodrecido! Perdeu Vida: " + manel.getLife());
		} else {
			manel.replenishLifeSteak(steakStatus);
			ImageGUI.getInstance().setStatusMessage("Comeu um bife! Ganhou Vida: " + manel.getLife());
		}
	}
}

