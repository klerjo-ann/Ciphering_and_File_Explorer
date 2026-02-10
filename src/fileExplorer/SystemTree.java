package fileExplorer;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class SystemTree extends JTree {

	public SystemTree() {
		super(new DefaultMutableTreeNode());
		setRootVisible(false);
	}

	public void initializePath(DetailedFile f) {
		setRoot(f.getRoot());
		
		setPath(f.getLineage());

		setExpandsSelectedPaths(true);
		setEditable(false);
		setDragEnabled(false);
	}

	private void setRoot(DetailedFile root) {
		DefaultMutableTreeNode superRoot = (DefaultMutableTreeNode) getModel().getRoot();
		superRoot.add(root);
		for (DetailedFile r : DetailedFile.listRoots())
			if (r.equals(root))
				superRoot.add(root);
			else
				superRoot.add(r);
	}

	private void setPath(DetailedFile[] lineage) {
		DefaultMutableTreeNode[] treeLineage = new DefaultMutableTreeNode[lineage.length + 1];
		System.arraycopy(lineage, 0, treeLineage, 1, lineage.length);
		treeLineage[0] = (DefaultMutableTreeNode) getModel().getRoot();
		
		TreePath path = new TreePath(treeLineage);
		
		expandPath(path);
		scrollPathToVisible(path);
		setSelectionPath(path);
	}

}
