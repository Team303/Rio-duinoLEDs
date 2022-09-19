package com.team303.rioduino.protocol;

import java.nio.ByteBuffer;
import java.util.HashMap;

public abstract class ProtoPacket {
	private static final int PACKET_DATA_BUFFER_SIZE = 1024;

	private static final HashMap<Integer, Class<?>> PACKET_ID_MAP = new HashMap<>();

	private ByteBuffer packetBuf;
	private int packetID;

	public ProtoPacket(int packetID) {
		if (this.getClass().getAnnotation(InternalPacket.class) == null) {
			// Keep track of classes and packet ids for custom packets
			PACKET_ID_MAP.putIfAbsent(packetID, this.getClass());

			// If the id comes from a class we don't expect, throw an error about an id
			// collision
			if (PACKET_ID_MAP.get(packetID) != this.getClass()) {
				throw new RuntimeException(
						String.format("Packet ID Collision! Packet ID 0x%s is already used by class %s",
								Integer.toHexString(packetID), PACKET_ID_MAP.get(packetID).getName()));
			}
		}

		// Assign packet id for serialization
		this.packetID = packetID;

		// Arbitrary packet capacity size
		this.packetBuf = ByteBuffer.allocate(PACKET_DATA_BUFFER_SIZE);
	}

	public byte[] serialize() {
		// Allocate a buffer 8 bytes bigger to accomidate for the packet id, length, and
		// internal marker
		ByteBuffer buffer = ByteBuffer.allocate(PACKET_DATA_BUFFER_SIZE + 4 + 4 + 1);

		// Write packet id
		buffer.putInt(packetID);

		// Write if is internal
		if (this.getClass().getAnnotation(InternalPacket.class) != null) {
			buffer.put((byte) 0x01);
		} else {
			buffer.put((byte) 0x00);
		}

		// Write length of following packet data
		buffer.putInt(packetBuf.position());

		// Write packet data buffer
		buffer.put(packetBuf.array());

		// Reset the packet buffer
		this.packetBuf = ByteBuffer.allocate(PACKET_DATA_BUFFER_SIZE);

		// Return the backing byte array
		return buffer.array();
	}

	/**
	 * Writes the given value to the packet buffer as a 32-bit int
	 * 
	 * @param value
	 */
	void writeInt(int value) {
		packetBuf.putInt(value);
	}

	/**
	 * Writes the given value to the packet buffer as a 32-bit int
	 * 
	 * @param value
	 */
	void writeBytes(byte[] bytes) {
		packetBuf.put(bytes);
	}
}
