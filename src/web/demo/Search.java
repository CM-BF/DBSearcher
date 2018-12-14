package web.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
	
	
	public List<Map<String, String>> search(String subterm, String ddlterm, String timeterm, String topicterm, String siteterm) throws Exception {
		String index = "index";
	    int repeat = 0;
	    BooleanQuery booleanquery;
	    List<Map<String, String>> resultdocs = new ArrayList<>();
	    
	    
	    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
	    IndexSearcher searcher = new IndexSearcher(reader);
	    Analyzer analyzer = new StandardAnalyzer();
	    
	    //*********************** construct queries*********************
	    Builder booleanbuilder = new BooleanQuery.Builder();
	    if(!subterm.equals("")) {
	    	Query subquery = new FuzzyQuery(new Term("subject", subterm));
	    	booleanbuilder.add(subquery, BooleanClause.Occur.MUST);
	    }
//	    if(!ddlterm.equals("") {
//	    	Query ddlquery = new FuzzyQuery(new Term("SubmissionDDL", ddlterm));
//	    	booleanbuilder.add(ddlquery, BooleanClause.Occur.MUST);
//	    }
	    if(!timeterm.equals("")) {
	    	Query timequery = new FuzzyQuery(new Term("time", timeterm));
	    	booleanbuilder.add(timequery, BooleanClause.Occur.MUST);
	    }
	    if(!topicterm.equals("")) {
	    	Query topicquery = new FuzzyQuery(new Term("topic", topicterm));
	    	booleanbuilder.add(topicquery, BooleanClause.Occur.MUST);
	    }
	    if(!siteterm.equals("")) {
	    	Query sitequery = new FuzzyQuery(new Term("site", siteterm));
	    	booleanbuilder.add(sitequery, BooleanClause.Occur.MUST);
	    }
	    
	    booleanquery = booleanbuilder.build();
	    
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
		    	docfields.put("ConferenceTime", doc.get("time"));
		    	docfields.put("DetailMessage", doc.get("url"));
		    	docfields.put("Topic", doc.get("topic"));
		    	docfields.put("Site", doc.get("site"));
		    	resultdocs.add(docfields);
		    }
	    }
	    
	    return resultdocs;
	}
	
	
}

