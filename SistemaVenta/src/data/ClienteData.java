package data;

import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;

/**
 *
 * @author Asullom
 */
public class ClienteData {

    static Connection cn = Conexion.connectSQLite();
    static PreparedStatement ps;
    

    public static int create(Cliente d) {
        int rsId = 0;  //MUESTRA ESTADO
        String[] returns = {"id"};
        String sql = "INSERT INTO Cliente(nombre, dni) "
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns); //prepareStatement (sentencia preparada) primera columna
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getDni());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) { //getGenerateKeys (obtener llaves generadas)
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from cliente
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
           
        }
        return rsId;
    }

    public static int update(Cliente d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE Cliente SET "
                + "nombre=?, "
                + "dni=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getDni());
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
           
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Cliente> listCmb(String filter) {
        List<Cliente> ls = new ArrayList();
        ls.add(new Cliente("Seleccione..."));
        ls.addAll(list(filter));
        return ls;
    }

    public static List<Cliente> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Cliente> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM Cliente ORDER BY id";
        } else {
            sql = "SELECT * FROM cliente WHERE (id LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR dni LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente d = new Cliente();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setDni(rs.getString("dni"));
                ls.add(d);
            }
        } catch (SQLException ex) {
           
        }
        return ls;
    }
    
    

    public static Cliente getByPId(int id) {
        Cliente d = new Cliente();

        String sql = "SELECT * FROM cliente WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setDni(rs.getString("dni"));
            }
        } catch (SQLException ex) {
           
        }
        return d;
    }
    /*
    public static void iniciarTransaccion() {
        try {
            cn.setAutoCommit(false);
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "iniciarTransaccion", ex);
        }
    }

    public static void finalizarTransaccion() {
        try {
            cn.commit();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "finalizarTransaccion", ex);
        }
    }

    public static void cancelarTransaccion() {
        try {
            cn.rollback();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "cancelarTransaccion", ex);
        }
    }
     */
}
