package frc.robot.commands.auto;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DriveAmpSide extends LoggingCommand {

    private final DriveSubsystem   driveSubsystem;
    private final ShooterSubsystem shooterSubsystem;
    private double                 rotation = 0;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveAmpSide(DriveSubsystem driveSubsystem, ShooterSubsystem shooterSubsystem, double rotation) {

        this.driveSubsystem   = driveSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.rotation         = rotation;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (isTimeoutExceeded(8)) {
            driveSubsystem.setMotorSpeeds(0, 0);
        }
        else if (isTimeoutExceeded(5)) {
            shooterSubsystem.setShooterSpeed(0);
            shooterSubsystem.setFeederSpeed(0);

            if (driveSubsystem.getGyroAngle() < 180)
                driveSubsystem.setMotorSpeeds(rotation, -rotation);

        }
        else if (isTimeoutExceeded(4)) {
            driveSubsystem.setMotorSpeeds(0, 0);
        }
        else if (isTimeoutExceeded(1.5)) {
            driveSubsystem.setMotorSpeeds(-0.2, -0.2);
        }
        else if (isTimeoutExceeded(0.5)) {
            System.out.println("shooting in speaker");
            driveSubsystem.setMotorSpeeds(0.0, 0.0);
            shooterSubsystem.setShooterSpeed(ShooterConstants.SHOOTER_SHOOT_SPEAKER_SPEED);
            shooterSubsystem.setFeederSpeed(ShooterConstants.FEEDER_SHOOT_SPEAKER_SPEED);

        }
        else {
            driveSubsystem.setMotorSpeeds(0, 0);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.

        if (isTimeoutExceeded(12))
            return true;
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}