package adat.ud1.examen.corregido.imartrodr;

import java.io.Serializable;
import java.time.LocalDate;

public class FaltaAsistencia implements Serializable{
    public final static int POS_ID = 0;
    public final static int POS_NOME_COMPLETO = 1;
    public final static int POS_CURSO = 2;
    public final static int POS_DTA = 3;
    public final static int POS_TIPO = 4;
    public final static int POS_MODULO = 5;
    public final static int POS_SES = 6;
    public final static int POS_XUS = 7;
    
    LocalDate data;
    int sesion;
    String modulo;
    String curso;
    String Alumno;
    public FaltaAsistencia(LocalDate data, int sesion, String modulo, String curso, String alumno) {
        this.data = data;
        this.sesion = sesion;
        this.modulo = modulo;
        this.curso = curso;
        this.Alumno = alumno;
    }

    public FaltaAsistencia(String[] csv) {
        if (csv.length != 8) {
            throw new IllegalArgumentException("CSV en mal formato");
        }
        this.sesion = Integer.valueOf(csv[POS_SES]);
        this.modulo = csv[POS_MODULO];
        this.curso = csv[POS_CURSO];
        this.Alumno = csv[POS_NOME_COMPLETO];
        String[] fecha = csv[POS_DTA].split("/");
        int[] numFecha = new int[fecha.length];
        for (int i = 0; i < fecha.length; i++) {
            numFecha[i] = Integer.valueOf(fecha[i]);
        }
        this.data = LocalDate.of(numFecha[2], numFecha[1],numFecha[0]);
    }

    @Override
    public String toString() {
        return "FaltaAsistencia [data=" + data + ", sesion=" + sesion + ", modulo=" + modulo + ", curso=" + curso
                + ", Alumno=" + Alumno + "]";
    }
}
