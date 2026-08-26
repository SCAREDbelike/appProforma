package com.jijijija.appproforma;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCodigo, etProducto, etPrecio, etCantidad;
    private TextView tvTotal;
    private Button btnNuevo, btnGrabar, btnActualizar, btnEliminar;

    private final ArrayList<ProformaItem> lista = new ArrayList<>();
    private ProformaAdapter adaptador;
    private int posicionSeleccionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Desactiva el modo oscuro para evitar cajas oscuras o texto ilegible
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        // Carga tu diseño de la proforma
        setContentView(R.layout.activity_main);

        // Inicio Programacion
        etCodigo = findViewById(R.id.etCodigo);
        etProducto = findViewById(R.id.etProducto);
        etPrecio = findViewById(R.id.etPrecio);
        etCantidad = findViewById(R.id.etCantidad);
        tvTotal = findViewById(R.id.tvTotal);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        androidx.recyclerview.widget.RecyclerView rvRegistros = findViewById(R.id.rvRegistros);
        rvRegistros.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new ProformaAdapter(lista, this::seleccionarItem);
        rvRegistros.setAdapter(adaptador);

        // Nuevo
        btnNuevo.setOnClickListener(v -> limpiarCampos());

        // Grabar
        btnGrabar.setOnClickListener(v -> {
            if (!validarCampos()) return;

            String codigo = etCodigo.getText().toString().trim();
            String producto = etProducto.getText().toString().trim();
            double precio = Double.parseDouble(etPrecio.getText().toString().trim());
            int cantidad = Integer.parseInt(etCantidad.getText().toString().trim());

            lista.add(new ProformaItem(codigo, producto, precio, cantidad));
            adaptador.notifyItemInserted(lista.size() - 1);
            actualizarTotal();
            limpiarCampos();
        });

        // Actualizar
        btnActualizar.setOnClickListener(v -> {
            if (posicionSeleccionada == -1) {
                Toast.makeText(this, "Selecciona un producto de la lista", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validarCampos()) return;

            ProformaItem item = lista.get(posicionSeleccionada);
            item.setCodigo(etCodigo.getText().toString().trim());
            item.setProducto(etProducto.getText().toString().trim());
            item.setPrecio(Double.parseDouble(etPrecio.getText().toString().trim()));
            item.setCantidad(Integer.parseInt(etCantidad.getText().toString().trim()));

            adaptador.notifyItemChanged(posicionSeleccionada);
            actualizarTotal();
            limpiarCampos();
        });

        // Eliminar
        btnEliminar.setOnClickListener(v -> {
            if (posicionSeleccionada == -1) {
                Toast.makeText(this, "Selecciona un producto de la lista", Toast.LENGTH_SHORT).show();
                return;
            }
            lista.remove(posicionSeleccionada);
            adaptador.notifyDataSetChanged();
            actualizarTotal();
            limpiarCampos();
        });
        // Fin Programacion

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Se llama al tocar un ítem de la lista (RecyclerView)
    private void seleccionarItem(int position) {
        posicionSeleccionada = position;
        ProformaItem item = lista.get(position);
        etCodigo.setText(item.getCodigo());
        etProducto.setText(item.getProducto());
        etPrecio.setText(String.valueOf(item.getPrecio()));
        etCantidad.setText(String.valueOf(item.getCantidad()));
    }

    private boolean validarCampos() {
        if (etCodigo.getText().toString().trim().isEmpty()
                || etProducto.getText().toString().trim().isEmpty()
                || etPrecio.getText().toString().trim().isEmpty()
                || etCantidad.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(etPrecio.getText().toString().trim());
            Integer.parseInt(etCantidad.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio o cantidad inválidos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void actualizarTotal() {
        double total = 0;
        for (ProformaItem item : lista) {
            total += item.getTotal();
        }
        tvTotal.setText(String.format(Locale.getDefault(), "Total: S/. %.2f", total));
    }

    // Inicio Implementar
    private void limpiarCampos() {
        etCodigo.setText("");
        etProducto.setText("");
        etPrecio.setText("");
        etCantidad.setText("");
        posicionSeleccionada = -1;
        etCodigo.requestFocus();
    }
    // Fin Implementacion
}