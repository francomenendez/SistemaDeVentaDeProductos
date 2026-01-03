package persistencia.daos;
import persistencia.consultas.*;
import logica.valueobjects.*;
import logica.Producto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import logica.excepciones.*;
import poolconexiones.*;

public class DAOProductosArchivo implements IDAOProductos{
    private static final String pre= "PROD-";
	private static final String post = ".txt";

	private String carpetaV;
	private String carpetaP;
    
    public DAOProductosArchivo() {
		this.carpetaV = "Ventas/";
		this.carpetaP = "Productos/";
	}

    public boolean member(String codP, IConexion icon) {
		File f = new File(carpetaP + pre  + codP+ post);
		return f.exists();
	}

   
    public void insert (Producto prod, IConexion icon) throws PersistenciaException{
        File dir = new File(carpetaP);
		//verifica que exista o se haya creado la carpeta correctamente (ingresa al throw solo en caso de error)
		if (!dir.exists() && !dir.mkdirs()) {
			throw new PersistenciaException("No se pudo crear la carpeta de productos: " + carpetaP, new Exception());
		}
        
        String cod = prod.getCodigo();
        
		File archivoProd = new File(carpetaP + pre + cod + post);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoProd))) {
			bw.write(String.valueOf(cod));
			bw.newLine();
			bw.write(prod.getNombre());
			bw.newLine();
			bw.write(String.valueOf(prod.getPrecio()));
		} catch (IOException e) {
			throw new PersistenciaException("Error al insertar el producto " + cod,e);
		}
    }

    public Producto find(String codP, IConexion icon) throws PersistenciaException {
		File f = new File(carpetaP + pre + codP + post);
		if (!f.exists())
			return null;

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String codigo = br.readLine();
			String nombre = br.readLine();
			int precio = Integer.parseInt(br.readLine());
			DAOVentasArchivo dao = new DAOVentasArchivo(codigo);
			return new Producto(codigo, nombre, precio, dao);
		} catch (IOException | NumberFormatException e) {
			throw new PersistenciaException("Error al obtener producto: " + codP, e);
		}
	}

    public void delete (String codP, IConexion icon) throws PersistenciaException {
        try{
            Producto prod = find(codP,icon);
            prod.borrarVentas(icon);

            File f = new File(carpetaP + pre + codP + post);
            f.delete();

        }catch(NullPointerException e1){
            throw new PersistenciaException("Error en la conexion ", e1);
        }
    }

    public boolean esVacio (IConexion icon) throws PersistenciaException {
        File dir = new File(carpetaP);
        try {
            if (!dir.exists())
                return true;

            if (!dir.isDirectory())
                throw new PersistenciaException("La ruta de productos no es un directorio: " + carpetaP, new Exception());

            File[] archivos = dir.listFiles((d, nombre) -> nombre.startsWith(pre) && nombre.endsWith(post));

            if (archivos == null)
                throw new PersistenciaException("No se pudo acceder al directorio de productos: " + carpetaP, new Exception());

            return archivos.length == 0;
        } catch (SecurityException se) {
            throw new PersistenciaException("Permiso denegado al acceder a: " + carpetaP, se);
        }
    }

   public List<VOProducto> listarProductos (IConexion icon) throws PersistenciaException{
        List<VOProducto> productos = new LinkedList<>();
		File dir = new File(carpetaP);
		File[] archivos = dir.listFiles(
				(d, nombre) -> nombre.startsWith(pre) && nombre.endsWith(post));

		if (archivos != null) {
           
			for (File f : archivos) {
				try (BufferedReader br = new BufferedReader(new FileReader(f))) {
					String codigo = br.readLine();
					String nombre =  br.readLine();
					int precio = Integer.parseInt(br.readLine());

                    productos.add(new VOProducto(codigo,nombre, precio));
				} catch (IOException | NumberFormatException e) {
					throw new PersistenciaException("Error recorriendo archivos", e);
				}
			}
		}
	
		return productos;
    }

    public VOProdVentas ProductoMasVendido(IConexion icon) throws PersistenciaException {
		File vCarpeta = new File(this.carpetaV);
		if (!vCarpeta.exists())
			return null;

		Map<String, Integer> ventasProducto = new HashMap<>();
		File[] archivos = vCarpeta.listFiles((d, n) ->
        n.startsWith("VENTA-") && n.endsWith(post));
		if (archivos == null || archivos.length == 0 )
			return null;

		for (File venta : archivos) {
			try (BufferedReader br = new BufferedReader(new FileReader(venta))) {
				br.readLine();
				String codigo = br.readLine().trim();
				int unidades = Integer.parseInt(br.readLine().trim());
				ventasProducto.merge(codigo, unidades, Integer::sum);
			} catch (IOException | NumberFormatException e) {
				System.err.println("Error leyendo " + venta.getName());
			}
		}

		Map.Entry<String, Integer> maxV = ventasProducto.entrySet().stream().max(Map.Entry.comparingByValue())
				.orElse(null);

		if (maxV == null)
			return null;

		String codigo = maxV.getKey();
		int maxUnidades = maxV.getValue();

		File productoFile = new File(carpetaP + pre + codigo + post);
		String nombre = " ";
		int precio = 0;

		if (productoFile.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(productoFile))) {
				codigo = br.readLine();
				//br.readLine();
				nombre = br.readLine().trim();
				precio = Integer.parseInt(br.readLine().trim());
			} catch (IOException | NumberFormatException e) {
				System.err.println("Error obteniendo producto " + codigo);
			}
		}

		return new VOProdVentas(codigo, nombre, precio, maxUnidades);
	}



}
