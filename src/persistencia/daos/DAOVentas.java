package persistencia.daos;
import persistencia.consultas.*;
import logica.Venta;
import logica.excepciones.PersistenciaException;

import java.sql.*;
import poolconexiones.*;

import java.util.LinkedList;
import java.util.List;
import logica.valueobjects.VOVentaTotal;


public class DAOVentas implements IDAOVentas{
    private String codigoProducto;

    public DAOVentas(String codP){
        codigoProducto = codP;
    }

    public void insback(Venta ven, IConexion icon) throws PersistenciaException{
        try{

            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.registrarVenta();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, ven.getNumero());
            pst.setString(2, this.codigoProducto);
            pst.setInt(3, ven.getUnidades());
            pst.setString(4, ven.getCliente());

            pst.executeUpdate();
            pst.close();

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

    public int largo(IConexion icon) throws PersistenciaException{
        try{
            int largo = 0;

            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.ultimoNro();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, this.codigoProducto);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                largo = rs.getInt("Ultimo");
            
            rs.close();
            pst.close();
            return largo;

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

    public Venta k_esimo(int numV, IConexion icon) throws PersistenciaException{
         try{
            Venta v = null;

            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.datosVentas();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, numV);
            pst.setString(2, this.codigoProducto);

            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                int uni = rs.getInt("V.unidades");
                String cliente = rs.getString("V.cliente");
                v = new Venta(numV,uni,cliente);
            }
            rs.close();
            pst.close();
            return v;

         }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

     public void borrarVentas(IConexion icon) throws PersistenciaException{
        try{

            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.borrarVentasProd();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, this.codigoProducto);

            pst.executeUpdate();
            pst.close();

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

     public List<VOVentaTotal> listarVentas(IConexion icon) throws PersistenciaException{
         try{
            List<VOVentaTotal> lvot = new LinkedList<>();
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();

            Consultas cs = new Consultas();
            String query = cs.listadoVentasProd();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, this.codigoProducto);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int num = rs.getInt("Numero");
                String cliente = rs.getString("Cliente");
                int uni = rs.getInt("Unidades");

                VOVentaTotal vot = new VOVentaTotal(uni, cliente, num, this.codigoProducto);
                lvot.add(vot);
            }
            rs.close();
            pst.close();
            return lvot;

         }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }

    public double totalRecaudado(IConexion icon) throws PersistenciaException{
        try{
            
            double total = 0.0;
            Conexion cx = (Conexion) icon;
            Connection con = cx.getConnection();
            Consultas cs = new Consultas();
            String query = cs.totalRecaudadoPorVentas();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, this.codigoProducto);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                total = rs.getDouble("TOTAL");
            }
            rs.close();
            pst.close();
            return total;

        }catch(SQLException e1){
            throw new PersistenciaException("Error en la conexion a BD", e1);
        }
    }
}
