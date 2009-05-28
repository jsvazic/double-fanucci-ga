package com.arm.fanucci;

/**
 * Interface defining some standard Fanucci card properties.
 * 
 * @author jsvazic
 */
public interface IFanucci {
	/** Constant for an unknown Fanucci suit. */
	public final short SUIT_UNKNOWN = -1;
	
	/** Constant for the Ears Fanucci suit. */
	public final short SUIT_EARS = 6;
	
	/** Constant for the Mazes Fanucci suit. */
	public final short SUIT_MAZES = 2;
	
	/** Constant for the Scythes Fanucci suit. */
	public final short SUIT_SCYTHES = 8;
	
	/** Constant for the Hives Fanucci suit. */
	public final short SUIT_HIVES = 4;
	
	/** Constant for the Inkblots Fanucci suit. */
	public final short SUIT_INKBLOTS = 5;

	/** Constant for the Plungers Fanucci suit. */
	public final short SUIT_PLUNGERS = 11;
	
	/** Constant for the Books Fanucci suit. */
	public final short SUIT_BOOKS = 10;
	
	/** Constant for the Zurfs Fanucci suit. */
	public final short SUIT_ZURFS = 9;
	
	/** Constant for the Tops Fanucci suit. */
	public final short SUIT_TOPS = 12;
	
	/** Constant for the Rain Fanucci suit. */
	public final short SUIT_RAIN = 13;
	
	/** Constant for the Faces Fanucci suit. */
	public final short SUIT_FACES = 14;
		
	/** Constant for the Bugs Fanucci suit. */
	public final short SUIT_BUGS = 0;
	
	/** Constant for the Time Fanucci suit. */
	public final short SUIT_TIME = 7;
	
	/** Constant for the Lamps Fanucci suit. */
	public final short SUIT_LAMPS = 1;
	
	/** Constant for the Fromps Fanucci suit. */
	public final short SUIT_FROMPS = 3;

	/** Constance for the Fanucci face cards. */
	public final short SUIT_FACE_ALL = 99;

	/** Power associated with a Naught of <i>X</i> card. */
	public final short POWER_NAUGHT = 2;

	/** Power associated with a 1 of <i>X</i> card. */
	public final short POWER_ONE = 4;

	/** Power associated with a 2 of <i>X</i> card. */
	public final short POWER_TWO = 8;
	
	/** Power associated with a 3 of <i>X</i> card. */
	public final short POWER_THREE = 16;
	
	/** Power associated with a 4 of <i>X</i> card. */
	public final short POWER_FOUR = 20;
	
	/** Power associated with a 5 of <i>X</i> card. */
	public final short POWER_FIVE = 24;
	
	/** Power associated with a 6 of <i>X</i> card. */
	public final short POWER_SIX = 30;
	
	/** Power associated with a 7 of <i>X</i> card. */
	public final short POWER_SEVEN = 36;
	
	/** Power associated with a 8 of <i>X</i> card. */
	public final short POWER_EIGHT = 40;
	
	/** Power associated with a 9 of <i>X</i> card. */
	public final short POWER_NINE = 44;
	
	/** Power associated with an Infinity of <i>X</i> card. */
	public final short POWER_INFINITY = 50;
	
	/** Power associated with an unknown card. */
	public final short POWER_UNKNOWN = 0;
	
	/** Constant for the Beauty face card. */
	public final short FACE_BEAUTY = 51;

	/** Constant for the Death face card. */
	public final short FACE_DEATH = 52;

	/** Constant for the Granola face card. */
	public final short FACE_GRANOLA = 53;
	
	/** Constant for the Grue face card. */
	public final short FACE_GRUE = 54;
	
	/** Constant for the Jester face card. */
	public final short FACE_JESTER = 55;
	
	/** Constant for the Light face card. */
	public final short FACE_LIGHT = 56;
	
	/** Constant for the Lobster face card. */
	public final short FACE_LOBSTER = 57;
	
	/** Constant for the Snail face card. */
	public final short FACE_SNAIL = 58;
	
	/** Constant for the Time face card. */
	public final short FACE_TIME = 59;
	
	/** Constant for the first group: Bugs, Time. */
	public final short GROUP_1 = 0;

	/** Constant for the second group: Lamps, Fromps. */
	public final short GROUP_2 = 1;
	
	/** Constant for the third group: Hives, Inkblots. */
	public final short GROUP_3 = 2;
	
	/** Constant for the fourth group: Mazes, Ears, Scythes. */
	public final short GROUP_4 = 3;
	
	/** Constant for the fifth group: Zurfs, Books, Plungers. */
	public final short GROUP_5 = 4;
	
	/** Constant for the sixth group: Tops, Rain, Faces. */
	public final short GROUP_6 = 5;
	
	/** Constant for the face card group. */
	public final short GROUP_FACE_CARDS = 6;
	
	/** Constant for the unknown group. */
	public final short GROUP_UNKNOWN = -1;
}