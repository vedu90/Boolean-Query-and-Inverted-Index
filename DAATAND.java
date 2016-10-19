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
public class DAATAND 
{
    static void GetDAATANDList(String[] inputTerms,BufferedWriter _bw) throws IOException
    {
        
        try
        {
            //GetDAATAND
            List<Integer> resultDAATAND = new ArrayList<Integer>();
               
            int numOfComparisons_DAAT_AND = 0;

            List<Integer> termPointer = new ArrayList<Integer>(Collections.nCopies(queryList.size(), 0));
            boolean completeBreak = false;
            for(int i = 0 ; i < queryList.get(0).size() ; i++)
            {
                /* Do intersection operation by considering each docID from the first
                   postings list and then search for that docID in the remaining
                   postings and increase their corresponding index pointers if found
                   in all the postings list.
                   If docID is not found in atleast one postings list, break the
                   loop and start searching for the next docID*/
                int docID = queryList.get(0).get(termPointer.get(0));
                int prev_index = termPointer.get(0);
                termPointer.set(0, prev_index+1);
                int postingTerm = 1;
             
                //Intersection Operation
                while(postingTerm < queryList.size())
                {
                    numOfComparisons_DAAT_AND++;
                    while(docID > queryList.get(postingTerm).get(termPointer.get(postingTerm)))
                    {
                        numOfComparisons_DAAT_AND++;
                        int prev_Value = termPointer.get(postingTerm);
                        if(prev_Value < (queryList.get(postingTerm).size()-1))
                        {
                            termPointer.set(postingTerm, prev_Value+1);
                        }
                        else
                        {
                            completeBreak = true;
                            break;
                        }
                    }
                    if(docID != queryList.get(postingTerm).get(termPointer.get(postingTerm)))
                    {
                        break;
                    }
                    else if(docID == queryList.get(postingTerm).get(termPointer.get(postingTerm)))
                    {
                        int prev_Value = termPointer.get(postingTerm);
                        if(prev_Value < (queryList.get(postingTerm).size()-1))
                        {
                            termPointer.set(postingTerm, prev_Value+1);
                        }
                    }
                    postingTerm++;
                }
                if(postingTerm == queryList.size())
                {
                    resultDAATAND.add(docID);
                }
                /*If index pointer in atleast one of the postings list completes
                  traversing through all its docID's, then the intersection is
                  treated as completed as there are no more terms to search in 
                  that particular postings list*/
                else if(completeBreak == true)
                {
                    break;
                }
            }

             //Write DAATAND results to output.txt file
            _bw.write("DaatAnd");
            _bw.newLine();
            for (String inputTerm : inputTerms) 
            {
                _bw.write(inputTerm+" ");
            }
            _bw.newLine();
            _bw.write("Results: ");

            if(resultDAATAND.isEmpty())
            {
                _bw.write("empty");
            }

            for(int n = 0 ; n < resultDAATAND.size() ; n++)
            {
                _bw.write(resultDAATAND.get(n)+" ");
            }

            _bw.newLine();
            _bw.write("Number of documents in results: "+resultDAATAND.size());
            _bw.newLine();
            _bw.write("Number of comparisons: "+numOfComparisons_DAAT_AND);
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
