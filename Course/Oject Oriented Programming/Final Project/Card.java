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
class Card { 
	
	int top;
	int down;
	int right;
	int left;
	int pass;
	int imgnum;
	Card(int top,int down,int right,int left,int pass,int imgnum)
	{
	this.top=top;
	this.down=down;
	this.right=right;
	this.left=left;
	this.pass=pass;
	this.imgnum=imgnum;
	}
	
}