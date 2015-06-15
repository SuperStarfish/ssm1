package cg.group4.util.orientation;

public class Landscape implements Orientation {
	
	protected final int MOVE_LEFT = 2, MOVE_RIGHT = 3, MOVE_DOWN = 1, MOVE_UP = 0;
	protected static final int ORIENTATION_NUMBER = 2;

	@Override
	public int transformOperation(final int operationNr) {
		int result;
		switch(operationNr) {
			case 0:
				result = MOVE_LEFT;
				break;
			case 1:
				result = MOVE_RIGHT;
				break;
			case 2:
				result = MOVE_DOWN;
				break;
			case 3:
				result = MOVE_UP;
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
		return operationNr;
	}
}
