public class User {

    public String pseudo;
    public int difficulte;
    public int score;
    public Object avatartxt;

    public User(String pseudo, int difficulte, int score) {
        this.pseudo = pseudo;
        this.difficulte = difficulte;
        this.score = score;
    }

    public String toString(){
        return this.pseudo;
    }

    public int getDifficulte() {
        return this.difficulte;
    }

    public int getScore() {
        return this.score;
    }


    public boolean isSup(User u){
        if (this.getScore()>u.getScore()){
            return true;
        } else {
            return false;
        }
    }
       

}
