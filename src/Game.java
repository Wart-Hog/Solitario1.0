import java.util.ArrayList;
import java.util.Stack;

public class Game {
    private Stack<Card> gameDeck;
    private Stack<Card> auxDeck;
    private Card[][] gameField;
    private Card[][] finalDecks;

    public Game(){
        Deck cards = new Deck();
        auxDeck = new Stack<>();
        gameField = new Card[20][7];
        finalDecks = new Card[13][4];
        gameDeck = cards.getDeck();
        System.out.println(gameDeck);
        System.out.println(gameDeck.size());
        startGame();
        System.out.println(toString());
        showCard();
        showCard();
        showCard();
        showCard();
        showCard();
        showCard();
        showCard();
        System.out.println(auxDeck);
        moveCardFromDeck(2,1);
        System.out.println(auxDeck.peek());
        System.out.println(auxDeck);
        System.out.println(toString());
        moveCardFromGrid(6,6,1,0);
        System.out.println(toString());
        System.out.println(gameDeck.size()+auxDeck.size());
        cardGroup(1,1,3);
        System.out.println(toString());

    }
    // metodo che inizializza la griglia a inizio gioco
    private void startGame(){
        for (int i = 0; i < this.gameField.length; i++){
            for (int j = 0; j< this.gameField[i].length; j++){
                if (i==0) {
                    this.gameField[i][j] = this.gameDeck.pop();
                }else if (i==1 && j<6){
                    this.gameField[i][j+1] = this.gameDeck.pop();
                }else if (i==2 && j<5){
                    this.gameField[i][j+2] = this.gameDeck.pop();
                }else if (i==3 && j<4) {
                    this.gameField[i][j+3] = this.gameDeck.pop();
                }else if (i==4 && j<3) {
                    this.gameField[i][j+4] = this.gameDeck.pop();
                }else if (i==5 && j<2) {
                    this.gameField[i][j+5] = this.gameDeck.pop();
                }else if (i==6 && j<1) {
                    this.gameField[i][j+6] = this.gameDeck.pop();
                }
            }
        }
    }
    /* metodo che se invocato mostra l'ultima carta del mazzo principale, la sposta in un mazzo ausiliario
        se il mazzo principale è vuoto, verrà rimpiazzato dal mazzo ausiliario che verrà azzerato.
     */
    public Card showCard(){
        if (gameDeck.size()==0){
            gameDeck=auxDeck;
            auxDeck.clear();
        }else
            auxDeck.push(gameDeck.pop());
            System.out.println(auxDeck.peek());
            return auxDeck.peek();
    }
    // metodo che ritorna true se la carta da muovere sarà posizionata sopra una carta con colore diverso e valore maggiore di 1
    public boolean canMoveCard(Card c, int i, int j){
        if (i==0 && this.gameField[i][j]==null){ // per mettere le carte nella riga 0 se libera
            return true;
        }else if (c.getColor() != this.gameField[i-1][j].getColor() &&
                c.getRealCardValue() == this.gameField[i-1][j].getRealCardValue()-1 &&
                this.gameField[i][j]==null && i>0 && j>=0 && j<=6){
            return true;
        }else
            return false;
    }
    //metodo che sposta le carte dal mazzo ausiliario alla griglia di gioco
    public void moveCardFromDeck(int i, int j){
        if (canMoveCard(auxDeck.peek(), i, j)){
            this.gameField[i][j] = auxDeck.pop();
        }else System.out.println("mossa non valida");
    }
    //metodo che sposta le carte all'interno della griglia di gioco
    public void moveCardFromGrid(int x, int y, int i, int j){
        if (canMoveCard(this.gameField[x][y], i, j) && this.gameField[x+1][y]==null){
            this.gameField[i][j] = this.gameField[x][y];
            this.gameField[x][y] = null;
        }else System.out.println("mossa non valida");
    }
    //controllo se una serie di carte sono selezionabili
    private boolean canMovecardGroup(int i, int j, int x){
        for (int k = i; k<=x-k; k++) {
            if ((this.gameField[k][j].getRealCardValue() == this.gameField[k + 1][j].getRealCardValue() + 1) &&
                this.gameField[x + 1][j] == null && this.gameField[x][j] !=null &&
                    this.gameField[k][j].getColor() != this.gameField[k + 1][j].getColor()) {
                return true;
            }
        }
        return false;
    }
    //metodo che crea un array temporaneo contenente carte della stessa colonna ordinate con valore decrescente
    public Card[] cardGroup(int i, int j, int x){
        //i e j sono la prima carta della colonna e x l'ultima della stessa colonna j
        int columnSize=x-i;
        Card[] auxArray = new Card[columnSize+1];
        int n=0;
        if (canMovecardGroup(i, j, x)) {
        for (int k = i; k <= x; k++) {
                auxArray[n] = this.gameField[k][j];
                n++;
                this.gameField[k][j] = null;
            }
            for (Card c : auxArray) {
                System.out.println(c.toString());
            }
        }else System.out.println("mossa non valida");

        return auxArray;
    }
    public void moveCardGroup(int i, int j, int x, int k, int z){

    }

    public String toString() {
        String result = "";
        for (int i = 0; i < this.gameField.length; i++) {
            result += "\n[";
            for (int j = 0; j < this.gameField[i].length; j++) {
                String value = this.gameField[i][j] != null ?
                        String.valueOf(this.gameField[i][j]) /*"card"*/ : "               ";
                result += "[" + value + "]";
            }
            result += "]";
        }
        return result;
    }
}
