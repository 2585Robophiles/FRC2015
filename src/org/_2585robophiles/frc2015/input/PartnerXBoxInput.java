package org._2585robophiles.frc2015.input;

import org._2585robophiles.lib2585.XboxConstants;
import edu.wpi.first.wpilibj.Joystick;

public class PartnerXBoxInput implements InputMethod{
    /*
    1: A - outtake
    2: B - shift
    3: X - intake
    4: Y - shooter pneumatic
    5: Left Bumper - shoot
    6: Right Bumper - shift
    7: Back - straight drive
    8: Start
    9: Left Joystick
    10: Right Joystick
     */

    private Joystick driver;
    private Joystick shooter;

    public PartnerXboxInput() {
        driver = new Joystick(1);
        shooter = new Joystick(2);
    }

    public double getLeftX() {
        return driver.getRawAxis(XboxInput.LEFT_X_AXIS);
    }

    public double getRightX() {
        return driver.getRawAxis(XboxInput.RIGHT_X_AXIS);
    }

    public double getLeftY() {
        return driver.getRawAxis(XboxInput.LEFT_Y_AXIS);
    }

    public double getRightY() {
        return driver.getRawAxis(XboxInput.RIGHT_Y_AXIS);
    }

    public boolean shift() {
        return driver.getRawButton(XboxInput.B_BUTTON) || driver.getRawButton(XboxInput.RIGHT_BUMPER);
    }

    public boolean catchBall() {
        return driver.getRawButton(XboxInput.A_BUTTON);
    }

    public boolean shoot() {
        return shooter.getRawButton(XboxInput.LEFT_BUMPER);
    }

    public boolean intakeLift() {
        return shooter.getRawButton(XboxInput.B_BUTTON);
    }

    public boolean shooterLift() {
        return shooter.getRawButton(XboxInput.Y_BUTTON);
    }

    public boolean directionToggle() {
        return driver.getRawButton(XboxInput.Y_BUTTON) || driver.getRawButton(XboxInput.RIGHT_BUMPER);
    }

    public boolean intakeToggle(){
        return shooter.getRawButton(XBoxInput.RIGHT_BUMPER);
    }

    public int intake(){
        if(shooter.getRawButton(XBoxInput.A_BUTTON)){
            return -1;
        }else if(shooter.getRawButton(XBoxInput.X_BUTTON)){
            return 1;
        }else{
            return 0;
        }
    }

    public boolean straightDrive(){
        return driver.getRawButton(XBoxInput.BACK_BUTTON);
    }
}
