/**
 * 
 */
package edu.fjnu.cerulean.util;

/**
 * @author Cerulean
 *
 */
public class CodoonData {
	private  Double distance;
	private  String duration;
	private  Double avg_speed;
	private  Double usePower;
	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the avg_speed
	 */
	public Double getAvg_speed() {
		return avg_speed;
	}
	/**
	 * @param avg_speed the avg_speed to set
	 */
	public void setAvg_speed(Double avg_speed) {
		this.avg_speed = avg_speed;
	}
	/**
	 * @return the usePower
	 */
	public Double getUsePower() {
		return usePower;
	}
	/**
	 * @param usePower the usePower to set
	 */
	public void setUsePower(Double usePower) {
		this.usePower = usePower;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CodoonData [distance=" + distance + ", duration=" + duration
				+ ", avg_speed=" + avg_speed + ", usePower=" + usePower + "]";
	}
	
	
}
