package web.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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
import org.apache.lucene.store.FSDirectory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
public class test {
	public static void main(String[] args) throws Exception{
		Search searcher = new Search();
		List<Map<String, String>> result = searcher.search("", "", "", "data", "");
//		System.out.println(result);
//		IndexSearcher searcher = search.search(request.getParameter("subject"), request.getParameter("Topic"),
//				request.getParameter("SubmissionDDL"), request.getParameter("MeetingTime"));
//		TopDocs results = searcher.search(search.booleanquery, hitsPerPage);
//	    ScoreDoc[] hits = results.scoreDocs;
//	    int numTotalHits = Math.toIntExact(results.totalHits);
//	    out.println(numTotalHits + " total matching documents");
		System.out.println("End");
		/*,org.apache.lucene.analysis.Analyzer,
org.apache.lucene.analysis.standard.StandardAnalyzer,
org.apache.lucene.document.Document,
org.apache.lucene.index.DirectoryReader,
org.apache.lucene.index.IndexReader,
org.apache.lucene.index.Term,
org.apache.lucene.queryparser.classic.QueryParser,
org.apache.lucene.store.FSDirectory
searcher.search(request.getParameter("subject"),
			request.getParameter("Topic"), request.getParameter("SubmissiontDDL"),
			request.getParameter("ConferenceTime"), request.getParameter("Site"));
*
*
*/
	}
}
