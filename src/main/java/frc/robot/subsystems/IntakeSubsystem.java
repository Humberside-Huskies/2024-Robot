package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    // Inake
    private final CANSparkMax     groundMotor = new CANSparkMax(IntakeConstants.GROUND_MOTOR_PORT, MotorType.kBrushed);
    // lights
    private final LightsSubsystem lightsSubsystem;

    private double                groundSpeed = 0;

    /** Creates a new ShooterSubsystem. */
    public IntakeSubsystem(LightsSubsystem lightsSubsystem, ShooterSubsystem shooterSubsystem) {
        this.lightsSubsystem = lightsSubsystem;
        groundMotor.setSmartCurrentLimit(40);
    }

    /**
     * Set the speed of the ground motor
     *
     * @param groundSpeed
     */
    public void setGroundSpeed(double groundSpeed) {

        this.groundSpeed = groundSpeed;

        groundMotor.set(this.groundSpeed);
    }


    /** Safely stop the subsystem from moving */
    public void stop() {
        setGroundSpeed(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Ground Motor", this.groundSpeed);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(", ground speed ").append(Math.round(groundSpeed * 100.0d) / 100.0d);


        return sb.toString();
    }


}
