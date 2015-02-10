package org._2585robophiles.frc2015.input;

import org._2585robophiles.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

public class PartnerXBoxInput implements InputMethod{
  private Joystick controller;
  private Joystick controller2;
  
  public PartnerXBoxInput(){
    controller = new Joystick(0);
    controller2 = new Joystick(1);
  }
  
  public PartnerXBoxInput(Joystick joystick, Joystick joystick2){
    controller = joystick;
    controller2 = joystick2;
  }
  
  @Override
  public double rotation(){
    return controller.getRawAxis(XBoxConstants.RIGHT_X_AXIS);
  }
  
  public double straightDrive(){
    return controller.getRawAxis(XBoxConstants.BACK_BUTTON);
  }
  
  public Joystick[] joysticks(){
    return new Joystick[]{controller, controller2};
  }
}
