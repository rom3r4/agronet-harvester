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
package org.ontspace.owl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/**
 * This class is created en order to easy the management of the languages in
 * the ont-space code.
 * @author ie
 */
public class LanguageISOHelper {
    
    private String[] _languages;
    private HashMap<String, Locale> _iso_639_1;
    private HashMap<String, Locale> _iso_639_3;
    private HashMap<String, Locale> _langName;

    /**
     * This constructor creates a new object of LanguageISOHelper
     */
    public LanguageISOHelper() {
        _languages = Locale.getISOLanguages();
        
        _iso_639_1 = new HashMap<String, Locale>(_languages.length);
        _iso_639_3 = new HashMap<String, Locale>(_languages.length);
        _langName = new HashMap<String, Locale>(_languages.length);
        for (String language : _languages) {
            Locale locale = new Locale(language);
            _iso_639_1.put(language, locale);
            _iso_639_3.put(locale.getISO3Language().toLowerCase(), locale);
            _langName.put(locale.getDisplayLanguage().toLowerCase(), locale);
        }
    }

    /**
     * This method obtains the ISO-639-1 from a given text
     * @param text String with the language
     * @return language formatted in ISO-639-1, or null if not found
     */
    public String getISO_639_1_fromText(String text) {
        String result = null;
        
        if (text == null || text.isEmpty()) {
          text = "en";
        }
        
        if (text.length() == 2) {
            //text = "en"
            if (_iso_639_1.containsKey(text)) {
                result = _iso_639_1.get(text).getLanguage();
            }
        } else if (text.length() == 3) {
            //text = "eng"
            if (_iso_639_3.containsKey(text)) {
                result = _iso_639_3.get(text).getLanguage();
            }            
        } else if (text.contains("_")) {
            //text = "en_US"
            String short_text=text.substring(0,text.indexOf("_"));
            if (_iso_639_1.containsKey(short_text)) {
                result = _iso_639_1.get(short_text).getLanguage();
            }            
        } else {
            //text = "english"
            if (_langName.containsKey(text.toLowerCase())) {
              Locale loc = _iso_639_1.get(text.toLowerCase());
              if (loc != null) {
                result = loc.getLanguage();
              }
            } else {
                result = null;
            }
        }
        
        return result == null ? "en" : result;
    }

    /**
     * this method prints the values stored in this class
     */
    public void printValues() {
        Iterator<String> it = _iso_639_1.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("ISO-639-1: " + key + " ISO-639-3: "
                    + _iso_639_1.get(key).getISO3Language()
                    + " Display language: " + _iso_639_1.get(key).
                    getDisplayLanguage());
        }
    }
}
