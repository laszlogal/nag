package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface NewsStand extends ClientBundle {
	NewsStand INSTANCE = GWT.create(NewsStand.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n1(), 
				INSTANCE.n2(), 
				INSTANCE.n3(), 
				INSTANCE.n4(), 
				INSTANCE.n5(), 
				INSTANCE.n6(), 
				INSTANCE.n7(), 
				INSTANCE.n8(), 
				INSTANCE.n9(), 
				INSTANCE.n10(), 
				INSTANCE.n11(), 
				INSTANCE.n12(), 
				INSTANCE.n13(), 
				INSTANCE.n14(), 
				INSTANCE.n15(), 
				INSTANCE.n16(), 
				INSTANCE.n17(), 
				INSTANCE.n18(), 
				INSTANCE.n19(), 
				INSTANCE.n45(), 
				INSTANCE.n46(), 
				INSTANCE.n47(), 
				INSTANCE.n48(), 
				INSTANCE.n49(), 
				INSTANCE.n50(), 
				INSTANCE.n51(), 
				INSTANCE.n52(), 
				INSTANCE.n53(), 
				INSTANCE.n54(), 
				INSTANCE.n55(), 
				INSTANCE.n56(), 
				INSTANCE.n57(), 
				INSTANCE.n58(), 
				INSTANCE.n59(), 
				INSTANCE.n60(), 
				INSTANCE.n61(), 
				INSTANCE.n62(), 
				INSTANCE.n63(), 
				INSTANCE.n64(), 
				INSTANCE.n65(), 
				INSTANCE.n66(), 
				INSTANCE.n67(), 
				INSTANCE.n202(), 
				INSTANCE.n203(), 
				INSTANCE.n204(), 
				INSTANCE.n205(), 
				INSTANCE.n206(), 
				INSTANCE.n207(), 
				INSTANCE.n208(), 
				INSTANCE.n209(), 
				INSTANCE.n210(), 
				INSTANCE.n211(), 
				INSTANCE.n213(), 
				INSTANCE.n214(), 
				INSTANCE.n215(), 
				INSTANCE.n216(), 
				INSTANCE.n217(), 
				INSTANCE.n218(), 
				INSTANCE.n219(), 
				INSTANCE.n220(), 
				INSTANCE.n221(), 
				INSTANCE.n222(), 
				INSTANCE.n223(), 
				INSTANCE.n224(), 
				INSTANCE.n225(), 
				INSTANCE.n226(), 
				INSTANCE.n227(), 
				INSTANCE.n228(), 
				INSTANCE.n229(), 
				INSTANCE.n230(), 
				INSTANCE.n231(), 
				INSTANCE.n232(), 
				INSTANCE.n298(), 
				INSTANCE.n300(), 
				INSTANCE.n301(), 
				INSTANCE.n302(), 
				INSTANCE.n304(), 
				INSTANCE.n305(), 
				INSTANCE.n306(), 
				INSTANCE.n307(), 
				INSTANCE.n309(), 
				INSTANCE.n310(), 
				INSTANCE.n316(), 
				INSTANCE.n320(), 
				INSTANCE.n321(), 
				INSTANCE.n322(), 
				INSTANCE.n325(), 
				INSTANCE.n332(), 
				INSTANCE.n333(), 
				INSTANCE.n334(), 
				INSTANCE.n341(), 
				INSTANCE.n342(), 
				INSTANCE.n343(), 
				INSTANCE.n344(), 
				INSTANCE.n345(), 
				INSTANCE.n346(), 
				INSTANCE.n347(), 
				INSTANCE.n348(), 
				INSTANCE.n349(), 
				INSTANCE.n350(), 
				INSTANCE.n351(), 
				INSTANCE.n402(), 
				INSTANCE.n405(), 
				INSTANCE.n406(), 
				INSTANCE.n414(), 
				INSTANCE.n415(), 
				INSTANCE.n416(), 
				INSTANCE.n417(), 
				INSTANCE.n418(), 
				INSTANCE.n419(), 
				INSTANCE.n434(), 
				INSTANCE.n435(), 
				INSTANCE.n436(), 
				INSTANCE.n437(), 
				INSTANCE.n438(), 
				INSTANCE.n446());
				//@formatter:on

		public static List<DataResource> newsAgent = Arrays.asList(
				//@formatter:off
				INSTANCE.u1(), 
				INSTANCE.u2(), 
				INSTANCE.u3(), 
				INSTANCE.u4(), 
				INSTANCE.u5(), 
				INSTANCE.u6(), 
				INSTANCE.u7(), 
				INSTANCE.u8(), 
				INSTANCE.u9(), 
				INSTANCE.u10(), 
				INSTANCE.u11(), 
				INSTANCE.u12(), 
				INSTANCE.u13(), 
				INSTANCE.u14(), 
				INSTANCE.u15(), 
				INSTANCE.u16(), 
				INSTANCE.u17(), 
				INSTANCE.u18(), 
				INSTANCE.u19(), 
				INSTANCE.u20(), 
				INSTANCE.u21(), 
				INSTANCE.u22(), 
				INSTANCE.u23(), 
				INSTANCE.u24(), 
				INSTANCE.u25(), 
				INSTANCE.u26(), 
				INSTANCE.u27(), 
				INSTANCE.u28(), 
				INSTANCE.u29(), 
				INSTANCE.u30(), 
				INSTANCE.u31(), 
				INSTANCE.u32(),
				INSTANCE.u33());
				//@formatter:on
		public static List<DataResource> hobo = Arrays.asList(
				//@formatter:off
				INSTANCE.cs1(), 
				INSTANCE.cs2(), 
				INSTANCE.cs3(), 
				INSTANCE.cs4(), 
				INSTANCE.cs5(), 
				INSTANCE.cs6(), 
				INSTANCE.cs7(), 
				INSTANCE.cs8(), 
				INSTANCE.cs9(), 
				INSTANCE.cs10(), 
				INSTANCE.cs11(), 
				INSTANCE.cs12(), 
				INSTANCE.cs13(), 
				INSTANCE.cs14(), 
				INSTANCE.cs15(), 
				INSTANCE.cs16(), 
				INSTANCE.cs17(), 
				INSTANCE.cs18(), 
				INSTANCE.cs19(), 
				INSTANCE.cs20(), 
				INSTANCE.cs21(), 
				INSTANCE.cs22(), 
				INSTANCE.cs23(), 
				INSTANCE.cs24(), 
				INSTANCE.cs25(), 
				INSTANCE.cs26(), 
				INSTANCE.cs27(), 
				INSTANCE.cs28(), 
				INSTANCE.cs29(), 
				INSTANCE.cs30(), 
				INSTANCE.cs31(), 
				INSTANCE.cs32(), 
				INSTANCE.cs33(), 
				INSTANCE.cs34(), 
				INSTANCE.cs35(), 
				INSTANCE.cs36(), 
				INSTANCE.cs37(), 
				INSTANCE.cs38(), 
				INSTANCE.cs39(), 
				INSTANCE.cs40(), 
				INSTANCE.cs41(), 
				INSTANCE.cs42(), 
				INSTANCE.cs43(), 
				INSTANCE.cs44(), 
				INSTANCE.cs45(), 
				INSTANCE.cs46(), 
				INSTANCE.cs47(), 
				INSTANCE.cs48(), 
				INSTANCE.cs49());
				//@formatter:on
		public static List<ImageResource> newsagent_talk = Arrays.asList(
				INSTANCE.newsagent_talk1(),
				INSTANCE.newsagent_talk2());

		public static List<ImageResource> hobo_talk = Arrays.asList(
				INSTANCE.hobo_talk1(),
				INSTANCE.hobo_talk2());

		public static List<ImageResource> hobo_talk_alcohol = Arrays.asList(
				INSTANCE.hobo_with_alcohol(),
				INSTANCE.hobo_with_alcohol_talk());

		public static List<ImageResource> hobo_talk_newspaper = Arrays.asList(
				INSTANCE.hobo_read_talk1(),
				INSTANCE.hobo_read_talk2(),
				INSTANCE.hobo_read_talk3());

		public static List<ImageResource> hobo_grab = Arrays.asList(
				INSTANCE.hobo_grab(),
				INSTANCE.hobo_grab());

		public static List<ImageResource> hobo_drink = Arrays.asList(
				INSTANCE.hobo_drink1(),
				INSTANCE.hobo_drink2());
	}

	enum HotSpots implements HotSpot.Id {
		TRASH, NEWSPAPERS1, NEWSPAPERS2, DAILIES, HOBO_HAT, HOBO
	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N05")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/text/hu/NewsAgent")
	TextResource newsAgentLines();

	@Source("hu/norbisquest/nq1/resources/text/hu/Hobo")
	TextResource hoboLines();

	@Source("hu/norbisquest/nq1/resources/walk/NewsStand")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S05_newsstand.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S05_newsstand_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/ujsagos/sit.png")
	ImageResource newsagent_sit();

	@Source("hu/norbisquest/nq1/resources/images/ujsagos/talk1.png")
	ImageResource newsagent_talk1();

	@Source("hu/norbisquest/nq1/resources/images/ujsagos/talk2.png")
	ImageResource newsagent_talk2();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csoves02b.png")
	ImageResource hobo_normal();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csoves02btalk.png")
	ImageResource hobo_talk1();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csoves02btalk2.png")
	ImageResource hobo_talk2();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesiszik1.png")
	ImageResource hobo_drink1();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesiszik2.png")
	ImageResource hobo_drink2();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesnyul.png")
	ImageResource hobo_grab();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesnyulujsag.png")
	ImageResource hobo_grab_newspaper();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesolvas02.png")
	ImageResource hobo_read();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesolvas02balra.png")
	ImageResource hobo_read_left();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesolvas02jobbra.png")
	ImageResource hobo_read_right();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesolvas02talk0.png")
	ImageResource hobo_read_talk1();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesolvas02talk1.png")
	ImageResource hobo_read_talk2();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesolvas02talk2.png")
	ImageResource hobo_read_talk3();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovespiakezben.png")
	ImageResource hobo_with_alcohol();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovespiakezbentalk1.png")
	ImageResource hobo_with_alcohol_talk();

	@Source("hu/norbisquest/nq1/resources/images/csoves/csovesujsagotnyujt.png")
	ImageResource hobo_give_newspaper_back();

	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N1.mp3")
	DataResource n1();

	@Source("hu/norbisquest/nq1/resources/speech/N2.mp3")
	DataResource n2();

	@Source("hu/norbisquest/nq1/resources/speech/N3.mp3")
	DataResource n3();

	@Source("hu/norbisquest/nq1/resources/speech/N4.mp3")
	DataResource n4();

	@Source("hu/norbisquest/nq1/resources/speech/N5.mp3")
	DataResource n5();

	@Source("hu/norbisquest/nq1/resources/speech/N6.mp3")
	DataResource n6();

	@Source("hu/norbisquest/nq1/resources/speech/N7.mp3")
	DataResource n7();

	@Source("hu/norbisquest/nq1/resources/speech/N8.mp3")
	DataResource n8();

	@Source("hu/norbisquest/nq1/resources/speech/N9.mp3")
	DataResource n9();

	@Source("hu/norbisquest/nq1/resources/speech/N10.mp3")
	DataResource n10();

	@Source("hu/norbisquest/nq1/resources/speech/N11.mp3")
	DataResource n11();

	@Source("hu/norbisquest/nq1/resources/speech/N12.mp3")
	DataResource n12();

	@Source("hu/norbisquest/nq1/resources/speech/N13.mp3")
	DataResource n13();

	@Source("hu/norbisquest/nq1/resources/speech/N14.mp3")
	DataResource n14();

	@Source("hu/norbisquest/nq1/resources/speech/N15.mp3")
	DataResource n15();

	@Source("hu/norbisquest/nq1/resources/speech/N16.mp3")
	DataResource n16();

	@Source("hu/norbisquest/nq1/resources/speech/N17.mp3")
	DataResource n17();

	@Source("hu/norbisquest/nq1/resources/speech/N18.mp3")
	DataResource n18();

	@Source("hu/norbisquest/nq1/resources/speech/N19.mp3")
	DataResource n19();

	@Source("hu/norbisquest/nq1/resources/speech/N45.mp3")
	DataResource n45();

	@Source("hu/norbisquest/nq1/resources/speech/N46.mp3")
	DataResource n46();

	@Source("hu/norbisquest/nq1/resources/speech/N47.mp3")
	DataResource n47();

	@Source("hu/norbisquest/nq1/resources/speech/N48.mp3")
	DataResource n48();

	@Source("hu/norbisquest/nq1/resources/speech/N49.mp3")
	DataResource n49();

	@Source("hu/norbisquest/nq1/resources/speech/N50.mp3")
	DataResource n50();

	@Source("hu/norbisquest/nq1/resources/speech/N51.mp3")
	DataResource n51();

	@Source("hu/norbisquest/nq1/resources/speech/N52.mp3")
	DataResource n52();

	@Source("hu/norbisquest/nq1/resources/speech/N53.mp3")
	DataResource n53();

	@Source("hu/norbisquest/nq1/resources/speech/N54.mp3")
	DataResource n54();

	@Source("hu/norbisquest/nq1/resources/speech/N55.mp3")
	DataResource n55();

	@Source("hu/norbisquest/nq1/resources/speech/N56.mp3")
	DataResource n56();

	@Source("hu/norbisquest/nq1/resources/speech/N57.mp3")
	DataResource n57();

	@Source("hu/norbisquest/nq1/resources/speech/N58.mp3")
	DataResource n58();

	@Source("hu/norbisquest/nq1/resources/speech/N59.mp3")
	DataResource n59();

	@Source("hu/norbisquest/nq1/resources/speech/N60.mp3")
	DataResource n60();

	@Source("hu/norbisquest/nq1/resources/speech/N61.mp3")
	DataResource n61();

	@Source("hu/norbisquest/nq1/resources/speech/N62.mp3")
	DataResource n62();

	@Source("hu/norbisquest/nq1/resources/speech/N63.mp3")
	DataResource n63();

	@Source("hu/norbisquest/nq1/resources/speech/N64.mp3")
	DataResource n64();

	@Source("hu/norbisquest/nq1/resources/speech/N65.mp3")
	DataResource n65();

	@Source("hu/norbisquest/nq1/resources/speech/N66.mp3")
	DataResource n66();

	@Source("hu/norbisquest/nq1/resources/speech/N67.mp3")
	DataResource n67();

	@Source("hu/norbisquest/nq1/resources/speech/N202.mp3")
	DataResource n202();

	@Source("hu/norbisquest/nq1/resources/speech/N203.mp3")
	DataResource n203();

	@Source("hu/norbisquest/nq1/resources/speech/N204.mp3")
	DataResource n204();

	@Source("hu/norbisquest/nq1/resources/speech/N205.mp3")
	DataResource n205();

	@Source("hu/norbisquest/nq1/resources/speech/N206.mp3")
	DataResource n206();

	@Source("hu/norbisquest/nq1/resources/speech/N207.mp3")
	DataResource n207();

	@Source("hu/norbisquest/nq1/resources/speech/N208.mp3")
	DataResource n208();

	@Source("hu/norbisquest/nq1/resources/speech/N209.mp3")
	DataResource n209();

	@Source("hu/norbisquest/nq1/resources/speech/N210.mp3")
	DataResource n210();

	@Source("hu/norbisquest/nq1/resources/speech/N211.mp3")
	DataResource n211();

	@Source("hu/norbisquest/nq1/resources/speech/N213.mp3")
	DataResource n213();

	@Source("hu/norbisquest/nq1/resources/speech/N214.mp3")
	DataResource n214();

	@Source("hu/norbisquest/nq1/resources/speech/N215.mp3")
	DataResource n215();

	@Source("hu/norbisquest/nq1/resources/speech/N216.mp3")
	DataResource n216();

	@Source("hu/norbisquest/nq1/resources/speech/N217.mp3")
	DataResource n217();

	@Source("hu/norbisquest/nq1/resources/speech/N218.mp3")
	DataResource n218();

	@Source("hu/norbisquest/nq1/resources/speech/N219.mp3")
	DataResource n219();

	@Source("hu/norbisquest/nq1/resources/speech/N220.mp3")
	DataResource n220();

	@Source("hu/norbisquest/nq1/resources/speech/N221.mp3")
	DataResource n221();

	@Source("hu/norbisquest/nq1/resources/speech/N222.mp3")
	DataResource n222();

	@Source("hu/norbisquest/nq1/resources/speech/N223.mp3")
	DataResource n223();

	@Source("hu/norbisquest/nq1/resources/speech/N224.mp3")
	DataResource n224();

	@Source("hu/norbisquest/nq1/resources/speech/N225.mp3")
	DataResource n225();

	@Source("hu/norbisquest/nq1/resources/speech/N226.mp3")
	DataResource n226();

	@Source("hu/norbisquest/nq1/resources/speech/N227.mp3")
	DataResource n227();

	@Source("hu/norbisquest/nq1/resources/speech/N228.mp3")
	DataResource n228();

	@Source("hu/norbisquest/nq1/resources/speech/N229.mp3")
	DataResource n229();

	@Source("hu/norbisquest/nq1/resources/speech/N230.mp3")
	DataResource n230();

	@Source("hu/norbisquest/nq1/resources/speech/N231.mp3")
	DataResource n231();

	@Source("hu/norbisquest/nq1/resources/speech/N232.mp3")
	DataResource n232();

	@Source("hu/norbisquest/nq1/resources/speech/N298.mp3")
	DataResource n298();

	@Source("hu/norbisquest/nq1/resources/speech/N300.mp3")
	DataResource n300();

	@Source("hu/norbisquest/nq1/resources/speech/N301.mp3")
	DataResource n301();

	@Source("hu/norbisquest/nq1/resources/speech/N302.mp3")
	DataResource n302();

	@Source("hu/norbisquest/nq1/resources/speech/N304.mp3")
	DataResource n304();

	@Source("hu/norbisquest/nq1/resources/speech/N305.mp3")
	DataResource n305();

	@Source("hu/norbisquest/nq1/resources/speech/N306.mp3")
	DataResource n306();

	@Source("hu/norbisquest/nq1/resources/speech/N307.mp3")
	DataResource n307();

	@Source("hu/norbisquest/nq1/resources/speech/N309.mp3")
	DataResource n309();

	@Source("hu/norbisquest/nq1/resources/speech/N310.mp3")
	DataResource n310();

	@Source("hu/norbisquest/nq1/resources/speech/N316.mp3")
	DataResource n316();

	@Source("hu/norbisquest/nq1/resources/speech/N320.mp3")
	DataResource n320();

	@Source("hu/norbisquest/nq1/resources/speech/N321.mp3")
	DataResource n321();

	@Source("hu/norbisquest/nq1/resources/speech/N322.mp3")
	DataResource n322();

	@Source("hu/norbisquest/nq1/resources/speech/N325.mp3")
	DataResource n325();

	@Source("hu/norbisquest/nq1/resources/speech/N332.mp3")
	DataResource n332();

	@Source("hu/norbisquest/nq1/resources/speech/N333.mp3")
	DataResource n333();

	@Source("hu/norbisquest/nq1/resources/speech/N334.mp3")
	DataResource n334();

	@Source("hu/norbisquest/nq1/resources/speech/N341.mp3")
	DataResource n341();

	@Source("hu/norbisquest/nq1/resources/speech/N342.mp3")
	DataResource n342();

	@Source("hu/norbisquest/nq1/resources/speech/N343.mp3")
	DataResource n343();

	@Source("hu/norbisquest/nq1/resources/speech/N344.mp3")
	DataResource n344();

	@Source("hu/norbisquest/nq1/resources/speech/N345.mp3")
	DataResource n345();

	@Source("hu/norbisquest/nq1/resources/speech/N346.mp3")
	DataResource n346();

	@Source("hu/norbisquest/nq1/resources/speech/N347.mp3")
	DataResource n347();

	@Source("hu/norbisquest/nq1/resources/speech/N348.mp3")
	DataResource n348();

	@Source("hu/norbisquest/nq1/resources/speech/N349.mp3")
	DataResource n349();

	@Source("hu/norbisquest/nq1/resources/speech/N350.mp3")
	DataResource n350();

	@Source("hu/norbisquest/nq1/resources/speech/N351.mp3")
	DataResource n351();

	@Source("hu/norbisquest/nq1/resources/speech/N402.mp3")
	DataResource n402();

	@Source("hu/norbisquest/nq1/resources/speech/N405.mp3")
	DataResource n405();

	@Source("hu/norbisquest/nq1/resources/speech/N406.mp3")
	DataResource n406();

	@Source("hu/norbisquest/nq1/resources/speech/N414.mp3")
	DataResource n414();

	@Source("hu/norbisquest/nq1/resources/speech/N415.mp3")
	DataResource n415();

	@Source("hu/norbisquest/nq1/resources/speech/N416.mp3")
	DataResource n416();

	@Source("hu/norbisquest/nq1/resources/speech/N417.mp3")
	DataResource n417();

	@Source("hu/norbisquest/nq1/resources/speech/N418.mp3")
	DataResource n418();

	@Source("hu/norbisquest/nq1/resources/speech/N419.mp3")
	DataResource n419();

	@Source("hu/norbisquest/nq1/resources/speech/N434.mp3")
	DataResource n434();

	@Source("hu/norbisquest/nq1/resources/speech/N435.mp3")
	DataResource n435();

	@Source("hu/norbisquest/nq1/resources/speech/N436.mp3")
	DataResource n436();

	@Source("hu/norbisquest/nq1/resources/speech/N437.mp3")
	DataResource n437();

	@Source("hu/norbisquest/nq1/resources/speech/N438.mp3")
	DataResource n438();

	@Source("hu/norbisquest/nq1/resources/speech/N446.mp3")
	DataResource n446();

	// NewsAgent
	@Source("hu/norbisquest/nq1/resources/speech/u1.mp3")
	DataResource u1();

	@Source("hu/norbisquest/nq1/resources/speech/u2.mp3")
	DataResource u2();

	@Source("hu/norbisquest/nq1/resources/speech/u3.mp3")
	DataResource u3();

	@Source("hu/norbisquest/nq1/resources/speech/u4.mp3")
	DataResource u4();

	@Source("hu/norbisquest/nq1/resources/speech/u5.mp3")
	DataResource u5();

	@Source("hu/norbisquest/nq1/resources/speech/u6.mp3")
	DataResource u6();

	@Source("hu/norbisquest/nq1/resources/speech/u7.mp3")
	DataResource u7();

	@Source("hu/norbisquest/nq1/resources/speech/u8.mp3")
	DataResource u8();

	@Source("hu/norbisquest/nq1/resources/speech/u9.mp3")
	DataResource u9();

	@Source("hu/norbisquest/nq1/resources/speech/u10.mp3")
	DataResource u10();

	@Source("hu/norbisquest/nq1/resources/speech/u11.mp3")
	DataResource u11();

	@Source("hu/norbisquest/nq1/resources/speech/u12.mp3")
	DataResource u12();

	@Source("hu/norbisquest/nq1/resources/speech/u13.mp3")
	DataResource u13();

	@Source("hu/norbisquest/nq1/resources/speech/u14.mp3")
	DataResource u14();

	@Source("hu/norbisquest/nq1/resources/speech/u15.mp3")
	DataResource u15();

	@Source("hu/norbisquest/nq1/resources/speech/u16.mp3")
	DataResource u16();

	@Source("hu/norbisquest/nq1/resources/speech/u17.mp3")
	DataResource u17();

	@Source("hu/norbisquest/nq1/resources/speech/u18.mp3")
	DataResource u18();

	@Source("hu/norbisquest/nq1/resources/speech/u19.mp3")
	DataResource u19();

	@Source("hu/norbisquest/nq1/resources/speech/u20.mp3")
	DataResource u20();

	@Source("hu/norbisquest/nq1/resources/speech/u21.mp3")
	DataResource u21();

	@Source("hu/norbisquest/nq1/resources/speech/u22.mp3")
	DataResource u22();

	@Source("hu/norbisquest/nq1/resources/speech/u23.mp3")
	DataResource u23();

	@Source("hu/norbisquest/nq1/resources/speech/u24.mp3")
	DataResource u24();

	@Source("hu/norbisquest/nq1/resources/speech/u25.mp3")
	DataResource u25();

	@Source("hu/norbisquest/nq1/resources/speech/u26.mp3")
	DataResource u26();

	@Source("hu/norbisquest/nq1/resources/speech/u27.mp3")
	DataResource u27();

	@Source("hu/norbisquest/nq1/resources/speech/u28.mp3")
	DataResource u28();

	@Source("hu/norbisquest/nq1/resources/speech/u29.mp3")
	DataResource u29();

	@Source("hu/norbisquest/nq1/resources/speech/u30.mp3")
	DataResource u30();

	@Source("hu/norbisquest/nq1/resources/speech/u31.mp3")
	DataResource u31();

	@Source("hu/norbisquest/nq1/resources/speech/u32.mp3")
	DataResource u32();

	@Source("hu/norbisquest/nq1/resources/speech/u33.mp3")
	DataResource u33();

	// Hobo
	@Source("hu/norbisquest/nq1/resources/speech/cs1.mp3")
	DataResource cs1();

	@Source("hu/norbisquest/nq1/resources/speech/cs2.mp3")
	DataResource cs2();

	@Source("hu/norbisquest/nq1/resources/speech/cs3.mp3")
	DataResource cs3();

	@Source("hu/norbisquest/nq1/resources/speech/cs4.mp3")
	DataResource cs4();

	@Source("hu/norbisquest/nq1/resources/speech/cs5.mp3")
	DataResource cs5();

	@Source("hu/norbisquest/nq1/resources/speech/cs6.mp3")
	DataResource cs6();

	@Source("hu/norbisquest/nq1/resources/speech/cs7.mp3")
	DataResource cs7();

	@Source("hu/norbisquest/nq1/resources/speech/cs8.mp3")
	DataResource cs8();

	@Source("hu/norbisquest/nq1/resources/speech/cs9.mp3")
	DataResource cs9();

	@Source("hu/norbisquest/nq1/resources/speech/cs10.mp3")
	DataResource cs10();

	@Source("hu/norbisquest/nq1/resources/speech/cs11.mp3")
	DataResource cs11();

	@Source("hu/norbisquest/nq1/resources/speech/cs12.mp3")
	DataResource cs12();

	@Source("hu/norbisquest/nq1/resources/speech/cs13.mp3")
	DataResource cs13();

	@Source("hu/norbisquest/nq1/resources/speech/cs14.mp3")
	DataResource cs14();

	@Source("hu/norbisquest/nq1/resources/speech/cs15.mp3")
	DataResource cs15();

	@Source("hu/norbisquest/nq1/resources/speech/cs16.mp3")
	DataResource cs16();

	@Source("hu/norbisquest/nq1/resources/speech/cs17.mp3")
	DataResource cs17();

	@Source("hu/norbisquest/nq1/resources/speech/cs18.mp3")
	DataResource cs18();

	@Source("hu/norbisquest/nq1/resources/speech/cs19.mp3")
	DataResource cs19();

	@Source("hu/norbisquest/nq1/resources/speech/cs20.mp3")
	DataResource cs20();

	@Source("hu/norbisquest/nq1/resources/speech/cs21.mp3")
	DataResource cs21();

	@Source("hu/norbisquest/nq1/resources/speech/cs22.mp3")
	DataResource cs22();

	@Source("hu/norbisquest/nq1/resources/speech/cs23.mp3")
	DataResource cs23();

	@Source("hu/norbisquest/nq1/resources/speech/cs24.mp3")
	DataResource cs24();

	@Source("hu/norbisquest/nq1/resources/speech/cs25.mp3")
	DataResource cs25();

	@Source("hu/norbisquest/nq1/resources/speech/cs26.mp3")
	DataResource cs26();

	@Source("hu/norbisquest/nq1/resources/speech/cs27.mp3")
	DataResource cs27();

	@Source("hu/norbisquest/nq1/resources/speech/cs28.mp3")
	DataResource cs28();

	@Source("hu/norbisquest/nq1/resources/speech/cs29.mp3")
	DataResource cs29();

	@Source("hu/norbisquest/nq1/resources/speech/cs30.mp3")
	DataResource cs30();

	@Source("hu/norbisquest/nq1/resources/speech/cs31.mp3")
	DataResource cs31();

	@Source("hu/norbisquest/nq1/resources/speech/cs32.mp3")
	DataResource cs32();

	@Source("hu/norbisquest/nq1/resources/speech/cs33.mp3")
	DataResource cs33();

	@Source("hu/norbisquest/nq1/resources/speech/cs34.mp3")
	DataResource cs34();

	@Source("hu/norbisquest/nq1/resources/speech/cs35.mp3")
	DataResource cs35();

	@Source("hu/norbisquest/nq1/resources/speech/cs36.mp3")
	DataResource cs36();

	@Source("hu/norbisquest/nq1/resources/speech/cs37.mp3")
	DataResource cs37();

	@Source("hu/norbisquest/nq1/resources/speech/cs38.mp3")
	DataResource cs38();

	@Source("hu/norbisquest/nq1/resources/speech/cs39.mp3")
	DataResource cs39();

	@Source("hu/norbisquest/nq1/resources/speech/cs40.mp3")
	DataResource cs40();

	@Source("hu/norbisquest/nq1/resources/speech/cs41.mp3")
	DataResource cs41();

	@Source("hu/norbisquest/nq1/resources/speech/cs42.mp3")
	DataResource cs42();

	@Source("hu/norbisquest/nq1/resources/speech/cs43.mp3")
	DataResource cs43();

	@Source("hu/norbisquest/nq1/resources/speech/cs44.mp3")
	DataResource cs44();

	@Source("hu/norbisquest/nq1/resources/speech/cs45.mp3")
	DataResource cs45();

	@Source("hu/norbisquest/nq1/resources/speech/cs46.mp3")
	DataResource cs46();

	@Source("hu/norbisquest/nq1/resources/speech/cs47.mp3")
	DataResource cs47();

	@Source("hu/norbisquest/nq1/resources/speech/cs48.mp3")
	DataResource cs48();

	@Source("hu/norbisquest/nq1/resources/speech/cs49.mp3")
	DataResource cs49();

}
