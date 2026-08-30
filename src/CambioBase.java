import java.math.*;
import java.util.ArrayList;

public class CambioBase {
    private int base;
    // private double num;
    public CambioBase(){
       

    }
    //Todos los valores tienen que estar en punto flotante Normalizado y sin normalizar
    public void sumaPonderada(int base, String num){
        //double aux = num;
        int dig=0,i=0;
        double suma=0;
        ArrayList<Integer> aux = listaDig(num);
        /*while (aux!=0 && dig<base){
            dig=(int)aux%10;
            aux=(int)aux/10;
            suma = (int) (dig * Math.pow((double)base, (double)i) + suma);
            i++;
        }*/
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
            System.out.println("Error En numero No corresponde a la base");
        }else{
            System.out.println("Suma Final:" + suma);
        }
        
    }
    public void divisionReiterada(int base, double num){
        int aux = (int) num;
        ArrayList<Integer> arrayAux = new ArrayList<>();
        int resto;

        while (aux > 0) {
            resto = (int) aux%base;
            System.out.println("resto: " + resto);
            arrayAux.add(resto);
            aux = (int)aux/base;
            System.out.println("aux: " +aux);
        }

        //System.out.println(arrayAux.toString());

        int index = arrayAux.size() - 1;
        double convertido = 0;

        while(index >= 0) {
            convertido = convertido * 10 + arrayAux.get(index);
            //System.out.println("convertido: " + convertido);
            index--;
        }
        System.out.println("convertido: " + convertido);
    }
    public void multiplicacionReiterada(int base,double num){

    }
    public ArrayList<Integer> listaDig(String cadena){
        ArrayList<Integer> aux = new ArrayList<>();
        char cad;
        int i;
        for( i=0; i<cadena.length();i++){
            cad=cadena.charAt(i);
            aux.add(Character.getNumericValue(cad));
        }
        //System.out.println(aux.toString());
        return aux;
    }

    //testeo
    public int getBase() {
        return base;
    }


    /// teste de brancheo

}
