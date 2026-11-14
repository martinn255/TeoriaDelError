package Tp2PruebaDelError;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import Tp2PruebaDelError.CambioBase.Modo;
public class Calculadora {
    private int tMant;
    private CambioBase cb = new CambioBase();
    //esto es para ingresar (a+b ) y esas cosas
    private Map<String, NumeroCalc> variables = new HashMap<>();
    private String expr;
    private int pos;

    public Calculadora(int tMant){
        this.tMant = tMant;
    }
    public static class NumeroCalc {//Clase Interna 
        public String exacto;    // El valor real ingresado (ej: "0.00427567688668")
        public String aproximado; // El valor para pantalla (ej: "0.004275")

        public NumeroCalc(String exacto, String aproximado) {
            this.exacto = exacto;
            this.aproximado = aproximado;
        }
    }

    // 1. Al ingresar, guardamos AMBOS: el exacto y el que se muestra en pantalla
    public NumeroCalc ingresarNumero(String numExacto,int base) {
        cb.convPartes(numExacto, base);
        if (base == 10) {
            cb.cargarDesdeDecimal();           // NUEVO: normaliza valorReal a "1.89"
        } else {
            cb.sumaPonde(base, 10);       // convierte a decimal
        }
        System.out.println("Ingreso:" + numExacto);
        System.out.println("valorBase: " + cb.getValorReal() + ")10");
        
        String exa = cb.getValorReal();
        // Normalizamos para la pantalla
        Normalizado n = cb.normalizar(cb.getParteEnt(), cb.getParteFrac(), cb.isEsNeg(), 10, gettMant(), Modo.SIMETRICO);
        String aprox = cb.desnormalizar(n);
        
        return new NumeroCalc(exa, aprox);
    }
    // 2. Operar: La operación usa los EXACTOS para no perder precisión,
    // pero luego redondea el resultado.
    public NumeroCalc operar(NumeroCalc a, NumeroCalc b, char op) {
        BigDecimal valA = new BigDecimal(a.exacto);
        BigDecimal valB = new BigDecimal(b.exacto);
        BigDecimal exacto = BigDecimal.ZERO;

        switch (op) {
            case '+': exacto = valA.add(valB); break;
            case '-': exacto = valA.subtract(valB); break;
            case '*': exacto = valA.multiply(valB); break;
            case '/': exacto = valA.divide(valB, 20, RoundingMode.HALF_UP); break;
        }

        String exactoStr = exacto.stripTrailingZeros().toPlainString();

        // Redondeamos el resultado de la operación a t dígitos
        cb.convPartes(exactoStr, 10);
        cb.cargarDesdeDecimal();
        Normalizado n = cb.normalizar(cb.getParteEnt(), cb.getParteFrac(), cb.isEsNeg(), 10, gettMant(), Modo.SIMETRICO);
        String aproxStr = cb.desnormalizar(n);

        return new NumeroCalc(exactoStr, aproxStr);
    }

    // 3. Mostrar resultado final con errores
    public void mostrarResultado(NumeroCalc resultado) {
        BigDecimal exacto = new BigDecimal(resultado.exacto);
        BigDecimal aprox = new BigDecimal(resultado.aproximado);

        BigDecimal errorAbs = exacto.subtract(aprox).abs();
        BigDecimal errorRel = BigDecimal.ZERO;
        if (exacto.compareTo(BigDecimal.ZERO) != 0) {
            // Usamos el exacto de abajo como dice tu hoja
            errorRel = errorAbs.divide(exacto.abs(), 15, RoundingMode.HALF_UP);
        }

        System.out.println("Resultado exacto: " + resultado.exacto);
        System.out.println("Resultado en pantalla (t=" + gettMant() + "): " + resultado.aproximado);
        System.out.println("Error Absoluto: " + errorAbs.stripTrailingZeros().toPlainString());
        System.out.println("Error Relativo: " + errorRel.stripTrailingZeros().toPlainString());
    }
    
    public int gettMant() {
        return tMant;
    }


    //esta parte Recien trabaja con la descomposicion del (a*b+c-d) y asi 
    // ===== Evaluador de expresiones ===== Sacado de Cloud

    public NumeroCalc declarar(String nombre, String numero, int base) {
        if (!nombre.matches("[a-zA-Z_][a-zA-Z0-9_]*"))
            throw new IllegalArgumentException("Nombre de variable inválido: " + nombre);
        NumeroCalc n = ingresarNumero(numero, base);
        variables.put(nombre, n);
        return n;
    }

    // Para guardar un resultado ya calculado, ej. z = (a+b)+c
    public void asignar(String nombre, NumeroCalc valor) {
        variables.put(nombre, valor);
    }


    public NumeroCalc evaluar(String expresion) {
        this.expr = expresion.replaceAll("\\s+", "");
        this.pos = 0;
        if (expr.isEmpty()) throw new IllegalArgumentException("Expresión vacía");
        NumeroCalc r = parseSumaResta();
        if (pos < expr.length())
            throw new IllegalArgumentException("Carácter inesperado '" + expr.charAt(pos) + "' en posición " + pos);
        return r;
    }

    // suma y resta (menor precedencia)
    private NumeroCalc parseSumaResta() {
        NumeroCalc izq = parseMulDiv();
        while (pos < expr.length() && (expr.charAt(pos) == '+' || expr.charAt(pos) == '-')) {
            char op = expr.charAt(pos++);
            NumeroCalc der = parseMulDiv();
            izq = operar(izq, der, op);
        }
        return izq;
    }

    // multiplicación y división
    private NumeroCalc parseMulDiv() {
        NumeroCalc izq = parseFactor();
        while (pos < expr.length() && (expr.charAt(pos) == '*' || expr.charAt(pos) == '/')) {
            char op = expr.charAt(pos++);
            NumeroCalc der = parseFactor();
            izq = operar(izq, der, op);
        }
        return izq;
    }

    // variable o (subexpresión)
    private NumeroCalc parseFactor() {
        if (pos >= expr.length())
            throw new IllegalArgumentException("La expresión termina inesperadamente");

        char c = expr.charAt(pos);

        if (c == '(') {
            pos++;
            NumeroCalc r = parseSumaResta();
            if (pos >= expr.length() || expr.charAt(pos) != ')')
                throw new IllegalArgumentException("Falta cerrar paréntesis");
            pos++;
            return r;
        }

        if (Character.isLetter(c) || c == '_') {
            int ini = pos;
            while (pos < expr.length() && (Character.isLetterOrDigit(expr.charAt(pos)) || expr.charAt(pos) == '_')) pos++;
            String nombre = expr.substring(ini, pos);
            NumeroCalc v = variables.get(nombre);
            if (v == null)
                throw new IllegalArgumentException("Variable no definida: " + nombre);
            return v;
        }

        throw new IllegalArgumentException("Carácter inesperado '" + c + "' en posición " + pos);
    }

    //Esto es Para el Scanners
    public void mostrarVariables() {
        if (variables.isEmpty()) {
            System.out.println("(no hay variables declaradas)");
            return;
        }
        for (Map.Entry<String, NumeroCalc> e : variables.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue().aproximado
                    + "   (exacto: " + e.getValue().exacto + ")");
        }
    }
}
