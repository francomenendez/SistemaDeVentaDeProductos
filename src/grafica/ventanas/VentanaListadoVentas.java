package grafica.ventanas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListadoVentas;
import logica.valueobjects.VOVentaTotal;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.TextField;
	
public class VentanaListadoVentas {

	private JFrame frame;
	private ControladorListadoVentas miControlador;
	private DefaultTableModel modeloTabla;
	private java.awt.TextField txtCodigoProd;
	private JTable table;
	private JTable table_1;
	private JLabel lblIngreseCodigoDe;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListadoVentas window = new VentanaListadoVentas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaListadoVentas() {
		initialize();
		miControlador = new ControladorListadoVentas(this);
	}


	private void initialize() {
		frame = new JFrame();
        frame.setBounds(100, 100, 700, 430);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado aquÃ­
        frame.getContentPane().setLayout(null);

		modeloTabla = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {"NUMERO", "CLIENTE", "UNIDADES"}
	        ) {
	            Class[] columnTypes = new Class[] {
	                Integer.class, String.class, Integer.class
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
		scrollPane.setBounds(30, 100, 628, 216);
		frame.getContentPane().add(scrollPane);
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
        
        JLabel lblListadoDeVentas = new JLabel("LISTADO DE VENTAS");
        lblListadoDeVentas.setHorizontalAlignment(SwingConstants.CENTER);
        lblListadoDeVentas.setForeground(Color.RED);
        lblListadoDeVentas.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblListadoDeVentas.setBounds(28, 20, 628, 30);
        frame.getContentPane().add(lblListadoDeVentas);
        
        lblIngreseCodigoDe = new JLabel("Ingrese codigo de un producto:");
        lblIngreseCodigoDe.setHorizontalAlignment(SwingConstants.LEFT);
        lblIngreseCodigoDe.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblIngreseCodigoDe.setBounds(28, 71, 335, 25);
        frame.getContentPane().add(lblIngreseCodigoDe);
        
		txtCodigoProd = new TextField();
		txtCodigoProd.setColumns(35);
		txtCodigoProd.setBounds(422, 72, 131, 21);
		frame.getContentPane().add(txtCodigoProd);

		
        JButton btnListadoVentas = new JButton("LISTAR");
        btnListadoVentas.setForeground(Color.WHITE);
        btnListadoVentas.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnListadoVentas.setBackground(Color.RED);
		btnListadoVentas.setBounds(560, 70, 113, 25);
        frame.getContentPane().add(btnListadoVentas);

		btnListadoVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cod = txtCodigoProd.getText().trim();
				cargarVentas(cod);
			}
		});
	}

	public void cargarVentas(String cod) {
		try {
			LinkedList<VOVentaTotal> listaVentas = miControlador.ListadoVentas(cod);
			modeloTabla.setRowCount(0);
			for (VOVentaTotal p : listaVentas) {
				modeloTabla.addRow(new Object[] {
					p.getNumero(),
					p.getCliente(),
					p.getUnidades()
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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