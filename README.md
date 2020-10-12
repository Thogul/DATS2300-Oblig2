# DATS2300-Oblig2
# Deltagere
Thomas Ax Gulbrandsrød s344043\
Margrethe Luth Røed s344101\
Camilla Bjørklund Gjermundnes s344038

# Beskrivelse
- Oppgave 1\
antall() returnerer antall noder i listen. tom()  er true når antall = 0. Konstruktøren bruker en for-løkke til å finne den første i tabellen a som ikke er null. Tar og legger den første verdien i a som den første noden. Bruker så en for-løkke for å finne alle veridene til indeksene i a som ikke er null og legger dem som en ny node i listen. Siste verdi i a blir lagt inn i hale.
- Oppgave 2\
Løste metoden toString() ved å bruke StringBuilder som setter hver node som er i listen inn i en streng. Koden sjekker at listen ikke er tom. Setter at første node peker på hode og går deretter til neste-peker. Den tar alle nodene som ikke er null og legger dem etterhverandre i strengen ved bruk av en while-løkke. omvendtString() bruker det samme oppsett, bortsett fra at den starter fra halen og går til forrige-peker.
- Oppgave 3\
finnNode sjekker om indeks er riktig og looper gjennom listen til den valgte indeksen og returnerer noden, oppdater bruker finnNode og endrer verdien til node(sjekker om verdi er gyldig og indeks). Subliste bruker finnNode inne i en forloop. Kan effektiviseres med to forløkker istede for finnNode kall hvor hvert element.
- Oppgave 4\
indeksTil() sjekker først om verdien er null. Går gjennom i en for-løkken å sjekker om verdi finnes i listen. Hvis verdi finnes vil den returnere indeksen til den første like verdien. inneholder() sjekker om indeksTil() ikke er lik -1 og vil returnere true hvis listen inneholder verdi.
- Oppgave 5\
leggInn() sjekker om indeks er 0, om listen er tom og om indeksen er det samme som antall. Legger den nye verdien på valgt indeks og flytter den nåværende noden til neste indeks. 
- Oppgave 6\
Fjern verdi: legger inn en verdi som skal fjernes, koden sjekker først om den verdien finnes deretter alle mulige posisjoner hvor den verdien ligger. hode: flytter pekere til riktig pekere, hale: flytter pekere til riktig peker. Returnerer true hvis verdi finnes og false hvis verdi ikke finnes. 
Fjern indeks, gjør akkurat det samme men returer verdien til indeksen som ble fjernet. 
- Oppgave 7\
Nullstill: løper igjennom hele listen og nullstiller alle verdier. 
- Oppgave 8\
  next flytter den nåværende noden en frem, iterator lager et iterator object og returnere det, samme med iterator(indeks) bare at den starter ved en gitt indeks.
- Oppgave 9\
  remove fjerner noden som er til venstre for nåværende noder og sjekker de forskjellige tilfellene som kan oppstå(slette hode, hale, ingenting osv..).
- Oppgave 10\
Sorter: sorterer listen, finner først minste tall, deretter bytter på de. Bruker metodene liste.hent og liste.oppdater.

# Kommentar på testresultat
I oppgave 6 testet vi tid1 og tid2 på to forskjellige pc-er. Den ene fikk tid1: 763 og tid2: 664 og vil da passere testen. Den andre fikk tid1: 1567 og tid2: 856. Den andre får feil på test 6zg pga for stor differanse mellom tidene (dette er nok pga for-løkken i første metode). Vi har testet flere ganger med på første pcen og får ikke opp feilmeldingen på oppgave 6. 

# Warnings
Vi har noen warnings i koden vår.
- En warning på en ubrukt kontruktør, dette er grunnet at vi endte med å ikke bruke den i oppgaven.
- En Warning en tom forloop. Denne skal være tom, grunnet at den bare brukes for å traversere et array til vi finner en ikke null verdi.
- Resten er warnings om at mange av metodene våre kommer til å kaste exception om argumentet er null, dette er jo ønsket funksjonalitet så ikke noe å tenke på.

