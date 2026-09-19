package hu.norbisquest.nq1.client;

import java.util.Arrays;
import java.util.List;

public class NQ1Texts {

	public static int s01_window = 0;

	public static int s01_poster = 1;

	public static int s01_dvd = 2;

	public static int s01_tv = 3;

	public static int s01_powerstrip = 4;

	public static int s01_wardrobe = 5;

	public static int s01_box = 6;

	public static int s01_books = 7;

	public static int s01_bed = 8;

	public static int s01_desk = 9;

	public static int s01_drawer = 10;

	public static int s01_picture = 11;

	public static int s01_door = 12;

	public static int s02_wardrobe = 13;

	public static int s02_shoes = 14;

	public static int s02_front_door = 15;

	public static int s02_telephone = 16;

	public static int s02_keys = 17;

	public static int s02_wc_door = 18;

	public static int s02_switch = 19;

	public static int s02_bathroom_door = 20;

	public static int s03_door = 21;

	public static int s03_lamp = 22;

	public static int s03_switch = 23;

	public static int s03_paper_holder = 24;

	public static int s03_paper = 25;

	public static int s03_brush = 26;

	public static int s03_toilet = 27;

	public static int s03_chain = 28;

	public static int s03_tank = 29;

	public static int s05_trash = 30;
	public static int s05_newspaper = 31;
	public static int s05_dailies = 32;
	public static int s05_hobo_hat = 33;

	public static int s06_seat = 34;
	public static int s06_trash = 35;
	public static int s06_timetable = 36;
	public static int s06_ball = 37;
	public static int s06_bus = 38;
	public static int s06_front_door = 39;
	public static int s06_back_door = 40;

	public static int s08_sign = 41;
	public static int s08_advertisments = 42;
	public static int s08_electric_box = 43;
	public static int s08_cannal = 44;
	public static int s08_punk = 45;
	public static int s09_clubchair = 46;
	public static int s09_pipe = 47;
	public static int s09_szurdi = 48;
	public static int s09_snips = 49;

	public static int s10_socket = 50;
	public static int s10_switch = 51;
	public static int s10_trash = 52;
	public static int s10_rubbish = 53;
	public static int s10_coat = 54;
	public static int s10_glass = 55;
	public static int s10_knife = 56;
	public static int s10_marsal = 57;
	public static int s04_house_door = 58;
	public static int s04_door_phone = 59;
	public static int s04_nonstop = 60;
	public static int s04_poster = 61;
	public static int s05_hobo = 62;

	public static int items_sleeve = 63;
	public static int items_sleeve_key = 64;
	public static int items_toilet_brush = 65;
	public static int items_toilet_paper = 66;
	public static int items_toilet_chain = 67;
	public static int items_ticket = 68;
	public static int items_used_ticket = 69;
	public static int items_cigarette = 70;
	public static int items_snips = 71;
	public static int items_money = 72;
	public static int items_alcohol = 73;
	public static int items_sandwitch = 74;
	public static int items_newspaper = 75;
	public static int items_fuse = 76;
	public static int none = 77;

	// Újságos ///
	private static String u1 = "Szervusz, mit szeretnél?";

	private static String u2 = "2000 Ft lesz";

	private static String u3 = "Igen, így felment.";

	private static String u4 = "200 Ft lesz";

	private static String u5 = "Ott van mind a kirakatban, nézd meg";

	private static String u6 = "Nincs  mit.";

	private static String u7 = "Persze, tudom, de már beletörõdtem.";

	private static String u8 = "Régebben hívtam a rendõrséget, vagy elzavartam õket.";

	private static String u9 = "De mindig visszajönnek, nem tehetek semmit.";

	private static String u10 = "Á, hagyd, kisfiú, inkább dobd be neki a visszajáró apródat.";

	private static String u11 = "Nem tudom, fiam, nem tartom számon az árakat.";

	private static String u12 = "És milyen újságot szeretnél?";

	private static String u13 = "A Délmagyar különkiadásra gondolsz?";

	private static String u14 = "300 Ft lesz";

	private static String u15 = "Még egyet?";

	private static String u16 = "Dehogynem. Tessék, 300 Ft lesz.";

	private static String u17 = "Akkor gyere vissza, ha lesz.";

	private static String u18 = "Szervusz.";

	private static String u19 = "Akkor sajnos nem tudok jegyet adni";

	private static String u20 = "Jól van, te tudod.";
	private static String u21 = "Akkor nem adhatok bérletet sajnos.";

	private static String u22 = "Köszönöm.";

	private static String u23 = "Akkor sajnos nem tudsz újságot venni.";

	private static String u24 = "Nincs.";

	private static String u25 = "Ok, mindegy.";

	private static String u26 = "Szégyeld magad! Ilyen fiatalon és fényes nappal inni!";

	private static String u27 = "Helyes. Ezt is jobb ha kiöntöd!";

	private static String u28 = "Ejnye kisfiú! Ilyen korban nem szabad dohányozni!";

	private static String u29 = "Akkor vigyed innen.";

	private static String u30 = "Köszönöm fiam, nem kérek.";

	private static String u31 = "Fúj, annak van ilyen szaga?";

	private static String u32 = "Itt a jegyed.";

	private static String u33 = "Az újságod.";

	public static List<String> newsAgent = Arrays.asList(u1, u2, u3, u4, u5, u6,
			u7, u8, u9, u10, u11, u12, u13, u14, u15, u16, u17, u18, u19, u20,
			u21, u22, u23, u24, u25, u26, u27, u28, u29, u30, u31, u32, u33);

	public static String money_examine2(int money) {
		return "Hajszálpontosan " + money + " Forint.";
	}
}
