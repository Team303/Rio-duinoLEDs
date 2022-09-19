# Example: Using a Raw LED Buffer

This example showcases how to set the LED strip to the contents of a `AddressableLEDBuffer`.

```java
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
	 * Create an alternating pattern (a, b, a, b, ...)
	 */
	public alternate(Color color1, Color color2) {
		AddressableLEDBuffer buffer = new AddressableLEDBuffer(LED_COUNT);

		for (int i = 0; i < LED_COUNT; i++) {
			buffer.setLED(i, i % 2 == 0 ? color1 : color2);
		}

		arduino.writeBuffer(buffer);
	}
}
```

## Ok what is going on here?

We are creating a new Subsystem to manage our LED strip, and instantiating a new ArduinoConnection.

The `LEDSubsystem#alternate` method takes in 2 colors, and fills a buffer with an alternating color pattern of [a, b, a, b, ...].

Then, we simply call `ArduinoConnection#writeBuffer` which will do all the work to serialize the data and send it to the arduino automatically.
