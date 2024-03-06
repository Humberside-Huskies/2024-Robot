package frc.robot.commands.auto;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DriveSpeakerCommand extends LoggingCommand {

    private final DriveSubsystem   driveSubsystem;
    private final ShooterSubsystem shooterSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveSpeakerCommand(DriveSubsystem driveSubsystem, ShooterSubsystem shooterSubsystem) {

        this.driveSubsystem   = driveSubsystem;
        this.shooterSubsystem = shooterSubsystem;

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
        if (isTimeoutExceeded(4)) {
            shooterSubsystem.setShooterSpeed(0);
            shooterSubsystem.setFeederSpeed(0);
            driveSubsystem.setMotorSpeeds(0.1, -0.1);
        }
        else if (isTimeoutExceeded(2)) {

            driveSubsystem.setMotorSpeeds(0, 0);
            System.out.println("drive forward");
        }
        else {
            System.out.println("shooting in speaker");
            shooterSubsystem.setShooterSpeed(ShooterConstants.SHOOTER_SHOOT_SPEAKER_SPEED);
            shooterSubsystem.setFeederSpeed(ShooterConstants.FEEDER_SHOOT_SPEAKER_SPEED);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.

        if (isTimeoutExceeded(6))
            return true;
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}