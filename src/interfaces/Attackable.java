package interfaces;

import objects.GameObject;

public interface Attackable {
	
	public void Attack(GameObject a);
	
	public float getLife();
	
	public void setLife(float dmg);
	
	public float getAttack();
	
	public void setAttack(float dmg);

}
