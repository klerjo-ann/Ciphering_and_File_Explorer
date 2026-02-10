package cryptage;

import java.io.IOException;
import java.nio.file.Path;

public abstract class Cryptage {
	final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
	protected String alphabetDeCryptage;
	protected String clef;
	
	
	private static final String MSG_CLEF_ERR = "Mot clef erroné :\nLe mot clef ne peut être composé que de lettres minuscules.";
	
	
	public Cryptage() {
		this("clef");
	}
	public Cryptage(String clef) {
		if (clef == null)
			throw new IllegalArgumentException("La clef ne peut être nulle.");
		for(int i = 0; i < clef.length(); i++)
			if(ALPHABET.indexOf(clef.charAt(i)) == -1)
				throw new IllegalArgumentException(MSG_CLEF_ERR);
		this.clef = clef;
		alphabetDeCryptage = clef + ALPHABET;
		for(int i = 0 ; alphabetDeCryptage.length() > 36; i++) {
			char c = alphabetDeCryptage.charAt(i);
			for(int j = alphabetDeCryptage.indexOf(c, i + 1); j != -1; j = alphabetDeCryptage.indexOf(c, i + 1) )
				alphabetDeCryptage = alphabetDeCryptage.substring(0, j) + alphabetDeCryptage.substring(j + 1);
		}
	}
	
	
	public String toString() {
		return "Cryptage " + this.getClass().getSimpleName() + "\nMot Clef : " + this.clef;
	}
	
	
	public abstract String cryptage(String s);
	public abstract String deCryptage(String s);
	public abstract String cryptageFichier(String s) throws IOException;
	public abstract String deCryptageFichier(String s) throws IOException;
	public abstract String cryptageFichier(String s, String s2) throws IOException;
	public abstract String deCryptageFichier(String s, String s2) throws IOException;
	
	
//--------
		

		
		
		
	protected static Path defaultResultFile(Path p) {
		Path parent = p.getParent();
		String nom = p.getFileName().toString();
		String nomSansExtension;
		String extension;
		
		try {
			nomSansExtension = nom.substring(0, nom.indexOf('.'));
			extension = nom.substring(nom.indexOf('.'));
		} catch (Exception e) {//pas d'extension
			nomSansExtension = nom;
			extension = "";
		}
			
		return parent.resolve(nomSansExtension + "_crypte" + extension);
			
	}
	
}
