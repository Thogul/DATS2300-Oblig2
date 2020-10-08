package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;



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
        indeksKontroll(indeks, false);
        Node<T> p;
        if(indeks<this.antall/2)
        {
            p = this.hode;
            for (int i=0;i<indeks;i++)
            {
                p = p.neste;
            }
        }
        else
        {
            p = this.hale;
            for (int i=this.antall-1;i>indeks;i--)
            {
                p = p.forrige;
            }
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
                    p = p.neste = new Node<>(a[i],p,null);
                    antall ++;
                }
            }
            hale = p;
        }
    }

    public Liste<T> subliste(int fra, int til){
        //throw new UnsupportedOperationException();
        fratilKontroll(antall, fra, til);
        Liste<T> liste = new DobbeltLenketListe<>();
        for (int i=fra;i<til;i++) {
            T verdi = finnNode(i).verdi;
            liste.leggInn(verdi);
        }
        return liste;
    }

    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall +" )");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
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
        if(antall == 0){
            hode = hale = new Node<>(verdi,null,null);
        }else{
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        antall ++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi,"Ikke tillatt med null-verdier!");

        indeksKontroll(indeks,true);

        if (indeks == 0) {
            if(antall == 0) {
                hode = hale = new Node<>(verdi, null, null);
            }else{
                Node<T> p = hode;
                hode = new Node<>(verdi,null,hode);
                p.forrige = hode;
            }
        }else if(indeks == antall){
            Node<T> p = hale;
            hale = new Node<>(verdi,hale,null);
            p.neste = hale;
        }else{
            Node<T> p = hode;
            Node<T> q = hode;
            for(int i = 1; i < indeks; i++){
                p = p.neste;
            }
            for(int i = 1; i < indeks+1; i++){
                q = q.neste;
            }
            Node<T> r = new Node<>(verdi,p,q);
            p.neste = q.forrige = r;
        }
        endringer ++;
        antall ++;
    }

    @Override
    public boolean inneholder(T verdi) {

        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        Node<T> p = this.finnNode(indeks);
        return p.verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if ( verdi == null){
            return -1;
        }
        Node<T> p = hode;
        for(int i = 0; i <antall; i ++){
            if(p.verdi.equals(verdi)){
                return i;
            }
            p =  p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi,"Null verdier er ikke tillat!");
        indeksKontroll(indeks, false);//Litt usikker på om vi skal bruke true eller false her

        Node<T> p = this.finnNode(indeks);
        T gammelverdi = p.verdi;
        p.verdi = nyverdi;
        this.endringer++;
        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> p = hode;
        Node <T> q;
        Node<T> r;

        for (int i = 0;i < antall; i++) {
            if (p.verdi.equals(verdi)) {
                if (antall == 1){
                    hode = hale = null;
                    break;
                }
                if (i == 0) {
                    p = p.neste;
                    p.forrige = null;
                    hode = p;
                    break;
                } else if (i < antall - 1) {
                    p = p.forrige;
                    q = p.neste;
                    r = q.neste;

                    p.neste = r;
                    r.forrige = p;
                    break;
                } else {
                    q = p.forrige;
                    q.neste = null;
                    hale = q;
                    break;
                }
            }
            if (i == antall-1) {
                return false;
            }
            p = p.neste;
        }

        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T fjernverdi;

        if(indeks == 0) {
            if (antall == 1){
                fjernverdi = hode.verdi;
                hale = hode = null;
            } else {
                fjernverdi = hode.verdi;
                hode = hode.neste;
                hode.forrige = null;
            }

        } else if(indeks == antall-1){
            fjernverdi = hale.verdi;
            hale = hale.forrige;
            hale.neste = null;
        } else{
            Node<T> p = finnNode(indeks -1);
            Node<T> q = p.neste;
            Node<T> r = q.neste;
            fjernverdi = q.verdi;
            p.neste = r;
            r.forrige = p;
        }
        antall--;
        endringer++;
        return fjernverdi;
    }

    @Override
    public void nullstill() {

        Node<T> p = hode;
        Node<T> q;

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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
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
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            if (iteratorendringer!=endringer)
            {
                throw new ConcurrentModificationException();
            }
            else if (!hasNext()) {
                throw new NoSuchElementException();
            }
            fjernOK = true;
            T returnVerdi = denne.verdi;
            denne = denne.neste;
            return returnVerdi;
        }

        @Override
        public void remove(){
            //vet ikke når det ikke er lov å remove, bør sjekke testkode kanskje
            if (antall == 0 || !fjernOK) {
                throw new IllegalStateException("Antall i listen er 0!");
            }
            if (iteratorendringer!=endringer) {
                throw new ConcurrentModificationException();
            }
            fjernOK = false;

            if (antall==1) {
                hode = null;
                hale = null;
            }
            else if (denne==null) {
                hale = hale.forrige;
                hale.neste = null;
            }
            else if (denne.forrige==hode) {
                hode = denne;
                denne.forrige = null;
            }
            else {
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
            }
            antall--;
            endringer++;
            iteratorendringer++;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {

        if(liste == null ) {
            throw new NullPointerException("Ingen verdi i listen");
        }

        int n = liste.antall();
        for (int i = 0; i < n-1; i++) {
            int min_index = i;
            for (int j = i+1; j < n; j++){
                if (c.compare(liste.hent(j),liste.hent(min_index)) < 0)
                    min_index = j;
            }

            T temp = liste.hent(min_index);
            liste.oppdater(min_index, liste.hent(i));
            liste.oppdater(i,temp);
        }








    }

} // class DobbeltLenketListe


