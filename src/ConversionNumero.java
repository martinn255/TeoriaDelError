import java.util.ArrayList;

public class ConversionNumero {
    private String num;
    private double numeroConvertido;
    private int base;
    private CambioBase cambioBase;


    public ConversionNumero(String num, int base) {
        this.num = num; //1AB = 1
        this.base = base;
        cambioBase = new CambioBase();

    }



    public void convertirNumeroDecimal() {
        this.numeroConvertido = cambioBase.sumaPonderada(listaDig(), this.base);
    }

    public void convertirNumero(int base) {
        this.base = base;
        this.numeroConvertido = cambioBase.divisionReiterada(numeroConvertido, base);
    }




    public int getBase() {
        return base;
    }

    //Combierte la cadenda en un array list de digitos del string
    public ArrayList<Integer> listaDig(){
        ArrayList<Integer> aux = new ArrayList<>();
        char cad;
        String cadena = num;
        int i;
        for( i=0; i<cadena.length();i++){
            cad=cadena.charAt(i);
            aux.add(Character.getNumericValue(cad));
        }

        System.out.println(aux.toString());
        return aux;
    }

    public void mostrarNumero() {
        System.out.println(numeroConvertido);
    }

    public double listANumero(){
        return 1;
    }


    public String toString() {

        return "";
    }

}
