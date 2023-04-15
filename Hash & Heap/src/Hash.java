
public class Hash {

	public Node[] table;
	public int size;
	public int sizeOfData;

	public Hash(int size) {
		this.size = size;

		this.table = new Node[size];
		for (int i = 0; i < table.length; i++) {
			table[i] = null;

		}

		this.sizeOfData = 0;

	}

	public void insert2(String key, Name name, Frequency fre) {
		
		  if (sizeOfData >= (size * 0.6)) { 
			  rehash(); 
			  }
		 

		int hash = getHash(key);
		if (table[hash] == null) {
			table[hash] = new Node(1, key, name, fre);
		} else {
			int c = 1;
			while (table[hash] != null && table[hash].status == 1) {
				hash = (hash + (c * (1 + getHash(key) % (size - 1)))) % size;
				c++;
			}
			table[hash] = new Node(1, key, name, fre);
		}
		sizeOfData++;

	}

	public void insert(String key, Name name, Frequency fre) {
		int h = search(key);
		if (h == -1) {
			
			insert2(key, name, fre);
		} else {
			
			table[h].heab.insert(fre);
		}
	}

	public void delete(String key) {
		int hash = getHash(key);
		int c = 1;
		while (table[hash] != null && table[hash].status != 2 && !table[hash].key.equals(key)) {
			hash = (hash + (c * (1 + getHash(key) % (size - 1)))) % size;
			c++;
		}

		if (table[hash] == null || table[hash].status == 2)
			System.out.println("no elemant");
		else {
			table[hash] = null;

		}

	}

	public int search(String key) {
		int hash = getHash(key);
		int c = 1;
		while (table[hash] != null && table[hash].status != 2 && !table[hash].key.equals(key)) {
			hash = (hash + (c * (1 + getHash(key) % (size - 1)))) % size;
			c++;
		}

		if (table[hash] == null || table[hash].status == 2)
			return -1;
		else {

			return hash;

		}
	}

	private int getHash(String str) {
		int result = 0;

		for (int i = 0; i < str.length(); i++) {
			result = (result << 5) + str.charAt(i);
		}

		result %= size;
		if (result < 0) {
			result += size;
		}

		return result;
	}

/*	private int getHash1(String str) {
		int result = 0;

		for (int i = 0; i < str.length(); i++) {
			result = (result << 5) + str.charAt(i);
		}

		result %= (size - 1);
		if (result < 0) {
			result += size;
		}

		return result;
	}*/

	public void rehash() {
		Hash newHash = new Hash(nextPrime(size * 2));

		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && table[i].status == 1) {
				newHash.insert2(table[i].key, table[i].name, table[i].heab.Heap[0]);
				for (int j = 1; j < table[i].heab.Heap.length; j++) {
					table[i].heab.insert(table[i].heab.Heap[j]);
				}
			}
		}

		size = newHash.size;
		table = newHash.table;

	}

	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		while (!isPrime(n))
			n += 2;

		return n;
	}

	private static boolean isPrime(int n) {

		if (n % 2 == 0 || n == 1)
			return false;

		if (n == 2 || n == 3)
			return true;

		for (int i = 3; i * i <= n / 2; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}
	public void empty() {
		for (int i = 0; i < table.length; i++) {
			table[i] =null;
		}
		sizeOfData=0;
		size=0;
	}
	public void print() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && table[i].status == 1)
				System.out.println(table[i].key + " " + i + " " + table[i].heab.print());
		}
	}

}
