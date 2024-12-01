package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

public class Simulador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulador frame = new Simulador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public Simulador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Primaria:");
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 86, 35);
		contentPane.add(lblNewLabel);

		JLabel lblSecundaria = new JLabel("Secundaria:");
		lblSecundaria.setFont(new Font("Consolas", Font.BOLD, 14));
		lblSecundaria.setBounds(10, 50, 108, 35);
		contentPane.add(lblSecundaria);

		JLabel lblTerciaria = new JLabel("Terciaria:");
		lblTerciaria.setFont(new Font("Consolas", Font.BOLD, 14));
		lblTerciaria.setBounds(10, 89, 86, 35);
		contentPane.add(lblTerciaria);

		JLabel lblQuaternaria = new JLabel("Quaternaria:");
		lblQuaternaria.setFont(new Font("Consolas", Font.BOLD, 14));
		lblQuaternaria.setBounds(10, 128, 108, 35);
		contentPane.add(lblQuaternaria);

		JSpinner spinnerPrimaria = new JSpinner();
		spinnerPrimaria.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spinnerPrimaria.setBounds(106, 13, 86, 28);
		contentPane.add(spinnerPrimaria);

		JSpinner spinnerSecundaria = new JSpinner();
		spinnerSecundaria.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spinnerSecundaria.setBounds(106, 52, 86, 28);
		contentPane.add(spinnerSecundaria);

		JSpinner spinnerTerciaria = new JSpinner();
		spinnerTerciaria.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spinnerTerciaria.setBounds(106, 91, 86, 28);
		contentPane.add(spinnerTerciaria);

		JSpinner spinnerQuaternaria = new JSpinner();
		spinnerQuaternaria.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spinnerQuaternaria.setBounds(106, 130, 86, 28);
		contentPane.add(spinnerQuaternaria);

		JButton btnSimula = new JButton("INICIAR SIMULACION");
		btnSimula.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSimula.setBounds(10, 174, 414, 76);
		contentPane.add(btnSimula);

		JLabel lblResultadoProceso = new JLabel("");
		lblResultadoProceso.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResultadoProceso.setBounds(202, 20, 222, 42);
		contentPane.add(lblResultadoProceso);

		JLabel lblResultadoHilo = new JLabel("");
		lblResultadoHilo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResultadoHilo.setBounds(202, 98, 222, 42);
		contentPane.add(lblResultadoHilo);

		btnSimula.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				int[] proteinas = new int[4];
				proteinas[0] = (int) spinnerPrimaria.getValue();
				proteinas[1] = (int) spinnerSecundaria.getValue();
				proteinas[2] = (int) spinnerTerciaria.getValue();
				proteinas[3] = (int) spinnerQuaternaria.getValue();
				
				// EJECUCION MULTIPROCESO
				

				long inicioContadorProcesos = System.currentTimeMillis();
				
				List<Process> procesos = new ArrayList<>();

				for (int i = 0; i < proteinas.length; i++) {
				    for (int j = 0; j < proteinas[i]; j++) {
				        String classe = "main.SimulacioMP";
				        String javaHome = System.getProperty("java.home");
				        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
				        String classpath = System.getProperty("java.class.path");
				        List<String> command = new ArrayList<>();
				        command.add(javaBin);
				        command.add("-cp");
				        command.add(classpath);
				        command.add(classe);
				        command.add(String.valueOf(i + 1));
				        command.add(String.valueOf(j));

				        ProcessBuilder builder = new ProcessBuilder(command);
				        try {
				            Process proceso = builder.start();
				            procesos.add(proceso);
				        } catch (IOException w) {
				            w.printStackTrace();
				        }
				    }
				}

				for (Process proceso : procesos) {
				    try {
				        proceso.waitFor();
				    } catch (InterruptedException e1) {
				        e1.printStackTrace();
				    }
				}
				long finContadorProcesos = System.currentTimeMillis();

				double tiempo = (finContadorProcesos - inicioContadorProcesos) / 1000.0;

				String tiempoFormateado = String.format("%.2f", tiempo);

				lblResultadoProceso.setText("Temps total multiproces: " + tiempoFormateado + " s");
				
				// EUJECUCION MULTIHILO

				long inicioContadorHilos = System.currentTimeMillis();

				ArrayList<Thread> hilos = new ArrayList<>();

				for (int i2 = 0; i2 < proteinas.length; i2++) {
					for (int j2 = 0; j2 < proteinas[i2]; j2++) {
						Thread t = new Thread(new SimulacioMT((i2 + 1),j2));
						t.start();
						hilos.add(t);
					}
				}

				for (Thread hilo : hilos) {
					try {
						hilo.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				long finContadorHilos = System.currentTimeMillis();

				double tiempoHilo = (finContadorHilos - inicioContadorHilos) / 1000.0;

				String tiempoFormateadoHilo = String.format("%.2f", tiempoHilo);

				lblResultadoHilo.setText("Temps total multihilo: " + tiempoFormateadoHilo + " s");

				
			}
		});
	}

	public static void lanzador(int n1) {

		
		String classe = "main.SimulacioMP";
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		List<String> command = new ArrayList<>();
		command.add(javaBin);
		command.add("-cp");
		command.add(classpath);
		command.add(classe);
		command.add(String.valueOf(n1));

		ProcessBuilder builder = new ProcessBuilder(command);

		try {
			Process p = builder.start();

		} catch (IOException w) {
			w.printStackTrace();
		}

	}


}
