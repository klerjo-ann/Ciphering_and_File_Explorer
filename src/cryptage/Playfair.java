package cryptage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

import fileExplorer.DetailedFile;

public class Playfair extends Cryptage{
	private static final int CRYPT = 0, DECRYPT = 1;
	
	
	public Playfair() {
		this("clef");
	}
	
	public Playfair(String clef) {
		super(clef);
	}
	
	
	
	public String cryptage(String s) {
		return transformation(CRYPT, s);
	}
	
	public String deCryptage(String s) {
		return transformation(DECRYPT, s);
	}
	
	
	
	public String cryptageFichier(String chemin) throws IOException {
		Path p1 = DetailedFile.absolute(chemin);
		Path p2 = defaultResultFile(p1);
		
		permuterFichier(p1.toFile(), p2.toFile(), CRYPT);
		
		return p2.toString();
	}
	
	
	public String deCryptageFichier(String chemin) throws IOException {
		Path p1 = DetailedFile.absolute(chemin);
		Path p2 = defaultResultFile(p1);
		
		permuterFichier(p1.toFile(), p2.toFile(), DECRYPT);
		
		return DetailedFile.relativeToHome(p2);
	}
	
	
	public String cryptageFichier(String cheminF1, String cheminF2) throws IOException {
		Path p1 = DetailedFile.absolute(cheminF1);
		Path p2 = DetailedFile.absolute(cheminF2);
		
		permuterFichier(p1.toFile(), p2.toFile(), CRYPT);
		
		return DetailedFile.relativeToHome(p2);
	}
	
	
	public String deCryptageFichier(String cheminF1, String cheminF2) throws IOException {
		Path p1 = DetailedFile.absolute(cheminF1);
		Path p2 = DetailedFile.absolute(cheminF2);
		
		permuterFichier(p1.toFile(), p2.toFile(), DECRYPT);
		
		return DetailedFile.relativeToHome(p2);
	}
	
	
	
//----------
	
	private void permuterFichier(File f1, File f2,  int mod) throws IOException {
		if(f1.equals(f2))
			throw new IllegalArgumentException("Le fichier de sortie doit être différent du fichier d'entrée.");
		
		char[] doublet = new char[2];
		
		Reader input;
		input = new BufferedReader(
						new FileReader(f1), 10000
					);
		
		Writer output;
		output = new BufferedWriter(
					new FileWriter(f2), 10000
				);
				
		
		for (int i = input.read(doublet); i != - 1; i = input.read(doublet)) {
			if(i == 1)
				doublet[1] = ' ';
			transformerDoublet(mod, doublet);
			output.write(doublet, 0, i);
		}
		
		output.close();
		input.close();
	}
	
//----------
	
	private String transformation(int mod, String s) {
		int nbDoublets = (s.length() + 1) / 2; //nb max de doublets (le dernier char d'une string de longueur impaire est le premier char d'un doublet {c, ' '})
		StringBuffer res = new StringBuffer(s.length() + 1);
		
		for(int i = 0; i < nbDoublets; i++) {
			char[] doublet = iemeDoublet(s, i);
			transformerDoublet(mod, doublet);
			res.append(doublet[0]).append(doublet[1]);
		}
		
		if(res.length() > s.length())
			res = res.deleteCharAt(s.length()); /*substring(0, s.length());*/ //retrait de l'espace qui a été rajouté.
		
		return new String(res);
	}
	
	
	
	private static char[] iemeDoublet(String s, int i) {
		if(i < 0 || i > s.length())
			throw new StringIndexOutOfBoundsException();
		
		char[] res = new char[2];
		res[0] = s.charAt(i * 2);
		
		try {
			res[1] = s.charAt(i * 2 + 1);
		} catch (StringIndexOutOfBoundsException e) {// i*2+1 == s.length() (il y avait un nombre impaire de charactères)
			res[1] = ' ';
		}
		
		return res;
	}
	
	
	
	private void transformerDoublet(int mod, char[] doublet) {
		int indexC1 = alphabetDeCryptage.indexOf(doublet[0]); //-1 si absent
		int indexC2 = alphabetDeCryptage.indexOf(doublet[1]); //-1 si absent
		
		try { 
			Coordonnees6x6 coordC1 = new Coordonnees6x6(indexC1);
			Coordonnees6x6 coordC2 = new Coordonnees6x6(indexC2);
			transformerCoordonnees(mod, coordC1, coordC2);
			doublet[0] = alphabetDeCryptage.charAt(coordC1.index());
			doublet[1] = alphabetDeCryptage.charAt(coordC2.index());
			
		} catch (IndexOutOfBoundsException e) { //indices[i] = -1 (i.e. char absent de l'alphabet de cryptage)
			//on ne change rien
		}
	}
	
	
	
	private void transformerCoordonnees(int modalite, Coordonnees6x6 a, Coordonnees6x6 b) {
		if (a.getI() == b.getI()) {	//même ligne
			
			if (modalite == CRYPT){
				a.incrCol();
				b.incrCol(); 	//suivants sur la ligne
			} else 
				if (modalite == DECRYPT){
					a.decrCol();
					b.decrCol();//précédents sur la ligne
				}
			
		} else if(a.getJ() == b.getJ()) { //même colonne
			
			if (modalite == CRYPT){
				a.incrLig();
				b.incrLig();	//suivants sur la colonne
			} else
				if (modalite == DECRYPT){
					a.decrLig();
					b.decrLig();//précédents sur la ligne
				}
			
		} else echangerCol(a, b);
	}
	
	
	
	private void echangerCol(Coordonnees6x6 a, Coordonnees6x6 b) {
		int temp = a.getJ();
		a.setJ(b.getJ()); b.setJ(temp);
	}
	
	
//----------
	
	private String separateur(char c, char d) {
		String res = "";
		for(int i = 0; i < 6 * 4 + 1; i ++)
			if (i % 4 == 0 && i != 0 && i != 6*4)
				res += c;
			else if (i == 0 || i == 6*4)
				res += d;
			else res += '-';
		return res;
	}
	
	
	
	private String toStringLigneMatrice(int n) {
		String res = "\n\t\t\t|";
		n *= 6;
		String lig = alphabetDeCryptage.substring(n, n + 6);
		
		for(int i = 0; i < lig.length(); i++)
			res += " " + lig.charAt(i) + " |";
		
		return res;
	}
	
	
	
	private String toStringMatriceDeCryptage() {
		String res = separateur('-', '-');
		
		int i;
		for ( i = 0; i < (alphabetDeCryptage.length() / 6) - 1; i++)
			res += toStringLigneMatrice(i) + "\n\t\t\t" + separateur('+', '-');
		
		res += toStringLigneMatrice(i) + "\n\t\t\t" + separateur('-', '-');
		return res;
	}
	
	
	
	public String toString() {
		return super.toString() + "\nMatrice de cryptage :\t" + toStringMatriceDeCryptage();
	}
	

}

//----------

class Coordonnees6x6 {
	private int i;
	private int j;
	public Coordonnees6x6() {
		this(0);
	}
	public Coordonnees6x6(int z) {
		if(z < 0 || z >= 36)
			throw new IndexOutOfBoundsException();
		i = z / 6; j = z % 6;
	}
	public int getI() {return i;}
	public int getJ() {return j;}
	public int index() {return i * 6 + j;}
	public void setI(int x) {i = x;}
	public void setJ(int y) {j = y;}
	public void incrCol() {j ++; j %= 6;}
	public void incrLig() {i ++; i %= 6;}
	public void decrCol() {j += 6; j --; j %= 6;}
	public void decrLig() {i += 6; i --; i %= 6;}
}