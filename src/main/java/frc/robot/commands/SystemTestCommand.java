package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.operator.GameController;
import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to cancel any running
 * commands
 */
public class SystemTestCommand extends LoggingCommand {

    private enum Motor {
        NONE,
        // Drive Motors
        LEFT_MOTOR_1, LEFT_MOTOR_2,
        RIGHT_MOTOR_1, RIGHT_MOTOR_2,
        // Shooter Motors
        FEEDER, SHOOTER
    }

    private final OperatorInput    operatorInput;
    private final ShooterSubsystem shooterSubsystem;
    private final DriveSubsystem   driveSubsystem;
    private final GameController   controller;

    private long                   startTime           = 0;
    private Motor                  selectedMotor       = Motor.NONE;
    private double                 povMotorSpeed       = 0;

    private boolean                previousLeftBumper  = false;
    private boolean                previousRightBumper = false;

    /**
     * System Test Command
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot
     * from moving.
     */
    public SystemTestCommand(OperatorInput operatorInput, DriveSubsystem driveSubsystem,
        ShooterSubsystem shooterSubsystem) {

        this.operatorInput    = operatorInput;
        this.driveSubsystem   = driveSubsystem;
        this.shooterSubsystem = shooterSubsystem;

        controller            = operatorInput.getDriverController();

        addRequirements(shooterSubsystem);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        /*
         * The SystemTestCommand is not interruptible, and prevents all other commands that try to
         * interrupt it. Only the cancel button will end the SystemTestCommand.
         */
        return InterruptionBehavior.kCancelIncoming;
    }

    @Override
    public void initialize() {

        logCommandStart();

        stopAllMotors();

        startTime = System.currentTimeMillis();

        SmartDashboard.putBoolean("Test Mode", true);
        SmartDashboard.putString("Test Motor", selectedMotor.toString());
        SmartDashboard.putNumber("Test Motor Speed", povMotorSpeed);

        clearMotorIndicators();
    }

    @Override
    public void execute() {

        /*
         * Use the bumpers to select the next / previous motor in the motor ring.
         *
         * Switching motors will cause all motors to stop
         */

        boolean rightBumper = controller.getRightBumper() && !previousRightBumper;
        previousRightBumper = controller.getRightBumper();

        boolean leftBumper = controller.getLeftBumper() && !previousLeftBumper;
        previousLeftBumper = controller.getLeftBumper();

        if (rightBumper || leftBumper) {

            int nextMotorIndex = selectedMotor.ordinal();

            if (rightBumper) {

                // Select the next motor in the ring
                nextMotorIndex = (nextMotorIndex + 1) % Motor.values().length;
            }
            else {

                // Select the previous motor in the ring
                nextMotorIndex--;
                if (nextMotorIndex < 0) {
                    nextMotorIndex = Motor.values().length - 1;
                }
            }

            clearMotorIndicators();
            stopAllMotors();

            selectedMotor = Motor.values()[nextMotorIndex];

            SmartDashboard.putString("Test Motor", selectedMotor.toString());
        }

        /*
         * The SystemTestCommand can use either the POV or the triggers to control
         * the motor speed. If the triggers are used, the POV is cleared.
         *
         * Once the motor is selected, use the POV up and down to
         * adjust the motor speed.
         *
         * The speed is adjusted 50 times / second as the user holds the
         * POV control. Allow a 5 seconds to ramp the speed from 0 to full value.
         *
         * increment = 1.0 (full) / 50 adjustments/sec / 5 sec = .004 adjustment size / loop.
         */

        int    pov          = controller.getPOV();
        double leftTrigger  = controller.getLeftTriggerAxis();
        double rightTrigger = controller.getRightTriggerAxis();

        double motorSpeed   = 0;

        if (leftTrigger > 0 && rightTrigger > 0) {

            // If both triggers are pressed, then stop the motor
            motorSpeed    = 0;
            povMotorSpeed = 0;
        }
        else if (leftTrigger > 0) {

            motorSpeed    = -leftTrigger;
            povMotorSpeed = 0;
        }
        else if (rightTrigger > 0) {

            motorSpeed    = rightTrigger;
            povMotorSpeed = 0;
        }
        else {

            // No triggers are pressed, use the POV to control the motor speed
            if (pov == 0) {

                povMotorSpeed += 0.004;

                if (povMotorSpeed > 1.0) {
                    povMotorSpeed = 1.0;
                }
            }

            if (pov == 180) {

                povMotorSpeed -= 0.004;

                if (povMotorSpeed < -1.0) {
                    povMotorSpeed = -1.0;
                }
            }

            motorSpeed = povMotorSpeed;
        }

        SmartDashboard.putNumber("Test Motor Speed", motorSpeed);

        /*
         * If the X button is pressed, reset the motor speed to zero
         */

        if (controller.getXButton()) {
            motorSpeed    = 0;
            povMotorSpeed = 0;
        }

        /*
         * Apply the motor speed to the selected motor
         */

        SmartDashboard.putNumber("Test Motor Speed", motorSpeed);

        switch (selectedMotor) {

        case NONE:
            break;

        case LEFT_MOTOR_1:
            driveSubsystem.setMotorSpeeds(motorSpeed, 0, 0, 0);
            SmartDashboard.putBoolean("Test Left 1", true);
            break;

        case LEFT_MOTOR_2:
            driveSubsystem.setMotorSpeeds(0, motorSpeed, 0, 0);
            SmartDashboard.putBoolean("Test Left 2", true);
            break;

        case RIGHT_MOTOR_1:
            driveSubsystem.setMotorSpeeds(0, 0, motorSpeed, 0);
            SmartDashboard.putBoolean("Test Right 1", true);
            break;

        case RIGHT_MOTOR_2:
            driveSubsystem.setMotorSpeeds(0, 0, 0, motorSpeed);
            SmartDashboard.putBoolean("Test Right 2", true);
            break;

        case SHOOTER:
            shooterSubsystem.setShooterSpeed(motorSpeed);
            SmartDashboard.putBoolean("Test Shooter", true);
            break;

        case FEEDER:
            shooterSubsystem.setFeederSpeed(motorSpeed);
            SmartDashboard.putBoolean("Test Feeder", true);
            break;

        }
    }

    @Override
    public boolean isFinished() {

        // Wait 1/2 second before finishing.
        // This allows the user to start this command using the start and back
        // button combination without cancelling on the start button as
        // the user releases the buttons
        if (System.currentTimeMillis() - startTime < 500) {
            return false;
        }

        // Cancel on the regular cancel button after the first 0.5 seconds
        if (operatorInput.isCancel()) {
            setFinishReason("Cancelled by driver controller");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        stopAllMotors();

        selectedMotor = Motor.NONE;

        SmartDashboard.putBoolean("Test Mode", false);
        SmartDashboard.putString("Test Motor", selectedMotor.toString());
        SmartDashboard.putNumber("Test Motor Speed", 0);

        clearMotorIndicators();

        logCommandEnd(interrupted);
    }

    private void clearMotorIndicators() {
        SmartDashboard.putBoolean("Test Left 1", false);
        SmartDashboard.putBoolean("Test Left 2", false);
        SmartDashboard.putBoolean("Test Right 1", false);
        SmartDashboard.putBoolean("Test Right 2", false);
        SmartDashboard.putBoolean("Test Shooter", false);
        SmartDashboard.putBoolean("Test Feeder", false);
    }

    // Safely stop all motors in all subsystems used by this command.
    private void stopAllMotors() {

        driveSubsystem.stop();
        shooterSubsystem.stop();
        povMotorSpeed = 0;
    }
}
