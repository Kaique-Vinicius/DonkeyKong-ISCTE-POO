package objects;

import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {

	private Point2D position;
	
	public Manel(Point2D initialPosition){
		position = initialPosition;
	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 2;
	}

	public void move(Direction direction) {
		System.out.println("Movi para " + direction.name());
		System.out.println("Antes "+ position.toString());
		position = position.plus(direction.asVector());
		System.out.println("Depois "+ position.toString());
	}
	
//	public void setPosition(Point2D newPosition) {
//        position = newPosition;
//    }
	
}
