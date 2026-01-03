import logica.*;
import logica.excepciones.PersistenciaException;
import persistencia.daos.*;
import poolconexiones.*;
import java.util.List;
import logica.valueobjects.*;

public class main3 {
    public static void main(String[] args) {
        IPoolConexiones pool = null;
        
        try {
            // Inicializar el pool de conexiones
            System.out.println("===== INICIALIZANDO POOL DE CONEXIONES =====");
            pool = new PoolConexiones();
            System.out.println("✓ Pool de conexiones creado exitosamente\n");
            
            // Obtener conexión del pool
            System.out.println("===== OBTENIENDO CONEXIÓN =====");
            IConexion icon = pool.obtenerConexion(true);
            System.out.println("✓ Conexión obtenida exitosamente\n");
            
            // ===== PRUEBAS DAOProductos =====
            System.out.println("===== PRUEBAS DAOProductos =====");
            DAOProductos daoP = new DAOProductos();
            
            System.out.println("1. Creando productos con constructor...");
            Producto p1 = new Producto("P001", "Laptop", 10);
            Producto p2 = new Producto("P002", "Mouse", 100);
            Producto p3 = new Producto("P003", "Teclado", 50);
            System.out.println("   ✓ Productos creados:\n      " + p1 + "\n      " + p2 + "\n      " + p3 + "\n");
            
            System.out.println("2. Insertando productos con.insert...");
            try {
                daoP.insert(p1, icon);
                System.out.println("   ✓ Producto P001 insertado");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                daoP.insert(p2, icon);
                System.out.println("   ✓ Producto P002 insertado");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                daoP.insert(p3, icon);
                System.out.println("   ✓ Producto P003 insertado\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            System.out.println("3. Buscando producto P001...");
            try {
                Producto pEncontrado = daoP.find("P001", icon);
                if (pEncontrado != null) {
                    System.out.println("   ✓ Producto encontrado: " + pEncontrado.getCodigo() + "\n");
                } else {
                    System.out.println("   ✗ Producto no encontrado\n");
                }
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }

            //buscar uno q no exista
            
            System.out.println("4. Listando todos los productos...");
            try {
                List<VOProducto> productos = daoP.listarProductos(icon);
                if (productos != null && !productos.isEmpty()) {
                    System.out.println("   ✓ Se encontraron " + productos.size() + " productos:");
                    for (VOProducto p : productos) {
                        System.out.println("      " + p.getNombre());
                    }
                    System.out.println();
                } else {
                    System.out.println("   ✗ No hay productos\n");
                }
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("6. Eliminando producto P003 con delete...");
            try {
                daoP.delete("P003", icon);
                System.out.println("   ✓ Producto P003 eliminado\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            // ===== PRUEBAS DAOVentas =====
            System.out.println("===== PRUEBAS DAOVentas =====");
            DAOVentas daoV = new DAOVentas("P001");
            
            System.out.println("1. Creando ventas con constructor...");
            Venta v1 = new Venta(1, 5, "Cliente A");
            Venta v2 = new Venta(2, 10, "Cliente B");
            Venta v3 = new Venta(3, 3, "Cliente C");
            System.out.println("   ✓ Ventas creadas:\n      " + v1 + "\n      " + v2 + "\n      " + v3 + "\n");
            
            System.out.println("2. Insertando ventas con.insert...");
            try {
                daoV.insback(v1, icon);
                System.out.println("   ✓ Venta V1 insertada");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                daoV.insback(v2, icon);
                System.out.println("   ✓ Venta V2 insertada");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            try {
                daoV.insback(v3, icon);
                System.out.println("   ✓ Venta V3 insertada\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage());
            }
            
            System.out.println("3. Obteniendo cantidad total de ventas con largo...");
            try {
                int cantidadVentas = daoV.largo(icon);
                System.out.println("   ✓ Total de ventas: " + cantidadVentas + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("4. Obteniendo venta k-ésima (número 1) con k_esimo...");
            try {
                Venta vEncontrada = daoV.k_esimo(1, icon);
                if (vEncontrada != null) {
                    System.out.println("   ✓ Venta encontrada: " + vEncontrada.getNumero() + "\n");
                } else {
                    System.out.println("   ✗ Venta no encontrada\n");
                }
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("5. Listando todas las ventas con listarVentas...");
            try {
                List<VOVentaTotal> ventas = daoV.listarVentas(icon);
                if (ventas != null && !ventas.isEmpty()) {
                    System.out.println("   ✓ Se encontraron " + ventas.size() + " ventas:");
                    for (VOVentaTotal v : ventas) {
                        System.out.println("      " + v.getNumero());
                    }
                    System.out.println();
                } else {
                    System.out.println("   ✗ No hay ventas\n");
                }
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("6. Obteniendo total recaudado con totalRecaudado...");
            try {
                double total = daoV.totalRecaudado(icon);
                System.out.println("   ✓ Total recaudado: $" + String.format("%.2f", total) + "\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            System.out.println("7. Borrando todas las ventas del producto P001 con borrarVentas...");
            try {
                daoV.borrarVentas(icon);
                System.out.println("   ✓ Ventas de P001 eliminadas\n");
            } catch (PersistenciaException e) {
                System.out.println("   ✗ Error: " + e.getMessage() + "\n");
            }
            
            // ===== LIBERACIÓN DE CONEXIÓN =====
            System.out.println("===== LIBERANDO CONEXIÓN =====");
            pool.liberarConexion(icon, true);
            System.out.println("✓ Conexión liberada exitosamente\n");
            
            System.out.println("===== PRUEBAS COMPLETADAS =====");
            
        } catch (Exception e) {
            System.out.println("\n✗ Error general:");
            System.out.println("   " + e.getMessage());
            e.printStackTrace();
        }
    }
}
