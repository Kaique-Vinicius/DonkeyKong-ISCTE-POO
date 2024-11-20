package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.DonkeyKong;
import objects.Door;
import objects.Manel;
import objects.Stairs;
import objects.Sword;
import objects.Trap;
import objects.Wall;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Room {
	
	private Point2D heroStartingPosition = new Point2D(0, 0);
	private Manel manel;
	private char[][] room;
	
	public Room() {
		manel = new Manel(heroStartingPosition);
		ImageGUI.getInstance().addImage(manel);
		room = new char[10][10];
	}
	
	public void loadRoom(String file){
        try {
        	Scanner scanner = new Scanner(new File(file));
        	
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
		List<ImageTile> tiles = new ArrayList<>();

        for (int row = 0; row < room.length; row++) {
            for (int col = 0; col < room[row].length; col++) {
                char cell = room[row][col];
                Point2D position = new Point2D(col, row);

                if (cell == 'W') {
                    tiles.add(new Wall(position));
                } else if (cell == 'H') {
                    heroStartingPosition = position; // Atualiza a posição inicial do Manel
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
        if (manel == null) {
            manel = new Manel(heroStartingPosition);
            ImageGUI.getInstance().addImage(manel);
        } else {
            manel.setPosition(heroStartingPosition);
        }

        // Adiciona os outros objetos ao GUI
        ImageGUI.getInstance().addImages(tiles);
    }
	
	public void moveManel() {
		manel.move();
	}
	
}