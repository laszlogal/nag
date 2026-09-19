package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nagbase.game.target.ActorTarget;
import hu.norbisquest.nq1.client.data.speech.CS;
import hu.norbisquest.nq1.client.data.speech.N05;

import java.util.List;

class HoboConversation extends Conversation {
    private enum Topics
    {GREETS, FUCKOFF, ASK_SELL, ASK_MONEY, WHAT_FOR, FULL_YOUR_HAT, ASK_NEWSPAPER_BACK, BANANA, BYE}

    HoboConversation(Actor hero, ActorTarget target) {
        super(hero, target);
    }

    @Override
    protected Enum toId(String s) {
        return Topics.valueOf(s);
    }

    @Override
    public void createTopics() {
        Norbi.get().setTalkBase(Norbi.TALK_RIGHT);
        addTopic(Topics.GREETS, N05.GREETINGS, this::greets, true);
        addTopic(Topics.FUCKOFF, N05.FUCKOFF, this::fuckoff, true);
        addTopic(Topics.ASK_SELL, N05.WHAT_SELL, this::askSell, true);
        addTopic(Topics.ASK_MONEY, N05.ASK_MONEY, this::askMoney, true);
        addTopic(Topics.WHAT_FOR, N05.WHAT_I_GET,this::whatsMoneyFor, false);
        addTopic(Topics.FULL_YOUR_HAT, N05.HAT_FULL,this::fullHat, false);
        addTopic(Topics.ASK_NEWSPAPER_BACK, N05.WANT_NEWSPAPER_BACK, this::askPaperBack, false);
        addTopic(Topics.BANANA, N05.BANANA, this::banana, true);
        addBye(Topics.BYE, N05.BYE, this::bye, true);
    }

    private List<Command> greets() {
        return Cmd.asList(heroSay(N05.GREETINGS),
                cmdEnableTopic(Topics.WHAT_FOR),
                cmdEnableTopic(Topics.FULL_YOUR_HAT),
                cmdDisableTopic(Topics.GREETS, Topics.FUCKOFF, Topics.ASK_SELL,
                Topics.ASK_MONEY, Topics.BANANA, Topics.BYE),
                partnerSay(CS.BEGGING));}

    private List<Command> fuckoff() {
        return Cmd.asList(heroSay(N05.FUCKOFF),
                cmdDisableTopic(Topics.GREETS),
                cmdDisableTopic(Topics.FUCKOFF), partnerSay(CS.SHUTUP),
                heroSay(N05.CALL_FRIENDS), partnerSay(CS.WAITING),
                heroSay(N05.NO_JOKING_I_CALL));}

    private List<Command> askSell() {
        return Cmd.asList(heroSay(N05.WHAT_SELL),
            cmdDisableTopic(Topics.GREETS, Topics.ASK_SELL),
            partnerSay(CS.SELL_NONE),
            heroSay(N05.WHY_SIT),
            partnerSay(CS.POOR_HOBO),
            heroSay(N05.WISH_SING),
            partnerSay(CS.RIGHT_I_SING),
            heroSay(N05.STOP_IT),
            partnerSay(CS.OK));}

    private List<Command> askMoney() {
        return Cmd.asList(
                heroSay(N05.ASK_MONEY),
                cmdDisableTopic(Topics.GREETS),
                cmdDisableTopic(Topics.ASK_MONEY),
                partnerSay(CS.NO_WAY),
                heroSay(N05.HARD_WORK_EH),
                partnerSay(CS.TRY_IT_ONCE),
                heroSay(N05.NO_THANKS));}

    private List<Command> whatsMoneyFor() {
        return Cmd.asList(heroSay(N05.WHAT_I_GET),
                cmdEnableTopic(Topics.FUCKOFF,
                        Topics.ASK_SELL, Topics.ASK_MONEY,
                        Topics.BANANA, Topics.BYE),
                cmdDisableTopic(Topics.WHAT_FOR, Topics.FULL_YOUR_HAT),
                partnerSay(CS.I_HAVE_NOTHING),
                heroSay(N05.ECONOMY_NOT_LIKE_THIS),
                heroSay(N05.I_PAY_I_GET_SOMETHING),
                partnerSay(CS.FUCKOFF_YOU_NERD),
                heroSay(N05.WANT_MY_MONEY));}

    private List<Command> fullHat() {
        return Cmd.asList(
            heroSay(N05.HAT_FULL),
            cmdDisableTopic(Topics.WHAT_FOR, Topics.FULL_YOUR_HAT,
                    Topics.FUCKOFF, Topics.ASK_SELL, Topics.ASK_MONEY,
                    Topics.BANANA),
            cmdEnableTopic(Topics.BYE),
            partnerSay(CS.YES_FULL),
            heroSay(N05.ASK_SPEND_FOR),
            partnerSay(CS.FOR_ALCOHOL),
            heroSay(N05.I_DONT_GIVE_FOR));}

    private List<Command> askPaperBack() {
        return Cmd.asList(
                heroSay(N05.WANT_NEWSPAPER_BACK),
                cmdEnableTopic(Topics.BYE),
                partnerSay(CS.READING_YET),
                heroSay(N05.I_DO_NEED_IT),
                partnerSay(CS.BUY_ANOTHER_ONE));}


    private List<Command> banana() {
        return Cmd.asList(heroSay(N05.BANANA),
            cmdDisableTopic(Topics.BANANA), partnerSay(CS.NO_MIRROR),
            heroSay(N05.I_WARN), partnerSay(CS.TNX));}

    private List<Command> bye() {return Cmd.asList(heroSay(N05.BYE));}
}
