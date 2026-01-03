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
	import javax.swing.JScrollPane;
	import javax.swing.table.DefaultTableCellRenderer;
	import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListadoProductos;
import logica.valueobjects.VOProducto;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JList;
	import javax.swing.JOptionPane;
	import javax.swing.DefaultListModel;
	import javax.swing.JSeparator;
	import java.awt.Label;
import java.awt.List;

import javax.swing.JLabel;
	import java.awt.Color;
	import java.awt.SystemColor;
	
public class VentanaListadoProductos {

	private JFrame frame;
    private ControladorListadoProductos miControlador;
	private DefaultTableModel modeloTabla;
	private JTextField txtListadoMinivans;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListadoProductos window = new VentanaListadoProductos();
					window.frame.setVisible(true);
					window.cargarProductos(); // Llamar a cargar los datos
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaListadoProductos() {
		initialize();
		miControlador = new ControladorListadoProductos(this);
		this.cargarProductos();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
        frame.setBounds(100, 100, 700, 430);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado aquÃ­
        frame.getContentPane().setLayout(null);

		modeloTabla = new DefaultTableModel(
	            new Object[][] {},
	            new String[] { "CODIGO", "NOMBRE", "PRECIO" }
	        ) {
	            Class[] columnTypes = new Class[] {
	                String.class, String.class, Integer.class
	            };

	            public Class getColumnClass(int columnIndex) {
	                return columnTypes[columnIndex];
	            }
        };

		table = new JTable(modeloTabla);
		table.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), null, null, null), null));
		table.setFont(new Font("Tahoma", Font.BOLD, 10));
		table.setBackground(new Color(240, 240, 240));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(30, 65, 628, 216);
		frame.getContentPane().add(scrollPane);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Aplicar el renderer a cada columna
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JLabel lblListadoDeProductos = new JLabel("LISTADO DE PRODUCTOS");
        lblListadoDeProductos.setHorizontalAlignment(SwingConstants.CENTER);
        lblListadoDeProductos.setForeground(Color.RED);
        lblListadoDeProductos.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblListadoDeProductos.setBounds(28, 21, 628, 30);
        frame.getContentPane().add(lblListadoDeProductos);
		
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
	
	public void cargarProductos() {
		try {
			LinkedList<VOProducto> listaProducto = miControlador.ListarProductos();
			
			modeloTabla.setRowCount(0);
			for (VOProducto p : listaProducto) {
				modeloTabla.addRow(new Object[] {
					p.getCodigo(),
					p.getNombre(),
					p.getPrecio()
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}