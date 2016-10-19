/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import static javaapplication1.LuceneIndex.indexes;

/**
 *
 * @author chaitu
 */
public class GetPostingsList 
{
      static void FindPostingsList(String[] inputTerms,BufferedWriter _bw) throws IOException
      {
         
        try
        {
            //GET POSTINGS
            /**Read the inputs terms and retrieve their corresponding inverted
            postings list from the preprocessed hashmap**/
              for (String inputTerm : inputTerms) 
              {
                  _bw.write("GetPostings");
                  _bw.newLine();
                  _bw.write(inputTerm);
                  _bw.newLine();
                  _bw.write("Postings list: ");
              
                  List<Integer> docList = indexes.get(inputTerm);
                  if(docList != null)
                  {
                      for(int l = 0 ; l < docList.size() ; l++)
                      {
                          _bw.write(docList.get(l) + " ");
                      }
                  }
                  _bw.newLine();
              }
        }             
        catch (IndexOutOfBoundsException e)
        {
            return;
        }
        catch (IOException e)
        {
            return;                     
        }
        catch (NullPointerException e)
        {
            return;
        }
        catch(ArrayStoreException e)
        {
            return;
        }
      }
}
