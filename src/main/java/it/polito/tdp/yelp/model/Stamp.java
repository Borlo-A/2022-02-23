package it.polito.tdp.yelp.model;

public class Stamp {
	
	Review review;
	int contatore;

	public Stamp(Review review, int contatore) {
		super();
		this.review = review;
		this.contatore = contatore;
	}
	
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	public int getContatore() {
		return contatore;
	}
	public void setContatore(int contatore) {
		this.contatore = contatore;
	}

	@Override
	public String toString() {
		return "[" + review + "   +++   " + contatore + "]\n";
	}
	
}
