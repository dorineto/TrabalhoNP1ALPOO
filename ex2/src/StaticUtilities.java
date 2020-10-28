import javax.swing.*;
import java.awt.*;

public class StaticUtilities{
	public static JLabel createJLabelWithFont(String txt, Font font, int horizontalAlign){
		JLabel aux = new JLabel(txt, horizontalAlign);
		aux.setFont(font);
		
		return aux;
	}
	
	public static JLabel createJLabelWithFont(Font font, int horizontalAlign){
		JLabel aux = new JLabel();
		aux.setHorizontalAlignment(horizontalAlign);
		aux.setFont(font);
		
		return aux;
	}
	
	public static JLabel createJLabelWithFont(String txt, Font font){
		JLabel aux = new JLabel(txt);
		aux.setFont(font);
		
		return aux;
	}
	
	public static JLabel createJLabelWithFont(Font font){
		JLabel aux = new JLabel();
		aux.setFont(font);
		
		return aux;
	}

}