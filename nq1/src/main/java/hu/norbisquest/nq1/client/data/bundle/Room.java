package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface Room extends ClientBundle {
	Room INSTANCE = GWT.create(Room.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				Room.INSTANCE.n68(),
				Room.INSTANCE.n69(),
				Room.INSTANCE.n70(),
				Room.INSTANCE.n71(),
				Room.INSTANCE.n72(),
				Room.INSTANCE.n73(),
				Room.INSTANCE.n74(),
				Room.INSTANCE.n75(),
				Room.INSTANCE.n76(),
				Room.INSTANCE.n77(),
				Room.INSTANCE.n78(),
				Room.INSTANCE.n79(),
				Room.INSTANCE.n80(),
				Room.INSTANCE.n81(),
				Room.INSTANCE.n82(),
				Room.INSTANCE.n83(),
				Room.INSTANCE.n84(),
				Room.INSTANCE.n85(),
				Room.INSTANCE.n86(),
				Room.INSTANCE.n87(),
				Room.INSTANCE.n88(),
				Room.INSTANCE.n89(),
				Room.INSTANCE.n90(),
				Room.INSTANCE.n91(),
				Room.INSTANCE.n92(),
				Room.INSTANCE.n93(),
				Room.INSTANCE.n94(),
				Room.INSTANCE.n95(),
				Room.INSTANCE.n96(),
				Room.INSTANCE.n97(),
				Room.INSTANCE.n98(),
				Room.INSTANCE.n99(),
				Room.INSTANCE.n100(),
				Room.INSTANCE.n101(),
				Room.INSTANCE.n102(),
				Room.INSTANCE.n103(),
				Room.INSTANCE.n104(),
				Room.INSTANCE.n105(),
				Room.INSTANCE.n106(),
				Room.INSTANCE.n107(),
				Room.INSTANCE.n108()
		);

		public static List<ImageResource> tv_program = Arrays.asList(
				INSTANCE.tv_program1(),
				INSTANCE.tv_program2(),
				INSTANCE.tv_program3(),
				INSTANCE.tv_program4(),
				INSTANCE.tv_program5(),
				INSTANCE.tv_program6(),
				INSTANCE.tv_program6(),
				INSTANCE.tv_program6(),
				INSTANCE.tv_program6(),
				INSTANCE.tv_program7(),
				INSTANCE.tv_program8(),
				INSTANCE.tv_program9(),
				INSTANCE.tv_program10(),
				INSTANCE.tv_program11(),
				INSTANCE.tv_program7(),
				INSTANCE.tv_program8(),
				INSTANCE.tv_program9(),
				INSTANCE.tv_program10(),
				INSTANCE.tv_program11(),
				INSTANCE.tv_program7(),
				INSTANCE.tv_program8(),
				INSTANCE.tv_program9(),
				INSTANCE.tv_program10(),
				INSTANCE.tv_program11()
				);
		//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		WINDOW, POSTER, DVD_PLAYER, TV, BED, POWERSTRIP, WARDROBE, WARDROBE_INSIDE, WARDROBE_DOOR, BOOKS, BOX, PICTURE, DESK, DRAWER, DOOR

	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N01")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/walk/Room")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S01_room.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S01_room_mask_top.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S01_room_mask_bottom.png")
	ImageResource mask_bottom();

	@Source("hu/norbisquest/nq1/resources/images/stages/S01_room_mask_hotspot.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/items/szekrenyajto.png")
	ImageResource wardrobe_door();

	@Source("hu/norbisquest/nq1/resources/images/items/musor1a.png")
	ImageResource tv_program1();

	@Source("hu/norbisquest/nq1/resources/images/items/musor1b.png")
	ImageResource tv_program2();

	@Source("hu/norbisquest/nq1/resources/images/items/musor1c.png")
	ImageResource tv_program3();

	@Source("hu/norbisquest/nq1/resources/images/items/musor1d.png")
	ImageResource tv_program4();

	@Source("hu/norbisquest/nq1/resources/images/items/musor1e.png")
	ImageResource tv_program5();

	@Source("hu/norbisquest/nq1/resources/images/items/musor2.png")
	ImageResource tv_program6();

	@Source("hu/norbisquest/nq1/resources/images/items/musor3a.png")
	ImageResource tv_program7();

	@Source("hu/norbisquest/nq1/resources/images/items/musor3b.png")
	ImageResource tv_program8();

	@Source("hu/norbisquest/nq1/resources/images/items/musor3c.png")
	ImageResource tv_program9();

	@Source("hu/norbisquest/nq1/resources/images/items/musor3d.png")
	ImageResource tv_program10();

	@Source("hu/norbisquest/nq1/resources/images/items/musor3e.png")
	ImageResource tv_program11();

	@Source("hu/norbisquest/nq1/resources/images/items/01_fiok1.png")
	ImageResource drawer();

	// sounds
	@Source("hu/norbisquest/nq1/resources/speech/N68.mp3")
	DataResource n68();

	@Source("hu/norbisquest/nq1/resources/speech/N69.mp3")
	DataResource n69();

	@Source("hu/norbisquest/nq1/resources/speech/N70.mp3")
	DataResource n70();

	@Source("hu/norbisquest/nq1/resources/speech/N71.mp3")
	DataResource n71();

	@Source("hu/norbisquest/nq1/resources/speech/N72.mp3")
	DataResource n72();

	@Source("hu/norbisquest/nq1/resources/speech/N73.mp3")
	DataResource n73();

	@Source("hu/norbisquest/nq1/resources/speech/N74.mp3")
	DataResource n74();

	@Source("hu/norbisquest/nq1/resources/speech/N75.mp3")
	DataResource n75();

	@Source("hu/norbisquest/nq1/resources/speech/N76.mp3")
	DataResource n76();

	@Source("hu/norbisquest/nq1/resources/speech/N77.mp3")
	DataResource n77();

	@Source("hu/norbisquest/nq1/resources/speech/N78.mp3")
	DataResource n78();

	@Source("hu/norbisquest/nq1/resources/speech/N79.mp3")
	DataResource n79();

	@Source("hu/norbisquest/nq1/resources/speech/N80.mp3")
	DataResource n80();

	@Source("hu/norbisquest/nq1/resources/speech/N81.mp3")
	DataResource n81();

	@Source("hu/norbisquest/nq1/resources/speech/N82.mp3")
	DataResource n82();

	@Source("hu/norbisquest/nq1/resources/speech/N83.mp3")
	DataResource n83();

	@Source("hu/norbisquest/nq1/resources/speech/N84.mp3")
	DataResource n84();

	@Source("hu/norbisquest/nq1/resources/speech/N85.mp3")
	DataResource n85();

	@Source("hu/norbisquest/nq1/resources/speech/N86.mp3")
	DataResource n86();

	@Source("hu/norbisquest/nq1/resources/speech/N87.mp3")
	DataResource n87();

	@Source("hu/norbisquest/nq1/resources/speech/N88.mp3")
	DataResource n88();

	@Source("hu/norbisquest/nq1/resources/speech/N89.mp3")
	DataResource n89();

	@Source("hu/norbisquest/nq1/resources/speech/N90.mp3")
	DataResource n90();

	@Source("hu/norbisquest/nq1/resources/speech/N91.mp3")
	DataResource n91();

	@Source("hu/norbisquest/nq1/resources/speech/N92.mp3")
	DataResource n92();

	@Source("hu/norbisquest/nq1/resources/speech/N93.mp3")
	DataResource n93();

	@Source("hu/norbisquest/nq1/resources/speech/N94.mp3")
	DataResource n94();

	@Source("hu/norbisquest/nq1/resources/speech/N95.mp3")
	DataResource n95();

	@Source("hu/norbisquest/nq1/resources/speech/N96.mp3")
	DataResource n96();

	@Source("hu/norbisquest/nq1/resources/speech/N97.mp3")
	DataResource n97();

	@Source("hu/norbisquest/nq1/resources/speech/N98.mp3")
	DataResource n98();

	@Source("hu/norbisquest/nq1/resources/speech/N99.mp3")
	DataResource n99();

	@Source("hu/norbisquest/nq1/resources/speech/N100.mp3")
	DataResource n100();

	@Source("hu/norbisquest/nq1/resources/speech/N101.mp3")
	DataResource n101();

	@Source("hu/norbisquest/nq1/resources/speech/N102.mp3")
	DataResource n102();

	@Source("hu/norbisquest/nq1/resources/speech/N103.mp3")
	DataResource n103();

	@Source("hu/norbisquest/nq1/resources/speech/N104.mp3")
	DataResource n104();

	@Source("hu/norbisquest/nq1/resources/speech/N105.mp3")
	DataResource n105();

	@Source("hu/norbisquest/nq1/resources/speech/N106.mp3")
	DataResource n106();

	@Source("hu/norbisquest/nq1/resources/speech/N107.mp3")
	DataResource n107();

	@Source("hu/norbisquest/nq1/resources/speech/N108.mp3")
	DataResource n108();

	@Source("hu/norbisquest/nq1/resources/sound/sound2.mp3")
    DataResource wardrobe_sound();

	@Source("hu/norbisquest/nq1/resources/sound/sound7.mp3")
    DataResource walk_carpet1();

	@Source("hu/norbisquest/nq1/resources/sound/sound8.mp3")
    DataResource walk_carpet2();

	@Source("hu/norbisquest/nq1/resources/sound/sound30.mp3")
    DataResource tv_sound();

	@Source("hu/norbisquest/nq1/resources/sound/sound3.mp3")
    DataResource drawer_sound();

}