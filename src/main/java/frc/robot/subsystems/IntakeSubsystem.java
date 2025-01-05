package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    // Inake
    private final SparkMax        groundMotor = new SparkMax(IntakeConstants.GROUND_MOTOR_PORT, MotorType.kBrushed);
    // lights
    private final LightsSubsystem lightsSubsystem;

    private double                groundSpeed = 0;

    /** Creates a new ShooterSubsystem. */
    public IntakeSubsystem(LightsSubsystem lightsSubsystem, ShooterSubsystem shooterSubsystem) {

        this.lightsSubsystem = lightsSubsystem;

        // Configure the SparkMax
        SparkMaxConfig config = new SparkMaxConfig();

        config.idleMode(IdleMode.kBrake)
            .smartCurrentLimit(80)
            .disableFollowerMode();

        groundMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
