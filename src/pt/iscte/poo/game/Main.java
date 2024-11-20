package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;

public class Main {

	public static void main(String[] args){
		ImageGUI gui = ImageGUI.getInstance();
		GameEngine engine = new GameEngine();
		
		Room room = new Room();
		room.loadRoom("room0.txt");
		room.initializeRoom();
		
		gui.setStatusMessage("Good luck!");
		gui.registerObserver(engine);
		gui.go();
	}
	
}
