package fileExplorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.ExpandVetoException;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class FileExplorer {

	private JFrame frame;
	
	private DetailedFile selection;
	private Directory currentDirectory;
	private JTextComponent targetField;
	
	private JList<DetailedFile> listView;
	private SystemTree treeView;
	
	private JTextField pathField;
	private JTextField selectionField;
	
	
	public static void launch(JTextComponent field, Directory d) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileExplorer window = new FileExplorer(field, d);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 */
	public FileExplorer(JTextComponent field, Directory d) {
		targetField = field;
		currentDirectory = d;
		selection = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Explorateur de fichiers");
		frame.setBounds(250, 250, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		treeView = new SystemTree();
		treeView.initializePath(currentDirectory);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_1.add(toolBar, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		toolBar.add(panel_3);
		FormLayout fl_panel_3 = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("40px"),
				ColumnSpec.decode("pref:grow(2)"),},
			new RowSpec[] {
				RowSpec.decode("fill:20px"),});
		panel_3.setLayout(fl_panel_3);
		
		pathField = new JTextField();
		pathField.setEditable(true);
		panel_3.add(pathField, "2, 1, fill, center");
		
		JButton btnParent = new JButton("Λ");
		btnParent.setFont(new Font("Tahoma", Font.BOLD, 8));
		panel_3.add(btnParent, "1, 1, fill, fill");
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("135px:grow"),
				ColumnSpec.decode("right:65px"),},
			new RowSpec[] {
				RowSpec.decode("25px"),}));
		
		selectionField = new JTextField();
		selectionField.setBackground(new Color(255, 255, 255));
		panel_2.add(selectionField, "1, 1, fill, center");
		selectionField.setColumns(10);
		
		JButton btnSelection = new JButton("Valider");
		btnSelection.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel_2.add(btnSelection, "2, 1, center, center");
		
		listView = new JList<>();
		listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listView.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JScrollPane listScroller = new JScrollPane(listView);
		panel_1.add(listScroller, BorderLayout.CENTER);
		
		
		
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		splitPane.setDividerSize(10);
		splitPane.setDividerLocation(250);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTree = new JScrollPane(treeView);
		treeView.scrollPathToVisible(treeView.getSelectionPath());
		panel.add(scrollPaneTree);
		
		
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, panel_1, toolBar, panel_3, pathField, btnParent, panel_2, btnSelection, selectionField, listView, scrollPaneTree, splitPane}));
	
		
		setView();
		
		
		
//		LISTENERS -----------------------
		
		
		btnParent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDirectory = currentDirectory.getParentDirectory();
				setView();
				}
		});
		
		
		
		
		pathField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					currentDirectory = new Directory(pathField.getText());
					setView();
				} catch (IllegalArgumentException e1){
					JOptionPane.showMessageDialog(panel, e1.getMessage());
				}
			}
		});
		
		
		
		
		listView.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			    if (e.getValueIsAdjusting() == false) 
				    if (listView.getSelectedIndex() != -1)
				    	setSelection(listView.getSelectedValue());
			}
		});
	
		
		MouseAdapter doubleLClickHandler = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					goToSelection();
				}
			}
		};
		
		
		listView.addMouseListener(doubleLClickHandler);
		treeView.addMouseListener(doubleLClickHandler);

		
	
		btnSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToSelection();
			}
		});
	
		
		
		treeView.addTreeWillExpandListener(new TreeWillExpandListener() {

			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				DetailedFile lastComponent = (DetailedFile) event.getPath().getLastPathComponent();
				lastComponent.establishChildren();
				currentDirectory = new Directory(lastComponent);
				setView();
			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			}
		});
		
		
		treeView.addTreeSelectionListener(e -> setSelection((DetailedFile) e.getPath().getLastPathComponent()));
	
	}

	private void setSelection(DetailedFile df) {
		selection = df;
    	selectionField.setText(selection.toString());
	}
	
	private void goToSelection() {
		boolean fileExists = false;
		
		try {
			fileExists = selection.fileExists();
		} catch (Exception e1) {}
		
		if (fileExists && !selection.equals(currentDirectory))
			
			if(!selection.isDirectory()) {
				targetField.setText(selection.getAbsolutePath());
				frame.dispose();
				}
			else {
				currentDirectory = new Directory(selection);
				setView();
			}
	}
	
	
	
	private void setView() {
		listView.setListData(currentDirectory.getChildren());
		pathField.setText(currentDirectory.getAbsolutePath());
		selectionField.setText("");
		
	}
	
}
