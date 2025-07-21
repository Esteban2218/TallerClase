package app.vista;

import app.controlador.Servicio;
import app.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class VistaProducto extends JFrame {
    private JPanel panel1;
    private JTextField txtNombre;
    private JButton btnNuevo;
    private JButton btnMostrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTable tablaProductos;
    private JTextField txtCodigo;
    private JTextField txtPrecio;
    private JButton imprimirReporteButton;
    private Servicio controlador=new Servicio();
    private Object[] columns={"Id","Código","Producto","Precio"};
    private Object[]row=new Object[4];
    private Map<Integer, Producto> mapa=null;
    private DefaultTableModel model;
    private String clave;

    public VistaProducto() {
        setTitle("Catálogo");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setLocationRelativeTo(null);
        cargarTabla();
        obtenerRegistrisTabla();
        limpiarCampos();

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i =tablaProductos.getSelectedRow();
                clave= model.getValueAt (i, 0).toString();
                txtCodigo. setText(model.getValueAt(i, 1). toString());
                txtNombre. setText(model.getValueAt(i, 2). toString());
                txtPrecio. setText(model.getValueAt (i, 3). toString());
                //super.mouseClicked(e);
            }
        });
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerRegistrisTabla();
            }
        });

        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo=txtCodigo.getText();
                String nombre=txtNombre.getText();
                double precio=Double.parseDouble(txtPrecio.getText());
                controlador. insertar (new Producto (codigo, nombre, precio));
                obtenerRegistrisTabla();
            }
        });
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id=Integer.parseInt(clave);
                String codigo=txtCodigo.getText();
                String nombre=txtNombre.getText();
                String precioStr=txtPrecio.getText().replace(",",".");
                double precio=Double.parseDouble(precioStr);
                controlador. actualizar (new Producto(id, codigo, nombre, precio));
                obtenerRegistrisTabla();
            }
        });


        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id=Integer.parseInt(clave);
                controlador.eliminar(id);
                obtenerRegistrisTabla();
            }
        });
        imprimirReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tablaProductos.print();
                }catch (Exception e2){
                    System.out.println(e2.getMessage());
                }
            }
        });
    }

    private void cargarTabla(){
        String [] columnas={"Id","Codigo","nombre","precio"};
        Object[] []datos={
        };
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        tablaProductos.setModel(modelo);
    }

    public void obtenerRegistrisTabla(){
        model=new DefaultTableModel(){
            private static final long serialVersionUID= 1L;
            @Override
            public boolean isCellEditable(int filas, int columnas)
            {return false;}
        };
        model.setColumnIdentifiers(columns);
        while (model.getRowCount() > 0) {model.removeRow(0);}
        mapa=controlador.seleccionarTodo();
        for(Map.Entry<Integer, Producto> entry:mapa.entrySet()){
            row[0]=entry.getKey();
            row[1]=entry.getValue().getCodigo();
            row[2]=entry.getValue().getNombre();
            row[3]=String.format("%.2f",entry.getValue().getPrecio());
            model.addRow(row);
        }


        tablaProductos.setModel(model);

        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(50);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(50);

        limpiarCampos();
    }

    // limpiar las cajas de texto
    public void limpiarCampos(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaProducto().setVisible(true);
        });
    }
}