package objects;


import pt.iscte.poo.utils.Point2D;

public class Door extends GameObject {
	
	private String nextRoomFile;


	public Door(Point2D position, String nextRoomFile) {
		super(position);
		this.nextRoomFile = nextRoomFile;
	}

	@Override
	public String getName() {
		return nextRoomFile != null ? "DoorOpen" : "DoorClosed" ;
	}
	
	public String getNextRoomFile() {
		return nextRoomFile;
	}
}
