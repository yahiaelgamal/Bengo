
public class DataFetcher {
	/*
	 * This is a container for the caches and memory
	 */
	
	int levels;
	Cache[] caches;
	Memory mem;
	
	public DataFetcher(int levels, int numlines1, int numlines2, int numlines3,
  					   int hitTime1,  int hitTime2, int hitTime3,
					   int penalty1, int penalty2, int penalty3, 
					   int assoc1, int assoc2, int assoc3,
  					   int policy1,  int policy2, int policy3) {
		this.levels = levels;
		caches = new Cache[levels];
		
		if (levels <= 1)
			caches[0] = new Cache(numlines1, hitTime1, penalty1, assoc1, policy1);
		if (levels <= 2)
			caches[1] = new Cache(numlines2, hitTime2, penalty2, assoc2, policy2);
		if (levels <= 3)
			caches[2] = new Cache(numlines3, hitTime3, penalty3, assoc3, policy3);
	}
	
	public int fetch(int address) {
		for(int i = 0; i < levels; i++) {
			Integer res = caches[i].read(address);
			if (res == null) {
				// miss, apply penalty 
				Bengo.CURRENT_CYCLE += caches[i].penalty;
			}else {
				Bengo.CURRENT_CYCLE += caches[i].hitTime;
				return res;
			}
		}
		Bengo.CURRENT_CYCLE += mem.hitTime;
		return mem.read(address);
	}
}
