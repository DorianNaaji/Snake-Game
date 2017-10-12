package Solidsnake.Model;

/**
 * Imports.
 */
import iut.Jeu;
import iut.Objet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * La classe moteur gère les éléments "techniques" du jeu :
 * la gestion clavier, calcul de la boucle de jeu et affichage graphique.
 * @author Dorian
 */
public class Moteur extends iut.Objet {

    /**
     * Un attribut de type Serpent contenant le serpent de la partie.
     */
    private Serpent _serpent;
    
    /**
     * Un booléen indiquant si la partie est perdue ou non.
     */
    private boolean _laPartieEstPerdue;
    
    /**
     * Un attribut de type Pomme contenant la pomme mangeable dans la zone de jeu.
     */
    private Pomme _pomme;
    

   

    /**
     * Constructeur du moteur du jeu.
     * @param g le Jeu tel que défini par le framework.
     * @param nom le nom tel que défini par le framework.
     * @param _x coordonnée horizontale.
     * @param _y coordonnée verticale.
     */
    
    public Moteur(Jeu g, String nom, double _x, double _y) {
        super(g, nom, _x, _y);
        this._serpent = new Serpent(g, nom, (int)_x, (int) _y);
        this._pomme = new Pomme(g, nom, (int)_x, (int) _y);
    }

    
    /**
     * La gestion du clavier à l'aide des constantes java.
     * @param event un Java KeyEvent.
     */
    public void gestionDuClavier(KeyEvent event) {
	if (event.getKeyCode() == KeyEvent.VK_RIGHT) { // Touche flèche droite.
            this._serpent.setDemandeClavier(Direction.DROITE);
        } 
        else if (event.getKeyCode() == KeyEvent.VK_LEFT) { // Touche flèche gauche.
            this._serpent.setDemandeClavier(Direction.GAUCHE);
        } 
        else if (event.getKeyCode() == KeyEvent.VK_UP) { // Touche flèche haut.
            this._serpent.setDemandeClavier(Direction.HAUT);
        } 
        else if (event.getKeyCode() == KeyEvent.VK_DOWN) { // Touche flèche bas.
            this._serpent.setDemandeClavier(Direction.BAS);
        }
    }

    /**
     * Calcul du niveau.
     * @return le niveau du jeu.
     */
    private int Niveau(){
	return (this._serpent.getPommesMangées() / 5) + 1;
    }
    
    /**
     * le booléen indiquant si la partie est perdu ou non.
     * @return vrai si la partie est perdue
     */
    public boolean getLaPartieEstPerdue(){
        return this._laPartieEstPerdue;
    }
    
    /**
     * Setter de l'attribut laPartiEstPerdue
     * @param b le nouvel état
     */
    public void setLaPartieEstPerdue(boolean b){
        this._laPartieEstPerdue=b;
    }
	
    /**
     * Calcul de la boucle de jeu
     * @throws IOException Exception liée à l'affichage graphique (pb de lecture de fichier)
     */
    public void calcul() throws IOException{
        if (!this._laPartieEstPerdue){ // Si la partie n'est pas perdue,
            this._pomme.calcul(); // calcul de la pomme,
            try{
                this._serpent.calcul(this._pomme, Niveau());// calcul du serpent.
            } 
            catch(IOException e){
                System.err.println("Erreur graphie " + e);
            }
            if (this._serpent.getEstMort()){ // Si le serpent est mort car celui-ci a atteint les limites du plateau de jeu,
                this._laPartieEstPerdue = true; // la partie est perdue.
            }
        }
    }
      
    /**
     * Le dessin graphique du jeu : gestion de l'affichage du serpent, du niveau et du
     * texte si le jouer a perdu.
     * @param g java Graphics
     * @throws IOException Exception liée à l'affichage graphique (pb de lecture de fichier)
     */
    public void affichage(Graphics g) throws IOException{
        try{
            this._serpent.affichage(g); // Affichage du serpent.
            this._pomme.affichage(g); // Affichage de la pomme.
        } 
        catch (IOException ex) { // Exception.
            Logger.getLogger(Moteur.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Exception innatendue : " + ex);
        }
        if (this._laPartieEstPerdue) { // L'utilisateur a perdu la partie
            String str = "GAME OVER"; // affichage
            g.setColor(Color.RED); // couleur du texte
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 75)); // police du texte
            FontMetrics fm = g.getFontMetrics();
            int x = (g.getClipBounds().width - fm.stringWidth(str)) / 2;
            int y = (g.getClipBounds().height / 2) + fm.getMaxDescent();
            g.drawString(str, x, y);
        } 
        g.setColor(Color.RED); // Affichage du niveau.
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20)); 
        g.drawString(String.valueOf(Niveau()), 5, 25); 
    }
    

    
    public boolean equals(Moteur m){
        if (this._laPartieEstPerdue == m._laPartieEstPerdue){
            if(this._pomme.estDansLePlateau()== m._pomme.estDansLePlateau()){
                if(this._serpent.getEstMort()==m._serpent.getEstMort()){
                    if(this.Niveau()==m.Niveau()){
                        if(this._serpent.getPommesMangées()==m._serpent.getPommesMangées()){
                            return true;
                        }
                    }
                }
            }
        }
       return false;
    }

    
    
    
    
//////////////////////////////////////////////////////////////////////////////// 
    // METHODES SURCHARGEES  appartennant à javaGame.jar et non utilisées 
    

    
    @Override
    public boolean testerCollision(Objet objet) {
        throw new UnsupportedOperationException("Le moteur du jeu n'a pas de collision avec d'autres objets");
    }

    @Override
    public void effetCollision(Objet objet) {
        throw new UnsupportedOperationException("Le moteur du jeu n'a pas de collision avec d'autres objets");
    }

    @Override
    public void evoluer(long l) {
        throw new UnsupportedOperationException("L'évolution du moteur du jeu est gérée dans calcul et affichage");
    }
      
}