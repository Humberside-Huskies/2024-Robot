package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.SafetyConstants;

public class DriveSubsystem extends SubsystemBase {
    private final SlewRateLimiter leftLimiter        = new SlewRateLimiter(DriveConstants.SLEW_LIMIT);
    private final SlewRateLimiter rightLimiter       = new SlewRateLimiter(DriveConstants.SLEW_LIMIT);
    private final AHRS            gyro               = new AHRS(NavXComType.kMXP_SPI);

    // The motors on the left side of the drive.
    private final SparkMax        leftPrimaryMotor   = new SparkMax(DriveConstants.LEFT_MOTOR_PORT, MotorType.kBrushless);
    private final SparkMax        leftFollowerMotor  = new SparkMax(DriveConstants.LEFT_MOTOR_PORT + 1, MotorType.kBrushless);

    private SparkMaxConfig        leftMotorConfig    = new SparkMaxConfig();
    private SparkMaxConfig        rightMotorConfig   = new SparkMaxConfig();

    // The motors on the right side of the drive.
    private final SparkMax        rightPrimaryMotor  = new SparkMax(DriveConstants.RIGHT_MOTOR_PORT, MotorType.kBrushless);
    private final SparkMax        rightFollowerMotor = new SparkMax(DriveConstants.RIGHT_MOTOR_PORT + 1, MotorType.kBrushless);

    // Motor speeds
    private double                leftSpeed          = 0;
    private double                rightSpeed         = 0;

    private double                encoderOffset      = 0;

    /** Creates a new DriveSubsystem. */
    public DriveSubsystem() {

        /*
         * Configure the SparkMax controllers for the left and right.
         */
        leftMotorConfig.idleMode(IdleMode.kCoast)
            .inverted(DriveConstants.LEFT_MOTOR_REVERSED)
            .smartCurrentLimit(80)
            .disableFollowerMode();

        leftPrimaryMotor.configure(leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leftFollowerMotor.configure(leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


        /*
         * Configure the SparkMax controllers for the left and right.
         */
        rightMotorConfig.idleMode(IdleMode.kCoast)
            .inverted(DriveConstants.RIGHT_MOTOR_REVERSED)
            .smartCurrentLimit(80)
            .disableFollowerMode();

        rightPrimaryMotor.getEncoder().setPosition(0);
        leftPrimaryMotor.getEncoder().setPosition(0);

        setMotorsBreak();
    }

    public void setMotorsBreak() {

        // Temporarily set the idle mode to break, but do not burn the flash
        leftMotorConfig.idleMode(IdleMode.kBrake);
        leftPrimaryMotor.configure(leftMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        leftFollowerMotor.configure(leftMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        rightMotorConfig.idleMode(IdleMode.kBrake);
        rightPrimaryMotor.configure(rightMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        rightFollowerMotor.configure(rightMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void setMotorsCoast() {

        // Temporarily set the idle mode to coast, but do not burn the flash
        leftMotorConfig.idleMode(IdleMode.kCoast);
        leftPrimaryMotor.configure(leftMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        leftFollowerMotor.configure(leftMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        rightMotorConfig.idleMode(IdleMode.kCoast);
        rightPrimaryMotor.configure(rightMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        rightFollowerMotor.configure(rightMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    /**
     * Set the left and right speed of the primary and follower motors
     *
     * @param leftSpeed
     * @param rightSpeed
     */
    public void setMotorSpeeds(double leftSpeed, double rightSpeed) {

        leftSpeed  = leftLimiter.calculate(leftSpeed);
        rightSpeed = rightLimiter.calculate(rightSpeed);

        leftPrimaryMotor.set(leftSpeed * SafetyConstants.DRIVE_SPEED_MULTIPLIER);
        rightPrimaryMotor.set(rightSpeed * SafetyConstants.DRIVE_SPEED_MULTIPLIER);

        // NOTE: The follower motors are set to follow the primary motors
        leftFollowerMotor.set(leftSpeed * SafetyConstants.DRIVE_SPEED_MULTIPLIER);
        rightFollowerMotor.set(rightSpeed * SafetyConstants.DRIVE_SPEED_MULTIPLIER);

        // save locally for periodic reporting to dashboard
        this.leftSpeed  = leftSpeed * SafetyConstants.DRIVE_SPEED_MULTIPLIER;
        this.rightSpeed = rightSpeed * SafetyConstants.DRIVE_SPEED_MULTIPLIER;
    }

    public void resetEncoderDistance() {
        // clear the offset
        encoderOffset = 0;
        // get a new offset
        encoderOffset = -getEncoderDistance();
    }

    public double getEncoderDistance() {
        return leftPrimaryMotor.getEncoder().getPosition()
            + rightPrimaryMotor.getEncoder().getPosition()
            + encoderOffset;
    }

    public double getDistanceCm() {
        return getEncoderDistance() * DriveConstants.CMS_PER_ENCODER_COUNT;
    }

    public double getGyroAngle() {
        return Math.floorMod((int) this.gyro.getAngle(), 360);
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
        setMotorSpeeds(0, 0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */

        SmartDashboard.putNumber("Right Motor", rightSpeed);
        SmartDashboard.putNumber("Left  Motor", leftSpeed);
        SmartDashboard.putNumber("Gyro angle", this.getGyroAngle());
        SmartDashboard.putNumber("Encoder Distance", getEncoderDistance());
        SmartDashboard.putNumber("Distance (cm)", getDistanceCm());
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(" [").append(Math.round(leftSpeed * 100.0d) / 100.0d)
            .append(',').append(Math.round(rightSpeed * 100.0d) / 100.0d).append(']');

        return sb.toString();
    }
}
