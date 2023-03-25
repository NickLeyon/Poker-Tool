
public class Hand {
	
	public Card card1, card2;

	public Hand(Card c1, Card c2) {
		this.card1 = c1;
		this.card2 = c2;
	}
	
	public String outputHand() {
		
		return this.card1.outputCardShort() + " " + this.card2.outputCardShort();
	}
	
	public boolean isSame(Hand h) {
		if ((h.card1.isSame(this.card1) || h.card1.isSame(this.card2)) 
				&& (h.card2.isSame(this.card1) || h.card2.isSame(this.card2)))
			return true;
		
		return false;
	}
}
