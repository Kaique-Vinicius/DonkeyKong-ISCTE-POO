package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {
	
	private Point2D position;
	
	public GameObject(Point2D position) {
		this.position = position;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D newPosition) {
		position = newPosition;
	}
	
	@Override
	public int getLayer() {
		return 1;
	}
	
	public static GameObject criarGameObject(char character, Point2D position, String nextRoomFile) {
		switch(character) {
			case 'W': return new Wall(position); 
			case 'H': return new Manel(position);
			case 't': return new Trap(position);
			case 'S': return new Stairs(position);
			case 'G': return new DonkeyKong(position);
			case 's': return new Sword(position);
			case '0': return new Door(position, nextRoomFile);
			case 'b': return new Steak(position);
			case 'P': return new Princess(position);
			case 'B': return new Bat(position);
			case 'T': return new HiddenTrap(position);
			case ' ': return new Floor(position);
			
			default: throw new IllegalArgumentException("Caractere inválido: " + character + " na posição " + position);
		}
	}
}
