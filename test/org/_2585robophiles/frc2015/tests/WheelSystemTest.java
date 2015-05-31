package org._2585robophiles.frc2015.tests;

import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.systems.AccelerometerSystem;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Unit test for the WheelSystem class
 */
public class WheelSystemTest {

	private TestWheelSystem wheelSystem;
	private double currentForwardMovement, currentSidewaysMovement,
	currentRotation;
	private boolean forwardDistancePIDEnabled, sidewaysDistancePIDEnabled, straightDrivePIDEnabled;

	//input
	private double sidewaysMovement;
	private double rotation;
	private double forwardMovement;
	private boolean straightDrive;
	private double analogLiftUp;
	private double analogLiftDown;
	private boolean digitalLiftDown;
	private boolean digitalLiftUp;
	private boolean liftSetpointUp;
	private boolean liftSetpointDown;
	private boolean liftSetpoint1;
	private boolean liftSetpoint2;
	private boolean liftSetpoint3;
	private boolean liftSetpoint4;
	private boolean groundLift;
	private boolean stopStraightDrive;
	private boolean changeSensitivity;

	/**
	 * Set up the unit test
	 */
	@Before
	public void setUp() {
		wheelSystem = new TestWheelSystem();
		wheelSystem.setAccelerometer(new AccelerometerSystem(){

			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#getSpeedX()
			 */
			@Override
			public double getSpeedX() {
				return 0;
			}

			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#getSpeedY()
			 */
			@Override
			public double getSpeedY() {
				return 0;
			}

		});
		wheelSystem.setInput(new InputMethod(){

			@Override
			public double forwardMovement() {
				return forwardMovement;
			}

			@Override
			public double sidewaysMovement() {
				return sidewaysMovement;
			}

			@Override
			public double rotation() {
				return rotation;
			}

			@Override
			public boolean straightDrive() {
				return straightDrive;
			}

			@Override
			public double analogLiftUp() {
				return analogLiftUp;
			}

			@Override
			public double analogLiftDown() {
				return analogLiftDown;
			}

			@Override
			public boolean digitalLiftDown() {
				return digitalLiftDown;
			}

			@Override
			public boolean digitalLiftUp() {
				return digitalLiftUp;
			}

			@Override
			public boolean liftSetpointDown() {
				return liftSetpointDown;
			}

			@Override
			public boolean liftSetpointUp() {
				return liftSetpointUp;
			}

			@Override
			public boolean liftSetpoint1() {
				return liftSetpoint1;
			}

			@Override
			public boolean liftSetpoint2() {
				return liftSetpoint2;
			}

			@Override
			public boolean liftSetpoint3() {
				return liftSetpoint3;
			}

			@Override
			public boolean liftSetpoint4() {
				return liftSetpoint4;
			}

			@Override
			public boolean groundLift() {
				return groundLift;
			}

			@Override
			public boolean stopStraightDrive() {
				return stopStraightDrive;
			}

			@Override
			public boolean changeSensitivity() {
				return changeSensitivity;
			}

			@Override
			public Joystick[] joysticks() {
				return null;
			}
		});

	}

	void resetInput(){
		sidewaysMovement = 0;
		rotation = 0;
		forwardMovement = 0;
		straightDrive = false;
		analogLiftUp = 0;
		analogLiftDown = 0;
		digitalLiftDown = false;
		digitalLiftUp = false;
		liftSetpointUp = false;
		liftSetpointDown = false;
		liftSetpoint1 = false ;
		liftSetpoint2 = false;
		liftSetpoint3 = false;
		liftSetpoint4 = false;
		groundLift = false;
		stopStraightDrive = false;
		changeSensitivity =false;
	}

	/**
	 * Test the general things in the run() method of the WheelSystem class
	 */
	@Test
	public void testRun() {

		// first make sure we aren't moving without input
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(0, currentForwardMovement, 0);
		Assert.assertEquals(0, currentRotation, 0);

		// test forward ramping
		forwardMovement = 1;
		
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(RobotMap.FORWARD_RAMPING, currentForwardMovement, 0);
		resetInput();
		
		forwardMovement = -1;
		
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(RobotMap.FORWARD_RAMPING
				+ (-1 - RobotMap.FORWARD_RAMPING) * RobotMap.FORWARD_RAMPING,
				currentForwardMovement, 0);
		resetInput();

		// test sideways ramping
		sidewaysMovement = 1;
		
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(RobotMap.SIDEWAYS_RAMPING, currentSidewaysMovement, 0);
		resetInput();
		
		sidewaysMovement = -1;
		
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(RobotMap.SIDEWAYS_RAMPING
				+ (-1 - RobotMap.SIDEWAYS_RAMPING) * RobotMap.SIDEWAYS_RAMPING,
				currentSidewaysMovement, 0);
		resetInput();

		
		// test squared rotation
		rotation = -1;
		
		wheelSystem.run();
		Assert.assertEquals(-1, currentRotation, 0);
		resetInput();
		
		rotation = 0.5;
		
		wheelSystem.run();
		Assert.assertEquals(Math.pow(0.5, RobotMap.ROTATION_EXPONENT), currentRotation, 0);
		resetInput();

		// test deadzone
		forwardMovement = 0.03;
		sidewaysMovement = 0.2;
		wheelSystem.run();
		Assert.assertTrue(currentSidewaysMovement > 0);
		Assert.assertEquals(0, currentForwardMovement, 0);
		resetInput();

		forwardMovement = 0.17;
		sidewaysMovement = 0.1;
		wheelSystem.run();
		Assert.assertTrue(currentForwardMovement > 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		resetInput();
		
		forwardMovement = 0.1;
		sidewaysMovement = 0.1;
		wheelSystem.run();
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(0, currentForwardMovement, 0);
		resetInput();

		//test rotation deadzone
		rotation = 0.1;
		
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
	}



	/**
	 * Test the driveDistance method includes forwardDriveDistance and sidewaysDriveDistance
	 */
	@Test
	public void testDriveDistance(){
		//test forwardDriveDistance
		Assert.assertFalse(forwardDistancePIDEnabled);
		Assert.assertFalse(sidewaysDistancePIDEnabled);
		for(int i = 0; i < 3; i++){
			wheelSystem.driveDistance(3,0);
			Assert.assertTrue(forwardDistancePIDEnabled);
			Assert.assertFalse(sidewaysDistancePIDEnabled);
			Assert.assertTrue(wheelSystem.getLastForwardDistanceUpdate() > 0);
			Assert.assertTrue(wheelSystem.getLastSidewaysDistanceUpdate() == 0);
		}
		wheelSystem.setForwardDistanceDriven(3);
		wheelSystem.driveDistance(3, 0);
		Assert.assertFalse(forwardDistancePIDEnabled);
		Assert.assertFalse(sidewaysDistancePIDEnabled);
		Assert.assertEquals(0, wheelSystem.getLastForwardDistanceUpdate());
		Assert.assertEquals(0, wheelSystem.getLastSidewaysDistanceUpdate());

		//reset for next test
		wheelSystem.setForwardDistanceDriven(0);
		wheelSystem.setSidewaysDistanceDriven(0);

		//test sidewaysDriveDistance
		Assert.assertFalse(sidewaysDistancePIDEnabled);
		Assert.assertFalse(forwardDistancePIDEnabled);
		for(int i = 0; i < 3; i++){
			wheelSystem.driveDistance(0,3);
			Assert.assertTrue(sidewaysDistancePIDEnabled);
			Assert.assertFalse(forwardDistancePIDEnabled);
			Assert.assertTrue(wheelSystem.getLastSidewaysDistanceUpdate() > 0);
			Assert.assertTrue(wheelSystem.getLastForwardDistanceUpdate() == 0);

		}
		wheelSystem.setSidewaysDistanceDriven(3);
		wheelSystem.driveDistance(0, 3);
		Assert.assertFalse(sidewaysDistancePIDEnabled);
		Assert.assertFalse(forwardDistancePIDEnabled);
		Assert.assertEquals(0, wheelSystem.getLastSidewaysDistanceUpdate());
		Assert.assertEquals(0, wheelSystem.getLastForwardDistanceUpdate());

		//reset for next test
		wheelSystem.setForwardDistanceDriven(0);
		wheelSystem.setSidewaysDistanceDriven(0);

		//test both forwardDriveDistance and sidewaysDriveDistance
		Assert.assertFalse(forwardDistancePIDEnabled);
		Assert.assertFalse(sidewaysDistancePIDEnabled);
		for(int i = 0; i < 9; i++){
			wheelSystem.driveDistance(3,3);
			Assert.assertTrue(forwardDistancePIDEnabled);
			Assert.assertTrue(sidewaysDistancePIDEnabled);
			Assert.assertTrue(wheelSystem.getLastForwardDistanceUpdate() > 0);
			Assert.assertTrue(wheelSystem.getLastSidewaysDistanceUpdate() > 0);
		}
		wheelSystem.setForwardDistanceDriven(3);
		wheelSystem.setSidewaysDistanceDriven(3);
		wheelSystem.driveDistance(3, 3);
		Assert.assertFalse(forwardDistancePIDEnabled);
		Assert.assertFalse(sidewaysDistancePIDEnabled);
		Assert.assertEquals(0, wheelSystem.getLastForwardDistanceUpdate());
		Assert.assertEquals(0, wheelSystem.getLastSidewaysDistanceUpdate());
	}

	/**
	 * test straight driving
	 */
	@Test
	public void testStraightDrive(){
		wheelSystem.disableSraightDrivePID();
		Assert.assertThat(straightDrivePIDEnabled, CoreMatchers.equalTo(false));
		wheelSystem.straightDrive(0.3, 0.3);
		Assert.assertThat(currentForwardMovement, CoreMatchers.equalTo(0.3));
		Assert.assertThat(currentSidewaysMovement, CoreMatchers.equalTo(0.3));
		Assert.assertThat(straightDrivePIDEnabled, CoreMatchers.equalTo(true));
	}

	/**
	 * WheelSystem subclass for the purpose of unit testing
	 */
	private class TestWheelSystem extends WheelSystem {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#drive(double,
		 * double, double)
		 */
		@Override
		public void drive(double normalMovement, double sidewaysMovement,
				double rotation) {
			currentForwardMovement = normalMovement;
			currentSidewaysMovement = sidewaysMovement;
			currentRotation = rotation;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setInput(org.
		 * _2585robophiles.frc2015.input.InputMethod)
		 */
		@Override
		protected synchronized void setInput(InputMethod input) {
			super.setInput(input);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setForwardDistanceDriven(double)
		 */
		@Override
		protected synchronized void setForwardDistanceDriven(double forwardDistanceDriven) {
			super.setForwardDistanceDriven(forwardDistanceDriven);
		}

		protected synchronized void setSidewaysDistanceDriven(double sidewaysDistanceDriven) {
			super.setSidewaysDistanceDriven(sidewaysDistanceDriven);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setLastForwardDistanceUpdate(long)
		 */
		protected synchronized void setLastForwardDistanceUpdate(
				long lastForwardDistanceUpdate) {
			super.setLastForwardDistanceUpdate(lastForwardDistanceUpdate);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setLastSidewaysDistanceUpdate(long)
		 */
		protected synchronized void setLastSidewaysDistanceUpdate(
				long lastSidewaysDistanceUpdate) {
			super.setLastSidewaysDistanceUpdate(lastSidewaysDistanceUpdate);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setPreviousNormalMovement(double)
		 */
		@Override
		protected synchronized void setPreviousNormalMovement(
				double currentNormalMovement) {
			super.setPreviousNormalMovement(currentNormalMovement);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setSidewaysMotor(edu.wpi.first.wpilibj.SpeedController)
		 */
		@Override
		protected synchronized void setSidewaysMotor(
				SpeedController sidewaysMotor) {
			super.setSidewaysMotor(sidewaysMotor);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setAccelerometer(org._2585robophiles.frc2015.systems.AccelerometerSystem)
		 */
		@Override
		protected synchronized void setAccelerometer(
				AccelerometerSystem accelerometer) {
			super.setAccelerometer(accelerometer);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#enableForwardDistancePID(double)
		 */
		protected synchronized void enableForwardDistancePID(double setpoint) {
			forwardDistancePIDEnabled = true;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#enableSidewaysDistancePID(double)
		 */
		protected synchronized void enableSidewaysDistancePID(double setpoint) {
			sidewaysDistancePIDEnabled = true;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#disableForwardDistancePID()
		 */
		protected synchronized void disableForwardDistancePID() {
			forwardDistancePIDEnabled = false;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#disableSidewaysDistancePID()
		 */
		protected synchronized void disableSidewaysDistancePID() {
			sidewaysDistancePIDEnabled = false;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#enableStraightDrivePID(double)
		 */
		@Override
		protected synchronized void enableStraightDrivePID() {
			straightDrivePIDEnabled = true;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#disableSraightDrivePID()
		 */
		@Override
		protected synchronized void disableSraightDrivePID() {
			straightDrivePIDEnabled = false;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#straightDriving()
		 */
		@Override
		public synchronized boolean straightDriving() {
			return straightDrivePIDEnabled;
		}



	}
}
