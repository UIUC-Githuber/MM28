package starterpack.AI.MoveState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import starterpack.Main;
import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.util.Utility;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.fasterxml.jackson.annotation.JsonTypeInfo.None;

public class Naive2KnightMoveState extends IMoveState{

    public Naive2KnightMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        Position tele = Teleport();
        if (tele != null) return tele;
        return Move();
        
    }

    public Position TeleForced(){
        return Utility.spawnPoints.get(getPlayerIndex());
    }


    //Move method2, works a little bit
    public Position Move2(){
        Position MyPosition = Utils.GetPosition(this, this.getPlayerIndex());
        Position kbest = null; //nearest crown
        switch (getPlayerIndex()){
            case 0:
            kbest = new Position(4,4);
            break;
            case 1:
            kbest = new Position(5,4);
            break;
            case 2:
            kbest = new Position(4,5);
            break;
            case 3:
            kbest = new Position(5,5);
            break;
        }
        int ydiff = kbest.getY() - MyPosition.getY();
        int xdiff = kbest.getX() - MyPosition.getX();
        int SignOfDirectionX = 0, SignOfDirectionY = 0;
        
        if(xdiff<0){
            SignOfDirectionX = -1;
        }
        else{
            SignOfDirectionX = 1;
        }
        if(ydiff<0){
            SignOfDirectionY = -1;
        }
        else{
            SignOfDirectionY = 1;
        }
        //Decide the steps need to be taken in x and y direction
        Position DestinationNext = new Position();
        int MySpeed = this.getPlayerState().getStatSet().getSpeed();
        
            
            
            if(xdiff<0&&ydiff<0){ //1
            int NewX = Math.max(MySpeed*SignOfDirectionX, xdiff);
            DestinationNext.setX(MyPosition.getX()+NewX);
            int NewY = Math.max((MySpeed - Math.abs(NewX))*SignOfDirectionY,ydiff);
            DestinationNext.setY(NewY+MyPosition.getY());
            }


            else if(xdiff>0&&ydiff<0){ //2
                int NewX = Math.min(MySpeed*SignOfDirectionX, xdiff);
                DestinationNext.setX(MyPosition.getX()+NewX);
                int NewY = Math.max((MySpeed - Math.abs(NewX))*SignOfDirectionY,ydiff);
                DestinationNext.setY(NewY+MyPosition.getY());
            }

            else if(xdiff>0&&ydiff>0){  //3
                int NewX = Math.min(MySpeed*SignOfDirectionX, xdiff);
                DestinationNext.setX(MyPosition.getX()+NewX);
                int NewY = Math.min((MySpeed - Math.abs(NewX))*SignOfDirectionY,ydiff);
                DestinationNext.setY(NewY+MyPosition.getY());
            }

            else if(xdiff<0&&ydiff>0){ //4
                int NewX = Math.max(MySpeed*SignOfDirectionX, xdiff);
                DestinationNext.setX(MyPosition.getX()+NewX);
                int NewY = Math.min((MySpeed - Math.abs(NewX))*SignOfDirectionY,ydiff);
                DestinationNext.setY(NewY+MyPosition.getY());
            }
            
        
        

        //if we can't get to the crown next rount, fource tele, and just buy something.
        if(this.getPlayerState().getGold() >= 8 && xdiff+ydiff >= MySpeed && getPlayerState().getItem()==Item.NONE){ 
            return this.TeleForced();
        }
        Main.LOGGER.info("The gold of bot 2 is: "+ this.getPlayerState().getGold());
        return  DestinationNext;
    }


    @Override
    
    public Position Move() {
            //LogManager.getLogger(Main.class.getName()).info("move");
            // TODO Auto-generated method stub
            Position cPos = Utils.GetPosition(this, getPlayerIndex());
            Position kbest = null; //nearest crown
            switch (getPlayerIndex()){
                case 0:
                kbest = new Position(4,4);
                break;
                case 1:
                kbest = new Position(5,4);
                break;
                case 2:
                kbest = new Position(4,5);
                break;
                case 3:
                kbest = new Position(5,5);
                break;
            }
            int ydiff = Math.abs(kbest.getY() - cPos.getY());
            int xdiff = Math.abs(kbest.getX() - cPos.getX()); // dist between best crown and us
            double slope = Math.abs(ydiff / (double) xdiff);  
            
            int rawYMov = Math.min(ydiff, (int)Math.round(slope * Utils.GetSpeed(this)));  //Y and X movement
            int rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
            
            if(cPos.getY() > kbest.getY()) rawYMov *= -1;  //sign of movement
            if(cPos.getX() > kbest.getX()) rawXMov *= -1;
            
            Main.LOGGER.info("[" + cPos.getX() + "," + cPos.getY() + "]" + " -> cb[" + String.valueOf(rawXMov) + "," + String.valueOf(rawYMov) + "]");
        
            int MySpeed = this.getPlayerState().getStatSet().getSpeed();

         //if we can't get to the crown next rount, fource tele, and just buy something.
         if(this.getPlayerState().getGold() >= 5  && rawYMov+rawXMov >= MySpeed && getPlayerState().getItem()==Item.NONE){ 
            return TeleForced();
        }
        Main.LOGGER.info("The gold of bot 2 is: "+ this.getPlayerState().getGold());
        return new Position(cPos.getX() + rawXMov, cPos.getY() + rawYMov);

        
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }
}
