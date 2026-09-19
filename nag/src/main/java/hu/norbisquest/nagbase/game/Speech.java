package hu.norbisquest.nagbase.game;

import com.google.gwt.resources.client.DataResource;

public class Speech {
	private String text;
	private DataResource audio;

	public Speech(String text, DataResource audio) {
		this.text = text;
		this.audio = audio;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public DataResource getAudio() {
		return audio;
	}
	public void setAudio(DataResource audio) {
		this.audio = audio;
	}
}
