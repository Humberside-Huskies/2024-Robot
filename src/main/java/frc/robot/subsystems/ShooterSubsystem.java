package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // Motors
    private final CANSparkMax     shooterMotor = new CANSparkMax(ShooterConstants.SHOOTER_MOTOR_PORT, MotorType.kBrushed);
    private final CANSparkMax     feederMotor  = new CANSparkMax(ShooterConstants.FEEDER_MOTOR_PORT, MotorType.kBrushed);

    // sensor
    private final DigitalInput    input        = new DigitalInput(0);

    // lights
    private final LightsSubsystem lightsSubsystem;

    private double                shooterSpeed = 0;
    private double                feederSpeed  = 0;

    /** Creates a new ShooterSubsystem. */
    public ShooterSubsystem(LightsSubsystem lightsSubsystem) {
        this.lightsSubsystem = lightsSubsystem;
        shooterMotor.setSmartCurrentLimit(40);
        feederMotor.setSmartCurrentLimit(40);
    }

    /**
     * Set the speed of the shooter motor
     *
     * @param shooterSpeed
     */
    public void setShooterSpeed(double shooterSpeed) {

        this.shooterSpeed = shooterSpeed;

        shooterMotor.set(this.shooterSpeed);
    }

    /**
     * Set the speed of the feeder motor
     *
     * @param feederSpeed
     */
    public void setFeederSpeed(double feederSpeed) {

        this.feederSpeed = feederSpeed;

        feederMotor.set(this.feederSpeed);
    }


    public boolean isNoteLoaded() {
        return !this.input.get();
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
        setShooterSpeed(0);
        setFeederSpeed(0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */
        SmartDashboard.putNumber("Shooter Motor", this.shooterSpeed);
        SmartDashboard.putNumber("Feeder Motor", this.feederSpeed);
        SmartDashboard.putBoolean("Sensor", this.isNoteLoaded());

        // Set the lights based on a note
        if (shooterSpeed == 0 && feederSpeed == 0)
            lightsSubsystem.setNote(isNoteLoaded());
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(" : shooter speed ").append(Math.round(shooterSpeed * 100.0d) / 100.0d)
            .append(", feeder speed ").append(Math.round(feederSpeed * 100.0d) / 100.0d);


        return sb.toString();
    }
}
