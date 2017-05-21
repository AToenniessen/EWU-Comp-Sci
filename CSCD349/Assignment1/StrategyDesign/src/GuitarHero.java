public class GuitarHero {
    public static void main(String[] args) {
        GameCharacter player1 = new GameCharacterSlash("Slash ", new Gibson_Flying_V(), new Smash_Guitar()); //note that constructor could be designed to accept initial behaviors
        GameCharacter player2 = new GameCharacterHendrix("Jimi Hendrix ", new Gibson_SG(), new Guitar_On_Fire());
        GameCharacter player3 = new GameCharacterYoung("Angus Young ", new Fender_Telecaster(), new Jump_Off_Stage());
        player1.playGuitar();
        player2.playGuitar();
        player3.playGuitar();
        player1.playSolo();
        player2.playSolo();
        player3.playSolo();

        //add code below to show the swapping of behaviors
        player1.setGuitar(new Fender_Telecaster());
        player1.playGuitar();
        player1.setSolo(new Jump_Off_Stage());
        player1.playSolo();
        player2.setGuitar(new Gibson_Flying_V());
        player2.setSolo(new Smash_Guitar());
        player2.playGuitar();
        player2.playSolo();
    }
}