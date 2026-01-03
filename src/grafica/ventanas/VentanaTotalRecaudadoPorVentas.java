package grafica.ventanas;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import grafica.controladores.ControladorTotalRecaudadoPorVentas;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class VentanaTotalRecaudadoPorVentas {

	private JFrame frame;
	private JTextField txtCodigoProd;
	private ControladorTotalRecaudadoPorVentas miControlador;
	private JLabel lblPrecio;
	private JLabel lblCalcularMontoRecaudado;
	private JLabel lblMontoTotal;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTotalRecaudadoPorVentas window = new VentanaTotalRecaudadoPorVentas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaTotalRecaudadoPorVentas() {
		initialize();
		miControlador = new ControladorTotalRecaudadoPorVentas(this);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 430);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado aquí
		frame.getContentPane().setLayout(null);
		
		JLabel lblIngreseUnProducto = new JLabel("INGRESE UN CÓDIGO:");
		lblIngreseUnProducto.setHorizontalAlignment(SwingConstants.CENTER);
		lblIngreseUnProducto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIngreseUnProducto.setBounds(279, 67, 160, 33);
		frame.getContentPane().add(lblIngreseUnProducto);
		
		txtCodigoProd = new JTextField();
		txtCodigoProd.setColumns(10);
		txtCodigoProd.setBounds(221, 110, 247, 19);
		frame.getContentPane().add(txtCodigoProd);
		
		JButton btnCalcular = new JButton("CALCULAR");
		btnCalcular.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCalcular.setBackground(Color.RED); // Botón rojo
		btnCalcular.setForeground(Color.WHITE);
		btnCalcular.setBounds(462, 329, 200, 40);
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cod = txtCodigoProd.getText().trim();
				CalcularMonto(cod);
			}
		});
		
		btnCalcular.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(java.awt.event.MouseEvent evt) {
        		btnCalcular.setBackground(new Color(200, 0, 0)); // Rojo más oscuro al pasar el mouse
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnCalcular.setBackground(Color.RED);
            }
        });
		btnCalcular.setBounds(483, 109, 113, 21);
		frame.getContentPane().add(btnCalcular);
		
		lblPrecio = new JLabel("$");
		lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblPrecio.setBounds(272, 178, 196, 70);
		frame.getContentPane().add(lblPrecio);
		
		lblCalcularMontoRecaudado = new JLabel("CALCULAR TOTAL RECAUDADO");
		lblCalcularMontoRecaudado.setHorizontalAlignment(SwingConstants.CENTER);
		lblCalcularMontoRecaudado.setForeground(Color.RED);
		lblCalcularMontoRecaudado.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCalcularMontoRecaudado.setBounds(29, 10, 630, 30);
		frame.getContentPane().add(lblCalcularMontoRecaudado);
		
		lblMontoTotal = new JLabel("MONTO TOTAL\r\n");
		lblMontoTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblMontoTotal.setForeground(Color.RED);
		lblMontoTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMontoTotal.setBounds(29, 156, 630, 30);
		frame.getContentPane().add(lblMontoTotal);
	}

	public void mensajeError(String e, boolean exit) {
		int input =0;
		if(exit == false) {
			input = JOptionPane.showConfirmDialog(null, e, "Error", JOptionPane.PLAIN_MESSAGE,JOptionPane.ERROR_MESSAGE,null);
		}else {
			input = JOptionPane.showConfirmDialog(null, e, "Correcto", JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null);
		}	
		
		if(input == JOptionPane.OK_OPTION && exit) {
			frame.dispose();
		}
		
	}
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	
	public void CalcularMonto(String cod) {
		try {
			lblPrecio.setText("$");
			Double precio = miControlador.totalRecaudadoPorVentas(cod);
			lblPrecio.setText(lblPrecio.getText() + " " +precio);
		}
		catch(Exception e) {
			mensajeError(e.getMessage(),false);
		}
	}
	
}