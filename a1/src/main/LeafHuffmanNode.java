package main;

public class LeafHuffmanNode implements HuffmanNode {
	
	int _symbol;
	int _count;
	
	public LeafHuffmanNode (int symbol, int count) {
		_symbol = symbol;
		_count = count;
	}
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return _count;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		return _symbol;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Can't");
	}

	@Override
	public HuffmanNode left() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Can't");
	}

	@Override
	public HuffmanNode right() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Can't");
	}

}
