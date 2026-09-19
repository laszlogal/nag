package hu.norbisquest.nagbase.game;

import com.google.gwt.resources.client.DataResource;
import hu.norbisquest.nagbase.core.NAGObject;

import java.util.List;
import java.util.Map;

class Speaker extends NAGObject {
	private String name;
	private Map<Integer, String> lineMap;
	private List<DataResource> audioData;
	private List<String> lines = null;

	Speaker(String name, Map<Integer, String> lines,
			List<DataResource> audioData) {
		this.name = name;
		this.setLineMap(lines);
		this.setAudioData(audioData);
	}

	public Speech speak(int idx) {

		if (getAudioData().size() < idx - 1) {
			App.warn("[" + name + "] No audio for index " + idx);
			return null;

		}

		if (lines != null) {
			String text = idx - 1 < lines.size() ? lines.get(idx - 1) : "Bug in lines at " + (idx - 1);
			if (App.isDebug()) {
				text += "(" + App.getDebugMessage() + ")";
			}
			return new Speech(text, getAudioData().get(idx - 1));

		}

		return new Speech(getLineMap().get(idx), getAudioData().get(idx - 1));
	}

	private List<DataResource> getAudioData() {
		return audioData;
	}

	public void setAudioData(List<DataResource> audioData) {
		this.audioData = audioData;
	}

	private Map<Integer, String> getLineMap() {
		return lineMap;
	}

	public void setLineMap(Map<Integer, String> lineMap) {
		this.lineMap = lineMap;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public void doDestroy() {
		lineMap = null;
		lines = null;
		audioData = null;
	}
}