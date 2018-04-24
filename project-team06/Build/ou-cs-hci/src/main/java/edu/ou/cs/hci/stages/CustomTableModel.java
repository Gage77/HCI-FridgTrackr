package edu.ou.cs.hci.stages;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {

	public CustomTableModel(String[] array) {
		
		for(int i = 0; i < array.length; i++) {
			this.addColumn(array[i]);
		}
	}
	
	@Override
    public Class<?> getColumnClass(int columnIndex) {
       return String.class;
    }
}
