package org.lrnmergepdf.MergePDFUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 * Merge PDF files
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	final String dir = "C:\\Users\\kiran\\Desktop\\FunctionalProgramming\\";
    	final int depth = 1;
    	
    	try {
			List<String> files = getFiles(dir, depth);
			convertToPdf(files);
			//files.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
    			
    }

	private static void convertToPdf(List<String> files) throws IOException{
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		pdfMerger.setDestinationFileName("C:\\Users\\kiran\\Desktop\\FunctionalProgramming\\FunctionalMerged.pdf");
		
		files
			.stream()
			.forEach(file -> {
				try {
					System.out.println("Adding "+file);
					pdfMerger.addSource(new File(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
		pdfMerger.mergeDocuments();
	}

	private static List<String> getFiles(final String dir, final int depth) throws IOException {
		try (Stream<Path> stream = Files.walk(Paths.get(dir), depth).sorted(Comparator.comparingLong(p -> p.toFile().lastModified()))) {
    		return stream
    					.filter(file -> !Files.isDirectory(file))
    					//.map(Path::getFileName)
    					.map(Path::toAbsolutePath)
    					.map(Path::toString)
    					.filter(fileName -> fileName.endsWith(".pdf"))
    					.collect(Collectors.toList());
		} 
	}
}
