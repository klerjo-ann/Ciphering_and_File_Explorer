package fileExplorer;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class DetailedFile extends DefaultMutableTreeNode implements Cloneable, Comparable<DetailedFile> {

	protected File f;
	private String name;
	private String type;

	protected DetailedFile parent;
	protected Vector<DetailedFile> children;

	public DetailedFile(URI uri) {
		this(new File(uri));
	}

	public DetailedFile(File file) {
		f = file;
		if (!f.exists())
			throw new IllegalArgumentException(f + "Not a file");

		name = f.getName();
		type = f.isDirectory() ? System.getProperty("file.separator") : "";

		parent = findParent();

		allowsChildren = f.isDirectory();

		children = new Vector<DetailedFile>();
	}

	public DetailedFile(DetailedFile df) {
		this(df.f);
	}

	public boolean add(DetailedFile df) {
		if (children.isEmpty()) {
			children.add(df);
			return true;
		}

		Iterator<DetailedFile> it = children.iterator();
		DetailedFile f;

		for (f = it.next(); it.hasNext(); f = it.next())
			if (f.equals(df))
				return false;

		super.add(df);
		return true;
	}

	protected void establishChildren() {
		File[] temp = f.listFiles();
		for (File f : temp) {
			DetailedFile child = new DetailedFile(f);
			if (children.indexOf(child) == -1)
				add(child);
		}
		children.sort(null);
	}

	public DetailedFile[] getChildren() {
		return children.toArray(new DetailedFile[children.size()]);
	}

	public int compareTo(DetailedFile that) {
		if (isDirectory() && that.isDirectory() || !isDirectory() && !that.isDirectory())
			return f.compareTo(that.f);
		else if (f.isDirectory()) // that is not a directory
			return f.compareTo(that.f) - 1000000;
		else // that is a directory but this is not
			return f.compareTo(that.f) + 1000000;
	}

	public boolean equals(DetailedFile that) {
		return compareTo(that) == 0;
	}

	public String toString() {
		if (name.length() == 0)
			return f.getAbsolutePath();
		return name + type;
	}

	public static String relativeToHome(Path r) {
		Path p = r.toAbsolutePath().normalize();
		String res;

		try {
			Path home = Path.of(System.getProperty("user.home")).toAbsolutePath().normalize();
			res = "~/" + home.relativize(p).toString();
		} catch (IllegalArgumentException e) {
			res = p.toString();
		}
		return res;
	}

	public static Path absolute(String s) {
		Path f = Path.of(s);
		if (!f.isAbsolute())
			f = f.toAbsolutePath();
		return f;
	}

	public static DetailedFile[] listRoots() {
		File[] fs = File.listRoots();
		DetailedFile[] res = new DetailedFile[fs.length];

		for (int i = 0; i < res.length; i++)
			res[i] = new DetailedFile(fs[i]);

		return res;
	}

	public File getFile() {
		return f;
	}

	public URI toURI() {
		return f.toURI();
	}

	public boolean fileExists() {
		return f.exists();
	}

	public boolean isDirectory() {
		return f.isDirectory();
	}

	public String getAbsolutePath() {
		return f.getAbsolutePath();
	}

	public int getChildCount() {
		return children.size();
	}

	public DetailedFile getChildAt(int index) {
		return children.get(index);
	}

	public int getIndex(DetailedFile child) {
		return children.indexOf(child);
	}

	public boolean hasParent() {
		return parent != null;
	}

	public DetailedFile getParent() {
		return parent;
	}

	private DetailedFile findParent() {
		if (f.getParent() == null)
			return null;

		DetailedFile d = new DetailedFile(f.getParentFile());
		d.add(this);
		return d;
	}

	public Directory getParentDirectory() {
		if (parent == null)
			return new Directory(this);

		return new Directory(getParent());
	}

	public boolean isLeaf() {
		return !isDirectory();
	}

	public DetailedFile getRoot() {
		if (hasParent()) {
			DetailedFile parent;

			for (parent = getParent(); parent.hasParent(); parent = parent.getParent())
				;
			return parent;
		}
		return this;
	}

	public DetailedFile[] getLineage() { // returns an array containing this.getRoot() as the first element and
											// this.getParent() as the last element.
		LinkedList<DetailedFile> lineage = new LinkedList<DetailedFile>();

		for (DetailedFile df = this; df.hasParent(); df = df.getParent())
			lineage.add(0, df);

		DetailedFile[] res = new DetailedFile[lineage.size() + 1];
		lineage.toArray(res);
		System.arraycopy(res, 0, res, 1, lineage.size());
		res[0] = getRoot();

		return res;
	}

}
