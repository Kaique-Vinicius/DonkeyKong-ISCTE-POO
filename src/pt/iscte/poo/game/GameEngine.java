package pt.iscte.poo.game;

<<<<<<< Updated upstream
import objects.DonkeyKong;
=======
import java.io.FileNotFoundException;

import objects.Manel;
>>>>>>> Stashed changes
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	private Room currentRoom;
	private int lastTickProcessed = 0;

	private static GameEngine INSTANCE; 

	private GameEngine() {
		currentRoom = new Room("room0.txt");
		currentRoom.loadRoom();
		currentRoom.initializeRoom();
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {

		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				Direction direction = Direction.directionFor(k);
				currentRoom.moveManel(direction);
			}

			if(k == 32) {
				currentRoom.dashManel();
			}
		}
<<<<<<< Updated upstream
		
=======

		currentRoom.updateMovementsOfKong();

>>>>>>> Stashed changes
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		lastTickProcessed++;
		
		currentRoom.updateMovementsOfKong();
		
		DonkeyKong donkeyKong = (DonkeyKong) currentRoom.getGameObjectByName("DonkeyKong");
		if(donkeyKong != null) {
			donkeyKong.moveBananas();
		}
	}

	public int getProcessTick() {
		return lastTickProcessed;
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public Room getCurrentRoom() {
		return this.currentRoom;
	}

	public void changeRoom(String nextRoomFile) { //tratar excecao do file
		Room nextRoom = new Room(nextRoomFile);
		nextRoom.loadRoom();
		nextRoom.initializeRoom();

		this.currentRoom = nextRoom;
		
		ImageGUI.getInstance().clearImages();
		ImageGUI.getInstance().addImages(currentRoom.getGameObjects());
		ImageGUI.getInstance().update();
	}



}
