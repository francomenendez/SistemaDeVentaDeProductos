package grafica.ventanas;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import logica.valueobjects.VOVenta;
import grafica.controladores.ControladorBajaProducto;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class VentanaBajaProducto {

	private JFrame frame;
	private JTextField txtCodigoProd;
	private JLabel lblCalcularMontoRecaudado;
	private ControladorBajaProducto miControlador;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBajaProducto window = new VentanaBajaProducto();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaBajaProducto() {
		initialize();
		miControlador = new ControladorBajaProducto(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 430);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado aquí
        frame.getContentPane().setLayout(null);

        JLabel lblIngreseUnProducto = new JLabel("INGRESE CÓDIGO");
        lblIngreseUnProducto.setHorizontalAlignment(SwingConstants.CENTER);
        lblIngreseUnProducto.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblIngreseUnProducto.setBounds(47, 67, 145, 33);
        frame.getContentPane().add(lblIngreseUnProducto);

        txtCodigoProd = new JTextField();
        txtCodigoProd.setColumns(10);
        txtCodigoProd.setBounds(221, 74, 247, 19);
        frame.getContentPane().add(txtCodigoProd);

        lblCalcularMontoRecaudado = new JLabel("BAJA DE PRODUCTO");
        lblCalcularMontoRecaudado.setHorizontalAlignment(SwingConstants.CENTER);
        lblCalcularMontoRecaudado.setForeground(Color.RED);
        lblCalcularMontoRecaudado.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblCalcularMontoRecaudado.setBounds(29, 10, 630, 30);
        frame.getContentPane().add(lblCalcularMontoRecaudado);

       	JButton btnDarBaja = new JButton("BAJA");
        btnDarBaja.setForeground(Color.WHITE);
        btnDarBaja.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDarBaja.setBackground(Color.RED);
        btnDarBaja.setBounds(532, 73, 113, 30);
        frame.getContentPane().add(btnDarBaja);

		btnDarBaja.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if (txtCodigoProd.getText().trim().isEmpty()) {
           			mensajeError("Debe ingresar un codigo ", false);
        		}else{ 
					String codP = txtCodigoProd.getText().trim();
					miControlador.BajaProducto(codP);
				}

            }
        });

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
}