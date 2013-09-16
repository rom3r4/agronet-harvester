/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.portalupdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.ontspace.dc.translator.DublinCore;
/*
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.voa3rap2.translator.Voa3rAP2;
*/

/**
 * 
 * @author ivan
 */
public class MetadataRDFHelper {

	private LODEBDRecommendations lodeBD = new LODEBDRecommendations();

	/**
	 * Retrieve a set of RDF triples from a DublinCore bean
	 * 
	 * @param voa3rId
	 * @param resourceTitle
	 * @param resourceLang
	 * @param dc
	 * @return
	 */
	 /*
	public List<Triple> retrieveTriplesforDC(String voa3rId, String resourceLang, String resourceTitle, DublinCore dc) {
		List<Triple> result = new ArrayList<Triple>();

		// LODE-BD Group: 1. Title Information
		result.addAll(lodeBD.retrieveTitleInformationTriples(voa3rId, resourceLang, resourceTitle, dc.getTitles(),
				new HashMap<String, String>()));

		// LODE-BD Group: 2. Responsable Body
		result.addAll(lodeBD.retrieveResponsableBodyTriples(voa3rId, dc.getCreators(), dc.getContributors(),
				dc.getPublishers()));

		// LODE-BD Group: 3. Physical Characteristics
		result.addAll(lodeBD.retrievePhysicalCharacteristicsTriples(voa3rId, dc.getDates(), dc.getIdentifiers(),
				dc.getLanguages(), dc.getFormats(), new ArrayList<String>(), dc.getSources()));

		// LODE-BD Group: 4. Holding/Location Information
		result.addAll(lodeBD.retrieveHoldingInformationTriples(voa3rId, new ArrayList<String>()));

		// LODE-BD Group: 5. Subject Information
		result.addAll(lodeBD.retrieveSubjectInformationTriples(voa3rId, dc.getSubjects(),new ArrayList<String>(), dc.getCoverages(), new ArrayList<String>(), new ArrayList<String>()));

		// LODE-BD Group: 6. Description of Content
		result.addAll(lodeBD.retrieveDescriptionofContentTriples(voa3rId, dc.getDescriptions(), new HashMap<String, String>(), new ArrayList<String>(), dc.getTypes()));

		// LODE-BD Group: 7. Intellectual Property Rights
		result.addAll(lodeBD.retrievePropertyRightsTriples(voa3rId, dc.getRights()));

		// LODE-BD Group: 8. Usage
		result.addAll(lodeBD.retrieveUsageTriples(voa3rId));

		// LODE-BD Group: 9. Relation
		result.addAll(lodeBD.retrieveRelationTriples(voa3rId, dc.getRelations(), null, null, null, null, null, null, null, null, null, null));

		// /// FALTA
		// createLiteralProperty(newDCInstance, dc.getAgrovocTerms(),
		// "subjectAgrovocTerm");

		return result;
	}*/

	/**
	 * Retrieve a set of RDF triples from a Agris bean
	 * 
	 * @param voa3rId
	 * @param agris
	 * @return
	 */
	 /*
	List<Triple> retrieveTriplesforAgris(String voa3rId, String resourceLang, String resourceTitle, Agrisap agris) {
		List<Triple> result = new ArrayList<Triple>();

		// LODE-BD Group: 1. Title Information
		result.addAll(lodeBD.retrieveTitleInformationTriples(voa3rId, resourceLang, resourceTitle, agris.getTitles(),
				createMultipleEntriesMap(resourceLang, agris.getAlternative())));

		// LODE-BD Group: 2. Responsable Body
		result.addAll(lodeBD.retrieveResponsableBodyTriples(voa3rId, agris.getCreatorPersonal(), new ArrayList<String>(),
				agris.getPublisherName()));

		// LODE-BD Group: 3. Physical Characteristics
		result.addAll(lodeBD.retrievePhysicalCharacteristicsTriples(voa3rId, agris.getDate(), agris.getIdentifier(),
				agris.getLanguage(), agris.getFormat(), agris.getMedium(), agris.getSource()));  

		// LODE-BD Group: 4. Holding/Location Information
		result.addAll(lodeBD.retrieveHoldingInformationTriples(voa3rId,prepareAvailability(agris.getAvailabilityLocation(),agris.getAvailabilityNumber())));

		// LODE-BD Group: 5. Subject Information
		ArrayList<String> subjects=agris.getSubject();
		subjects.addAll(agris.getSubjectClassification());
		result.addAll(lodeBD.retrieveSubjectInformationTriples(voa3rId, createOneEntryMap(resourceLang, subjects), agris.getSubjectThesaurus(), agris.getCoverage(),agris.getSpatial(),agris.getTemporal()));

		// LODE-BD Group: 6. Description of Content
		result.addAll(lodeBD.retrieveDescriptionofContentTriples(voa3rId, createMultipleEntriesMap(resourceLang, agris.getDescription()) , agris.getAbstracts(), new ArrayList<String>(),agris.getType()));

		// LODE-BD Group: 7. Intellectual Property Rights
		result.addAll(lodeBD.retrievePropertyRightsTriples(voa3rId, agris.getRights()));

		// LODE-BD Group: 8. Usage
		result.addAll(lodeBD.retrieveUsageTriples(voa3rId));

		// LODE-BD Group: 9. Relation
		result.addAll(lodeBD.retrieveRelationTriples(voa3rId, agris.getRelation(),agris.getIsVersionOf(),
				agris.getHasVersion(),agris.getIsReplacedBy(),agris.getReplaces(),agris.getIsRequiredBy(),agris.getRequires(),
				agris.getIsPartOf(),agris.getHasPart(),agris.getIsReferencedBy(),agris.getReferences()));


		return result;
	}*/

	private ArrayList<String> prepareAvailability(ArrayList<String> availabilityLocation,
			ArrayList<String> availabilityNumber) {
		ArrayList<String> result=new ArrayList<String>();
		String aux="";
		for(int i=0;i<availabilityLocation.size();i++) {
			aux=availabilityLocation.get(i);		
			result.add(i,aux);
		}
		
		for(int i=0;i<availabilityNumber.size();i++) {
			aux=availabilityNumber.get(i);	
			
			if(result.size()>i && result.get(i)!=null){
				result.set(i, result.get(i).concat(". " + aux));	
			}
			
		}
		return result;
	}

	private HashMap<String, String> createMultipleEntriesMap(String resourceLang, ArrayList<String> elementList) {

		HashMap<String, String> result = new HashMap<String, String>();

		for (String aux : elementList) {
			result.put(resourceLang, aux);
		}

		return result;

	}

	private HashMap<String, ArrayList<String>> createOneEntryMap(String resourceLang, ArrayList<String> elementList) {

		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();

		result.put(resourceLang, elementList);

		return result;

	}

	
	/*
	public Entry<String, String> retrieveTitleAndLangFromDC(DublinCore dc) {
		Map<String, String> res = new HashMap<String, String>();

		String title;
		String resourcelang;

		try {
			resourcelang = dc.getLanguages().get(0);
		} catch (Exception e) {
			resourcelang = "en";
		}

		try {
			title = dc.getTitle(resourcelang);
			if (title == null) {
				Map<String, String> titles = dc.getTitles();
				Iterator<String> langIt = titles.keySet().iterator();
				if (langIt.hasNext()) {
					String lang = langIt.next();
					title = titles.get(lang);
					resourcelang = lang;
				} else {
					title = "title not found in any language";
					resourcelang = "en";
				}
			}
		} catch (Exception e) {
			title = "title";
			resourcelang = "en";
		}

		res.put(resourcelang, title);
		return res.entrySet().iterator().next();

	}

	
	public Entry<String, String> retrieveTitleAndLangFromAgris(Agrisap agris) {

		Map<String, String> res = new HashMap<String, String>();

		String title;
		String resourcelang;
		try {
			resourcelang = agris.getLanguage().get(0);
		} catch (Exception e) {
			resourcelang = "en";
		}

		try {
			title = agris.getTitle(resourcelang);
			if (title == null) {
				HashMap<String, String> titles = agris.getTitles();
				Set<String> availableLang = titles.keySet();
				Iterator<String> langIt = availableLang.iterator();
				if (langIt.hasNext()) {
					String lang = langIt.next();
					title = titles.get(lang);
					resourcelang = lang;
				} else {
					title = "title not found in any language";
					resourcelang = "en";
				}
			}
		} catch (Exception e) {
			title = "default title";
			resourcelang = "en";
		}

		if (title.length() > 249) {
			title = title.substring(0, 249);
		}
		if(title.startsWith("[") && title.endsWith("]")){
			res.put(resourcelang, title.substring(1, title.length()-1));
		} else {
			res.put(resourcelang, title); 
		}
        
        
		return res.entrySet().iterator().next();
	}*/

}
