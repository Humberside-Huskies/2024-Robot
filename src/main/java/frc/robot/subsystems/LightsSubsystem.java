package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final AddressableLED       led                  = new AddressableLED(LightsConstants.LED_PORT);
    private final AddressableLEDBuffer ledBuffer            = new AddressableLEDBuffer(LightsConstants.NUM_LEDS);

    private int                        rainbowFirstPixelHue = 0;
    private boolean                    firsTime             = true;

    /** Creates a new LedSubsystem. */
    public LightsSubsystem() {

        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();

        this.setLEDColor(0, 0, 0);
    }

    // Set the LEDs on or off when you have a note
    public void setNote(boolean hasNote) {

        if (hasNote) {
            // setLEDPhilip();
            setLEDColor(0, 255, 0);
            // setLEDTony2();
        }
        else {
            setLEDColor(255, 0, 0);
        }
    }

    /**
     * Set the led color
     */
    private void setLEDColor(int red, int green, int blue) {

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setRGB(i, red, green, blue);
        }

        this.led.setData(ledBuffer);
    }

    public void setLEDRainbow() {
        rainbowFirstPixelHue -= 5;

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            int hue = (rainbowFirstPixelHue + (i * 180 / this.ledBuffer.getLength())) % 180;
            this.ledBuffer.setHSV(i, hue, 255, 120);

            rainbowFirstPixelHue += 3;
            // Check bounds
            rainbowFirstPixelHue %= 180;
        }

        this.led.setData(ledBuffer);
    }

    public void setLEDTony() {
        Random rand = new Random();

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setHSV(i, rand.nextInt(255), 255, 120);
        }

        this.led.setData(this.ledBuffer);

    }

    public void setLEDTony2() {
        Random rand = new Random();

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            if (rand.nextDouble() < 0.1) {
                Color color = this.ledBuffer.getLED(i);

                if (color.red != 255)
                    this.ledBuffer.setRGB(i, 255, 0, 0);
                else
                    this.ledBuffer.setRGB(i, 0, 0, 0);

            }
        }

        this.led.setData(this.ledBuffer);

    }

    public void setLEDPhilip() {
        if (firsTime) {
            for (int index = 0; index < this.ledBuffer.getLength(); index++) {
                this.ledBuffer.setLED(index, new Color(Math.random(), Math.random(), Math.random()));
            }
            firsTime = false;
        }
        for (int index = 0; index < this.ledBuffer.getLength(); index++) {
            this.ledBuffer.setLED(index, randomColorShift(this.ledBuffer.getLED(index)));
        }
        this.led.setData(this.ledBuffer);

    }

    private Color randomColorShift(Color aColor) {
        return new Color(randomShift(aColor.red), randomShift(aColor.green), randomShift(aColor.blue));
    }

    private double randomShift(double value) {
        double sign   = Math.random() >= 0.5 ? 1.0 : -1.0;
        double amount = Math.random() / 10;
        return MathUtil.clamp(value + sign * amount, 0, 1);
    }

    public boolean isAnimated() {
        return true;
    }


    @Override
    public void periodic() {
        /*
         * Update all dashboard values in the periodic routine
         */

        // setLEDPhilip();
    }

    @Override
    public String toString() {
        // Create an appropriate text readable string describing the state of the subsystem

        return "Tony was here... Shhhhhhh";
    }

}
