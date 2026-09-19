package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface RockKlub extends ClientBundle {
	RockKlub INSTANCE = GWT.create(RockKlub.class);

	class Data {

		public static List<ImageResource> szurdi_talk = Arrays.asList(
				INSTANCE.szurdi_sit(),
				INSTANCE.szurdi_talk1(),
				INSTANCE.szurdi_sit(),
				INSTANCE.szurdi_talk2());

		public static List<ImageResource> szurdi_newspaper_talk = Arrays.asList(
				INSTANCE.szurdi_newspaper(),
				INSTANCE.szurdi_newspaper_talk1(),
				INSTANCE.szurdi_newspaper(),
				INSTANCE.szurdi_newspaper_talk2());

		//@formatter:off
		public static List<DataResource> norbi = Arrays.asList(
				INSTANCE.n264(), 
				INSTANCE.n265(), 
				INSTANCE.n266(), 
				INSTANCE.n267(), 
				INSTANCE.n268(), 
				INSTANCE.n269(), 
				INSTANCE.n270(), 
				INSTANCE.n271(), 
				INSTANCE.n272(), 
				INSTANCE.n273(), 
				INSTANCE.n286(), 
				INSTANCE.n323(), 
				INSTANCE.n324(), 
				INSTANCE.n325(), 
				INSTANCE.n326(), 
				INSTANCE.n327(), 
				INSTANCE.n328(), 
				INSTANCE.n329(), 
				INSTANCE.n330(), 
				INSTANCE.n331(), 
				INSTANCE.n352(), 
				INSTANCE.n353(), 
				INSTANCE.n354(), 
				INSTANCE.n355(), 
				INSTANCE.n356(), 
				INSTANCE.n357(), 
				INSTANCE.n358(), 
				INSTANCE.n359(), 
				INSTANCE.n360(), 
				INSTANCE.n361(), 
				INSTANCE.n362(), 
				INSTANCE.n363(), 
				INSTANCE.n364(), 
				INSTANCE.n365(), 
				INSTANCE.n366(), 
				INSTANCE.n367(), 
				INSTANCE.n370(), 
				INSTANCE.n371(), 
				INSTANCE.n372(), 
				INSTANCE.n373(), 
				INSTANCE.n374(), 
				INSTANCE.n375(), 
				INSTANCE.n376(), 
				INSTANCE.n377(), 
				INSTANCE.n378(), 
				INSTANCE.n379(), 
				INSTANCE.n380(), 
				INSTANCE.n381(), 
				INSTANCE.n382(), 
				INSTANCE.n383(), 
				INSTANCE.n384(), 
				INSTANCE.n385(), 
				INSTANCE.n386(), 
				INSTANCE.n387(), 
				INSTANCE.n388(), 
				INSTANCE.n420(), 
				INSTANCE.n421(), 
				INSTANCE.n430(), 
				INSTANCE.n431(), 
				INSTANCE.n432(), 
				INSTANCE.n433(), 
				INSTANCE.n439(), 
				INSTANCE.n440(), 
				INSTANCE.n441(), 
				INSTANCE.n442());

		public static List<DataResource> szurdi = Arrays.asList(
				INSTANCE.sz1(), 
				INSTANCE.sz2(), 
				INSTANCE.sz3(), 
				INSTANCE.sz4(), 
				INSTANCE.sz5(), 
				INSTANCE.sz6(), 
				INSTANCE.sz7(), 
				INSTANCE.sz8(), 
				INSTANCE.sz9(), 
				INSTANCE.sz10(), 
				INSTANCE.sz11(), 
				INSTANCE.sz12(), 
				INSTANCE.sz13(), 
				INSTANCE.sz14(), 
				INSTANCE.sz15(), 
				INSTANCE.sz16(), 
				INSTANCE.sz17(), 
				INSTANCE.sz18(), 
				INSTANCE.sz19(), 
				INSTANCE.sz20(), 
				INSTANCE.sz21(), 
				INSTANCE.sz22(), 
				INSTANCE.sz23(), 
				INSTANCE.sz24(), 
				INSTANCE.sz25(), 
				INSTANCE.sz26(), 
				INSTANCE.sz27(), 
				INSTANCE.sz28(), 
				INSTANCE.sz29(), 
				INSTANCE.sz30(), 
				INSTANCE.sz31(), 
				INSTANCE.sz32(), 
				INSTANCE.sz33(), 
				INSTANCE.sz34(), 
				INSTANCE.sz35(), 
				INSTANCE.sz36(), 
				INSTANCE.sz37(), 
				INSTANCE.sz38(), 
				INSTANCE.sz39(), 
				INSTANCE.sz40(), 
				INSTANCE.sz41(), 
				INSTANCE.sz42(), 
				INSTANCE.sz43(), 
				INSTANCE.sz44(), 
				INSTANCE.sz45(), 
				INSTANCE.sz46(), 
				INSTANCE.sz47(), 
				INSTANCE.sz48(), 
				INSTANCE.sz49(), 
				INSTANCE.sz50(), 
				INSTANCE.sz51(), 
				INSTANCE.sz52(), 
				INSTANCE.sz53(), 
				INSTANCE.sz54(), 
				INSTANCE.sz55(), 
				INSTANCE.sz56(),
				INSTANCE.sz57(),
				INSTANCE.sz58()
				
		);
		//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		CLUBCHAIR, PIPE, SNIPS
	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N09")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/text/hu/Szurdi")
	TextResource szurdiLines();

	@Source("hu/norbisquest/nq1/resources/walk/RockKlub")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S09_rockklub.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S09_rockklub_mask_top.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S09_rockklub_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/zsolt05.png")
	ImageResource szurdi_sit();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/zsolt05talk1.png")
	ImageResource szurdi_talk1();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/zsolt05talk2.png")
	ImageResource szurdi_talk2();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/nyul.png")
	ImageResource szurdi_grab();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/olvas.png")
	ImageResource szurdi_read();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/nyulujsaglent.png")
	ImageResource szurdi_newspaper_grab();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/ujsaglent.png")
	ImageResource szurdi_newspaper();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/ujsagtalk1.png")
	ImageResource szurdi_newspaper_talk1();

	@Source("hu/norbisquest/nq1/resources/images/szurdi/ujsagtalk2.png")
	ImageResource szurdi_newspaper_talk2();

	// audio

	// Szurdi
	@Source("hu/norbisquest/nq1/resources/speech/SZ1.mp3")
	DataResource sz1();

	@Source("hu/norbisquest/nq1/resources/speech/SZ2.mp3")
	DataResource sz2();

	@Source("hu/norbisquest/nq1/resources/speech/SZ3.mp3")
	DataResource sz3();

	@Source("hu/norbisquest/nq1/resources/speech/SZ4.mp3")
	DataResource sz4();

	@Source("hu/norbisquest/nq1/resources/speech/SZ5.mp3")
	DataResource sz5();

	@Source("hu/norbisquest/nq1/resources/speech/SZ6.mp3")
	DataResource sz6();

	@Source("hu/norbisquest/nq1/resources/speech/SZ7.mp3")
	DataResource sz7();

	@Source("hu/norbisquest/nq1/resources/speech/SZ8.mp3")
	DataResource sz8();

	@Source("hu/norbisquest/nq1/resources/speech/SZ9.mp3")
	DataResource sz9();

	@Source("hu/norbisquest/nq1/resources/speech/SZ10.mp3")
	DataResource sz10();

	@Source("hu/norbisquest/nq1/resources/speech/SZ11.mp3")
	DataResource sz11();

	@Source("hu/norbisquest/nq1/resources/speech/SZ12.mp3")
	DataResource sz12();

	@Source("hu/norbisquest/nq1/resources/speech/SZ13.mp3")
	DataResource sz13();

	@Source("hu/norbisquest/nq1/resources/speech/SZ14.mp3")
	DataResource sz14();

	@Source("hu/norbisquest/nq1/resources/speech/SZ15.mp3")
	DataResource sz15();

	@Source("hu/norbisquest/nq1/resources/speech/SZ16.mp3")
	DataResource sz16();

	@Source("hu/norbisquest/nq1/resources/speech/SZ17.mp3")
	DataResource sz17();

	@Source("hu/norbisquest/nq1/resources/speech/SZ18.mp3")
	DataResource sz18();

	@Source("hu/norbisquest/nq1/resources/speech/SZ19.mp3")
	DataResource sz19();

	@Source("hu/norbisquest/nq1/resources/speech/SZ20.mp3")
	DataResource sz20();

	@Source("hu/norbisquest/nq1/resources/speech/SZ21.mp3")
	DataResource sz21();

	@Source("hu/norbisquest/nq1/resources/speech/SZ22.mp3")
	DataResource sz22();

	@Source("hu/norbisquest/nq1/resources/speech/SZ23.mp3")
	DataResource sz23();

	@Source("hu/norbisquest/nq1/resources/speech/SZ24.mp3")
	DataResource sz24();

	@Source("hu/norbisquest/nq1/resources/speech/SZ25.mp3")
	DataResource sz25();

	@Source("hu/norbisquest/nq1/resources/speech/SZ26.mp3")
	DataResource sz26();

	@Source("hu/norbisquest/nq1/resources/speech/SZ27.mp3")
	DataResource sz27();

	@Source("hu/norbisquest/nq1/resources/speech/SZ28.mp3")
	DataResource sz28();

	@Source("hu/norbisquest/nq1/resources/speech/SZ29.mp3")
	DataResource sz29();

	@Source("hu/norbisquest/nq1/resources/speech/SZ30.mp3")
	DataResource sz30();

	@Source("hu/norbisquest/nq1/resources/speech/SZ31.mp3")
	DataResource sz31();

	@Source("hu/norbisquest/nq1/resources/speech/SZ32.mp3")
	DataResource sz32();

	@Source("hu/norbisquest/nq1/resources/speech/SZ33.mp3")
	DataResource sz33();

	@Source("hu/norbisquest/nq1/resources/speech/SZ34.mp3")
	DataResource sz34();

	@Source("hu/norbisquest/nq1/resources/speech/SZ35.mp3")
	DataResource sz35();

	@Source("hu/norbisquest/nq1/resources/speech/SZ36.mp3")
	DataResource sz36();

	@Source("hu/norbisquest/nq1/resources/speech/SZ37.mp3")
	DataResource sz37();

	@Source("hu/norbisquest/nq1/resources/speech/SZ38.mp3")
	DataResource sz38();

	@Source("hu/norbisquest/nq1/resources/speech/SZ39.mp3")
	DataResource sz39();

	@Source("hu/norbisquest/nq1/resources/speech/SZ40.mp3")
	DataResource sz40();

	@Source("hu/norbisquest/nq1/resources/speech/SZ41.mp3")
	DataResource sz41();

	@Source("hu/norbisquest/nq1/resources/speech/SZ42.mp3")
	DataResource sz42();

	@Source("hu/norbisquest/nq1/resources/speech/SZ43.mp3")
	DataResource sz43();

	@Source("hu/norbisquest/nq1/resources/speech/SZ44.mp3")
	DataResource sz44();

	@Source("hu/norbisquest/nq1/resources/speech/SZ45.mp3")
	DataResource sz45();

	@Source("hu/norbisquest/nq1/resources/speech/SZ46.mp3")
	DataResource sz46();

	@Source("hu/norbisquest/nq1/resources/speech/SZ47.mp3")
	DataResource sz47();

	@Source("hu/norbisquest/nq1/resources/speech/SZ48.mp3")
	DataResource sz48();

	@Source("hu/norbisquest/nq1/resources/speech/SZ49.mp3")
	DataResource sz49();

	@Source("hu/norbisquest/nq1/resources/speech/SZ50.mp3")
	DataResource sz50();

	@Source("hu/norbisquest/nq1/resources/speech/SZ51.mp3")
	DataResource sz51();

	@Source("hu/norbisquest/nq1/resources/speech/SZ52.mp3")
	DataResource sz52();

	@Source("hu/norbisquest/nq1/resources/speech/SZ53.mp3")
	DataResource sz53();

	@Source("hu/norbisquest/nq1/resources/speech/SZ54.mp3")
	DataResource sz54();

	@Source("hu/norbisquest/nq1/resources/speech/SZ55.mp3")
	DataResource sz55();

	@Source("hu/norbisquest/nq1/resources/speech/SZ56.mp3")
	DataResource sz56();

	@Source("hu/norbisquest/nq1/resources/speech/SZ57.mp3")
	DataResource sz57();

	@Source("hu/norbisquest/nq1/resources/speech/SZ58.mp3")
	DataResource sz58();

	@Source("hu/norbisquest/nq1/resources/speech/N264.mp3")
	DataResource n264();

	@Source("hu/norbisquest/nq1/resources/speech/N265.mp3")
	DataResource n265();

	@Source("hu/norbisquest/nq1/resources/speech/N266.mp3")
	DataResource n266();

	@Source("hu/norbisquest/nq1/resources/speech/N267.mp3")
	DataResource n267();

	@Source("hu/norbisquest/nq1/resources/speech/N268.mp3")
	DataResource n268();

	@Source("hu/norbisquest/nq1/resources/speech/N269.mp3")
	DataResource n269();

	@Source("hu/norbisquest/nq1/resources/speech/N270.mp3")
	DataResource n270();

	@Source("hu/norbisquest/nq1/resources/speech/N271.mp3")
	DataResource n271();

	@Source("hu/norbisquest/nq1/resources/speech/N272.mp3")
	DataResource n272();

	@Source("hu/norbisquest/nq1/resources/speech/N273.mp3")
	DataResource n273();

	@Source("hu/norbisquest/nq1/resources/speech/N286.mp3")
	DataResource n286();

	@Source("hu/norbisquest/nq1/resources/speech/N323.mp3")
	DataResource n323();

	@Source("hu/norbisquest/nq1/resources/speech/N324.mp3")
	DataResource n324();

	@Source("hu/norbisquest/nq1/resources/speech/N325.mp3")
	DataResource n325();

	@Source("hu/norbisquest/nq1/resources/speech/N326.mp3")
	DataResource n326();

	@Source("hu/norbisquest/nq1/resources/speech/N327.mp3")
	DataResource n327();

	@Source("hu/norbisquest/nq1/resources/speech/N328.mp3")
	DataResource n328();

	@Source("hu/norbisquest/nq1/resources/speech/N329.mp3")
	DataResource n329();

	@Source("hu/norbisquest/nq1/resources/speech/N330.mp3")
	DataResource n330();

	@Source("hu/norbisquest/nq1/resources/speech/N331.mp3")
	DataResource n331();

	@Source("hu/norbisquest/nq1/resources/speech/N352.mp3")
	DataResource n352();

	@Source("hu/norbisquest/nq1/resources/speech/N353.mp3")
	DataResource n353();

	@Source("hu/norbisquest/nq1/resources/speech/N354.mp3")
	DataResource n354();

	@Source("hu/norbisquest/nq1/resources/speech/N355.mp3")
	DataResource n355();

	@Source("hu/norbisquest/nq1/resources/speech/N356.mp3")
	DataResource n356();

	@Source("hu/norbisquest/nq1/resources/speech/N357.mp3")
	DataResource n357();

	@Source("hu/norbisquest/nq1/resources/speech/N358.mp3")
	DataResource n358();

	@Source("hu/norbisquest/nq1/resources/speech/N359.mp3")
	DataResource n359();

	@Source("hu/norbisquest/nq1/resources/speech/N360.mp3")
	DataResource n360();

	@Source("hu/norbisquest/nq1/resources/speech/N361.mp3")
	DataResource n361();

	@Source("hu/norbisquest/nq1/resources/speech/N362.mp3")
	DataResource n362();

	@Source("hu/norbisquest/nq1/resources/speech/N363.mp3")
	DataResource n363();

	@Source("hu/norbisquest/nq1/resources/speech/N364.mp3")
	DataResource n364();

	@Source("hu/norbisquest/nq1/resources/speech/N365.mp3")
	DataResource n365();

	@Source("hu/norbisquest/nq1/resources/speech/N366.mp3")
	DataResource n366();

	@Source("hu/norbisquest/nq1/resources/speech/N367.mp3")
	DataResource n367();

	@Source("hu/norbisquest/nq1/resources/speech/N370.mp3")
	DataResource n370();

	@Source("hu/norbisquest/nq1/resources/speech/N371.mp3")
	DataResource n371();

	@Source("hu/norbisquest/nq1/resources/speech/N372.mp3")
	DataResource n372();

	@Source("hu/norbisquest/nq1/resources/speech/N373.mp3")
	DataResource n373();

	@Source("hu/norbisquest/nq1/resources/speech/N374.mp3")
	DataResource n374();

	@Source("hu/norbisquest/nq1/resources/speech/N375.mp3")
	DataResource n375();

	@Source("hu/norbisquest/nq1/resources/speech/N376.mp3")
	DataResource n376();

	@Source("hu/norbisquest/nq1/resources/speech/N377.mp3")
	DataResource n377();

	@Source("hu/norbisquest/nq1/resources/speech/N378.mp3")
	DataResource n378();

	@Source("hu/norbisquest/nq1/resources/speech/N379.mp3")
	DataResource n379();

	@Source("hu/norbisquest/nq1/resources/speech/N380.mp3")
	DataResource n380();

	@Source("hu/norbisquest/nq1/resources/speech/N381.mp3")
	DataResource n381();

	@Source("hu/norbisquest/nq1/resources/speech/N382.mp3")
	DataResource n382();

	@Source("hu/norbisquest/nq1/resources/speech/N383.mp3")
	DataResource n383();

	@Source("hu/norbisquest/nq1/resources/speech/N384.mp3")
	DataResource n384();

	@Source("hu/norbisquest/nq1/resources/speech/N385.mp3")
	DataResource n385();

	@Source("hu/norbisquest/nq1/resources/speech/N386.mp3")
	DataResource n386();

	@Source("hu/norbisquest/nq1/resources/speech/N387.mp3")
	DataResource n387();

	@Source("hu/norbisquest/nq1/resources/speech/N388.mp3")
	DataResource n388();

	@Source("hu/norbisquest/nq1/resources/speech/N420.mp3")
	DataResource n420();

	@Source("hu/norbisquest/nq1/resources/speech/N421.mp3")
	DataResource n421();

	@Source("hu/norbisquest/nq1/resources/speech/N430.mp3")
	DataResource n430();

	@Source("hu/norbisquest/nq1/resources/speech/N431.mp3")
	DataResource n431();

	@Source("hu/norbisquest/nq1/resources/speech/N432.mp3")
	DataResource n432();

	@Source("hu/norbisquest/nq1/resources/speech/N433.mp3")
	DataResource n433();

	@Source("hu/norbisquest/nq1/resources/speech/N439.mp3")
	DataResource n439();

	@Source("hu/norbisquest/nq1/resources/speech/N440.mp3")
	DataResource n440();

	@Source("hu/norbisquest/nq1/resources/speech/N441.mp3")
	DataResource n441();

	@Source("hu/norbisquest/nq1/resources/speech/N442.mp3")
	DataResource n442();

}
