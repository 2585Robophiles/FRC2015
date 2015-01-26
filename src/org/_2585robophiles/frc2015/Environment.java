package org._2585robophiles.frc2015;

import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.input.XboxInput;
import org._2585robophiles.frc2015.systems.AccelerometerSystem;
import org._2585robophiles.frc2015.systems.DashboardSystem;
import org._2585robophiles.frc2015.systems.GyroSystem;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org._2585robophiles.lib2585.RobotEnvironment;

/**
 * This class contains the robot's systems and input
 */
public class Environment extends RobotEnvironment {
	
	private static final long serialVersionUID = 8684301017988652791L;
	
	private WheelSystem wheelSystem;
	private GyroSystem gyroSystem;
	private AccelerometerSystem accelerometerSystem;
	private InputMethod input;
	private DashboardSystem dashboardSystem;

	/**
	 * Just a default constructor
	 */
	public Environment() {
		super();
	}
	
	/**
	 * Initialize the systems
	 * @param robot the robot which the environment belongs to
	 */
	public Environment(Robot robot) {
		super(robot);
		
		input = new XboxInput();
		
		gyroSystem = new GyroSystem();
		gyroSystem.init(this);
		
		accelerometerSystem = new AccelerometerSystem();
		accelerometerSystem.init(this);
				
		wheelSystem = new WheelSystem();
		wheelSystem.init(this);
		
		dashboardSystem = new DashboardSystem();
		dashboardSystem.init(this);
	}

	/**
	 * @return the wheelSystem
	 */
	public WheelSystem getWheelSystem() {
		return wheelSystem;
	}

	/**
	 * @param wheelSystem the wheelSystem to set
	 */
	protected synchronized void setWheelSystem(WheelSystem wheelSystem) {
		this.wheelSystem = wheelSystem;
	}

	/**
	 * @return the input
	 */
	public InputMethod getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	protected synchronized void setInput(InputMethod input) {
		this.input = input;
	}

	/**
	 * @return the gyroSystem
	 */
	public GyroSystem getGyroSystem() {
		return gyroSystem;
	}

	/**
	 * @param gyroSystem the gyroSystem to set
	 */
	protected synchronized void setGyroSystem(GyroSystem gyroSystem) {
		this.gyroSystem = gyroSystem;
	}

	/**
	 * @return the accelerometerSystem
	 */
	public AccelerometerSystem getAccelerometerSystem() {
		return accelerometerSystem;
	}

	/**
	 * @param accelerometerSystem the accelerometerSystem to set
	 */
	protected synchronized void setAccelerometerSystem(
			AccelerometerSystem accelerometerSystem) {
		this.accelerometerSystem = accelerometerSystem;
	}

	/**
	 * @return the dashboardSystem
	 */
	public DashboardSystem getDashboardSystem() {
		return dashboardSystem;
	}

	/**
	 * @param dashboardSystem the dashboardSystem to set
	 */
	protected synchronized void setDashboardSystem(DashboardSystem dashboardSystem) {
		this.dashboardSystem = dashboardSystem;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		wheelSystem.destroy();
		gyroSystem.destroy();
		accelerometerSystem.destroy();
	}

}
