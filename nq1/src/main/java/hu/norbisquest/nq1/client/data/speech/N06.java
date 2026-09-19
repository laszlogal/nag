package hu.norbisquest.nq1.client.data.speech;

import hu.norbisquest.nagbase.game.SpeechEnum;

//@formatter:off
public enum N06 implements SpeechEnum {
	NO_WALKING, // 233;
	LETS_SEE_WHEN_BUS_COMES, // 234;
	COMES_VERY_OFTEN, // 235;
	WHATS_FOR, // 236;
	MUST_BE_EMPTIED_RECENTLY, // 237;
	EMPTY_BUT_NO_RAG_AND_BONE, // 238;
	SOMEBODY_BROKE_IT, // 239;
	CANT_BE_REPAIRED, // 240;
	BALL_ON_ROOF, // 241;
	NO_CLIMB_FOR_IT, // 242;
	NO_TRASH_IT, // 243;
	HERE_IS_THE_BUS, // 244;
	OPEN_DOOR_WAITS_FOR_YOU // 245;
	;

	public int value() {
		return ordinal() + 1;
	}
}
//@formatter:on
