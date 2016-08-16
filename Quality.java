package stegano.image.processing;

public enum Quality {
	
	GOOD(4);
	
	public int leastBitsSum;
	public int shift;
	public int cuttingMask;
	
	private Quality(int bits){
		computeLeastBitsSum(bits);
		computeCuttingMask(bits);
		computeShift(bits);
	}

	private void computeShift(int bits) {
		shift = 8 - bits;
	}

	private void computeLeastBitsSum(int bits) {
		leastBitsSum = 0;
		for(int i = 0; i < bits; i++){
			leastBitsSum += Math.pow(2, i);
		}
	}

	private void computeCuttingMask(int bits) {
		cuttingMask = 255 - leastBitsSum;
	}
}
