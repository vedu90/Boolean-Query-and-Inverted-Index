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
public class TAATOR 
{
     static void GetTAATORList(String[] inputTerms,BufferedWriter _bw) throws IOException
     {
         
       try
       {
         //  GetTAATOR  
           List<Integer> resultTAATOR = new ArrayList<Integer>(queryList.get(0));
       
          int numOfComparisons_OR = 0;
          for(int s = 1 ; s < queryList.size() ; s++)
          {
              /*Do union for the current postings list and intermediate union
                list result obtained from previous union*/
              List<Integer> tempDocs_OR = queryList.get(s);
              List<Integer> previousDoc_OR = new ArrayList<Integer>(resultTAATOR);

              int list1_OR = 0;
              int list2_OR = 0;
           
              resultTAATOR.clear();
    
              //Union Operation
              while(list1_OR < tempDocs_OR.size() && list2_OR < previousDoc_OR.size())
              {
                  numOfComparisons_OR++;
                  int val1 = tempDocs_OR.get(list1_OR);
                  int val2 = previousDoc_OR.get(list2_OR);

                  if(val1 == val2)
                  {
                      resultTAATOR.add(tempDocs_OR.get(list1_OR));
                      list1_OR++;
                      list2_OR++;
                  }
                  else if(val1 < val2)
                  {
                      resultTAATOR.add(tempDocs_OR.get(list1_OR));
                      list1_OR++;
                  }
                  else
                  {
                      resultTAATOR.add(previousDoc_OR.get(list2_OR));
                      list2_OR++;
                  }
              }
            
              while(list1_OR < tempDocs_OR.size())
              {
                  resultTAATOR.add(tempDocs_OR.get(list1_OR));
                  list1_OR++;
              }

              while(list2_OR < previousDoc_OR.size())
              {
                  resultTAATOR.add(previousDoc_OR.get(list2_OR));
                  list2_OR++;
              }


          }

          //Write TAATOR results to output.txt file
          _bw.write("TaatOr");
          _bw.newLine();
          for (String inputTerm : inputTerms) 
          {
              _bw.write(inputTerm+" ");
          }
          _bw.newLine();
          _bw.write("Results: ");

          if(resultTAATOR.size() == 0)
          {
              _bw.write("empty");
          }

          for(int n = 0 ; n < resultTAATOR.size() ; n++)
          {
              _bw.write(resultTAATOR.get(n)+" ");
          }

          _bw.newLine();
          _bw.write("Number of documents in results: "+resultTAATOR.size());
          _bw.newLine();
          _bw.write("Number of comparisons: "+numOfComparisons_OR);
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
