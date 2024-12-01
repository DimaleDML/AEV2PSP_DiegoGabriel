package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulacioMP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int tipo = Integer.parseInt(args[0]);
		int numProteina = Integer.parseInt(args[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss_SSS");
		String fecha = sdf.format(new Date());
		fecha = formatearFecha(fecha);
		double resultado;
		long tiempoInicio = System.currentTimeMillis();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/MP_Resultados/PROT_MP_"+tipo+"_n"+(numProteina+1)+"_"+fecha+".sim"))){
			resultado = simulation(tipo);
			long tiempoFinal = System.currentTimeMillis();
			long duracionTotal = tiempoFinal - tiempoInicio;
			long segundos = duracionTotal / 1000;
	        long centesimas = (duracionTotal % 1000) / 10;
	        String tiempoEnCentesimas = segundos + "_" + String.format("%02d", centesimas);
	        
			String fechaFinalizacion = sdf.format(new Date());
			fechaFinalizacion = formatearFecha(fechaFinalizacion);
			bw.write(fecha + "\n");
			bw.write(fechaFinalizacion+ "\n");
			bw.write(tiempoEnCentesimas+ "\n");
			bw.write(Double.toString(resultado)+ "\n");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static double simulation(int type) {
		double calc = 0.0;
		double simulationTime = Math.pow(5, type);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;
		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(), 2));
		}
		return calc;
	}
	
	public static String formatearFecha(String fecha) {
		String centesimas = fecha.substring(fecha.length() - 3, fecha.length() - 1);
		fecha = fecha.substring(0, fecha.length() - 3) + centesimas;
		return fecha;
	}
}
