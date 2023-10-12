import java.util.Hashtable;

public interface DictionaryADT {
	
	public Hashtable<Integer, String> getDictionary();
	
	public int size();
	
	public String find(int key);
	
	public void insert(int key, String value);
}
