/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static javaapplication1.LuceneIndex.queryList;

/**
 *
 * @author chaitu
 */


class indexElements{
    int index;
    int element;
}

public class DAATOR 
{   
    static void GetDAATORList(String[] inputTerms,BufferedWriter _bw) throws IOException
    {
        List<Integer> resultDAATOR = new ArrayList<Integer>();
               
        int numOfComparisons_DAAT_OR = 0;

        int numOfTerms = queryList.size();
        
        try
        {
            //GetDAATOR
             List<Integer> termPointer = new ArrayList<Integer>(Collections.nCopies(queryList.size(), 0));
            while(numOfTerms > 1)
            {
                /*Do union by finding the minimum element among all the
                  elements the index pointer points to in each postings list
                  (initially points to the first element in each postings list)
                  and insert that element into the resultant union list, and
                  move the index pointer element to next element and repeat 
                  the min operation again until the number of postings list left is 1*/
                List<Integer> minPointerList = new ArrayList<Integer>();
                int pos = 0;
                int min = 0;
                for(int i = 0 ; i < queryList.size() ; i++)
                {
                    if(termPointer.get(i) < queryList.get(i).size())
                    {
                        minPointerList.add(i);
                        pos = i+1;
                        min = queryList.get(i).get(termPointer.get(i));
                        break;
                    }
                }

                //Union operation - min element is found out here
                for(int i = pos ; i < queryList.size(); i++)
                {
                    if(termPointer.get(i) >= queryList.get(i).size())
                    {
                        continue;
                    }
                    numOfComparisons_DAAT_OR++;
             
                    if(queryList.get(i).get(termPointer.get(i)) < min)
                    {
                        minPointerList.clear();
                        minPointerList.add(i);
                        min = queryList.get(i).get(termPointer.get(i));
                    }
                    else if(queryList.get(i).get(termPointer.get(i)) == min)
                    {
                        minPointerList.add(i);
                    }
                }

                resultDAATOR.add(min);

                while(!minPointerList.isEmpty())
                {
                   int c = minPointerList.get(0);
                   int prevValue = termPointer.get(c);
                   prevValue++;
                   termPointer.set(c,prevValue);

                   if(prevValue >= queryList.get(c).size())
                   {
                       numOfTerms--;
                   }
                   minPointerList.remove(0);
                }

            }

            if(numOfTerms == 1)
            {
                int index = 0;

                while(index < queryList.size())
                {
                    if(termPointer.get(index) < queryList.get(index).size())
                    {
                        break;
                    }
                    index++;
                }

                int temp = termPointer.get(index);
          
                while(temp < queryList.get(index).size())
                {
                    resultDAATOR.add(queryList.get(index).get(temp));
                    temp++;
                }

            }

            //Write DAATOR results to output.txt file
            _bw.write("DaatOr");
            _bw.newLine();
            for (String inputTerm : inputTerms) 
            {
                _bw.write(inputTerm+" ");
            }
            _bw.newLine();
            _bw.write("Results: ");

            if(resultDAATOR.isEmpty())
            {
                _bw.write("empty");
            }

            for(int n = 0 ; n < resultDAATOR.size() ; n++)
            {
                _bw.write(resultDAATOR.get(n)+" ");
            }

            _bw.newLine();
            _bw.write("Number of documents in results: "+resultDAATOR.size());
            _bw.newLine();
            _bw.write("Number of comparisons: "+numOfComparisons_DAAT_OR);
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



