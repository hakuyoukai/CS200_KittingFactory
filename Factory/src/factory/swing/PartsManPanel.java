package factory.swing;


import factory.Part;
import factory.managers.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


/*
* This class is the GUI for the Parts Manaager. This will be
* instantiated in the general PartsManager class (which extends JFrame).
* Written by : Marc Mendiola
* Last edited : 11/12/12 11:59 PM
*/



public class PartsManPanel extends JPanel{

	// Data
	JTabbedPane tabbedPane;
	AddPanel addPanel;
	RemovePanel removePanel;
	ArrayList<String> fileNames;
	PartsManager partsManager;
	ArrayList<CurrentItem> partsList;
	JScrollPane currentList;
	JPanel currentListPanel;
	//New Part

	// Methods

	public PartsManPanel(PartsManager p){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		partsManager = p;
		fileNames = new ArrayList<String>();
		fileNames.add("eye");
		fileNames.add("body");
		fileNames.add("hat");
		fileNames.add("arm");
		fileNames.add("shoe");
		fileNames.add("mouth");
		fileNames.add("nose");
		fileNames.add("moustache");
		fileNames.add("ear");
		partsList = new ArrayList<CurrentItem>();
		addPanel = new AddPanel();
		removePanel = new RemovePanel();
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("addPanel", addPanel);
		tabbedPane.addTab("removePanel", removePanel);
		currentListPanel = new JPanel();
		currentListPanel.setLayout(new BoxLayout(currentListPanel, BoxLayout.Y_AXIS));
		currentList = new JScrollPane(currentListPanel);
		this.add(tabbedPane);
		this.add(currentList);
		
	}
	
	public void setManager(PartsManager manager){
		partsManager = manager;
	}



	// Internal Classes

	private class AddPanel extends JPanel implements ActionListener{

		// Data
		JLabel title;
		JLabel label1;
		JLabel label2;
		JLabel previewFrame;
		JTextField itemName;
		JComboBox imageSelection;
		JButton saveItem;
		

		//Methods
		
		public AddPanel(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
				
			
			title = new JLabel ("Parts Manager");
			title.setFont(new Font("Serif", Font.BOLD, 16));
			label1 = new JLabel ("New Item : ");
			itemName = new JTextField(8);
			label2 = new JLabel ("Image : ");
			imageSelection = new JComboBox();
			for(int i = 0; i < fileNames.size(); i++){
				imageSelection.addItem(fileNames.get(i));
			}
			
			imageSelection.addActionListener(this);
			previewFrame = new JLabel();
			ImageIcon imagePreview = new ImageIcon("Images/" + fileNames.get(0) + ".png");
			previewFrame.setIcon(imagePreview);
			
			saveItem = new JButton ("Save Item");
			saveItem.addActionListener(this);
			
			partsList = new ArrayList<CurrentItem>();
			currentList = new JScrollPane();
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.insets = new Insets(0,0,20,0);
			this.add(title, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			this.add(label1, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(itemName, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(label2, c);
			c.gridx = 1;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(imageSelection, c);
			c.gridx = 2;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(previewFrame, c);
			c.gridx = 0;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(saveItem, c);

		}
		
		public void updatePicture(String item){
			ImageIcon imagePreview = new ImageIcon("Images/" + item + ".png");
			previewFrame.setIcon(imagePreview);
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			 if (ae.getSource() == saveItem){
				 boolean nameTaken = false;
				 /*for(int i = 0; i < partsManager.getParts().size(); i++){
					 if(itemName.getText().equals(partsManager.getParts().get(i)))
						 nameTaken = true;
				 }*/
				 if(nameTaken){
					 itemName.setText("Name taken.");
				 }else{
					 System.out.println("I will create a new part and send it to the server.");
					 
					 partsList.add(new CurrentItem(new JLabel(itemName.getText()), new JLabel(), (String)imageSelection.getSelectedItem()));
					 partsList.get(partsList.size() - 1).addCurrentItem(currentListPanel);
					 System.out.println("parts size : " + partsManager.parts.size());
					 Part p = new Part(itemName.getText(), 2, "This is a test.","Images/" + (String)imageSelection.getSelectedItem() + ".png", 2);
					 
					 //partsManager.parts.put(p.name, p);
					 //partsManager.sendMessage("add", partsManager.getParts().get((String)imageSelection.getSelectedItem()));
				 }
		     }else{
			 JComboBox cb = (JComboBox)ae.getSource();
		     String petName = (String)cb.getSelectedItem();
		     updatePicture(petName);
		     }

		}
		
		
	}

	private class RemovePanel extends JPanel implements ActionListener{
		// Data

				JLabel title;
				JLabel label1;
				JLabel previewFrame;
				JComboBox imageSelection;
				JButton removeItem;

				//Methods

				public RemovePanel(){
					this.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
						
					
					title = new JLabel ("Parts Manager");
					title.setFont(new Font("Serif", Font.BOLD, 16));
					label1 = new JLabel ("Item : ");
					imageSelection = new JComboBox();
					/*Iterator it = partsManager.getParts().entrySet().iterator();
					
					while(it.hasNext()){
						Map.Entry keyPair = (Map.Entry)it.next();
						imageSelection.addItem(keyPair.getValue().imagePath);
						it.remove();
					}*/
					for ( Part p : partsManager.getParts().values()){
						imageSelection.addItem(p.name);
						System.out.println(p.name);
					}
					imageSelection.addActionListener(this);
					previewFrame = new JLabel();
					System.out.println("Hey " + (String)imageSelection.getItemAt(0));
					//ImageIcon imagePreview = new ImageIcon((String)imageSelection.getItemAt(0));
					//previewFrame.setIcon(imagePreview);
					
					removeItem = new JButton ("Remove Item");
					removeItem.addActionListener(this);
			
					c.gridx = 0;
					c.gridy = 0;
					c.gridwidth = 2;
					c.fill = GridBagConstraints.VERTICAL;
					c.insets = new Insets(0,0,20,0);
					this.add(title, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridwidth = 1;
					c.gridx = 0;
					c.gridy = 1;
					this.add(label1, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 1;
					c.gridy = 1;
					this.add(imageSelection, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 2;
					c.gridy = 1;
					this.add(previewFrame, c);
					c.gridx = 0;
					c.gridy = 2;
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(removeItem, c);

				}
				
				public void updatePicture(Part p){
					ImageIcon imagePreview = new ImageIcon(p.imagePath);
					previewFrame.setIcon(imagePreview);
				}

				public void actionPerformed(ActionEvent ae) {
					 
				     if (ae.getSource() == removeItem){
				    	 System.out.println("I will remove a part and update the server.");
				    	 partsManager.sendMessage("remove", partsManager.getParts().get((String)imageSelection.getSelectedItem()));
				    	 partsManager.parts.remove((String)imageSelection.getSelectedItem());
				    	 
				     }else{
				    	 JComboBox cb = (JComboBox)ae.getSource();
					     String petName = (String)cb.getSelectedItem();
					     updatePicture(partsManager.parts.get(petName));
				     }

				}
				
	}
	
	private class CurrentItem{
		JLabel name;
		JLabel previewFrame;
		ImageIcon imagePreview;
		
		public CurrentItem(JLabel n, JLabel p, String s){
			name = n;
			previewFrame = p;
			imagePreview = new ImageIcon("Images/" + s + ".png");
		}
		
		public void addCurrentItem(JPanel panel){
			JPanel panel1 = new JPanel();
			panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
			panel1.add(name);
			previewFrame.setIcon(imagePreview);
			panel1.add(previewFrame);
			panel.add(panel1);
			panel1.setVisible(true);
			panel.setVisible(true);
			System.out.println("I'm in");
			panel.revalidate();
		}
		
	}

}
