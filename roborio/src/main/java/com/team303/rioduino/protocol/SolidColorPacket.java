package com.team303.rioduino.protocol;

import edu.wpi.first.wpilibj.util.Color;

@InternalPacket
public class SolidColorPacket extends ProtoPacket {
	public final Color color;

	public SolidColorPacket(Color color) {
		super(0x02);

		this.color = color;
	}

	@Override
	public byte[] serialize() {
		// Put the RGB values in a byte tuple and write them
		byte[] rgb = new byte[3];

		rgb[0] = (byte) (color.red * 255);
		rgb[1] = (byte) (color.blue * 255);
		rgb[2] = (byte) (color.green * 255);

		writeBytes(rgb);

		return super.serialize();
	}
}
