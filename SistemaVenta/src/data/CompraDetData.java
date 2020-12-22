/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import modelo.CompraDet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jonatan
 */
public class CompraDetData {
     static Connection cn = Conexion.connectSQLite();
    static PreparedStatement ps;
    

    
    public static int create(CompraDet d) {
        
        System.out.println("inicio crear");
        int rsId = 0;
        String[] returns = {"idCompra_det"};
        String sql = "INSERT INTO Compra_det( nomProducto,cantidad, precio,  parcial,  id_compra,  id_producto) " //activo
                + "VALUES(?,?,?,?   ,?,?)";
        int i = 0;
        try {
            System.out.println("fin crear");
            ps = cn.prepareStatement(sql,returns);
            //ps.setInt(1, d.getIdCompraDet());
             System.out.println("fin crearrrrr");
            ps.setString(++i, d.getNomProducto());
            ps.setInt(++i, d.getCantidad());
            ps.setDouble(++i, d.getPrecio());
            ps.setDouble(++i, d.getParcial());
            
            ps.setInt(++i, d.getIdCompra());
            ps.setInt(++i, d.getIdProducto());
            
             System.out.println(d.getIdCompraDet());
            rsId = ps.executeUpdate();// 0 no o 1 si commit 
           
            System.out.println("fin crear");
            
            System.out.println("fin adsfadsf");
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idCompra_det)  from Compra_det
                    //System.out.println("rs.getInt(rsId): " + rsId);
                    // soy sullon local
                    //
                    System.out.println(" rs.getInt(rsId): " + rsId);
                    
                }
                rs.close();
                System.out.println("fin crear");
                System.out.println(rsId);
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            
        }
        return rsId;
        
    }

    public static int update(CompraDet d) {
        System.out.println("actualizar d.getId(): " + d.getIdCompraDet());
        int comit = 0;
        String sql = "UPDATE Compra_det SET "
                + "cantidad=?, "
                + "precio=?, "
                + "parcial=?, "
               
                + "id_producto=? "
                
                + "WHERE idCompra_det=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setInt(++i, d.getCantidad());
            ps.setDouble(++i, d.getPrecio());
            ps.setDouble(++i, d.getParcial());
            ps.setDouble(++i, Math.round(d.getCantidad()* d.getPrecio() * 100.0) / 100.0 ); //ps.setDouble(++i, d.getSubtotal());
            
            //ps.setInt(++i, d.getVenta_id());
            ps.setInt(++i, d.getIdProducto());
            
            ps.setInt(++i, d.getIdCompraDet());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
        }
        return comit;
    }
    

    public static int delete(int idCompra_det) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM Compra_det WHERE idCompra_det = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idCompra_det);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<CompraDet> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<CompraDet> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM Compra_det ORDER BY idCompra_det";
        } else {
            sql = "SELECT * FROM Compra_det WHERE (idCompra_det LIKE'" + filter + "%' OR "
                    + "descripcion LIKE'" + filter + "%' OR cantidad LIKE'" + filter + "%' OR "
                    + "idCompra_det LIKE'" + filter + "%') "
                    + "ORDER BY descripcion";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraDet d = new CompraDet();
                d.setIdCompraDet(rs.getInt("idCompra_det"));
                d.setNomProducto(rs.getString("nomProducto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio(rs.getDouble("precio"));
                
                d.setParcial(rs.getDouble("parcial"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setIdCompra(rs.getInt("id_compra"));
                
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }

    public static CompraDet getByPId(int idCompra_det) {
        CompraDet d = new CompraDet();

        String sql = "SELECT * FROM Compra_det WHERE idCompra_det = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idCompra_det);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               d.setIdCompraDet(rs.getInt("idCompra_det"));
                d.setNomProducto(rs.getString("nomProducto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio(rs.getDouble("precio"));
                
                d.setParcial(rs.getDouble("parcial"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setIdCompra(rs.getInt("id_compra"));
            }
        } catch (SQLException ex) {
            
        }
        return d;
    }
    
     public static List<CompraDet> listCompraDetbyCompra(int idTipo) {
        System.out.println("listByCliente.idTipo:" + idTipo);
        String sql = "";
        List<CompraDet> ls = new ArrayList<CompraDet>();

        sql = " SELECT * FROM Compra_det "
                + " WHERE id_compra = " + idTipo
                + " ORDER BY idCompra_det ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraDet d = new CompraDet();
                d.setIdCompraDet(rs.getInt("idCompra_det"));
               

                d.setIdCompra(rs.getInt("id_compra"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setNomProducto(rs.getString("nomProducto"));

                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio(rs.getDouble("precio"));
                d.setParcial(rs.getDouble("parcial"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }
     
    public static List<CompraDet> listCompraDetbyProducto(int idTipo) {
        System.out.println("listByCliente.idTipo:" + idTipo);
        String sql = "";
        List<CompraDet> ls = new ArrayList<CompraDet>();

        sql = " SELECT * FROM Compra_det "
                + " WHERE id_producto = " + idTipo
                + " ORDER BY idCompra_det ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraDet d = new CompraDet();
                d.setIdCompraDet(rs.getInt("idCompra_det"));
               

                d.setIdCompra(rs.getInt("id_compra"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setNomProducto(rs.getString("nomProducto"));

                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio(rs.getDouble("precio"));
                d.setParcial(rs.getDouble("parcial"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }
    
    
    public static List<CompraDet> listByVenta(int id_compra) { // devolver todas las lineas de una venta

        System.out.println("listByVenta.comp_id:" + id_compra);

        List<CompraDet> ls = new ArrayList();

        String sql = "";
         sql = " SELECT * FROM Compra_det "
                + " WHERE id_compra = " + id_compra + " "
                + " ORDER BY idCompra_det ";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraDet d = new CompraDet();
               d.setIdCompraDet(rs.getInt("idCompra_det"));
               d.setNomProducto(rs.getString("nomProducto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio(rs.getDouble("precio"));
                
                d.setParcial(rs.getDouble("parcial"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setIdCompra(rs.getInt("id_compra"));
                
                ls.add(d);
            }
        } catch (SQLException ex) {
           
        }
        return ls;
    }
}
