package com.dsi.controller.tableModel;

import com.dsi.model.beans.Materiel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageCellRenderer extends DefaultTableCellRenderer {

    public Materiel visuels;

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {

        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        JLabel label = (JLabel) component;
        String cheminImage = String.valueOf(value);

        ImageIcon icon = new ImageIcon(cheminImage);

        if (icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            label.setIcon(icon);
            table.setRowHeight(row, icon.getIconHeight());
        } else {
            label.setIcon(null);
            table.setRowHeight(row, table.getRowHeight());
        }
        label.setText(""); // on efface le texte


        return component;
    }
}
