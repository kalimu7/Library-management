package service;

public class CleintResult {
    private boolean Addedclient ;
    private int ID;

    public boolean isAddedclient() {
        return Addedclient;
    }

    public CleintResult(boolean addedclient, int ID) {
        Addedclient = addedclient;
        this.ID = ID;
    }

    public void setAddedclient(boolean addedclient) {
        Addedclient = addedclient;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
