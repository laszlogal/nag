package hu.norbisquest.nagbase.core;

public final class NAGColor {
	private int red;
	private int green;
	private int blue;
	private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public NAGColor(int red, int green, int blue) {
		this.setRed(red);
		this.setGreen(green);
		this.setBlue(blue);
	}
	private int getRed() {
		return red;
	}
	private void setRed(int red) {
		this.red = red;
	}
	private int getGreen() {
		return green;
	}
	private void setGreen(int green) {
		this.green = green;
	}
	private int getBlue() {
		return blue;
	}
	private void setBlue(int blue) {
		this.blue = blue;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}

		if (!(other instanceof NAGColor)) {
			return false;
		}

		NAGColor color = (NAGColor) other;
		return red == color.getRed() && green == color.getGreen()
				&& blue == color.getBlue();
	}

	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	public String toHexString() {
		byte r = (byte) getRed();
		byte g = (byte) getGreen();
		byte b = (byte) getBlue();

		// RED
        // look up high nibble char
        // GREEN
        // look up high nibble char
        // BLUE
        // look up high nibble char
        return "#" +
                hexChar[(r & 0xf0) >>> 4] +
                hexChar[r & 0x0f] + // look up low nibble char
                hexChar[(g & 0xf0) >>> 4] +
                hexChar[g & 0x0f] + // look up low nibble char
                hexChar[(b & 0xf0) >>> 4] +
                hexChar[b & 0x0f] // look up low nibble char
                ;
	}

}
