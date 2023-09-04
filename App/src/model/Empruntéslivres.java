package model;

import java.time.LocalDate;

public class Empruntéslivres {
    private int id;
    private int idlivre;
    private int iddemprunteur;
    private LocalDate datedepris;
    private LocalDate datederetour;

    public int getIdlivre() {
        return idlivre;
    }

    public void setIdlivre(int idlivre) {
        this.idlivre = idlivre;
    }

    public int getIddemprunteur() {
        return iddemprunteur;
    }

    public void setIddemprunteur(int iddemprunteur) {
        this.iddemprunteur = iddemprunteur;
    }

    public LocalDate getDatedepris() {
        return datedepris;
    }

    public void setDatedepris(LocalDate datedepris) {
        this.datedepris = datedepris;
    }

    public LocalDate getDatederetour() {
        return datederetour;
    }

    public Empruntéslivres(int idlivre, int iddemprunteur, LocalDate datedepris, LocalDate datederetour) {
        this.idlivre = idlivre;
        this.iddemprunteur = iddemprunteur;
        this.datedepris = datedepris;
        this.datederetour = datederetour;
    }

    public void setDatederetour(LocalDate datederetour) {
        this.datederetour = datederetour;
    }
}
