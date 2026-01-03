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

import grafica.controladores.ControladorProductoMasUnidadesVendidas;
import logica.valueobjects.VOProdVentas;
import logica.valueobjects.VOProducto;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JSeparator;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.TextField;
import javax.swing.JScrollPane;

public class VentanaProductoMasUnidadesVendidas {

	private JFrame frame;
	private ControladorProductoMasUnidadesVendidas miControlador;
	private DefaultTableModel modeloTabla;
	private JTextField txtListadoMinivans;
	private JTable table;
	private JTable table_1;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaProductoMasUnidadesVendidas window = new VentanaProductoMasUnidadesVendidas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaProductoMasUnidadesVendidas() {
		initialize();
		miControlador = new ControladorProductoMasUnidadesVendidas(this);
		this.cargarProductoMasUnidades();
	}
	
	private void initialize() {
		frame = new JFrame();
	    frame.setBounds(100, 100, 700, 430);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado aquÃ­
	    frame.getContentPane().setLayout(null);
	
	    // Crear el modelo de la tabla con los nombres de las columnas
	    modeloTabla = new DefaultTableModel(
	        new Object[][] {},
	        new String[] {"COD.PRODUCTO", "NOMBRE", "PRECIO", "UNIDADES VENDIDAS"}
	    	) {
	            Class[] columnTypes = new Class[] {
	                Integer.class, String.class, String.class, Integer.class
	            };

	            public Class getColumnClass(int columnIndex) {
	                return columnTypes[columnIndex];
	            }
        };
	
	    // Crear la tabla con el modelo (mantener modeloTabla definido arriba)
	    table = new JTable(modeloTabla);
	    table.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), null, null, null), null));
	    table.setFont(new Font("Tahoma", Font.BOLD, 10));
	    table.setBackground(new Color(240, 240, 240));
	    table.setFillsViewportHeight(true);
	    table.setRowSelectionAllowed(false);

	    // Poner la tabla dentro de un JScrollPane para que se muestren los encabezados
	    JScrollPane scroll = new JScrollPane(table);
	    scroll.setBounds(31, 65, 628, 250); // mismo tamaño que tenías para la tabla
	    frame.getContentPane().add(scroll);

	    // Aplicar renderer a las columnas (seguí usando table para referenciar columnas)
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	    }
	    
	    
	    JLabel lblListadoDeVentas = new JLabel("PRODUCTO CON MAS UNIDADES VENDIDAS");
	    lblListadoDeVentas.setHorizontalAlignment(SwingConstants.CENTER);
	    lblListadoDeVentas.setForeground(Color.RED);
	    lblListadoDeVentas.setFont(new Font("Tahoma", Font.BOLD, 24));
	    lblListadoDeVentas.setBounds(31, 20, 628, 30);
	    frame.getContentPane().add(lblListadoDeVentas);
		
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

	public void cargarProductoMasUnidades() {
		try {
			VOProdVentas prod = miControlador.ProductoMasUnidadesVendidas();

			modeloTabla.setRowCount(0);

			modeloTabla.addRow(new Object[] {
					prod.getCodigo(),
					prod.getNombre(),
					prod.getPrecio(),
					prod.getUnidadesVendidas()
				});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}