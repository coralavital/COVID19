package UI;

import java.util.Random;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;

public class VirusManagement {
	
	private static boolean[][] data;
	
	static {
		data = new boolean[VirusEnum.values().length][];
		for(int i = 0; i < data.length; i++) {
			data[i] = new boolean[VirusEnum.values().length];
			for(int j = 0; j < data.length; j++) {
				if(i != j)
					data[i][j] = false;
				else
					data[i][j] = true;
			}
		}
	}

	public static void toogle(int i, int j) {
		data[i][j] =! data[i][j];
	}

	public static IVirus contagion(IVirus src)  {
		int index = VirusEnum.findv(src);
		IVirus v = findmutation(data[index]);
		return v;
	}
	
	public static IVirus findmutation(boolean[] data) {
		int size = 0;
		int[] indexes = null;
		for(int i = 0; i < data.length; i++) {
			if(data[i]) {
				size++;
			}
		}
		
		indexes = new int[size];
		int j = 0;
		for(int i = 0; i < size; i++) {
			if(data[i]) {  
				indexes[j] = i;
				j++;
			}
		}
		Random rand = new Random();
		int x = rand.nextInt(size);

		return VirusEnum.values()[indexes[x]].getv();
	}

	public static boolean getval(int i, int j) {
		return data[i][j];
	}

	public enum VirusEnum {
		
		BritishVariant("BritishVariant", new BritishVariant()), ChineseVariant("ChineseVariant", new ChineseVariant()),
		SouthAfricanVariant("SouthAfricanVariant", new SouthAfricanVariant());

		private final String string;
		private final IVirus v;

		VirusEnum(String string, IVirus v) {
			this.string = string;
			this.v = v;
		}

		public static int findv(IVirus v) {
			for (int i = 0; i < VirusEnum.values().length; i++) {
				if (VirusEnum.values()[i].v == v)
					return i;
			}
			return -1;
		}

		public String toString() {
			return string;
		}
		
		public IVirus getv() {
			return v;
		}
	}

}