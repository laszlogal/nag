package hu.norbisquest.nqcommon.client.control;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.Hero;
import hu.norbisquest.nagbase.game.SpeechEnum;

import java.util.List;

public class ExamineControl extends ItemControl {
	private SpeechEnum[] examineList;

	public ExamineControl(SpeechEnum... speeches) {
		examineList = speeches;
	}

	@Override
	public List<Command> onExamine(int x, int y) {
		return Hero.sayAll(examineList);
	}

}