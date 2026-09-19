package hu.norbisquest.nq1.client.game.actors;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface NorbiFrames extends ClientBundle {
	class Data {
		public static List<ImageResource> fronts = Arrays.asList(
				INSTANCE.front1(),
				INSTANCE.front2(), INSTANCE.front3(),
				INSTANCE.front4(), INSTANCE.front5(), INSTANCE.front6(),
				INSTANCE.front7());

		public static List<ImageResource> backs = Arrays.asList(
				INSTANCE.back1(),
				INSTANCE.back2(), INSTANCE.back3(),
				INSTANCE.back4(), INSTANCE.back5(), INSTANCE.back6(),
				INSTANCE.back7());

		public static List<ImageResource> rights = Arrays.asList(
				INSTANCE.right1(),
				INSTANCE.right2(), INSTANCE.right3(),
				INSTANCE.right4(), INSTANCE.right5(), INSTANCE.right6(),
				INSTANCE.right7());

		public static List<ImageResource> lefts = Arrays.asList(
				INSTANCE.left1(),
				INSTANCE.left2(), INSTANCE.left3(),
				INSTANCE.left4(), INSTANCE.left5(), INSTANCE.left6(),
				INSTANCE.left7());

		public static List<ImageResource> talk_fronts = Arrays.asList(
				INSTANCE.talk_front1(), INSTANCE.front1(),
				INSTANCE.talk_front2(), INSTANCE.front1());

		public static List<ImageResource> talk_backs = Arrays.asList(
				INSTANCE.back1(), INSTANCE.back1());

		public static List<ImageResource> talk_lefts = Arrays.asList(
				INSTANCE.talk_left1(), INSTANCE.left1(),
				INSTANCE.talk_left2(), INSTANCE.left1());

		public static List<ImageResource> squat_talk_lefts = Arrays.asList(
				INSTANCE.squat_talk_left(), INSTANCE.squat_left());

		public static List<ImageResource> talk_rights = Arrays.asList(
				INSTANCE.talk_right1(), INSTANCE.right1(),
				INSTANCE.talk_right2(), INSTANCE.right1());

		public static List<ImageResource> grab_fronts = Arrays.asList(
				INSTANCE.grab_front(), INSTANCE.front1());

		public static List<ImageResource> grab_lefts = Arrays.asList(
				INSTANCE.grab_left(), INSTANCE.left1());

		public static List<ImageResource> grab_rights = Arrays.asList(
				INSTANCE.grab_right(), INSTANCE.grab_right());

		public static List<ImageResource> cut_chain = Arrays.asList(
				INSTANCE.cut_chain1(), INSTANCE.cut_chain2(),
				INSTANCE.cut_chain1(), INSTANCE.cut_chain2(),
				INSTANCE.cut_chain1(), INSTANCE.cut_chain2());

		public static List<ImageResource> pull_knife = Arrays.asList(
				INSTANCE.pull_knife1(), INSTANCE.pull_knife2(),
				INSTANCE.pull_knife3()

		);

		public static List<ImageResource> search_coat = Arrays.asList(
				INSTANCE.search_coat1(), INSTANCE.search_coat2(),
				INSTANCE.search_coat3()

		);

		public static List<ImageResource> squat_grab_back = Arrays.asList(
				INSTANCE.squat_grab_back1(), INSTANCE.squat_grab_back2());

		public static List<ImageResource> play_bass = Arrays.asList(
				INSTANCE.bass2(), INSTANCE.bass8(), INSTANCE.bass4(),
				INSTANCE.bass6(), INSTANCE.bass2(), INSTANCE.bass8(),
				INSTANCE.bass2(), INSTANCE.bass6(), INSTANCE.bass2(),
				INSTANCE.bass8(), INSTANCE.bass4(), INSTANCE.bass6(),
				INSTANCE.bass2(), INSTANCE.bass6(), INSTANCE.bass2(),
				INSTANCE.bass4(), INSTANCE.bass6(), INSTANCE.bass2(),
				INSTANCE.bass9(), INSTANCE.bass10(), INSTANCE.bass11(),
				INSTANCE.bass5(), INSTANCE.bass3(), INSTANCE.bass9(),
				INSTANCE.bass10(), INSTANCE.bass11(), INSTANCE.bass12(),
				INSTANCE.bass13(), INSTANCE.bass14(), INSTANCE.bass13(),
				INSTANCE.bass14(), INSTANCE.bass13(), INSTANCE.bass14(),
				INSTANCE.bass17(), INSTANCE.bass18(), INSTANCE.bass19(),
				INSTANCE.bass20(), INSTANCE.bass6(), INSTANCE.bass2(),
				INSTANCE.bass17(), INSTANCE.bass20(), INSTANCE.bass19(),
				INSTANCE.bass18(), INSTANCE.bass3(), INSTANCE.bass5(),
				INSTANCE.bass21(), INSTANCE.bass22(), INSTANCE.bass23(),
				INSTANCE.bass24(), INSTANCE.bass25(), INSTANCE.bass26(),
				INSTANCE.bass27(), INSTANCE.bass28(), INSTANCE.bass21(),
				INSTANCE.bass22(), INSTANCE.bass23(), INSTANCE.bass24(),
				INSTANCE.bass25(), INSTANCE.bass26(), INSTANCE.bass27(),
				INSTANCE.bass28(), INSTANCE.bass21(), INSTANCE.bass22(),
				INSTANCE.bass23(), INSTANCE.bass24(), INSTANCE.bass25(),
				INSTANCE.bass26(), INSTANCE.bass27(), INSTANCE.bass28(),
				INSTANCE.bass29(), INSTANCE.bass30(), INSTANCE.bass31(),
				INSTANCE.bass32(), INSTANCE.bass33(), INSTANCE.bass34(),
				INSTANCE.bass35(), INSTANCE.bass36(), INSTANCE.bass29(),
				INSTANCE.bass30(), INSTANCE.bass31(), INSTANCE.bass32(),
				INSTANCE.bass33(), INSTANCE.bass34(), INSTANCE.bass35(),
				INSTANCE.bass36(), INSTANCE.bass29(), INSTANCE.bass30(),
				INSTANCE.bass31(), INSTANCE.bass32(), INSTANCE.bass33(),
				INSTANCE.bass34(), INSTANCE.bass35(), INSTANCE.bass36(),
				INSTANCE.bass33(), INSTANCE.bass34(), INSTANCE.bass35(),
				INSTANCE.bass34(), INSTANCE.bass35(), INSTANCE.bass36(),
				INSTANCE.bass33(), INSTANCE.bass34(), INSTANCE.bass35(),
				INSTANCE.bass34(), INSTANCE.bass35(), INSTANCE.bass36(),
				INSTANCE.bass33(), INSTANCE.bass34(), INSTANCE.bass35(),
				INSTANCE.bass34(), INSTANCE.bass35(), INSTANCE.bass36(),
				INSTANCE.bass33(), INSTANCE.bass34(), INSTANCE.bass35(),
				INSTANCE.bass36());
		public static List<ImageResource> burn = Arrays.asList(
				INSTANCE.burn1(), INSTANCE.burn2(), INSTANCE.burn3(),
				INSTANCE.burn4(), INSTANCE.burn5(), INSTANCE.burn6(),
				INSTANCE.burn7(), INSTANCE.burn8());
	}

	NorbiFrames INSTANCE = GWT.create(NorbiFrames.class);

	@Source("hu/norbisquest/nq1/resources/images/norbi/1.png")
	ImageResource front1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/2.png")
	ImageResource front2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/3.png")
	ImageResource front3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/4.png")
	ImageResource front4();

	@Source("hu/norbisquest/nq1/resources/images/norbi/5.png")
	ImageResource front5();

	@Source("hu/norbisquest/nq1/resources/images/norbi/6.png")
	ImageResource front6();

	@Source("hu/norbisquest/nq1/resources/images/norbi/7.png")
	ImageResource front7();

	@Source("hu/norbisquest/nq1/resources/images/norbi/8.png")
	ImageResource back1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/9.png")
	ImageResource back2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/10.png")
	ImageResource back3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/11.png")
	ImageResource back4();

	@Source("hu/norbisquest/nq1/resources/images/norbi/12.png")
	ImageResource back5();

	@Source("hu/norbisquest/nq1/resources/images/norbi/13.png")
	ImageResource back6();

	@Source("hu/norbisquest/nq1/resources/images/norbi/14.png")
	ImageResource back7();

	@Source("hu/norbisquest/nq1/resources/images/norbi/15.png")
	ImageResource right1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/16.png")
	ImageResource right2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/17.png")
	ImageResource right3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/18.png")
	ImageResource right4();

	@Source("hu/norbisquest/nq1/resources/images/norbi/19.png")
	ImageResource right5();

	@Source("hu/norbisquest/nq1/resources/images/norbi/20.png")
	ImageResource right6();

	@Source("hu/norbisquest/nq1/resources/images/norbi/21.png")
	ImageResource right7();

	@Source("hu/norbisquest/nq1/resources/images/norbi/22.png")
	ImageResource left1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/23.png")
	ImageResource left2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/24.png")
	ImageResource left3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/25.png")
	ImageResource left4();

	@Source("hu/norbisquest/nq1/resources/images/norbi/26.png")
	ImageResource left5();

	@Source("hu/norbisquest/nq1/resources/images/norbi/27.png")
	ImageResource left6();

	@Source("hu/norbisquest/nq1/resources/images/norbi/28.png")
	ImageResource left7();

	@Source("hu/norbisquest/nq1/resources/images/norbi/talk/talkbal1b.png")
	ImageResource talk_left1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/talk/talkbal2b.png")
	ImageResource talk_left2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/talk/talkjobbb.png")
	ImageResource talk_right1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/talk/talkjobb2b.png")
	ImageResource talk_right2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/talk/talkelol1b.png")
	ImageResource talk_front1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/talk/talkelol2b.png")
	ImageResource talk_front2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/ulagyon.png")
	ImageResource sit_on_bed();

	@Source("hu/norbisquest/nq1/resources/images/norbi/nyulelol.png")
	ImageResource grab_front();

	@Source("hu/norbisquest/nq1/resources/images/norbi/balranyulel.png")
	ImageResource grab_left();

	@Source("hu/norbisquest/nq1/resources/images/norbi/nyuljobbra.png")
	ImageResource grab_right();

	@Source("hu/norbisquest/nq1/resources/images/norbi/gugol/gugolszemben.png")
	ImageResource squat_front();

	@Source("hu/norbisquest/nq1/resources/images/norbi/gugol/gugolbal.png")
	ImageResource squat_left();

	@Source("hu/norbisquest/nq1/resources/images/norbi/gugol/gugolbaltalk2.png")
	ImageResource squat_talk_left();

	@Source("hu/norbisquest/nq1/resources/images/norbi/gugol/gugolnyuljobb.png")
	ImageResource squat_grab_right();

	@Source("hu/norbisquest/nq1/resources/images/norbi/gugol/gugolhat02.png")
	ImageResource squat_grab_back1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/gugol/gugolhat03b.png")
	ImageResource squat_grab_back2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/piatnyujt.png")
	ImageResource give_alcohol();

	@Source("hu/norbisquest/nq1/resources/images/norbi/ujsagotnyujt.png")
	ImageResource give_newspaper();

	@Source("hu/norbisquest/nq1/resources/images/norbi/szendvicsetnyujt.png")
	ImageResource give_sandwitch();

	@Source("hu/norbisquest/nq1/resources/images/norbi/cigitnyujt.png")
	ImageResource give_cigarette();

	@Source("hu/norbisquest/nq1/resources/images/norbi/trolinelol.png")
	ImageResource sit_bus_front();

	@Source("hu/norbisquest/nq1/resources/images/norbi/trolinhatul.png")
	ImageResource sit_bus_back();

	@Source("hu/norbisquest/nq1/resources/images/norbi/lyukaszt.png")
	ImageResource use_ticket_on_bus();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kesthuz1.png")
	ImageResource pull_knife1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kesthuz2.png")
	ImageResource pull_knife2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kesthuz3.png")
	ImageResource pull_knife3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kutat1.png")
	ImageResource search_coat1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kutat2.png")
	ImageResource search_coat2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kutat3.png")
	ImageResource search_coat3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/kilincsbal.png")
	ImageResource knob_left();

	@Source("hu/norbisquest/nq1/resources/images/norbi/cutchain01.png")
	ImageResource cut_chain1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/cutchain02.png")
	ImageResource cut_chain2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/nyulle.png")
	ImageResource grab_down();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass1.png")
	ImageResource bass1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass2.png")
	ImageResource bass2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass3.png")
	ImageResource bass3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass4.png")
	ImageResource bass4();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass5.png")
	ImageResource bass5();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass6.png")
	ImageResource bass6();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass7.png")
	ImageResource bass7();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass8.png")
	ImageResource bass8();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass9.png")
	ImageResource bass9();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass10.png")
	ImageResource bass10();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass11.png")
	ImageResource bass11();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass12.png")
	ImageResource bass12();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass13.png")
	ImageResource bass13();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass14.png")
	ImageResource bass14();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass15.png")
	ImageResource bass15();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass16.png")
	ImageResource bass16();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass17.png")
	ImageResource bass17();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass18.png")
	ImageResource bass18();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass19.png")
	ImageResource bass19();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass20.png")
	ImageResource bass20();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass21.png")
	ImageResource bass21();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass22.png")
	ImageResource bass22();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass23.png")
	ImageResource bass23();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass24.png")
	ImageResource bass24();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass25.png")
	ImageResource bass25();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass26.png")
	ImageResource bass26();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass27.png")
	ImageResource bass27();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass28.png")
	ImageResource bass28();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass29.png")
	ImageResource bass29();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass30.png")
	ImageResource bass30();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass31.png")
	ImageResource bass31();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass32.png")
	ImageResource bass32();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass33.png")
	ImageResource bass33();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass34.png")
	ImageResource bass34();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass35.png")
	ImageResource bass35();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass36.png")
	ImageResource bass36();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass37.png")
	ImageResource bass37();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass38.png")
	ImageResource bass38();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass39.png")
	ImageResource bass39();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass40.png")
	ImageResource bass40();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass41.png")
	ImageResource bass41();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass42.png")
	ImageResource bass42();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass43.png")
	ImageResource bass43();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass44.png")
	ImageResource bass44();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass45.png")
	ImageResource bass45();

	@Source("hu/norbisquest/nq1/resources/images/norbi/bass/bass46.png")
	ImageResource bass46();

	@Source("hu/norbisquest/nq1/resources/images/norbi/norbiboom.png")
	ImageResource boom();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burned.png")
	ImageResource burned();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/1.png")
	ImageResource burn1();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/2.png")
	ImageResource burn2();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/3.png")
	ImageResource burn3();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/4.png")
	ImageResource burn4();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/5.png")
	ImageResource burn5();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/6.png")
	ImageResource burn6();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/7.png")
	ImageResource burn7();

	@Source("hu/norbisquest/nq1/resources/images/norbi/burn/8.png")
	ImageResource burn8();

}
