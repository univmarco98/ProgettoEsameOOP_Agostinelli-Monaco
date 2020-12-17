/**
 * 
 */
package application.utility;

import java.io.Serializable;

import application.file.File;

/**
 * @author Matteo
 *
 */
public class Time implements Serializable{
	private int day,month,year,hour,minute;
	/**
	 * 
	 */
	public Time(int year, int month, int day, int hour,int minute) {
		this.year=year;
		this.month=month;
		this.day=day;
		this.hour=hour;
		this.minute=minute;
	}
	
	public Time() {
		// TODO Auto-generated constructor stub
	}

	public void set_year(int year) {
		this.year=year;
	}
	
	public void set_month(int month) {
		this.month=month;
	}
	
	public void set_day(int day) {
		this.day=day;
	}
	
	public void set_hour(int hour) {
		this.hour=hour;
	}
	
	public void set_minute(int minute) {
		this.minute=minute;
	}
	
	public int set_year() {
		return this.year;
	}
	
	public int set_month() {
		return this.month;
	}
	
	public int set_day() {
		return this.day;
	}
	
	public int set_hour() {
		return this.hour;
	}
	
	public int set_minute() {
		return this.minute;
	}
	
	public String toString(){
		String tmp = Integer.toString(year)+Integer.toString(month)+Integer.toString(day)+Integer.toString(hour)+Integer.toString(minute);
		return tmp;
	}

}
