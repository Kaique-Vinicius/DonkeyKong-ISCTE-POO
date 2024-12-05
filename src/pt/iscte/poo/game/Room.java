package pt.iscte.poo.game;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.DonkeyKong;
//GameObject imports
import objects.Floor;
import objects.GameObject;
import objects.Manel;
import objects.Steak;
import objects.Sword;
//Utils imports
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

	private Point2D heroStartingPosition = new Point2D(0, 0);
	private Manel manel;
	private int roomNumber;

	private List<GameObject> gameObjects;
	String roomfile = "";

	private char[][] room;


	public Room(String roomFile) {
		manel = new Manel(heroStartingPosition);
		this.roomfile = roomFile;
		this.gameObjects = new ArrayList<>();

		room = new char[10][10];
	}

	public void loadRoom(){
		try {
			Scanner scanner = new Scanner(new File(this.roomfile));

			if (scanner.hasNextLine()) {
				String firstLine = scanner.nextLine();

				if(firstLine.charAt(0) != '#') {
					this.roomNumber = 3;
				}

				this.roomNumber = Integer.parseInt(Character.toString(firstLine.charAt(1)));
			}

			int row = 0;

			while (scanner.hasNextLine() && row < room.length) {
				String line = scanner.nextLine();

				int k = 0;

				for (int col = 0; col < line.length() && col < room[row].length; col++) {
					if(line.charAt(k) != ' ')
						room[row][col] = line.charAt(k);
					else
						room[row][col] = ' ';

					k++;
				}

				row++;
			}

			scanner.close();
		}
		catch(FileNotFoundException  e) {
			System.err.println(e);
		}
	}



	public void initializeRoom() {
		for (int row = 0; row < room.length; row++) {
			for (int col = 0; col < room[row].length; col++) {
				char cell = room[row][col];
				Point2D position = new Point2D(col, row);

				if(cell == 's' || cell == 'H' || cell == 'G' || cell == 'b' || cell == 'P') {

					Floor floor = new Floor(position);
					gameObjects.add(floor);
				}

				GameObject gameObject = GameObject.criarGameObject(cell, position);
				gameObjects.add(gameObject);

				if(gameObject instanceof Manel) {
					manel = (Manel) gameObject;
				}
			}
		}

		// Adiciona os outros objetos ao GUI
		ImageGUI.getInstance().addImages(gameObjects);
	}

	public void moveManel(Direction direction) {
		manel.move(direction);
		System.out.println(manel.getPosition());
		System.out.println(gameObjectPosition(manel.getPosition()));

		GameObject objectAtPosition = gameObjectPosition(manel.getPosition());
		if(objectAtPosition instanceof Sword) {
			manel.Interact(objectAtPosition);
		}
		if(objectAtPosition instanceof Steak) {
			manel.Interact(objectAtPosition);
		}

	}

	public void dashManel() {
		manel.dash(manel.getPosition());
	}

	public void updateMovementsOfKong() {
		for(GameObject obj : gameObjects) {
			if(obj instanceof DonkeyKong) {
				DonkeyKong donkeykong = (DonkeyKong) obj;
				donkeykong.updateMovement(heroStartingPosition, roomNumber);
			}
		}
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public GameObject gameObjectPosition(Point2D point) {

		GameObject floor = null;

		for(GameObject obj : gameObjects) {
			if(obj.getPosition().equals(point)) {


				if(obj instanceof Floor) {
					floor = obj;
				}
				else {
					return obj;
				}

			}
		}
		return floor;
	}

	public Manel getManel() {
		return this.manel;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}


}