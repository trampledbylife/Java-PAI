
import Game.Client;
import Game.LobbyWindow;
import Game.Game;
import Server.PlayerInfo;
import org.junit.jupiter.api.Test;

import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ExampleTests{

    @Test
    void PlayerInfoNullSocket() {
        try {
            new PlayerInfo(1, null);
            fail("PlayerInfo can't have null socket");
        } catch (Exception e) {}
    }

   @Test
    void ClientNullSocketTest(){
        try {
            Client client = new Client(null);
            fail("Expected exception due to null socket");
        }catch (Exception e){

        }
    }

    @Test
    void SendMessageTest(){
        try {
            Client client = new Client(null);
            client.sendMessage("message");
            fail("Expected exception due to null socket");
        }catch (Exception e){
        }
    }

    @Test
    void JoinLobbyTest(){
        try {
            Client client = new Client(null);
            client.joinLobby("message");
            fail("Expected exception due to null socket");
        }catch (Exception e){
        }
    }

    @Test
    void LobbyWindowWidthLessThenZero(){
        try {
            new LobbyWindow(-10, 10);
            fail("Expected exception width can't be less then zero");
        }catch (Exception e){
        }
    }

    @Test
    void LobbyWindowHeightLessThenZero(){
        try {
            new LobbyWindow(10, -10);
            fail("Expected exception height can't be less then zero");
        }catch (Exception e){
        }
    }


    @Test
    void GameNullTest(){
        try {
            new Game(10, 10, null);
            fail("Expected exception height can't be less then zero");
        }catch (Exception e){
        }
    }

}