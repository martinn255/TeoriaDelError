package Tp2PruebaDelError;
import java.util.ArrayList;

import Tp2PruebaDelError.CambioBase.Modo;

public class ConversionNumero {
    private String num;

    private int baseOrigen;
    private int baseDestino;
    private int t;
    private CambioBase cambioBase = new CambioBase();


    public ConversionNumero(String num, int baseOrigen,int baseDestino,int t) {
        cambioBase.validarBase(baseOrigen);
        cambioBase.validarBase(baseDestino);
        this.num = num.trim(); //1AB = 1
        this.baseOrigen = baseOrigen;
        this.baseDestino = baseDestino;
        this.t=t;
        cambioBase.convPartes(num, baseOrigen);
        String ent = cambioBase.getParteEnt() , frac = cambioBase.getParteFrac();
        String signo = cambioBase.isEsNeg() ? "-" : "";
        String numvv = ent.isEmpty() ? "0" : ent;
        if (!frac.isEmpty()) numvv += "." + frac;
        System.out.println("NUM:" + signo + numvv + ")" + baseOrigen);

        if (baseOrigen == 10) {
            cambioBase.cargarDesdeDecimal();           // NUEVO: normaliza valorReal a "1.89"
        } else {
            cambioBase.sumaPonde(baseOrigen, t);       // convierte a decimal
        }

        System.out.println("valorBase: " + cambioBase.getValorReal() + ")10");

        // ---- Base 10 -> baseDestino ----
        if (baseDestino != 10) {
            convertirNumero(baseDestino, t);
        }else{
            System.out.println("conversion a dicha Base: " + cambioBase.getValorReal() + ")10");
        }
        //cambioBase.sumaPonde(baseOrigen,t);
        //System.out.println("valorBase: " + cambioBase.getValorReal() + ")10");
        //convertirNumero(baseDestino,t);
    }


    public void convertirNumero(int base,int t) {
        //if(base!=10){
            String valor = cambioBase.conversionDivMul(base,t);
            System.out.println("Conversion a dicha Base: " + valor + ")" + base);
        //}else{
            //System.out.println("conversion a dicha Base: " + cambioBase.getValorReal() + ")10");
        //}
        
    }
    /* 
    public void norConversion(){
        //int t = this.t + cambioBase.getParteEnt().length();
        int t = this.t;
        int base = this.baseDestino;
        String ent = cambioBase.divRei(base);

        int sigEnt = ent.equals("0") ? 0 : ent.length();
        int fracLen = cambioBase.getParteFrac().length();
        int holgura = 10;
        int pedir = t + fracLen + holgura;

        String dec = cambioBase.multiRei(base, pedir);
        Normalizado n = cambioBase.normalizar(ent, dec, cambioBase.isEsNeg(), base,  t + sigEnt);

        System.out.println("Normalizado: " + (n.isNegativo() ? "-" : "") + "0." + n.getMantisa() + " x " + base + "^" + n.getExponente() + (n.isExacta() ? "" : " (truncado)"));
        System.out.println("Desnormalizado: " + cambioBase.desnormalizar(n));
    }*/
   

    public void norConversion(Modo modo, int tCorte) {
        int base = this.baseDestino;
        String ent = cambioBase.divRei(base);
        int sigEnt = ent.equals("0") ? 0 : ent.length();

        // 1. Cuántos dígitos fraccionarios generar de la base destino
        int fracLen = cambioBase.getParteFrac().length();
        int holgura = 10;
    
        // Pedimos los suficientes para el corte/redondeo (tCorte), 
        // o los que pida this.t (fraccionario), el que sea mayor.
        int necesarios = Math.max(this.t, tCorte) + 1; // +1 para tener dígito de decisión en redondeo
        int pedir = Math.max(0, necesarios - sigEnt) + fracLen + holgura;

        String dec = cambioBase.multiRei(base, pedir);

        // 2. Mostramos cómo queda el número SIN cortar (opcional, para ver el completo)
        Normalizado nCompleto = cambioBase.normalizar(ent, dec, cambioBase.isEsNeg(), base, 9999, Modo.CORTE); // 9999 = no cortar
        System.out.println("Completo normalizado: " + (nCompleto.isNegativo() ? "-" : "") + "0."
            + nCompleto.getMantisa() + " x " + base + "^" + nCompleto.getExponente());

        // 3. Aplicamos el corte o redondeo real
        Normalizado nCortado = cambioBase.normalizar(ent, dec, cambioBase.isEsNeg(), base, tCorte, modo);

        System.out.println("[" + modo + "] Normalizado: " + (nCortado.isNegativo() ? "-" : "") + "0."
            + nCortado.getMantisa() + " x " + base + "^" + nCortado.getExponente()
            + (nCortado.isExacta() ? "" : " (hay más dígitos)"));
        System.out.println("[" + modo + "] Desnormalizado: " + cambioBase.desnormalizar(nCortado));
    }


    public int getBaseOrigen() {return baseOrigen;}
    
    public int getBaseDestino() {return baseDestino;}



    public void mostrarNumero() {
        
    }

   


    public String toString() {

        return "";
    }

}
