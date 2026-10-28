import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;


class Textbox extends Text{
	//variables
	boolean textactive;
	MyPanel4 m;
	ArrayList<String> dialogue;
	ArrayList<Integer> questions;
	int index = 0;
	int questionoffset = 0;
	String character = "";
	
	//create textbox and add text/choices
	public Textbox(MyPanel4 x) {
		//initialize variables
		super("");
		m = x;
		textactive = true;
		dialogue = new ArrayList<String>();
		questions = new ArrayList<Integer>();
		character = m.character;
		
		//week 1
		addText("Welcome to the DEADLY Chisholm Trail! You will be spending 6 weeks crossing the trail. "
			+ "You will spend two weeks in Texas, two VERY DANGEROUS weeks in Oklahoma, and then two weeks in Kansas.");
		addText("You will have parameters like wages, cattle, health, money, and more, all of which can change depending on the decisions you make.");
		addText("This trail also has a trail boss that is prone to crashing out. You will need to be careful to appease him. "
			+ "Additionally, if you make stupid decisions, you will lose money, health, or cattle.");
		addText("At the end, if you are not dead, you will see how much money you made. The goal of the game is to make as much money as possible without dying. Good luck!");
		addText("...");
		
		//choice 1
		String job1 = "";
		if (character.equals("Wrangler")) {
			job1 = "Oh no! One of the horses you are in charge of gets sick. Decide whether to get medicine from the store or let the horse die. "
				+ "   1 - Store \t 2 - Horse dies";
		}
		else if (character.equals("Drover")) {
			job1 = "The cows are getting bored! You need to entertain them. Decide whether to perform a stand-up comedy show or sing the western classic “Party in the USA! "
				+ "1 - Stand-up \t 2 - Sing";
		}
		else {
			job1 = "Decide if you want to make beans or biscuits. Remember the trail boss is a D1 crashout and is a picky eater.\n" + 
			"1 - Beans\t 2- Biscuits";
		}
		addChoicer(job1);
		addText("If you read this, there was an error :C"); //index 5
		
		//week 2
		addText("Welcome to week 2 of the trip! After completing Week 1, everyone on the cattle drive feels happy. Because of this, they threw a party and ate too much food and drank all the beer. ");
		addText("But, this causes you guys to run out of resources WAY too quickly. Also, there’s a sandstorm on the way which you need to avoid.");
		addText("... [A sandstorm is approaching] ...");
		
		//choice 2
		addChoicer("The trail boss trusts you to make the right decision for the cattle drive. Decide whether to keep going or stop at a store.  1 - Keep going  2 - Stop at a store");
		addText("If you read this, there was an error :C");
		
		//week 3
		addText("Congrats on making it to week 3! You guys have made it to the treacherous Oklahoma portion of the trail. Here you will encounter many dangerous situations.");
		addText("Make decisions that you think the trail boss and creators of the game will like.");
		addText("...");
		
		//choice 3
		addChoicer("A rival cattle trail group starts SHOOTING at you :( Decide whether to shoot back or run away. 1 - Run away  2 - Shoot back");
		addText("If you read this, there was an error :C");
		
		//week 4
		addText("Week 4 is finally here! We are swiftly approaching the end of this trip, but not without more problems to face.");
		addText("Oh no! One fourth of the cows have contracted the DEADLIEST and most NOTORIOUS disease on cattle drives, BVD (Bovine Viral Diarrhea)!");
		addText("They are pooping their guts out and spreading it to all the other cows. It is quite gross. Please make wise decisions...");
		addText("... [Bovine Viral Diarrhea incoming] ...");
		
		//choice 4
		addChoicer("Decide whether to kill the infected cows or try to save them.   1 - Kill them  2 - Save them");
		addText("If you read this, there was an error :C");
		
		//week 5
		addText("Congratulations on making it to week 5! You have made it through the most perilous part of the trail in Oklahoma and have finally reached Kansas.");
		addText("Now you have to focus on getting to the cattle market. Do your tasks well and everything will be ok.");
		addText("... [Mental instability approaching] ...");
		
		//choice 5
		String job2 = "";
		if (character.equals("Wrangler")) {
			job2 = "The trail boss tells you to go scrape horse poop, but you are really tired. Decide whether to scrape horse poop or take a nap. "
				+ "1 - Horse poop \t 2 - Nap";
		}
		else if (character.equals("Drover")) {
			job2 = "You see a cow running rouge. He has the cow zoomies! Decide whether to play tag with the cow or beat it with a stick. "
				+ "      1 - Tag \t 2 - Beat";
		}
		else {
			job2 = "Your best friend, the trail boss is crying. Decide whether to console him or leave him alone. " + 
			"1 - Console him \t 2- Leave him alone";
		}
		addChoicer(job2);
		addText("If you read this, there was an error :C");
		
		//week 6
		addText("It is officially week 6! REJOICE! CELEBRATE! You have successfully made it to the cattle market without dying!");
		addText("Now it’s time to sell your cows and see how much you made.");
		addText("Are you the most successful cattle trailer to ever hit the cattle trail?");
		addText("... [Market Approaching] ...");
		addText("   ");
		
		//choice 6
		addChoicer("You arrive at the market and a man offers to buy your cows for 10 dollars apiece. Do you accept the deal? \n 1 - yes  2 - no");
		addText("If you read this, there was an error :C");
	}
	
	//add text
	public void addText(String s) {
		dialogue.add(s);
	}
	
	//add choice
	public void addChoicer(String s) {
		questions.add((Integer)dialogue.size());
		addText(s);
	}
	
	//draw text
	public void draw(Graphics g) {
		if (index < dialogue.size()) {
			setString(dialogue.get(index));
			g.setColor(Color.BLACK);
			super.draw(g);
		}
	}
	
	//increments text
	public void inc() {
		index++;
		if (questions.contains(index+1)) {
			textactive = false;
		}
	}
	
	//set dialogue index
	public void setIndex(int i) {
		index = i;
	}
	
	//make choices
	public void choice (int index, int option) {
		String s = "";
		
		//trigger decision made
		boolean triggered = false;
		if (option == 1 || option == 2) {
			triggered = true;
		}
		
		//switch case of decisions based on question index
		switch (index) {
			
			//week 1
			case 0:
				//cook
				if (character.equals("Cook")){
					if (option == 1) {
						s = "The trail boss HATES beans. He docks $20 from your pay and throws the rest of the beans at you giving you 2nd degree burns that cost $200 to get treated." 
							+ "You also lose 40 health points from your burns. Good job!";
						m.income(-20);
						m.moneyChange(-200);
						m.healthChange(-40);
					}
					else if (option ==2) {
						s = "The trail boss LOVES biscuits and gives you a $50 bonus because you made them awesomely.";
						m.income(50);
					}
				}
				//wrangler
				else if (character.equals("Wrangler")){
					if (option == 1) {
						s = "The trail boss yells at you for slowing the group down, but ultimately understands where you’re coming from. He only takes away $10 from you.";
						m.moneyChange(-10);
					}
					else if (option == 2) {
						s = "The trail boss crashes out because you let a horse die and horses are expensive. He docked $80 from your paycheck.";
						m.income(-80);
					}
				}
				//drover
				else if (character.equals("Drover")){
					if (option == 1) {
						s = "The cows hate it and so does the trail boss. One of the cows steps on your foot and you have to pay $50 to get it fixed. You also lose 25 health points. Womp womp.";
						m.moneyChange(-50);
						m.healthChange(-25);
					}
					else if (option ==2) {
						s = "The trail boss and the cows LOVE “Party in the USA” and the trail boss gives you a $50 bonus for your amazing singing skills.";
						m.income(50);
					}
				}
				//update
				if (triggered) {
					dialogue.set(questions.get(0)+1, s);
					m.salary();
				}
				break;
				
			//week 2
			case 1:
				//universal
				if (option == 1) {
					s = "You guys successfully avoid the sandstorm, "
						+ "but resources cost more on the next portion of the trail, so you end up losing $50.";
					m.moneyChange(-50);
				}
				else if (option ==2) {
					s = "You absolute moron. There’s a sandstorm pulling up and you decide to stop. "+
						"You lose 500 cows which will make you lose money eventually.";
					m.cowChange(-500);
				}
				//update
				if (triggered) {
					dialogue.set(questions.get(1)+1, s);
					m.salary();
				}
				break;
				
			//week 3
			case 2:
				//universal
				if (option == 1){
					s = "Coward alert! Coward alert! The trail boss is upset and docked $" + 2000*m.difficulty + " from your balance.";
					m.moneyChange(-2000*m.difficulty);					
				}
				else if (option ==2) {
					s = "Turns out the rival cattle gang was actually trying to just have fun, "+
						"and since you entertained them, they gave you 150 cows! You got a bit hurt though, so that's minus 25 health points.";
					m.cowChange(150);
					m.healthChange(-25);
				}
				//update
				if (triggered) {
					dialogue.set(questions.get(2)+1, s);
					m.salary();
				}
				break;
				
			//week 4
			case 3:
				//universal
				if (option == 1){
					s = "You lose a quarter of the cows, but no more than that.";
					m.cowChange(m.cattle / -4);					
				}
				else if (option ==2) {
					s = "Half the cows contract BVD and die. Also, unsanitary conditions lead to you getting sick and losing 75 health points.";
					m.cowChange(m.cattle / -2);
					m.healthChange(-75);
				}
				//update
				if (triggered) {
					dialogue.set(questions.get(3)+1, s);
					m.salary();
				}
				break;
				
			//week 5
			case 4:
				//cook
				if (character.equals("Cook")){
					if (option == 1) {
						s = "The trail boss declares his undying love for you while sobbing. " 
							+ "To reap the benefits, you guys enter a relationship and the trail boss adds $200 to your paycheck.";
						m.income(200);
					}
					else if (option ==2) {
						s = "The trail boss is very upset and yells at you for no reason. He eventually gets so upset he takes away your entire paycheck.";
						m.setIncome(0);
					}
				}
				//wrangler
				else if (character.equals("Wrangler")){
					if (option == 1) {
						s = "Because you are too tired, you end up accidentally flinging horse poop at the trail boss. Enraged, he docked $200 from your paycheck.";
						m.income(-200);
					}
					else if (option == 2) {
						s = "Fortunately, the trail boss doesn’t notice you napping and you are able to keep all your money. Your health increases by 15.";
						m.healthChange(15);
					}
				}
				//drover
				else if (character.equals("Drover")){
					if (option == 1) {
						s = "This unfortunately does not calm the cow down, and he headbutts you. You lose 20 health points and $300 to pay for head repairs.";
						m.moneyChange(-300);
						m.healthChange(-20);
					}
					else if (option ==2) {
						s = "The cow kills you.";
						m.moving = false;
						m.die = true;
						m.reason = "The cow killed you.";
						m.losex = 520;
					}
				}
				//update
				if (triggered) {
					dialogue.set(questions.get(4)+1, s);
					m.salary();
				}
				break;
				
			//week 6
			case 5:
				//if accept first offer
				if (option == 1){
					m.moneyChange(m.cattle/2);
					s = "Your deal is successful, and after the trail boss takes his share, you successfully receive your share of 50 cents a cow and cash out with $" + m.money + 
						". You journey your way out of the market...";
					m.cowChange(-1 * m.cattle);
					//meteor strike
					addText("As the markets close, you begin to head back to your wagon, prepared to head home...");
					addText("You have successfully made it to the end of the trail! Whether you lost all of your money or made it big, let's see your results...");
					addText("...");
					addText("... [A rumbling is heard in the distance] ...");
					addText("Oh no! a meteor is approaching the market!...");
					addText("   ");
					//meteor strike decision
					//addChoicer("filler");
					addChoicer("What do you do?  1 - RUN FOR YOUR LIFE!!! 2 - Accept your fate...");
					addText("If you read this, there was an error :C");
					
					questionoffset = 1;
				}
				//if reject decision
				else if (option ==2) {
					s = "The man gets angry, yelling at you for rejecting him and muttering that you will soon regret your decision. "+
						"You continue to look for more buyers...";
					//second offer
					addText("Oh no! The sun is now setting, and many businessmen are going home. The railroads are starting up their engines and leaving...");
					addText("Hurry and sell your cows before you can't anymore...");
					addText("   ");
					//second offer decision
					addChoicer("At the last minute, a mysterious man approaches and offers to buy your cows for 20 dollars a piece. Do you accept the deal? \n 1 - yes  2 - no");
					addText("If you read this, there was an error :C");
				}
				//update
				if (triggered) {
					dialogue.set(questions.get(5)+1, s);
				}
				break;
				
			//second offer
			case 6:
				//if accept offer
				if (option == 1){
					m.moneyChange(-400);
					s = "Oh no! The sketchy man took all of your cattle and pickpocketed $400 from your wallet." + 
						"You earn no money and end with only $" + m.money + ". Disappointed, you journey your way  of the market...";
					m.cowChange(m.cattle * -1);		
					
					//meteor strike	
					addText("As the markets close, you begin to head back to your wagon, prepared to head home...");
					addText("You have (un)successfully made it to the end of the trail! Whether you lost all of your money or made it big, let's see your results...");
					addText("...");
					addText("... [A rumbling is heard in the distance] ...");
					addText("Oh no! a meteor is approaching the market!...");
					addText("   ");
					//meteor decision
					addChoicer("What do you do?  1 - RUN FOR YOUR LIFE!!! 2 - Accept your fate...");
					addText("If you read this, there was an error :C");
					
				}
				//if reject offer
				else if (option == 2) {
					s = "The sketchy man takes off his hood and reveals himself... he's actually a mafia boss! "+
						"The man snaps his fingers and his henchmen blindfold you, dragging you away from your former life.";
					m.die = true;
					m.reason = "You were kidnapped by the mafia.";
					m.losex = 290;					
				}
				
				//update
				if (triggered) {
					dialogue.set(questions.get(6)+1, s);
				}
				break;
			
			//meteor strike
			case 7:
				int qindex = 7-questionoffset;
				if (option == 1){
					int percent = (int)(Math.random()*100);
					
					//change of dying depends on the game difficulty and health
					if (percent<=20*(0.5*m.difficulty+0.5)+0.3*(100-m.health)){
						s = "You didn't run fast enough...";
						dialogue.set(questions.get(qindex)+1, s);
						m.die = true;
						m.reason = "You were fireballed to death by a meteor.";
						m.losex = 100;
					}
					
					//change of surviving ranges from 60 to 80%
					else{
						//if negative balance die
						if (m.money < 0){
							s = "Somehow you made it out alive! but amidst the chaos... a figure approaches in the distance. The IRS has arrived to arrest you for your crippling debt and tax evasion!";
							dialogue.set(questions.get(qindex)+1, s);
							m.die = true;
							m.reason = "You were jailed by the IRS.";
							m.losex =370;
						}
						//else win
						else{
							s = "Somehow you made it out alive!";
							dialogue.set(questions.get(qindex)+1, s);
							m.win = true;
							
						}
					}
				}
				
				//if accept fate
				else if (option == 2) {
					int percent = (int)(Math.random()*100);
					
					//change of dying 50%
					if (percent<=50){
						s = "You calmly meet your fate as you stand with arms wide open...";
						dialogue.set(questions.get(qindex)+1, s);
						m.die = true;
						m.reason = "You were fireballed to death by a meteor.";
						m.losex = 100;
					}
					
					//change of surviving 50%
					else{
						//if negative balance die
						if (m.money < 0){
							s = "Somehow you made it out alive! but amidst the chaos... a figure approaches in the distance. The IRS has arrived to arrest you for your crippling debt and tax evasion!";
							dialogue.set(questions.get(qindex)+1, s);
							m.die = true;
							m.reason = "You were jailed by the IRS.";
							m.losex =370;
						}
						//else win
						else{
							s = "Somehow your suicidal idealation was unsuccessful... while everyone else burns, the meteor barely misses you.";
							dialogue.set(questions.get(qindex)+1, s);
							m.win = true;
							
						}
					}				
				}
				break;
				
				
		}
		inc();
	}
}

//super class text for dialogue
class Text {
	static int LINE_WIDTH = 65;
	String str; int length = 0;
	int x; int y;
	int font;
	public Text(String s) {
		this(s, 120, 570);
	}
	public Text(String s, int xx, int yy) {
		str = s;
		length = str.length();
		x = xx;
		y = yy;
	}
	public void setString(String s) {
		str = s;
	}
	public void setFont(int f) {
		font = f;
	}
	//draw dialogue
	public void draw(Graphics g) {
		int i = 0;
		int j = 0;
		String[] words = str.split(" ");
		while (j < words.length) {
			String line = "";
			while (line.length()<LINE_WIDTH) {
				if (j < words.length && line.length() + words[j].length() < LINE_WIDTH) {
					line += words[j] + " ";
					j++;
				}
				else {
					break;
				}
			}
			g.drawString(line, x, y+60*i);
			i++;
		}
	}
}

//superclass colortext extends text class
class ColorText extends Text {           
	Color c = Color.BLACK;
	public ColorText(Color cc, String s, int xx, int yy) {
		super(s, xx, yy);
		c = cc;
	}
	public void draw(Graphics g) {
		g.setColor(c);
		super.draw(g);
	}
}
