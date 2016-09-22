package lee.zac;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Zac - a robot by (your name here)
 */
public class Terminator2 extends TeamRobot {

	private byte moveDirection = 1;
	private byte scanDirection = 1;

   
    public void run() {
        // Initialization of the robot should be put here
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		setTurnRadarRight(360);
		execute(); 
		
        // Robot main loop
        while(true) {
            // Replace the next 4 lines with any behavior you would like
            turnRadarRight(360);
			if (getRadarTurnRemaining() == 0) {
				setTurnRadarRight(999);
				execute();
			}
        }
    }
    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
	
		setTurnRight(e.getBearing());
		setAhead(999);
		
		
		setFire(3);
		//setFire(1000 / e.getDistance());	
	
		setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());

		scanDirection *=-1;
		setTurnRadarRight(36000 *scanDirection);
		
	//	setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());

	}

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
	  //   setTurnLeft(135);
      //  back(77);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        // Replace the next line with any behavior you would like
        moveDirection *= -1;
		setTurnRight(135);
    }
	public void onHitRobot(HitRobotEvent e) { 
		
	}
}

