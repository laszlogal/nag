package hu.norbisquest.nagbase.game;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.game.target.ActorTarget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorData {

    private ActorTarget.ActorId id;
    public String name;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public int textX = 0;
    public int textY = 0;
    public String textStyleName;
    public double scale = 1.0;
    public TextResource lines = null;

	// Language: <lineCode, text>
	private Map<String, Map<Integer, String>> multiLangText;
	private Map<String, List<DataResource>> multiLangAudio;

	private ActorData(ActorTarget.ActorId id, String name, int left, int top, int right,
					  int bottom) {
		this.id = id;
		this.name = name;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;

		multiLangText = new HashMap<>();
		multiLangAudio = new HashMap<>();
	}

	public ActorData(ActorTarget.ActorId id, String name, int left, int top) {
		this(id, name, left, top, 0, 0);
	}

	public void addText(String language, Map<Integer, String> lines) {
		multiLangText.put(language, lines);

	}

	public void addAudio(String language, List<DataResource> audio) {
		multiLangAudio.put(language, audio);

	}

	public Map<Integer, String> getText(String language) {
		return multiLangText.get(language);
	}

	public List<DataResource> getAudio(String language) {
		return multiLangAudio.get(language);
	}

	public ActorTarget.ActorId getId() {
		return id;
	}
}
