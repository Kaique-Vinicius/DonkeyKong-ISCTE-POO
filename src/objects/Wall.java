package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import java.io.File;
import java.io.FileNotFoundException;

public class Wall implements ImageTile {
	
	private Point2D position;
	
	public Wall(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Wall";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {
		return this.position;
	}
	
}
