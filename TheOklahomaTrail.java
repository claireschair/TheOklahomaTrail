import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.sound.sampled.*;

public class TheOklahomaTrail
{
	public static void main(String...args)
	{
		
		JFrame j = new JFrame();  //JFrame is the window; window is a depricated class
		MyPanel4 m = new MyPanel4();
		j.setSize(m.getSize());
		j.add(m); //adds the panel to the frame so that the picture will be drawn
			      //use setContentPane() sometimes works better then just add b/c of greater efficiency.

		j.setVisible(true); //allows the frame to be shown.

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the dialog box exit when you click the "x" button.
	}

}

class MyPanel4 extends JPanel implements ActionListener,KeyListener, MouseListener  // extends JPanel to support the swing framework
{
	//declaring variables
	private Timer time;
	//music
	private static File startFile,gameFile,creditFile;
    private static AudioInputStream stream;
    private static Clip music,musicFX;
    private static boolean canPause;
    private double end = -1;
    //images
	Image home, background, drover, cook, wrangler, cWagon, dWagon, wWagon, lose;
	//movement
	int x, y, z, losex;
	//variables
	int money, cattle, health, wage, food, week;
	String state, character, reason;
	double miles, weekCount;
	int difficulty;
	//debug
	private boolean debug, turbo;
	int choicer;
	int[] flags = new int[20];
	
	//booleans
	Textbox t;
	Font[] fonts;
	boolean charselect, chardesc, start, showWeek, die, win, showText;
	int gameEnd = 0;
	boolean moving;
	MyPanel4()
	{
		//instantiation
		time = new Timer(10, this); //sets delay to 15 millis and calls the actionPerformed of this class.
		setSize(1800, 1200);
		setVisible(true); //it's like calling the repaint method.
		time.start();
		x=y=losex=0;
		money = 500; cattle = 2000; health = 100; miles = 0; wage = 0; food = 2500; week = 1; weekCount = 0;
		state = "Texas";
		character = ""; reason = "";
		start = false; charselect = false; chardesc = false; showWeek = true; die = false; canPause = false; win = false; showText = false;
		fonts = new Font[201];
		difficulty = 1;
		moving = false;
		choicer = -1;
		
		//load images
		try
		{
			home = ImageIO.read(new File("home.png"));
			background = ImageIO.read(new File("background.png"));
			drover = ImageIO.read(new File("drover.png"));
			wrangler = ImageIO.read(new File("wrangler.png"));
			cook = ImageIO.read(new File("cook.png"));
			dWagon = ImageIO.read(new File("dWagon.png"));
			wWagon = ImageIO.read(new File("wWagon.png"));
			cWagon = ImageIO.read(new File("cWagon.png"));
			lose = ImageIO.read(new File ("lose.png"));
			startFile = new File("Timber.wav");
			
			
			
		}
		catch(Exception e)	{}	
		
		//listeners
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
	}


	public void loadFonts() {
		//import oregon trail font from size 1-200
		for (int i=1; i<=200; i++) {
			Font customFont = null;
			try{
				File fontFile = new File("oregon-bound.otf");
				customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) i);
			}
			catch (Exception e){}
			fonts[i] = customFont;
		}
	}
	
	
	
	public void paintComponent(Graphics g)
	{
		//reset
		loadFonts();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1800,1200);
		
		//homescreen
		checkDie();
		g.drawImage(home, 0, 0, null);
		g.setFont(fonts[20]);
		g.drawString("Click to Start" ,630, 400);
		
		//draw character select screen
		if (charselect){
			drawCharSelect(g);
		}
		
		//draw character description screen
		if (chardesc){
			drawCharDesc(g);
		}
		
		//draw game once started
		if (start){
			drawScreen(g);
			t.setFont(12);
			g.setColor(Color.BLACK);
			g.drawImage(background,-x,0,null);
			g.drawImage(background,-x+2475,0,null);
			if (character.equals("Drover"))
				g.drawImage(dWagon, 10, 30, null);
			if (character.equals("Wrangler"))
				g.drawImage(wWagon, 10, 30, null);
			if (character.equals("Cook"))
				g.drawImage(cWagon, 10, 30, null);
			drawStats(g);
			t.draw(g);

			handleEvents();
		}
		
		//controls and draws game ending (win/lose)
		if (end > 0 && System.currentTimeMillis() > end) {
			if (gameEnd == 1) {
				g.drawImage(lose, 0, 0, null);
				g.setFont(fonts[30]);
				g.setColor(Color.GREEN);
				g.drawString("YOU WIN", 705,430);
				g.setFont(fonts[18]);
				g.drawString("Somehow, you went through all of that", 230, 510);
				g.drawString("and still didn't die to a meteor strike...", 160, 570);
				start = false;
			}
			if (gameEnd == 2) {
				g.drawImage(lose, 0, 0, null);
				g.setFont(fonts[30]);
				g.setColor(Color.RED);
				g.drawString("YOU LOSE", 660,440);
				g.setFont(fonts[20]);
				g.drawString(reason, losex, 520);
				start = false;
			}
		}
		
		//debug screen (unactivatable, debug mode was only usable in draft versions)
		if (debug) {
			g.setColor(Color.RED);
			g.drawString("Choicer: " + choicer,100, 100);
			g.drawString("Moving: " + moving,100, 150);
			g.drawString("textctive: " + t.textactive,100, 200);
			g.drawString("flags: " + flags[0]+flags[1]+flags[2]+flags[3]+flags[4]+flags[5]+flags[6],100, 250);
			for (int i = 0; i<t.questions.size(); i++) {
				g.drawString(t.dialogue.get(t.questions.get(i)), 400, 50+50*i);
			}
		}
	}

	//check if out of health	
	public void checkDie(){
		if (health<=0 && gameEnd == 0) {	
			die = true;
			reason = "You ran out of health.";
			losex = 480;
			moving = false;
		}
	}
	
	//controls movement and next decisions
	public void handleEvents() {
		
		//week 1 to 2
		if (miles > 200 && flags[0] == 0) {
			moving = false;
			t.setIndex(t.questions.get(0));
			choicer = 0;
			week = 2;
		}
		
		//week 2 to 3
		if (miles > 350 && flags[1] == 0) {
			moving = false;
			t.setIndex(t.questions.get(1));
			choicer = 1;
			week = 3;
			state = "Oklahoma";
		}
		
		//week 3 to 4
		if (miles > 450 && flags[2] == 0) {
			moving = false;
			t.setIndex(t.questions.get(2));
			choicer = 2;
			week = 4;
		}
		
		//week 4 to 5
		if (miles > 650 && flags[3] == 0) {
			moving = false;
			t.setIndex(t.questions.get(3));
			choicer = 3;
			week = 5;
			state = "Kansas";
		}
		
		//week 5 to 6
		if (miles > 750 && flags[4] == 0) {
			moving = false;
			t.setIndex(t.questions.get(4));
			choicer = 4;
			week = 6;
		}
		
		//stop movement once reach mile 800
		if (miles > 1000 && flags[4] != 0){
			moving = false;
			showWeek = false;
		}
		
		//first buy offer (manual scroll)
		if (flags[4]!=0 && flags[5] == 0 && t.dialogue.get(t.index).contains("   ")) {
			moving = false;
			showText = false;
			t.setIndex(t.questions.get(5));
			choicer = 5;
		}
		
		//second buy offer (manual scroll)
		if (flags[5] == 2 && flags[6] == 0 && cattle !=0 && t.dialogue.get(t.index).contains("   ")) {
			moving = false;
			showText = false;
			t.setIndex(t.questions.get(6));
			choicer = 6;
		}
		
		//meteor strike (manual scroll)
		if ((flags[5] == 1 && flags[6] == 0 || flags[6] == 1 && flags[7] == 0) && (t.dialogue.size() > t.index) &&(t.dialogue.get(t.index).contains("   "))) {
			moving = false;
			showText = false;
			if (flags[5] == 1){
				t.setIndex(t.questions.get(6));
				choicer = 7;
			}
			else{
				t.setIndex(t.questions.get(7));
				choicer = 7;
			}
		}
		
		//pause if win/lose
		if (win){
			showText = false;
			long st = System.currentTimeMillis();
			end = st + 5*1000;
			win = false;
			gameEnd = 1;
			t.textactive = false;
		}
		if (die){
			showText = false;
			long st = System.currentTimeMillis();
			end = st + 5*1000;
			die = false;
			gameEnd = 2;
			t.textactive = false;
		}
	}
	
	//draw screen for dialogue
	public void drawScreen(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1800,1200);
		g.setColor(Color.WHITE);
		g.fillRect(100,530,1575,200);
		if (moving){
			g.setFont(fonts[11]);
			g.drawString("Press z to continue", 100, 520);
		}
		if (miles >= 800 && showText){
			g.setFont(fonts[11]);
			g.drawString("Press z to continue", 100, 520);
		}
	}
	
	//draw statistics at the bottom of the screen
	public void drawStats(Graphics g){
		colorText(g, "Miles: " + (int) Math.floor(miles), Color.RED, 150, 800);
		colorText(g, "Balance: $" + money, Color.RED, 150, 850);
		colorText(g, "Wage: $" + wage + "/week", Color.RED, 150, 900);
		colorText(g, "Cattle Count: " + cattle, Color.RED, 700, 800);
		colorText(g, "Health: " + health + "hp", Color.RED, 700, 850);
		colorText(g, "Food: " + food + "lbs", Color.RED, 700, 900);
		colorText(g, "Week: " + week, Color.RED, 1300, 800);
		colorText(g, "State: " + state, Color.RED, 1300, 850);
		colorText(g, "Character: " + character, Color.RED, 1300, 900);
		
		
		//show week and location at the start of each movement
		if (showWeek && weekCount<=5){
			Color trBlack = new Color (0,0,0,127);
			g.setColor(trBlack);
			
			if (state == "Texas"){
				g.fillRect(65, 40, 580, 80);	
			}
			else if (state == "Oklahoma"){
				g.fillRect(65, 40, 700, 80);
			}
			else if (state == "Kansas"){
				g.fillRect(65, 40, 620, 80);
			}
			g.setColor(Color.WHITE);
			g.setFont(fonts[20]);
			g.drawString("Week " + week + ": " + state, 100, 100);
			g.setFont(fonts[12]);
		}
		
	}
	
	//draw character description screen
	public void drawCharDesc(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1800,1200);
		
		//intro
		g.setColor(Color.WHITE);
		g.setFont(fonts[20]);
		g.drawString("Traveling through Oklahoma ain't easy!", 100, 100);
		g.drawString("Read the following roles:", 100, 160);
		g.drawString("Press SPACE BAR to continue", 370, 900);
		
		//pictures
		g.drawImage(drover,20,50,null);
		g.drawImage(wrangler,520,50,null);
		g.drawImage(cook,1020,50,null);
		
		//lines
		g.fillRect(150,210,6,400);
		g.fillRect(650,210,6,400);
		g.fillRect(1150,210,6,400);
		g.fillRect(1650,210,6,400);
		
		//characters
		g.setFont(fonts[15]);
		g.drawString("Drover", 310, 650);
		g.drawString("Wrangler", 790, 650);
		g.drawString("Cook", 1340, 650);
		
		//descriptions
		g.setFont(fonts[13]);
		g.drawString("Wage:$210/week", 210, 810);
		g.drawString("Medium Difficulty", 180, 760);
		g.drawString("Manages Cattle", 210, 710);		
		g.drawString("Wage:$140/week", 720, 810);
		g.drawString("Low Risk/Reward", 700, 760);
		g.drawString("Manages Horses", 720, 710);
		g.drawString("Wage:$280/week", 1220, 810);
		g.drawString("High Risk/Reward", 1200, 760);
		g.drawString("Cooks Meals", 1260, 710);
		

		
	}
	
	//draw character select screen
	public void drawCharSelect(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1800,1200);
		g.setColor(Color.WHITE);
		g.setFont(fonts[20]);
		g.drawString("Many different kinds of people made the", 100, 200);
		g.drawString("trip to Oklahoma.", 100, 260);
		g.drawString("You may:", 100, 360);
		g.drawString("1. Be a drover from Ding Dong, Texas", 150, 460);
		g.drawString("2. Be a wrangler from Oatmeal, Texas", 150, 520);
		g.drawString("3. Be a cook from Noodle, Texas", 150, 580);
		g.drawString("4. Find out the differences between ", 150, 640);
		g.drawString("   these choices", 150, 690);
		g.drawString("Type the number of your choice:", 100, 790);
		
	}
	
	//draw colored text with input
	public void colorText(Graphics g, String s, Color c, int x, int y){
		g.setFont(fonts[12]);
		ColorText t = new ColorText(c, s, x, y);
		t.draw(g);
	}
	
	//action performed
	public void actionPerformed(ActionEvent e)
	{
		//control moving (miles/weekCount/background)
		if (moving) {
			x+=10;
			miles += .4;
			if (turbo) {
				miles +=10;
			}
			weekCount +=0.1;
		}
		
		//reset background
		if (x>=2475)
			x=0;
			
		//reset week text visuals
		if (weekCount == 5){
			showWeek = false;
			weekCount = 0;
		}
		
		//repaint
		repaint();
	}
	
	//key pressed
	public void keyPressed(KeyEvent e)
	{
		//choosing a character
		if(charselect && (e.getKeyCode()==KeyEvent.VK_1 || e.getKeyCode()==KeyEvent.VK_2 || e.getKeyCode()==KeyEvent.VK_3)){
			//pick drover + update
			if (e.getKeyCode()==KeyEvent.VK_1){
				character = "Drover";
				wage = 210;
				difficulty = 2;
			}
			//pick wrangler + update
			else if (e.getKeyCode()==KeyEvent.VK_2){
				character = "Wrangler";	
				wage = 140;
				difficulty = 1;
			}
			//pick cook + update
			else if (e.getKeyCode()==KeyEvent.VK_3){
				character = "Cook";
				wage = 280;
				difficulty = 3;
			}
			
			//initialize game variables
			charselect = false;
			start = true;
			moving = true;
			showText = true;
			t = new Textbox(this);
			health -= 25*(difficulty-1);
			
			//play music
			try
			{
				play(1);	
			}
			catch(Exception ex)	{}	
		}
		
		//character select -> character description page
		if (charselect && e.getKeyCode()==KeyEvent.VK_4){
			charselect = false;
			chardesc = true;
		}
		//character description -> character select page
		if (chardesc && e.getKeyCode()==KeyEvent.VK_SPACE){
			chardesc = false;
			charselect = true;
		}
		
		//make choices
		if (choicer != -1) {
			int x = e.getKeyCode();
			boolean chosen = true;
			//if pick decision 1
			if (x==KeyEvent.VK_1) {
				flags[choicer] = 1;
			}
			//if pick decision 2
			else if (x==KeyEvent.VK_2) {
				flags[choicer] = 2;
			}
			//did not choose
			else {
				chosen = false;
			}
			//update decision after chosen
			if (chosen) {
				moving = true;
				t.textactive = true;
				t.choice(choicer, flags[choicer]);
				choicer = -1;
				showText = true;
				showWeek = true;
				weekCount = 0;
			}
			//break;
		}
		//repaint
		repaint();
	}
	
	public void mouseClicked(MouseEvent e)
	{
		//home screen -> character select screen
		if (!start){
			charselect = true;
		}
		//debugging (only for bugfixing)
		/*
		if (start) {
			debug = true;
			turbo = true;
		}
		*/
		//repaint
		repaint();
	}
	
	public void keyTyped(KeyEvent e){}
	
	//key released
	public void keyReleased(KeyEvent e){
		//increment text 
		if (start && e.getKeyCode()==KeyEvent.VK_Z && t.textactive) {
			t.inc();
		}
		if (start && e.getKeyCode()==KeyEvent.VK_X && moving) {
			miles += 50 -(miles % 50);
		}
	}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	//change text active
	public void toggleText() {
		t.textactive = !t.textactive;
	}
	
	//receive salary (update money with wage)
	public void salary(){
		money += wage;
	}
	
	//change income
	public void income(int i) {
		wage += i;
	}
	
	//set income
	public void setIncome(int i){
		wage = i;
	}
	
	//change cows
	public void cowChange(int c) {
		cattle += c;
	}
	
	//change health
	public void healthChange(int h){
		health += h;
	}
	
	//change balance
	public void moneyChange (int m){
		money += m;
	}
	
	//play music 
	private static void play(int a) throws Exception
	{
		switch(a)
		{
			case 1 : stream = AudioSystem.getAudioInputStream(startFile); break;
			default: stream = AudioSystem.getAudioInputStream(startFile); break;
		}
		music = AudioSystem.getClip();
		
		music.open(stream);
		music.start();
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}


}





