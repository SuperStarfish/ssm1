package cg.group4.util.orientation;

public class Portrait implements Orientation {

	protected final int cMoveLeft = 0, cMoveRight = 1, cMoveDown = 2, cMoveUp = 3;
	public static final int ORIENTATION_NUMBER = 1;

	@Override
	public int transformOperation(final int operationNr) {
		int result;
		switch(operationNr) {
			case 0:
				result = cMoveUp;
				break;
			case 1:
				result = cMoveDown;
				break;
			case 2:
				result = cMoveLeft;
				break;
			case 3:
				result = cMoveRight;
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
