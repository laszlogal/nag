package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N08;
import hu.norbisquest.nq1.client.data.speech.P;

import java.util.List;

class PunkConversation extends Conversation {
    enum Topics {YO, GOAWAY, HELP, MONEY, CHAIN, BYE}

    PunkConversation(Punk punk, Actor questioner, Punk partner) {
        super(questioner, partner);
    }

@Override
protected Enum toId(String s) {
        return Topics.valueOf(s);
}

@Override
    public void createTopics() {
        Norbi.get().setTalkBase(Norbi.TALK_RIGHT);
        addTopic(Topics.YO, N08.YO_WHATSUP, this::yo,true);
        addTopic(Topics.GOAWAY, N08.GET_OFF_DIRTY_PUNK, this::goAway, true);
        addTopic(Topics.HELP, N08.HELP, this::help, false);
        addTopic(Topics.MONEY, N08.SOME_MONEY_PLEASE, this::money, false);
        addTopic(Topics.CHAIN, N08.SZURDI_LOOKING_FOR_CHAIN, this::chain, false);
        addBye(Topics.BYE, N08.BYE, this::bye,true);

    }

    @Override
    protected void activteExternalTopics() {
        NQ1Settings s = NQ1Settings.get();
        if (s.getAskAboutKnife()) {
            setTopicActive(PunkConversation.Topics.HELP, true);
        } else {
            setTopicActive(PunkConversation.Topics.HELP, false);

        }

        if (s.isChainNeeded() && !s.getAskAboutChain()) {
            setTopicActive(PunkConversation.Topics.CHAIN, true);
        }

    }

    private List<Command> yo() {
        return Punk.Cmd.asList(
                partnerSay(P.WHO_THE_HELL_ARE_YOU),
                heroSay(N08.IM_NORBI),
                partnerSay(P.NO_FITNESS),
                heroSay(N08.ANOTHER_NORBI),
                partnerSay(P.WHAT_DO_YOU_WANT),
                heroSay(N08.JUST_SAY_HELLO),
                partnerSay(P.HI_THEN),
                cmdDisableTopic(Topics.YO));
    }

    private List<Command> goAway() {
        return Punk.Cmd.list(heroSay(N08.GET_OFF_DIRTY_PUNK),
                partnerSay(P.BEAT_EH),
                heroSay(N08.HIT_ME),
                partnerSay(P.CALL_HOSPITAL_FOR_BED),
                heroSay(N08.NO_TIME_FOR_THIS),
                partnerSay(P.AFRAID_EH),
                heroSay(N08.NO_SG_TODO),
                partnerSay(P.OF_COURSE),
                cmdDisableTopic(Topics.YO, Topics.GOAWAY));
    }

    private List<Command> help() {
        return Punk.Cmd.asList(
                heroSay(N08.HELP_PLEASE),
                partnerSay(P.NO_WAY_LEGEND),
                heroSay(N08.LEGEND),
                partnerSay(P.DONT_YOU_HEARD),
                heroSay(N08.NOT_THIS_WAY),
                heroSay(N08.NEVERMIND),
                new OneShotCommand() {

                    @Override
                    public void start() {
                        NQ1Settings.get().setAskAboutKnife(true);

                    }
                },
                cmdDisableTopic(Topics.HELP));
    }

    private List<Command> money() {
        return Punk.Cmd.asList(
                heroSay(N08.SOME_MONEY_PLEASE),
                partnerSay(P.NO_MONEY),
                heroSay(N08.SAD_MUST_PAY_FEE),
                partnerSay(P.QUIT_NO_PROBLEM),
                heroSay(N08.NOWHERE_TO_PRACTISE),
                partnerSay(P.PAY_THEN),
                cmdDisableTopic(Topics.MONEY)

        );
    }
    private List<Command> chain() {

        boolean cig = NQ1Settings.get().getCigaretteToPunk() == 1;

        boolean hasCigNoOffer = NQ1Settings.get().getCigaretteToPunk() == 0
                && App.getInventory().has(InventoryModel.Ids.CIGARETTE);

        boolean noCig = NQ1Settings.get().getCigaretteToPunk() == 0 &&
                !App.getInventory().has(InventoryModel.Ids.CIGARETTE);

        List<Command> list = Punk.Cmd.list(heroSay(N08.SZURDI_LOOKING_FOR_CHAIN),
                partnerSay(P.WHAT_CHAIN),
                heroSay(N08.REPAIRING_TOILET),
                partnerSay(P.DUNNO_WHERE_IT_IS),
                heroSay(N08.THOUGTH_SO),
                heroSay(N08.HE_WONT_HELP),
                new OneShotCommand() {

                    @Override
                    public void start() {
                        NQ1Settings.get().setAskAboutChain(true);

                    }
                },
                cmdDisableTopic(Topics.GOAWAY),
                cmdDisableTopic(Topics.CHAIN));
        if (cig) {
            list.addAll(Punk.Cmd.asList(
                    partnerSay(P.HEARD_ARTICLE1),
                    partnerSay(P.HE_MAY_HELP1),
                    heroSay(N08.TNX_INFO1),
                    partnerSay(P.TNX_CIG1),
                    new OneShotCommand() {

                        @Override
                        public void start() {
                            NQ1Settings.get().setArticleTold(true);
                        }
                    }));
        }

        if (hasCigNoOffer) {
            list.addAll(Punk.Cmd.list(
                    partnerSay(P.HAVE_A_CIG),
                    heroSay(N08.YES_I_HAVE),
                    partnerSay(P.GIVE_ONE),
                    Punk.get().getCigaretteAnim(N08.HERE_YOU_ARE, P.TNX_MAN, 1),
                    partnerSay(P.I_MAY_HELP),
                    partnerSay(P.HEARD_ARTICLE2),
                    partnerSay(P.HE_MAY_HELP2),
                    heroSay(N08.TNX_INFO2),
                    partnerSay(P.TNX_CIG2),
                    new OneShotCommand() {
                        @Override
                        public void start() {
                            NQ1Settings.get().setArticleTold(true);
                        }
                    }));
        } else if(noCig) {
            list.addAll(Punk.Cmd.asList(
                    partnerSay(P.GET_CIG_I_HELP),
                    heroSay(N08.OK_I_TRY),
                    new OneShotCommand() {

                        @Override
                        public void start() {
                            NQ1Settings.get().setPunkHelps(true);
                        }
                    }));
            return list;

        }
        return list;
    }


    private List<Command> bye() {
        return Cmd.list(heroSay(N08.BYE), partnerSay(P.SEE_YA));
    }
}
