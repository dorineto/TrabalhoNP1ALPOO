import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;

class Pizzaria extends JPanel{
	protected ArrayList<String> sabores;
	protected ArrayList<String> tamanhos;
	protected Hashtable<String,Double> saboresValores;
	protected Hashtable<String,JRadioButton> saboresRadioButton;
	
	protected ArrayList<String> adicionais;
	protected Hashtable<String,JCheckBox> adicionaisCheckBox;
	protected Hashtable<String,Double> adicionaisValores;
	
	protected ArrayList<Pizza> pedidos;
	
	protected JTextArea taPedidos;
	
	protected JLabel lblTotal;
	protected JButton btnFinalizarPedido;
	
	private Font fonteTitulo, fonteNormal;
	
	public Pizzaria(){
		JFrame root = new JFrame("Pizzaria");
		
		root.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		root.setLocationRelativeTo(null);
		root.setSize(500, 450);
		root.setResizable(false);
		
		this.fonteTitulo = new Font("Arial", Font.BOLD, 18);
		this.fonteNormal = new Font("Arial", Font.PLAIN, 12);
		
		root.getContentPane().add(this, BorderLayout.CENTER);
		
		this.setupValues();
		this.setupUI();
		
		root.setVisible(true);
	}
	
	public static void main(String[] args){
		Pizzaria piz = new Pizzaria();
	}
	
	private void setupValues(){
		this.sabores = new ArrayList<String>(Arrays.asList("Pepperoni", "Mussarela", "Supreme"));
		
		this.tamanhos = new ArrayList<String>(Arrays.asList("Individual", "Regular", "Familia"));
		
		double[][] valoresSab = {{15, 27  , 33  },
							     {12, 21.6, 26.4},
							     {17, 30.6, 37.4}};
			
		this.saboresValores = new Hashtable<String, Double>();
		for(int i = 0; i < this.sabores.size(); i++){
			for(int j = 0; j < this.tamanhos.size(); j++){
				this.saboresValores.put(this.sabores.get(i)+this.tamanhos.get(j), valoresSab[i][j]);
			}
		}
										
		this.saboresRadioButton = new Hashtable<String, JRadioButton>();
										
		
		this.adicionais = new ArrayList<String>(Arrays.asList("Bacon" , "Cebola"  , "Champignon",
														      "Tomate", "Catupiry", "Presunto"  ));
		
		double[] valoresAdd = {2, 1.5, 2.5, 
							   1, 3  , 2.5};
							 
		this.adicionaisValores = new Hashtable<String, Double>();
		for(int i = 0; i < this.adicionais.size(); i++)
			this.adicionaisValores.put(this.adicionais.get(i), valoresAdd[i]);
		
		this.adicionaisCheckBox = new Hashtable<String, JCheckBox>();
		
		this.pedidos = new ArrayList<Pizza>();
	}
	
	private void setupUI(){
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(StaticUtilities.createJLabelWithFont("Pizzas", this.fonteTitulo, SwingConstants.CENTER), BorderLayout.NORTH);
		
		JPanel saboresPanel = new JPanel(new GridLayout(this.sabores.size()+1, this.tamanhos.size()+1));
		saboresPanel.add(StaticUtilities.createJLabelWithFont("Sabores", this.fonteNormal, SwingConstants.CENTER));
		
		for(String tamanho : this.tamanhos){
			saboresPanel.add(StaticUtilities.createJLabelWithFont(tamanho, this.fonteNormal, SwingConstants.CENTER));
		}
		
		ButtonGroup btnGroup = new ButtonGroup();
		JRadioButton rbAux;
		
		for(String sabor : this.sabores){
			saboresPanel.add(new Label(sabor));
			for(String tamanho : this.tamanhos){
				rbAux = new JRadioButton(String.format("R$ %.2f", this.saboresValores.get(sabor+tamanho)));
				rbAux.setFont(this.fonteNormal);
				
				btnGroup.add(rbAux);
				saboresPanel.add(rbAux);
				
				this.saboresRadioButton.put(sabor+tamanho, rbAux);
			}
		}
		
		this.saboresRadioButton.get(this.sabores.get(0)+this.tamanhos.get(0)).setSelected(true);
		
		topPanel.add(saboresPanel);
		
		this.add(topPanel);
		
		JPanel midPanel = new JPanel(new BorderLayout());
		
		midPanel.add(StaticUtilities.createJLabelWithFont("Adicionais", this.fonteTitulo, SwingConstants.CENTER), BorderLayout.NORTH);
		
		JPanel adicionaisPanel = new JPanel(new GridLayout((int)Math.ceil(this.adicionais.size() / 3), 3));
		
		JCheckBox chkAux;
		for(String adicional : this.adicionais){
			chkAux = new JCheckBox(adicional+" (R$ "+String.format("%.2f", this.adicionaisValores.get(adicional))+")");
			chkAux.setFont(fonteNormal);
			
			adicionaisPanel.add(chkAux);
			
			this.adicionaisCheckBox.put(adicional, chkAux);
		}
		
		midPanel.add(adicionaisPanel, BorderLayout.CENTER);
		this.add(midPanel);
		
		JPanel pedidosPanel = new JPanel(new BorderLayout());
		
		pedidosPanel.add(StaticUtilities.createJLabelWithFont("Pedido", this.fonteTitulo, SwingConstants.CENTER), BorderLayout.NORTH);
		
		this.taPedidos = new JTextArea(7,40);
		this.taPedidos.setEditable(false);
		this.taPedidos.setFont(this.fonteNormal);
		
		JScrollPane scrpPedidos = new JScrollPane(this.taPedidos);
		
		pedidosPanel.add(scrpPedidos, BorderLayout.CENTER);
		
		this.add(pedidosPanel);
		
		JPanel botPanel = new JPanel(new BorderLayout());
		
		Font fonteTotal = new Font(this.fonteNormal.getName(), Font.BOLD, 15);
		this.lblTotal = StaticUtilities.createJLabelWithFont("Total a pagar:", fonteTotal, SwingConstants.CENTER);
		
		botPanel.add(this.lblTotal, BorderLayout.NORTH);
		
		JPanel controlePanel = new JPanel();
		
		JButton btnAdicionarPedido = new JButton("Adicionar Pedido");
		btnAdicionarPedido.setFont(this.fonteNormal);
		btnAdicionarPedido.addActionListener(new ActListenAdicionarPedido());
		
		controlePanel.add(btnAdicionarPedido);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(this.fonteNormal);
		btnCancelar.addActionListener(new ActListenResetInputs("cancelar o pedido"));
		
		controlePanel.add(btnCancelar);
		
		this.btnFinalizarPedido = new JButton("Finalizar Pedido");
		this.btnFinalizarPedido.setFont(this.fonteNormal);
		this.btnFinalizarPedido.addActionListener(new ActListenResetInputs("finalizar o pedido"));
		
		this.btnFinalizarPedido.setVisible(false);
		
		controlePanel.add(this.btnFinalizarPedido);
		
		botPanel.add(controlePanel, BorderLayout.CENTER);
		
		this.add(botPanel);
	}
	
	protected void calculaTotal(){
		double sum = 0;
		for(Pizza pedido : this.pedidos)
			sum += pedido.getValor();
		
		this.lblTotal.setText("Total a pagar: "+(sum > 0 ? String.format("%.2f", sum) : ""));
	}
	
	protected void atualizaListaPedido(){
		String txtPedido = "";
		for(Pizza pedido : this.pedidos)
			txtPedido += pedido.toString() + "\n";
		
		this.taPedidos.setText(txtPedido);
	}
	
	private class ActListenAdicionarPedido implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(JOptionPane.showConfirmDialog(null, "Deseja adicionar esse pedido a lista?") != 0)
				return;
			
			Pizza pedido = new Pizza();
			
			double sum = 0;
			for(String sabor : sabores){
				for(String tamanho : tamanhos){
					if(saboresRadioButton.get(sabor+tamanho).isSelected()){
						sum += saboresValores.get(sabor+tamanho);
						
						pedido.setSabor(sabor);
						pedido.setTamanho(tamanho);
						
						break;
					}
				}
			}
			
			saboresRadioButton.get(sabores.get(0)+tamanhos.get(0)).setSelected(true);
			
			JCheckBox chkAux;
			for(String adicional : adicionais){
				chkAux = adicionaisCheckBox.get(adicional);
				if(chkAux.isSelected()){
					pedido.getAdicionais().add(adicional);
					sum += adicionaisValores.get(adicional);
				}
				
				chkAux.setSelected(false);
			}
			
			pedido.setValor(sum);
			pedidos.add(pedido);
			
			btnFinalizarPedido.setVisible(true);
			
			calculaTotal();
			atualizaListaPedido();
		}
	}
	
	private class ActListenResetInputs implements ActionListener{
		String acao;
		
		public ActListenResetInputs(String acao){
			this.acao = acao;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(JOptionPane.showConfirmDialog(null, "Deseja "+this.acao+"?") != 0)
				return;
			
			pedidos.clear();
			
			saboresRadioButton.get(sabores.get(0)+tamanhos.get(0)).setSelected(true);
			
			for(String adicional : adicionais)
				adicionaisCheckBox.get(adicional).setSelected(false);
			
			btnFinalizarPedido.setVisible(false);
			
			calculaTotal();
			atualizaListaPedido();
		}
	}
	
}

