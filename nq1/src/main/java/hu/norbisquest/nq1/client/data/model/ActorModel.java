package hu.norbisquest.nq1.client.data.model;

import hu.norbisquest.nagbase.game.ActorData;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nq1.client.data.bundle.BusFront;
import hu.norbisquest.nq1.client.data.bundle.NewsStand;
import hu.norbisquest.nq1.client.data.bundle.RockKlub;
import hu.norbisquest.nq1.client.data.bundle.VRKFront;
import hu.norbisquest.nq1.client.game.actors.NorbiText;

public class ActorModel {
	public enum Ids implements ActorTarget.ActorId {
		NORBI,
		NEWSAGENT,
		HOBO,
		BUSDRIVER,
		PUNK,
		SZURDI
	}

	public static ActorData getNorbi() {
		ActorData res = new ActorData(Ids.NORBI,"Norbi", 0, 0);
		res.addText("HU", NorbiText.lines);
		res.textStyleName = "norbi";
		res.addAudio("HU", null);// NorbiResources.norbi);
		return res;
	}

	public static ActorData getNewsAgent() {
		ActorData res = new ActorData(Ids.NEWSAGENT,"Újságos néni", 416, 111);
		res.scale = 1.0;
		res.textX = 379;
		res.textY = 90;
		res.textStyleName = "newsAgent";
		res.lines = NewsStand.INSTANCE.newsAgentLines();
		res.addAudio("HU", NewsStand.Data.newsAgent);
		return res;
	}

	public static ActorData getHobo() {
		ActorData res = new ActorData(Ids.HOBO,"Hobo", 170, 155);
		res.scale = 1.0;
		res.textX = 140;
		res.textY = 120;
		res.textStyleName = "hobo";
		res.lines = NewsStand.INSTANCE.hoboLines();
		res.addAudio("HU", NewsStand.Data.hobo);
		return res;
	}

	public static ActorData getBusDriver() {
		ActorData res = new ActorData(Ids.BUSDRIVER,"busDriver", 290, 82);
		res.scale = 1.0;
		res.textX = 250;
		res.textY = 50;
		res.textStyleName = "busDriver";
		res.lines = BusFront.INSTANCE.busDriverLines();
		res.addAudio("HU", BusFront.Data.busDriver);
		return res;
	}

	public static ActorData getPunk() {
		ActorData res = new ActorData(Ids.PUNK,"punk", 490, 162);
		res.scale = 1.0;
		res.textX = 500;
		res.textY = 132;
		res.textStyleName = "punk";
		res.lines = VRKFront.INSTANCE.punkLines();
		res.addAudio("HU", VRKFront.Data.punk);
		return res;
	}

	public static ActorData getSzurdi() {
		ActorData res = new ActorData(Ids.SZURDI,"szurdi", 366, 168);
		res.scale = 1.0;
		res.textX = 350;
		res.textY = 110;
		res.textStyleName = "szurdi";
		res.lines = RockKlub.INSTANCE.szurdiLines();
		res.addAudio("HU", RockKlub.Data.szurdi);
		return res;
	}

}
