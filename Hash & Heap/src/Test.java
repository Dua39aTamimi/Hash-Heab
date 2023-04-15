import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hash h = new Hash(1000);
		File files = new File("C:\\Users\\tamim\\Desktop\\1");
		File filesList[] = files.listFiles();
		Scanner sc = null;

		for (File file : filesList) {

			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				String[] line = s.split(",");

				char[] m = file.getName().toCharArray();
				String g = "" + m[m.length - 8] + m[m.length - 7] + m[m.length - 6] + m[m.length - 5];

				int year = Integer.parseInt(g);

				h.insert(line[0] + "," + line[1], new Name(line[0], line[1].charAt(0)),
						new Frequency(year, Integer.parseInt(line[2])));
				
				System.out.println();
				int q = h.search(line[0] + "," + line[1]);
				if (q != -1) {
					Node n = h.table[h.search(line[0] + "," + line[1])];
					if (n != null)
						System.out.println(n);
				}
			}

		}

		h.print();
		// int r=h.search("Saja,F");

		// System.out.println(h.table[r]+" "+h.table[r].heab.extractMax());
	}

}
