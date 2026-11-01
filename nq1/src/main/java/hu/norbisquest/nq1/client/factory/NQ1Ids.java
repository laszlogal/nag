package hu.norbisquest.nq1.client.factory;

import hu.norbisquest.nagbase.common.engine.ScreenId;
import hu.norbisquest.nagbase.game.engine.ScreenCreator;

public enum NQ1Ids implements ScreenId {
	//@formatter:off
	TestRoom(hu.norbisquest.nq1.client.factory.TestRoom::create),
	TestScreen(hu.norbisquest.nq1.client.factory.TestScreen::create),
	Splash1(hu.norbisquest.nq1.client.game.screens.Splash1::create),
	Splash2(hu.norbisquest.nq1.client.game.screens.Splash2::create),
	Splash3(hu.norbisquest.nq1.client.game.screens.Splash3::create),
	MainMenu(hu.norbisquest.nq1.client.game.screens.MainMenu::create),
	Setup(hu.norbisquest.nq1.client.game.screens.Setup::create),
	PoorNorbi(hu.norbisquest.nq1.client.game.screens.PoorNorbi::create),
	Room(hu.norbisquest.nq1.client.factory.Room::create),
	Corridor(hu.norbisquest.nq1.client.factory.Corridor::create),
	TOILET(hu.norbisquest.nq1.client.factory.Toilet::create),
	HouseFront(hu.norbisquest.nq1.client.factory.HouseFront::create),
	NewsStand(hu.norbisquest.nq1.client.factory.NewsStand::create),
	BusStop(hu.norbisquest.nq1.client.factory.BusStop::create),
	BusFront(hu.norbisquest.nq1.client.factory.BusFront::create),
	BusBack(hu.norbisquest.nq1.client.factory.BusBack::create),
	VRKFront(hu.norbisquest.nq1.client.factory.VRKFront::create),
	RockKlub(hu.norbisquest.nq1.client.factory.RockKlub::create),
	RehearsalRoom(hu.norbisquest.nq1.client.factory.RehearsalRoom::create),
	PlayBass(hu.norbisquest.nq1.client.factory.PlayBass::create),
	Credits(hu.norbisquest.nq1.client.game.screens.Credits::create),
	Actors(hu.norbisquest.nq1.client.game.screens.ActorVoices::create),
	End(hu.norbisquest.nq1.client.game.screens.TheEnd::create),
	None(null),
	Exit(hu.norbisquest.nq1.client.game.screens.Exit::create);
	//@formatter:on

    private final ScreenCreator screenCreator;

    NQ1Ids(ScreenCreator screenCreator) {
        this.screenCreator = screenCreator;
    }

	public ScreenCreator screenCreator() {
		return screenCreator;
	}

}
