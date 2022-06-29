package it.polito.tdp.yelp.model;

public class Arco {
	
	Review r1;
	Review r2;
	long differenza;
	
	
	public Arco(Review r1, Review r2, long differenza) {
		super();
		this.r1 = r1;
		this.r2 = r2;
		this.differenza = differenza;
	}
	
	public Review getR1() {
		return r1;
	}
	public void setR1(Review r1) {
		this.r1 = r1;
	}
	public Review getR2() {
		return r2;
	}
	public void setR2(Review r2) {
		this.r2 = r2;
	}
	public long getDifferenza() {
		return differenza;
	}
	public void setDifferenza(long differenza) {
		this.differenza = differenza;
	}

	@Override
	public String toString() {
		return "[" + r1 + "  +++   " + r2 + "   :   " + differenza + "]";
	}
	
}
