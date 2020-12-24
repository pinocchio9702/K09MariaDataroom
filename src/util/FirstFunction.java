package util;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class FirstFunction {
	
	public static void srcGuGudan(JspWriter out) {
		try{
			out.println("<table border='1'>");
			for(int dan=2; dan<=9; dan++){
				out.println("	<tr>");
				for(int su=1; su<=9; su++){
					out.println("		<td>"+dan+"*" + su+ "=" + dan*su + "</td>");
				}
				out.println("	<tr>");				
			}
			out.println("</table>");
		}
		catch(IOException e){
			
		}
	}
	
	

	
	
	
	
	
	

	
	
}
