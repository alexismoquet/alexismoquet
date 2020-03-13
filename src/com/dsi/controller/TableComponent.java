package com.dsi.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

    public class TableComponent extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (value instanceof JTable)
                return (JTable) value;
            else
                return this;
        }
    }

