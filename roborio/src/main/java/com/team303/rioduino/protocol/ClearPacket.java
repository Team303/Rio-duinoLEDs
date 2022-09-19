package com.team303.rioduino.protocol;

/**
 * Custom packet to tell the arduino to clear all its pixels and stop the current LED program
 * 
 * Used for resetting state
 */
@InternalPacket
public class ClearPacket extends ProtoPacket {

	/**
	 * Doesnt do much because this is all handled on the arduino side
	 */
	public ClearPacket() {
		super(0x01);
	}
		
}
