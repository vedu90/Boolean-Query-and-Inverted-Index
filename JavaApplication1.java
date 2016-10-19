/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;

import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.*;
import java.util.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.Fields;
import static org.apache.lucene.index.MultiFields.getFields;
import static org.apache.lucene.index.MultiFields.getTerms;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.util.BytesRef;
/**
 *
 * @author chaitu
 */

class LuceneIndex
{
    public static HashMap<String, List<Integer>> indexes;
    public static List<List<Integer>> queryList = new ArrayList<List<Integer>>();
     static Comparator<List<Integer>> doc_len_compare = new Comparator<List<Integer>>()
     {

        @Override
        public int compare(List<Integer> l1, List<Integer> l2)
        {
            int i = l1.size();
            int j = l2.size();
            if (i < j) {
                return -1;
            } 
            else if (i > j) 
            {
                return 1;
            }
            else 
            {
                return 0;
            }
        }

    };
    
    static int ReadIndexValues(String[] args) throws IOException
    {
        indexes = new HashMap<>();
        String path = args[0];
        FileSystem fs = FileSystems.getDefault();
        Path path1 = fs.getPath(path);  
        IndexReader reader = DirectoryReader.open(FSDirectory.open(path1));
        
        //Extract all the fields from Lucene Index
        Fields _fields = getFields(reader);
        
        Iterator<String> it_fields = _fields.iterator();
        int k = 0;
        while(it_fields.hasNext())
        {
            String text_field = it_fields.next();
            
            
            //Build the inverted index for all the terms using hashmap
            if(text_field.startsWith("text"))
            {   
                //Get all the terms for the corresponding field
                Terms _term = getTerms(reader, text_field);
                
                TermsEnum it_terms = _term.iterator();
  
                int temp = 0;
                while(true)
                {   
                    
                    BytesRef eachTerm = it_terms.next();
                    PostingsEnum _postings= it_terms.postings(null);
                    if(eachTerm != null)
                    {                                 
                        List<Integer> docList = new ArrayList<>();
                       
                        while(true)
                        {
                            int next_Doc = _postings.nextDoc();
                            if(next_Doc != PostingsEnum.NO_MORE_DOCS)
                            {
                                docList.add(next_Doc);
                            }
                            else
                            {
                                break;
                            }
                        }
                        indexes.put(eachTerm.utf8ToString(),docList);
                    }
                    else
                    {
                        break;
                  
                    }
                }
            }
           
                
        }
    
        //Open the output file in write mode
        File _fout = new File(args[1]);
	FileOutputStream _fos = new FileOutputStream(_fout);
 
	BufferedWriter _bw = new BufferedWriter(new OutputStreamWriter(_fos,"UTF-8"));
        
        int new_line = 0;
        //Open the input file in read mode
        try (BufferedReader br = new BufferedReader(new FileReader(args[2])))
        {
            String line;
            //Read each line from the input
            while ((line = br.readLine()) != null)
            {
                //Remove BOM characters
                line = line.replace("\uFEFF", "");  
                String[] inputTerms = line.split(" ");
                if(new_line != 0)
                {
                    _bw.newLine();
                }
                new_line++;
                //GET_POSTINGS_LIST and write them to output file
                GetPostingsList.FindPostingsList(inputTerms,_bw);
                    
                //Process Query Terms and make a list of list in sorted order of term frequency
                queryList.clear();
                
                for (String inputTerm : inputTerms) 
                {
                    List<Integer> docList = indexes.get(inputTerm);
                    if(docList != null)
                    {
                         queryList.add(docList);
                    }
                }
       
                
                //Sort the query List based on postings size
                Collections.sort(queryList, doc_len_compare);
                
                //TAAT_AND 
                TAATAND.GetTAATANDList(inputTerms,_bw);
                 
                //TAAT_OR 
                TAATOR.GetTAATORList(inputTerms, _bw);
                
                //DAAT_AND
                DAATAND.GetDAATANDList(inputTerms, _bw);
               
                //DAATOR  
                DAATOR.GetDAATORList(inputTerms, _bw);
                
            }
            br.close();
        }
        _bw.close();
        
        return reader.maxDoc();
    }
}
 

public class JavaApplication1 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException 
    {
       int val = 0;       
       val = LuceneIndex.ReadIndexValues(args);
    }
    
}
