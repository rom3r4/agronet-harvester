/*
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ontspace.owl.util;

import com.lingway.ld.LangDetector;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the code to automatically detect the language of some text.
 *  For the moment it is working with: en, es, fr, el, it, nl 
 * It uses JLangDetect is a pure Java implementation of a language detector. 
 * JLangDetect provides a toolkit for training language recognition, and a simple 
 * implementation of a detector.
 * http://www.jroller.com/melix/entry/nlp_in_java_a_language
 */
public class AutomaticLangDetector {

    private LangDetector _detector = null;
    private String _treeFolderPath = null;

    /**
     * Creates a new object of this class but specifying the local folder with
     * the corpus files
     * @param corpusFolderPath folder with the corpus files
     */
    public AutomaticLangDetector(String corpusFolderPath) {
        _detector = new LangDetector();
        _treeFolderPath = corpusFolderPath;
        initLangDetector();
    }

    /**
     * Set up the LangDetector by registering all the corpus files
     */
    private void initLangDetector() {
        ObjectInputStream langTree = null;
        try {

            File corpusTreeFolder = new File(_treeFolderPath);
            FileFilter binFilter = new FileFilter() {

                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".bin"));
                }
            };
            ArrayList<File> binTreeFiles = new ArrayList<File>();
            binTreeFiles.addAll(Arrays.asList(corpusTreeFolder.listFiles(
                binFilter)));
            //the bin tree file name is LANG_tree.bin
            String treeLang;
            _detector = new LangDetector();
            for (File treeFile : binTreeFiles) {
                langTree = new ObjectInputStream(new FileInputStream(treeFile));
                //+ "en_tree.bin"));
                treeLang = treeFile.getName();
                treeLang = treeLang.substring(0, treeLang.indexOf("_"));
                _detector.register(treeLang, langTree);

            }

        } catch (IOException ex) {
            Logger.getLogger(AutomaticLangDetector.class.getName()).
                log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AutomaticLangDetector.class.getName()).
                log(Level.SEVERE, null, ex);
        }
//        finally {
//            try {
//                langTree.close();
//            } catch (IOException ex) {
//                Logger.getLogger(AutomaticLangDetector.class.getName()).
//                    log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public String detectLang(String text) {
        return _detector.detectLang(text, false);
    }
}
