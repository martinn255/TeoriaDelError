import java.math.*;
import java.util.ArrayList;

public class CambioBase {
    
    private ArrayList<Integer> valorEnDig;
    private double valorReal;
    
    public CambioBase(){
        //this.valorEnDig.listaDig(num); 
        //this.valorReal=listANumero();
    }
    //Todos los valores tienen que estar en punto flotante Normalizado y sin normalizar
    public double sumaPonderada(ArrayList<Integer> num, int base){
        int dig=0,i=0;
        double suma=0;
        ArrayList<Integer> aux=num;
       
        i=aux.size()-1;//posicion del vector
        double exp=0;
        while(i>=0 && dig<base){
            dig=aux.get(i);
            //System.out.println("Digito:" + dig );
            suma =(dig * Math.pow((double)base, exp) + suma);
            i--;
            exp++;
        }
        if(dig>=base){
            System.out.println("Error: ln numero no corresponde a la base");
        }else{
            System.out.println("Suma en base 10:" + suma);
            return suma;
        }

        return -1;

        
    }
    public double divisionReiterada(double num, int base) {
        int aux = (int) num;
        ArrayList<Integer> arrayAux = new ArrayList<>();
        int resto;

        while (aux > 0) {
            resto = (int) aux%base;
            //System.out.println("resto: " + resto);
            arrayAux.add(resto);
            aux = (int)aux/base;
            //System.out.println("aux: " +aux);
        }

        //System.out.println(arrayAux.toString());

        int index = arrayAux.size() - 1;
        double convertido = 0;

        while(index >= 0) {
            convertido = convertido * 10 + arrayAux.get(index);
            //System.out.println("convertido: " + convertido);
            index--;
        }
        System.out.println("convertido " + num + " a base " + base + ": " + convertido);

        return convertido;
    }
    public void multiplicacionReiterada(int base,double num){

    }
    //Combierte la cadenda en un array list de digitos del string
    

    public void normalizarNumero(double num){
        double aux=num;
        boolean band = false;
        int exponente = 0;
        while(aux > 0 && !band) {
            if(aux < 1) {
                band = true;
            } else {
                aux = aux / 10;
                exponente++;
            }

        }
        System.out.println(aux + "x10^" + exponente);

    }

    //testeo
    public int getBase() {
        return 1;
    }


    /// teste de brancheo

}
