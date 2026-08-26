import java.math.*;
public class CambioBase {
    private int base;
    private double num;
    public CambioBase(){
       

    }
    //Todos los valores tienen que estar en punto flotante Normalizado y sin normalizar
    public void sumaPonderada(int base, double num){
        double aux = num;
        System.out.println(aux);
        System.out.println(base);
        int dig=0;
        int i=0;
        int suma=0;
        while (aux!=0 && dig<base){
            
            dig=(int)aux%10;
            System.out.println("Digito:" + dig );
            aux=(int)aux/10;
            suma = (int) (dig * Math.pow((double)base, (double)i) + suma);
            //System.out.println("Suma Final:" + suma);
            i++;
        }
        if(dig>=base){
            System.out.println("Error En numero No corresponde a la base");
        }else{
            System.out.println("Suma Final:" + suma);
        }
        
    }
    public void divisionReiterada(int base, double num){

    }
    public void multiplicacionReiterada(int base,double num){

    }

    //testeo
    public int getBase() {
        return base;
    }


    /// teste de brancheo

}
