package hu.norbisquest.nq1.client.data.speech;

import hu.norbisquest.nagbase.game.SpeechEnum;

//@formatter:off
public enum N07 implements SpeechEnum {
	NOPE, //  20;
	TICKET, //  21;
	JOKING, //  22;
	DONT_WORRY, //  23;
	SLOW_DOWN_MAN, //  24;
	TRIED //  303;
	;

	public int value() {
		return ordinal() + 1;
	}
}
//@formatter:on
