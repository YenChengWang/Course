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
import java.awt.geom.AffineTransform;     
import java.awt.image.AffineTransformOp;     
import java.awt.image.BufferedImage;     
import java.io.FileInputStream;     
import java.io.IOException;     
import javax.imageio.ImageIO;     
import javax.swing.ImageIcon;     
import javax.swing.JFrame;     
import javax.swing.JLabel;     
import javax.swing.JTabbedPane;     

class Game 
{
    static Card[][] board=new Card[11][7]; //virtual board
    static
    {
        int i,j;
        for(i=0;i<11;i++){
            for(j=0;j<7;j++){
                board[i][j] = null;
            }
        }
        board[1][3]= new Card(1,1,1,1,1,4);                 //initial card
                ArrayList <Card> random=new ArrayList<>();
                random.add(new Card(1,1,1,1,1,3));                  //a gold(goal) card and 2 stone card
                random.add(new Card(1,1,1,1,1,2));
                random.add(new Card(1,1,1,1,1,1));
                Collections.shuffle(random);     //shuffle the position
        //set stone card 
        board[9][1]= random.get(0);
        board[9][3]= random.get(1);
        board[9][5]= random.get(2);

    }

    public static ImageIcon rotatedicon (ImageIcon icon){
        BufferedImage sourceImage = new BufferedImage(
        icon.getIconWidth(),
        icon.getIconHeight(),
        BufferedImage.TYPE_INT_RGB);
        Graphics g = sourceImage.createGraphics();  // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0,0);
        g.dispose();
        
        BufferedImage dstImage = null;        
        AffineTransform transform = new   AffineTransform(-1,0,0,-1,sourceImage.getWidth()-1,sourceImage.getHeight()-1);    
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);     
        dstImage = op.filter(sourceImage, null);   
        return new ImageIcon(dstImage);
    }

    public static boolean judge(int x,int y,int flip,Card put)
    {
        //Card[][] board=new Card[7][11]; is surrounded with null 
        boolean L=false,R=false,T=false,D=false;
        int temp;
        //Check reasonable position input
        if(x<1||y<1||x>9||y>5){
            System.out.println("You can not choose this position!!!"); //warning on label
            return false;
        }
        //Revise : check the chosen position is null/ (x,y)-->[x][y]/ use getter
        if(board[x][y]==null){ 
             //notice edge : check at least 1 neighbor is no null
            if(board[x-1][y]!=null || board[x+1][y]!=null || board[x][y-1]!=null || board[x][y+1]!=null){
                if(flip==0){
                    if((board[x-1][y]!=null&&board[x-1][y].pass==1)
                    ||(board[x+1][y]!=null&&board[x+1][y].pass==1)
                    ||(board[x][y-1]!=null&&board[x][y-1].pass==1)
                    ||(board[x][y+1]!=null&&board[x][y+1].pass==1)) //at least 1 pass
                    {
                        if(board[x][y-1]==null){
                            L = true;
                        }
                        else if(board[x][y-1]!=null&&put.left==board[x][y-1].right){
                            L = true;
                        }
                        if(board[x][y+1]==null){
                            R = true;
                        }
                        else if(board[x][y+1]!=null&&put.right==board[x][y+1].left){
                            R = true;
                        }
                        if(board[x-1][y+1]==null){
                            T = true;
                        }
                        else if(board[x-1][y]!=null&&put.top==board[x-1][y].down){
                            T = true;
                        }
                        if(board[x+1][y]==null){
                            D = true;
                        }
                        else if(board[x+1][y]!=null&&put.down==board[x+1][y].top){
                            D = true;
                        }
                        if(L==true&&R==true&&T==true&&D==true){
                            board[x][y] = put;
                            return true;
                        }
                    }
                }
                else if(flip==1){
                    if((board[x-1][y]!=null&&board[x-1][y].pass==1)
                    ||(board[x+1][y]!=null&&board[x+1][y].pass==1)
                    ||(board[x][y-1]!=null&&board[x][y-1].pass==1)
                    ||(board[x][y+1]!=null&&board[x][y+1].pass==1)) //at least 1 pass
                    {
                        if(board[x][y-1]==null){
                            L = true;
                        }
                        else if(board[x][y-1]!=null&&put.right==board[x][y-1].right){
                            L = true;
                        }
                        if(board[x][y+1]==null){
                            R = true;
                        }
                        else if(board[x][y+1]!=null&&put.left==board[x][y+1].left){
                            R = true;
                        }
                        if(board[x-1][y]==null){
                            T = true;
                        }
                        else if(board[x-1][y]!=null&&put.down==board[x-1][y].down){
                            T = true;
                        }
                        if(board[x+1][y]==null){
                            D = true;
                        }
                        else if(board[x+1][y]!=null&&put.top==board[x+1][y].top){
                            D = true;
                        }
                        if(L==true&&R==true&&T==true&&D==true){
                            temp = put.left;
                            put.left = put.right;
                            put.right = temp;
                            temp = put.top;
                            put.top = put.down;
                            put.down = temp;
                            board[x][y] = put;
                            return true;
                        }
                    } //Put the card on the boaed successfully!
                }
            }
        }
        return false;
    }
}