# Example: Creating a Custom Packet

If you want to add your own custom packet to the protocol its very simple. Use cases for this are that you want to do a specific LED animation, but have the computation be done off the RoboRIO. This is helpful not only because it saves processing power, but it also means you are not limited to the tick rate of the Robot. 

To extend the protocol with your own custom packet, you need to assign a packet ID for that specific packet so that it can be properly parsed and deserialized on the Arduino. You will also need to implement packet and animation in C++ within the Arduino code.

```java
// FastBreathePacket.java
public class FastBreathePacket extends ProtoPacket {
	private Color color;
	
	public FastBreathePacket(Color color) {
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

```

```java
// LEDSubsystem.java
public class LEDSubsystem extends Subsystem {
	private static final int LED_COUNT = 100;

	/**
	 * Serial communication port for sending data to the arduino over USB
	 */
	private final ArduinoConnection arduino;

	public LEDSubsystem() {
		// Initialize new connection on USB port 1, and with 100 pixels
		this.arduino = new ArduinoConnection(Port.kUSB1, LED_COUNT);
	}

	/**
	 * Performs a high frame rate color fade animation
	 */
	public fastBreathe(Color color) {
		// Sends a packet to the arduino to be interpretted by the packet parser
		arduino.sendPacket(new FastBreathePacket(color));
	}
}
```

TODO explain this code

During execution, the Arduino is in one of 2 states. In the Slave state, the arduino does not do any computations on its own. It will only wait for more pixel data to be sent in the form of a packet. The Slave state can be entered by sending any variety of the default packets such as RawLedBufferPacket, ClearPacket, or SolidColorPacket. When the Arduino recieves these, it  stops any animations that are in progress and switches into the slave state.

The other state is the Mater state. In this mode, the arduino does all the compuations for the currently active animation on a specified interval. This state can be entered by sending one of the default animation packets, or by defining a custom animation and packet pair. When in the Master state, the currently schduled animation will be processed and executed by the `AnimationHandler`, and lifecycle methods will be called automatically.

TODO write the cpp code