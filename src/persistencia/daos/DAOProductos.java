package persistencia.daos;
import persistencia.consultas.*;
import logica.valueobjects.*;
import logica.Producto;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import logica.excepciones.PersistenciaException;
import poolconexiones.*;

public class DAOProductos implements IDAOProductos{

    public DAOProductos(){
    }

    public boolean member (String codP, IConexion  icon) throws PersistenciaException{
        try{
            boolean existe = false;
            Conexion cx = (Conexion)icon;
            Connection con = cx.getConnection();

            Consultas cons = new Consultas();
            String query = cons.productoExiste();

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, codP);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                existe = true;
            rs.close();
            pstmt.close();

            return existe;
        }
        catch(SQLException e1){
            throw new PersistenciaException("error de conexion a bd", e1);
        }
    }

    public void insert (Producto prod, IConexion icon) throws PersistenciaException{
        try{
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.altaProducto();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, prod.getCodigo());
            pst.setString(2, prod.getNombre());
            pst.setInt(3, prod.getPrecio());

            pst.executeUpdate();
            pst.close();

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

    public Producto find (String codP, IConexion icon) throws PersistenciaException{
        try{
            Producto prod = null;
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.productoExiste();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, codP);
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String cod = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int precio = rs.getInt("precio");
                DAOVentas dao = new DAOVentas(cod);
                prod = new Producto(cod,nombre,precio, dao);
            }
            rs.close();
            pst.close();

            return prod;
            
        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    
    }

    public void delete (String codP, IConexion icon) throws PersistenciaException {
        try{
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.borrarProd();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, codP);

            pst.executeUpdate();
            pst.close();

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

    public boolean esVacio (IConexion icon) throws PersistenciaException {
        try{
            boolean vacio = true;
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.listadoProductos();

            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                vacio = false;
                
            rs.close();
            stmt.close();

            return vacio;

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);

        }
    }

   public List<VOProducto> listarProductos (IConexion icon) throws PersistenciaException{
     try{
            List<VOProducto> lvop = new LinkedList<>();

            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.listadoProductos();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
               String cod = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int precio = rs.getInt("precio");

                VOProducto vop = new VOProducto(cod, nombre, precio);
                lvop.add(vop);
            }
            rs.close();
            stmt.close();
            return lvop;

         }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
   }

    public VOProdVentas ProductoMasVendido(IConexion icon) throws PersistenciaException{
        try{
            //inicializar el value object con el constructor normal con nulls en los parametros
            VOProdVentas vopv = null;
            
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.productoMasUnidadesVendidas();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()){
               String cod = rs.getString("p.codigo");
                String nombre = rs.getString("p.nombre");
                int precio = rs.getInt("p.precio");
                int unidades = rs.getInt("total_unidades");

                 vopv = new VOProdVentas(cod, nombre, precio, unidades);

            }
            rs.close();
            stmt.close();
            return vopv;

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }
}