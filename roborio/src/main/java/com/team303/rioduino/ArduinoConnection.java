package com.team303.rioduino;

import com.team303.rioduino.protocol.ClearPacket;
import com.team303.rioduino.protocol.ProtoPacket;
import com.team303.rioduino.protocol.RawLedBufferPacket;
import com.team303.rioduino.protocol.SolidColorPacket;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.util.Color;

public class ArduinoConnection {

	/**
	 * Serial communication port for sending data to the arduino over USB
	 */
	private final SerialPort arduino;

	private int numLEDs;

	/**
	 * @param port         - The usb port on the RoboRIO that the arduino is
	 *                     connected to
	 * @param bufferLength - The number of LEDs in your LED strip
	 */
	public ArduinoConnection(SerialPort.Port port, int bufferLength) {
		this.numLEDs = bufferLength;

		SerialPort arduino = null;

		// Assures that even if the connection cant be established, that the robot can
		// still function normally
		try {
			// Create the serial connection
			arduino = new SerialPort(9600, port);

			// Only flush data when the buffer is full
			arduino.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushWhenFull);
			// We only want to write this many bytes over the buffer
			arduino.setWriteBufferSize(bufferLength * 3);

			// Send the new buffer to the arduino to set all LEDs to off
			clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.arduino = arduino;
	}

	/**
	 * Sets the LED Strip pixels to the contents of this buffer
	 * 
	 * @throws IllegalArgumentException if the length of the buffer does not match
	 *                                  the number of LEDs configured
	 */
	public void writeBuffer(AddressableLEDBuffer ledBuffer) {
		if (ledBuffer.getLength() != this.numLEDs) {
			throw new IllegalArgumentException("ledBuffer length must match pre-configured strip length");
		}

		this.sendPacket(new RawLedBufferPacket(ledBuffer));
	}

	/**
	 * Clears the entire LED strip setting all the pixels to off
	 */
	public void clear() {
		this.sendPacket(new ClearPacket());
	}

	/**
	 * Sets the entire strip to a single solid color
	 * @param color The color to set to
	 */
	public void setSolidColor(Color color) {
		this.sendPacket(new SolidColorPacket(color));
	}

	/**
	 * Send a protocol packet to the arduino
	 * 
	 * @param packet The packet to serialize and send
	 */
	public void sendPacket(ProtoPacket packet) {
		// If there is no arduino connected, just bail
		if (arduino == null)
			return;

		byte[] buff = packet.serialize();

		// Write the byte buffer and force the serial connection to flush its contents
		arduino.write(buff, buff.length);
		arduino.flush();
	}

}