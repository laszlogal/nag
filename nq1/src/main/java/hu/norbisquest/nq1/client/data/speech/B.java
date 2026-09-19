package hu.norbisquest.nq1.client.data.speech;

import hu.norbisquest.nagbase.game.SpeechEnum;

//@formatter:off
public enum B implements SpeechEnum {
	TICKET_OR_PASS, // 1
	GET_OFF_THEN, // 2;
	GO_ON_IM_BUSY, // 3;
	DONT_FOOL_ON_ME, // 4;
	ACCEPT_OR_GET_OFF, // 5;
;

	public int value() {
		return ordinal() + 1;
	}
}
//@formatter:on
