package view;

import util.BulkLoader;

import javax.swing.*;
import java.io.File;

public class BulkLoading {
    public void show() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a CSV file");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File csvFile = fileChooser.getSelectedFile();
            if (csvFile != null && csvFile.getName().endsWith(".csv")) {
                BulkLoader loader = new BulkLoader();
                int count = loader.loadBooksFromCSV(csvFile);
                JOptionPane.showMessageDialog(null, "Bulk loading completed. Books added: " + count);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a valid .csv file.");
            }
        }
    }
}
