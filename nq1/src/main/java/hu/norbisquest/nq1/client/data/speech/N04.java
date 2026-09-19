package hu.norbisquest.nq1.client.data.speech;

import hu.norbisquest.nagbase.game.SpeechEnum;

//@formatter:off
public enum N04 implements SpeechEnum {
	HOUSE_ENTRY, //192;
	DORBELLS, //193;
	I_HAVE_KEY, // 194;
	CLOSED_AGAIN, //195;
	GOING_IS_A_ROBBERY, //196;
	NEW_KALYBER, //197;
	ILL_BY_FROM_ROLI, //198;
	IF_I_NEED_ASK_FOR, //199;
	NOT_THIS_KEY, //200;
	WRONG_KEY, //201;
;

	public int value() {
		return ordinal() + 1;
	}
}
//@formatter:on
