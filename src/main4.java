import logica.*;
import logica.excepciones.*;
import logica.valueobjects.*;
import java.util.List;

public class main4 {
    public static void main(String[] args) {
        Fachada fachada = null;
        
        try {
            System.out.println("===== INICIALIZANDO FACHADA =====");
            fachada = new Fachada();
            System.out.println("✓ Fachada inicializada exitosamente\n");
            
            // ===== PRUEBAS DE ALTA PRODUCTO =====
            System.out.println("===== PRUEBAS altaProducto() =====");
            
            System.out.println("1. Creando productos con VOProducto...");
            VOProducto vop1 = new VOProducto("P004", "Pala", 150);
            VOProducto vop2 = new VOProducto("P005", "Mancuerna", 25);
            VOProducto vop3 = new VOProducto("P006", "Silla", 75);
            System.out.println("   ✓ Productos creados:\n      " + vop1.getCodigo() + "\n      " + vop2.getCodigo() + "\n      " + vop3.getCodigo() + "\n");
            
            System.out.println("2. Insertando productos con altaProducto...");
            try {
                fachada.altaProducto(vop1);
                System.out.println("   ✓ Producto P004 insertado");
            } catch (ProductoExisteException e) {
                System.out.println("   ⚠ Producto P004 ya existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                fachada.altaProducto(vop2);
                System.out.println("   ✓ Producto P005 insertado");
            } catch (ProductoExisteException e) {
                System.out.println("   ⚠ Producto P005 ya existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                fachada.altaProducto(vop3);
                System.out.println("   ✓ Producto P006 insertado\n");
            } catch (ProductoExisteException e) {
                System.out.println("   ⚠ Producto P006 ya existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            // ===== PRUEBAS DE LISTADO PRODUCTOS =====
            System.out.println("===== PRUEBAS listadoProductos() =====");
            System.out.println("1. Listando todos los productos...");
            try {
                List<VOProducto> productos = fachada.listadoProductos();
                if (productos != null && !productos.isEmpty()) {
                    System.out.println("   ✓ Se encontraron " + productos.size() + " productos:");
                    for (VOProducto p : productos) {
                        System.out.println("      " + p.getNombre());
                    }
                    System.out.println();
                } else {
                    System.out.println("   ✗ No hay productos\n");
                }
            } catch (PersistenciaException | ProductoNoExisteException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            // ===== PRUEBAS DE REGISTRO VENTA =====
            System.out.println("===== PRUEBAS registroVenta() =====");
            
            System.out.println("1. Creando ventas con VOVenta...");
            VOVenta vov1 = new VOVenta(5, "Cliente A");
            VOVenta vov2 = new VOVenta(10, "Cliente B");
            VOVenta vov3 = new VOVenta(3, "Cliente C");
            System.out.println("   ✓ Ventas creadas:\n      " + vov1 + "\n      " + vov2 + "\n      " + vov3 + "\n");
            
            System.out.println("2. Registrando ventas para producto P004 con registroVenta...");
            try {
                fachada.registroVenta("P004", vov1);
                System.out.println("   ✓ Venta V1 registrada para P004");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                fachada.registroVenta("P004", vov2);
                System.out.println("   ✓ Venta V2 registrada para P004");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                fachada.registroVenta("P004", vov3);
                System.out.println("   ✓ Venta V3 registrada para P004");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                fachada.registroVenta("P005", vov1);
                System.out.println("   ✓ Venta V1 registrada para P005");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                fachada.registroVenta("P005", vov2);
                System.out.println("   ✓ Venta V2 registrada para P005\n");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage());
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            // ===== PRUEBAS DE LISTADO VENTAS =====
            System.out.println("===== PRUEBAS listadoVentas() =====");
            System.out.println("1. Listando ventas para producto P004...");
            try {
                List<VOVentaTotal> ventas = fachada.listadoVentas("P004");
                if (ventas != null && !ventas.isEmpty()) {
                    System.out.println("   ✓ Se encontraron " + ventas.size() + " ventas:");
                    for (VOVentaTotal v : ventas) {
                        System.out.println("      " + v.codPro() + " " + v.getCliente());
                    }
                    System.out.println();
                } else {
                    System.out.println("   ✗ No hay ventas para P004\n");
                }
            } catch (VentaNoExisteException | ProductoNoExisteException | PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("2. Listando ventas para producto P005...");
            try {
                List<VOVentaTotal> ventas = fachada.listadoVentas("P005");
                if (ventas != null && !ventas.isEmpty()) {
                    System.out.println("   ✓ Se encontraron " + ventas.size() + " ventas:");
                    for (VOVentaTotal v : ventas) {
                        System.out.println("      " + v.codPro() + " " + v.getCliente());
                    }
                    System.out.println();
                } else {
                    System.out.println("   ✗ No hay ventas para P005\n");
                }
            } catch (VentaNoExisteException | ProductoNoExisteException | PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            // ===== PRUEBAS DE DATOS VENTA =====
            System.out.println("===== PRUEBAS datosVenta() =====");
            System.out.println("1. Obteniendo datos de venta número 1 de P004...");
            try {
                VOVenta venta = fachada.datosVenta("P004", 1);
                System.out.println("   ✓ Venta encontrada: " + venta.getCliente() + "\n");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage() + "\n");
            } catch (VentaNoExisteException e) {
                System.out.println("   ✗ Venta no existe: " + e.getMessage() + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("2. Obteniendo datos de venta número 2 de P004...");
            try {
                VOVenta venta = fachada.datosVenta("P004", 2);
                System.out.println("   ✓ Venta encontrada: " + venta.getCliente() + "\n");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage() + "\n");
            } catch (VentaNoExisteException e) {
                System.out.println("   ✗ Venta no existe: " + e.getMessage() + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            //===== PRUEBAS DE TOTAL RECAUDADO =====
            System.out.println("===== PRUEBAS totalRecaudadoPorVentas() =====");
            System.out.println("1. Obteniendo total recaudado para P004...");
            try {
                double total = fachada.totalRecaudadoPorVentas("P004");
                System.out.println("   ✓ Total recaudado P004: $" + String.format("%.2f", total) + "\n");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage() + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("2. Obteniendo total recaudado para P005...");
            try {
                double total = fachada.totalRecaudadoPorVentas("P005");
                System.out.println("   ✓ Total recaudado P005: $" + String.format("%.2f", total) + "\n");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage() + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            // ===== PRUEBAS DE PRODUCTO MAS VENDIDO =====
            System.out.println("===== PRUEBAS productoMasUnidadesVendidas() =====");
            System.out.println("1. Obteniendo producto más vendido...");
            try {
                VOProdVentas prodVentas = fachada.productoMasUnidadesVendidas();
                if (prodVentas != null) {
                    System.out.println("   ✓ Producto más vendido: " + prodVentas.getNombre() + prodVentas.getUnidadesVendidas() + "\n");
                } else {
                    System.out.println("   ✗ No hay información de ventas\n");
                }
            } catch (PersistenciaException | ProductoNoExisteException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            // ===== PRUEBAS DE BAJA PRODUCTO =====
            System.out.println("===== PRUEBAS bajaProducto() =====");
            System.out.println("1. Eliminando producto P006...");
            try {
                fachada.bajaProducto("P006");
                System.out.println("   ✓ Producto P006 eliminado\n");
            } catch (ProductoNoExisteException e) {
                System.out.println("   ✗ Producto no existe: " + e.getMessage() + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("2. Listando productos finales...");
            try {
                List<VOProducto> productos = fachada.listadoProductos();
                if (productos != null && !productos.isEmpty()) {
                    System.out.println("   ✓ Se encontraron " + productos.size() + " productos:");
                    for (VOProducto p : productos) {
                        System.out.println("      " + p.getNombre());
                    }
                    System.out.println();
                } else {
                    System.out.println("   ✗ No hay productos\n");
                }
            } catch (PersistenciaException | ProductoNoExisteException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("===== PRUEBAS COMPLETADAS =====");
            
        } catch (Exception e) {
            System.out.println("\n✗ Error general:");
            System.out.println("   " + e.getMessage());
            e.printStackTrace();
        }
    }
}
