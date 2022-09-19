package com.team303.rioduino.protocol;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/**
 * Changes the arduino into slave mode and does not update any pixels on its own
 * 
 * This packet tells the arduino to set every pixel to match the specified buffer
 */
@InternalPacket
public class RawLedBufferPacket extends ProtoPacket {
	public final AddressableLEDBuffer ledBuffer;

	public RawLedBufferPacket(AddressableLEDBuffer ledBuffer) {
		super(0x00);

		this.ledBuffer = ledBuffer;
	}

	@Override
	public byte[] serialize() {
		// Write pixel buffer length (in pixels not in bytes)
		writeInt(this.ledBuffer.getLength());

		// Create a byte buffer to store all the pixels
		byte[] buff = new byte[this.ledBuffer.getLength() * 3];

		for (var i = 0; i < ledBuffer.getLength(); i++) {
			buff[i * 3 + 0] = (byte) ledBuffer.getLED(i).red;
			buff[i * 3 + 1] = (byte) ledBuffer.getLED(i).green;
			buff[i * 3 + 2] = (byte) ledBuffer.getLED(i).blue;
		}

		writeBytes(buff);

		return super.serialize();
	}

}
