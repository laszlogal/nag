package hu.norbisquest.nq1.client.data.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.game.target.HotSpot;

import java.util.Arrays;
import java.util.List;

public interface VRKFront extends ClientBundle {
	VRKFront INSTANCE = GWT.create(VRKFront.class);

	class Data {
		public static List<ImageResource> punk_talk = Arrays.asList(
				INSTANCE.punk(),
				INSTANCE.punk_talk1(),
				INSTANCE.punk_talk2(),
				INSTANCE.punk());

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n25(), 
				INSTANCE.n26(), 
				INSTANCE.n27(), 
				INSTANCE.n28(), 
				INSTANCE.n29(), 
				INSTANCE.n30(), 
				INSTANCE.n31(), 
				INSTANCE.n32(), 
				INSTANCE.n33(), 
				INSTANCE.n34(), 
				INSTANCE.n35(), 
				INSTANCE.n36(), 
				INSTANCE.n37(), 
				INSTANCE.n38(), 
				INSTANCE.n39(), 
				INSTANCE.n40(), 
				INSTANCE.n41(), 
				INSTANCE.n42(), 
				INSTANCE.n43(), 
				INSTANCE.n44(), 
				INSTANCE.n247(), 
				INSTANCE.n248(), 
				INSTANCE.n249(), 
				INSTANCE.n250(), 
				INSTANCE.n251(), 
				INSTANCE.n252(), 
				INSTANCE.n253(), 
				INSTANCE.n254(), 
				INSTANCE.n255(), 
				INSTANCE.n256(), 
				INSTANCE.n257(), 
				INSTANCE.n258(), 
				INSTANCE.n259(), 
				INSTANCE.n260(), 
				INSTANCE.n261(), 
				INSTANCE.n311(), 
				INSTANCE.n312(), 
				INSTANCE.n313(), 
				INSTANCE.n314(), 
				INSTANCE.n315(), 
				INSTANCE.n335(), 
				INSTANCE.n336(), 
				INSTANCE.n337(), 
				INSTANCE.n338(), 
				INSTANCE.n339(), 
				INSTANCE.n340(), 
				INSTANCE.n422(), 
				INSTANCE.n423(), 
				INSTANCE.n424(), 
				INSTANCE.n425(), 
				INSTANCE.n426(), 
				INSTANCE.n427(), 
				INSTANCE.n428(), 
				INSTANCE.n429(), 
				INSTANCE.n443(), 
				INSTANCE.n444(), 
				INSTANCE.n445(), 
				INSTANCE.n446(), 
				INSTANCE.n447(),
				null);
				//@formatter:on

		public static List<DataResource> punk = Arrays.asList(
		//@formatter:off
				INSTANCE.p1(), 
				INSTANCE.p2(), 
				INSTANCE.p3(), 
				INSTANCE.p4(), 
				INSTANCE.p5(), 
				INSTANCE.p6(), 
				INSTANCE.p7(), 
				INSTANCE.p8(), 
				INSTANCE.p9(), 
				INSTANCE.p10(), 
				INSTANCE.p11(), 
				INSTANCE.p12(), 
				INSTANCE.p13(), 
				INSTANCE.p14(), 
				INSTANCE.p15(), 
				INSTANCE.p16(), 
				INSTANCE.p17(), 
				INSTANCE.p18(), 
				INSTANCE.p19(), 
				INSTANCE.p20(), 
				INSTANCE.p21(), 
				INSTANCE.p22(), 
				INSTANCE.p23(), 
				INSTANCE.p24(), 
				INSTANCE.p25(), 
				INSTANCE.p26(), 
				INSTANCE.p27(), 
				INSTANCE.p28(), 
				INSTANCE.p29(), 
				INSTANCE.p30(), 
				INSTANCE.p31(), 
				INSTANCE.p32(), 
				INSTANCE.p33(), 
				INSTANCE.p34(), 
				INSTANCE.p35(), 
				INSTANCE.p36(), 
				INSTANCE.p37(), 
				INSTANCE.p38(), 
				INSTANCE.p39(), 
				INSTANCE.p40()
				);
		//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		SIGN, AVERTISMENTS, ELETRIC_BOX, CANNAL, PUNK, DOOR

	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N08")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/text/hu/Punk")
	TextResource punkLines();

	@Source("hu/norbisquest/nq1/resources/walk/VRKFront")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S08_rockklub_front.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S08_vrkfront_mask_bottom.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S08_vrkfront_mask_top.png")
	ImageResource mask_bottom();

	@Source("hu/norbisquest/nq1/resources/images/stages/S08_rockklub_front_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/items/vrkkapu.png")
	ImageResource door();

	@Source("hu/norbisquest/nq1/resources/images/punk/punk04d.png")
	ImageResource punk();

	@Source("hu/norbisquest/nq1/resources/images/punk/punk04dnyul.png")
	ImageResource punk_grab();

	@Source("hu/norbisquest/nq1/resources/images/punk/punk04dtalk1.png")
	ImageResource punk_talk1();

	@Source("hu/norbisquest/nq1/resources/images/punk/punk04dtalk2.png")
	ImageResource punk_talk2();

	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N25.mp3")
	DataResource n25();

	@Source("hu/norbisquest/nq1/resources/speech/N26.mp3")
	DataResource n26();

	@Source("hu/norbisquest/nq1/resources/speech/N27.mp3")
	DataResource n27();

	@Source("hu/norbisquest/nq1/resources/speech/N28.mp3")
	DataResource n28();

	@Source("hu/norbisquest/nq1/resources/speech/N29.mp3")
	DataResource n29();

	@Source("hu/norbisquest/nq1/resources/speech/N30.mp3")
	DataResource n30();

	@Source("hu/norbisquest/nq1/resources/speech/N31.mp3")
	DataResource n31();

	@Source("hu/norbisquest/nq1/resources/speech/N32.mp3")
	DataResource n32();

	@Source("hu/norbisquest/nq1/resources/speech/N33.mp3")
	DataResource n33();

	@Source("hu/norbisquest/nq1/resources/speech/N34.mp3")
	DataResource n34();

	@Source("hu/norbisquest/nq1/resources/speech/N35.mp3")
	DataResource n35();

	@Source("hu/norbisquest/nq1/resources/speech/N36.mp3")
	DataResource n36();

	@Source("hu/norbisquest/nq1/resources/speech/N37.mp3")
	DataResource n37();

	@Source("hu/norbisquest/nq1/resources/speech/N38.mp3")
	DataResource n38();

	@Source("hu/norbisquest/nq1/resources/speech/N39.mp3")
	DataResource n39();

	@Source("hu/norbisquest/nq1/resources/speech/N40.mp3")
	DataResource n40();

	@Source("hu/norbisquest/nq1/resources/speech/N41.mp3")
	DataResource n41();

	@Source("hu/norbisquest/nq1/resources/speech/N42.mp3")
	DataResource n42();

	@Source("hu/norbisquest/nq1/resources/speech/N43.mp3")
	DataResource n43();

	@Source("hu/norbisquest/nq1/resources/speech/N44.mp3")
	DataResource n44();

	@Source("hu/norbisquest/nq1/resources/speech/N247.mp3")
	DataResource n247();

	@Source("hu/norbisquest/nq1/resources/speech/N248.mp3")
	DataResource n248();

	@Source("hu/norbisquest/nq1/resources/speech/N249.mp3")
	DataResource n249();

	@Source("hu/norbisquest/nq1/resources/speech/N250.mp3")
	DataResource n250();

	@Source("hu/norbisquest/nq1/resources/speech/N251.mp3")
	DataResource n251();

	@Source("hu/norbisquest/nq1/resources/speech/N252.mp3")
	DataResource n252();

	@Source("hu/norbisquest/nq1/resources/speech/N253.mp3")
	DataResource n253();

	@Source("hu/norbisquest/nq1/resources/speech/N254.mp3")
	DataResource n254();

	@Source("hu/norbisquest/nq1/resources/speech/N255.mp3")
	DataResource n255();

	@Source("hu/norbisquest/nq1/resources/speech/N256.mp3")
	DataResource n256();

	@Source("hu/norbisquest/nq1/resources/speech/N257.mp3")
	DataResource n257();

	@Source("hu/norbisquest/nq1/resources/speech/N258.mp3")
	DataResource n258();

	@Source("hu/norbisquest/nq1/resources/speech/N259.mp3")
	DataResource n259();

	@Source("hu/norbisquest/nq1/resources/speech/N260.mp3")
	DataResource n260();

	@Source("hu/norbisquest/nq1/resources/speech/N261.mp3")
	DataResource n261();

	@Source("hu/norbisquest/nq1/resources/speech/N311.mp3")
	DataResource n311();

	@Source("hu/norbisquest/nq1/resources/speech/N312.mp3")
	DataResource n312();

	@Source("hu/norbisquest/nq1/resources/speech/N313.mp3")
	DataResource n313();

	@Source("hu/norbisquest/nq1/resources/speech/N314.mp3")
	DataResource n314();

	@Source("hu/norbisquest/nq1/resources/speech/N315.mp3")
	DataResource n315();

	@Source("hu/norbisquest/nq1/resources/speech/N335.mp3")
	DataResource n335();

	@Source("hu/norbisquest/nq1/resources/speech/N336.mp3")
	DataResource n336();

	@Source("hu/norbisquest/nq1/resources/speech/N337.mp3")
	DataResource n337();

	@Source("hu/norbisquest/nq1/resources/speech/N338.mp3")
	DataResource n338();

	@Source("hu/norbisquest/nq1/resources/speech/N339.mp3")
	DataResource n339();

	@Source("hu/norbisquest/nq1/resources/speech/N340.mp3")
	DataResource n340();

	@Source("hu/norbisquest/nq1/resources/speech/N422.mp3")
	DataResource n422();

	@Source("hu/norbisquest/nq1/resources/speech/N423.mp3")
	DataResource n423();

	@Source("hu/norbisquest/nq1/resources/speech/N424.mp3")
	DataResource n424();

	@Source("hu/norbisquest/nq1/resources/speech/N425.mp3")
	DataResource n425();

	@Source("hu/norbisquest/nq1/resources/speech/N426.mp3")
	DataResource n426();

	@Source("hu/norbisquest/nq1/resources/speech/N427.mp3")
	DataResource n427();

	@Source("hu/norbisquest/nq1/resources/speech/N428.mp3")
	DataResource n428();

	@Source("hu/norbisquest/nq1/resources/speech/N429.mp3")
	DataResource n429();

	@Source("hu/norbisquest/nq1/resources/speech/N443.mp3")
	DataResource n443();

	@Source("hu/norbisquest/nq1/resources/speech/N444.mp3")
	DataResource n444();

	@Source("hu/norbisquest/nq1/resources/speech/N445.mp3")
	DataResource n445();

	@Source("hu/norbisquest/nq1/resources/speech/N446.mp3")
	DataResource n446();

	@Source("hu/norbisquest/nq1/resources/speech/N447.mp3")
	DataResource n447();

	@Source("hu/norbisquest/nq1/resources/speech/p1.mp3")
	DataResource p1();

	@Source("hu/norbisquest/nq1/resources/speech/p2.mp3")
	DataResource p2();

	@Source("hu/norbisquest/nq1/resources/speech/p3.mp3")
	DataResource p3();

	@Source("hu/norbisquest/nq1/resources/speech/p4.mp3")
	DataResource p4();

	@Source("hu/norbisquest/nq1/resources/speech/p5.mp3")
	DataResource p5();

	@Source("hu/norbisquest/nq1/resources/speech/p6.mp3")
	DataResource p6();

	@Source("hu/norbisquest/nq1/resources/speech/p7.mp3")
	DataResource p7();

	@Source("hu/norbisquest/nq1/resources/speech/p8.mp3")
	DataResource p8();

	@Source("hu/norbisquest/nq1/resources/speech/p9.mp3")
	DataResource p9();

	@Source("hu/norbisquest/nq1/resources/speech/p10.mp3")
	DataResource p10();

	@Source("hu/norbisquest/nq1/resources/speech/p11.mp3")
	DataResource p11();

	@Source("hu/norbisquest/nq1/resources/speech/p12.mp3")
	DataResource p12();

	@Source("hu/norbisquest/nq1/resources/speech/p13.mp3")
	DataResource p13();

	@Source("hu/norbisquest/nq1/resources/speech/p14.mp3")
	DataResource p14();

	@Source("hu/norbisquest/nq1/resources/speech/p15.mp3")
	DataResource p15();

	@Source("hu/norbisquest/nq1/resources/speech/p16.mp3")
	DataResource p16();

	@Source("hu/norbisquest/nq1/resources/speech/p17.mp3")
	DataResource p17();

	@Source("hu/norbisquest/nq1/resources/speech/p18.mp3")
	DataResource p18();

	@Source("hu/norbisquest/nq1/resources/speech/p19.mp3")
	DataResource p19();

	@Source("hu/norbisquest/nq1/resources/speech/p20.mp3")
	DataResource p20();

	@Source("hu/norbisquest/nq1/resources/speech/p21.mp3")
	DataResource p21();

	@Source("hu/norbisquest/nq1/resources/speech/p22.mp3")
	DataResource p22();

	@Source("hu/norbisquest/nq1/resources/speech/p23.mp3")
	DataResource p23();

	@Source("hu/norbisquest/nq1/resources/speech/p24.mp3")
	DataResource p24();

	@Source("hu/norbisquest/nq1/resources/speech/p25.mp3")
	DataResource p25();

	@Source("hu/norbisquest/nq1/resources/speech/p26.mp3")
	DataResource p26();

	@Source("hu/norbisquest/nq1/resources/speech/p27.mp3")
	DataResource p27();

	@Source("hu/norbisquest/nq1/resources/speech/p28.mp3")
	DataResource p28();

	@Source("hu/norbisquest/nq1/resources/speech/p29.mp3")
	DataResource p29();

	@Source("hu/norbisquest/nq1/resources/speech/p30.mp3")
	DataResource p30();

	@Source("hu/norbisquest/nq1/resources/speech/p31.mp3")
	DataResource p31();

	@Source("hu/norbisquest/nq1/resources/speech/p32.mp3")
	DataResource p32();

	@Source("hu/norbisquest/nq1/resources/speech/p33.mp3")
	DataResource p33();

	@Source("hu/norbisquest/nq1/resources/speech/p34.mp3")
	DataResource p34();

	@Source("hu/norbisquest/nq1/resources/speech/p35.mp3")
	DataResource p35();

	@Source("hu/norbisquest/nq1/resources/speech/p36.mp3")
	DataResource p36();

	@Source("hu/norbisquest/nq1/resources/speech/p37.mp3")
	DataResource p37();

	@Source("hu/norbisquest/nq1/resources/speech/p38.mp3")
	DataResource p38();

	@Source("hu/norbisquest/nq1/resources/speech/p39.mp3")
	DataResource p39();

	@Source("hu/norbisquest/nq1/resources/speech/p40.mp3")
	DataResource p40();

	@Source("hu/norbisquest/nq1/resources/sound/sound21.mp3")
    DataResource town_noise();

}