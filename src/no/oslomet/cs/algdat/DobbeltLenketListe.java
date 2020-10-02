package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {             //konstruktør

            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    //konstruktør
    public DobbeltLenketListe() {
        hode = hale = null;
        endringer = 0;
        antall = 0;

    }
    //Hjelpekode
    private Node<T> finnNode(int indeks)
    {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++){
            p = p.neste;
        }
        return p;
    }

    public DobbeltLenketListe(T[] a) {
        this();         //alle variabler er nullet
        Objects.requireNonNull(a,"Tabellen a er null!");
        int i = 0;
        for(;i < a.length && a[i] == null; i++);        //Finer den første i a som ikke er null

        if(i < a.length){
            Node<T> p = hode = hale = new Node<>(a[i],null,null);
            antall = 1;

            for (i++; i < a.length; i++){
                if(a[i] != null){
                    p = p.neste = new Node<>(a[i],null,null);
                    antall ++;
                }
            }
            hale = p;
        }



    }

    public Liste<T> subliste(int fra, int til){
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {

        return antall;
    }

    @Override
    public boolean tom() {

        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi,"Null verdier er ikke tillat!");
        if(antall ==0){
            hode = hale = new Node<>(verdi,null,null);
        }else{
            hale = hale.neste = new Node<T>(verdi,null,null);
        }
        antall ++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> q = hode;
        Node <T> p = null;

        while (q != null)
        {
            if (q.verdi.equals(verdi)) {
                break;
            }
            p = q; q = q.neste;
        }

        if (q == null) {
            return false;
        }
        else if (q == hode) {
            hode = hode.neste;
        }
        else {
            p.neste = q.neste;
        }

        if (q == hale){
            hale = p;
        }

        q.verdi = null;
        q.neste = null;

        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

            T fjernverdi;

            if(indeks == 0) {
                fjernverdi = hode.verdi;
                hode = hode.neste;
                if (antall == 1){
                    hale = null;
                }
            } else {
                Node<T> p = finnNode(indeks -1);
                Node<T> q = p.neste;
                fjernverdi = q.verdi;

                if(q == hale) {
                    hale = p;
                }
                p.neste = q.neste;
            }
            antall--;
            endringer++;
            return fjernverdi;
        }

    @Override
    public void nullstill() {
        T temp;
        Node<T> p = hode;
        Node<T> q = null;

        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        endringer++;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');              //starter s med en [

        if(!tom()){
            Node<T> p = hode;       //starter på hode
            s.append(p.verdi);      //så legger den inn i s

            p = p.neste;            //hode blir da neste.

            while( p  != null){             //Denne sjekker om det er flere verdier i listen som ikke er lik null.
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }

        }
        s.append(']');              //avslutter s med en ]
        return s.toString();
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if(!tom()){
            Node<T> p = hale;
            s.append(p.verdi);

            p = p.forrige;

            while( p != null){
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }
        s.append(']');              //avslutter s med en ]
        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


