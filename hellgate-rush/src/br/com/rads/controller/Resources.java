package br.com.rads.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Resources {

	private static Resources instance;
	private Music music = Gdx.audio.newMusic(Gdx.files.internal("DST-Desert.mp3"));
	private Sound pancakeSound = Gdx.audio.newSound(Gdx.files.internal("blop.mp3"));

	/**
	 * @return the music
	 */
	public Music getMusic() {
		if(music == null)
			music = Gdx.audio.newMusic(Gdx.files.internal("DST-Desert.mp3"));
		return music;
	}

	/**
	 * @param music the music to set
	 */
	public void setMusic(Music music) {
		this.music = music;
	}

	private Resources() {
	}

	public static Resources getInstance() {
		if (instance == null)
			instance = new Resources();
		return instance;
	}

	public void playMusic(){
		if(music != null)
			music.play();
	}
	
	public void playSound(){
		if(pancakeSound != null)
			this.pancakeSound.play(1.0f);
	}
	
	public Sound getPancakeSound() {
		return pancakeSound;
	}

	public void setPancakeSound(Sound pancakeSound) {
		this.pancakeSound = pancakeSound;
	}

	public void stopMusic() {
		if(this.music != null && this.music.isPlaying())
			music.stop();
	}

	public void stopSounds() {
		if(this.pancakeSound != null)
			this.pancakeSound.stop();
	}

	public void restart() {
		music = Gdx.audio.newMusic(Gdx.files.internal("DST-Desert.mp3"));
		pancakeSound = Gdx.audio.newSound(Gdx.files.internal("blop.mp3"));
	}
}
