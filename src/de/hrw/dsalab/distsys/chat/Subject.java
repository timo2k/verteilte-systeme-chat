package de.hrw.dsalab.distsys.chat;

public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver(Observer observer);
}
