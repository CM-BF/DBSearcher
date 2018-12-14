package web.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.*;
import org.apache.lucene.store.FSDirectory;

public class Search {
		String Subject="";
		String SubmissionDDL="";
		String ConferenceTime="";
		String DetailMessage="";
		String Topic="";
	public int numTotalHits;
	
	
	public List<Map<String, String>> search(String subterm, String ddlterm, String timeterm, String topicterm, String siteterm, String dateterm) throws Exception {
		String index = "/home/levishery/eclipse-workspace/DBSearcher/WebContent/index";
	    int repeat = 0;
	    BooleanQuery booleanquery;
	    List<Map<String, String>> resultdocs = new ArrayList<>();
	    
	    System.out.println("entry search!");
	    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
	    IndexSearcher searcher = new IndexSearcher(reader);
	    Analyzer analyzer = new StandardAnalyzer();
	    
	    //*********************** construct queries*********************
	    Builder booleanbuilder = new BooleanQuery.Builder();
	    if(subterm!=null && !subterm.equals("")) {
		    QueryParser parser = new QueryParser("subject", analyzer);
		    Query subquery = parser.parse(subterm);
	    	booleanbuilder.add(subquery, BooleanClause.Occur.MUST);
	    }
	    if(ddlterm!=null && !ddlterm.equals("")) {
	    	QueryParser parser = new QueryParser("deadline", analyzer);
		    Query ddlquery = parser.parse(ddlterm);
	    	booleanbuilder.add(ddlquery, BooleanClause.Occur.MUST);
	    }
	    
	    System.out.println("sub"+subterm);
	    System.out.println(ddlterm);
	    System.out.println(timeterm);
	    System.out.println("topic:"+topicterm);
	    System.out.println(siteterm);
	    if(timeterm!=null && !timeterm.equals("")) {
	    	QueryParser parser = new QueryParser("conference", analyzer);
		    Query timequery = parser.parse(timeterm);
		    booleanbuilder.add(timequery, BooleanClause.Occur.MUST);
	    }
	    if(topicterm!=null && !topicterm.equals("")) {
	    	QueryParser parser = new QueryParser("topic", analyzer);
		    Query topicquery = parser.parse(topicterm);
		    booleanbuilder.add(topicquery, BooleanClause.Occur.MUST);
	    }
	    if(siteterm!=null && !siteterm.equals("")) {
	    	QueryParser parser = new QueryParser("site", analyzer);
		    Query sitequery = parser.parse(siteterm);
		    booleanbuilder.add(sitequery, BooleanClause.Occur.MUST);
	    }
	    if(dateterm!=null && !dateterm.equals("")) {
	    	QueryParser parser = new QueryParser("date", analyzer);
		    Query datequery = parser.parse(dateterm);
		    booleanbuilder.add(datequery, BooleanClause.Occur.MUST);
	    }
	    
	    booleanquery = booleanbuilder.build();
	    System.out.println(booleanquery);
	    
//	    Sort sort = new SortField(field, comparator);
	    
	    if (repeat > 0) {                           // repeat & time as benchmark
	        Date start = new Date();
	        for (int i = 0; i < repeat; i++) {
	          searcher.search(booleanquery, 100);
	        }
	        Date end = new Date();
	        System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
	    }
	    
	    //**************return searcher*********************
	    TopDocs results = searcher.search(booleanquery, 10);
	    
	    
	    numTotalHits = Math.toIntExact(results.totalHits);
	    System.out.println(numTotalHits + " total matching documents");
	    ScoreDoc[] hits = null;
	    if(numTotalHits>0) {
	    	hits = searcher.search(booleanquery, numTotalHits).scoreDocs;
		    for(ScoreDoc hit: hits) {
		    	Document doc = searcher.doc(hit.doc);
		    	Map<String, String> docfields = new HashMap<>();
		    	docfields.put("Subject", doc.get("subject"));
		    	docfields.put("SubmissionDDL", doc.get("deadline"));
		    	docfields.put("ConferenceTime", doc.get("conference"));
		    	docfields.put("DetailMessage", doc.get("url"));
		    	docfields.put("Topic", doc.get("topic"));
		    	docfields.put("Site", doc.get("site"));
		    	docfields.put("ImportantDate", doc.get("time"));
		    	resultdocs.add(docfields);
		    }
	    }
	    
	    return resultdocs;
	}
	public boolean isInteger(String str) {    
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(str).matches();    
	}
	
	
}

