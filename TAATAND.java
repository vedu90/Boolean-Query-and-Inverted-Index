/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static javaapplication1.LuceneIndex.queryList;

/**
 *
 * @author chaitu
 */
public class TAATAND 
{
    static void GetTAATANDList(String[] inputTerms,BufferedWriter _bw) throws IOException
    {
        try
        {
            //GetTAATAND
            List<Integer> resultTAATAND = new ArrayList<Integer>(queryList.get(0));
                
            int numOfComparisons = 0;
            for(int i = 1 ; i < queryList.size() ; i++)
            {
                //Merge each postings list with the intermediate result obtained 
                //from the previous merging

                List<Integer> tempDocs = queryList.get(i);
                List<Integer> previousDoc = new ArrayList<Integer>(resultTAATAND);

                int list1 = 0;
                int list2 = 0;
              
                resultTAATAND.clear();
               
                //Intersection operation
                while(list1 < tempDocs.size() && list2 < previousDoc.size())
                {
                    numOfComparisons++;
                    int val1 = tempDocs.get(list1);
                    int val2 = previousDoc.get(list2);

                    if(val1 == val2)
                    {
                        resultTAATAND.add(tempDocs.get(list1));
                        list1++;
                        list2++;
                    }
                    else if(val1 < val2)
                    {
                        list1++;
                    }
                    else
                    {
                        list2++;
                    }
                }
            }
            
            
            //Write the TAATAND results to output.txt file
            _bw.write("TaatAnd");
            _bw.newLine();
            for (String inputTerm : inputTerms) 
            {
                _bw.write(inputTerm+" ");
            }
            _bw.newLine();
            _bw.write("Results: ");
            if(resultTAATAND.isEmpty())
            {
                _bw.write("empty");
            }
     
            
            for(int n = 0 ; n < resultTAATAND.size() ; n++)
            {
                _bw.write(resultTAATAND.get(n)+" ");
            }

            _bw.newLine();
            _bw.write("Number of documents in results: "+resultTAATAND.size());
            _bw.newLine();
          
            _bw.write("Number of comparisons: "+numOfComparisons);
            _bw.newLine();
           
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
