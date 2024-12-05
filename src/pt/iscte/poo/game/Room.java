package pt.iscte.poo.game;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	private List<GameObject> gameObjects;
	String roomfile = "";
	
	private char[][] room;
	
	
	public Room(String roomFile) {
		manel = new Manel(heroStartingPosition);
		this.roomfile = roomFile;
		this.gameObjects = new ArrayList<>();
		
		//Apagar depois
		room = new char[10][10];
	}
	
	public void loadRoom(){
        try {
        	Scanner scanner = new Scanner(new File(this.roomfile));
        	
        	if (scanner.hasNextLine()) {
        		//Ignora primeira linha
                scanner.nextLine();
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
                
                if(cell == 's' || cell == 'H' || cell == 'G'|| cell == 'b') {
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
		interactWithObjects();
	}
	
	public GameObject gameObjectPosition(Point2D point) {

		for(GameObject obj : gameObjects) {
			if(obj.getPosition().equals(point)) {
				return obj;
			}
		}
		return null;
	}
	
	public Manel getManel() {
		return this.manel;
	}
	
	public void interactWithObjects() {
		Point2D manelPosition = manel.getPosition();
		
		for(int i = 0;i < gameObjects.size(); i++) {
			GameObject obj = gameObjects.get(i);
			
			if(obj.getPosition().equals(manelPosition)) {
				if(obj instanceof Sword) {
					ImageGUI.getInstance().setStatusMessage("Espada Coletada");
					
				} else if (obj instanceof Steak) {
					ImageGUI.getInstance().setStatusMessage("Bife Coletado");
					
				}
				
				gameObjects.remove(i);
				break;
			}
		}
		ImageGUI.getInstance().update();
	}
	
}