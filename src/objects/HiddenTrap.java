package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class HiddenTrap extends GameObject {
	
	private boolean activated = false;

	public HiddenTrap(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return activated ? "Trap" : "Wall";
	}
	
	public void activateTrap() {
		if(!activated) {
			activated = true;
			System.out.println("A armadilha foi ativada!");
			ImageGUI.getInstance().setStatusMessage("Armadilha ativada");
			ImageGUI.getInstance().update();
		}
	}
	
	public void interactWithTrap(Manel manel) {
		if(activated) {
			manel.setLife(manel.getLife() -1);
			ImageGUI.getInstance().setStatusMessage("Atingido pela Trap! Vida restante: " + manel.getLife());
		}
	}
	
	public boolean isActivated() {
		return activated;
	}

}
