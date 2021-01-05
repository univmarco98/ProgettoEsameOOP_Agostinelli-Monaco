package application.utility;

import java.io.Serializable;

import application.file.File;

/**
 * Classe per modellare  una data e un'ora in maniera congiunta
 * 
 * @author Marco 
 * @author Matteo
 *
 */

public class Time implements Serializable{

	private static final long serialVersionUID = 1;
	private int day,month,year,hour,minute;

/**
 * Costruttore classe Time
 * @param year anno in formato int
 * @param month mese in formato int
 * @param day giorno in formato int
 * @param hour ora in formato int
 * @param minute minuti in formato int
 */
	public Time(int year, int month, int day, int hour,int minute) {
		this.year=year;
		this.month=month;
		this.day=day;
		this.hour=hour;
		this.minute=minute;
	}
	
	/**
	 * Costruttore classe Time
	 */
	public Time() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo per settare l'anno
	 * 
	 * @param year in formato int
	 */
	public void set_year(int year) {
		this.year=year;
	}

	/**
	 * Metodo per settare il mese
	 * 
	 * @param month in formato int
	 */
	public void set_month(int month) {
		this.month=month;
	}

	/**
	 * Metodo per settare il giorno
	 * 
	 * @param day in formato int
	 */
	public void set_day(int day) {
		this.day=day;
	}

	/**
	 * Metodo per settare l'ora
	 * 
	 * @param hour in formato int
	 */	
	public void set_hour(int hour) {
		this.hour=hour;
	}

	/**
	 * Metodo per settare i minuti
	 * 
	 * @param minute in formato int
	 */
	public void set_minute(int minute) {
		this.minute=minute;
	}
	
	/**
	 * Metodo per ottenere l'anno
	 * @return year in formato int
	 */
	public int set_year() {
		return this.year;
	}
	
	/**
	 * Metodo per ottenere il mese
	 * @return month in formato int
	 */
	public int set_month() {
		return this.month;
	}
	
	/**
	 * Metodo per ottenere il giorno
	 * @return day in formato int
	 */
	public int set_day() {
		return this.day;
	}
	
	/**
	 * Metodo per ottenere l'ora
	 * @return hour in formato int
	 */
	public int set_hour() {
		return this.hour;
	}
	
	/**
	 * Metodo per ottenere i minuti
	 * @return minute in formato int
	 */
	public int set_minute() {
		return this.minute;
	}
	
	@Override
	public String toString(){
		String tmp = Integer.toString(year);
		if(this.month<10)
			tmp+="0";
		tmp+=Integer.toString(month);
		if(this.day<10)
			tmp+="0";
		tmp+=Integer.toString(day);
		return tmp;
	}

}
