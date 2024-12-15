package objects;

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


	public void applyTickDamage(Manel manel) {
		manel.setLife(TRAP_DAMAGE);
		System.out.println("Armadilha ativada! Sai dai!");
	}
}
