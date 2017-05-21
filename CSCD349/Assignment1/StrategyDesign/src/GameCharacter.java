class GameCharacter {
    String Name;
    GuitarBehavior Guitar;
    SoloBehavior Solo;

    GameCharacter() {}

    void setGuitar(GuitarBehavior gb) {
        this.Guitar = gb;
    }

    void setSolo(SoloBehavior sb) {
        this.Solo = sb;
    }

    void playGuitar() {
        System.out.print(this.Name);
        this.Guitar.play();
    }

    void playSolo() {
        System.out.print(this.Name);
        this.Solo.play();
    }
}
