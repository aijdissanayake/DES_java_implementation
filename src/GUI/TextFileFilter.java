/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Achala PC
 */
public class TextFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
         if (f.isDirectory()) {
        return true;
    }

    String extension = Utils.getExtension(f);
    if (extension != null) {
        if (extension.equals(Utils.txt)) {
                return true;
        } else {
            return false;
        }
    }

    return false;
    }

    @Override
    public String getDescription() {
        return "Just Text Files";
    }
    
}
