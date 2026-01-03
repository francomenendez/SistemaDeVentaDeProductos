package grafica.ventanas;

import java.awt.EventQueue;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.ButtonGroup;

public class VentanaPrincipal {

    private JFrame frame;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	VentanaPrincipal window = new VentanaPrincipal();
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
    public VentanaPrincipal() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centra la ventana


        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 255, 255));
        menuBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        frame.setJMenuBar(menuBar);

        // Menú principal
        JMenu menuRegistrar = new JMenu("Registrar");
        menuRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuBar.add(menuRegistrar);
        
        // Menú principal
        JMenu menuListar = new JMenu("Listar");
        menuListar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuBar.add(menuListar);
        
        JMenu menuOpciones = new JMenu("Opciones");
        buttonGroup.add(menuOpciones);
        menuOpciones.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuBar.add(menuOpciones);
        
        //Menu Listar
        JMenuItem listarProductos = new JMenuItem("Listar Productos");
        listarProductos.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuListar.add(listarProductos);
        
        JMenuItem listarVentas = new JMenuItem("Listar Ventas");
        listarVentas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuListar.add(listarVentas);
        
        JMenuItem mostrarVenta = new JMenuItem("Mostrar Venta");
        mostrarVenta.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuListar.add(mostrarVenta);
        
        JMenuItem ProductoMax = new JMenuItem("Producto mas vendido");
        ProductoMax.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuListar.add(ProductoMax);

        // Opción de menú "Registrar"
        JMenuItem Producto = new JMenuItem("Producto");
        Producto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuRegistrar.add(Producto);
        
        JMenuItem Venta = new JMenuItem("Venta");
        Venta.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuRegistrar.add(Venta);
        
        JMenuItem BajaProducto = new JMenuItem("Baja Producto");
        BajaProducto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuRegistrar.add(BajaProducto);
        
        //Menu Opciones
        JMenuItem Calcular = new JMenuItem("Total recaudado");
        Calcular.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuOpciones.add(Calcular);
        
        
        //Acción al hacer clic en "Minivan"
        Producto.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              VentanaAltaProducto nuevaVentana = new VentanaAltaProducto();
               nuevaVentana.setVisible(true);
           }
         });
       
        Venta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaRegistroVenta nuevaVentana = new VentanaRegistroVenta();
                nuevaVentana.setVisible(true);
            }
        });
        
        listarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaListadoProductos nuevaVentana = new VentanaListadoProductos();
                nuevaVentana.setVisible(true);
            }
        });
        
        listarVentas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaListadoVentas nuevaVentana = new VentanaListadoVentas();
                nuevaVentana.setVisible(true);
            }
        });
        
        mostrarVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaDatosVenta nuevaVentana = new VentanaDatosVenta();
                nuevaVentana.setVisible(true);
            }
        });
        
        ProductoMax.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaProductoMasUnidadesVendidas nuevaVentana = new VentanaProductoMasUnidadesVendidas();
                nuevaVentana.setVisible(true);
            }
        });
        
        Calcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaTotalRecaudadoPorVentas nuevaVentana = new VentanaTotalRecaudadoPorVentas();
                nuevaVentana.setVisible(true);
            }
        });

        BajaProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaBajaProducto nuevaVentana = new VentanaBajaProducto();
                nuevaVentana.setVisible(true);
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