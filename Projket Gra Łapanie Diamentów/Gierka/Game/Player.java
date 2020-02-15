package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Player słuzy do przechowywania informacji o graczach
 */
public class Player
{
    public int scoreStatus;
    public int colorNumber;
    public int x, y;
    public int foodShow;
    public int id;

    public int[] foodX = new int[6];
    public int[] foodY = new int[6];
    public int[] foodColor = new int[6];
    public int[] foodStatus = new int[6];
    public int[] foodRender = new int[6];
    public boolean up = false, down = false, left = false,right = false;

    public Client client;
    public String nickname;
    BufferedImage image, diamond1, diamond2, diamond3, bucket0, bucket1, bucket2, bucket3;

    /**
     * @param id identyfikator użytkownika
     * @param x pozycja horyzontalna na mapie
     * @param y pozycja vertykalna na mapie
     * @param color kolor wiaderka uzytkownika
     * @param score punkty uzytkownika
     * @param food status renderingu jedzenia
     * @param client umoliwia poalczenie z serwerem
     */
    public Player(int id, int x, int y, int color, int score, int food, Client client){

        this.id = id;
        this.x = x;
        this.y = y;
        this.colorNumber = color;
        this.scoreStatus = score;
        this.foodShow = food;
        this.client = client;

        foodY[0]=-150;
        foodY[1]=-225;
        foodY[2]=-300;
        foodY[3]=-375;
        foodY[4]=-525;
        foodY[5]=-475;

        for (int i = 0; i <= 5; i++){
            foodStatus[i]=foodY[i];
        }

        foodX[0]=150;
        foodX[1]=800;
        foodX[2]=250;
        foodX[3]=650;
        foodX[4]=720;
        foodX[5]=70;

        foodColor[0]=1;
        foodColor[1]=3;
        foodColor[2]=2;
        foodColor[3]=3;
        foodColor[4]=1;
        foodColor[5]=2;


        for (int i = 0; i <= 5; i++){
            foodRender[i]=1;
        }

        try {
            diamond1 = ImageIO.read(new File("Gierka\\Game\\Textures\\1.png"));
            diamond2 = ImageIO.read(new File("Gierka\\Game\\Textures\\2.png"));
            diamond3 = ImageIO.read(new File("Gierka\\Game\\Textures\\3.png"));
            bucket0 = ImageIO.read(new File("Gierka\\Game\\Textures\\bucket0.png"));
            bucket1 = ImageIO.read(new File("Gierka\\Game\\Textures\\bucket1.png"));
            bucket2 = ImageIO.read(new File("Gierka\\Game\\Textures\\bucket2.png"));
            bucket3 = ImageIO.read(new File("Gierka\\Game\\Textures\\bucket3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * odpowiada za aktualziacje pozycji xy na podstawie otzrymywanych sygnalow z kalwiatury
     */
    public void updatePositions() {

        if(up == true)
        {
            if(y-3>50){
                y+=-3;
            }
        }

        if(down == true){
            if(y+3<550){
                y += 3;
            }
        }

        if(left == true){
            if(x-3>50){
                x+=-3;
            }
        }

        if(right == true){
           if(x+3<850){
                x+=3;
           }
        }
    }

    /** odpowiada za render graczy
     * @param g parametr Graphic umozliwiajacy rysowanie w oknie
     */
    public void renderPlayer(Graphics g) {
            if (colorNumber == 0) {
                image = bucket0;
            }
            else if (colorNumber == 1) {
                image = bucket1;
            }
            else if (colorNumber == 2) {
                image = bucket2;
            }
            else if (colorNumber == 3) {
                image = bucket3;
            }

        g.drawImage(image, x,y,null);
    }


    /** odpowiada za render jedzenia
     * @param g parametr Graphic umozliwiajacy rysowanie w oknie
     */
    public void renderFood(Graphics g) {
        if(foodShow == 1)
        {
            for (int i = 0; i <= 5; i++) //5
            {
                if(foodRender[i]==1) {

                        if (foodColor[i] == 1) {
                            image = diamond1;
                        }
                        else if (foodColor[i] == 2) {

                            image = diamond2;
                        }
                        else if (foodColor[i] == 3) {
                            image = diamond3;
                        }
                    g.drawImage(image, foodX[i],foodY[i],null);
                }
            }
        }
    }

}
