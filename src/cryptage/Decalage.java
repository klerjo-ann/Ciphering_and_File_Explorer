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

public class Decalage extends Cryptage{

	public Decalage() {
		this("clef");
	}
	public Decalage(String clef) {
		super(clef);
		int decalage = clef.length();
		int indiceSeparation = alphabetDeCryptage.length() - decalage;
		String fin = alphabetDeCryptage.substring(indiceSeparation);
		String debut = alphabetDeCryptage.substring(0, indiceSeparation);
		alphabetDeCryptage = fin + debut;
	}
	
	
	
	public String cryptage(String s) {
		return permutation(s, ALPHABET, alphabetDeCryptage);
	}
	public String deCryptage(String s) {
		return permutation(s, alphabetDeCryptage, ALPHABET);
	}
	
	
	
	
	public String cryptageFichier(String chemin) throws IOException {
		Path p1 = DetailedFile.absolute(chemin);
		Path p2 = defaultResultFile(p1);
		
		permuterFichier(p1.toFile(), p2.toFile(), ALPHABET, alphabetDeCryptage);
		
		return p2.toString();
	}
	
	public String deCryptageFichier(String chemin) throws IOException {
		Path p1 = DetailedFile.absolute(chemin);
		Path p2 = defaultResultFile(p1);
		
		permuterFichier(p1.toFile(), p2.toFile(), alphabetDeCryptage, ALPHABET);
		
		return DetailedFile.relativeToHome(p2);
	}
	
	
	public String cryptageFichier(String cheminF1, String cheminF2) throws IOException {
		Path p1 = DetailedFile.absolute(cheminF1);
		Path p2 = DetailedFile.absolute(cheminF2);
		
		permuterFichier(p1.toFile(), p2.toFile(), ALPHABET, alphabetDeCryptage);
		
		return DetailedFile.relativeToHome(p2);
	}
	
	public String deCryptageFichier(String cheminF1, String cheminF2) throws IOException {
		Path p1 = DetailedFile.absolute(cheminF1);
		Path p2 = DetailedFile.absolute(cheminF2);
		
		permuterFichier(p1.toFile(), p2.toFile(), alphabetDeCryptage, ALPHABET);
		
		return DetailedFile.relativeToHome(p2);
	}
	
	
//---------
	
	
	private static String permutation(String s, String alpha1, String alpha2) {
		StringBuffer res = new StringBuffer(s.length());
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			char nouveauC = ' ';
				try {
					int indexChar = alpha1.indexOf(s.charAt(i), 0);
					nouveauC = alpha2.charAt(indexChar);
				} catch (Exception e) {
					nouveauC = c;
				}
			res.append(nouveauC);
		}
		return new String(res);
	}
	
	
	private static char permutation(char c, String alpha1, String alpha2) {
		try {
			int indexChar = alpha1.indexOf(c, 0);
			return alpha2.charAt(indexChar);
		} catch (Exception e) {
			return c;
		}
	}
	
	
//--------
	
	
	private static void permuterFichier(File f1, File f2, String alpha1, String alpha2) throws IOException {
		if(f1.equals(f2))
			throw new IllegalArgumentException("Le fichier de sortie doit être différent du fichier d'entrée.");
		
		Reader entree;
		entree = new BufferedReader(
				new FileReader(f1), 10000
				);
		
		Writer sortie;
		sortie = new BufferedWriter(
				new FileWriter(f2), 10000
				);
		
		for (int i = entree.read(); i != - 1; i = entree.read())
			sortie.append(permutation((char) i, alpha1, alpha2));
		
		entree.close();
		sortie.close();
	}
	
	
//--------
	
	
	public String toString() {
		return super.toString() + "\nTransformation de cryptage :\t" + ALPHABET + "\n\t\t\t\t" + alphabetDeCryptage;
	}
	
	


}
