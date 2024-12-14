package pt.iscte.poo.game;
//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Bat;
import objects.DonkeyKong;
//GameObject imports
import objects.Floor;
import objects.GameObject;
import objects.HiddenTrap;
import objects.Manel;
import objects.Steak;
//Utils imports
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

	private Point2D heroStartingPosition = new Point2D(0, 0);
	private Manel manel;

	private int roomNumber;
	private String nextRoomFile;
	private String roomfile = "";

	private List<GameObject> gameObjects;
	private List<GameObject> bananas;

	private char[][] room;


	public Room(String roomFile) {
		manel = new Manel(heroStartingPosition);

		this.roomfile = roomFile;
		this.gameObjects = new ArrayList<>();
		this.bananas = new ArrayList<>();

		room = new char[10][10];
	}

	public void loadRoom(){
		try {
			Scanner scanner = new Scanner(new File(this.roomfile));
			String firstLine = "";

			if (scanner.hasNextLine()) {
				firstLine = scanner.nextLine();

				if(firstLine.startsWith("#")) {
					try {
						String[] splitedLine = firstLine.substring(1).split(";");
						nextRoomFile = splitedLine[1];
					} catch(Exception e) {
						System.err.println(e);
					}
				} else {
					System.out.println("Sala não tem cabeçalho");
					scanner = new Scanner(new File(this.roomfile));
				}
			}

			System.out.println("Próxima sala: " + (nextRoomFile != null ? nextRoomFile : "Nenhuma"));

			int row = 0;

			while (scanner.hasNextLine() && row < room.length) {
				String line = scanner.nextLine();

				for (int col = 0; col < line.length() && col < room[row].length; col++) {
					room[row][col] = line.charAt(col) != ' ' ? line.charAt(col) : ' ';
				}
				row++;
			}

			scanner.close();
		} catch(FileNotFoundException  e) {
			System.err.println(e);
		}
	}

	public void initializeRoom() {
		if(nextRoomFile == null) {
			System.out.println("Chegou à sala final");
		}

		for (int row = 0; row < room.length; row++) {
			for (int col = 0; col < room[row].length; col++) {
				char cell = room[row][col];
				Point2D position = new Point2D(col, row);

				System.out.println("Caractere encontrado: " + cell + " na posição: " + position);

				if(cell == 's' || cell == 'H' || cell == 'G' || cell == 'b' || cell == 'P' || cell == 'B' || cell == 'T') {
					gameObjects.add(new Floor(position));
				}




				if(nextRoomFile != null) {
					GameObject gameObject = GameObject.criarGameObject(cell, position, nextRoomFile);	
					if(gameObject != null)
						System.out.println("Objeto criado: " + gameObject.getName() + " em " + position);
					gameObjects.add(gameObject);

					if(gameObject instanceof Manel) {
						manel = (Manel) gameObject;
					}				
				} else {
					System.out.println("Nenhum objeto criado para: " + cell);

				}
			}
		}

		// Adiciona os outros objetos ao GUI
		ImageGUI.getInstance().addImages(gameObjects);
		System.out.println("Objetos adicionados ao GUI: " + gameObjects.size());

	}

	public void moveManel(Direction direction) {
		manel.move(direction);
		System.out.println(manel.getPosition());

		GameObject objectAtPosition = gameObjectPosition(manel.getPosition());
		if(objectAtPosition != null) {
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

	public void updateSteaks() {
		for(GameObject obj : gameObjects) {
			if(obj instanceof Steak) {
				Steak steak = (Steak) obj;
				steak.decreaseTurns();
			}
		}
	}

	public void updateMovementsOfBats() {
		for(GameObject obj : new ArrayList<>(gameObjects)) {
			if(obj instanceof Bat) {
				Bat bat = (Bat) obj;
				bat.moveRandomly();;
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
				} else {
					return obj;
				}
			}
		}
		return floor;
	}

	public GameObject getGameObjectByName(String gameObjectName) {
		return gameObjects.stream().filter(obj -> obj.getName().equals(gameObjectName)).findFirst().orElse(null);
	}

	public Manel getManel() {
		return this.manel;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void addBananas(GameObject banana) {
		this.bananas.add(banana);
	}

	public List<GameObject> getBananas(){
		return this.bananas;
	}

	public void moveBananas() {
		List<GameObject> currentBananas = GameEngine.getInstance().getCurrentRoom().getBananas();
		List<GameObject> bananasToRemove = new ArrayList<>();

		for(GameObject b : currentBananas) {
			Point2D currentPosition = b.getPosition();
			Point2D newPosition = currentPosition.plus(Direction.DOWN.asVector());

			if(isWithinBounds(newPosition)) {
				b.setPosition(newPosition);
			}else {
				bananasToRemove.add(b);
				ImageGUI.getInstance().removeImage(b);
			}
		}

		currentBananas.removeAll(bananasToRemove);
	}

	public boolean isWithinBounds(Point2D position) {
		int x = position.getX();
		int y = position.getY();
		return x>-1 && x<10 && y>-1 && y<10;
	}
}