package Tp2PruebaDelError;
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
        //Hola mundo 
        
    }
    //Combierte la cadenda en un array list de digitos del string
    
    //Creo que ya esta completo
    
    public void normalizarNumero(double num,int exp){
        double aux=num;
        boolean band =false;
        int exponente = exp;
        while(!band) {

            if(aux>=1){
                aux = aux /10;
                exponente++;
                if(aux<1) band=true;
            }else{
                if(aux<= 0.1){
                    aux=aux*10;
                    exponente--;
                    if(aux>=0.1) band=true;
                }
            }
        }
        System.out.println("-------------------Normalizacion---------------------");
        System.out.println("Numero: " + aux + "x10^" + exponente);

    }
    //Nota esto solo sirve para pasar cada exponenete a 0 no lo desnormaliza
    public void desnormalicion(double num,int exp){
        double aux=num;
        while(exp!=0){
            if(exp>0){
                aux=aux*10;
                exp--;
            }else{
                aux=aux/10;
                exp++;
            }
        }
        
        //System.out.println("Valor: " + no);
        System.out.println("-------------------DesNormalizacion---------------------");
        System.out.println("Numero: " + aux + "x10^" + exp );

    }
    //commiteo

    //testeo
    public int getBase() {
        return 1;
    }


    /// teste de brancheo

}
