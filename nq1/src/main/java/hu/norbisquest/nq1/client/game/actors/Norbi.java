package hu.norbisquest.nq1.client.game.actors;

import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Hero;
import hu.norbisquest.nagbase.game.command.OneShotCommand;
import hu.norbisquest.nq1.client.NQ1Settings;
import hu.norbisquest.nq1.client.data.model.ActorModel;
import hu.norbisquest.nq1.client.factory.NQ1Ids;

public class Norbi extends Hero {
	public static class Cmd extends Hero.Cmd {
		public static Command setRole(final Role role) {
			return new OneShotCommand() {

				@Override
				public void start() {
					Norbi.get().setRole(role);
				}
			};
		}

		public static Command restoreRole() {
			return new OneShotCommand() {

				@Override
				public void start() {
					Norbi.get().restoreRole();
				}
			};
		}

	}

	private static final int WALK_SPEED = 8;
	private static final int TALK_SPEED = 8;
	public static final int GRAB_FRONT = 4;
	public static final int GRAB_LEFT = 5;
	private static final int SQUAT_TALK_LEFT = 6;
	private static final int SQUAT_LEFT = 6;
	public static final int GRAB_RIGHT = 7;
	public static final int SQUAT_GRAB_RIGHT = 8;
	static final int GIVE_ALCOHOL = 9;
	static final int GIVE_NEWSPAPER = 10;
	static final int GIVE_SANDWICH = 11;
	static final int GIVE_CIGARETTE = 12;
	static final int SIT_ON_BUS = 13;
	static final int USE_TICKET_ON_BUS = 14;
	public static final int GRAB_BACK = 14;
	public static final int PULL_KNIFE = 15;
	private static final int SEARCH_COAT = 16;
	public static final int SIT_ON_BED = 17;
	public static final int KNOB_LEFT = 18;
	public static final int CUT_CHAIN = 19;
	public static final int GRAB_DOWN = 20;
	public static final int SIT_ON_BUS_BACK = 21;
	private static final int SQUAT_GRAB_BACK = 22;
	public static final int PLAY_BASS = 23;
	public static final int BOOM = 24;
	public static final int BURN = 25;
	private static final int BURNED = 26;
	private static final int SQUAT_FRONT = 27;

	public static final int GRAB_TIMEOUT = 10;
	private static final int PULL_SPEED = 8;
	private static final int SEARCH_COAT_SPEED = 8;
	private static final int CUT_CHAIN_SPEED = 8;
	private static final int PLAY_BASS_SPEED = 6;
	private static final int BURN_SPEED = 8;

    private Role role;
	private Role prevRole = Role.WALK;
	private Integer prevWalkId;

	public enum Role {
		WALK("walk"), SQUAT_LEFT("sq_left"), SQUAT_FRONT("sq_front"),
			SEARCH_COAT("src_coat"), SEARCH_BRUSH("src_brush"), None("none");
		String name;

		Role(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	private static void create() {
		if (INSTANCE == null) {
			INSTANCE = new Norbi();
		}

	}

	private Norbi() {
		super(ActorModel.getNorbi());
		setWalkAnimatiomSpeed(App.getSettings().getAnimSpeed(),
				App.getSettings().getMoveSpeed(),
				App.getSettings().getSlopeSpeed());
	}

	@Override
	protected void addAnimations() {
		addAnimationPreloaded(WALK_FRONT, NorbiFrames.Data.fronts, getWalkAnimSpeed(),true);
		addAnimationPreloaded(WALK_BACK, NorbiFrames.Data.backs, getWalkAnimSpeed(), true);
		addAnimationPreloaded(WALK_LEFT, NorbiFrames.Data.lefts, getWalkAnimSpeed(), true);
		addAnimationPreloaded(WALK_RIGHT, NorbiFrames.Data.rights, getWalkAnimSpeed(), true);
		addAnimationPreloaded(GRAB_FRONT, NorbiFrames.Data.grab_fronts, WALK_SPEED);
		addAnimationPreloaded(GRAB_LEFT, NorbiFrames.Data.grab_lefts, WALK_SPEED);
		addAnimationPreloaded(GRAB_RIGHT, NorbiFrames.Data.grab_rights, WALK_SPEED);
		addAnimationPreloaded(TALK_FRONT, NorbiFrames.Data.talk_fronts, TALK_SPEED);
		addAnimationPreloaded(TALK_BACK, NorbiFrames.Data.talk_backs, TALK_SPEED);
		addAnimationPreloaded(TALK_LEFT, NorbiFrames.Data.talk_lefts, TALK_SPEED);
		addAnimationPreloaded(TALK_RIGHT, NorbiFrames.Data.talk_rights, TALK_SPEED);
		addAnimationPreloaded(SQUAT_TALK_LEFT, NorbiFrames.Data.squat_talk_lefts,
				TALK_SPEED);
		addAnimationPreloaded(SQUAT_FRONT, NorbiFrames.INSTANCE.squat_front());
		addAnimationPreloaded(SQUAT_GRAB_RIGHT, NorbiFrames.INSTANCE.squat_grab_right());
			addAnimationPreloaded(KNOB_LEFT, NorbiFrames.INSTANCE.knob_left());
		addAnimationPreloaded(CUT_CHAIN, NorbiFrames.Data.cut_chain, CUT_CHAIN_SPEED);
		addAnimationPreloaded(GRAB_DOWN, NorbiFrames.INSTANCE.grab_down());
		addAnimationPreloaded(SQUAT_GRAB_BACK, NorbiFrames.Data.squat_grab_back,
				PULL_SPEED);
		createWalkSfx();
		setRole(Role.WALK);
		setAnimationId(WALK_FRONT);
	}

	@Override
    protected void addStageDependentAnimations() {
	    switch ((NQ1Ids)App.getSettings().getScreenId()) {
            case Room:
                addAnimationPreloaded(SIT_ON_BED, NorbiFrames.INSTANCE.sit_on_bed());
                addAnimationPreloaded(GRAB_FRONT, NorbiFrames.Data.grab_fronts, WALK_SPEED);
                addAnimationPreloaded(GRAB_LEFT, NorbiFrames.Data.grab_lefts, WALK_SPEED);
                break;
            case Corridor:
                break;
            case TOILET:
                addAnimationPreloaded(CUT_CHAIN, NorbiFrames.Data.cut_chain, CUT_CHAIN_SPEED);
                break;
            case HouseFront:
                break;
            case NewsStand:
                addAnimationPreloaded(GIVE_ALCOHOL, NorbiFrames.INSTANCE.give_alcohol());
                addAnimationPreloaded(GIVE_NEWSPAPER, NorbiFrames.INSTANCE.give_newspaper());
                addAnimationPreloaded(GIVE_SANDWICH, NorbiFrames.INSTANCE.give_sandwitch());
                break;
            case BusStop:
                break;
            case BusFront:
                addAnimationPreloaded(USE_TICKET_ON_BUS,
                        NorbiFrames.INSTANCE.use_ticket_on_bus());
                addAnimationPreloaded(SIT_ON_BUS, NorbiFrames.INSTANCE.sit_bus_front());
                break;
            case BusBack:
                addAnimationPreloaded(SIT_ON_BUS_BACK, NorbiFrames.INSTANCE.sit_bus_back());
                break;
            case VRKFront:
                addAnimationPreloaded(GIVE_CIGARETTE, NorbiFrames.INSTANCE.give_cigarette());
                break;
            case RockKlub:
                break;
            case RehearsalRoom:
                addAnimationPreloaded(PULL_KNIFE, NorbiFrames.Data.pull_knife, PULL_SPEED);
                addAnimationPreloaded(SEARCH_COAT, NorbiFrames.Data.search_coat,
                        SEARCH_COAT_SPEED);
                break;
            case PlayBass:
                addAnimationPreloaded(PLAY_BASS, NorbiFrames.Data.play_bass, PLAY_BASS_SPEED);
                addAnimationPreloaded(BOOM, NorbiFrames.INSTANCE.boom());
                addAnimationPreloaded(BURN, NorbiFrames.Data.burn, BURN_SPEED);
                addAnimationPreloaded(BURNED, NorbiFrames.INSTANCE.burned());
                break;
            case TestRoom:
            case TestScreen:
            case Splash1:
            case Splash2:
            case Splash3:
            case MainMenu:
            case Setup:
            case PoorNorbi:
            case Credits:
            case Actors:
            case End:
            case None:
            case Exit:
                break;
        }
     }

    @Override
	public void stop() {
		reset();
		super.stop();
	}

	public void nextAnimation() {
		Integer id = getCurrentAnimationId();
		if (id == WALK_RIGHT) {
			id = WALK_FRONT;
		} else {
			id++;
		}

		changeAnimation(id);
	}

	public static Command addMoney(final int delta) {
		return new OneShotCommand() {

			@Override
			public void start() {
				NQ1Settings settings = (NQ1Settings) App.getSettings();
				int money = settings.getMoney() + delta;
				settings.setMoney(money);
			}
		};
	}

	public Command cmdSetTalkBase(final int phase) {
		return new OneShotCommand() {

			@Override
			public void start() {
				setTalkBase(phase);
			}
		};
	}

	public static Norbi get() {
		if (getInstance() == null) {
			Norbi.create();
		}
		return ((Norbi) getInstance());
	}

	@Override
	public int getTalkDirection() {
		Integer animId = getCurrentAnimationId();
        if (animId == SQUAT_TALK_LEFT) {
            return animId;
        }
        return super.getTalkDirection();
    }

	private void setRole(Role role) {
		prevRole = this.role;
		prevWalkId = getCurrentAnimationId();
		this.role = role;

		switch (role) {
		case SQUAT_FRONT:
			setWalkAnimation(SQUAT_FRONT, SQUAT_FRONT, SQUAT_FRONT, SQUAT_FRONT);
			setTalkAnimation(SQUAT_FRONT);
			turnFront();
			break;
		case SQUAT_LEFT:
			setWalkAnimation(SQUAT_LEFT, SQUAT_LEFT, SQUAT_LEFT, SQUAT_LEFT);
			setTalkAnimation(SQUAT_TALK_LEFT);
			turnLeft();
			break;
        case SEARCH_COAT:
                setAnimation(SEARCH_COAT);
                turnLeft();
                break;

		case SEARCH_BRUSH:
				setAnimation(SQUAT_GRAB_BACK);
				turnFront();
				break;
		case WALK:
			setWalkAnimation(WALK_FRONT, WALK_BACK, WALK_LEFT, WALK_RIGHT);
			setTalkAnimation(TALK_FRONT, TALK_BACK, TALK_LEFT, TALK_RIGHT);
			break;
		default:
			break;

		}
		updatePosition();
	}

	private void restoreRole() {
		if (prevRole == Role.None) {
			return;
		}
		int walkId = prevWalkId;

		setRole(prevRole);

		switch (walkId) {
		case WALK_FRONT:
			turnFront();
			break;
		case WALK_BACK:
			turnBack();
			break;
		case WALK_LEFT:
			turnLeft();
			break;
		case WALK_RIGHT:
			turnRight();
			break;

		}

		updatePosition();
        prevRole = Role.None;
	}

	public static void destroyInstance() {
		INSTANCE.destroy();
		INSTANCE = null;
	}
}
