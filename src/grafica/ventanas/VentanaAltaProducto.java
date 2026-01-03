package grafica.ventanas;

import javax.swing.*;

import grafica.controladores.ControladorAltaProducto;
import logica.valueobjects.VOProducto;

import java.awt.*;

public class VentanaAltaProducto {

	private JFrame frame;
	private ControladorAltaProducto miControlador;

	private final JTextField txtCod    = new JTextField(12);
	private final JTextField txtNombre = new JTextField(18);
	private final JTextField txtPrecio = new JTextField(8);

	public VentanaAltaProducto() {
		initialize();
		miControlador = new ControladorAltaProducto(this);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Alta de producto");
		frame.setBounds(100, 100, 360, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(8, 8));

		JPanel form = new JPanel(new GridLayout(3, 2, 6, 6));
		form.add(new JLabel("Código:"));  form.add(txtCod);
		form.add(new JLabel("Nombre:"));  form.add(txtNombre);
		form.add(new JLabel("Precio:"));  form.add(txtPrecio);

		JButton btnAceptar  = new JButton("Aceptar");
		JButton btnCancelar = new JButton("Cancelar");
		btnAceptar.addActionListener(e -> onAceptar());
		btnCancelar.addActionListener(e -> frame.dispose());

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botones.add(btnCancelar);
		botones.add(btnAceptar);

		frame.getContentPane().add(form, BorderLayout.CENTER);
		frame.getContentPane().add(botones, BorderLayout.SOUTH);
	}

	private void onAceptar() {
		int precio;
		if (txtPrecio.getText().isEmpty())
			precio = 0;
		else
			precio = Integer.parseInt(txtPrecio.getText());
		
		new ControladorAltaProducto(this).AltaProducto(txtCod.getText(), txtNombre.getText(), precio);
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