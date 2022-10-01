package starterpack.AI.MoveState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import starterpack.Main;
import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.Position;
import starterpack.util.Utility;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Naive2KnightMoveState extends IMoveState{

    public Naive2KnightMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        Position tele = Teleport();
        if (tele != null) return tele;
        return Move2();
        
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
        if(Math.abs(xdiff)>Math.abs(ydiff)){
            int NewX = Math.min(MySpeed*SignOfDirectionX, xdiff);
            DestinationNext.setX(MyPosition.getX()+NewX);
            int NewY = Math.min((MySpeed - Math.abs(NewX))*SignOfDirectionY,ydiff);
            DestinationNext.setY(NewY+MyPosition.getY());
        }
        else{
            int NewY = Math.min(MySpeed*SignOfDirectionY, ydiff);
            DestinationNext.setY(MyPosition.getY()+NewY);
            int NewX = Math.min((MySpeed - Math.abs(NewY))*SignOfDirectionX,xdiff);
            DestinationNext.setX(NewX+MyPosition.getX());
        }

        //if we can't get to the crown next rount, fource tele, and just buy something.
        if(this.getPlayerState().getGold() >= 8){ // && xdiff+ydiff >= MySpeed
            this.TeleForced();
        }
        Main.LOGGER.info("The gold of bot 3 is: "+ this.getPlayerState().getGold());
        return  DestinationNext;
    }

    @Override
    public Position Move() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }
}
