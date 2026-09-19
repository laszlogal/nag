package hu.norbisquest.nq1.client.data.speech;

import hu.norbisquest.nagbase.game.SpeechEnum;

//@formatter:off
public enum INV implements SpeechEnum {
	MY_SLEEVE, // 288;
	NO_CRASH_BUT_KEY, // 289;
	WONDER_WHERE_IT_IS, // 290;
	THERE_WAS_MY_MONEY, // 291;
	OPEN_SESAMI, // 292;
	ONLY_1900HUF, // 293;
	HUNDRED_NEEDED, // 294;
	ALREADY_OPEN, // 295;
	WORKS_REVERSE, // 296;
	LET_ME_SEE, // 297;
	MY_SLEEVE_KEY, // 389;
	DONT_FORGET_TO_TAKE_HOME, // 390;
	HOPE_NOBODYS_ON_TOILET_HOME, // 391;
	TOO_MUCH_FOR_THIS, // 392;
	USELESS, // 393;
	LOOKS_NEW, // 394
	DUNNO_WHY_TRASHED, // 395
	DM_EXCLUSIVE, // 396;
	THERE_ARTICLE, // 397;
	NONAME_BEER, // 398;
	CIGAR_FROM_ABROAD, // 399;
	STRONG_SNIPS, // 400;
	FUSE_AT_LAST, // 401;
	DONT_FORGET_TO_REPLACE // 403;
;	
	public int value() {
		return ordinal() + 1;
		}
	}
//@formatter:on
