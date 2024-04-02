package frc.robot.commands.intake;

import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;

public class DefaultGroundIntakeCommand extends LoggingCommand {

    private final IntakeSubsystem intakeSubsystem;
    private final LightsSubsystem lightsSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public DefaultGroundIntakeCommand(IntakeSubsystem intakeSubsystem, LightsSubsystem lightsSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.lightsSubsystem = lightsSubsystem;

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
        intakeSubsystem.setGroundSpeed(IntakeConstants.GROUND_INTAKE_SPEED);
        // If this command has been running for 0.5 seconds, then start the feeder

        lightsSubsystem.setLEDRainbow();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // Stop this command after 2.5 seconds total
        // FIX check if note is loaded if it isn't spin for 0.5 second more and then stop
        if (isTimeoutExceeded(2.5)) {

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