package hu.norbisquest.nq1.client.factory;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.StageModel;
import hu.norbisquest.nagbase.game.command.Cmd;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nagbase.game.command.TakeCommand;
import hu.norbisquest.nagbase.game.target.HotSpot;
import hu.norbisquest.nagbase.game.target.HotSpot.Id;
import hu.norbisquest.nagbase.game.target.InventoryItem;
import hu.norbisquest.nagbase.game.target.ItemFactory;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.bundle.Inventory;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.model.InventoryModel.Ids;
import hu.norbisquest.nq1.client.data.speech.INV;
import hu.norbisquest.nq1.client.game.actors.Norbi;
import hu.norbisquest.nq1.client.game.stages.NQ1Stage;
import hu.norbisquest.nqcommon.client.NQApp;
import hu.norbisquest.nqcommon.client.control.ItemControl;

import java.util.ArrayList;
import java.util.List;

public final class InventoryFactory implements ItemFactory {
	private static InventoryFactory INSTANCE = null;

	private InventoryFactory() {

	}

	private static InventoryItem createSleeve() {
		final class SleeveOpenControl extends ItemControl {
			private static final int SAFE_MONEY = 1900;

			@Override
			public List<Command> onExamine(int x, int y) {
				if (NQ1Settings.get().isSleeveOpened()) {
					return Norbi.sayAll(INV.THERE_WAS_MY_MONEY);
				}

                List<Command> list = new ArrayList<>(Norbi.sayAll(INV.MY_SLEEVE, INV.NO_CRASH_BUT_KEY));
				if (!App.getInventory().has(Ids.SLEEVE_KEY)) {
					list.add(Norbi.say(INV.WONDER_WHERE_IT_IS));
					list.add(new OneShotCommand() {

						@Override
						public void start() {
							NQ1Settings.get().setSleeveExamined(true);
						}
					});
				}

				return list;

			}

			@Override
			public List<Command> onInventory(HotSpot.Id id) {
				if (NQ1Settings.get().isSleeveOpened()) {
					return Norbi.sayAll(INV.ALREADY_OPEN);
				}

				if (id == InventoryModel.Ids.SLEEVE_KEY) {
					return Cmd.list(
							Norbi.sayAll(INV.OPEN_SESAMI, INV.ONLY_1900HUF, INV.HUNDRED_NEEDED),
							new TakeCommand(createMoney()),
							Cmd.resetMode(),
							new OneShotCommand() {

								@Override
								public void start() {
									NQ1Settings.get().setMoney(SAFE_MONEY);
									NQ1Settings.get().setSleeveOpened(true);

								}
							});
				}
				return null;

			}
		}

		InventoryItem item = new InventoryItem(InventoryModel.sleeve());
		item.setControl(new SleeveOpenControl());
		return item;
	}

	private static InventoryItem createSleeveKey() {

		final class SleeveKeyControl extends ItemControl {

			@Override
			public List<Command> onExamine(int x, int y) {
				return Norbi.sayAll(INV.MY_SLEEVE_KEY);
			}

			@Override
			public List<Command> onInventory(HotSpot.Id id) {
				if (id == InventoryModel.Ids.SLEEVE) {
					return Norbi.saySimple(INV.WORKS_REVERSE);
				}
				return null;
			}

		}

		InventoryItem item = new InventoryItem(InventoryModel.sleeve_key());
		item.setControl(new SleeveKeyControl());
		return item;
	}

	private static InventoryItem createMoney() {
		final class MoneyControl extends ItemControl {

			private String moneyToString() {
				return "Hajszálpontosan " + NQ1Settings.get().getMoney() + " Forint.";
			}

			@Override
			public List<Command> onExamine(int x, int y) {
				return Cmd.list(
						Norbi.say(INV.LET_ME_SEE),
						Norbi.say(moneyToString()));

			}

		}

		InventoryItem item = new InventoryItem(InventoryModel.money());
		item.setControl(new MoneyControl());
		return item;
	}

	private static InventoryItem createToiletPaper() {
		return NQApp.createItemExamineOnly(InventoryModel.toilet_paper(), INV.DONT_FORGET_TO_REPLACE);
	}

	private static InventoryItem createToiletBrush() {
		return NQApp.createItemExamineOnly(InventoryModel.toilet_brush(), INV.DONT_FORGET_TO_TAKE_HOME);

	}

	private static InventoryItem createToiletChain() {
		return NQApp.createItemExamineOnly(InventoryModel.toilet_chain(), INV.HOPE_NOBODYS_ON_TOILET_HOME);
	}

	private static InventoryItem createTicket() {
		return NQApp.createItemExamineOnly(InventoryModel.ticket(), INV.TOO_MUCH_FOR_THIS);
	}

	private static InventoryItem createUsedTicket() {
		return NQApp.createItemExamineOnly(InventoryModel.used_ticket(), INV.USELESS);
	}

	private static InventoryItem createSandwitch() {
		return NQApp.createItemExamineOnly(InventoryModel.sandwitch(),
				INV.LOOKS_NEW, INV.DUNNO_WHY_TRASHED);
	}

	private static InventoryItem createCigarette() {
		return NQApp.createItemExamineOnly(InventoryModel.cigarette(), INV.CIGAR_FROM_ABROAD);
	}

	private static InventoryItem createAlcohol() {
		return NQApp.createItemExamineOnly(InventoryModel.alcohol(), INV.NONAME_BEER);
	}

	private static InventoryItem createNewspaper() {
		return NQApp.createItemExamineOnly(InventoryModel.newspaper(),
				INV.DM_EXCLUSIVE, INV.THERE_ARTICLE);

	}

	private static InventoryItem createSnips() {
		return NQApp.createItemExamineOnly(InventoryModel.snips(), INV.STRONG_SNIPS);
	}

	private static InventoryItem createFuse() {
		return NQApp.createItemExamineOnly(InventoryModel.fuse(), INV.FUSE_AT_LAST);
	}

	@Override
	public InventoryItem createItem(String name) {
		return createItem(InventoryModel.Ids.valueOf(name));
	}

	@Override
	public void fill() {
		for (InventoryModel.Ids id : InventoryModel.Ids.values()) {
			App.getInventory().put(createItem(id));
		}
	}

	public static void setInventorySpeech() {
		Norbi.get().setLines(Inventory.INSTANCE.nLines());
		Norbi.get().setAudio(Inventory.Data.norbi);
	}

	public static void restoreSpeech() {
		StageModel res = NQ1Stage.current().getResource();
		if (res.heroAudio != null) {
			Norbi.get().setAudio(res.heroAudio);
		}

		if (res.heroLines != null) {
			Norbi.get().setLines(res.heroLines);
		}
	}

	@Override
	public InventoryItem createItem(Id id) {
		switch ((InventoryModel.Ids) id) {
		case ALCOHOL:
			return createAlcohol();
		case CIGARETTE:
			return createCigarette();
		case FUSE:
			return createFuse();
		case MONEY:
			return createMoney();
		case NEWSPAPER:
			return createNewspaper();
		case SANDWITCH:
			return createSandwitch();
		case SLEEVE:
			return createSleeve();
		case SLEEVE_KEY:
			return createSleeveKey();
		case SNIPS:
			return createSnips();
		case TICKET:
			return createTicket();
		case TOILET_BRUSH:
			return createToiletBrush();
		case TOILET_CHAIN:
			return createToiletChain();
		case TOILET_PAPER:
			return createToiletPaper();
		case USED_TICKET:
			return createUsedTicket();
		default:
			break;

		}
		return null;
	}

	public static ItemFactory get() {
		if (INSTANCE == null) {
			INSTANCE = new InventoryFactory();
		}
		return INSTANCE;
	}
}
