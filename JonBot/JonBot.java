package leczner.jon.JonBot;

import robocode.*;
import robocode.Robot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * JonBot - a robot by (your name here)
 */
public class JonBot extends AdvancedRobot
{
	private ArrayList<String> friendlies = new ArrayList<>();
	private ArrayList enemies = new ArrayList<>();
	private byte scanDirection = 1;

	/**
	 * run: JonBot's default behavior
	 */
	public void run() {
		friendlies.add("Champion");
		friendlies.add("RocketDoll");
		friendlies.add("StaticTinTin");
		friendlies.add("Jelly");

		setAllColors(Color.BLACK);
		setBulletColor(Color.BLUE);
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);


		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			setAhead(30);
			setTurnRight(120);
			setTurnRadarRight(360);
			execute();
////			onWin(new WinEvent());
//			setBack(35);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		int turnDirection;
		if (e.getBearing() >= 0) {
			turnDirection = 1;
		} else {
			turnDirection = -1;
		}

		turnRight(e.getBearing());
		ahead(e.getDistance() + 5);
		scan();

		//** based on http://mark.random-article.com/robocode/basic_scanning.html
		setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
		scanDirection *= -1; // changes value from 1 to -1
		setTurnRadarRight(360 * scanDirection);
		// ********
		Robot target = null;
		if (friendlies.contains(e.getName())) {
			setEventPriority("friendly", 0);
			setInterruptible(true);
		} else {
			//** based on https://sourceforge.net/p/robocode/discussion/116459/thread/df3a124f/
			enemies.add(e);
			double weakestEnergy = e.getEnergy();
			for (int i = 0; i < enemies.size(); i++) {
				Robot current = (Robot)enemies.get(i);
				double otherEnemyEnergy = current.getEnergy();
				if (otherEnemyEnergy < weakestEnergy) {
					weakestEnergy = otherEnemyEnergy;
					target = current;
				}
			}
			if (target.equals(e)) {
				double absoluteBearing = getHeading() + e.getBearing();
				setTurnGunRight(robocode.util.Utils.normalRelativeAngle(absoluteBearing - getGunHeading()));
				if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
					setFire(Math.min(400 / e.getDistance(), 3));
				execute();
			}
		}
		//********

	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		setTurnLeft(90);
		setTurnGunRight(e.getBearing());
		fire(2);
		setAhead(30);
		scan();
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		setTurnRight(e.getBearing());
		setTurnGunLeft(e.getBearing());
		Random r = new Random();
		int randomDirection = r.nextInt((int)e.getBearing() * -1);
		setTurnLeft(randomDirection);
		setBack(100);
	}	
}
