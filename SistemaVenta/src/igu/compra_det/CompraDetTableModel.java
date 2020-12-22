package igu.compra_det;

import data.CompraData;
import data.CompraDetData;
import modelo.Compra;
import modelo.CompraDet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asullom
 */
public class CompraDetTableModel extends AbstractTableModel {

    private List<CompraDet> lis = new ArrayList();
    private String[] columns = {"#","Id compra", "Id producto", "Cantidad ", "Precio", "Parcial", };
    private Class[] columnsType = {Integer.class,Integer.class, Integer.class, Integer.class, Double.class, Double.class};

    public CompraDetTableModel() {
         lis = CompraDetData.list("");
    }

    /*public CompraDetTableModel(Compra d) {
        this.lis = CompraDetData.listByVenta(d.getIdCompra());
        this.lis.add(new CompraDet());
    }*/

    public CompraDetTableModel(String filter) {

        lis = CompraDetData.list(filter);
    }

    // public List<CompraDet> getRegistros() {
    //      return CienteData.listCmb("");
    // }
    // public List<CompraDet> getlist(String filter) {
    //    lis = CienteData.list(filter);
    //    return lis;
    // }
    @Override
    public Object getValueAt(int row, int column) {
        CompraDet d = (CompraDet) lis.get(row);
        switch (column) {
            case 0:
                return row + 1;
            case 1:
                return d.getIdCompra();
            case 2:
                return d.getIdProducto();
            
            case 3:
                return d.getCantidad();
            case 4:
                return d.getPrecio();
            case 5:
                return d.getParcial();
            

            default:
                return null;
        }
    }

    @Override
    

    
    public boolean isCellEditable(int row, int column) {
        
        //Compra ventaSelected = VentaData.getByPId(d.getVenta_id());
        //System.out.println("d.getProd_id() : " + d.getProd_id());
        //System.out.println("column : " + column);
       

        return false;//bloquear edicion
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnsType[column];
    }

    @Override
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

   

    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return lis.get(row);
    }

}
