package frc.robot.commands.drive;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveForwardCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;
    private final double         executeTime;
    private final double         driveSpeed;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveForwardCommand(DriveSubsystem driveSubsystem, double executeTime, double driveSpeed) {

        this.driveSubsystem = driveSubsystem;
        this.executeTime    = executeTime;
        this.driveSpeed     = driveSpeed;

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
        driveSubsystem.setMotorSpeeds(this.driveSpeed, this.driveSpeed);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (isTimeoutExceeded(this.executeTime))
            return true;
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
        logCommandEnd(interrupted);
    }
}