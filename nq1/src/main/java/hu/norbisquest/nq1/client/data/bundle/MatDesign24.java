package hu.norbisquest.nq1.client.data.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;

public interface MatDesign24 extends MatDesign {

	MatDesign24 INSTANCE = GWT.create(MatDesign24.class);

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/look.png")
	ImageResource examine_icon();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/use.png")
	ImageResource use_icon();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/bag.png")
	ImageResource toolbar_inventory();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/sound_on.png")
	ImageResource toolbar_music_on();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/sound_off.png")
	ImageResource toolbar_music_off();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/fullscreen.png")
	ImageResource toolbar_fullscreen();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/fullscreen_exit.png")
	ImageResource toolbar_fullscreen_exit();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/ok.png")
	ImageResource dlg_button_ok();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/cancel.png")
	ImageResource dlg_button_cancel();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/menu_black.png")
	ImageResource burger_black();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/menu_white.png")
	ImageResource burger_white();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/open.png")
	ImageResource burger_load();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/save.png")
	ImageResource burger_save();

	@Source("hu/norbisquest/nq1/resources/images/icons/material/24px/exit.png")
	ImageResource burger_exit();
}
