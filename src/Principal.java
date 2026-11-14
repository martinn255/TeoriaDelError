import Tp2PruebaDelError.CambioBase;
import Tp2PruebaDelError.ConversionNumero;
import Tp3CambioRaiz.*;
import java.math.*;
import java.util.ArrayList;

public class Principal {
    public static void main(String[] args)  {
        //System.out.println("Hello, World!");

        //CambioBase nue =new CambioBase();
        //nue.sumaPonde("AA,1A", 16);
        System.out.println("-----------------------");
        ConversionNumero b1 = new ConversionNumero("-AA,1A", 16, 12,10);
        //b1.norConversion();
        b1.norConversion(CambioBase.Modo.SIMETRICO,6);
        System.out.println("--------------------");
        ConversionNumero b2 = new ConversionNumero("0,1", 3, 10,50);
        //b2.norConversion();
        System.out.println("--------------------");
        ConversionNumero b3 = new ConversionNumero("12", 3, 2,10);
        //b3.norConversion();
        b3.norConversion(CambioBase.Modo.SIMETRICO,2);
        System.out.println("--------------------");
        ConversionNumero b4 = new ConversionNumero("1,89", 10, 3, 10);
        //b4.norConversion();
        System.out.println("--------------------");
        ConversionNumero b5 = new ConversionNumero("9999999.999999", 10, 10, 10);
        //b5.norConversion();
        b5.norConversion(CambioBase.Modo.CORTE,4);
        System.out.println();
    }
}
