package Tp2PruebaDelError;
import java.math.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class CambioBase {
    private int BASE_MIN=2;
    private int BASE_MAX=16;
    private String valorReal;
    private String parteEnt="";
    private String parteFrac="";
    private boolean esNeg=false;
    
    public CambioBase(){
        //this.valorEnDig.listaDig(num); 
        //this.valorReal=listANumero();
        
    }
    public boolean valBaseNum(int dig,int base){ //Valida si el cada dig es menor a la base
        return dig <base;
    }
    public void validarBase(int base){ //Valida si las bases pertenecen a [2-16]
        if( base < BASE_MIN || base > BASE_MAX){
           throw new   IllegalArgumentException( "La base debe estar en el rango [" + "2" + ", " + "16" + "]: " + base);
        }
    }
    private void validarDigitos(String s, int base) {
        for (char c : s.toCharArray()) {
            if (Character.digit(c, base) < 0) {
                throw new IllegalArgumentException(
                    "Dígito inválido '" + c + "' para base " + base);
            }
        }
    }
    public void convPartes(String valor, int base) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Valor vacío");
        }
        this.esNeg = false;
        String aux = valor.trim();

        if (aux.startsWith("-")) {
            this.esNeg = true;
            aux = aux.substring(1);
        }

        // Un solo separador decimal (coma o punto)
        int sep = -1;
        for (int i = 0; i < aux.length(); i++) {
            char c = aux.charAt(i);
            if (c == ',' || c == '.') {
                if (sep != -1) throw new IllegalArgumentException("Más de un separador decimal");
                sep = i;
            }
        }

        String parteE = (sep == -1) ? aux : aux.substring(0, sep);
        String parteF = (sep == -1) ? ""  : aux.substring(sep + 1);

        if (parteE.isEmpty() && parteF.isEmpty()) {
            throw new IllegalArgumentException("Número inválido: " + valor);
        }
        
        validarDigitos(parteE, base);
        validarDigitos(parteF, base);
        this.parteEnt = parteE;
        this.parteFrac = parteF;
        this.valorReal = valor;
        //System.out.println();
    } 
    
    public void sumaPonde( int baseOrigen, int t) {
        //convPartes(valor, baseOrigen);   // setea esNeg, parteEnt, parteFrac (ya validados)
        String ent = getParteEnt(), frac = getParteFrac();
        
        BigInteger b = BigInteger.valueOf(baseOrigen);

        // Parte entera: Horner
        BigInteger valorEnt = BigInteger.ZERO;
        for (int i = 0; i < ent.length(); i++) {
            int dig = Character.digit(ent.charAt(i), baseOrigen);
            valorEnt = valorEnt.multiply(b).add(BigInteger.valueOf(dig));
        }

        // Parte fraccionaria: (dígitos como entero) / base^cantidad, una sola división
        BigDecimal valorDec = BigDecimal.ZERO;
        if (!frac.isEmpty()) {
            BigInteger num = BigInteger.ZERO;
            for (int i = 0; i < frac.length(); i++) {
                int dig = Character.digit(frac.charAt(i), baseOrigen);
                num = num.multiply(b).add(BigInteger.valueOf(dig));
            }
            BigInteger den = b.pow(frac.length());
            valorDec = new BigDecimal(num)
                    .divide(new BigDecimal(den), t, RoundingMode.HALF_UP);
        }

        BigDecimal total = new BigDecimal(valorEnt).add(valorDec);

        // Estos dos los usan divRei y multiRei (siempre sin signo)
        this.parteEnt  = valorEnt.toString();
        //this.parteFrac = valorDec.stripTrailingZeros().toPlainString();
        String fs = valorDec.stripTrailingZeros().toPlainString();
        this.parteFrac = fs.contains(".") ? fs.substring(fs.indexOf('.') + 1) : "";

        if (isEsNeg()) total = total.negate();
        this.valorReal = total.stripTrailingZeros().toPlainString();
        
    }
    
    public String divRei(int baseDestino){ // siempre trabaja con base 10 a otra base
        String aux = getParteEnt();
        if (aux == null || aux.isEmpty()) return "0";

        BigInteger num  = new BigInteger(aux);
        BigInteger base = BigInteger.valueOf(baseDestino);

        if (num.signum() == 0) return "0";
        StringBuilder resu = new  StringBuilder();
        
        while(num.signum()>0){
            int resto = num.mod(base).intValue();
            num = num.divide(base);
            resu.insert(0, Character.toUpperCase(Character.forDigit(resto, baseDestino)));
        }
        String val=resu.toString();
        //System.out.println("conversionE: " +val + ")" + baseDestino);
        return val;
    }
    public String multiRei(int baseDestino, int t) {
        String aux = getParteFrac();
        if (aux == null || aux.isEmpty()) return "";
        if (t <= 0) return "";

        BigDecimal num  = new BigDecimal("0." +aux);    
        //BigDecimal num  = new BigDecimal(aux);
        BigDecimal base = BigDecimal.valueOf(baseDestino);

        if (num.signum() == 0) return "";

        StringBuilder resu = new StringBuilder();
        int iter = 0;

        while (iter < t && num.signum() > 0) {
            num = num.multiply(base);
            int digEnt = num.intValue();
            num = num.subtract(BigDecimal.valueOf(digEnt));
            resu.append(Character.toUpperCase(Character.forDigit(digEnt, baseDestino)));
            iter++;
        }
        return resu.toString();
    }
    public String conversionDivMul(int baseDestino,int t){//Realiza los 2 metodos
        String ent = divRei(baseDestino);
        String dec = multiRei(baseDestino,t);
        //System.out.println("v:"+dec);

        if (ent == null || ent.isEmpty()) ent = "0";
        String valor = (dec == null || dec.isEmpty()) ? ent : ent + "." + dec;
        if (isEsNeg()) valor = "-" + valor;

        /*if (ent == null || ent.isEmpty()) ent = "0";
        if (dec == null || dec.isEmpty()) dec = "0";

        String valor;
        if (!isEsNeg()) {
            valor = ent + "." + dec;
        }  else {
            valor = "-" + ent + "." + dec;
        }*/

        return valor;


    }
  
    //Combierte la cadenda en un array list de digitos del string
    //Creo que ya esta completo
    public enum Modo{ CORTE, SIMETRICO }
    public Normalizado normalizar(String ent, String frac, boolean neg, int base, int tCorte, Modo modo) {
        String digs = quitarCerosFinales(ent + frac);
        int p = ent.length();

        int z = 0;
        while (z < digs.length() && digs.charAt(z) == '0') z++;
        digs = digs.substring(z);
        int exp = p - z;

        if (digs.isEmpty()) return new Normalizado(false, "0", 0, base, true); // el número es 0

        boolean exacta = digs.length() <= tCorte;
        
        if (!exacta) {
            // Hay más dígitos que tCorte, aplicamos corte o redondeo
            boolean subir = false;
            if (modo == Modo.SIMETRICO && digs.length() > tCorte) {
                int digitoSiguiente = Character.digit(digs.charAt(tCorte), base);
                subir = (digitoSiguiente * 2 >= base); 
            }

            char[] c = digs.substring(0, tCorte).toCharArray();

            if (subir) {
                int i = tCorte - 1;
                while (i >= 0) {
                    int d = Character.digit(c[i], base) + 1;
                    if (d < base) {
                        c[i] = Character.toUpperCase(Character.forDigit(d, base));
                        break;
                    } else {
                        c[i] = '0';
                        i--;
                    }
                }
                if (i < 0) {
                    digs = "1" + new String(c);
                    exp++; 
                } else {
                    digs = new String(c);
                }
            } else {
                digs = new String(c);
            }
            digs = quitarCerosFinales(digs); 
        }

        return new Normalizado(neg, digs, exp, base, exacta);
    }


    /*public Normalizado normalizar(String ent, String frac, boolean neg, int base, int t) {
        String digs = quitarCerosFinales(ent + frac);
        int p = ent.length();

        int z = 0;
        while (z < digs.length() && digs.charAt(z) == '0') z++;
        digs = digs.substring(z);
        int exp = p - z;

        if (digs.isEmpty()) return new Normalizado(false, "0", 0, base, true); // el número es 0

        boolean exacta = digs.length() <= t;
        if (!exacta) digs = quitarCerosFinales(digs.substring(0, t));          // corte a t dígitos

        return new Normalizado(neg, digs, exp, base, exacta);
    }*/

    private String quitarCerosFinales(String s) {
        int fin = s.length();
        while (fin > 0 && s.charAt(fin - 1) == '0') fin--;
        return s.substring(0, fin);
    }
    public String desnormalizar(Normalizado n) {
        String m = n.getMantisa();
        int e = n.getExponente();
        String ent, frac;

        if (m.equals("0")) {
            ent = "0"; frac = "";
        } else if (e <= 0) {                       // 0.00125 -> 0.|00|125
            ent = "0"; frac = "0".repeat(-e) + m;
        } else if (e >= m.length()) {              // sobran ceros: 12 x 10^4 -> 12000
            ent = m + "0".repeat(e - m.length()); frac = "";
        } else {                                   // 0.12125 x 10^3 -> 121.25
            ent = m.substring(0, e); frac = m.substring(e);
        }
        return (n.isNegativo() ? "-" : "") + ent + (frac.isEmpty() ? "" : "." + frac);
    }
    public String getParteEnt() {
        return this.parteEnt;
    }
    public String getParteFrac() {
        return this.parteFrac;
    }
    public boolean isEsNeg() {
        return this.esNeg;
    }
    public String getValorReal() {
        return valorReal;
    }
    public void cargarDesdeDecimal() {
        // Normaliza ceros: entero sin ceros a la izquierda, fracción sin ceros a la derecha
        this.parteEnt  = new BigInteger(parteEnt.isEmpty() ? "0" : parteEnt).toString();
        this.parteFrac = quitarCerosFinales(parteFrac);

        // Si es cero, quitar el signo
        if (parteEnt.equals("0") && parteFrac.isEmpty()) this.esNeg = false;

        String v = parteEnt + (parteFrac.isEmpty() ? "" : "." + parteFrac);
        BigDecimal total = new BigDecimal(v);
        this.valorReal = (esNeg ? total.negate() : total).stripTrailingZeros().toPlainString();
    }
}
