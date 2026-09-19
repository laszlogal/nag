package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

import hu.norbisquest.nagbase.game.target.HotSpot;

public interface Toilet extends ClientBundle {
	Toilet INSTANCE = GWT.create(Toilet.class);

	class Data {

		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n165(), 
				INSTANCE.n166(), 
				INSTANCE.n167(), 
				INSTANCE.n168(), 
				INSTANCE.n169(), 
				INSTANCE.n170(), 
				INSTANCE.n171(), 
				INSTANCE.n172(), 
				INSTANCE.n173(), 
				INSTANCE.n174(), 
				INSTANCE.n175(), 
				INSTANCE.n176(), 
				INSTANCE.n177(), 
				INSTANCE.n178(), 
				INSTANCE.n179(), 
				INSTANCE.n180(), 
				INSTANCE.n181(), 
				INSTANCE.n182(), 
				INSTANCE.n183(), 
				INSTANCE.n184(), 
				INSTANCE.n185(), 
				INSTANCE.n186(), 
				INSTANCE.n187(), 
				INSTANCE.n188(), 
				INSTANCE.n189(), 
				INSTANCE.n190(), 
				INSTANCE.n191());
				//@formatter:on

	}

	enum HotSpots implements HotSpot.Id {
		DOOR, LAMP, SWITCH, PAPER, PAPER_HOLDER, BRUSH, CHAIN, TOILET, TANK
	}

	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/N03")
	TextResource nLines();

	@Source("hu/norbisquest/nq1/resources/walk/Toilet")
	TextResource graph();

	// images
	@Source("hu/norbisquest/nq1/resources/images/stages/S03_wc.png")
	ImageResource background();

	@Source("hu/norbisquest/nq1/resources/images/stages/S03_wc_mask.png")
	ImageResource mask_top();

	@Source("hu/norbisquest/nq1/resources/images/stages/S03_wc_hotspots.png")
	ImageResource hotspots();

	@Source("hu/norbisquest/nq1/resources/images/items/wckefe.png")
	ImageResource toilet_brush();

	@Source("hu/norbisquest/nq1/resources/images/items/wcpapir.png")
	ImageResource toilet_paper();

	@Source("hu/norbisquest/nq1/resources/images/items/wclanc.png")
	ImageResource toilet_chain();

	@Source("hu/norbisquest/nq1/resources/speech/N165.mp3")
	DataResource n165();

	@Source("hu/norbisquest/nq1/resources/speech/N166.mp3")
	DataResource n166();

	@Source("hu/norbisquest/nq1/resources/speech/N167.mp3")
	DataResource n167();

	@Source("hu/norbisquest/nq1/resources/speech/N168.mp3")
	DataResource n168();

	@Source("hu/norbisquest/nq1/resources/speech/N169.mp3")
	DataResource n169();

	@Source("hu/norbisquest/nq1/resources/speech/N170.mp3")
	DataResource n170();

	@Source("hu/norbisquest/nq1/resources/speech/N171.mp3")
	DataResource n171();

	@Source("hu/norbisquest/nq1/resources/speech/N172.mp3")
	DataResource n172();

	@Source("hu/norbisquest/nq1/resources/speech/N173.mp3")
	DataResource n173();

	@Source("hu/norbisquest/nq1/resources/speech/N174.mp3")
	DataResource n174();

	@Source("hu/norbisquest/nq1/resources/speech/N175.mp3")
	DataResource n175();

	@Source("hu/norbisquest/nq1/resources/speech/N176.mp3")
	DataResource n176();

	@Source("hu/norbisquest/nq1/resources/speech/N177.mp3")
	DataResource n177();

	@Source("hu/norbisquest/nq1/resources/speech/N178.mp3")
	DataResource n178();

	@Source("hu/norbisquest/nq1/resources/speech/N179.mp3")
	DataResource n179();

	@Source("hu/norbisquest/nq1/resources/speech/N180.mp3")
	DataResource n180();

	@Source("hu/norbisquest/nq1/resources/speech/N181.mp3")
	DataResource n181();

	@Source("hu/norbisquest/nq1/resources/speech/N182.mp3")
	DataResource n182();

	@Source("hu/norbisquest/nq1/resources/speech/N183.mp3")
	DataResource n183();

	@Source("hu/norbisquest/nq1/resources/speech/N184.mp3")
	DataResource n184();

	@Source("hu/norbisquest/nq1/resources/speech/N185.mp3")
	DataResource n185();

	@Source("hu/norbisquest/nq1/resources/speech/N186.mp3")
	DataResource n186();

	@Source("hu/norbisquest/nq1/resources/speech/N187.mp3")
	DataResource n187();

	@Source("hu/norbisquest/nq1/resources/speech/N188.mp3")
	DataResource n188();

	@Source("hu/norbisquest/nq1/resources/speech/N189.mp3")
	DataResource n189();

	@Source("hu/norbisquest/nq1/resources/speech/N190.mp3")
	DataResource n190();

	@Source("hu/norbisquest/nq1/resources/speech/N191.mp3")
	DataResource n191();

	@Source("hu/norbisquest/nq1/resources/sound/sound27.mp3")
    DataResource cutting_chain();

}