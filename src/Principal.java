import Tp2PruebaDelError.Calculadora;
import Tp2PruebaDelError.CambioBase;
import Tp2PruebaDelError.ConversionNumero;
import Tp2PruebaDelError.Normalizado;
import Tp3CambioRaiz.*;
import java.math.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        b5.norConversion(CambioBase.Modo.SIMETRICO,4);
        System.out.println("--------------------------");
        ConversionNumero b6 = new ConversionNumero("0.9999999", 16, 10, 10);
        b6.norConversion(CambioBase.Modo.SIMETRICO, 10);
        System.out.println();
        
        //Si Activas este lee las instrucciones sino usa manual abajo
        //modoInteractivo();

        Calculadora calc = new Calculadora(4); // t = 4
         
        calc.declarar("a", "7221", 10);
        calc.declarar("b", "0.004275", 10);
        calc.declarar("c", "7.645", 10);

        // (a*(b/c)) 
        System.out.println("\n=== OPERACIÓN 1: (a*(b/c)) ===");
        calc.mostrarResultado(calc.evaluar("(a*(b/c))"));

        // ((a+b)+c)
        System.out.println("\n=== OPERACIÓN 2: (a+b) + c ===");
        calc.mostrarResultado(calc.evaluar("((a+b)+c)"));

        // d no existe error
        System.out.println();
        try {
            calc.evaluar("(a+d)");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());   // Variable no definida: d
        }

        // declaro d después y ya se puede usar
        calc.declarar("d", "2.5", 10);
        System.out.println("\n=== OPERACIÓN 3: ((a+b) + (c+d)) ===");
        calc.mostrarResultado(calc.evaluar("((a+b)+(c+d))"));
        

        /* 
        //Esto es Ingreso Simple osea manual solo resuelve una operacion
        Calculadora.NumeroCalc a = calc.ingresarNumero("7221",10);
        Calculadora.NumeroCalc b = calc.ingresarNumero("0.004275",10);
        Calculadora.NumeroCalc c = calc.ingresarNumero("7.645",10);

        //System.out.println("Pantalla b: " + b.aproximado); // Muestra 0.004276 (o similar)
        //System.out.println("Exacto b: " + b.exacto);       // Muestra 0.00427567688668

        // 2. Operar (a + b)
        System.out.println("\n=== OPERACIÓN 1: a + b ===");
        Calculadora.NumeroCalc ab = calc.operar(a, b, '+');
        calc.mostrarResultado(ab);

        // 3. Operar (ab + c)
        System.out.println("\n=== OPERACIÓN 2: (a+b) + c ===");
        Calculadora.NumeroCalc z = calc.operar(ab, c, '+');
        calc.mostrarResultado(z);

        // 4. Operar ((a-b) - c)
        System.out.println("\n=== OPERACIÓN 3: (a-b) - c ===");
        Calculadora.NumeroCalc aMb = calc.operar(a, b, '-');
        Calculadora.NumeroCalc f = calc.operar(aMb, c, '-');
        calc.mostrarResultado(f);
        */
    }
    /*
    Ejemplo de uso
        Cantidad de dígitos de la mantisa (t): 4
        > def a 7221 10
        > def b 0.004275 10
        > def c 7.645 10
        > (a+b)
        > ((a+b)+c)
        > ((a+b)+(c+d))
        Error: Variable no definida: d
        > def d 1A.8 16
        > ((a+b)+(c+d))
        > z = (a-b)-c
        > vars
        > salir
     */




    public static void modoInteractivo() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Cantidad de dígitos de la mantisa (t): ");
        int t = Integer.parseInt(sc.nextLine().trim());
        Calculadora calc = new Calculadora(t);

        System.out.println("\nComandos:");
        System.out.println("  def <nombre> <numero> <base>   declarar variable con def  ej: def a -AA.1A 16");
        System.out.println("  <nombre> = <expresion>         guardar resultado          ej: z = (a+b)+c");
        System.out.println("  <expresion>                    resolver abs y relativo    ej: ((a+b)+(c+d))");
        System.out.println("  vars                           listar variables todas[a,b,...]");
        System.out.println("  salir\n"); 

        while (true) {
            System.out.print("> ");
            String linea = sc.nextLine().trim();

            if (linea.isEmpty()) continue;
            if (linea.equalsIgnoreCase("salir")) break;

            try {
                if (linea.equalsIgnoreCase("vars")) {
                    calc.mostrarVariables();

                } else if (linea.toLowerCase().startsWith("def ")) {
                    String[] p = linea.split("\\s+");
                    if (p.length != 4)
                        throw new IllegalArgumentException("Uso: def <nombre> <numero> <base>");
                    int base = Integer.parseInt(p[3]);
                    Calculadora.NumeroCalc n = calc.declarar(p[1], p[2], base);
                    System.out.println(p[1] + " = " + n.aproximado + "  (exacto: " + n.exacto + ")");

                } else if (linea.contains("=")) {
                    String[] p = linea.split("=", 2);
                    String nombre = p[0].trim();
                    Calculadora.NumeroCalc r = calc.evaluar(p[1].trim());
                    calc.asignar(nombre, r);
                    System.out.println(nombre + " = " + r.aproximado + "  (exacto: " + r.exacto + ")");

                } else {
                    calc.mostrarResultado(calc.evaluar(linea));
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: base o número mal escrito");
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
