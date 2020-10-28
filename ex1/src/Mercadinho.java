import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;

class Mercadinho extends JPanel{
	protected ArrayList<String> produtos;
	protected Hashtable<String,Double> produtosValores;
	protected Hashtable<String,JCheckBox> produtosCheckBox;
	protected Hashtable<String,JTextField> produtosTextField;
	
	protected JLabel lbResult;
	
	public Mercadinho(){
		JFrame root = new JFrame("Mercadinho Unip");
		
		root.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		root.setSize(400, 375);
		root.setResizable(false);
		
		root.getContentPane().add(this, BorderLayout.CENTER);
		
		this.produtos = new ArrayList<String>(Arrays.asList("Batata", "Cenoura"
														   ,"Cebola", "Beterraba"
														   ,"Pepino", "Pimentão"
														   ,"Tomate"));
		
		this.produtosValores = new Hashtable<String, Double>();
		
		double[] valores = new double[]{1.5, 2, 3.1, 2.35, 1.85, 3, 3.5}; 
		for(int i = 0; i < this.produtos.size(); i++)
			this.produtosValores.put(this.produtos.get(i), valores[i]);
		
		this.produtosCheckBox = new Hashtable<String, JCheckBox>();
		this.produtosTextField = new Hashtable<String, JTextField>();	
		
		this.setup();
		root.setVisible(true);
	}
	
	public static void main(String[] args){
		Mercadinho mer = new Mercadinho();
	}
	
	private void setup(){
		JPanel topPanel = new JPanel(new GridLayout(this.produtos.size()+1, 3));
		
		topPanel.add(new Label("Produtos"));
		topPanel.add(new Label("Valor"));
		topPanel.add(new Label("Quantidade (kg)"));
		
		JCheckBox chkAux;
		JTextField txtAux;
		for(String prod : this.produtos){
			chkAux = new JCheckBox(prod);
			topPanel.add(chkAux);
			this.produtosCheckBox.put(prod, chkAux);
			
			topPanel.add( new Label( String.format( "%.2f", this.produtosValores.get(prod) ) ) );
			
			txtAux = new JTextField();
			topPanel.add(txtAux);
			this.produtosTextField.put(prod, txtAux);
		}
		
		this.add(topPanel);
		
		JPanel botPanel = new JPanel(new GridLayout(2,1));
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActListenCalculaTotal());
		botPanel.add(btnComprar);
		
		this.lbResult = new JLabel();
		this.lbResult.setVisible(false);
		botPanel.add(this.lbResult);
		
		this.add(botPanel);
	}
	
	private class ActListenCalculaTotal implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			try{
				double sum = 0;
				for(String prod : produtos){
					if(produtosCheckBox.get(prod).isSelected()){
						String text = produtosTextField.get(prod).getText().trim();
						double quantidade = text.isEmpty() ? 0 : Double.parseDouble(text);
						
						sum += produtosValores.get(prod) * quantidade;
					}
				}
				
				for(String prod: produtos){
					produtosCheckBox.get(prod).setSelected(false);
					produtosTextField.get(prod).setText("");
				}
				
				lbResult.setText(String.format("Total a pagar: %.2f", sum));
			}
			catch(NumberFormatException ex){
				lbResult.setText("O valor preenchido na quantidade em quilos é inválido");
			}
			
			lbResult.setVisible(true);
		}
	}
	
}

