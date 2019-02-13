package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {
		
	private Map<Integer, String> _code_map;
	private Map<Integer, String> _temp_map;
	
	public HuffmanEncoder(int[] symbols, int[] symbol_counts) throws Exception {
		assert symbols.length == symbol_counts.length;
		//System.out.println(symbols.length == symbol_counts.length);
		
		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes
		
		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();
		
		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.
		for (int i = 0; i < 256; i++) {
			node_list.add(new LeafHuffmanNode(symbols[i], symbol_counts[i]));
		}

		// Sort the leaf nodes
		Collections.sort(node_list);
		//System.out.println(node_list.get(node_list.size()-1).symbol());

		// While you still have more than one node in your list...
		while(node_list.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			HuffmanNode small_node1 = node_list.remove(0);
			HuffmanNode small_node2 = node_list.remove(0);
			
			// Create a new internal node with those two nodes as children.
			InternalHuffmanNode internal_node = new InternalHuffmanNode(small_node1,small_node2);
			
			// Add the new internal node back into the list
			node_list.add(internal_node);
			
			// Resort
			Collections.sort(node_list);
		}

		// Create a temporary empty mapping between symbol values and their code strings
		_temp_map = new HashMap<Integer, String>();

		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.
		
		
		HuffmanNode root = node_list.get(0);
		//cmap = traversetree(root, "", cmap);
		traverse(root, "", true);
		//System.out.println(_temp_map.size());
		
		
		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in cmap).
		for (int i = 0; i < 256; i++) {
			//System.out.println(_temp_map.get(i).length());
			sym_with_length.add(new SymbolWithCodeLength(i,_temp_map.get(i).length()));
		}
		
		// Sort sym_with_lenght
		Collections.sort(sym_with_length);

		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor
		
		InternalHuffmanNode canonical_root = new InternalHuffmanNode();
		for (int i = 0; i < 256; i++) {
			canonical_root.insertSymbol(sym_with_length.get(i).codeLength(), sym_with_length.get(i).value());
		}

		// If all went well, tree should be full.
		assert canonical_root.isFull();
		
		// Create code map that encoder will use for encoding
		
		_code_map = new HashMap<Integer, String>();
		
		// Walk down canonical tree forming code strings as you did before and
		// insert into map.	
		//traversetree(canonical_root, "", _code_map);
		traverse(canonical_root, "", false);
	}

//	public Map<Integer,String> traversetree(HuffmanNode root, String code, Map<Integer, String> map) throws Exception {
//		Map<Integer,String> _map = map;
//		if (root.isLeaf()) {
//			_map.put(root.symbol(), code);
//			//return _map;
//		} else {
//			if (root.left() != null) {
//				String newcode = code + "0";
//				traversetree(root.left(),newcode,map);
//			} else if (root.right() != null) {
//				String newcode = code += '1';
//				traversetree(root.right(),newcode,map);
//			}
//		}
//		return _map;
//	}
	
	public void traverse(HuffmanNode root, String code_string, boolean temp) throws Exception {
		if(!root.isLeaf()) {
			if(root.left()!=null) {
				traverse(root.left(),code_string+"0",temp);
			}
			if(root.right()!=null) {
				traverse(root.right(),code_string+"1",temp);
			}
		}else {
			if(temp) _temp_map.put(root.symbol(), code_string);
			else _code_map.put(root.symbol(), code_string);
		}
	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}

}
