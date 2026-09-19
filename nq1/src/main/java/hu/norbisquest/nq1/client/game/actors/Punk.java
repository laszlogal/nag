package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.common.engine.ScreenListener;
import hu.norbisquest.nagbase.game.SpeechEnum;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.VRKFront;
import hu.norbisquest.nq1.client.data.model.ActorModel;
import hu.norbisquest.nq1.client.data.speech.N08;
import hu.norbisquest.nq1.client.data.speech.P;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Punk extends ActorTarget {

	private static final int NORMAL = 0;
	static final int TALK = 1;
	private static final int GRAB = 2;

	private static Punk INSTANCE = null;
	private ScreenListener listener;
	private HotSpot hotSpot;

	public static class Cmd extends hu.norbisquest.nagbase.game.command.Cmd {
		public static Command openConversation() {
			return get().openConversation();
		}

		static Command face(final int animId) {
			return new OneShotCommand() {

				@Override
				public void start() {
					get().changeAnimation(animId);
				}
			};
		}

	}

	private Punk() {
		super(ActorModel.getPunk());
	}

	@Override
	protected void addAnimations() {
		setTalkAnimation(VRKFront.INSTANCE.punk(), VRKFront.Data.punk_talk);
		addAnimation(GRAB, VRKFront.INSTANCE.punk_grab());
		changeAnimation(NORMAL);
		stop();

	}

	Collection<Command> getCigaretteAnim(SpeechEnum sNorbi, SpeechEnum sPunk, final int count) {
		return Arrays.asList(
				Norbi.say(sNorbi),
				Norbi.Cmd.face(Norbi.GIVE_CIGARETTE),
				Punk.Cmd.pause(20),
				Punk.Cmd.face(GRAB),
				Punk.Cmd.pause(20),
				Norbi.Cmd.face(Norbi.TALK_RIGHT),
				say(sPunk),
				Punk.Cmd.face(NORMAL),
				new OneShotCommand() {

					@Override
					public void start() {
						NQ1Settings.get().setCigaretteToPunk(count);

					}
				});
	}


	@Override
    protected Conversation createConversation() {
      	return new PunkConversation(this, Norbi.get(), this);
	}

    private static final int MAX_CIGS = 2;

    public Collection<Command> giveCigarette() {
        int count = NQ1Settings.get().getCigaretteToPunk();
        ArrayList<Command> list = new ArrayList<>();
        if (count == MAX_CIGS) {
            list.add(Norbi.say(N08.RUNNING_OUT_OF));
        } else if (count == 1) {
            list.add(Norbi.say(N08.ANOTHER_CIG));
            list.addAll(getCigaretteAnim(N08.ANOTHER_CIG, P.ALWAYS_THANS, MAX_CIGS));
        } else if (NQ1Settings.get().isPunkHelps()) {
            NQ1Settings.get().setArticleTold(true);
            list.addAll(getCigaretteAnim(N08.GET_CIG_FOR_YOU, P.GREAT_THANKS, 1));
            list.addAll(Arrays.asList(
                    Norbi.say(N08.HELP_THEN),
                    say(P.SURE_HELP),
                    say(P.HEARD_ARTICLE),
                    say(P.SZURDI_MAY_INTERESTED),
                    Norbi.say(N08.THANKS_FOR_INFO),
                    say(P.THANKS_FOR_CIG)));

        } else {
            list.addAll(getCigaretteAnim(N08.A_CIG, P.SURE_THANKS, 1));

        }
        return list;
    }
        public ScreenListener getListener() {
		return listener;
	}

	public void setListener(ScreenListener listener) {
		this.listener = listener;
	}

	public HotSpot getHotSpot() {
		return hotSpot;
	}

	public void setHotSpot(HotSpot hotSpot) {
		this.hotSpot = hotSpot;
	}


	public static Punk get() {
		if (INSTANCE == null) {
			INSTANCE = new Punk();
		}
		return INSTANCE;
	}

	@Override
	protected void prepareForStage() {
		//
	}

	public static void destroyInstance() {
		INSTANCE.destroy();
		INSTANCE = null;
	}
}