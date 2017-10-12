package Solidsnake.Model;

/**
 * Imports.
 */
import iut.Jeu;
import iut.Objet;

/**
 * Classe gérant les cases.
 * @author Dorian
 */
public class Case extends iut.ObjetTouchable implements Constantes {
    
    /**
     * Attribut contenant la valeur de l'indice horizontal x.
     */
    private int _x;
    
    /**
     * Attribut contenant la valeur de l'indice vertical y.
     */
    private int _y;

    

    /**
     * Constructeur d'une case.
     * @param g le jeu (ici inutilisé car SolidSnake ne dépend pas de jeu)
     * @param nom le nom de la case
     * @param x l'indice horizontal x
     * @param y l'indice vertical y
     */
    public Case(Jeu g, String nom, int x, int y) {
        super(g, nom, x, y);
        this._x=x;
        this._y=y;
    }

    
    
// GESTION DES COORDONNES HORIZONTALES
    /**
     * Méthode setter de l'indice horizontal x.
     * @param x la nouvelle valeur de _x
     */
    public void setIndiceX(int x){
        this._x = x;
    }

    /**
     * Fonction getter de l'indice horizontal x.
     * @return l'indice horizontal _x
     */
    public int getIndiceX(){
        return this._x;
    }
    
    /**
     * Fonction getter des coordonnées horizontales en pixels
     * @return une case en pixels
     */
    public int getX(){
        return this._x * CASE_EN_PIXELS;
    }

    
    
    
// GESTION DES COORDONNES VERTICALES
    
    /**
     * Procédure setter de l'indice vertical y
     * @param y la nouvelle valeur de y
     */
    public void setIndiceY(int y){
        this._y = y;
    }

    /**
     * Fonction getter de l'indice vertical y
     * @return l'indice horizontal _y
     */
    public int getIndiceY(){
        return this._y;
    }

    /**
     * Fonction getter des coordonnées verticales en pixels
     * @return les coordonnées verticales de la case en pixels
     */
    public int getY(){
        return this._y * CASE_EN_PIXELS;
    }

    
    
// GETTERS DE CASE_EN_PIXELS ISSU DE L'INTERFACE Constantes
    
    /**
     * Fonction getter renvoyant la constante CASE_EN_PIXELS issue de l'interface Constantes.
     * @return CASE_EN_PIXELS
     */
    public int getLargeur(){
        return CASE_EN_PIXELS;
    }
    
    /**
     * Fonction getter renvoyant la constante CASE_EN_PIXELS issue de l'interface Constantes.
     * @return CASE_EN_PIXELS
     */
    public int getHauteur(){
        return CASE_EN_PIXELS;
    }
      
    
    
    /**
     * Fonction testant si une case est contenue dans le plateau de jeu ou non.
     * @return true si la case est contenue dans le plateau de jeu.
     */
    public boolean estDansLePlateau(){
        return this._x >= 0 && this._x < NOMBRE_DE_COLONNES && this._y >= 0 && this._y < NOMBRE_DE_LIGNES;
    }
    
    /**
     *  Fonction testant l'égalité entre deux cases
     * @param obj l'objet à tester
     * @return true si les deux cases sont égales.
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Case){
            Case c = (Case) obj;
                return (this._x == c._x) && (this._y == c._y);
            }
            return false;
      }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this._x;
        hash = 19 * hash + this._y;
        return hash;
    }

    
    
    
//////////////////////////////////////////////////////////////////////////////// 
    // METHODES SURCHARGEES  appartennant à javaGame.jar et non utilisées
    
    
    @Override
    public void effetCollision(Objet objet){
        throw new UnsupportedOperationException("Pas d'effet de collision entre les cases.");
    }

    @Override
    public void evoluer(long l){
        throw new UnsupportedOperationException("Les cases n'évoluent pas.");
    }
    
}