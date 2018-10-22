package com.hm.sort;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class will be sort the source CSV file as mentioned below.
 * HM seems to have the very odd need of sorting csv files in an way
 * determined by another file. That is, sorting should not be on ascending nor
 * decending way in the normal manner, but rather as determined by another csv
 * file.
 *
 * @author Balaji Jayabal
 */

public class CSVFileSortingService {
	
	private static final String COMMA_DELIMITER = ",";
	public static final String EMPTY = "";

	public static void main(String[] args){	
		
		BufferedInputStream sourceInputStream  = null;
		BufferedInputStream sortingInputStream  = null;
	    InputStreamReader sourceInputStreamReader = null;
	    InputStreamReader sortingInputStreamReader = null;   
		BufferedReader sourceFileReader,sortingFileReader;
		String sourceDesiderHeader = null; 
		String sortingDesiderHeader = null;
		sourceFileReader = null;
		sortingFileReader = null;
		String sourceFileline =EMPTY;
		String sortingFileline =EMPTY;			
		List<List<String>> sourceArray = new ArrayList<>();
		List<List<String>> destinationArray = new ArrayList<>();

		if (args.length == 4) {    
			try {
				// Read arguments.
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				sourceInputStream = (BufferedInputStream) classLoader.getResourceAsStream(args[0]);
				sourceInputStreamReader = new InputStreamReader(sourceInputStream);		
				sourceFileReader = new BufferedReader(sourceInputStreamReader);
				
				sortingInputStream = (BufferedInputStream) classLoader.getResourceAsStream(args[2]);
				sortingInputStreamReader = new InputStreamReader(sortingInputStream);	
				sortingFileReader = new BufferedReader(sortingInputStreamReader);
				
				sourceDesiderHeader = args[1];
				sortingDesiderHeader = args[3];
				
				System.out.println("Source File");
				// Read source CSV file and add into ArrayList.	
				while ((sourceFileline = sourceFileReader.readLine()) != null) {
					String[] sourceFileValues = sourceFileline.split(COMMA_DELIMITER);
					if (sourceFileValues.length > 0) {
						sourceArray.add(Arrays.asList(sourceFileValues));
						System.out.println(Arrays.toString(sourceFileValues));
					}
				} 
				
				System.out.println("Sorting File");
				// Read sorting CSV file and add into ArrayList	
				while ((sortingFileline = sortingFileReader.readLine()) != null) {
					String[] sortingFileValues = sortingFileline.split(COMMA_DELIMITER);
					if (sortingFileValues.length > 0) {
						destinationArray.add(Arrays.asList(sortingFileValues));	
						System.out.println(Arrays.toString(sortingFileValues));
					} 
				} 
				System.out.println("Soruce File Header: "+ sourceDesiderHeader + "  Sorting File Header: "+ sortingDesiderHeader);

			}catch (FileNotFoundException e) {	
				System.out.println("Provided File not exist");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error while reading file");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Error while reading file");
				e.printStackTrace();
			}
			finally {
				try {
					closeFileReader(sourceFileReader, sortingFileReader, sourceInputStream, sortingInputStream, sourceInputStreamReader, sortingInputStreamReader);
				} catch (IOException e) {
					System.out.println("Error while closing fileReader");
					e.printStackTrace();
				} 
			}  
			sortSourceFileData(sourceDesiderHeader, sortingDesiderHeader, sourceArray, destinationArray);
		} else {
			System.out.println("You have given '" +args.length+"' arguments. Please give 4 arguments. For example, ./file1.csv b ./file2.csv d");
		}
	}

	// Close file.
	private static void closeFileReader(BufferedReader sourceFileReader, BufferedReader sortingFileReader,
			BufferedInputStream sourceInputStream, BufferedInputStream sortingInputStream,
			InputStreamReader sourceInputStreamReader, InputStreamReader sortingInputStreamReader) throws IOException {
		sourceFileReader.close();
		sortingFileReader.close();
		sourceInputStream.close();
		sortingInputStream.close();
		sourceInputStreamReader.close();
		sortingInputStreamReader.close();
	}
	
	
	//Sort Source File based on the Sorting File.
	private static void sortSourceFileData(String sourceDesiderHeader, String sortingDesiderHeader,
			List<List<String>> sourceArray, List<List<String>> destinationArray) {

		// Find the column header where input argument is matching.
		int sortingColumnNumber = destinationArray.get(0).indexOf(sortingDesiderHeader);
		int sourceColumnNumber = sourceArray.get(0).indexOf(sourceDesiderHeader);

		if (sortingColumnNumber >= 0) {
			if (sourceColumnNumber >= 0) {
				for (int i = 1; destinationArray.size() > i; i++) {
					for (int j = 1; sourceArray.size() > j; j++) {
						if (destinationArray.get(i).get(sortingColumnNumber)
								.equalsIgnoreCase(sourceArray.get(j).get(sourceColumnNumber))) {
							System.out.println(sourceArray.get(j));
							// remove printed row from sourceArray.
							sourceArray.remove(j);
						}
					}
				}
				System.out.println("Sorting Completed Successfully!!!");
			} else {
				System.out.println("Argument '" + sourceDesiderHeader
						+ "' is not matching with any of the Source File's Header. Please provide valid header value");
			}
		} else {
			System.out.println("Argument '" + sortingDesiderHeader
					+ "' is not matching with Sorting File's Header. Please provide valid header value.");
		}
	}	
}
