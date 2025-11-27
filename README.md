# Exercici 2. Tècnica del Backtracking
Un excursionista està preparant una sortida i necessita organitzar la seva motxilla de manera que porti els articles necessaris sense excedir ni el pes ni el volum màxim de la motxilla. Cada article té un pes, una utilitat (valor d'importància per a l'excursió), un volum específic, un identificador de si és essencial i família a la que pertany (veure classe Article).

L'objectiu és trobar la selecció d'articles que s’han de posar a la motxilla per a maximitzar el seu volum -quedi el màxim de plena-. Per ser solució s’han de complir les següents restriccions:

 **1. Restriccions de pes i volum:** La suma del pes i volum dels articles seleccionats per la motxilla no pot excedir ni el pes ni volum màxims que pot carregar la motxilla.
 
 **2. Quantitat mínima d'articles essencials:** La motxilla ha d'incloure com a mínim dos articles dels considerats "essencial".
 
 **3. Quantitat màxima d'articles de cada família:** La solució ha d’incorporar com a màxim un número determinat d’articles de la mateixa família -valor demanat a l’usuari mitjançant una operació de lectura-. Hi ha tres famílies d’articles: supervivència, aliment i farmaciola. Cada article només pertany a una d’aquestes famílies.
 
 **4. Incompatibilitats:** Alguns articles són incompatibles entre ells i no poden ser seleccionats junts (per exemple, dos tipus de fogonets diferents que usen combustibles incompatibles). Aquesta informació estarà emmagatzemada en una matriu simètrica de booleans (veure explicació més endavant i classe Solucio).

Si trobes més d’una solució que iguala el volum de la motxilla, entre ambdues s'ha de seleccionar la que maximitza la utilitat total dels articles seleccionats -suma de la utilitat de tots els articles seleccionats-. A igualtat serà irrellevant ambdues de les dues solucions.

### 🔹 Decisió
En cada nivell del backtracking ens fem la pregunta: *Quin article poso a la motxilla?*
Cada decisió consisteix a escollir un article concret.

### 🔹 Domini
El domini de cada decisió són: tots els articles que encara no han estat seleccionats → cal marcatge.
El domini és sempre el mateix conjunt d'articles, però descartant els ja marcats.

### 🔹 Acceptable
Una decisió (agafar un article) és acceptable si:
- És compatible amb tots els articles ja seleccionats. 
- No supera el pes màxim de la motxilla. 
- No supera el volum màxim de la motxilla. 
- No excedeix el màxim d'articles per família.

### 🔹 Solució
Serà solució quan hi hagi un mínim de dos essencials.

### 🔹 Completable = poda
Un conjunt de decisions parcial és completable mentre:
- hi hagi pes i volum per seguir afegint articles, 
- encara quedin articles per provar, i 
- no hem arribat al màxim de totes les families.

### 🔹 Espai de cerca

**Alçada de l'arbre:** és màxima, el nombre d'articles, perquè en cada nivell es
pot decidir afegir un article diferent.

**Amplada de l'arbre** depèn de la implementació: En el nostre codi és exacta, iterem sobre tots els articles.

![arbre](/EspaiCercaRecuGener2025.drawio.png)

### 🔹 Marcatge
És necessari el marcatge, ja que un article no es pot repetir.

### 🔹 Esquema a aplicar
Totes les solucions.

