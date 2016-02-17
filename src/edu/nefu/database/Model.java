package edu.nefu.database;

public class Model {
	
	public String time;
	public double temp;
	public double humi;
	public double light;
	public double tentemp;
	public double twetemp;
	public double tenhumi;
	public double twehumi;
	public double co2;
	public double rain;
	
	public Model(String time, double temp, double humi, double light, double tentemp, double twetemp, double tenhumi, double twehumi, double co2, double rain) {
		// TODO Auto-generated constructor stub
		this.time=time;
		this.temp=temp;
		this.humi=humi;
		this.light=light;
		this.tentemp=tentemp;
		this.twetemp=twetemp;
		this.tenhumi=tenhumi;
		this.twehumi=twehumi;
		this.co2=co2;
		this.rain=rain;
	}

}
