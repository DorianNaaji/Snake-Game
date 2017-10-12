package Solidsnake.Model;

/**
 * Imports.
 */
import iut.Jeu;
import iut.Objet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 * 
 * @author Dorian
 */
public class Serpent extends iut.ObjetTouchable {

    /**
     * Une liste de case correspondant à l'espace que prend le serpent, en cases.
     */
    private LinkedList<Case> _list;
    
    /**
     * Une direction telle que définie dans l'enumeration Direction.
     * C'est la direction que prend le serpent.
     */
    private Direction _direction;
    
    /**
     * Booléen indiquant si le serpent est mort ou non.
     */
    private boolean _estMort;
    
    /**
     * Une direction telle que définie dans l'enumeration Direction.
     * C'est la direction demandée par l'utilisateur.
     */
    private Direction _demande;
    
    /**
     * Entier contenant le nombre de pommes mangées au cours d'une partie.
     */
    private int _pommesMangées;
    
    /**
     * Entier contenant le nombre de mouvements faits par le serpent.
     */
    private int _compteurDeMouvements;
    
    /**
     * Un jeu tel que défini par le framework.
     */
    private Jeu _g;
    
    /**
     * Une Image qui contiendra la ressource liée à l'affichage de la tête.
     */
    private BufferedImage _spriteTete = null;
    
    /**
     * Un fichier qui contiendra la ressource liée à l'affichage de la tête.
     */
    private File _fileTete = null;
    
    /**
     * Une Image qui contiendra une des ressources liée à l'affichage du corps.
     */
    private BufferedImage _spriteCorps = null;
    
    /**
     * Un fichier qui contiendra une des ressources liée à l'affichage du corps.
     */
    private File _fileCorps = null;

    /**
     * Constructeur d'un serpet
     * @param g un jeu tel que défini par le framework
     * @param nom un nom tel que défini par le framework
     * @param x une coordonnée horizontale
     * @param y une coordonnée vertical
     */
    public Serpent(Jeu g, String nom, int x, int y) {
        super(g, nom, x, y);
        this._list = new LinkedList<Case>(); // création de la liste

        this._list.add(new Case(g,"c1",14, 15)); // LE SERPENT
        this._list.add(new Case(g,"c2",15, 15)); // TIENT AU DEPART
        this._list.add(new Case(g,"c3",16, 15)); // SUR 3 CASES
        this._direction = Direction.GAUCHE; // le serpent a pour direction initiale la gauche
        try {
            this._fileTete = new File("src\\SolidSnake\\ressources\\TETE_GAUCHE.png");
            this._spriteTete = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
            this._spriteTete = ImageIO.read(this._fileTete);
            // Le serpent va à gauche, c'est donc le sprite "tête gauche" qui est chargé.
            
            this._fileCorps = new File("src\\SolidSnake\\ressources\\CORPS_GAUCHE.png");
            this._spriteCorps = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
            this._spriteCorps = ImageIO.read(this._fileCorps);
            // Le serpent va à gauche, c'est donc le sprite "corps gauche" qui est chargé.
        }
        catch (IOException e){ // exception liée à la lecture du fichier.
            System.err.println("EXCEPTION : Problème de lecture du fichier." + e);
        }
    }
       
    /**
     * Setter
     * @param demande la Direction demandée par l'utilisateur
     */
    public void setDemandeClavier(Direction demande) {
        this._demande = demande;
    }
    
    /**
     * Getter
     * @return la demande de direction.
     */
    public Direction getDemande(){
        return this._demande;
    }
      
    /**
     * Méthode gérant la "traduction" des entrées clavier :
     * En fonction des entrées claviers, la méthode assigne une direction.
     * @throws IOException : exception liée au chargement des sprites.
     */
    private void tourner() throws IOException {
        if (this._demande != null) { // Une touche a été pressée.
            if (this._direction == Direction.HAUT || this._direction == Direction.BAS) { // Le serpent va vers le haut ou vers le bas.
                if (this._demande == Direction.DROITE) { // la touche droite a été pressée : le serpent tourne à droite
                    this._direction = Direction.DROITE;
                    try{
                        this._fileTete = new File("src\\SolidSnake\\ressources\\TETE_DROITE.png");
                        this._spriteTete = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteTete = ImageIO.read(this._fileTete);
                        // Chargement d'une ressource spécifique lorsque le serpent va à droite.
                        
                        this._fileCorps = new File("src\\SolidSnake\\ressources\\CORPS_DROITE.png");
                        this._spriteCorps = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteCorps = ImageIO.read(this._fileCorps);
                        // Chargement d'une ressource spécifique lorsque le serpent va à droite.
                    }
                    catch(IOException e){
                        System.err.println("Erreur Sprites " + e);
                    }
                } 
                else if (this._demande == Direction.GAUCHE) { // La touche gauche a été pressée : le serpent tourne à gauche.
                    this._direction = Direction.GAUCHE;
                    try{
                        this._fileTete = new File("src\\SolidSnake\\ressources\\TETE_GAUCHE.png");
                        this._spriteTete = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteTete = ImageIO.read(this._fileTete);
                        // Chargement d'une ressource spécifique lorsque le serpent va à gauche.
                        
                        this._fileCorps = new File("src\\SolidSnake\\ressources\\CORPS_GAUCHE.png");
                        this._spriteCorps = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteCorps = ImageIO.read(this._fileCorps);
                        // Chargement d'une ressource spécifique lorsque le serpent va à droite.
                    }
                    catch(IOException e){
                        System.err.println("Erreur Sprites " + e);
                    }
                    
                }
            }
            else { // Le serpent va vers la droite ou vers la gauche.
                if (this._demande == Direction.HAUT) { // La touche haut a été pressée : le serpent va vers le haut.
                    this._direction = Direction.HAUT;
                    try{
                        this._fileTete = new File("src\\SolidSnake\\ressources\\TETE_HAUT.png");
                        this._spriteTete = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteTete = ImageIO.read(this._fileTete);
                        // Chargement d'une ressource spécifique lorsque le serpent va en haut.
                        this._fileCorps = new File("src\\SolidSnake\\ressources\\CORPS_HAUT.png");
                        this._spriteCorps = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteCorps = ImageIO.read(this._fileCorps);
                        // Chargement d'une ressource spécifique lorsque le serpent va en haut.
                    }
                    catch(IOException e){
                        System.err.println("Erreur Sprites " + e);
                    }                   
                } 
                else if (this._demande == Direction.BAS) { // la touche bas : a été pressée : le serpent tourne vers le bas
                    this._direction = Direction.BAS;
                    try{
                        this._fileTete = new File("src\\SolidSnake\\ressources\\TETE_BAS.png");
                        this._spriteTete = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteTete = ImageIO.read(this._fileTete); 
                        // Chargement d'une ressource spécifique lorsque le serpent va en bas.
                        this._fileCorps = new File("src\\SolidSnake\\ressources\\CORPS_BAS.png");
                        this._spriteCorps = new BufferedImage(this._list.getFirst().getHauteur(),this._list.getFirst().getLargeur(), BufferedImage.TYPE_INT_ARGB);
                        this._spriteCorps = ImageIO.read(this._fileCorps);
                        // Chargement d'une ressource spécifique lorsque le serpent va en bas.
                    }
                    catch(IOException e){
                        System.err.println("Erreur Sprites " + e);
                    }      
                }
            }
            // La direction demandée par le joueur a été prise en compte,
            // la variable demande prend donc la valeur null pour que l'utilisateur
            // puisse de nouveau diriger le serpent.
            this._demande = null;
        }
    }
      
    /**
     * Méthode gérant l'avancement du serpent
     */
    private void avance() {
        this._list.addFirst(Nextcase()); // Ajoute en tête de _liste la case sur laquelle le serpent doit se déplacer
        this._list.removeLast(); // supprime le dernier élément de la _liste
    }
    
    /**
     * 
     * @return la case devant la tête du serpent.
     */
    private Case Nextcase() {
        Case tete = this._list.getFirst();
        switch (this._direction) {
            case HAUT:
                return new Case(this._g,"haut",tete.getIndiceX(), tete.getIndiceY() - 1);
            case DROITE:
                return new Case(this._g,"droite",tete.getIndiceX() + 1, tete.getIndiceY());
            case BAS:
                return new Case(this._g,"bas",tete.getIndiceX(), tete.getIndiceY() + 1);
            case GAUCHE:
                return new Case(this._g,"gauche",tete.getIndiceX() - 1, tete.getIndiceY());
        }
        return null;
    }
      
    /**
     * Méthode permettant de savoir si le serpent peut avancer ou non.
     * @return false si le serpent ne peut pas avancer. true sinon.
     */
    private boolean peutAvancer() {
        Case nextCase = Nextcase();
        return nextCase.estDansLePlateau() && !this._list.contains(nextCase); // vérification
    }
      
    /**
     * Getter
     * @return l'attribut _estMort
     */
    public boolean getEstMort() {
        return this._estMort;
    }
     
    /**
     * Méthode vérifiant si le serpent peut ou non manger la pomme sur la carte.
     * @param pomme une pomme
     * @return true si le serpent peut manger la pomme.
     */
    private boolean peutManger(Pomme pomme) {
        return pomme.equals(Nextcase());
    }
      
    /**
     * Méthode permettant au serpent de manger une pomme.
     */
    private void mange() {
        this._list.addFirst(Nextcase()); // ajoute en tête de _liste la case sur laquelle le serpent doit se déplacer.
        this._pommesMangées++; // incrémentation du nombre de pommes mangées.
    }
      
    /**
     * Getter.
     * @return le nombre de pommes mangées.
     */
    public int getPommesMangées() {
        return this._pommesMangées;
    }
      
    /**
     * Gestion du niveau de jeu.
     * @param niveau un entier
     * @return 
     */
    private int seuilNiveau(int niveau) {
        switch (niveau) {
            case 1:
                return 20;
            case 2:
                return 16;
            case 3:
                return 14;
            case 4:
                return 12;
            case 5:
                return 10;
            case 6:
                return 8;
            case 7:
                return 6;
            case 8:
                return 4;
            case 9:
                return 3;
            default :
                return 2;
          }
    }
    
    /**
     * 
     * @param pomme une pomme
     * @param niveau le niveau actuel
     * @throws IOException  exception liée à la lecture des fichiers
     */
    public void calcul(Pomme pomme, int niveau) throws IOException {
        this._compteurDeMouvements++; // Le compteur de mouvements est incrémenté.
        if (this._compteurDeMouvements >= seuilNiveau(niveau)) { // Condition vérifiant qu'il est bien temps d'animer le serpent
            this._compteurDeMouvements = 0; // Le compteur est remis à 0.
            try{
                tourner(); // Appel de tourner.
            } 
            catch(IOException e){
                System.err.println("Erreur sprites " + e);
            }
            if (peutManger(pomme)) { // Cas où le joueur mange une pomme.
                mange();
                pomme.nouvellePomme(); // Apparition d'une nouvelle pomme.
            } 
            else if (peutAvancer()) { // Cas où le joueur avance.
                avance();
            } 
            else { // Dernier cas : la partie est perdue car le serpent a atteint les limites du jeu ou il s'est mangé.
                this._estMort = true;
            }
        }
    }
    
      
    /**
     * Procédure permettant d'afficher le serpent, dont le corps est contenu dans une liste de case.
     * @param g java Graphics
     * @throws IOException exceptiong graphique
     */
    public void affichage(Graphics g) throws IOException {
        ImageObserver o=null;
        try{ 
            for (int i=1;i<this._list.size();i++) {
                g.setColor(Color.GREEN);
                g.drawImage(this._spriteTete, this._list.getFirst().getX(), this._list.getFirst().getY(), o);
                g.drawImage(_spriteCorps, _list.get(i).getX(), _list.get(i).getY(), o);
            }
        }
        catch(ConcurrentModificationException e){
            System.err.println("EXCEPTION : Problème graphique");
        }
    }
    
    
    
//////////////////////////////////////////////////////////////////////////////// 
    // METHODES SURCHARGEES  appartennant à javaGame.jar et non utilisées
    
    
    
    /**
     * Procédure non utilisée
     * @param objet  un objet dont on veut tester la collision
     */
    @Override
    public void effetCollision(Objet objet) {
        throw new UnsupportedOperationException("Les collisions sont gérées dans une autre méthode.");
    }
    /**
     * Procédure non utilisée
     * @param l //
     */
    @Override
    public void evoluer(long l) {
        throw new UnsupportedOperationException("L'évolution est gérée dans une autre méthode.");
    }


}
