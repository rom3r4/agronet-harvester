/*
ont-space - The ontology-based resource metadata repository
Copyright (c) 2006-2011, Information Eng. Research Unit - Univ. of Alcalá
http://www.cc.uah.es/ie
This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.
This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.
You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.ontspace.agrisap.translator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.ontspace.owl.util.AutomaticLangDetector;
import org.ontspace.owl.util.LanguageISOHelper;

/**
 *
 * @author raquel
 */
public class Title2Title {

    /**
     * Obtain the title
     * @param child JDOM Element
     * @param agris Agris object
     * @param langISOHelper Language ISO Helper
     * @param detector Automatic Language detector
     * @param logger Looger
     * @param xmlFile xmlfile
     */
    public Title2Title(Element child, Agrisap agris,
            LanguageISOHelper langISOHelper, AutomaticLangDetector detector,
            Logger logger, File xmlFile) {
        String metadata = child.getName();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");

        //1-Title
        if (metadata.compareTo("title") == 0) {
            String titleValue = child.getTextTrim();
            //it is possible that the title element contains a dc:alternativetitle
            //so the rest of the code does not make any sense here
            if (titleValue.length() > 2) {
                String lang = child.getAttributeValue("lang", xmlNS);
                String iso_lang = null;
                if (lang != null) {
                    iso_lang = new String();
                    iso_lang = langISOHelper.getISO_639_1_fromText(lang.toLowerCase());
                }
                boolean englishByDefault = false;
                if (iso_lang == null) {
                    //I try to autodetec the language
                    if (detector != null) {
                        iso_lang = detector.detectLang(titleValue);
                    }
                    //in some cases the
                    if (iso_lang == null) {
                        englishByDefault = true;
                        iso_lang = "en";
                    }

                    if (logger != null) {
                        String message = new String();
                        if (xmlFile != null) {
                            message = xmlFile.getName() + " ";
                        }
                        message += "TITLE -> ";
                        if (!englishByDefault) {
                            message += "language autodetected: " + iso_lang
                                    + " ";
                        } else {
                            message += "language by default: " + iso_lang;
                        }
                        message += titleValue;
                        logger.log(Level.WARNING, message);
                    }
                }

                agris.addTitle(iso_lang, titleValue);
            }
        } else //1.1-alternative
        if (metadata.compareTo("alternative") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setAlternative(value);
            }
        }

    }
}
