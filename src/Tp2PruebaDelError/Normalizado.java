package Tp2PruebaDelError;

public class Normalizado {
    private boolean negativo;
    private String mantisa;
    private int exponente;
    private int base;
    private boolean exacta; //false => el número tiene más dígitos que los mostrados
    
    public Normalizado(boolean negativo,String mantisa,int exponente,int base,boolean exacta){
        this.negativo = negativo;
        this.mantisa = mantisa;
        this.exponente = exponente;
        this.base = base;
        this.exacta = exacta;
    }

    public boolean isNegativo() {return negativo;}
    public String getMantisa() {return mantisa;}
    public int getExponente() {return exponente;}
    public int getBase() {return base;}
    public boolean isExacta() {return exacta;}

    
    
}
