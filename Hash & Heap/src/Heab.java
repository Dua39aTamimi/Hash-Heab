
public class Heab {

	public Frequency[] Heap;
	public int size;
	public int maxsize;

	public Heab(int size) {
		this.maxsize = size;
		this.size = 0;
		Heap = new Frequency[this.maxsize + 1];
		Heap[0] = new Frequency(0,Integer.MAX_VALUE);

	}

	public int parent(int pos) {
		return pos / 2;
	}

	public int leftChild(int pos) {
		return (2 * pos);
	}

	public int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	public void swap(int fpos, int spos) {
		Frequency tmp;
		tmp = Heap[fpos];
		Heap[fpos] = Heap[spos];
		Heap[spos] = tmp;
	}

	public void downHeapify(int pos) {
		if (pos >= (size / 2) && pos <= size)
			return;

		if (Heap[pos].fre < Heap[leftChild(pos)].fre || Heap[pos].fre < Heap[rightChild(pos)].fre) {

			if (Heap[leftChild(pos)].fre > Heap[rightChild(pos)].fre) {
				swap(pos, leftChild(pos));
				downHeapify(leftChild(pos));
			} else {
				swap(pos, rightChild(pos));
				downHeapify(rightChild(pos));
			}
		}
	}

	public void heapifyUp(int pos) {
		Frequency temp = Heap[pos];
		while (pos > 0 && temp.fre > Heap[parent(pos)].fre) {
			Heap[pos] = Heap[parent(pos)];
			pos = parent(pos);
		}
		Heap[pos] = temp;
	}

	public void insert(Frequency element) {
		Heap[++size] = element;

		int current = size;
		heapifyUp(current);

	}

	public String print() {
		String l = "";
		for (int i = 0; i <= size ; i++) {
			l += Heap[i].year + " " + Heap[i].fre + "\n";

		}
		return l;
	}

	public int extractMax() {
		int max = Heap[1].fre;
		Heap[1] = Heap[size--];
		downHeapify(1);
		return max;
	}
}
