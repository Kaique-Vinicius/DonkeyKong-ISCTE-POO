package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

	private Room currentRoom;
	private int lastTickProcessed = 0;
	private int manelLifes = 3;
	
	private final int SPACEBAR_CODE = 32;

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
			if (Direction.isDirection(k)) {
				Direction direction = Direction.directionFor(k);
				currentRoom.moveManel(direction);
			}

			if(k == SPACEBAR_CODE) {
				currentRoom.dashManel();
			}
		}
		
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		lastTickProcessed++;
		
		currentRoom.updateMovementsOfKong();
		currentRoom.updateSteaks();
		currentRoom.updateMovementsOfBats();
		
		if(!currentRoom.getBananas().isEmpty())
			currentRoom.moveBananas();
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
	
	public void endGameWithVictory() {
		System.out.println("Parabéns!! Você salvou a princesa!");
		ImageGUI.getInstance().setName("Vitória! Você salvou a princesa!");
		try {
			Thread.sleep(100);
		}catch(Exception e) {
			System.err.println(e);
		}
		System.exit(0);
	}
	
	

	public void subtractManelLife() {
		manelLifes--;
	}
	
	public int getManelRemainingLifes() {
		return manelLifes;
	}

}
