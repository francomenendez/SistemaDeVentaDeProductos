package persistencia.consultas;

public class Consultas {

    public Consultas() {

	}

	public String productoExiste() {
		return "SELECT * FROM Productos p WHERE p.Codigo = ?";
	}

	public String ventaExiste() {
		return "SELECT v.codProd FROM VENTAS V WHERE V.codProd = ? AND V.numero = ?";
	}

	public String altaProducto() {
		return "INSERT INTO PRODUCTOS(codigo,nombre,precio) VALUES(?,?,?)";
	}

	public String borrarVentasProd() {
		return "DELETE FROM VENTAS V WHERE V.codProd = ?";
	}

	public String borrarProd() {
		return "DELETE FROM PRODUCTOS P WHERE P.codigo = ?";
	}

	public String ultimoNro() {
		return "SELECT MAX(V.Numero) AS Ultimo FROM VENTAS V WHERE V.codProd = ?";
	}
	
	public String registrarVenta() {
		return "INSERT INTO VENTAS(numero, codProd,unidades,cliente) VALUES (?,?,?,?)";
	}

	public String datosVentas() {
		return "SELECT V.unidades, V.cliente FROM VENTAS V WHERE V.numero = ? AND V.codProd = ?";
	}

	public String listadoProductos() {
		return "SELECT * FROM PRODUCTOS P ORDER BY P.codigo";
	}

	public String listadoVentasProd() {
		return "SELECT * FROM VENTAS V WHERE V.codProd = ? ORDER BY V.CodProd";
	}

    public String productoMasUnidadesVendidas() {
        return "SELECT p.codigo, p.nombre, p.precio, SUM(v.unidades) AS total_unidades " +
               "FROM Productos p " +
               "JOIN Ventas v ON p.codigo = v.codProd " +
               "GROUP BY p.codigo, p.nombre, p.precio " +
               "ORDER BY total_unidades DESC " +
               "LIMIT 1;";
    }

	public String totalRecaudadoPorVentas() {
		return "SELECT SUM(P.Precio * V.Unidades) AS TOTAL FROM PRODUCTOS P, VENTAS V WHERE P.Codigo = ? AND V.CodProd = P.Codigo";
	}

}
