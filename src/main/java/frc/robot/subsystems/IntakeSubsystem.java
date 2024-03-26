package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    // Loader
    private final CANSparkMax     shooterMotor = new CANSparkMax(IntakeConstants.SHOOTER_MOTOR_PORT, MotorType.kBrushed);
    private final CANSparkMax     feederMotor  = new CANSparkMax(IntakeConstants.FEEDER_MOTOR_PORT, MotorType.kBrushed);

    // Inake
    private final CANSparkMax     groundMotor  = new CANSparkMax(IntakeConstants.GROUND_MOTOR_PORT, MotorType.kBrushless);

    // lights
    private final LightsSubsystem lightsSubsystem;

    private double                shooterSpeed = 0;
    private double                feederSpeed  = 0;
    private double                groundSpeed  = 0;

    /** Creates a new ShooterSubsystem. */
    public IntakeSubsystem(LightsSubsystem lightsSubsystem) {
        this.lightsSubsystem = lightsSubsystem;
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
        setShooterSpeed(0);
        setFeederSpeed(0);
        setGroundSpeed(0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */
        SmartDashboard.putNumber("Shooter Motor", this.shooterSpeed);
        SmartDashboard.putNumber("Feeder Motor", this.feederSpeed);
        SmartDashboard.putNumber("Ground Motor", this.groundSpeed);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(" : shooter speed ").append(Math.round(shooterSpeed * 100.0d) / 100.0d)
            .append(", feeder speed ").append(Math.round(feederSpeed * 100.0d) / 100.0d)
            .append(", ground speed ").append(Math.round(groundSpeed * 100.0d) / 100.0d);


        return sb.toString();
    }
}
