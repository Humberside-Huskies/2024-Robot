package frc.robot.commands.drive;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveRotateCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;
    private final double         gyroAngle;
    private final double         driveSpeed;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveRotateCommand(DriveSubsystem driveSubsystem, double gyroAngle, double driveSpeed) {

        this.driveSubsystem = driveSubsystem;
        this.gyroAngle      = gyroAngle;
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
        driveSubsystem.setMotorSpeeds(this.driveSpeed, -this.driveSpeed);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (Math.abs(this.gyroAngle - driveSubsystem.getGyroAngle()) < 5)
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