package grafica.ventanas;

import javax.swing.*;

import grafica.controladores.ControladorRegistroVenta;

import java.awt.*;
//import grafica.controladores.ControladorRegistrarVenta;

public class VentanaRegistroVenta {

	private JFrame frame;
	private ControladorRegistroVenta miControlador;

	private final JTextField txtCodProd = new JTextField(12);
	private final JTextField txtUnidades = new JTextField(8);
	private final JTextField txtCliente = new JTextField(18);

	/**
	 * Create the application.
	 */
	public VentanaRegistroVenta() {
		initialize();
		miControlador = new ControladorRegistroVenta(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Registrar venta");
		frame.setBounds(100, 100, 380, 220);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(8, 8));

		// Formulario
		JPanel form = new JPanel(new GridLayout(3, 2, 6, 6));
		form.add(new JLabel("Código producto:"));
		form.add(txtCodProd);
		form.add(new JLabel("Unidades:"));
		form.add(txtUnidades);
		form.add(new JLabel("Cliente:"));
		form.add(txtCliente);

		// Botones
		JButton btnAceptar = new JButton("Aceptar");
		JButton btnCancelar = new JButton("Cancelar");
		btnAceptar.addActionListener(e -> onAceptar());
		btnCancelar.addActionListener(e -> frame.dispose());

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botones.add(btnCancelar);
		botones.add(btnAceptar);

		// Layout general
		frame.getContentPane().add(form, BorderLayout.CENTER);
		frame.getContentPane().add(botones, BorderLayout.SOUTH);
	}

	// Llama al controlador (no hace lógica acá)
	private void onAceptar() {
        
        if (txtUnidades.getText().trim().isEmpty()) {
           mensajeError("Debe ingresar un precio", false);
        }else{        
            int uni = Integer.parseInt(txtUnidades.getText());
            miControlador.RegistroVenta(
                txtCodProd.getText(),
                txtCliente.getText(),
                uni
            );
        }
    }

	public void mensajeError(String e, boolean exit) {
		int input = 0;
		if (exit == false) {
			input = JOptionPane.showConfirmDialog(null, e, "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null);
		} else {
			input = JOptionPane.showConfirmDialog(null, e, "Correcto", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null);
		}

		if (input == JOptionPane.OK_OPTION && exit) {
			frame.dispose();
		}
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}