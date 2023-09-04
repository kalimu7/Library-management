package model;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int ISBN;
    private String status;

    private int quantie;

    public Livre(String titre, String auteur, int quantie) {
        this.titre = titre;
        this.auteur = auteur;
        this.quantie = quantie;
    }

    public Livre(String titre, String auteur, int ISBN, int quantite) {
        this.titre = titre;
        this.auteur = auteur;
        this.ISBN = ISBN;
        this.status = "disponible";
        this.quantie = quantite;
    }

    public Livre(int id, String titre, String auteur, int ISBN, String status, int quantite) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.ISBN = ISBN;
        this.status = status;
        this.quantie = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantie() {
        return quantie;
    }

    public void setQuantie(int quantie) {
        this.quantie = quantie;
    }
}
