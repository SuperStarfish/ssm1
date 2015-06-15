package cg.group4.util.orientation;

public class Portrait implements Orientation {

	protected final int MOVE_LEFT = 0, MOVE_RIGHT = 1, MOVE_DOWN = 2, MOVE_UP = 3;
	public static final int ORIENTATION_NUMBER = 1;

	@Override
	public int transformOperation(final int operationNr) {
		int result;
		switch(operationNr) {
			case 0:
				result = MOVE_UP;
				break;
			case 1:
				result = MOVE_DOWN;
				break;
			case 2:
				result = MOVE_LEFT;
				break;
			case 3:
				result = MOVE_RIGHT;
				break;
			default:
				result = operationNr;
				break;
		}
		return result;
	}
	
	@Override
	public int getOrientationNumber() {
		return ORIENTATION_NUMBER;
	}

	@Override
	public int getTextIndex(final int operationNr) {
		return transformOperation(operationNr);
	}
}
