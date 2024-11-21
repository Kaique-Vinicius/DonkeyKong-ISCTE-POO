package pt.iscte.poo.game;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//GameObject imports
import objects.DonkeyKong;
import objects.GameObject;
import objects.Manel;
import objects.Stairs;
import objects.Trap;
import objects.Wall;

//Utils imports
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
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
	/*
	public void loadRoom1() {
		try {
        	Scanner scanner = new Scanner(new File(this.roomfile));
        	
        	if (scanner.hasNextLine()) {
        		//Ignora primeira linha
                scanner.nextLine();
            }
        	
        	while(scanner.hasNextLine()) {
        		char[] line = scanner.nextLine().toCharArray();
        		for(char object : line) {
        			if (object == 'W') {
        				gameObjects.add(new Wall(position));
                    } else if (object == 'H') {
                    	System.out.println("Novo Hero");
                    	 manel = new Manel(position);
                    	 System.out.println(manel.getPosition().toString());
                    	 gameObjects.add(manel);// Atualiza a posição inicial do Manel
                    } else if (object == 't') {
                    	gameObjects.add(new Trap(position));
                    } else if (object == 'S') {
                    	gameObjects.add(new Stairs(position));
                    } else if (object == 'G') {
                    	gameObjects.add(new DonkeyKong(position));
                    }
        		}
        	}
        	
		}
		catch(FileNotFoundException e) {
			System.err.println("File not found");
		}
		
	}
	*/
	public void initializeRoom() {
		List<ImageTile> tiles = new ArrayList<>();

        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[row].length; col++) {
                char cell = room[row][col];
                Point2D position = new Point2D(col, row);

                if (cell == 'W') {
                    tiles.add(new Wall(position));
                } else if (cell == 'H') {
                	System.out.println("Novo Hero");
                	 manel = new Manel(position);
                	 System.out.println(manel.getPosition().toString());
                    tiles.add(manel);// Atualiza a posição inicial do Manel
                } else if (cell == 't') {
                    tiles.add(new Trap(position));
                } else if (cell == 'S') {
                    tiles.add(new Stairs(position));
                } else if (cell == 'G') {
                    tiles.add(new DonkeyKong(position));
                }
            }
        }
        
        // Cria ou atualiza o Manel com a posição inicial

        // Adiciona os outros objetos ao GUI
        ImageGUI.getInstance().addImages(tiles);
    }
	
	public void moveManel(Direction direction) {
		manel.move(direction);
	}
	
}