package hu.norbisquest.nq1.client.data.bundle;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface Inventory extends ClientBundle {
	Inventory INSTANCE = GWT.create(Inventory.class);

	class Data {
		public static List<DataResource> norbi = Arrays.asList(
				//@formatter:off
				INSTANCE.n288(), 
				INSTANCE.n289(), 
				INSTANCE.n290(), 
				INSTANCE.n291(), 
				INSTANCE.n292(), 
				INSTANCE.n293(), 
				INSTANCE.n294(), 
				INSTANCE.n295(), 
				INSTANCE.n296(), 
				INSTANCE.n297(), 
				INSTANCE.n389(), 
				INSTANCE.n390(), 
				INSTANCE.n391(), 
				INSTANCE.n392(), 
				INSTANCE.n393(), 
				INSTANCE.n394(), 
				INSTANCE.n395(), 
				INSTANCE.n396(), 
				INSTANCE.n397(), 
				INSTANCE.n398(), 
				INSTANCE.n399(), 
				INSTANCE.n400(), 
				INSTANCE.n401(), 
				INSTANCE.n403());
		//@formatter:off
	}
	// text
	@Source("hu/norbisquest/nq1/resources/text/hu/INV")
	TextResource nLines();
	
	// audio
	@Source("hu/norbisquest/nq1/resources/speech/N288.mp3")
	DataResource n288();

	@Source("hu/norbisquest/nq1/resources/speech/N289.mp3")
	DataResource n289();

	@Source("hu/norbisquest/nq1/resources/speech/N290.mp3")
	DataResource n290();

	@Source("hu/norbisquest/nq1/resources/speech/N291.mp3")
	DataResource n291();

	@Source("hu/norbisquest/nq1/resources/speech/N292.mp3")
	DataResource n292();

	@Source("hu/norbisquest/nq1/resources/speech/N293.mp3")
	DataResource n293();

	@Source("hu/norbisquest/nq1/resources/speech/N294.mp3")
	DataResource n294();

	@Source("hu/norbisquest/nq1/resources/speech/N295.mp3")
	DataResource n295();

	@Source("hu/norbisquest/nq1/resources/speech/N296.mp3")
	DataResource n296();

	@Source("hu/norbisquest/nq1/resources/speech/N297.mp3")
	DataResource n297();
	
	@Source("hu/norbisquest/nq1/resources/speech/N389.mp3")
	DataResource n389();

	@Source("hu/norbisquest/nq1/resources/speech/N390.mp3")
	DataResource n390();

	@Source("hu/norbisquest/nq1/resources/speech/N391.mp3")
	DataResource n391();

	@Source("hu/norbisquest/nq1/resources/speech/N392.mp3")
	DataResource n392();

	@Source("hu/norbisquest/nq1/resources/speech/N393.mp3")
	DataResource n393();
	
	@Source("hu/norbisquest/nq1/resources/speech/N394.mp3")
	DataResource n394();

	@Source("hu/norbisquest/nq1/resources/speech/N395.mp3")
	DataResource n395();
	
	@Source("hu/norbisquest/nq1/resources/speech/N396.mp3")
	DataResource n396();

	@Source("hu/norbisquest/nq1/resources/speech/N397.mp3")
	DataResource n397();
	
	@Source("hu/norbisquest/nq1/resources/speech/N398.mp3")
	DataResource n398();
	
	@Source("hu/norbisquest/nq1/resources/speech/N399.mp3")
	DataResource n399();
	
	@Source("hu/norbisquest/nq1/resources/speech/N400.mp3")
	DataResource n400();

	@Source("hu/norbisquest/nq1/resources/speech/N401.mp3")
	DataResource n401();
	
	@Source("hu/norbisquest/nq1/resources/speech/N403.mp3")
	DataResource n403();


	@Source("hu/norbisquest/nq1/resources/images/items/inventory/malacpersely.png")
	ImageResource sleeve();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/kulcs.png")
	ImageResource sleeve_key();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/wckefe.png")
	ImageResource toilet_brush();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/wcpapir.png")
	ImageResource toilet_paper();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/wclanc.png")
	ImageResource toilet_chain();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/jegy.png")
	ImageResource ticket();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/kezelt_jegy.png")
	ImageResource used_ticket();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/cigi.png")
	ImageResource cigarette();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/lemezvago.png")
	ImageResource snips();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/penz.png")
	ImageResource money();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/pia.png")
	ImageResource alcohol();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/ujsag.png")
	ImageResource newspaper();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/szendvics.png")
	ImageResource sandwitch();

	@Source("hu/norbisquest/nq1/resources/images/items/inventory/biztositek.png")
	ImageResource fuse();

}