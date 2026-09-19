package hu.norbisquest.nq1.client.factory;

import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.engine.ScreenParent;
import hu.norbisquest.nq1.client.game.screens.NQ1ScreenFactory;

public class NQ1StageFactory {

	public static void createStage(final Stage.Id id, final ScreenParent parent) {
		createStage((NQ1Ids)id, parent);
	}

	public static void createStage(final NQ1Ids id, final ScreenParent parent) {
		switch (id) {
		case TestRoom:
			TestRoom.create(parent);
			return;

		case TestScreen:
			TestScreen.create(parent);
			return;
		case Room:
			Room.create(parent);
			return;
		case Corridor:
			Corridor.create(parent);
			return;
		case TOILET:
			Toilet.create(parent);
			return;
		case HouseFront:
			HouseFront.create(parent);
			return;
		case NewsStand:
			NewsStand.create(parent);
			return;
		case BusStop:
			BusStop.create(parent);
			break;
		case BusFront:
			BusFront.create(parent);
			break;

		case BusBack:
			BusBack.create(parent);
			break;

		case VRKFront:
			VRKFront.create(parent);
			break;

		case RockKlub:
			RockKlub.create(parent);
			break;
		case RehearsalRoom:
			RehearsalRoom.create(parent);
			break;
		case PlayBass:
			PlayBass.create(parent);
			break;
		default:
			NQ1ScreenFactory.createScreen(id, parent);
		}
	}

}
