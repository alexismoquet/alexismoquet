package com.dsi.controller.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ExempleImageJTable {

    private static final String[] COLUMN_NAMES = {"Texte", "Texte avec icône", "Image"};
    private static final Object[][] ROW_DATA = {
            { "XXXX", "OUI", "LogoIconeDSI.png" },
            { "YYYY", "NON", "toto.jpg" },
            { "ZZZZ", "OUI", "IMG_1790.jpg" }
    };

    public static void main(String[] args) {

        JFrame frame = new JFrame("JTable avec image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable(ROW_DATA, COLUMN_NAMES);

        table.getColumnModel().getColumn(1).setCellRenderer(new IconCellRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new ImageCellRenderer());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.getContentPane().add(panel);

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public final static class IconCellRenderer extends DefaultTableCellRenderer {

        private final static ImageIcon OUI_IMAGE = new ImageIcon("yes-icon.png");
        private final static ImageIcon NON_IMAGE = new ImageIcon("no-icon.png");

        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {

            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            JLabel label = (JLabel)component;
            if ( "OUI".equals(value) ) {
                label.setIcon(OUI_IMAGE);
            }
            else if ( "NON".equals(value) ) {
                label.setIcon(NON_IMAGE);
            }
            else {
                label.setIcon(null);
            }

            return component;
        }


    }

    public final static class ImageCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {

            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            JLabel label = (JLabel)component;
            String cheminImage = String.valueOf(value);

            ImageIcon icon = new ImageIcon(cheminImage);

            if ( icon.getImageLoadStatus()==java.awt.MediaTracker.COMPLETE ) {
                label.setIcon(icon);
                table.setRowHeight(row, icon.getIconHeight());
            }
            else {
                label.setIcon(null);
                table.setRowHeight(row, table.getRowHeight());
            }
            label.setText(""); // on efface le texte


            return component;
        }


    }

}
