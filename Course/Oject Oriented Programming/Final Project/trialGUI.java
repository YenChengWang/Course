
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class card_deck{
        private ImageIcon[] Images=new ImageIcon[22];
    card_deck(){
        int height=62;
        int wild=40;
        ImageIcon image0=new ImageIcon("./rowcards/back.jpg");
        image0.setImage(image0.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image1=new ImageIcon("./rowcards/1(gold).jpg");
        image1.setImage(image1.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));   
        ImageIcon image2=new ImageIcon("./rowcards/1(gold).jpg");
        image2.setImage(image2.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image3=new ImageIcon("./rowcards/3(stone).jpg");
        image3.setImage(image3.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image4=new ImageIcon("./rowcards/4(start).jpg");
        image4.setImage(image4.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image5=new ImageIcon("./rowcards/5(pass2).jpg");
        image5.setImage(image5.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image6=new ImageIcon("./rowcards/6(pass2).jpg");
        image6.setImage(image6.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image7=new ImageIcon("./rowcards/7(pass2.5).jpg");
        image7.setImage(image7.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image8=new ImageIcon("./rowcards/8(pass2.5).jpg");
        image8.setImage(image8.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image9=new ImageIcon("./rowcards/9(pass3).jpg");
        image9.setImage(image9.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image10=new ImageIcon("./rowcards/10(pass3).jpg");
        image10.setImage(image10.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image11=new ImageIcon("./rowcards/11(pass4).jpg");
        image11.setImage(image11.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image12=new ImageIcon("./rowcards/12(block4).jpg");
        image12.setImage(image12.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image13=new ImageIcon("./rowcards/13(block2).jpg");
        image13.setImage(image13.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image14=new ImageIcon("./rowcards/14(block2).jpg");
        image14.setImage(image14.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image15=new ImageIcon("./rowcards/15(block2.5).jpg");
        image15.setImage(image15.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image16=new ImageIcon("./rowcards/16(block2.5).jpg");
        image16.setImage(image16.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image17=new ImageIcon("./rowcards/17(block3).jpg");
        image17.setImage(image17.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image18=new ImageIcon("./rowcards/18(block3).jpg");
        image18.setImage(image18.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image19=new ImageIcon("./rowcards/19(block1).jpg");
        image19.setImage(image19.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image20=new ImageIcon("./rowcards/20(block1).jpg");
        image20.setImage(image20.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        ImageIcon image21=new ImageIcon("./rowcards/back2.jpg");
        image21.setImage(image21.getImage().getScaledInstance(height, wild, Image.SCALE_DEFAULT));
        
        Images[0]=image0;Images[1]=image1;Images[2]=image2;Images[3]=image3;Images[4]=image4;Images[5]=image5;Images[6]=image6;Images[7]=image7;Images[8]=image8;Images[9]=image9;Images[10]=image10;
        Images[11]=image11;Images[12]=image12;Images[13]=image13;Images[14]=image14;Images[15]=image15;Images[16]=image16;Images[17]=image17;Images[18]=image18;Images[19]=image19;Images[20]=image20;  
        Images[21]=image21;
    }
    ImageIcon get(int i){
        return Images[i];
    }
} 
public class trialGUI {
	//--------------------------------------------------------
	public Player[] P =new Player[4];
	
	//--------------------------------------------------------
    public Deck deck = new Deck();
    public Game game = new Game();
    public card_deck cards = new card_deck(); //restore img of cards
    public JFrame frame;
    public String[] name;
    public int att[][];
    public ArrayList<JComponent> GUIComponent;
    public JComponent player_Component[][] = new JComponent[4][6];
    public JPanel[] player_JPanel={new JPanel(),new JPanel(),new JPanel(),new JPanel()};
    public String userinput, userinput1, userinput2, userinput3,userinput4;
    public int player_number=0;

    public trialGUI() {
		//
        identity();
        deck.distribute(P[0],P[1],P[2],P[3]);

        int fill[]  =  { GridBagConstraints.BOTH,
                         GridBagConstraints.VERTICAL,
                         GridBagConstraints.HORIZONTAL,
                         GridBagConstraints.NONE};
        int anchor[] = { GridBagConstraints.CENTER,
                         GridBagConstraints.EAST,
                         GridBagConstraints.SOUTHEAST,
                         GridBagConstraints.SOUTH,
                         GridBagConstraints.SOUTHWEST,
                         GridBagConstraints.WEST,
                         GridBagConstraints.NORTHWEST,
                         GridBagConstraints.NORTH,
                         GridBagConstraints.NORTHEAST};
        String n[] = {"X_value", 
                      "Y_value", 
                      "card_num",
                      "Direction",
                      "Now is Player0 :", 
                      "Player's Identity", 
                      "1",
                      "2",
                      "3",
                      "4",
                      "5",
                      "1",
                      "2",
                      "3",
                      "4",
                      "5",
                      "6",
                      "7",
                      "8",
                      "9",
                      "Set", 
                      "Discard", 
                      "set_all", 
                      "Next",
                      "but4", 
                      "but5", 
                      "but6"};
        name = n;
        int a[][] = {{1, 1, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {1, 2, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {4, 2, 1, 1, 0, 0, fill[3], anchor[0]}, // 0-2, x_value,y_value,card_num
                     {4, 1, 1, 1, 0, 0, fill[3], anchor[0]},    //3 position
                     {1, 14, 2, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 14, 2, 1, 0, 0, fill[3], anchor[0]}, //4-5, player_number and player_identity


                     {1, 4, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 4, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 4, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 4, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 4, 1, 1, 0, 0, fill[3], anchor[0]},//6-10 first row

                     
                     {0, 5, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 6, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 7, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 8, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 9, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 10, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 11, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 12, 1, 1, 0, 0, fill[3], anchor[0]},
                     {0, 13, 1, 1, 0, 0, fill[3], anchor[0]},

                     //11-19  first list


                     {2, 1, 1, 1, 0, 0, fill[3], anchor[5]},
                     {2, 2, 1, 1, 0, 0, fill[3], anchor[5]}, 
                     {5, 2, 1, 1, 0, 0, fill[3], anchor[5]}, //20-22, Jtext
                     {5, 1, 1, 1, 0, 0, fill[3], anchor[5]},// 23 position Jtext

                     {2, 3, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 3, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {4, 3, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 3, 1, 1, 0, 0, fill[3], anchor[0]},
                     //24-27,,Jbutton
                     {1, 5, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 5, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 5, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 5, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 5, 1, 1, 0, 0, fill[3], anchor[0]},//28-32

                     
                     {1, 6, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 6, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 6, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 6, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 6, 1, 1, 0, 0, fill[3], anchor[0]},//33-37

                     
                     {1, 7, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 7, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 7, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 7, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 7, 1, 1, 0, 0, fill[3], anchor[0]},//38-42

                     
                     {1, 8, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 8, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 8, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 8, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 8, 1, 1, 0, 0, fill[3], anchor[0]},//43-47

                     
                     {1, 9, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 9, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 9, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 9, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 9, 1, 1, 0, 0, fill[3], anchor[0]},//48-52

                     
                     {1, 10, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 10, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 10, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 10, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 10, 1, 1, 0, 0, fill[3], anchor[0]},//53-57

                     
                     {1, 11, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 11, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 11, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 11, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 11, 1, 1, 0, 0, fill[3], anchor[0]},//58-62

                     
                     {1, 12, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 12, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 12, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 12, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 12, 1, 1, 0, 0, fill[3], anchor[0]},//63-67

                     
                     {1, 13, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {2, 13, 1, 1, 0, 0, fill[3], anchor[0]}, 
                     {3, 13, 1, 1, 0, 0, fill[3], anchor[0]},
                     {4, 13, 1, 1, 0, 0, fill[3], anchor[0]},
                     {5, 13, 1, 1, 0, 0, fill[3], anchor[0]},//68-72
                     {0, 15, 7, 1, 0, 0, fill[3], anchor[0]},// 73player's component
                     {1, 0, 7, 1, 0, 0, fill[3], anchor [5]}

                 };
        att = a;
        frame = new JFrame("Saboteur");
        GUIComponent = new ArrayList<JComponent>();
    }
     
    public void run() {
        frame.setSize(1000, 600);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //card_deck cards=new card_deck() ;
        int i;
        for (i = 0; i < 20; i++) {
            JLabel nLabel = new JLabel(name[i]);
            GUIComponent.add(nLabel);
        }//0~19 labels
        for (i = 0; i < 4; i++) {
            JTextField nText = new JTextField("", 8);
            GUIComponent.add(nText);
        }//20-23 textfield
        for (i = 20; i < 24; i++) {
            JButton nButton = new JButton(name[i]);
            GUIComponent.add(nButton);
            if(i==22) nButton.setVisible(false);
        }//24-27 button
        for (i =0; i < 45; i++) {
            JButton nButton = new JButton();
            ImageIcon img = cards.get(0);
            if(i==2) 
                {img = cards.get(4);} 
            if(i==40||i==42||i==44) {img = cards.get(21);}
            nButton.setIcon(img);
            GUIComponent.add(nButton);
        }// button
               
        for (i = 0; i < GUIComponent.size(); i++) { //decide how many component to add
            addComponent(i);
        } 
        frame.setVisible(true);
        set_playercomponent(0);
        set_playercomponent(1);
        set_playercomponent(2);
        set_playercomponent(3);

        JLabel instruction= new JLabel("WELCOME! When click \"Set\" place your card. When click \"Discard\" surrender.");
        GridBagConstraints d = new GridBagConstraints();
        d.gridx = att[74][0];
        d.gridy = att[74][1];
        d.gridwidth = att[74][2];
        d.gridheight = att[74][3];
        d.weightx = att[74][4];
        d.weighty = att[74][5];
        d.fill = att[74][6];
        d.anchor = att[74][7];
        frame.add(instruction, d);

        JButton b = (JButton) GUIComponent.get(24);
        b.addActionListener(new set_pictrue());
        b = (JButton) GUIComponent.get(25);
        b.addActionListener(new discard());
        //b = (JButton) GUIComponent.get(26);
        //b.addActionListener(new set_all());
        b = (JButton) GUIComponent.get(27);
        b.addActionListener(new change_player()); 
         ((JButton)GUIComponent.get(27)).setEnabled(false);

        
    }
	//NEW!!! identity------------------------------------------
	public void identity(){
        String[] ID = {"GOOD Dwarf","Saboteur"};
        ArrayList<Integer> id = new ArrayList<Integer>();    
    	id.add(0);
    	id.add(0);
    	id.add(0);
    	id.add(1);
    	Collections.shuffle(id);
        P[0]= new Player(ID[id.get(0)]);
        P[1] = new Player(ID[id.get(1)]);
        P[2] = new Player(ID[id.get(2)]);
        P[3] = new Player(ID[id.get(3)]);
	}
	//-----------------------------------------------------------
    private void set_playercomponent(int i)
    {
        GUIComponent.add(player_JPanel[i]);

        JButton nButton = new JButton();
        ImageIcon img = null;

        img=cards.get(P[i].gethandcard().get(0).imgnum);             //show the first card
        nButton.setIcon(img);
        player_Component[i][0]=nButton;

        nButton = new JButton();
        img=cards.get(P[i].gethandcard().get(1).imgnum);             //show the 2nd card
        nButton.setIcon(img);
        player_Component[i][1]=nButton;

        nButton = new JButton();
        img=cards.get(P[i].gethandcard().get(2).imgnum);             //show the 3rd card
        nButton.setIcon(img);
        player_Component[i][2]=nButton;

        nButton = new JButton();
        img=cards.get(P[i].gethandcard().get(3).imgnum);             //show the 4th card
        nButton.setIcon(img);
        player_Component[i][3]=nButton;

        nButton = new JButton();
        img=cards.get(P[i].gethandcard().get(4).imgnum);             //show the 5th card
        nButton.setIcon(img);
        player_Component[i][4]=nButton;

        nButton = new JButton();
        img=cards.get(P[i].gethandcard().get(5).imgnum);             //show the 6th card
        nButton.setIcon(img);
        player_Component[i][5]=nButton;

        for (int j = 0; j < 6; j++) {
            player_JPanel[i].add(player_Component[i][j]);
        } 
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = att[73][0];
        c.gridy = att[73][1];
        c.gridwidth = att[73][2];
        c.gridheight = att[73][3];
        c.weightx = att[73][4];
        c.weighty = att[73][5];
        c.fill = att[73][6];
        c.anchor = att[73][7];
        frame.add(player_JPanel[i], c);
        if(i==0)
        {
            player_JPanel[i].setVisible(true);
            JLabel l2 = (JLabel) GUIComponent.get(5);
            l2.setText("You are "+P[i].getid()+".");
        }
        else {player_JPanel[i].setVisible(false);}

        
    }
 
    private void addComponent(int i) {
        GridBagConstraints c = new GridBagConstraints();
        int a[] = att[i]; 
         
        c.gridx = a[0];
        c.gridy = a[1];
        c.gridwidth = a[2];
        c.gridheight = a[3];
        c.weightx = a[4];
        c.weighty = a[5];
        c.fill = a[6];
        c.anchor = a[7];
        frame.add(GUIComponent.get(i), c);
    }
	//Choose card and set
    class set_pictrue implements ActionListener{
        public void actionPerformed(ActionEvent event) {
			//P[player_number]

			//x_value
            JTextField t1 = (JTextField) GUIComponent.get(20);
            userinput1 = t1.getText();
            int int1 = Integer.parseInt(userinput1);
			//y_value
            JTextField t2 = (JTextField) GUIComponent.get(21);
            userinput2 = t2.getText();
            int int2 = Integer.parseInt(userinput2);
			//card number
            JTextField t3 = (JTextField) GUIComponent.get(22);
            userinput3 = t3.getText();
            int int3 = Integer.parseInt(userinput3);
			//direction
            JTextField t4 = (JTextField) GUIComponent.get(23);
            userinput4 = t4.getText();
            int int4 = Integer.parseInt(userinput4);
            
        
            if(int1<10&&int1>0&&int2<6&&int2>0&&int3<7&&int3>0){
                if (game.judge(int1,int2,int4,(P[player_number].gethandcard()).get((int3)-1)))
                {
                    JButton b1 = (JButton) GUIComponent.get(27+int2+(int1-1)*5);
                    ImageIcon img = null;

                    int i = player_number;
                    
                    if(int3==1){
                        img=cards.get((((P[i].gethandcard()).get(0)).imgnum));
                    }
                    else if(int3==2){
                        img=cards.get((((P[i].gethandcard()).get(1)).imgnum));
                    }
                    else if(int3==3){
                        img=cards.get((((P[i].gethandcard()).get(2)).imgnum));
                    }
                    else if(int3==4){
                        img=cards.get((((P[i].gethandcard()).get(3)).imgnum));
                    }
                    else if(int3==5){
                        img=cards.get((((P[i].gethandcard()).get(4)).imgnum));
                    }
                    else if(int3==6){
                        img=cards.get((((P[i].gethandcard()).get(5)).imgnum));
                    }
                    if(int4==1){
                        img = Game.rotatedicon(img);
                    }

                    b1.setIcon(img);
                    P[i].discard(int3,deck);
                    change_player_component();
                    //display destination 
                    if(int1==9&&int2==2) display_destination_card(1);
                    if(int1==9&&int2==4) display_destination_card(2);
                    if(int1==8&&int2==1) display_destination_card(3);
                    if(int1==8&&int2==3) display_destination_card(4);
                    if(int1==8&&int2==5) display_destination_card(5);
                    if(deck.getdecktop() == 40){       
                        JOptionPane.showMessageDialog(null,"The deck is out of cards");
                        deck.adddecktop();
                    }
                    print_no_card();
                    ((JButton)GUIComponent.get(24)).setEnabled(false);
                    ((JButton)GUIComponent.get(25)).setEnabled(false);
                    ((JButton)GUIComponent.get(27)).setEnabled(true);
                }
                else 
                {
                    JOptionPane.showMessageDialog(null,"You can't choose the position. PLease input again.");
                }
                
            }
            
        }
    }

    void change_player_component()
    {
        for(int j=0;j<P[player_number].gethandcard().size();j++)
        {
            ImageIcon img2 = null;
            img2=cards.get(P[player_number].gethandcard().get(j).imgnum);            
            ((JButton)player_Component[player_number][j]).setIcon(img2);
        }
        for(int j=P[player_number].gethandcard().size();j<6;j++)
        {
            ImageIcon img2 = null;          
            ((JButton)player_Component[player_number][j]).setIcon(img2);
        } 
    }

    class change_player implements ActionListener{
        public void actionPerformed(ActionEvent event){
            player_JPanel[player_number].setVisible(false);
            player_number=(player_number+1)%4;
            player_JPanel[player_number].setVisible(true);
            JLabel l1 = (JLabel) GUIComponent.get(4);
            l1.setText("Now is Player "+player_number+":");
            JLabel l2 = (JLabel) GUIComponent.get(5);
            l2.setText("You are "+P[player_number].getid()+".");
            ((JButton)GUIComponent.get(24)).setEnabled(true);
            ((JButton)GUIComponent.get(25)).setEnabled(true);
            ((JButton)GUIComponent.get(27)).setEnabled(false);
        }
    }
    class discard implements ActionListener{
        public void actionPerformed(ActionEvent event)
        {
            JTextField t3 = (JTextField) GUIComponent.get(22);
            userinput3 = t3.getText();
            int int3 = Integer.parseInt(userinput3);
            P[player_number].discard(int3,deck);
            change_player_component();
            if(deck.getdecktop() == 40)
            {       
                JOptionPane.showMessageDialog(null,"The deck is out of cards");
                deck.adddecktop();
            }
            print_no_card();
            ((JButton)GUIComponent.get(24)).setEnabled(false);
            ((JButton)GUIComponent.get(25)).setEnabled(false);
            ((JButton)GUIComponent.get(27)).setEnabled(true);
        }
    }


    void display_destination_card(int x)
    {
        int board_card1_imgnum=game.board[9][1].imgnum;
        int board_card2_imgnum=game.board[9][3].imgnum;
        int board_card3_imgnum=game.board[9][5].imgnum;
        ImageIcon img1=cards.get(board_card1_imgnum);
        ImageIcon img2=cards.get(board_card2_imgnum);
        ImageIcon img3=cards.get(board_card3_imgnum);
        JButton b1 = (JButton) GUIComponent.get(68);
        JButton b2 = (JButton) GUIComponent.get(70);
        JButton b3 = (JButton) GUIComponent.get(72);
        if(x==1)
        {  
            b1.setIcon(img1);b2.setIcon(img2); 
            if(board_card1_imgnum==1||board_card2_imgnum==1||board_card1_imgnum==2||board_card2_imgnum==2) JOptionPane.showMessageDialog(null,"GOOD Dwarfs win!");
            else JOptionPane.showMessageDialog(null,"Saboteur win!");
        }
        if(x==2)
        {
            b2.setIcon(img2);b3.setIcon(img3);
            if(board_card2_imgnum==1||board_card3_imgnum==1||board_card2_imgnum==2||board_card3_imgnum==2) JOptionPane.showMessageDialog(null,"GOOD Dwarfs win!");
            else JOptionPane.showMessageDialog(null,"Saboteur win!");
        }
        if(x==3)
        {
            b1.setIcon(img1);
            if(board_card1_imgnum==1||board_card1_imgnum==2) JOptionPane.showMessageDialog(null,"GOOD Dwarfs win!");
            else JOptionPane.showMessageDialog(null,"Saboteur win!");
        }
            
        if(x==4)
        {
            b2.setIcon(img2);
            if(board_card2_imgnum==1||board_card2_imgnum==2) JOptionPane.showMessageDialog(null,"GOOD Dwarfs win!");
            else JOptionPane.showMessageDialog(null,"Saboteur win!");
        }
            
        if(x==5)
        {
            b3.setIcon(img3);
            if(board_card3_imgnum==1||board_card3_imgnum==2) JOptionPane.showMessageDialog(null,"GOOD Dwarfs win!");
            else JOptionPane.showMessageDialog(null,"Saboteur win!");
        }
    }

    void print_no_card()
    {
        if(P[3].gethandcard().size()==0)
        {
            JOptionPane.showMessageDialog(null,"All players have no card. Saboteur win!");
        }
    }
}