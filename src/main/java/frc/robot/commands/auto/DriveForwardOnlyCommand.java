package frc.robot.commands.auto;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DriveForwardOnlyCommand extends LoggingCommand {

    private final DriveSubsystem   driveSubsystem;
    private final ShooterSubsystem shooterSubsystem;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveForwardOnlyCommand(DriveSubsystem driveSubsystem, ShooterSubsystem shooterSubsystem) {

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
        // wait 10 second to move forward
        if (isTimeoutExceeded(12)) {
            driveSubsystem.setMotorSpeeds(0.0, 0.0);
        }
        // stop
        else if (isTimeoutExceeded(10)) {
            driveSubsystem.setMotorSpeeds(0.5, 0.5);
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