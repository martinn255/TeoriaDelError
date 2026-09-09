package Tp3CambioRaiz;
public class Metodos {
    double a;
    double b;
    double funcion;
    public Metodos(){

    }  
    public double Biseccion(double funcion,double a ,double b,double e){ 
        double c=0;
        if(funcion*funcion>0){
            System.out.println("Salgo de la Operacion");
            return 0;
        }
        c=(a+b)/2;

        while(funcion>e){
            if(funcion*funcion<0){
                b=c;
            }else{
                a=c;
            }
            c=(a+b)/2;
        }
        return c;
    }
    public double Regula_Falsi(double funcion,double a,double b,double e){
        double c=0;
        if(funcion*funcion>0){
            return 0;
        }
        c=(a*funcion-b*funcion)/(funcion-funcion);
        while(funcion>e){
            if(funcion*funcion<0){
                b=c;
            }else{
                a=c;
            }
            c=(a*funcion-b*funcion)/(funcion-funcion);
        }
        return c;
    }
    public double Regula_FalsiModificada(double funcion,double a,double b,double e){
        double c=0;
        if(funcion*funcion>0){
            return 0;
        }
        double auxA=funcion,w=funcion;
        double auxB=funcion;
        c=(a*funcion-b*funcion)/(funcion-funcion);
        while(funcion>e){
            if(funcion*funcion<0){
                b=c;
                auxB=funcion;
                if(w*auxB>0){
                    auxA=auxA/2;
                }
            }else{
                
            }
        }
        return c;
    }
}

