package cg.group4.view.screen_mechanics;

/**
 * Used to execute assetsDone upon finished loading of the assets through the LoadingScreen.
 *
 * @see LoadingScreen
 */
public interface AssetsLoadingHandler {
	
	/**
	 * Called when the assets are finished loading.
	 */
    void assetsDone();
}
