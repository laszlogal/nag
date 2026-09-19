package hu.norbisquest.nq1.client;

import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Settings;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.Stage.Id;
import hu.norbisquest.nq1.client.data.bundle.Room;
import hu.norbisquest.nq1.client.data.model.InventoryModel;
import hu.norbisquest.nq1.client.data.model.InventoryModel.Ids;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class NQ1Settings extends Settings {
	// Global int comments: original Adventure Game Studio reference
	private static final NQ1Ids startScreen = NQ1Ids.Room;
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 400;
	public static final int FEE_PRICE = 2000;

	private Integer money = 0;
	private Integer cigaretteToPunk = 0;
	private Integer stealCount = 0;

	private Boolean tvWatched = false;
	private Boolean sleeveExamined = false;
	private Boolean sleeveOpened = false;
	private Boolean wardrobeOpened = false;
	private Boolean chainNeeded = false;
	private Boolean feePayed = false;
	private Boolean brushNeeded = false;
	private Boolean articleTold = false;
	private Boolean cheapestPaperEnabled = false;
	private Boolean newspaperBack = false;
	private Boolean cigaretteToHobo = false;
	private Boolean cigaretteToSzurdi = false;
	private Boolean cannalShouted = false;
	private Boolean brushToSzurdi = false;
	private Boolean hoboHadNewspaper = false;
	private Boolean punkHelp = false;
	private Boolean knifePulled = false;
	private Boolean askAboutKnife = false;
	private Boolean askAboutChain = false;
	private Boolean coatSearched = false;
	private Boolean trashSearched = false;
	private Boolean rubbishSearched = false;
	private Boolean szurdiReading = false;
	private Boolean szurdiHasChain = false;
	private Boolean szurdiFedUp = false;

	// TODO: to registry.
	private Ids hoboItem;

	public NQ1Settings(SettingsChanged listener) {
		super(listener);
		setScreenSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaults();
	}

	@Override
	protected void fillRegistry() {
		getRegistry().addNumbers(money,
				cigaretteToPunk,
				stealCount);

		getRegistry().addBooleans(
				tvWatched,
				sleeveExamined,
				sleeveOpened,
				wardrobeOpened,
				chainNeeded,
				feePayed,
				brushNeeded,
				articleTold,
				cheapestPaperEnabled,
				newspaperBack,
				cigaretteToHobo,
				cigaretteToSzurdi,
				cannalShouted,
				brushToSzurdi,
				hoboHadNewspaper,
				punkHelp,
				knifePulled,
				askAboutKnife,
				askAboutChain,
				coatSearched,
				trashSearched,
				rubbishSearched,
				szurdiReading,
				szurdiHasChain,
				szurdiFedUp);
	}

	@Override
	protected void restoreFromRegistry() {

		Registry reg = getRegistry();
		int i = 0;

		money = reg.getNumber(i++);
		cigaretteToPunk = reg.getNumber(i++);
		stealCount = reg.getNumber(i++);

		int b = 0;

		tvWatched = reg.getBoolean(b++);
		sleeveExamined = reg.getBoolean(b++);
		sleeveOpened = reg.getBoolean(b++);
		wardrobeOpened = reg.getBoolean(b++);
		chainNeeded = reg.getBoolean(b++);
		feePayed = reg.getBoolean(b++);
		brushNeeded = reg.getBoolean(b++);
		articleTold = reg.getBoolean(b++);
		cheapestPaperEnabled = reg.getBoolean(b++);
		newspaperBack = reg.getBoolean(b++);
		cigaretteToHobo = reg.getBoolean(b++);
		cigaretteToSzurdi = reg.getBoolean(b++);
		cannalShouted = reg.getBoolean(b++);
		brushToSzurdi = reg.getBoolean(b++);
		hoboHadNewspaper = reg.getBoolean(b++);
		punkHelp = reg.getBoolean(b++);
		knifePulled = reg.getBoolean(b++);
		askAboutKnife = reg.getBoolean(b++);
		askAboutChain = reg.getBoolean(b++);
		coatSearched = reg.getBoolean(b++);
		trashSearched = reg.getBoolean(b++);
		rubbishSearched = reg.getBoolean(b++);
		szurdiReading = reg.getBoolean(b++);
		szurdiHasChain = reg.getBoolean(b++);
		szurdiFedUp = reg.getBoolean(b++);

	}

	@Override
	protected void setDefaults() {
		setStartId(startScreen);
//		setSpeechMode(SpeechMode.Text  );
		setMusicOn(true);
		if (App.isDebug()) {
			setDebug();
		}
		//setLanguage("EN");
	}

	private void setDebug() {
		setMusicOn(false);
		setStartId(NQ1Ids.Corridor);

	}

	private boolean isSleeveExamined() {
		return sleeveExamined;
	}

	public void setSleeveExamined(boolean sleeveExamined) {
		this.sleeveExamined = sleeveExamined;
	}

	public boolean isKeyFoundInDrawer() {
		return isSleeveExamined() && !App.getInventory().has(InventoryModel.Ids.SLEEVE_KEY);
	}

	public boolean hasTicket() {
		return App.getInventory().has(InventoryModel.Ids.TICKET);
	}

	public boolean hasUsedTicket() {
		return App.getInventory().has(InventoryModel.Ids.USED_TICKET);
	}

	public boolean isWardrobeOpened() {
		return wardrobeOpened;
	}

	public void setWardrobeOpened(boolean wardrobeOpen) {
		this.wardrobeOpened = wardrobeOpen;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
		App.debug("[MONEY] " + money);
	}

	public boolean isChainNeeded() {
		return chainNeeded;
	}

	public void setChainNeeded(boolean chainNeeded) {
		this.chainNeeded = chainNeeded;
	}

	public boolean isArticleTold() {
		return articleTold;
	}

	public void setArticleTold(boolean articleTold) {
		this.articleTold = articleTold;
	}

	public boolean isCheapestPaperEnabled() {
		return cheapestPaperEnabled;
	}

	public void setCheapestPaperEnabled(boolean cheapestPaperEnabled) {
		this.cheapestPaperEnabled = cheapestPaperEnabled;
	}

	public Ids getHoboItem() {
		return hoboItem;
	}

	public void setHoboItem(Ids hoboItem) {
		this.hoboItem = hoboItem;
		if (hoboItem == InventoryModel.Ids.NEWSPAPER) {
			setHoboHadNewspaper(true);
		}
	}

	public boolean isNewspaperBack() {
		return newspaperBack;
	}

	public void setNewspaperBack(boolean newspaperBack) {
		this.newspaperBack = newspaperBack;
	}

	public boolean isCigaretteToHobo() {
		return cigaretteToHobo;
	}

	public void setCigaretteToHobo(boolean cigaretteToHobo) {
		this.cigaretteToHobo = cigaretteToHobo;
	}

	public boolean isSleeveOpened() {
		return sleeveOpened;
	}

	public void setSleeveOpened(boolean sleeveOpened) {
		this.sleeveOpened = sleeveOpened;
	}

	public boolean isHoboHadNewspaper() {
		return hoboHadNewspaper;
	}

	private void setHoboHadNewspaper(boolean hoboHadNewspaper) {
		this.hoboHadNewspaper = hoboHadNewspaper;
	}

	public boolean isBrushNeeded() {
		return brushNeeded;
	}

	public void setBrushNeeded(boolean brushNeeded) {
		this.brushNeeded = brushNeeded;
	}

	public boolean isCoatSearched() {
		return coatSearched;
	}

	public void setCoatSearched(boolean coatSearched) {
		this.coatSearched = coatSearched;
	}

	public int getStealCount() {
		return stealCount;
	}

	public void 	hoboStolen() {
		stealCount++;
	}

	public boolean isFeePayed() {
		return feePayed;
	}

	public void setFeePayed(boolean feePayed) {
		this.feePayed = feePayed;
	}

	public boolean isSzurdiReading() {
		return szurdiReading;
	}

	public void setSzurdiReading(boolean szurdiReading) {
		this.szurdiReading = szurdiReading;
	}

	public boolean isSzurdiHasChain() {
		return szurdiHasChain;
	}

	public void setSzurdiHasChain(boolean szurdiHasChain) {
		this.szurdiHasChain = szurdiHasChain;
	}

	public boolean isCigaretteToSzurdi() {
		return cigaretteToSzurdi;
	}

	public void setCigaretteToSzurdi(boolean cigaretteToSzurdi) {
		this.cigaretteToSzurdi = cigaretteToSzurdi;
	}

	public boolean isBrushToSzurdi() {
		return brushToSzurdi;
	}

	public void setBrushToSzurdi(boolean brushToSzurdi) {
		this.brushToSzurdi = brushToSzurdi;
	}

	public boolean isSzurdiFedUp() {
		return szurdiFedUp;
	}

	public void setSzurdiFedUp(boolean szurdiFedUp) {
		this.szurdiFedUp = szurdiFedUp;
	}

	public int getCigaretteToPunk() {
		return cigaretteToPunk;
	}

	public void setCigaretteToPunk(int cigaretteToPunk) {
		this.cigaretteToPunk = cigaretteToPunk;
	}

	public boolean isPunkHelps() {
		return punkHelp;
	}

	public void setPunkHelps(boolean punkHelps) {
		this.punkHelp = punkHelps;
	}

	@Override
	protected Id createStageId(String name) {
		return name == null || "".equals(name) ? null : NQ1Ids.valueOf(name);
	}

	@Override
	protected hu.norbisquest.nagbase.game.target.HotSpot.Id createHotSpotId(Stage.Id id, String name) {
		// TODO Auto-generated method stub
		NQ1Ids stageId = (NQ1Ids) id;
		switch (stageId) {
		// case BusStop:
		// return BusStopHotSpots.valueOf(name);
		// case Corridor:
		// return CorridorHotSpots.valueOf(name);
		// case RehearsalRoom:
		// return FittingRoomHotSpots.valueOf(name);
		// case HouseFront:
		// return HouseFrontHotSpots.valueOf(name);
		// case NewsStand:
		// return NewsStandHotSpots.valueOf(name);
		// case RockKlubFront:
		// return RCFrontHotSpots.valueOf(name);
		// case RockKlubIn:
		// return RCHotSpots.valueOf(name);
		case Room:
			return Room.HotSpots.valueOf(name);
		case TOILET:
			// return WCHotSpots.valueOf(name);
		case BusBack:
		case BusFront:
		case PlayBass:
		case TestRoom:
		case None:
		default:
			break;

		}
		return null;
	}

	public boolean isTrashSearched() {
		return trashSearched;
	}

	public void setTrashSearched(boolean trashSearched) {
		this.trashSearched = trashSearched;
	}

	public boolean isRubbishSearched() {
		return rubbishSearched;
	}

	public void setRubbishSearched(boolean rubbishSearched) {
		this.rubbishSearched = rubbishSearched;
	}

	public void setStealCount(int stealCount) {
		this.stealCount = stealCount;
	}


	@Override
	public String getPrefix() {
		return "[NQSETTINGS]";
	}

	public Boolean isTvWatched() {
		return tvWatched;
	}

	public void setTvWatched(Boolean tvWatched) {
		this.tvWatched = tvWatched;
	}

	public static NQ1Settings get() {
		return (NQ1Settings) App.getSettings();
	}

	public void toggleWardrobeDoor() {
		wardrobeOpened = !wardrobeOpened;
	}

	public Boolean isCannalShouted() {
		return cannalShouted;
	}

	public void setCannalShouted(Boolean cannalShouted) {
		this.cannalShouted = cannalShouted;
	}

	public Boolean getAskAboutKnife() {
		return askAboutKnife;
	}

	public void setAskAboutKnife(Boolean askAboutKnife) {
		this.askAboutKnife = askAboutKnife;
	}

	public Boolean isKnifePulled() {
		return knifePulled;
	}

	public void setKnifePulled(Boolean knifePulled) {
		this.knifePulled = knifePulled;
	}

	public Boolean getAskAboutChain() {
		return askAboutChain;
	}

	public void setAskAboutChain(Boolean askAboutChain) {
		this.askAboutChain = askAboutChain;
	}
}
