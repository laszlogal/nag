package hu.norbisquest.nq1.client.data.speech;

import hu.norbisquest.nagbase.game.SpeechEnum;

//@formatter:off
public enum U implements SpeechEnum {
	GREETINGS, //  1;
	PASS_PRICE, //  2;
	YES_EXPENSIVE, //  3;
	TICKET_PRICE, //  4;
	LOOK_THERE, //  5;
	WELCOME, //  6;
	HOBO_1, //  7;
	HOBO_2, //  8;
	HOBO_3, //  9;
	HOBO_LEAVE_HIM, //  10;
	DUNNO_PRIZES, //  11;
	WHAT_KIND_OF_PAPER, //  12;
	ASK_DM_EXCLUSIVE, //  13;
	ITS_300_FT, //  14;
	ASK_ANOTHER_ONE, //  15;
	INDEED_YOU_CAN_300FT, //  16;
	COME_BACK_IF_HAVE_ENOUGH, //  17;
	BYE, //  18;
	NO_TICKET_SORRY, //  19;
	OK_YOU_KNOW, //  20;
	NO_MONEY_NO_PASS, //  21;
	THANKS, // 22
	YOU_CANNOT_BUY_THEN, //  23;
	NONE, //  24;
	NEVERMIND, // 25
	SHAME_TOO_YOUNG, //  26;
	RIGHT_THEN, //  27;
	SHAME_ON_YOU, //  28;
	TAKE_AWAY_THEN, //  29;
	NO_FOOD_TNX, //  30;
	SMELLS_BAD, //  31;
	HERE_IS_YOUR_NEWSPAPER, //  32;
	HERE_IS_YOUR_TICKET, //  33;
	;

	public int value() {
		return ordinal() + 1;
	}
}
//@formatter:on
