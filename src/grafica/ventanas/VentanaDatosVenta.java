package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorDatosVenta;
import logica.valueobjects.VOProdVentas;
import logica.valueobjects.VOVenta;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList; 
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.TextField;


public class VentanaDatosVenta {

	private JFrame frame;
	private ControladorDatosVenta miControlador;
	private DefaultTableModel modeloTabla;
	private JTextField txtCodP;
	private JTextField txtNumV;
	private JTable table;
	private JTable table_1;
	private JLabel lblIngreseCodigoDe;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaDatosVenta window = new VentanaDatosVenta();
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
	public VentanaDatosVenta() {
		initialize();
		miControlador = new ControladorDatosVenta(this);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	    frame.setBounds(100, 100, 700, 430);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado aquÃ­
	    frame.getContentPane().setLayout(null);
	
	    // Crear el modelo de la tabla con los nombres de las columnas
	    modeloTabla = new DefaultTableModel(
	        new Object[][] {},
	        new String[] {"CLIENTE", "UNIDADES"}
	    ) {
			Class[] columnTypes = new Class[] {
	                String.class, Integer.class
	            };

	            public Class getColumnClass(int columnIndex) {
	                return columnTypes[columnIndex];
	            }
		};

		table = new JTable(modeloTabla);
	    table.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), null, null, null), null));
	    table.setFont(new Font("Tahoma", Font.BOLD, 10));
	    table.setRowSelectionAllowed(false);
	    table.setBackground(new Color(240, 240, 240));
	    // table.setBounds(28, 239, 628, 143);
		table.setBounds(28, 200, 314, 50);

	    frame.getContentPane().add(table);

		// Poner la tabla dentro de un JScrollPane para que se muestren los encabezados
	    JScrollPane scroll = new JScrollPane(table);
	    scroll.setBounds(31, 270, 628, 70); // mismo tamaño que tenías para la tabla
	    frame.getContentPane().add(scroll);
	    
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    // Aplicar el renderer a cada columna
	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	    }
	    
	    JLabel lblListadoDeVentas = new JLabel("DATOS DE UNA VENTA");
	    lblListadoDeVentas.setHorizontalAlignment(SwingConstants.CENTER);
	    lblListadoDeVentas.setForeground(Color.RED);
	    lblListadoDeVentas.setFont(new Font("Tahoma", Font.BOLD, 24));
	    lblListadoDeVentas.setBounds(28, 20, 628, 30);
	    frame.getContentPane().add(lblListadoDeVentas);
	    
	    lblIngreseCodigoDe = new JLabel("Ingrese Codigo de un Producto:");
	    lblIngreseCodigoDe.setHorizontalAlignment(SwingConstants.LEFT);
	    lblIngreseCodigoDe.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblIngreseCodigoDe.setBounds(28, 71, 335, 25);
	    frame.getContentPane().add(lblIngreseCodigoDe);
	    
	    TextField txtCodP = new TextField();
	    txtCodP.setColumns(35);
	    txtCodP.setBounds(422, 72, 131, 21);
	    frame.getContentPane().add(txtCodP);
	    
	    JLabel lblIngreseNroDe = new JLabel("Ingrese nro de Venta:");
	    lblIngreseNroDe.setHorizontalAlignment(SwingConstants.LEFT);
	    lblIngreseNroDe.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblIngreseNroDe.setBounds(28, 128, 248, 25);
	    frame.getContentPane().add(lblIngreseNroDe);
	    
	    TextField txtNumV = new TextField();
	    txtNumV.setColumns(35);
	    txtNumV.setBounds(422, 128, 131, 21);
	    frame.getContentPane().add(txtNumV);

		JButton btnListarDatosVenta = new JButton("LISTAR");
        btnListarDatosVenta.setForeground(Color.WHITE);
        btnListarDatosVenta.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnListarDatosVenta.setBackground(Color.RED);
        btnListarDatosVenta.setBounds(546, 201, 113, 30);
        frame.getContentPane().add(btnListarDatosVenta);

		btnListarDatosVenta.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
				
				if (txtNumV.getText().trim().isEmpty()) {
           			mensajeError("Debe ingresar un codigo ", false);
        		}else{ 
					int NumV = Integer.parseInt(txtNumV.getText());
                	String CodP = txtCodP.getText().trim();
					cargarDatosVenta(CodP, NumV);
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


	public void cargarDatosVenta(String Cod, int Num) {
		try {
			VOVenta venta = miControlador.DatosVenta(Cod,Num);

			modeloTabla.setRowCount(0);
			if (venta == null) {
				mensajeError("Venta no encontrada", false);
				return;
			}

			modeloTabla.addRow(new Object[] {
					venta.getCliente(),
					venta.getUnidades()
				});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}