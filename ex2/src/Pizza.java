import java.util.ArrayList;

public class Pizza{
	private String sabor, tamanho;
	private ArrayList<String> adicionais;
	private double valor;
	
	public Pizza(){
		this.sabor = "";
		this.tamanho = "";
		this.adicionais = new ArrayList<String>();
		this.valor = 0;
	}
	
	public void setSabor(String sabor){ this.sabor = sabor; }
	
	public String getSabor(){ return this.sabor; }
	
	public void setTamanho(String tamanho){ this.tamanho = tamanho; }
	
	public String getTamanho(){ return this.tamanho; }
	
	public void setAdicionais(ArrayList<String> adicionais){ this.adicionais = adicionais; }
	
	public ArrayList<String> getAdicionais(){ return adicionais; }
	
	public boolean setValor(double valor){
		if(valor <= 0)
			return false;
		
		this.valor = valor;
		return true;
	}
	
	public double getValor(){ return this.valor; }
	
	@Override
	public String toString(){
		String retorno = "\u2022 Pizza " + this.sabor + " tamanho " + this.tamanho.toLowerCase();
		
		if(this.adicionais.size() > 0){
			String adicionaisStr = "";
			for(int i = 0; i < this.adicionais.size(); i++){
				if(i > 0)
					adicionaisStr += i == this.adicionais.size() - 1? " e " : ", ";
				
				adicionaisStr += this.adicionais.get(i);
			}
			
			boolean plural = this.adicionais.size() > 1;
			
			retorno += " com o"+(plural? "s" : "")+" adiciona"+(plural? "is" : "l")+" de " + adicionaisStr;
		}
		
		retorno += " - Preço: R$ "+String.format("%.2f", this.valor);
		
		return retorno;
	}
}
