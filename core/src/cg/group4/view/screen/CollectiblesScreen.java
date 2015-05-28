package cg.group4.view.screen;

import java.awt.Color;

import cg.group4.rewards.CollectibleDrawer;
import cg.group4.rewards.collectibles.FishA;
import cg.group4.rewards.collectibles.FishB;
import cg.group4.rewards.collectibles.FishC;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CollectiblesScreen implements ApplicationListener{
	
	private SpriteBatch batch;
	
	private Texture fa, fb, fc;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		FishA fishA = new FishA(500);
		FishB fishB = new FishB(780);
		FishC fishC = new FishC(410);
		
		CollectibleDrawer cd = new CollectibleDrawer();
		
		fa = cd.drawCollectible(fishA);
		fb = cd.drawCollectible(fishB);
		fc = cd.drawCollectible(fishC);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub		
		batch.begin();
		batch.draw(fa, 0, 0);
		batch.draw(fb, 0, 254);
		batch.draw(fc, 254, 0);
		batch.end();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	

}
