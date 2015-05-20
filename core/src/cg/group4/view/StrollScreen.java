package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class StrollScreen extends ScreenLogic {
    protected Label cTimeRemaining, cText;
    protected Table cTable;

	public StrollScreen(WorldRenderer worldRenderer) {
		super(worldRenderer);
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.row().expandY();
        cTimeRemaining = new Label("300", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.STROLL.name())
                .subscribe(new TimerTask() {
                    @Override
                    public void onTick(int seconds) {
                        cTimeRemaining.setText(Integer.toString(seconds));
                    }

                    @Override
                    public void onStart(int seconds) {

                    }

                    @Override
                    public void onStop() {

                    }
                });
        cTable.add(cTimeRemaining);

        cTable.row().expandY();
        cText = new Label("Waiting for event", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        cTable.add(cText);


        cWorldRenderer.setActor(cTable);
	}
}

///**
// * Screen that gets shown when someone is on a stroll.
// * @author Nick Cleintuar
// * @author Jean de Leeuw
// * @author Martijn Gribnau
// * @author Benjamin Los
// */
//public class StrollScreen implements Screen {
//
//	/**
//	 * Stage containing all the actors.
//	 */
//	protected Stage cStage;
//
//	/**
//	 * Sprite batch containing all the sprites.
//	 */
//	protected SpriteBatch cSpriteBatch;
//
//
//	/**
//	 * Texture for the background.
//	 */
//	protected Texture cStrollBackground;
//
//	/**
//	 * Skin used for creating the dialog.
//	 */
//	protected Skin skin;
//
//	/**
//	 * A screen for the strolls has a current stroll backend.
//	 */
//	protected Stroll stroll;
//
//	/**
//	 * Button font.
//	 */
//	protected BitmapFont cFont;
//
//	/**
//	 * Variables for keeping track of the window sizes and time.
//	 */
//	protected int cScreenWidth, cScreenHeight, cTime;
//
//    protected final TimerTask cTimerTask = new TimerTask() {
//        @Override
//        public void onTick(int seconds) {
//            cTime = seconds;
//        }
//
//        @Override
//        public void onStart(int seconds) {
//
//        }
//
//        @Override
//        public void onStop() {
//
//        }
//    };
//
//    /**
//     * Back button on the screen.
//     */
//    protected TextButton cBackButton;
//
//	public StrollScreen(){
//		super();
//
//		cStage = new Stage();
//        skin = new Skin();
//        cSpriteBatch = new SpriteBatch();
//        cStrollBackground = new Texture(Gdx.files.internal("demobackground.jpg"));
//        cFont = new BitmapFont();
//
//        Timer strollTimer = StandUp.getInstance().getTimeKeeper().getTimer("STROLL");
//        strollTimer.subscribe(cTimerTask);
//
//        cTime = strollTimer.getRemainingTime();
//
//        TextButtonStyle backButtonStyle = new TextButtonStyle();
//        backButtonStyle.font = cFont;
//        cBackButton = new TextButton("Back", backButtonStyle);
////        cBackButton.addListener(new ChangeListener() {
////            @Override
////            public void changed(final ChangeEvent event, final Actor actor) {
////                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
////            }
////        });
//
//        cStage.addActor(cBackButton);
//	}
//
//}
