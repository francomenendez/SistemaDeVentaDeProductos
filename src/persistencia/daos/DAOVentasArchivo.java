package persistencia.daos;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import logica.Venta;
import logica.excepciones.PersistenciaException;
import logica.valueobjects.*;
import poolconexiones.*;

public class DAOVentasArchivo implements IDAOVentas {

	private static final String pre= "VENTA-";
	private static final String post = ".txt";

	private  String carpetaV;
	private  String carpetaP;
	private  String codP;

	public DAOVentasArchivo(String codigo){
		this.carpetaV = "Ventas/";
		this.carpetaP = "Productos/";
		this.codP = codigo;
	}

	public void insback(Venta v, IConexion icon) throws PersistenciaException {
 
        File dir = new File(carpetaV);
		if (!dir.exists() && !dir.mkdirs()) {
			throw new PersistenciaException("No se pudo crear la carpeta de ventas: " + carpetaV, new Exception());
		}
        
		File archivoVenta = new File(carpetaV + pre + codP + "-" + v.getNumero() + post);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoVenta))) {
			bw.write(String.valueOf(v.getNumero()));
			bw.newLine();
			bw.write(codP);
			bw.newLine();
			bw.write(String.valueOf(v.getUnidades()));
			bw.newLine();
			bw.write(v.getCliente());
		} catch (IOException e) {
			throw new PersistenciaException("Error al guardar la venta del producto " + codP,e);
		}
	}

	public List<VOVentaTotal> listarVentas(IConexion icon) throws PersistenciaException{
		List<VOVentaTotal> ventas = new LinkedList<>();
		File dir = new File(carpetaV);
		File[] archivos = dir.listFiles(
				(d, nombre) -> nombre.startsWith(pre + codP + "-") && nombre.endsWith(post));

		if (archivos != null) {
           
			for (File f : archivos) {
				try (BufferedReader br = new BufferedReader(new FileReader(f))) {
					int numero = Integer.parseInt(br.readLine());
					br.readLine();
					int unidades = Integer.parseInt(br.readLine());
					String cliente = br.readLine();

                    ventas.add(new VOVentaTotal(unidades,cliente, numero, codP));
				} catch (IOException | NumberFormatException e) {
					throw new PersistenciaException("Error recorriendo archivos", e);
				}
			}
		}
	
		return ventas;
	}

	public Venta k_esimo( int numero, IConexion icon) throws PersistenciaException {
		File f = new File(carpetaV + pre + codP + "-" + numero + post);
		if (!f.exists())
			return null;

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			int num = Integer.parseInt(br.readLine());
			br.readLine();
			int unidades = Integer.parseInt(br.readLine());
			String cliente = br.readLine();
			return new Venta(num, unidades, cliente);
		} catch (IOException | NumberFormatException e) {
			throw new PersistenciaException("Error leyendo la venta " + numero + " del producto " + codP, e);
		}
	}

	public double totalRecaudado(IConexion icon) throws PersistenciaException {
		File productoFile = new File(carpetaP + "PROD-" + codP + ".txt");

		if (!productoFile.exists()) {
			throw new PersistenciaException("No se encontro el archivo del producto: " + codP, new Exception());
		}

		double precio;
		try (BufferedReader brProd = new BufferedReader(new FileReader(productoFile))) {
			brProd.readLine(); // codigo
			brProd.readLine(); // nombre
			precio = Double.parseDouble(brProd.readLine());
		} catch (IOException | NumberFormatException e) {
			throw new PersistenciaException("Error leyendo el archivo del producto " + codP, e);
		}

		// Calcular total recorriendo archivos de venta directamente (sin crear listas intermedias)
		double total = 0.0;
		File dir = new File(carpetaV);
		File[] archivos = dir.listFiles((d, nombre) -> nombre.startsWith(pre + codP + "-") && nombre.endsWith(post));
		if (archivos != null) {
			for (File f : archivos) {
				try (BufferedReader br = new BufferedReader(new FileReader(f))) {
					br.readLine(); // numero
					br.readLine(); // carpetaP (segunda linea en tu formato)
					int unidades = Integer.parseInt(br.readLine());
					total += unidades * precio;
				} catch (IOException | NumberFormatException e) {
					throw new PersistenciaException("Erro recorriendo archivos", e);
					// // Ignorar archivos problemáticos y continuar
					// System.err.println("Archivo de venta ignorado por error: " + f.getName() + " (" + e.getMessage() + ")");
				}
			}
		}
		return total;
	}

    public int largo(IConexion icon) throws PersistenciaException{
        int largo = 0;        
		File dir = new File(carpetaV);
		File[] archivos = dir.listFiles(
				(d, nombre) -> nombre.startsWith(pre + codP + "-") && nombre.endsWith(post));

		if (archivos != null) 
            largo = archivos.length;

        return largo;
    }

	public void borrarVentas(IConexion icon) throws PersistenciaException {
        try{
            File dir = new File(carpetaV);
            if (!dir.exists())
                return;

            File[] archivos = dir.listFiles(
                    (d, nombre) -> nombre.startsWith(pre + codP + "-") && nombre.endsWith(post));

            if (archivos != null) {
                for (File f : archivos) {
                    f.delete();
                }
            }
	    }catch(NullPointerException e1){
            throw new PersistenciaException("Error de conexion", e1);
        }
    }
}
