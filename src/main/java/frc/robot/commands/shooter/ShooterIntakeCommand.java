package frc.robot.commands.shooter;

import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterIntakeCommand extends LoggingCommand {

    private final ShooterSubsystem shooterSubsystem;
    private final OperatorInput    operatorInput;
    private final IntakeSubsystem  intakeSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public ShooterIntakeCommand(ShooterSubsystem shooterSubsystem, OperatorInput operatorInput, IntakeSubsystem intakeSubsystem) {

        this.shooterSubsystem = shooterSubsystem;
        this.operatorInput    = operatorInput;
        this.intakeSubsystem  = intakeSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooterSubsystem);
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
        shooterSubsystem.setFeederSpeed(-ShooterConstants.SHOOTER_SHOOT_SPEAKER_SPEED);
        shooterSubsystem.setShooterSpeed(-ShooterConstants.FEEDER_SHOOT_SPEAKER_SPEED);
        intakeSubsystem.setGroundSpeed(-IntakeConstants.GROUND_INTAKE_SPEED);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        if (shooterSubsystem.isNoteLoaded()) {
            setFinishReason("Sensor Detected, Intake Stopped");
            return true;
        }
        if (isTimeoutExceeded(4)) {
            setFinishReason("Intake timed out");
            return true;
        }
        return false;

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
        intakeSubsystem.stop();
        logCommandEnd(interrupted);
    }
}