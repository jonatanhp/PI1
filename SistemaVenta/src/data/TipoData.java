package data;

import modelo.Tipo;
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
public class TipoData {

    static Connection cn = Conexion.connectSQLite();
    static PreparedStatement ps;
    

    public static int create(Tipo d) {
        int rsId = 0;  //MUESTRA ESTADO
        String[] returns = {"idTipo"};
        String sql = "INSERT INTO Tipo(nomTipo) "
                + "VALUES(?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns); //prepareStatement (sentencia preparada) primera columna
            ps.setString(++i, d.getNomTipo());
            
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) { //getGenerateKeys (obtener llaves generadas)
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idTipo)  from Tipo
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
           
        }
        return rsId;
    }

    public static int update(Tipo d) {
        System.out.println("actualizar d.getId(): " + d.getIdTipo());
        int comit = 0;
        String sql = "UPDATE Tipo SET "
                + "nomTipo=? "
                
                + "WHERE idTipo=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNomTipo());
          
            ps.setInt(++i, d.getIdTipo());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
           
        }
        return comit;
    }

    public static int delete(int idTipo) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM Tipo WHERE idTipo = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idTipo);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Tipo> listCmb(String filter) {
        List<Tipo> ls = new ArrayList();
        ls.add(new Tipo("Seleccione..."));
        ls.addAll(list(filter));
        return ls;
    }

    public static List<Tipo> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Tipo> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM Tipo ORDER BY idTipo";
        } else {
            sql = "SELECT * FROM Tipo WHERE (idTipo LIKE'" + filter + "%' OR "
                    + "nomTipo LIKE'" + filter + "%' OR dni LIKE'" + filter + "%' OR "
                    + "idTipo LIKE'" + filter + "%') "
                    + "ORDER BY nomTipo";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Tipo d = new Tipo();
                d.setIdTipo(rs.getInt("idTipo"));
                d.setNomTipo(rs.getString("nomTipo"));
                
                ls.add(d);
            }
        } catch (SQLException ex) {
           
        }
        return ls;
    }

    public static Tipo getByPId(int idTipo) {
        Tipo d = new Tipo();

        String sql = "SELECT * FROM Tipo WHERE idTipo = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idTipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdTipo(rs.getInt("idTipo"));
                d.setNomTipo(rs.getString("nomTipo"));
               
            }
        } catch (SQLException ex) {
           
        }
        return d;
    }
    /*
    public static voidTipo iniciarTransaccion() {
        try {
            cn.setAutoCommit(false);
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "iniciarTransaccion", ex);
        }
    }

    public static voidTipo finalizarTransaccion() {
        try {
            cn.commit();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "finalizarTransaccion", ex);
        }
    }

    public static voidTipo cancelarTransaccion() {
        try {
            cn.rollback();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "cancelarTransaccion", ex);
        }
    }
     */
}
