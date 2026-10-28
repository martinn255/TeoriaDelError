import Tp2PruebaDelError.CambioBase;
import Tp2PruebaDelError.ConversionNumero;
import Tp3CambioRaiz.*;

public class Principal {
    public static void main(String[] args)  {
        //System.out.println("Hello, World!");

        //nuevo.listaDig("45f");

        //nuevo.sumaPonderada(5,"34");
        //nuevo.divisionReiterada(2, 8);
        //System.out.println(nuevo.listaDig("12af").toString());

        ConversionNumero numero = new ConversionNumero("1AB", 16);

        numero.listaDig();

        numero.convertirNumeroDecimal();
        numero.convertirNumero(8);
        numero.mostrarNumero();

        CambioBase test = new CambioBase();

        test.normalizarNumero(121.25,-1);

        test.desnormalicion(0.12125, -2);
        //ACtualizacion

        Metodos aux = new Metodos(x -> x * x * x - x - 2);

        double raiz = aux.Biseccion(1, 2, 0.0001);
        System.out.println("Raiz aproximada: " + raiz);

        Metodos aux1 = new Metodos(x -> Math.exp(x) -3 );
        double raiz1 = aux1.Biseccion(0, 2, 0.0001);
        System.out.println("Raiz aprox: " + raiz1);
    }
}
