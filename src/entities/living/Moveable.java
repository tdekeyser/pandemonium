package entities.living;

// Interface for any entity that can move, not public so only accessible within the package entities
interface Moveable {	
	
	public void moveLeft();
	public void moveRight();
	public void moveUp();
	public void moveDown();
	
}
