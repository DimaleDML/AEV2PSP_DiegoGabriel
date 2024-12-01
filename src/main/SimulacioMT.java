package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulacioMT implements Runnable {

	private int tipoProteina;
	private int numeroProteina;

	public SimulacioMT(int tipoProteina , int numeroProteina) {
		this.tipoProteina = tipoProteina;
		this.numeroProteina = numeroProteina;
	}

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss_SSS");
		String fecha = sdf.format(new Date());
		fecha = formatearFecha(fecha);
		double resultado;
		long tiempoInicio = System.currentTimeMillis();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/MT_Resultados/PROT_MT_"+tipoProteina+"_n"+(numeroProteina+1)+"_"+fecha+".sim"))){
			resultado = simulation(this.tipoProteina);
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
	
	public String formatearFecha(String fecha) {
		String centesimas = fecha.substring(fecha.length() - 3, fecha.length() - 1);
		fecha = fecha.substring(0, fecha.length() - 3) + centesimas;
		return fecha;
	}
}
