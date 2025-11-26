
// AQUESTA CLASSE NO ES POT MODIFICAR
public class Article {
    enum Categoria {
        ALIMENT, SUPERVIVENCIA, FARMACIOLA
    }
    private final int pes;
    private final int volum;
    private final int utilitat; // [1,5] →5 és la màxima
    private final int identificador;
    private final boolean esEssencial;
    private final Categoria familia;
    public Article(int pes, int volum, int utilitat, int identificador, boolean
            esEssencial, Categoria tipus) {
        this.pes = pes;
        this.volum = volum;
        this.utilitat = utilitat;
        this.identificador = identificador;
        this.esEssencial = esEssencial;
        this.familia = tipus;
    }
    public int getPes() {return pes;}
    public int getIdentificador() {return identificador;}
    public int getVolum() {return volum;}
    public int getUtilitat() {return utilitat;}
    public boolean isEssencial() {return esEssencial == true;}
    public Categoria getFamilia() {return familia;}
}//fi classe Article
