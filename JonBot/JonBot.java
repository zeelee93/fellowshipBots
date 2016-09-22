package leczner.jon.JonBot;

import robocode.*;

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
	String targetName;

	/**
	 * run: JonBot's default behavior
	 */
	public void run() {
		friendlies.add("Champion");
		friendlies.add("RocketDoll");
		friendlies.add("StaticTinTin");
		friendlies.add("Jelly");
		friendlies.add("McKiller");

		setAllColors(Color.BLACK);
		setBulletColor(Color.BLUE);
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);

		Random r = new Random();
		int randomDirection;

		System.out.println("init complete");
		// Robot main loop
		while(true) {
			setTurnRadarRight(360);
			randomDirection = r.nextInt(180);
			setTurnRight(getHeading() - randomDirection);
			waitFor(new TurnCompleteCondition(this));
			setAhead(1000);

			execute();
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (targetName != null && !e.getName().equals(targetName)) {
			return;
		}

		if (targetName == null) {
			targetName = e.getName();
			System.out.println("Tracking " + targetName);
		}

		int turnDirection;
		if (e.getBearing() >= 0) {
			turnDirection = 1;
		} else {
			turnDirection = -1;
		}
//
//		turnRight(e.getBearing());
//		if (e.getDistance() > 100)
//			ahead(e.getDistance() + 5);
//
//		//** based on http://mark.random-article.com/robocode/basic_scanning.html
//		setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
//		scanDirection *= -1; // changes value from 1 to -1
//		setTurnRadarRight(360 * scanDirection);
////		execute();
//		// ********
//		Robot target = null;
//		if (friendlies.contains(e.getName())) {
//			setEventPriority("friendly", 0);
//			setInterruptible(true);
////			execute();
//		} else {
//			//** based on https://sourceforge.net/p/robocode/discussion/116459/thread/df3a124f/
//			enemies.add(e);
//			System.out.println("enemy found: " + e.getName());
//			double weakestEnergy = e.getEnergy();
//			for (int i = 0; i < enemies.size(); i++) {
//				Robot current = (Robot)enemies.get(i);
//				double otherEnemyEnergy = current.getEnergy();
//				if (otherEnemyEnergy < weakestEnergy) {
//					weakestEnergy = otherEnemyEnergy;
//					target = current;
//				}
//			}
//			if (target.equals(e)) {
//				double absoluteBearing = getHeading() + e.getBearing();
//				setTurnGunRight(robocode.util.Utils.normalRelativeAngle(absoluteBearing - getGunHeading()));
//				if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
//					setFire(Math.min(400 / e.getDistance(), 3));
////				execute();
//			}
//		}
//		//********
		scan();
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
		setBack(100);
	}	
}
