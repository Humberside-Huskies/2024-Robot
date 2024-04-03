package frc.robot.commands.intake;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class BackUpIntakeCommand extends LoggingCommand {

    private final IntakeSubsystem intakeSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public BackUpIntakeCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // Run the shooter wheel
        intakeSubsystem.setGroundSpeed(-0.2);
        // IntakeSubsystem.setShooterSpeed(Inta)
        // If this command has been running for 0.5 seconds, then start the feeder

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // Stop this command after 2.5 seconds total
        // FIX check if note is loaded if it isn't spin for 0.5 second more and then stop
        if (isTimeoutExceeded(0.1)) {
            return true;
        }
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stop();
        logCommandEnd(interrupted);
    }
}