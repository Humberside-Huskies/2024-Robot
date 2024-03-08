package frc.robot.commands.lights;

import frc.robot.commands.LoggingCommand;
import frc.robot.operator.GameController;
import frc.robot.subsystems.LightsSubsystem;

public class DefaultLightsCommand extends LoggingCommand {

    // private final LEDSubsystem ledSubsystem;
    private final LightsSubsystem lightsSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param LightsSubsystem The subsystem used by this command.
     */
    public DefaultLightsCommand(GameController driverController,
        LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(lightsSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        lightsSubsystem.setLEDColor(1, 0, 1);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}