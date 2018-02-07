package io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestRegEx {

	@Test
	public void testRegex(){

		String txt="   { },{ },{ }   {2},{6},{ }   {7},{ },{1}";

		String re1=".*?";	// Non-greedy match on filler
		String re2=".";	// Uninteresting: c
		String re3=".*?";	// Non-greedy match on filler
		String re4=".";	// Uninteresting: c
		String re5=".*?";	// Non-greedy match on filler
		String re6=".";	// Uninteresting: c
		String re7=".*?";	// Non-greedy match on filler
		String re8=".";	// Uninteresting: c
		String re9="(.)";	// Any Single Character 1
		String re10=".*?";	// Non-greedy match on filler
		String re11=".";	// Uninteresting: c
		String re12=".*?";	// Non-greedy match on filler
		String re13=".";	// Uninteresting: c
		String re14=".*?";	// Non-greedy match on filler
		String re15=".";	// Uninteresting: c
		String re16=".*?";	// Non-greedy match on filler
		String re17="(.)";	// Any Single Character 2
		String re18=".*?";	// Non-greedy match on filler
		String re19=".";	// Uninteresting: c
		String re20=".*?";	// Non-greedy match on filler
		String re21=".";	// Uninteresting: c
		String re22=".*?";	// Non-greedy match on filler
		String re23=".";	// Uninteresting: c
		String re24=".*?";	// Non-greedy match on filler
		String re25="(.)";	// Any Single Character 3
		String re26=".*?";	// Non-greedy match on filler
		String re27=".";	// Uninteresting: c
		String re28=".*?";	// Non-greedy match on filler
		String re29=".";	// Uninteresting: c
		String re30=".*?";	// Non-greedy match on filler
		String re31=".";	// Uninteresting: c
		String re32=".*?";	// Non-greedy match on filler
		String re33=".";	// Uninteresting: c
		String re34=".*?";	// Non-greedy match on filler
		String re35=".";	// Uninteresting: c
		String re36=".*?";	// Non-greedy match on filler
		String re37="(.)";	// Any Single Character 4
		String re38=".*?";	// Non-greedy match on filler
		String re39=".";	// Uninteresting: c
		String re40=".*?";	// Non-greedy match on filler
		String re41=".";	// Uninteresting: c
		String re42=".*?";	// Non-greedy match on filler
		String re43=".";	// Uninteresting: c
		String re44=".*?";	// Non-greedy match on filler
		String re45="(.)";	// Any Single Character 5
		String re46=".*?";	// Non-greedy match on filler
		String re47=".";	// Uninteresting: c
		String re48=".*?";	// Non-greedy match on filler
		String re49=".";	// Uninteresting: c
		String re50=".*?";	// Non-greedy match on filler
		String re51=".";	// Uninteresting: c
		String re52=".*?";	// Non-greedy match on filler
		String re53="(.)";	// Any Single Character 6
		String re54=".*?";	// Non-greedy match on filler
		String re55=".";	// Uninteresting: c
		String re56=".*?";	// Non-greedy match on filler
		String re57=".";	// Uninteresting: c
		String re58=".*?";	// Non-greedy match on filler
		String re59=".";	// Uninteresting: c
		String re60=".*?";	// Non-greedy match on filler
		String re61=".";	// Uninteresting: c
		String re62=".*?";	// Non-greedy match on filler
		String re63=".";	// Uninteresting: c
		String re64=".*?";	// Non-greedy match on filler
		String re65="(.)";	// Any Single Character 7
		String re66=".*?";	// Non-greedy match on filler
		String re67=".";	// Uninteresting: c
		String re68=".*?";	// Non-greedy match on filler
		String re69=".";	// Uninteresting: c
		String re70=".*?";	// Non-greedy match on filler
		String re71=".";	// Uninteresting: c
		String re72=".*?";	// Non-greedy match on filler
		String re73="(.)";	// Any Single Character 8
		String re74=".*?";	// Non-greedy match on filler
		String re75=".";	// Uninteresting: c
		String re76=".*?";	// Non-greedy match on filler
		String re77=".";	// Uninteresting: c
		String re78=".*?";	// Non-greedy match on filler
		String re79=".";	// Uninteresting: c
		String re80=".*?";	// Non-greedy match on filler
		String re81="(.)";	// Any Single Character 9
		String re82="\\}";	// Any Single Character 10

		Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11+re12+re13+re14+re15+re16+re17+re18+re19+re20+re21+re22+re23+re24+re25+re26+re27+re28+re29+re30+re31+re32+re33+re34+re35+re36+re37+re38+re39+re40+re41+re42+re43+re44+re45+re46+re47+re48+re49+re50+re51+re52+re53+re54+re55+re56+re57+re58+re59+re60+re61+re62+re63+re64+re65+re66+re67+re68+re69+re70+re71+re72+re73+re74+re75+re76+re77+re78+re79+re80+re81+re82,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(txt);
		if (m.find())
		{
			String c1=m.group(1);
			String c2=m.group(2);
			String c3=m.group(3);
			String c4=m.group(4);
			String c5=m.group(5);
			String c6=m.group(6);
			String c7=m.group(7);
			String c8=m.group(8);
			String c9=m.group(9);
		
			System.out.print("("+c1.toString()+")"+"("+c2.toString()+")"+"("+c3.toString()+")"+"("+c4.toString()+")"+"("+c5.toString()+")"+"("+c6.toString()+")"+"("+c7.toString()+")"+"("+c8.toString()+")"+"("+c9.toString()+")"+"\n");
			System.out.println("The pattern is:");
			System.out.println(p.toString());
		}
	}

	@Test
	public void testPattern2(){
		   //String txt="   {true},{true},{true}   {false},{false},{true}   {false},{true},{false}";
			String txt = "    {false},{false},{true}   {true},{false},{true}   {true},{false},{true}";
		
		    String re1=".*?";	// Non-greedy match on filler
		    String re2="(true|false)";	// Word 1
		    String re3=".*?";	// Non-greedy match on filler
		    String re4="(true|false)";	// Word 2
		    String re5=".*?";	// Non-greedy match on filler
		    String re6="(true|false)";	// Word 3
		    String re7=".*?";	// Non-greedy match on filler
		    String re8="(true|false)";	// Word 4
		    String re9=".*?";	// Non-greedy match on filler
		    String re10="(true|false)";	// Word 5
		    String re11=".*?";	// Non-greedy match on filler
		    String re12="(true|false)";	// Word 6
		    String re13=".*?";	// Non-greedy match on filler
		    String re14="(true|false)";	// Word 7
		    String re15=".*?";	// Non-greedy match on filler
		    String re16="(true|false)";	// Word 8
		    String re17=".*?";	// Non-greedy match on filler
		    String re18="(true|false)";	// Word 9

	//	    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11+re12+re13+re14+re15+re16+re17+re18,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Pattern p = Pattern.compile(".*?(true|false).*?(true|false).*?(true|false).*"
					+ "?(true|false).*?(true|false).*?(true|false).*?(true|false).*"
					+ "?(true|false).*?(true|false)",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m = p.matcher(txt);
		    if (m.find())
		    {
		        String word1=m.group(1);
		        String word2=m.group(2);
		        String word3=m.group(3);
		        String word4=m.group(4);
		        String word5=m.group(5);
		        String word6=m.group(6);
		        String word7=m.group(7);
		        String word8=m.group(8);
		        String word9=m.group(9);
		        System.out.print("("+word1.toString()+")"+"("+word2.toString()+")"+"("+word3.toString()+")"+"("+word4.toString()+")"+"("+word5.toString()+")"+"("+word6.toString()+")"+"("+word7.toString()+")"+"("+word8.toString()+")"+"("+word9.toString()+")"+"\n");
			System.out.println("The pattern is:");
			System.out.println(p.toString());
		}
	}


}


