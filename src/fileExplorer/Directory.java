package fileExplorer;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Iterator;

@SuppressWarnings("serial")
public class Directory extends DetailedFile {

	public Directory(String s) {
		this(new File(s));
	}

	public Directory(File f) {
		this(f.toURI());
	}

	public Directory(DetailedFile df) {
		this(df.f);
		
		parent = df.parent;
	}

	public Directory(URI uri) {
		super(uri);

		if (!f.isDirectory())
			throw new IllegalArgumentException(f + "Not a directory");

		establishChildren();
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

		children.add(df);
		return true;
	}



//	public DetailedFile[] getChildren() {
//		return children.toArray(new DetailedFile[children.size()]);
//	}
//
//
//	public int getIndex(DetailedFile child) {
//		Iterator<DetailedFile> it = children.iterator();
//		for (int i = 0; it.hasNext(); i++)
//			if (it.next().equals(child))
//				return i;
//
//		return -1;
//	}
//
//	public DetailedFile getChildAt(int index) {
//		if (index >= getChildCount())
//			return null;
//
//		return getChildren()[index];
//	}

	public static final String pwd() {
		return Path.of(".").toAbsolutePath().normalize().toString();
	}

}
