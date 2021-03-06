package web.demo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import javax.swing.text.html.parser.Parser;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.protobuf.WireFormat.FieldType;

/** Index all text files under a directory.
 * <p>
 * This is a command-line application demonstrating simple Lucene indexing.
 * Run it with no command-line arguments for usage information.
 */
public class Index {
  
  private Index() {}

  /** Index all text files under a directory. 
 * @throws Exception */
  public static void index() throws Exception {
    String indexPath = "/home/levishery/eclipse-workspace/DBSearcher/WebContent/index";
    boolean create = true;
    
    Date start = new Date();
    try {
      System.out.println("Indexing to directory '" + indexPath + "'...");

      Directory dir = FSDirectory.open(Paths.get(indexPath));
      Analyzer analyzer = new StandardAnalyzer();
      IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

      if (create) {
        // Create a new index in the directory, removing any
        // previously indexed documents:
        iwc.setOpenMode(OpenMode.CREATE);
      } else {
        // Add new documents to an existing index:
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
      }

      // Optional: for better indexing performance, if you
      // are indexing many documents, increase the RAM
      // buffer.  But if you do this, increase the max heap
      // size to the JVM (eg add -Xmx512m or -Xmx1g):
      //
      iwc.setRAMBufferSizeMB(256.0);

      IndexWriter writer = new IndexWriter(dir, iwc);
      indexDocs(writer);

      // NOTE: if you want to maximize search performance,
      // you can optionally call forceMerge here.  This can be
      // a terribly costly operation, so generally it's only
      // worth it when your index is relatively static (ie
      // you're done adding documents to it):
      //
      // writer.forceMerge(1);

      writer.close();

      Date end = new Date();
      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

    } catch (IOException e) {
      System.out.println(" caught a " + e.getClass() +
       "\n with message: " + e.getMessage());
    }
  }

  static void indexDocs(final IndexWriter writer) throws IOException, Exception {
	  File file = new File("documents");
      int filecount = file.listFiles().length;
      for(int num=0; num<filecount; num++) {
      	// open file
          Path path = Paths.get("documents/" + "Mail"+ num +".txt");
          String s = "";
          try {
          	BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
          	s = reader.readLine();
          	
          }catch(IOException e) {
          	e.printStackTrace();
          }
       // make a new, empty document
          Document doc = new Document();
          
          Field pathField = new StringField("path", path.toString(), Field.Store.YES);
          doc.add(pathField);
          
          
          doc.add(new LongPoint("modified", Files.getLastModifiedTime(path).toMillis()));
          
          // **********************Json prepare*******************
          JSONParser parser = new JSONParser();
          Object obj = parser.parse(s);
          JSONObject dir = (JSONObject)obj;
          // add content
          String content = dir.get("content").toString();
          doc.add(new TextField("content", content, Field.Store.YES));
          // add subject
          doc.add(new TextField("subject", String.join("", (JSONArray)dir.get("subject")).toString(), Field.Store.YES));
          // add author
          doc.add(new TextField("author", dir.get("author").toString(), Field.Store.YES));
          // add topic
          doc.add(new TextField("topic", String.join("\n", (JSONArray)dir.get("Topic")).toString(), Field.Store.YES));
          // add ddl
          doc.add(new TextField("deadline", String.join("", (JSONArray)dir.get("deadline")).toString(), Field.Store.YES));
          // add Time
          doc.add(new TextField("time", String.join("\n", (JSONArray)dir.get("Time")).toString(), Field.Store.YES));
          // add Site
          doc.add(new TextField("site", dir.get("Site").toString().replaceAll(",", " "), Field.Store.YES));
          // add url
          doc.add(new TextField("url", dir.get("url").toString(), Field.Store.YES));
          // add conference
          doc.add(new TextField("conference", dir.get("Conference").toString(), Field.Store.YES));


          
          if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
            // New index, so we just add the document (no old document can be there):
            System.out.println("adding " + path.toString());
            writer.addDocument(doc);
          } else {
            // Existing index (an old copy of this document may have been indexed) so 
            // we use updateDocument instead to replace the old one matching the exact 
            // path, if present:
            System.out.println("updating " + path.toString());
            writer.updateDocument(new Term("path", file.toString()), doc);
          }
      }
  }

  /** Indexes a single document */
  static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
    try (InputStream stream = Files.newInputStream(file)) {
      // make a new, empty document
      Document doc = new Document();
      
      // Add the path of the file as a field named "path".  Use a
      // field that is indexed (i.e. searchable), but don't tokenize 
      // the field into separate words and don't index term frequency
      // or positional information:
      Field pathField = new StringField("path", file.toString(), Field.Store.YES);
      doc.add(pathField);
      
      // Add the last modified date of the file a field named "modified".
      // Use a LongPoint that is indexed (i.e. efficiently filterable with
      // PointRangeQuery).  This indexes to milli-second resolution, which
      // is often too fine.  You could instead create a number based on
      // year/month/day/hour/minutes/seconds, down the resolution you require.
      // For example the long value 2011021714 would mean
      // February 17, 2011, 2-3 PM.
      doc.add(new LongPoint("modified", lastModified));
      
      // Add the contents of the file to a field named "contents".  Specify a Reader,
      // so that the text of the file is tokenized and indexed, but not stored.
      // Note that FileReader expects the file to be in UTF-8 encoding.
      // If that's not the case searching for special characters will fail.
      // **********************Json prepare*******************
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
      String s = reader.readLine();
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(s);
      JSONObject dir = (JSONObject)obj;
      // add content
      String content = dir.get("content").toString();
      doc.add(new TextField("content", content, Field.Store.YES));
      // add url
      doc.add(new TextField("subject", dir.get("subject").toString(), Field.Store.YES));
      // add author
      doc.add(new TextField("author", dir.get("author").toString(), Field.Store.YES));
      // add topic
      doc.add(new TextField("topic", dir.get("Topic").toString(), Field.Store.YES));
      // add ddl
      doc.add(new TextField("deadline", dir.get("deadline").toString(), Field.Store.YES));
      // add Time
      doc.add(new TextField("time", dir.get("Time").toString(), Field.Store.YES));
      // add Site
      doc.add(new TextField("site", dir.get("Site").toString(), Field.Store.YES));


      
      if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
        // New index, so we just add the document (no old document can be there):
        System.out.println("adding " + file);
        writer.addDocument(doc);
      } else {
        // Existing index (an old copy of this document may have been indexed) so 
        // we use updateDocument instead to replace the old one matching the exact 
        // path, if present:
        System.out.println("updating " + file);
        writer.updateDocument(new Term("path", file.toString()), doc);
      }
    }catch (Exception e) {
		// TODO: handle exception
    	e.printStackTrace();
	}
  }
}

