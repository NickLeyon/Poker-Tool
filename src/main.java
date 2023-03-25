import java.text.DecimalFormat;
import java.util.Scanner;

public class main {
	static Scanner scan = new Scanner(System.in);
	static Hand hands[] = new Hand[1326]; //holds all possible hands
	
	public static void main(String[] args) throws InterruptedException {

		Card c1, c2;
		Hand myHand;
		/*c1 = new Card(0, 13);
		c2 = new Card(1, 14);
		Hand test = new Hand(c1, c2);*/
		
		System.out.println("Enter your 1st card  (14 for A  ;  12 for Q)");
		int card1 = scan.nextInt();
		c1 = new Card(0, card1);
		
		System.out.println("Enter your 2nd card");
		int card2 = scan.nextInt();
		c2 = new Card(2, card2);
		
		myHand = new Hand(c1, c2);
		
		System.out.println("How many players?");
		int handsAhead = scan.nextInt();
		
		
		runIt(myHand);
		
		scan.close();	//Done with any and all input.
		System.out.println("\n\nGoodbye!");
		Thread.sleep(3333);
		System.exit(0);
	}
	
	public static Card interpretCard(String c) {
		int suit = -1, rank = -1;
		
		int hintCounter = 0;	//Display a hint for card entry after 3 incorrect attempts.
		while(!checkCard(c)) {	//Check if input is a valid card
			hintCounter++;
			if(hintCounter >= 3) {
				System.out.println("Invalid card entry! Please enter a card by typing the specified letter corresponding to your desired suit,"
						+ " followed directly by the rank.\n\nA - Spade\nS - Club\nD - Heart\nF - Diamond\n1-13 for rank, 1 is Ace.\n"
							+ "Example: S8 is 8 of Clubs!");
				System.out.print("\nInput Card: ");
				
			} else {
				System.out.print("\nInvalid input for card entry, please try again: ");
			}
			c = scan.nextLine();
		}
		
		/*Parse a Card from the inputed string*/
		//Parse the suit
		switch (Character.toUpperCase(c.charAt(0))) {
			case 'A': suit = 0;
					break;
			case 'S': suit = 1;
					break;
			case 'D': suit = 2;
					break;
			case 'F': suit = 3;
					break;
		}
		//Parse the rank
		String sRank = "";
		for(int i = 1; i < c.length(); i++) {
			sRank += c.charAt(i);
		}

		rank = Integer.parseInt(sRank);
		
		if (rank == 1) {	//Ace is actually 14 and not 1.
			rank = 14;
		}
		
		//Failsafe to turn Ace from 1 -> 14 built in the constructor.
		return new Card(suit, rank);
	}
	
	public static boolean checkCard(String c) {
		//Check for valid length
		if (c.length() < 2 || c.length() > 3) {
			return false;
		}
		//Check for valid suit
		char j = Character.toUpperCase(c.charAt(0));
		if (j != 'A' && j != 'S' && j != 'D' && j != 'F') {
			return false;
		}
		//Check for valid rank
		String sRank = "";		//Parse rank from string
		for(int i = 1; i < c.length(); i++) {
			sRank += c.charAt(i);
		}
		int rank = -1;	//Initialized to avoid errors.
		try {
			rank = Integer.parseInt(sRank);
		} catch (NumberFormatException e) {
			rank = -1;
		}
		if(rank < 1 || rank > 13) {
			return false;
		}
		
		return true;
	}
	
	
	public static Hand[] allHands() {
		Hand[] hands = new Hand[1326];
		Deck d = new Deck();
		int count = 0;
		
		for(int x = 0; x < 51; x++) {
			for(int y = x+1; y < 52; y++) {
				hands[count] = new Hand(d.getDeck()[x], d.getDeck()[y]);
				count++;
			}
		}
		/*NICK Please add verification of the 1326 hands here*/
		//System.out.println(count);
		return hands;
	}
	
	public static void runIt(Hand h) {
		/***********************************************************************************/
		double Dom=0,Sub=0,Flip=0,Same=0;
		Hand hold = null; //Used for for loop
		hands = allHands();
		
		
		//Check if your hand is a pair
		Boolean isPair = false;
		if (h.card1.rank == h.card2.rank)
			isPair = true;
		
		//Keep ranks in order to allow for simpler code
		if(h.card2.rank > h.card1.rank){
			Card mold = h.card1;
			h.card1 = h.card2;
			h.card2 = mold;
		}
		
		for(int i = 0; i < hands.length; i++) {
			hold = hands[i];
			//Keep ranks in order to allow for simpler code
			if(hold.card2.rank > hold.card1.rank){
				Card mold2 = hold.card1;
				hold.card1 = hold.card2;
				hold.card2 = mold2;
			}
			//Check for your hand
			if (h.isSame(hold))
				hold = null;	//This line doesn't do anything
			else {
				//Pair
				if(hold.card1.rank == hold.card2.rank) {
					//Pair v Pair
					if(isPair) {
						if(h.card1.rank == hold.card1.rank)
							Same++;
						else if (h.card1.rank > hold.card2.rank)
							Dom++;
						else
							Sub++;
					} else {/*!Pair v Pair!*/
						if(h.card1.rank > hold.card1.rank && h.card2.rank > hold.card1.rank)
							Flip++;
						else if(h.card1.rank > hold.card1.rank && h.card2.rank <= hold.card1.rank)
							Sub++;
						else if(h.card2.rank > hold.card1.rank && h.card1.rank <= hold.card1.rank)
							Sub++;
						else if(h.card1.rank <= hold.card1.rank && h.card2.rank <= hold.card1.rank)
							Sub++; //Crushed **Add later
						else
							System.out.println(">:(");
					}
					
				} else if(!isPair){	/*!Pair!*/
					if(h.card1.rank == hold.card1.rank && h.card2.rank == hold.card2.rank)
						Same++;
					else if (h.card1.rank == hold.card1.rank && h.card2.rank > hold.card2.rank)
						Dom++;
					else if (h.card1.rank == hold.card1.rank && h.card2.rank < hold.card2.rank)
						Sub++;
					else if (h.card1.rank > hold.card1.rank)
						Dom++;
					else if (h.card1.rank < hold.card1.rank)
						Sub++;
					else
						System.out.println(">:)");
				} else {	/*High Cards*/
						//Pair vs High Cards
					if(hold.card1.rank > h.card1.rank && hold.card2.rank > h.card1.rank)
						Flip++;
					else if (hold.card1.rank > h.card1.rank && hold.card2.rank <= h.card1.rank)
						Dom++;
					else if (hold.card1.rank <= h.card1.rank)
						Dom++; //Crushinggg *Add later*
				}		
			}
		}
		
		/*Variables to make the output look cleaner*/
		double total = Dom + Sub + Flip + Same;
		String W = new DecimalFormat("#.###").format(Dom/total);
		String L = new DecimalFormat("#.###").format(Sub/total);
		String F = new DecimalFormat("#.###").format(Flip/total);
		String S = new DecimalFormat("#.###").format(Same/total);
		
		
		System.out.println("\nW: " + W + "%" + "\nL: " + L  + "%" + 
				"\n\nFlip: " + F  + "%" + "\nSame: " + S + "%");
		//System.out.println("\n\nTotal: " + total);
		/***********************************************************************************/
	}

}
