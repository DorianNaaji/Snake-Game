package Solidsnake.Model;

/**
 * Imports.
 */
import iut.Jeu;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * La classe Pomme gérant les pommes mangées par le serpent.
 * Elle hérite de case car possède des propriétés similaires.
 * @author Dorian
 */
public class Pomme extends Case {

    /**
     * Attribut permettant de créer une cordoonée aléatoire.
     */
    private final static Random _random = new Random();

    /**
     * 
     */
    private int _angle;
    private BufferedImage _spritePomme = null;
    private File _filePomme=null;

    /**
     * Constructeur d'une pomme.
     * @param g un Jeu tel que défini par le framework.
     * @param nom un nom tel que défini par le framework.
     * @param x coordonnée horizontale.
     * @param y coordonnée vertical.
     */
    public Pomme(Jeu g, String nom, int x, int y) {
        super(g, nom, randomX(), randomY());
    }
    
    /**
     * 
     * @return une coordonnée horizontale aléatoire.
     */
    private static int randomX() {
	return _random.nextInt(NOMBRE_DE_COLONNES);
    }
    
    /**
     * 
     * @return une coordonnée verticale aléatoire.
     */
    private static int randomY() {
        return _random.nextInt(NOMBRE_DE_LIGNES);
    }
    
    /**
     * Méthode permettant de créer une nouvelle pomme à des coordonnées aléatoires. 
     */
    public void nouvellePomme() {
	setIndiceX(randomX());
	setIndiceY(randomY());
	this._angle = 0;
    }

    /**
     * 
     */
    public void calcul() {
        this._angle += 4; // incrémentation de l'angle de 4 degrés
    }

    /**
     * gestion de l'affichage graphique.
     * @param g java graphics.
     * @throws IOException Exception liée à la lecture des fichiers de ressources.
     */
    public void affichage(Graphics g) throws IOException {
        ImageObserver o=null; // un image observer dont on ne se sert pas
        this._filePomme = new File("src\\SolidSnake\\ressources\\POMME.png"); // le fichier de ressources correspondant à la pomme
        this._spritePomme = new BufferedImage(15-4,15-4, BufferedImage.TYPE_INT_ARGB); // création d'une BufferedImage
        this._spritePomme = ImageIO.read(this._filePomme); // lecture du fichier
        g.drawImage(this._spritePomme, getX()+1 , getY()-2, o); // affichage de l'image
    }

}
