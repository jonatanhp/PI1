/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author jonatan
 */
public class ProductoData {
    static Connection cn = Conexion.connectSQLite();
    static PreparedStatement ps;
   

    //static Date dt = new Date();
    //static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT);

    public static int create(Producto d) {
        int rsId = 0;
        String[] returns = {"idProducto"};
        String sql = "INSERT INTO Producto(nombreProd,stock,idTipo, nomTipo,precio) " //activo
                + "VALUES(?,?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNombreProd());
            ps.setInt(++i, d.getStock());
            ps.setInt(++i, d.getIdTipo());
            ps.setString(++i, d.getNomTipo());
            ps.setDouble(++i, d.getPrecio());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            System.out.println("producto insertado");
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idProducto)  from Producto
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
            System.out.println("create.rsId:" + rsId);
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            
        }
        return rsId;
    }

    public static int update(Producto d) {
        System.out.println("actualizar d.getId(): " + d.getIdProducto());
        int comit = 0;
        String sql = "UPDATE Producto SET "
                + "nombreProd=?, "
                + "stock=?, "
                + "idTipo=?, "
                + "nomTipo=?, "
                + "precio=? "
                + "WHERE idProducto=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(++i, d.getNombreProd());
            ps.setInt(++i, d.getStock());
            ps.setInt(++i,d.getIdTipo());
            ps.setString(++i, d.getNomTipo());
            ps.setDouble(++i, d.getPrecio());
            ps.setInt(++i, d.getIdProducto());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
        }
        return comit;
    }

    public static int delete(int idProducto) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM Producto WHERE idProducto = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Producto> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Producto> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM Producto ORDER BY idProducto";
        } else {
            sql = "SELECT * FROM Producto WHERE (idProducto LIKE'" + filter + "%' OR "
                    + "nombres LIKE'" + filter + "%' OR cod LIKE'" + filter + "%' OR "
                    + "idProducto LIKE'" + filter + "%') "
                    + "ORDER BY nombres";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto d = new Producto();
                d.setIdProducto(rs.getInt("idProducto"));

                

                d.setNombreProd(rs.getString("nombreProd"));
                d.setStock(rs.getInt("stock"));
                d.setIdTipo(rs.getInt("idTipo"));

                d.setNomTipo(rs.getString("nomTipo"));
                d.setPrecio(rs.getDouble("precio"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }
    
    public static List<Producto> listCmb(String filter) {
        List<Producto> ls = new ArrayList();
        ls.add(new Producto("Seleccione..."));
        ls.addAll(list(filter));
        return ls;
    }
    
     public static List<Producto> listActivesByCliente(int idTipo) {
        System.out.println("listByClientessss.idTipo:" + idTipo);
        String sql = "";
        List<Producto> ls = new ArrayList<Producto>();

        sql = " SELECT * FROM Producto "
                + " WHERE idTipo = " + idTipo
                + " ORDER BY idProducto ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto d = new Producto();
                d.setIdProducto(rs.getInt("id"));
               

                d.setNombreProd(rs.getString("nombreProd"));
                d.setStock(rs.getInt("stock"));
                d.setIdTipo(rs.getInt("idTipo"));

                d.setNomTipo(rs.getString("nomTipo"));
                d.setPrecio(rs.getDouble("precio"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }

    

    public static Producto getByPId(int idProducto) {
        Producto d = new Producto();

        String sql = "SELECT * FROM Producto WHERE idProducto = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idProducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdProducto(rs.getInt("idProducto"));

                

                d.setNombreProd(rs.getString("nombreProd"));
                d.setStock(rs.getInt("stock"));
                d.setIdTipo(rs.getInt("idTipo"));

                d.setNomTipo(rs.getString("nomTipo"));
                d.setPrecio(rs.getDouble("precio"));

            }
        } catch (SQLException ex) {
            
        }
        return d;
    }
    
}
