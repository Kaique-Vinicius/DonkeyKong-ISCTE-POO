package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject {
	
	private final float TRAP_DAMAGE = 1;
	
	public Trap(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Trap";
	}


	public void Attack(GameObject obj) {
		if(obj instanceof Manel) {
			GameEngine.getInstance().getCurrentRoom().getManel().setLife(TRAP_DAMAGE);
		}
		
	}

}
