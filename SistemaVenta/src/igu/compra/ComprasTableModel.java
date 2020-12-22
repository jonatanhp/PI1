package igu.compra;


import igu.producto.*;
import igu.tipo.*;
import data.CompraData;
import modelo.Compra;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asullom
 */
public class ComprasTableModel extends AbstractTableModel {

    private List<Compra> lis = new ArrayList();
    private String[] columns = {"#","id Compra", "Cliente","Fecha compra"};
    private Class[] columnsType = {Integer.class,Integer.class, String.class,Date.class};

    public ComprasTableModel() {
        lis = CompraData.list("");
    }

    public ComprasTableModel(String filter) {

        lis = CompraData.list(filter);
    }

   // public List<Compra> getRegistros() {
  //      return CienteData.listCmb("");
   // }

    // public List<Compra> getlist(String filter) {
    //    lis = CienteData.list(filter);
    //    return lis;
    // }
    @Override
    public Object getValueAt(int row, int column) {
        Compra d = (Compra) lis.get(row);
        switch (column) {
            case 0:
                return row + 1;
             case 1:
                return d.getIdCompra();
            case 2:
                return d.getNomCliente();
            case 3:
                return d.getFechaCompra();
           
           
            default:
                return null;
        }
    }

    /*
    @Override
    public void setValueAt(Object valor, int row, int column) {
        Compra d = (Compra) lis.get(row);
        switch (column) {
            
           // case 0:
           //     int id = 0;
           //     try {
            //        if (valor instanceof Number) {
           //             id = Integer.parseInt("" + valor);
           //         }
           //     } catch (NumberFormatException nfe) {
            //        System.err.println("" + nfe);
             //   }
            //    d.setId(id);
             //   break;
             
            case 1:
                d.setNombres("" + valor);
                break;
            case 2:
                d.setInfoadic("" + valor);
                break;

        }
        this.fireTableRowsUpdated(row, row);
        //fireTableCellUpdated(row, row);
    }
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        //Compra c = (Compra) lis.get(row);
        if (column >= 0 && column != 0) {
            //return true;
        }
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

    
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public void addRow(Compra d) { // con db no se usa
        this.lis.add(d);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lis.size(), lis.size());
    }

    public void removeRow(int linha) { // con db no se usa
        this.lis.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return lis.get(row);
    }

}
