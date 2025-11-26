import java.util.Random;
import java.util.Scanner;

public class Solucio {

    // informació de la motxilla
    private final int volumMotxilla;
    private final int pesMotxilla;
    private final int maxQuantsFamilia;
    // informació dels articles
    private Article[] articles;
    private boolean incompatibilitats[][];
    // solució actual
    private int solucio[]; //guardem indexes escollits
    private int quinVolumActual;
    private int quinPesActual;
    private int quantsEssencialActual;
    private int sumaUtilitatsActual;
    private boolean[] marcats;
    private int quantsFamiliaActual[];

    // per guardar la millor solució
    private int millor[];
    private int quinVolumMillor; // Maximitzar
    private int sumaUtilitatsMillor; // Maximitzar – segon criteri

    public Solucio(int volum, int pes, int quants) {
        volumMotxilla = volum;
        pesMotxilla = pes;
        articles = obtenirDadesArticles();
        incompatibilitats = obtenirIncompatibilitats(articles);
        maxQuantsFamilia = quants;

        // solució actual:
        solucio = new int[articles.length];
        marcats = new boolean[articles.length];
        for (int i = 0; i < articles.length; i++) {
            solucio[i] = -1;
            marcats[i] = false;
        }
        sumaUtilitatsActual = 0;
        quinVolumActual = 0;
        quinPesActual = 0;
        quantsEssencialActual = 0;
        quantsFamiliaActual = new int[3];
        // comptadors de famílies
        for (int i = 0; i < quantsFamiliaActual.length; i++)
            quantsFamiliaActual[i] = 0;

        // millor solució:
        millor = new int[articles.length];
        quinVolumMillor = 0; // valor a maximitzar
        sumaUtilitatsMillor = 0; // segon valor a maximitzar
    }
    private static Article[] obtenirDadesArticles(){
        //crea i retorna els articles que potser portem a la motxilla
        Random random = new Random();
        int countEssencials = 0;
        Article[] articles = new Article[10];
        Article.Categoria[] families = Article.Categoria.values();
        for( int i = 0; i < articles.length; i++){
            int pes = random.nextInt(1,50);
            int volum = random.nextInt(1,20);
            int utilitat = random.nextInt(1,6);
            boolean essencial = (random.nextInt(3) == 0? true:false); //aprox 33% de ser essencial
            if(essencial) countEssencials++;
            int tipus = random.nextInt(3);
            Article.Categoria familia = families[tipus];
            articles[i] = new Article(pes, volum, utilitat, i, essencial, familia);
        }
        if(countEssencials < 2){
            //ens assegurem que hi hagi almenys 2 essencials
            return obtenirDadesArticles();
        }
        return articles;
    }
    private static boolean[][] obtenirIncompatibilitats(Article[] articles){
        // emplena les incompatibilitats entre els objectes donats per paràmetre. Les
        // taules estaran relacionades per índex
        Random random = new Random();
        boolean[][] incompatibles = new boolean[articles.length][articles.length];
        for( int row = 0; row < articles.length; row++){
            for( int col = row+1; col < articles.length; col ++){
                if( random.nextInt(4) == 0){ //25% de probabilitat de ser incompatibles
                    incompatibles[row][col] = true;
                    incompatibles[col][row] = true;
                }
            }
        }
        return incompatibles;
    }
    public static void main(String[] args) {
        /*cal generar aleatòriament -valors que vulgueu- el pes i volum màxim de
        la motxilla a emplenar i demanar a l’usuari el màxim d’articles de cada
        família -un únic valor-. Invoca al backtracking i mostra la solució
        trobada*/
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indica el número màxim d'articles de cada família");
        int quants = scanner.nextInt();
        Solucio s = new Solucio(random.nextInt(100,500), random.nextInt(20,100), quants);
        s.backMillor(0);
        System.out.println(s);
    }

    public void backMillor(int k) {
        // Iterem sobre els articles. En cada nivell triem un article
        for (int i = 0; i < articles.length; i++) {
            if (!marcats[i] && acceptable(i,k)) {
                anotar(i,k);
                if (esSolucio()) {
                // despres de cada decisió cal mirar si és o no solució
                    if (millor()) {
                        guardarMillorSolucio();
                    } // fi millor
                } //else (és solució si com a mínim hi ha 2 essencials, però es poden posar més)
                if (k < articles.length-1 &&
                        quinVolumActual<volumMotxilla &&
                        quinPesActual<pesMotxilla &&
                        (quantsFamiliaActual[0]<maxQuantsFamilia ||quantsFamiliaActual[2]<maxQuantsFamilia ||
                                quantsFamiliaActual[1]<maxQuantsFamilia)) {
                    // queden articles per seleccionar, hi ha lloc (pes i volum)
                    // i no hem arribat el màxim de totes les famílies
                    backMillor(k + 1);
                }
                desanotar(i,k);
            }
        }
    }

    private void guardarMillorSolucio() {
        for (int i = 0; i < articles.length; i++)
            millor[i] = solucio[i];
        sumaUtilitatsMillor = sumaUtilitatsActual;
        quinVolumMillor = quinVolumActual;
    }

    private boolean millor() {
        if( quinVolumActual > quinVolumMillor) return true;
        if( quinVolumActual == quinVolumMillor && sumaUtilitatsActual > sumaUtilitatsMillor) return true;
        return false;
    }

    private boolean esSolucio() {
        return quantsEssencialActual >= 2;
    }

    private boolean acceptable(int quin, int k) {
        for( int i = 0; i < k; i++){
            if( incompatibilitats[quin][solucio[i]])
                return false;
        }
        // NO sobrepassi el màxim de cada família
        // Aquí podeu fer un switch
        if( quantsFamiliaActual[articles[quin].getFamilia().ordinal()] == maxQuantsFamilia)
            return false;

        return quinVolumActual + articles[quin].getVolum() < volumMotxilla
                && quinPesActual + articles[quin].getPes() < pesMotxilla;
    }

    private void anotar(int quin, int k) {
        marcats[quin] = true;
        solucio[k] = quin;
        Article article = articles[quin];
        if (article.isEssencial())
            quantsEssencialActual++;
        sumaUtilitatsActual += article.getUtilitat();
        quinVolumActual += article.getVolum();
        quinPesActual += article.getPes();
        quantsFamiliaActual[article.getFamilia().ordinal()] ++; // Aquí podeu fer un switch
    }
    private void desanotar(int quin, int k){
        marcats[quin] = false;
        solucio[k] = -1;
        Article article = articles[quin];
        if (article.isEssencial())
            quantsEssencialActual--;
        sumaUtilitatsActual -= article.getUtilitat();
        quinVolumActual -= article.getVolum();
        quinPesActual -= article.getPes();
        quantsFamiliaActual[article.getFamilia().ordinal()] --; // Aquí podeu fer un switch
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Descripció de la Motxilla:\n");
        sb.append("\tVolum màxim: " + volumMotxilla + "\n");
        sb.append("\tPes màxim: " + pesMotxilla + "\n");
        sb.append("\tMàxim d'articles per família: " + maxQuantsFamilia + "\n\n");
        sb.append("Millor solució trobada:\n");
        sb.append("\tVolum ocupat: " + quinVolumMillor + "\n");
        sb.append("\tUtilitat total: " + sumaUtilitatsMillor + "\n");
        sb.append("\tArticles seleccionats:\n");
        for( int i = 0; i < millor.length; i++){
            if( millor[i] != -1){
                Article a = articles[millor[i]];
                sb.append("\t\tID: " + a.getIdentificador() + " Pes: " + a.getPes() + " Volum: " + a.getVolum() +
                        " Utilitat: " + a.getUtilitat() + " Essencial: " + a.isEssencial() +
                        " Família: " + a.getFamilia() + "\n");
            }
        }
        return sb.toString();
    }
}
