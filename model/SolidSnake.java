package Solidsnake.Model;

/**
 * Imports.
 */
import iut.Jeu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * La classe SolidSnake n'hérite pas de iut.Jeu mais de JFrame. Cela nous a donné plus de possibilités même s'il a fallu configurer manuellement
 * toutes les options de la fenêtre.
 * Nous avons également optimisé toute notre phase de Conception Objet pour réaliser un programme le plus fonctionnel possible.
 * @author Dorian
 */
public class SolidSnake extends JFrame implements Constantes {

    /**
     * Le moteur du jeu
     */
    private Moteur _modele;
    
    /**
     * Un jeu tel que défini par JavaGame.jar
     */
    private Jeu g;
    
    /**
     * Conteneur de type JPanel qui contiendra le jeu
     */
    private JPanel _contenu;
    
    private boolean _fin=true;
	
    /**
    * Le constructeur de la fenêtre de jeu
    * @return la fenêtre de jeu qui sera le contenu d'une autre fenêtre.
    */
    public JPanel contenu(){
        return this._contenu;
    }
    
    
    /**
     * Constructeur de la fenêtre principale.
     */
    public SolidSnake() {
        super("SOLID SNAKE"); // Le titre de la fenêtre.
        this._modele = new Moteur(g,"Moteur,",1,1); // Création du moteur du jeu.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Fermeture de l'application lorsque la fenêtre est fermée .
        setResizable(false); // Pas de redimensionnement possible de la fenêtre.
        setFocusable(false); // S'assurer du focus pour le listener clavier.
        this._contenu = creationJPanel(this._contenu); // Gestion du conteneur.
        this.gestionJPanel(this._contenu); // Gestion du conteneur.
        setContentPane(this._contenu); // Ajout du conteneur à la fenêtre. 
        gestionThread(); // Création d'un thread infini.
    }
    
      
    /**
     * Lancement du jeu
     * @param args args
     */
    public static void main(String[] args) {
        ImageIcon logoSnake = new ImageIcon("src\\SolidSnake\\ressources\\LOGO.png");
        JOptionPane.showMessageDialog(null, "Bienvenue dans le jeu Solid Snake.\nFermez cette fenêtre pour jouer !", "SOLID SNAKE", JOptionPane.INFORMATION_MESSAGE, logoSnake);
        SolidSnake fenetre = new SolidSnake(); // création de la fenêtre
        fenetre.pack(); // dimensionnement de la fenêre "au plus juste" suivant
        fenetre.setLocationRelativeTo(null); // centrage sur l'écran
        fenetre.setVisible(true); // affichage
    }

    
    
    
    /**
     * Gestion du JPanel.
     * @param j un Jpanel qui devra être géré.
     */
    private void gestionJPanel(JPanel j){
        // le listener gérant les entrées au clavier
        this._contenu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                SolidSnake.this._modele.gestionDuClavier(e);
            }
        });    
        
        // dimension de ce conteneur 
        this._contenu.setPreferredSize(new Dimension( NOMBRE_DE_COLONNES * CASE_EN_PIXELS, NOMBRE_DE_LIGNES * CASE_EN_PIXELS));
        this._contenu.setFocusable(true);
        this._contenu.setBackground(Color.getHSBColor(55, 100, 100));
        
    }
    
    /**
     * création d'un JPanel
     * @param j un JPanel passé en paramètre
     * @return le JPanel ainsi crée et initialisé qui était passé en paramètree
     */
    private JPanel creationJPanel(JPanel j){
        j = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    SolidSnake.this._modele.affichage(g); // affichage du modèle du jeu
                } catch (IOException ex) {
                    Logger.getLogger(SolidSnake.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Erreur liée à l'affichage graphique. " + ex);
                }
            }
        };
        return j;
    }
    
    
    /**
     * Méthode permettant de gérer un thread.
     */
    private void gestionThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
                public void run() {
                while (true) { 
                    try {
                    // boucle infinie. à chaque fois que la boucle est exécutée, la
                    // méthode de calcul du jeu est appelée.
                    // Comme la boucle est infinie, la méthode de calcul
                    // sera appelée en cycle perpétuel.
                    SolidSnake.this._modele.calcul();
                    if(SolidSnake.this._modele.getLaPartieEstPerdue()){
                        if (SolidSnake.this._fin){ 
                            
                            // Gestion de l'IHM de fin de partie
                            _contenu.repaint();
                            ImageIcon logoSnake = new ImageIcon("src\\SolidSnake\\ressources\\LOGO.png");
                            JOptionPane.showMessageDialog(null, "Perdu ! Si vous souhaitez arrêter de jouer,\nfermez cette fenêtre puis fermez le jeu.", "PERDU !", JOptionPane.INFORMATION_MESSAGE, logoSnake);
                            SolidSnake.this._fin=false;
                            SolidSnake.this.setVisible(false);
                            
                            // Nouvelle fenêtre
                            SolidSnake fenetre = new SolidSnake(); // création de la fenêtre
                            fenetre.pack(); // dimensionnement de la fenêre "au plus juste" suivant
                            fenetre.setLocationRelativeTo(null); // centrage sur l'écran
                            fenetre.setVisible(true); // affichage
                            SolidSnake.this._modele.setLaPartieEstPerdue(false);
                            SolidSnake.this._fin=false;
                        }
                    }
                    }
                    
                    catch (IOException ex) {
                        Logger.getLogger(SolidSnake.class.getName()).log(Level.SEVERE, null, ex);
                        System.err.println("Erreur liée à l'affichage graphique. " + ex);
                    } 
                    _contenu.repaint(); // demander à l'EDT de redessiner le conteneur
                    try {
                        Thread.sleep(DELAY);
                    } 
                    catch (InterruptedException e) {
                        System.err.println("Exception innatendue liée au thread. " + e);
                    }
                }
            }
        });
        thread.start(); // lancement du thread
    }
}