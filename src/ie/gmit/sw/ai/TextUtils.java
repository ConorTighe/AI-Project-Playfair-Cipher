package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author Conor Tighe
 * Utility class for setting up the key
 */
public class TextUtils {

	/***
	 * For doing operations with the text before certain jobs
	 * @return Turn text all uppercase
	 * @return Prepare key for cipher under playfair rules, apply constants
	 * @return get text ready for playfair
	 * @return get list of diagrams
	 */
	
	// Does RegEx conversion for us
	public static void plainTextRegEx(String s) {
		s = s.toUpperCase().replaceAll("[^A-Za-z0-9 ]", "");
	}
	
	// get key ready 
	public static String prepareKey (String key)
	{
		StringBuilder sb = new StringBuilder();
		key=key.toUpperCase();
		for (char c : key.toCharArray())
		{
			if (Character.isUpperCase(c))
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	// get the text ready
	public static String prepareText(String s)
	{
		plainTextRegEx(s);
		StringBuilder sb = new StringBuilder();
		String ss=s.toUpperCase();
		char previous=' ';
		boolean pair=false;
		for (char c : ss.toCharArray())
		{
			if (Character.isUpperCase(c))
			{
				if (c==PlayfairConstants.EQUAL_CHAR2) c=PlayfairConstants.EQUAL_CHAR1;
				if (c==previous && pair)sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
				else pair=!pair;
				sb.append(c);
				previous=c;
			}
		}
		if (sb.length()%2!=0) sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
		return sb.toString();
	}
	// Get diagrams and prepare them for processing
	public static List<String> getDigraphs(String s)
	{
		List<String> toReturn = new ArrayList<String>();
		String q = prepareText(s);
		for (int i=0;i<q.length();i+=2) toReturn.add(q.substring(i, i+2));
		
		return toReturn;
	}
}