package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.Actor;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.conversation.Conversation;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.speech.N05;
import hu.norbisquest.nq1.client.data.speech.U;

import java.util.List;

class NewsAgentConversation extends Conversation {



    enum Topics {
        PASS, TICKET, PAPERS, HOBO, CHEAPEST_NEWSPAPER, BUY_PAPER, BUY_SPECIAL, BYE
    }
    
    private static final int NEWSPAPER_PRICE = 300;
    private static final int TICKET_PRICE = 200;
    private static final int PASS_PRICE = 2000;

    NewsAgentConversation(NewsAgent newsAgent, Actor questioner, Actor partner) {
        super(questioner, partner);
    }

    @Override
    protected Enum toId(String s) { return Topics.valueOf(s);}

    @Override
    public void createTopics() {
        Norbi.get().setTalkBase(Norbi.TALK_BACK);
        addTopic(Topics.PASS, N05.ASK_PASS, this::pass, true);
        addTopic(Topics.TICKET, N05.ASK_TICKET, this::ticket, false);
        addTopic(Topics.PAPERS, N05.ASK_PAPERS, this::paper, false);
        addTopic(Topics.HOBO, N05.ASK_HOBO_NEARBY, this::hobo, true);
        addTopic(Topics.CHEAPEST_NEWSPAPER, N05.ASK_CHEAPEST_PAPER, this::cheapestNewsPaper, false);
        addTopic(Topics.BUY_PAPER, N05.BUY_NEWSPAPER, this::buyPaper,false);
        addTopic(Topics.BUY_SPECIAL, N05.BUY_DM_EXCLUSIVE, this::buySpecial, false);
        addBye(Topics.BYE, N05.BYE, this::bye, true);
    }

    @Override
    protected void activteExternalTopics() {
        if (NQ1Settings.get().isCheapestPaperEnabled()) {
            addEnabledIndex(Topics.CHEAPEST_NEWSPAPER);
        }
    }

    private List<Command> cheapestNewsPaper() {
        return Cmd.asList(heroSay(N05.ASK_CHEAPEST_PAPER),
                partnerSay(U.DUNNO_PRIZES),
                heroSay(N05.I_SEE),
                cmdEnableTopic(Topics.BUY_PAPER),
                cmdDisableTopic(Topics.CHEAPEST_NEWSPAPER));
    }

    private List<Command> bye() {
        return Cmd.asList(heroSay(N05.BYE), partnerSay(U.BYE));
    }

    private List<Command> buySpecial() {
        return Cmd.list(partnerSay(U.ASK_ANOTHER_ONE),
                heroSay(N05.ASK_NO_MORE),
                partnerSay(U.INDEED_YOU_CAN_300FT),
                heroSay(N05.OOOPS_NO_SUCH_MONEY),
                partnerSay(U.COME_BACK_IF_HAVE_ENOUGH),
                heroSay(N05.OK));
    }

    private List<Command> buyPaper() {
        boolean hasMoney = NQ1Settings.get().getMoney() > NEWSPAPER_PRICE;
        List<Command> list = Cmd.list(heroSay(N05.BUY_NEWSPAPER),
                partnerSay(U.WHAT_KIND_OF_PAPER),
                heroSay(N05.ONE_AT_THE_TOP),
                partnerSay(U.ASK_DM_EXCLUSIVE),
                heroSay(N05.YES_LOOKS_CHEAP_ENOUGH),
                partnerSay(U.ITS_300_FT));
                if (hasMoney) {
                    list.addAll(Cmd.list(
                            heroSay(N05.THE_PRICE),
                            partnerSay(U.HERE_IS_YOUR_NEWSPAPER),
                            heroSay(N05.THANKS),
                            Norbi.Cmd.take(null, null, InventoryModel.Ids.NEWSPAPER),
                            Norbi.addMoney(-NEWSPAPER_PRICE),
                            cmdDisableTopic(Topics.BUY_PAPER),
                            cmdEnableTopic(Topics.BUY_SPECIAL),
                            new OneShotCommand() {

                                @Override
                                public void start() {
                                    NQ1Settings.get().setCheapestPaperEnabled(false);
                                }
                            }));
                } else {
                    list.addAll(Cmd.list(
                            heroSay(N05.NO_MONEY_FOR_NEWSPAPER),
                            partnerSay(U.YOU_CANNOT_BUY_THEN),
                            heroSay(N05.ASK_ANY_CHEAPER),
                            partnerSay(U.NONE),
                            heroSay(N05.OK_NEVERMIND)));
                }
                return list;
    }

    private List<Command> ticket() {
        boolean hasMoney = NQ1Settings.get().getMoney() >= TICKET_PRICE;
        List<Command> list = Cmd.list(heroSay(N05.ASK_TICKET),
                partnerSay(U.TICKET_PRICE));

        if (hasMoney) {
            list.addAll(Cmd.list(heroSay(N05.HERE_IT_IS),
                    partnerSay(U.HERE_IS_YOUR_TICKET),
                    heroSay(N05.THANKS),
                    cmdDisableTopic(Topics.TICKET),
                    Norbi.addMoney(-TICKET_PRICE),
                    Norbi.Cmd.take(null, null, InventoryModel.Ids.TICKET)));
        } else {
            list.addAll(Cmd.list(heroSay(N05.NO_MONEY),
                    partnerSay(U.NO_TICKET_SORRY),
                    heroSay(N05.OKOK_THANKS)));
        }
        return list;
    }

    private List<Command> pass() {
        boolean hasMoney = NQ1Settings.get().getMoney() >= PASS_PRICE;
        List<Command> list = Cmd.list(
                heroSay(N05.ASK_PASS),
                partnerSay(U.PASS_PRICE),
                heroSay(N05.PASS_EXPENSIVE),
                partnerSay(U.YES_EXPENSIVE),
                cmdEnableTopic(Topics.TICKET));
        if (hasMoney) {
            list.addAll(Cmd.list(
                    heroSay(N05.NO_WASTING_MONEY, N05.NO_THANKS),
                    partnerSay(U.OK_YOU_KNOW)));
        } else {
            list.addAll(Cmd.list(
                    heroSay(N05.NO_MONEY_FOR_PASS),
                    partnerSay(U.NO_MONEY_NO_PASS),
                    cmdDisableTopic(Topics.PASS)));
        }
        return list;
    }

    private List<Command> paper() {
        return Cmd.list(heroSay(N05.ASK_PAPERS),
                partnerSay(U.LOOK_THERE),
                heroSay(N05.I_SEE_TNX));
    }

    private List<Command> hobo() {
        return Cmd.list(heroSay(N05.ASK_HOBO_NEARBY),
                partnerSay(U.HOBO_1, U.HOBO_2, U.HOBO_3),
                heroSay(N05.MAKE_HIM_GO),
                partnerSay(U.HOBO_LEAVE_HIM),
                heroSay(N05.ALLRIGTH),
                cmdDisableTopic(Topics.HOBO));
    }

    public void enableCheapestNewspaper() {
        addEnabledIndex(Topics.CHEAPEST_NEWSPAPER);
    }
}
