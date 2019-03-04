package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import ac.ArithmeticDecoder;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class PriorValueDecoder {

	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
		String input_file_name = "data/out-compressed-priorval.dat";
		String output_file_name = "data/out-uncompressed-priorval.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		Integer[] symbols = new Integer[256];
		
		for (int i=0; i<256; i++) {
			symbols[i] = i;
		}

		// set up models
		
		FreqCountIntegerSymbolModel[] models = new FreqCountIntegerSymbolModel[256];
		
		
		for (int i=0; i<256; i++) {
			models[i] = new FreqCountIntegerSymbolModel(symbols);
		}
		
		// Read in number of symbols encoded

		int num_symbols = bit_source.next(32);

		// Read in range bit width and setup the decoder

		int range_bit_width = bit_source.next(8);
		ArithmeticDecoder<Integer> decoder = new ArithmeticDecoder<Integer>(range_bit_width);

		// Decode and produce output.
		
		System.out.println("Uncompressing file: " + input_file_name);
		System.out.println("Output file: " + output_file_name);
		System.out.println("Range Register Bit Width: " + range_bit_width);
		System.out.println("Number of encoded symbols: " + num_symbols);
		
		FileOutputStream fos = new FileOutputStream(output_file_name);

		// set up previous frame
		
		int[] prev_frame = new int[4096];
		
		// decode each frame
		
		for (int i=0; i<300; i++) {
			for (int j=0; j<4096; j++) {
				int symbol = decoder.decode(models[prev_frame[j]], bit_source);
				fos.write(symbol);
				models[prev_frame[j]].addToCount(symbol);
				prev_frame[j] = symbol;
			}
		}
		
		/*for (int i=0; i<num_symbols; i++) {
			int sym = decoder.decode(model, bit_source);
			fos.write(sym);
			model.addToCount(sym);
		}*/

		System.out.println("Done.");
		fos.flush();
		fos.close();
		fis.close();
	}
}
