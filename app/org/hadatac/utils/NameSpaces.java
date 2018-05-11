package org.hadatac.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Comparator;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetRewindable;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
//import org.hadatac.console.http.SPARQLUtils;
//import org.hadatac.metadata.loader.URIUtils;
//import org.hadatac.utils.CollectionUtil;

import org.apache.commons.io.FileUtils;

public class NameSpaces {

	public static String CACHE_PATH   = "tmp/cache/";
	public static String CACHE_PREFIX = "copy-";

	public static Map<String, NameSpace> table = new HashMap<String, NameSpace>();

        public Map<String, Integer> loadedOntologies; 

	private static NameSpaces instance = null;

	public static NameSpaces getInstance() {
	      if(instance == null) {
	         instance = new NameSpaces();
	      }
	      return instance;
	   }
	   
	private NameSpaces() {
				
		Properties prop = new Properties();
		InputStream input = null;
                loadedOntologies= new HashMap<String, Integer>();
		
		try {
			//System.out.println("loading properties file for namespaces.");
			//prop.load(new FileInputStream("namespaces.properties"));
			input = getClass().getClassLoader().getResourceAsStream("namespaces.properties");
			prop.load(input);
			String tmpList[];
			NameSpace tmpNS;
			for (Map.Entry<Object, Object> nsEntry : prop.entrySet()) {
				String nsAbbrev = ((String)nsEntry.getKey());
				//System.out.println("abbrev.: " + nsAbbrev);
			    if (nsAbbrev != null) {
			        tmpList = prop.getProperty(nsAbbrev).split(",");
			        //System.out.println("Value: " + nsEntry.getValue());
			        tmpNS = null;
			        if (tmpList.length >= 1 && tmpList[0] != null && !tmpList[0].equals("")) {
			        	tmpNS = new NameSpace();
			        	tmpNS.setAbbreviation(nsAbbrev);
			        	tmpNS.setName(tmpList[0]);
				        if (tmpList.length >= 2 && tmpList[1] != null && !tmpList[1].equals("")) {
				        	tmpNS.setType(tmpList[1]);
				        }
				        if (tmpList.length >= 3 && tmpList[2] != null && !tmpList[2].equals("")) {
				        	tmpNS.setURL(tmpList[2]);
				        }
				        //System.out.println("loaded " + tmpNS.toString());
			        }
			        if (tmpNS != null) {
			        	table.put(nsAbbrev, tmpNS);
			        }
			    }
			}
		} catch (FileNotFoundException e) {
			System.out.println("[NameSpaces.java ERROR]: could not read file namespaces.properties");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public String printTurtleNameSpaceList() {
		String ttl = "";
	    for (Map.Entry<String, NameSpace> entry : table.entrySet()) {
	        String abbrev = entry.getKey().toString();;
	        NameSpace ns = entry.getValue();
	        ttl = ttl + "@prefix " + abbrev + ": <" + ns.getName() + "> . \n";
	    }
	    return ttl;
	}
	
	public String printSparqlNameSpaceList() {
		String ttl = "";
	    for (Map.Entry<String, NameSpace> entry : table.entrySet()) {
	        String abbrev = entry.getKey().toString();;
	        NameSpace ns = entry.getValue();
	        ttl = ttl + "PREFIX " + abbrev + ": <" + ns.getName() + "> \n";
	    }
	    return ttl;
	}
    
        public int getNumberNameSpaces() {
	    if (table == null) {  
		return 0;
	    }
	    return table.size();
	}
    
        public String jsonLoadedOntologies() {
	    String json = "";
	    boolean first = true;
	    List<Map.Entry<String,Integer>> entries = 
		new ArrayList<Map.Entry<String,Integer>>(loadedOntologies.entrySet());
	    Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
	        public int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b) {
                   return Integer.compare(b.getValue(), a.getValue());
                }
            });
	    for (Map.Entry<String, Integer> entry : entries) {
		if (first) {
		    first = false;
		} else {
		    json = json + ",";
		}
	        String abbrev = entry.getKey().toString();;
	        int triples = entry.getValue();
	        json = json + " [\"" + abbrev + "\"," + triples +"]";
	    }
	    return json;
        }

        public void nameSpacesStats() {
	  /*for (Map.Entry<String, NameSpace> entry : table.entrySet()) {
	  String abbrev = entry.getKey().toString();;
	        NameSpace ns = entry.getValue();
		String queryString = NameSpaces.getInstance().printSparqlNameSpaceList() +
		    "select (count(?a) as ?triples) where {" +
		    " ?a ?b ?c . " +
		    " filter (strStarts(str(?a), \"" + ns.getName() + "\")). " + 
		    " }"; 
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				CollectionUtil.getCollectionsName(CollectionUtil.METADATA_SPARQL), query);
		ResultSet results = qexec.execSelect();
		ResultSetRewindable resultsrw = ResultSetFactory.copyResults(results);
		qexec.close();
		//System.out.println(queryString + "\n" + abbrev + " " + resultsrw.size());

		if (resultsrw.size() > 0) {
		    QuerySolution soln = resultsrw.next();
		    try {
			if (soln.getLiteral("triples") != null) {
			    String answer = soln.getLiteral("triples").getString();
			    //if (answer.length() != 0) {
				System.out.println(abbrev + " has " + answer + " subjects");
				//}
			}
		    } catch (Exception e1) {
			e1.printStackTrace();
		    }
		} else {
		    System.out.println(abbrev + " has 0 subjects");
		}
		}*/
	}
		
	public String copyNameSpacesLocally(int mode) {
		String message = "";
	    //byte[] buffer = new byte[1024];
	    //int bytesRead;

	    // copy supporting ontologies locally
	    for (Map.Entry<String, NameSpace> entry : table.entrySet()) {
	    	String abbrev = entry.getKey().toString();
	    	String nsURL = entry.getValue().getURL();
	    	if (nsURL != null && !nsURL.equals("") && !nsURL.equals(":")) {
	    		String filePath = CACHE_PATH + CACHE_PREFIX + abbrev.replace(":","");
	    		message += Feedback.print(mode, "   Creating local copy of " + abbrev + ". ");		
			    for (int i = abbrev.length(); i < 36; i++) {
			    	message += Feedback.print(mode, ".");
			    }
	         
	    		//if (!abbrev.equals("jp-entities:") && (!abbrev.equals(":")) && (!abbrev.equals("jp-sn:"))) {
	    			URL url;

	    			
	    			try {
						url = new URL(nsURL);
						File f = new File(filePath);
		    			FileUtils.copyURLToFile(url, f);
		    			
		    			/*
		    			BufferedInputStream inputStream = null;
						BufferedOutputStream outputStream = null;
						URLConnection connection = url.openConnection();
						// If you need to use a proxy for your connection, the URL class has another openConnection method.
						// For example, to connect to my local SOCKS proxy I can use:
						// url.openConnection(new Proxy(Proxy.Type.SOCKS, newInetSocketAddress("localhost", 5555)));
						inputStream = new BufferedInputStream(connection.getInputStream());
						File f = new File(filePath);
						outputStream = new BufferedOutputStream(new FileOutputStream(f));
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, bytesRead);
						}
						inputStream.close();
						outputStream.close();
						*/
		    			message += Feedback.println(mode, "Local copy created.");
					} catch (Exception e) {
						message += Feedback.println(mode, "Failed to create local copy.");
					}
	    		//}
	    	}
	        
	    }	
	    message += Feedback.println(mode," ");
	    return message;
	}	

    public static void main(String[] args) {
    	NameSpaces ns = new NameSpaces();
    	System.out.println(ns.printTurtleNameSpaceList());
    }
	
}
