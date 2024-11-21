package interfaces;

import objects.GameObject;

public interface Attackable {
	
	public void Attack(GameObject a);
	
	public int getLife();
	
	public void setLife();

}
