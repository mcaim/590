package main;

public class InternalHuffmanNode implements HuffmanNode {

	HuffmanNode _left;
	HuffmanNode _right;
	
	public InternalHuffmanNode() {
		_left = null;
		_right = null;
	}
	
	public InternalHuffmanNode(HuffmanNode left, HuffmanNode right) {
		_left = left;
		_right = right;
	}
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return _left.count() + _right.count();
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int symbol() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Can't");
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return Math.max(_left.height() + 1, _right.height() + 1);
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return _left != null && _right != null && _left.isFull() && _right.isFull();
		
	}

	@Override
	public boolean insertSymbol(int length, int symbol) throws Exception {
		// TODO Auto-generated method stub
		if (_left != null) {
			if (!_left.isFull()) {
				return _left.insertSymbol(length-1,symbol);
			} else if (_right!=null) {
				if (!_right.isFull()) {
					return _right.insertSymbol(length-1, symbol);
				} else {
					return false;
				}
			} else {
				if (length == 1) {
					_right = new LeafHuffmanNode(symbol, 0);
					return true;
				} else {
					_right = new InternalHuffmanNode();
					return _right.insertSymbol(length-1, symbol);
				}
			}
		} else {
			if (length == 1) {
				_left = new LeafHuffmanNode(symbol, 0);
				return true;
			} else {
				_left = new InternalHuffmanNode();
				return _left.insertSymbol(length-1, symbol);
			}
		}
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return _left;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return _right;
	}

}
