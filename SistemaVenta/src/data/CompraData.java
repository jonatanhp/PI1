/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import modelo.Compra;
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
public class CompraData {
    static Connection cn = Conexion.connectSQLite();
    static PreparedStatement ps;
   

    //static Date dt = new Date();
    //static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT);

    public static int create(Compra d) {
        int rsId = 0;
        String[] returns = {"idCompra"};
        String sql = "INSERT INTO Compra(id_cliente,nom_cliente) " //activo
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdCliente());
            ps.setString(++i, d.getNomCliente());
           
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idCompra)  from Compra
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

    public static int update(Compra d) {
        System.out.println("actualizar d.getId(): " + d.getIdCompra());
        int comit = 0;
        String sql = "UPDATE Compra SET "
                + "id_cliente=?, "
                + "nom_cliente=? "
                
                + "WHERE idCompra=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, d.getIdCliente());
            ps.setString(++i, d.getNomCliente());
            
            
            ps.setInt(++i, d.getIdCompra());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
        }
        return comit;
    }

    public static int delete(int idCompra) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM Compra WHERE idCompra = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idCompra);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }
    
     public static List<Compra> listCmb(String filter) {
        List<Compra> ls = new ArrayList();
        ls.add(new Compra(0));
        ls.addAll(list(filter));
        return ls;
    }

    public static List<Compra> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Compra> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM Compra ORDER BY idCompra";
        } else {
            sql = "SELECT * FROM Compra WHERE (idCompra LIKE'" + filter + "%' OR "
                    + "nombres LIKE'" + filter + "%' OR cod LIKE'" + filter + "%' OR "
                    + "idCompra LIKE'" + filter + "%') "
                    + "ORDER BY nombres";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Compra d = new Compra();
                d.setIdCompra(rs.getInt("idCompra"));

                
                d.setIdCliente(rs.getInt("id_cliente"));
                d.setNomCliente(rs.getString("nom_cliente"));
                
               
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }
    
     public static List<Compra> listActivesByCliente(int idTipo) {
        System.out.println("listByCliente.idTipo:" + idTipo);
        String sql = "";
        List<Compra> ls = new ArrayList<Compra>();

        sql = " SELECT * FROM Compra "
                + " WHERE id_cliente = " + idTipo
                + " ORDER BY idCompra ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Compra d = new Compra();
                d.setIdCompra(rs.getInt("idCompra"));

                
                d.setIdCliente(rs.getInt("id_cliente"));
                
                d.setNomCliente(rs.getString("nom_cliente"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            
        }
        return ls;
    }

    

    public static Compra getByPId(int idCompra) {
        Compra d = new Compra();

        String sql = "SELECT * FROM Compra WHERE idCompra = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idCompra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 d.setIdCompra(rs.getInt("idCompra"));

                
                d.setIdCliente(rs.getInt("id_cliente"));
                
                d.setNomCliente(rs.getString("nom_cliente"));
            }
        } catch (SQLException ex) {
            
        }
        return d;
    }
    
}
