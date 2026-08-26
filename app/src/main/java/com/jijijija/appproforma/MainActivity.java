package com.jijijija.appproforma;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtcodigo, txtProducto, txtPrecio, txtCantidad;
    private Button btnGrabar, btnEditar, btnEliminar, btnNuevo;
    private ListView listProforma;
    private TextView txtResultado;
    ArrayList<ProformaItem> lista = new ArrayList<>();
    ArrayAdapter<ProformaItem> adaptador;
    int posicionSeleccionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicio Programacion
        txtcodigo = findViewById(R.id.txtcodigo);
        txtProducto = findViewById(R.id.txtProducto);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtResultado = findViewById(R.id.txtResultado);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listProforma = findViewById(R.id.listProforma);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listProforma.setAdapter(adaptador);

        // Nuevo
        btnNuevo.setOnClickListener(v -> {
            txtcodigo.setText("");
            txtProducto.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("");
            txtResultado.setText("S/. 0.00");
            txtcodigo.requestFocus(); // cursor en el primer campo
        });

        // Grabar
        btnGrabar.setOnClickListener(v -> {
            String dni = txtcodigo.getText().toString();
            String prod = txtProducto.getText().toString();
            double precio = Double.parseDouble(txtPrecio.getText().toString());
            int cant = Integer.parseInt(txtCantidad.getText().toString());

            // Operacion Aritmetica
            double total = precio * cant;
            txtResultado.setText("Total: S/. " + total);

            lista.add(new ProformaItem(dni, prod, precio, cant));
            adaptador.notifyDataSetChanged();
            limpiarCampos();
        });

        // Seleccionar item
        listProforma.setOnItemClickListener((parent, view, position, id) -> {
            posicionSeleccionada = position;
            ProformaItem item = lista.get(position);
            txtcodigo.setText(item.getCodigo());
            txtProducto.setText(item.getProducto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));
            txtCantidad.setText(String.valueOf(item.getCantidad()));
            txtResultado.setText("S/. " + item.getTotal());
        });

        // Editar
        btnEditar.setOnClickListener(v -> {
            if (posicionSeleccionada != -1) {
                ProformaItem item = lista.get(posicionSeleccionada);
                item.setProducto(txtProducto.getText().toString());
                item.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
                item.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            }
        });

        // Eliminar
        btnEliminar.setOnClickListener(v -> {
            if (posicionSeleccionada != -1) {
                lista.remove(posicionSeleccionada);
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            }
        });
        // Fin Programacion

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Inicio Implementar
    private void limpiarCampos() {
        txtcodigo.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        posicionSeleccionada = -1;
    }
    // Fin Implementacion
}