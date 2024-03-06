package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.ShooterSubsystem;

public class DefaultIntakeCommand extends LoggingCommand {

    private final ShooterSubsystem shooterSubsystem;
    private final OperatorInput    operatorInput;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public DefaultIntakeCommand(ShooterSubsystem shooterSubsystem, OperatorInput operatorInput) {

        this.shooterSubsystem = shooterSubsystem;
        this.operatorInput    = operatorInput;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooterSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Intake begin");
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Run the shooter wheel
        shooterSubsystem.setFeederSpeed(-ShooterConstants.SHOOTER_SHOOT_SPEAKER_SPEED);
        shooterSubsystem.setShooterSpeed(-ShooterConstants.FEEDER_SHOOT_SPEAKER_SPEED);


    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {


        // Checks if the note has triggered the laser. If it hasn't runs Intake motors for 4 seconds
        // and then stops
        if (operatorInput.isAltIntake()) {
            setFinishReason("Intake forcestop");

            return true;
        }
        if (shooterSubsystem.isNoteLoaded() || isTimeoutExceeded(4)) {
            setFinishReason("Intake no more");
            return true;

        }
        return false;

    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
        logCommandEnd(interrupted);
    }
}