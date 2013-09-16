/**
 * 
 */
package es.uah.cc.ie.portalupdater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

// import es.uah.cc.ie.automaticannotation.DBUtils;

/**
 * @author ivan
 * 
 */
public class LODEBDRecommendations {

	// private DBUtils dbUtils = new DBUtils();

	/**
	 * Return triples for Title Information LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 *            Resource ID
	 * @param resourceTitle
	 * @param resourceLang
	 * @param titles
	 *            Map of titles
	 * @param alternative
	 * @return
	 */
	public List<Triple> retrieveTitleInformationTriples(String voa3rId, String resourceLang, String resourceTitle,
			HashMap<String, String> titles, HashMap<String, String> alternative) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;
		String value = null;

		// Processing dc:title
		triple = new Triple();
		triple.setSubject(voa3rId);
		triple.setPredicate("http://purl.org/dc/elements/1.1/title");
		triple.setStringObject(resourceTitle);
		triple.setObjectFormat(resourceLang);
		result.add(triple);

		// Processing dcterms:title
		triple = new Triple();
		triple.setSubject(voa3rId);
		triple.setPredicate("http://purl.org/dc/terms/title");
		triple.setStringObject(resourceTitle);
		triple.setObjectFormat(resourceLang);
		result.add(triple);

		// Add the alternative titles
		titles.putAll(alternative);

		for (Entry<String, String> entry : titles.entrySet()) {

			// Filter text
			value = entry.getValue();
			if (value.length() > 249) {
				value = value.substring(0, 249);
			}
			if (value.startsWith("[") && value.endsWith("]")) {
				value = value.substring(1, value.length() - 1);
			}

			if (!value.equalsIgnoreCase(resourceTitle)) {

				// Processing dcterms:alternative
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/alternative");
				triple.setStringObject(value);
				triple.setObjectFormat(entry.getKey());
				result.add(triple);
			}
		}

		return result;

	}

	/**
	 * Return triples for Responsable Body LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param creators
	 * @param contributors
	 * @param publishers
	 * @return
	 */
	public List<Triple> retrieveResponsableBodyTriples(String voa3rId, ArrayList<String> creators,
			ArrayList<String> contributors, ArrayList<String> publishers) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;

		// Processing dc:creator
		for (String creator : creators) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/creator");
			triple.setStringObject(cleanFullName(creator));
			result.add(triple);
		}

		// Processing dc:contributor
		for (String contributor : contributors) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/contributor");
			triple.setStringObject(cleanFullName(contributor));
			result.add(triple);
		}

		// Processing dc:publisher
		for (String publisher : publishers) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/publisher");
			triple.setStringObject(publisher);
			result.add(triple);
		}

		return result;
	}

	/**
	 * Return triples for Physical Characteristics LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param dates
	 * @param identifiers
	 * @param languages
	 * @param formats
	 * @param sources
	 * @param arrayList
	 * @return
	 */
	public List<Triple> retrievePhysicalCharacteristicsTriples(String voa3rId, ArrayList<String> dates,
			ArrayList<String> identifiers, ArrayList<String> languages, ArrayList<String> formats,
			ArrayList<String> mediums, ArrayList<String> sources) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;

		// Processing dc:date
		String datePattern;
		for (String dateString : dates) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/date");
			datePattern = findOutDatePattern(dateString);
			triple.setObjectFormat(datePattern);
			triple.setDateObject(cleanDate(dateString, datePattern));
			result.add(triple);
		}

		// Processing dc:identifier
		for (String identifier : identifiers) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/identifier");

			if (identifier != null & identifier.contains("http")) {
				if ((identifier.length() > 249)) {
					identifier = identifier.substring(0, 249);
				}
				triple.setUriObject(identifier);
			} else {
				triple.setStringObject(identifier);
			}

			result.add(triple);
		}

		// Processing dc:language
		for (String language : languages) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/language");
			triple.setStringObject(language);
			result.add(triple);
		}

		// Processing dc:format
		for (String format : formats) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/format");
			triple.setStringObject(format);
			result.add(triple);
		}

		// Processing dc:format
		for (String medium : mediums) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/terms/medium");
			triple.setStringObject(medium);
			result.add(triple);
		}

		// Processing dc:source
		for (String source : sources) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/source");
			triple.setStringObject(source);
			result.add(triple);
		}
		return result;
	}

	/**
	 * Return triples for Holding Information LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param availability
	 * @return
	 */
	public List<Triple> retrieveHoldingInformationTriples(String voa3rId, ArrayList<String> availability) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;

		// Processing agls:availability
		for (String aux : availability) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://www.agls.gov.au/agls/terms/availability");
			triple.setStringObject(aux);
			result.add(triple);
		}
		return result;
	}

	/**
	 * Return triples for Subject Information LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param subjects
	 * @param coverages
	 * @param temporals
	 * @param spatials
	 * @param arrayList
	 * @return
	 */
	public List<Triple> retrieveSubjectInformationTriples(String voa3rId, HashMap<String, ArrayList<String>> subjects,
			ArrayList<String> agrovocTerms, ArrayList<String> coverages, ArrayList<String> spatials,
			ArrayList<String> temporals) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;

		// Processing dc:subject
		for (Entry<String, ArrayList<String>> subjectsPerLang : subjects.entrySet()) {

			for (String subject : subjectsPerLang.getValue()) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/elements/1.1/subject");
				triple.setStringObject(subject);
				triple.setObjectFormat(subjectsPerLang.getKey());
				result.add(triple);
			}
		}

		/*
		String aux;
		// Processing agrovocTerms
		for (String agrovocTerm : agrovocTerms) {
			triple = new Triple();
			triple.setSubject(voa3rId);

			aux = dbUtils.getUriFromSubject(StringEscapeUtils.escapeSql(agrovocTerm));
			if (aux != null & aux.contains("http")) {
				triple.setPredicate("http://purl.org/dc/terms/subject");
				triple.setUriObject(aux);
			} else {
				triple.setStringObject(aux);
				triple.setPredicate("http://purl.org/dc/elements/1.1/subject");
			}

			result.add(triple);
		}*/

		// Processing dc:coverage
		for (String coverage : coverages) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/coverage");
			triple.setStringObject(coverage);
			result.add(triple);
		}

		// Processing dc:spatial
		for (String spatial : spatials) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/terms/spatial");
			triple.setStringObject(spatial);
			result.add(triple);
		}

		// Processing dc:temporal
		for (String temporal : temporals) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/terms/temporal");
			triple.setStringObject(temporal);
			result.add(triple);
		}
		return result;
	}

	/**
	 * Return triples for Description of Content LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param arrayList
	 * @param descriptions
	 * @param types
	 * @param arrayList2
	 * @return
	 */
	public List<Triple> retrieveDescriptionofContentTriples(String voa3rId, HashMap<String, String> descriptions,
			HashMap<String, String> abstracts, ArrayList<String> tablesOfContent, ArrayList<String> types) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;

		// Processing dc:description
		for (Entry<String, String> entry : descriptions.entrySet()) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/description");
			triple.setStringObject(entry.getValue());
			triple.setObjectFormat(entry.getKey());
			result.add(triple);
		}

		// Processing dc:abstract
		for (Entry<String, String> entry : abstracts.entrySet()) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/terms/abstract");
			triple.setStringObject(entry.getValue());
			triple.setObjectFormat(entry.getKey());
			result.add(triple);
		}

		// Processing dc:type
		for (String type : types) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/type");
			triple.setStringObject(type);
			result.add(triple);
		}
		return result;
	}

	/**
	 * Return triples for Property Rights LODE according to FAO LODE-BD
	 * Recommendations 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param rights
	 * @return
	 */
	public List<Triple> retrievePropertyRightsTriples(String voa3rId, ArrayList<String> rights) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;
		// Processing dc:rights
		for (String right : rights) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/rights");
			triple.setStringObject(right);
			result.add(triple);
		}
		return result;
	}

	/**
	 * Return triples for Use LODE according to FAO LODE-BD Recommendations 2.0
	 * - Beta
	 * 
	 * @param voa3rId
	 * @return
	 */
	public List<Triple> retrieveUsageTriples(String voa3rId) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;
		return result;
	}

	/**
	 * Return triples for RelationLODE according to FAO LODE-BD Recommendations
	 * 2.0 - Beta
	 * 
	 * @param voa3rId
	 * @param relations
	 * @param arrayList7
	 * @param arrayList6
	 * @param arrayList5
	 * @param arrayList4
	 * @param arrayList3
	 * @param arrayList2
	 * @param arrayList
	 * @param arrayList3
	 * @param arrayList2
	 * @param arrayList
	 * @return
	 */
	public List<Triple> retrieveRelationTriples(String voa3rId, ArrayList<String> relations, ArrayList<String> isVersionOfs,
			ArrayList<String> hasVersions, ArrayList<String> isReplacedBys, ArrayList<String> replacess,
			ArrayList<String> isRequiredBys, ArrayList<String> requiress, ArrayList<String> isPartOfs,
			ArrayList<String> hasParts, ArrayList<String> isReferencedBys, ArrayList<String> referencess) {
		List<Triple> result = new ArrayList<Triple>();
		Triple triple = null;

		// Processing dc:relation
		for (String relation : relations) {
			triple = new Triple();
			triple.setSubject(voa3rId);
			triple.setPredicate("http://purl.org/dc/elements/1.1/relation");
			if (relation != null & relation.contains("http")) {
				triple.setUriObject(relation);
			} else {
				triple.setStringObject(relation);
			}
			result.add(triple);
		}

		// Processing dcterms:isVersionOf
		if (isVersionOfs != null) {
			for (String aux : isVersionOfs) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/isVersionOf");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}

		// Processing dcterms:hasVersion
		if (hasVersions != null) {
			for (String aux : hasVersions) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/hasVersion");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}
		// Processing dcterms:isReplacedBy
		if (isReplacedBys != null) {
			for (String aux : isReplacedBys) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/isReplacedBy");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}
		// Processing dcterms:replaces
		if (replacess != null) {
			for (String aux : replacess) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/replaces");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}

		// Processing dcterms:isRequiredBy
		if (isRequiredBys != null) {
			for (String aux : isRequiredBys) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/isRequiredBy");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}

		// Processing dcterms:requires
		if (requiress != null) {
			for (String aux : requiress) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/requires");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}

		// Processing dcterms:isPartOf
		if (isPartOfs != null) {
			for (String aux : isPartOfs) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/isPartOf");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}

		// Processing dcterms:hasPart
		if (hasParts != null) {
			for (String aux : hasParts) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/hasPart");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}
		// Processing dcterms:isReferencedBy
		if (isReferencedBys != null) {
			for (String aux : isReferencedBys) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/isReferencedBys");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}
		// Processing dcterms:references
		if (referencess != null) {
			for (String aux : referencess) {
				triple = new Triple();
				triple.setSubject(voa3rId);
				triple.setPredicate("http://purl.org/dc/terms/references");
				triple.setStringObject(aux);
				result.add(triple);
			}
		}
		return result;
	}

	/**
	 * Formats the full name
	 * 
	 * @param name
	 * @return
	 */
	private String cleanFullName(String name) {
		// Example author: Vutto, N.L.
		// Example author: Volotovskij, I.D., Nationals, Minsk . Institute ...
		// I only want: Volotovskij, I.D.
		// Example author: Correa, Carla Maria Camargo(Universidade Federal do
		// Parana)
		// I only want: Correa, Carla Maria Camargo
		int firstComma = name.indexOf(",");
		int secondComma = name.indexOf(",", firstComma + 1);
		if (secondComma != -1) {
			name = name.substring(0, secondComma);
		}
		int firstParenthesis = name.indexOf("(");
		if (firstParenthesis != -1) {
			name = name.substring(0, firstParenthesis);
		}

		if (name.length() > 249) {
			name = name.substring(0, 249);
		}
		return name;

	}

	/**
	 * Discover date pattern
	 * 
	 * @param dateString
	 * @return
	 */
	private String findOutDatePattern(String dateString) {
		String datePattern = "unknown";
		for (String regexp : MetadataSQLHelper.getDATE_FORMAT_REGEXPS().keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				datePattern = MetadataSQLHelper.getDATE_FORMAT_REGEXPS().get(regexp);
			}
		}

		if (datePattern.equals("MMMyyyy")) {
			datePattern = "yyyy";
		}
		return datePattern;
	}

	// Comprobar el string

	/**
	 * Formats the date
	 * 
	 * @param dateString
	 * @param datePattern
	 * @return
	 */
	private String cleanDate(String dateString, String datePattern) {
		dateString = dateString.replaceAll("\\.", "");
		dateString = dateString.replaceAll("\\(", "");
		dateString = dateString.replaceAll("\\)", "");

		if (datePattern.equals("MMMyyyy")) {
			dateString = dateString.substring(dateString.length() - 4, dateString.length());
		}

		return dateString;
	}

}
