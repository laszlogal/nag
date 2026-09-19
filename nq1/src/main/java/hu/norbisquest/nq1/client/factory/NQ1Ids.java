package hu.norbisquest.nq1.client.factory;

import hu.norbisquest.nagbase.game.Stage.Id;

public enum NQ1Ids implements Id {
	//@formatter:off
	TestRoom,
	TestScreen,
	Splash1,
	Splash2,
	Splash3,
	MainMenu,
	Setup,
	PoorNorbi,
	Room, 
	Corridor,
	TOILET,
	HouseFront,
	NewsStand,
	BusStop, 
	BusFront, 
	BusBack, 
	VRKFront,
	RockKlub,
	RehearsalRoom,
	PlayBass,
	Credits,
	Actors,
	End,
	None,
	Exit;
	//@formatter:on

    @Override
	public Id toId(String name) {
		return null;
	}
}
