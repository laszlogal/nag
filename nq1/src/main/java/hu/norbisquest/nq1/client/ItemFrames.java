package hu.norbisquest.nq1.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public interface ItemFrames extends ClientBundle {
	ItemFrames INSTANCE = GWT.create(ItemFrames.class);

	class Data {
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
	}

	List<ImageResource> boom = Arrays.asList(
			INSTANCE.boom1(),
			INSTANCE.boom2(),
			INSTANCE.boom3(),
			INSTANCE.boom5(),
			INSTANCE.boom6(),
			INSTANCE.boom7(),
			INSTANCE.boom8(),
			INSTANCE.boom9(),
			INSTANCE.boom10(),
			INSTANCE.boom11(),
			INSTANCE.boom12(),
			INSTANCE.boom13(),
			INSTANCE.boom14(),
			INSTANCE.boom15(),
			INSTANCE.boom16()

	);

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
	ImageResource s01_drawer();

	@Source("hu/norbisquest/nq1/resources/images/items/wckefe.png")
	ImageResource toilet_brush();

	@Source("hu/norbisquest/nq1/resources/images/items/wcpapir.png")
	ImageResource toilet_paper();

	@Source("hu/norbisquest/nq1/resources/images/items/wclanc.png")
	ImageResource toilet_chain();

	@Source("hu/norbisquest/nq1/resources/images/items/szekrenyajto.png")
	ImageResource s01_wardrobe_door();

	@Source("hu/norbisquest/nq1/resources/images/items/sapka2.png")
	ImageResource s05_hobo_hat();

	@Source("hu/norbisquest/nq1/resources/images/items/ollo.png")
	ImageResource snips();

	@Source("hu/norbisquest/nq1/resources/images/items/troli1newmegy.png")
	ImageResource bus_going();

	@Source("hu/norbisquest/nq1/resources/images/items/troli2newmegy.png")
	ImageResource bus_coming_back();

	@Source("hu/norbisquest/nq1/resources/images/items/ajtoelso.png")
	ImageResource bus_front_door();

	@Source("hu/norbisquest/nq1/resources/images/items/ajtohatso.png")
	ImageResource bus_back_door();

	@Source("hu/norbisquest/nq1/resources/images/items/kabat02.png")
	ImageResource coat();

	@Source("hu/norbisquest/nq1/resources/images/items/kes02.png")
	ImageResource knife();

	@Source("hu/norbisquest/nq1/resources/images/items/marsal2.png")
	ImageResource marsal();

	@Source("hu/norbisquest/nq1/resources/images/items/marsal2egett.png")
	ImageResource marsal_burned();

	@Source("hu/norbisquest/nq1/resources/images/items/marsal_mask.png")
	ImageResource marsal_mask();

	@Source("hu/norbisquest/nq1/resources/images/items/uveg.png")
	ImageResource glass();

	@Source("hu/norbisquest/nq1/resources/images/boom/1.png")
	ImageResource boom1();

	@Source("hu/norbisquest/nq1/resources/images/boom/2.png")
	ImageResource boom2();

	@Source("hu/norbisquest/nq1/resources/images/boom/3.png")
	ImageResource boom3();

	@Source("hu/norbisquest/nq1/resources/images/boom/4.png")
	ImageResource boom4();

	@Source("hu/norbisquest/nq1/resources/images/boom/5.png")
	ImageResource boom5();

	@Source("hu/norbisquest/nq1/resources/images/boom/6.png")
	ImageResource boom6();

	@Source("hu/norbisquest/nq1/resources/images/boom/7.png")
	ImageResource boom7();

	@Source("hu/norbisquest/nq1/resources/images/boom/8.png")
	ImageResource boom8();

	@Source("hu/norbisquest/nq1/resources/images/boom/9.png")
	ImageResource boom9();

	@Source("hu/norbisquest/nq1/resources/images/boom/10.png")
	ImageResource boom10();

	@Source("hu/norbisquest/nq1/resources/images/boom/11.png")
	ImageResource boom11();

	@Source("hu/norbisquest/nq1/resources/images/boom/12.png")
	ImageResource boom12();

	@Source("hu/norbisquest/nq1/resources/images/boom/13.png")
	ImageResource boom13();

	@Source("hu/norbisquest/nq1/resources/images/boom/14.png")
	ImageResource boom14();

	@Source("hu/norbisquest/nq1/resources/images/boom/15.png")
	ImageResource boom15();

	@Source("hu/norbisquest/nq1/resources/images/boom/16.png")
	ImageResource boom16();

}